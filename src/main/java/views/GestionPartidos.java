/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

/**
 *
 * @author Javier Caná
 */

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import services.partido_service;
import models.partido_model;
import java.util.Date;
import java.util.List;
import views.Consultar;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import services.localidad_service;





public class GestionPartidos extends javax.swing.JFrame {
    
    FondoPanel fondo = new FondoPanel();
    private partido_service service = new partido_service();


    /**
     * Creates new form GestionUsuarios
     */
public GestionPartidos() {
    
    initComponents();
    setLocationRelativeTo(null);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);

    // === Fondo principal ===
    jPanel1.setLayout(new java.awt.BorderLayout());
    jPanel1.setOpaque(false);

    // === Panel superior (formulario) ===
    panelSuperior.setOpaque(false);
    panelSuperior.setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    gbc.insets = new java.awt.Insets(15, 15, 15, 15);
    gbc.anchor = java.awt.GridBagConstraints.WEST;
    gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

    // === Fuentes ===
    java.awt.Font fuenteLabel = new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 16);
    java.awt.Font fuenteCampo = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15);

    // === Asignar fuentes a labels ===
    jLabelEquipoLocal.setFont(fuenteLabel);
    jLabelEquipoVisitante.setFont(fuenteLabel);
    jLabelFecha.setFont(fuenteLabel);
    jLabelEstadio.setFont(fuenteLabel);
    jLabelEstado.setFont(fuenteLabel);

    // === Campos de texto (más compactos) ===
    JTextField[] campos = { txtEquipoLocal, txtEquipoVisitante, txtFecha, txtHora };
    for (JTextField t : campos) {
        t.setFont(fuenteCampo);
        t.setPreferredSize(new java.awt.Dimension(200, 30)); // más corto
        t.setBackground(new java.awt.Color(255, 255, 255, 230));
        t.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
    }

    // === ComboBox ===
    comboEstadio.setFont(fuenteCampo);
    comboEstadio.setBackground(new java.awt.Color(255, 255, 255, 230));
    comboEstadio.setPreferredSize(new java.awt.Dimension(200, 30));

    comboEstado.setFont(fuenteCampo);
    comboEstado.setBackground(new java.awt.Color(255, 255, 255, 230));
    comboEstado.setPreferredSize(new java.awt.Dimension(200, 30));

    // === Agregar items a comboEstado ===
    comboEstado.removeAllItems();
    comboEstado.addItem("programado");
    comboEstado.addItem("activo");
    comboEstado.addItem("finalizado");
    comboEstado.setSelectedIndex(0);

    // === Cargar estadios (localidades) desde la API ===
    comboEstadio.addItem("Cargando localidades...");
    try {
        services.localidad_service locService = new services.localidad_service();
        java.util.List<models.localidad_model4> localidades = locService.getLocalidades();

        comboEstadio.removeAllItems();
        for (models.localidad_model4 loc : localidades) {
            comboEstadio.addItem(loc.getNombre());
        }
    } catch (Exception e) {
        comboEstadio.removeAllItems();
        comboEstadio.addItem("Error al cargar");
        JOptionPane.showMessageDialog(this,
            "Error cargando localidades desde la API: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }

    // === Colocar los labels y campos ===
    gbc.gridx = 0; gbc.gridy = 0;
    panelSuperior.add(jLabelEquipoLocal, gbc);
    gbc.gridx = 1;
    panelSuperior.add(txtEquipoLocal, gbc);

    gbc.gridx = 0; gbc.gridy++;
    panelSuperior.add(jLabelEquipoVisitante, gbc);
    gbc.gridx = 1;
    panelSuperior.add(txtEquipoVisitante, gbc);

    gbc.gridx = 0; gbc.gridy++;
    panelSuperior.add(jLabelFecha, gbc);
    gbc.gridx = 1;
    JPanel panelFechaHora = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
    panelFechaHora.setOpaque(false);
    panelFechaHora.add(txtFecha);
    panelFechaHora.add(txtHora);
    panelSuperior.add(panelFechaHora, gbc);

    gbc.gridx = 0; gbc.gridy++;
    panelSuperior.add(jLabelEstadio, gbc);
    gbc.gridx = 1;
    panelSuperior.add(comboEstadio, gbc);

    gbc.gridx = 0; gbc.gridy++;
    panelSuperior.add(jLabelEstado, gbc);
    gbc.gridx = 1;
    panelSuperior.add(comboEstado, gbc);

    // === Panel inferior de botones ===
    panelBotones.setOpaque(false);
    panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 20));

    JButton[] botones = { btnVolver, btnCrearPartido, btnAgregarLocalidad, btnConsultar };
    for (JButton b : botones) {
        b.setPreferredSize(new java.awt.Dimension(150, 40));
        b.setBackground(new java.awt.Color(8, 184, 223));
        b.setForeground(Color.WHITE);
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelBotones.add(b);
    }

    // === Acciones de botones ===
    btnVolver.addActionListener(e -> {
        new Administracion().setVisible(true);
        this.dispose();
    });

    btnAgregarLocalidad.addActionListener(e -> {
        new GestionLocalidades().setVisible(true);
        this.dispose();
    });

    btnConsultar.addActionListener(e -> {
        new ConsultaPartido().setVisible(true);
        this.dispose();
    });

    // === Añadir ambos paneles ===
    jPanel1.add(panelSuperior, java.awt.BorderLayout.CENTER);
    jPanel1.add(panelBotones, java.awt.BorderLayout.SOUTH);
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
        panelSuperior = new javax.swing.JPanel();
        jLabelEquipoLocal = new javax.swing.JLabel();
        jLabelEquipoVisitante = new javax.swing.JLabel();
        jLabelFecha = new javax.swing.JLabel();
        jLabelEstadio = new javax.swing.JLabel();
        jLabelEstado = new javax.swing.JLabel();
        txtEquipoLocal = new javax.swing.JTextField();
        txtEquipoVisitante = new javax.swing.JTextField();
        comboEstadio = new javax.swing.JComboBox<>();
        comboEstado = new javax.swing.JComboBox<>();
        txtFecha = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        panelBotones = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        btnCrearPartido = new javax.swing.JButton();
        btnAgregarLocalidad = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelEquipoLocal.setText("Local");

        jLabelEquipoVisitante.setText("Visitante");

        jLabelFecha.setText("Fecha");

        jLabelEstadio.setText("Estadio");

        jLabelEstado.setText("Estado");

        comboEstadio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnVolver.setText("Regresar");

        btnCrearPartido.setText("Crear");
        btnCrearPartido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearPartidoActionPerformed(evt);
            }
        });

        btnAgregarLocalidad.setText("Localidades");

        btnConsultar.setText("Consultas");

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(btnVolver)
                .addGap(40, 40, 40)
                .addComponent(btnCrearPartido)
                .addGap(33, 33, 33)
                .addComponent(btnAgregarLocalidad)
                .addGap(18, 18, 18)
                .addComponent(btnConsultar)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap(320, Short.MAX_VALUE)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(btnAgregarLocalidad)
                    .addComponent(btnCrearPartido)
                    .addComponent(btnConsultar))
                .addGap(67, 67, 67))
        );

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabelEstadio)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelEquipoLocal)
                                            .addComponent(jLabelEquipoVisitante)))
                                    .addComponent(jLabelFecha, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(12, 12, 12)
                                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEquipoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEquipoVisitante, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 23, Short.MAX_VALUE))))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabelEstado)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(comboEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 297, Short.MAX_VALUE)
                    .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 298, Short.MAX_VALUE)))
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEquipoLocal)
                    .addComponent(txtEquipoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEquipoVisitante)
                    .addComponent(txtEquipoVisitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabelEstadio))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(433, Short.MAX_VALUE))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabelEstado)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(comboEstadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelSuperiorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(panelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearPartidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPartidoActionPerformed
        // TODO add your handling code here:
        // === Validar campos ===
    String equipoLocal = txtEquipoLocal.getText().trim();
    String equipoVisitante = txtEquipoVisitante.getText().trim();
    String fecha = txtFecha.getText().trim();
    String hora = txtHora.getText().trim();
    String estadio = comboEstadio.getSelectedItem() != null ? comboEstadio.getSelectedItem().toString() : "";
    String estado = comboEstado.getSelectedItem() != null ? comboEstado.getSelectedItem().toString() : "";

    if (equipoLocal.isEmpty() || equipoVisitante.isEmpty() || fecha.isEmpty() || hora.isEmpty() || estadio.isEmpty() || estado.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "⚠️ Todos los campos son obligatorios.",
            "Validación",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // === Formato de fecha para Sequelize ===
    String fechaCompleta = fecha + " " + hora + ":00"; // ejemplo: 2025-10-27 15:30:00

    // === Crear el objeto partido ===
    models.partido_model partido = new models.partido_model();
    partido.setEquipo_local(equipoLocal);
    partido.setEquipo_visitante(equipoVisitante);
    partido.setFecha_partido(fechaCompleta);
    partido.setEstadio(estadio);
    partido.setEstado(estado);

    // === Llamar al servicio ===
    services.partido_service service = new services.partido_service();

    try {
        models.partido_model partidoCreado = service.createPartido(partido);

        if (partidoCreado != null && partidoCreado.getEquipo_local() != null) {
            JOptionPane.showMessageDialog(this,
                "✅ Partido creado correctamente:\n" +
                partidoCreado.getEquipo_local() + " vs " + partidoCreado.getEquipo_visitante(),
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Limpiar campos
            txtEquipoLocal.setText("");
            txtEquipoVisitante.setText("");
            txtFecha.setText("");
            txtHora.setText("");
            comboEstadio.setSelectedIndex(0);
            comboEstado.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this,
                "⚠️ El servidor respondió pero no se pudo procesar el partido.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "❌ Error al crear el partido:\n" + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
        ex.printStackTrace();
    }


        
       
    }//GEN-LAST:event_btnCrearPartidoActionPerformed

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
            java.util.logging.Logger.getLogger(GestionPartidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionPartidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionPartidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionPartidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionPartidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarLocalidad;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrearPartido;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboEstadio;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JLabel jLabelEquipoLocal;
    private javax.swing.JLabel jLabelEquipoVisitante;
    private javax.swing.JLabel jLabelEstadio;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTextField txtEquipoLocal;
    private javax.swing.JTextField txtEquipoVisitante;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    // End of variables declaration//GEN-END:variables
class FondoPanel extends JPanel {
    private Image imagen;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagen = new ImageIcon(getClass().getResource("/images/5.jpg")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}

}
