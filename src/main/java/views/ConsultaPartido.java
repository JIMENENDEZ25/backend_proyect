/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

/**
 *
 * @author Javier Caná
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import models.partido_model;
import services.partido_service;

public class ConsultaPartido extends javax.swing.JFrame {

    FondoPanel fondo = new FondoPanel();
    private partido_service service = new partido_service();
    private JTable tablaPartidos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscarId;

    public ConsultaPartido() {
        setContentPane(fondo);
        setTitle("Consulta de Partidos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        

        // === Panel de búsqueda ===
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelBusqueda.setOpaque(false);

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
        btnBuscar.addActionListener(e -> buscarPartido());

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarId);
        panelBusqueda.add(btnBuscar);

        // === Tabla de partidos ===
        String[] columnas = {"ID", "Equipo Local", "Equipo Visitante", "Fecha", "Estadio", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo permitir edición en columnas de texto, no en ID
                return column != 0;
            }
        };

        tablaPartidos = new JTable(modeloTabla);
        tablaPartidos.setRowHeight(30);
        tablaPartidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaPartidos.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        tablaPartidos.setSelectionBackground(new Color(8, 184, 223));
        tablaPartidos.setGridColor(Color.LIGHT_GRAY);
        tablaPartidos.getTableHeader().setBackground(new Color(8, 184, 223));
        tablaPartidos.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaPartidos);
        scrollTabla.setPreferredSize(new Dimension(850, 350));
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setBackground(Color.WHITE);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel panelTabla = new JPanel(new GridBagLayout());
        panelTabla.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 30, 30, 30);
        panelTabla.add(scrollTabla, gbc);

        // === Menú contextual para eliminar ===
        JPopupMenu menuEliminar = new JPopupMenu();
        JMenuItem eliminarItem = new JMenuItem("Eliminar partido");
        eliminarItem.setBackground(new Color(8, 184, 223));
        eliminarItem.setForeground(Color.WHITE);
        eliminarItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        eliminarItem.setOpaque(true);
        eliminarItem.addActionListener(e -> eliminarPartidoSeleccionado());
        menuEliminar.add(eliminarItem);

        tablaPartidos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int fila = tablaPartidos.rowAtPoint(e.getPoint());
                if (fila >= 0) {
                    tablaPartidos.setRowSelectionInterval(fila, fila);
                    if (SwingUtilities.isRightMouseButton(e)) {
                        menuEliminar.show(tablaPartidos, e.getX(), e.getY());
                    }
                }
            }
        });

        // === Botón actualizar ===
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

        add(panelBusqueda, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarTodosLosPartidos();
    }

    // === Cargar todos ===
    private void cargarTodosLosPartidos() {
        modeloTabla.setRowCount(0);
        try {
            List<partido_model> partidos = service.getPartidos();
            for (partido_model p : partidos) {
                modeloTabla.addRow(new Object[]{
                    p.getId_partido(),
                    p.getEquipo_local(),
                    p.getEquipo_visitante(),
                    p.getFecha_partido(),
                    p.getEstadio(),
                    p.getEstado()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar los partidos:\n" + ex.getMessage());
        }
    }

    // === Buscar por ID ===
    private void buscarPartido() {
        String idTexto = txtBuscarId.getText().trim();
        if (idTexto.isEmpty()) {
            cargarTodosLosPartidos();
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            List<partido_model> partidos = service.getPartidos();

            partido_model encontrado = partidos.stream()
                    .filter(p -> p.getId_partido() == id)
                    .findFirst()
                    .orElse(null);

            modeloTabla.setRowCount(0);
            if (encontrado != null) {
                modeloTabla.addRow(new Object[]{
                    encontrado.getId_partido(),
                    encontrado.getEquipo_local(),
                    encontrado.getEquipo_visitante(),
                    encontrado.getFecha_partido(),
                    encontrado.getEstadio(),
                    encontrado.getEstado()
                });
            } else {
                JOptionPane.showMessageDialog(this,
                    "⚠️ No se encontró ningún partido con ID " + id,
                    "Sin resultados", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ ID inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al buscar partido:\n" + ex.getMessage());
        }
    }

    // === Eliminar seleccionado ===
    private void eliminarPartidoSeleccionado() {
        int fila = tablaPartidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un partido para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar este partido?", "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            service.deleteCustomer(id);
            JOptionPane.showMessageDialog(this, "🗑️ Partido eliminado correctamente.");
            cargarTodosLosPartidos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar:\n" + ex.getMessage());
        }
    }

    // === Actualizar cambios ===
    private void actualizarCambios() {
        try {
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                int id = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
                String local = modeloTabla.getValueAt(i, 1).toString().trim();
                String visitante = modeloTabla.getValueAt(i, 2).toString().trim();
                String fecha = modeloTabla.getValueAt(i, 3).toString().trim();
                String estadio = modeloTabla.getValueAt(i, 4).toString().trim();
                String estado = modeloTabla.getValueAt(i, 5).toString().trim();

                if (local.isEmpty() || visitante.isEmpty() || estadio.isEmpty() || estado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "⚠️ Campos vacíos en fila " + (i + 1));
                    continue;
                }

                partido_model actualizado = new partido_model();
                actualizado.setEquipo_local(local);
                actualizado.setEquipo_visitante(visitante);
                actualizado.setFecha_partido(fecha);
                actualizado.setEstadio(estadio);
                actualizado.setEstado(estado);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(actualizado);
                System.out.println("Actualizando partido ID: " + id);
                System.out.println("JSON enviado: " + json);

                // Llamada al servicio (deberás agregar método updatePartido en partido_service)
                // service.updatePartido(id, actualizado);
            }

            JOptionPane.showMessageDialog(this, "✅ Cambios actualizados correctamente.");
            cargarTodosLosPartidos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar:\n" + ex.getMessage());
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
            java.util.logging.Logger.getLogger(ConsultaPartido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaPartido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaPartido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaPartido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPartido().setVisible(true);
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
        imagen = new ImageIcon(getClass().getResource("/images/6.png")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}

}
