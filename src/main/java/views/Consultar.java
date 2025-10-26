/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import services.usuario_service;
import models.usuario_model;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;


/**
 *
 * @author HP
 */
public class Consultar extends javax.swing.JFrame {

    FondoPanel fondo = new FondoPanel();
    private usuario_service service = new usuario_service();
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscarId;
    
    public Consultar() {
    setContentPane(fondo);
    setTitle("Consulta de Usuarios");
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    // === Panel de búsqueda alineado a la izquierda ===
    JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
    panelBusqueda.setOpaque(false);

    // Estilo del texto "Buscar por ID" sin fondo, letra blanca
    JLabel lblBuscar = new JLabel("Buscar por ID:");
    lblBuscar.setForeground(Color.WHITE);
    lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));

    txtBuscarId = new JTextField(10);
    txtBuscarId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    txtBuscarId.setPreferredSize(new Dimension(150, 35));
    txtBuscarId.setBackground(new Color(255, 255, 255, 230));
    txtBuscarId.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

    JButton btnBuscar = new JButton("Buscar");
    btnBuscar.setBackground(new Color(8, 184, 223));
    btnBuscar.setForeground(Color.WHITE);
    btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnBuscar.setFocusPainted(false);
    btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnBuscar.addActionListener(e -> buscarUsuario());

    panelBusqueda.add(lblBuscar);
    panelBusqueda.add(txtBuscarId);
    panelBusqueda.add(btnBuscar);

    // === Tabla de usuarios con columna "Creación" ===
    String[] columnas = {"ID", "Usuario", "Contraseña", "Nombre Completo", "Rol", "Creación"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0 && column != 4 && column != 5;
        }
    };

    tablaUsuarios = new JTable(modeloTabla);
    tablaUsuarios.setRowHeight(30);
    tablaUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    tablaUsuarios.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
    tablaUsuarios.setSelectionBackground(new Color(8, 184, 223));
    tablaUsuarios.setGridColor(Color.LIGHT_GRAY);
    tablaUsuarios.getTableHeader().setBackground(new Color(8, 184, 223));
    tablaUsuarios.getTableHeader().setForeground(Color.WHITE);

    // === ScrollPane sin bordes visibles ===
    JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
    scrollTabla.setPreferredSize(new Dimension(700, 300));
    scrollTabla.setBorder(BorderFactory.createEmptyBorder());
    scrollTabla.setBackground(Color.WHITE);
    scrollTabla.getViewport().setBackground(Color.WHITE);

    // === Panel centrado con espacio alrededor para efecto "flotante" ===
    JPanel panelTabla = new JPanel(new GridBagLayout());
    panelTabla.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(40, 40, 40, 40); // espacio alrededor
    panelTabla.add(scrollTabla, gbc);

    // === Menú contextual para eliminar con estilo de botón ===
    JPopupMenu menuEliminar = new JPopupMenu();
    JMenuItem eliminarItem = new JMenuItem("Eliminar usuario");
    eliminarItem.setBackground(new Color(8, 184, 223));
    eliminarItem.setForeground(Color.WHITE);
    eliminarItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
    eliminarItem.setOpaque(true);
    eliminarItem.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    eliminarItem.addActionListener(e -> eliminarUsuarioSeleccionado());
    menuEliminar.add(eliminarItem);

    tablaUsuarios.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            int fila = tablaUsuarios.rowAtPoint(e.getPoint());
            if (fila >= 0) {
                tablaUsuarios.setRowSelectionInterval(fila, fila);
                if (SwingUtilities.isRightMouseButton(e)) {
                    menuEliminar.show(tablaUsuarios, e.getX(), e.getY());
                }
            }
        }
    });

    // === Botón actualizar con estilo consistente ===
    JButton btnActualizar = new JButton("Actualizar cambios");
    btnActualizar.setBackground(new Color(8, 184, 223));
    btnActualizar.setForeground(Color.WHITE);
    btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnActualizar.setFocusPainted(false);
    btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnActualizar.addActionListener(e -> actualizarCambios());

    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    panelBotones.setOpaque(false);
    panelBotones.add(btnActualizar);

    // === Añadir todo a la ventana ===
    add(panelBusqueda, BorderLayout.NORTH);     // búsqueda arriba a la izquierda
    add(panelTabla, BorderLayout.CENTER);       // tabla centrada y flotante
    add(panelBotones, BorderLayout.SOUTH);      // botón actualizar abajo

    // === Cargar usuarios al iniciar ===
    cargarTodosLosUsuarios();
}



    
private void buscarUsuario() {
    String idTexto = txtBuscarId.getText().trim();
    if (idTexto.isEmpty()) {
        cargarTodosLosUsuarios(); // Si está vacío, carga todos
        return;
    }

    try {
        int id = Integer.parseInt(idTexto);
        usuario_model u = service.getUsuarioPorId(id);

        modeloTabla.setRowCount(0); // Limpiar tabla
        modeloTabla.addRow(new Object[]{
            u.getId_usuario(),
            u.getNombre_usuario(),
            "*****",
            u.getNombre_completo(),
            u.getRol(),
            u.getFecha_creacion() != null ? u.getFecha_creacion() : "Sin fecha"
        });
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "⚠️ ID inválido.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "❌ Usuario no encontrado:\n" + ex.getMessage());
    }
}

    
private void eliminarUsuarioSeleccionado() {
    int fila = tablaUsuarios.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "⚠️ Selecciona un usuario para eliminar.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    try {
        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        service.deleteUsuario(id);
        JOptionPane.showMessageDialog(this, "🗑️ Usuario eliminado correctamente.");
        cargarTodosLosUsuarios();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "❌ Error al eliminar:\n" + ex.getMessage());
    }
}


private void actualizarCambios() {
    try {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int id = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
            String usuario = modeloTabla.getValueAt(i, 1).toString().trim();
            String contrasena = modeloTabla.getValueAt(i, 2).toString().trim();
            String nombre = modeloTabla.getValueAt(i, 3).toString().trim();
            String rol = modeloTabla.getValueAt(i, 4).toString().trim();

            // Validación de campos vacíos
            if (usuario.isEmpty() || nombre.isEmpty() || rol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Campos vacíos en fila " + (i + 1));
                continue;
            }

            // Validación de duplicado en nombre de usuario
            if (nombreUsuarioDuplicado(usuario, id)) {
                JOptionPane.showMessageDialog(this, "⚠️ El nombre de usuario '" + usuario + "' ya existe en otra fila.");
                continue;
            }

            usuario_model actualizado = new usuario_model();
            actualizado.setId_usuario(id); // por si el backend lo requiere
            actualizado.setNombre_usuario(usuario);
            actualizado.setNombre_completo(nombre);
            actualizado.setRol(rol);

            // Solo enviar contraseña si fue modificada
            if (!contrasena.equals("*****") && !contrasena.isEmpty()) {
                actualizado.setContrasena(contrasena);
            }

            // Depuración: imprimir JSON enviado
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(actualizado);
            System.out.println("Actualizando ID: " + id);
            System.out.println("JSON enviado: " + json);

            service.actualizarUsuario(id, actualizado);
        }

        JOptionPane.showMessageDialog(this, "✅ Cambios actualizados correctamente.");
        cargarTodosLosUsuarios();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "❌ Error al actualizar:\n" + ex.getMessage());
    }
}

private boolean nombreUsuarioDuplicado(String nombre, int idActual) {
    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
        int idFila = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
        if (idFila == idActual) continue;

        String otroNombre = modeloTabla.getValueAt(i, 1).toString().trim();
        if (nombre.equalsIgnoreCase(otroNombre)) {
            return true;
        }
    }
    return false;
}






  private void cargarTodosLosUsuarios() {
    modeloTabla.setRowCount(0); // Limpiar tabla

    try {
        List<usuario_model> usuarios = service.getTodosLosUsuarios();

        for (usuario_model u : usuarios) {
            modeloTabla.addRow(new Object[]{
                u.getId_usuario(),
                u.getNombre_usuario(),
                "*****", // Contraseña oculta por seguridad
                u.getNombre_completo(),
                u.getRol(),
                u.getFecha_creacion() != null ? u.getFecha_creacion() : "Sin fecha"
            });
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "❌ Error al cargar usuarios:\n" + ex.getMessage());
    }
}





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new FondoPanel();
        panelBotones = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        create = new javax.swing.JButton();
        jButtonConsultar = new javax.swing.JButton();
        panelSuperior = new javax.swing.JPanel();
        jLabelUsuario = new javax.swing.JLabel();
        jLabelContrasena = new javax.swing.JLabel();
        jLabelNombreCompleto = new javax.swing.JLabel();
        jLabelRol = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtFullName = new javax.swing.JTextField();
        comboRol = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Regresar");

        create.setText("Crear");
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });

        jButtonConsultar.setText("Consultar");

        jLabelUsuario.setText("Usuario");

        jLabelContrasena.setText("Contraseña");

        jLabelNombreCompleto.setText("Nombre Completo");

        jLabelRol.setText("Rol");

        comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabelRol)
                        .addGap(433, 433, 433)
                        .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(99, Short.MAX_VALUE))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUsuario)
                                    .addComponent(jLabelContrasena)))
                            .addComponent(jLabelNombreCompleto, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsuario)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelContrasena)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabelRol))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNombreCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(439, 439, 439)
                        .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jButton1)
                .addGap(40, 40, 40)
                .addComponent(create)
                .addGap(33, 33, 33)
                .addComponent(jButtonConsultar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButtonConsultar)
                    .addComponent(create))
                .addGap(67, 67, 67))
        );

        jPanel1.add(panelBotones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 410));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed
    
    }//GEN-LAST:event_createActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consultar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consultar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JButton create;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonConsultar;
    private javax.swing.JLabel jLabelContrasena;
    private javax.swing.JLabel jLabelNombreCompleto;
    private javax.swing.JLabel jLabelRol;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
class FondoPanel extends JPanel {
    private Image imagen;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagen = new ImageIcon(getClass().getResource("/images/4.png")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}

}
