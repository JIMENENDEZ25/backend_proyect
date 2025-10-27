package views;

import java.awt.*;
import javax.swing.*;
/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;*/
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.util.List;
import services.usuario_service;
import models.usuario_model;
import views.Administracion;



public class inicio extends javax.swing.JFrame {
    private usuario_service userService;
    private boolean mostrar = true;
    private char echoOriginal;
    private PanelFondo fondo;
    public inicio() {
        userService = new usuario_service();
        fondo = new PanelFondo("/images/4.png");
        setContentPane(fondo);
        
        initComponents();
        
        setLocationRelativeTo(null);
        echoOriginal = txtPassword.getEchoChar();
           }

    class PanelFondo extends JPanel{
        private Image imagen;
        public PanelFondo(String ruta){
            imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setVisible(true);
        }
    }
    
    /*public usuario_model login(String nombre_usuario, String contrasena) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        Prepared
    }*/
    
private void iniciarSesion() {
    String nombre_usuario = txtUser.getText().trim();
    String contrasena = new String(txtPassword.getPassword()).trim();

    if (nombre_usuario.isEmpty() || contrasena.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 💡 IMPORTANTE: Habilitar y mostrar mensaje de espera antes de la tarea.
    btnLogin.setEnabled(false);
    lblMessage.setText("Validando...");

    // INICIO: UN SOLO SWINGWORKER
    new SwingWorker<usuario_model, Void>() {
        @Override
        protected usuario_model doInBackground() throws Exception {
            // Llama al servicio para intentar el login
            return userService.login(nombre_usuario, contrasena);
        }
        
        @Override
        protected void done(){
            try{
                usuario_model usuarioLogueado = get();
                
                // 1. PRIMERO: Verificar si el login fue exitoso (usuarioLogueado no es null)
                if (usuarioLogueado != null) {
                    
                    // 2. Si es exitoso, obtenemos el rol de forma segura
                    String rol = usuarioLogueado.getRol() != null ? usuarioLogueado.getRol().trim() : ""; 
                    
                    System.out.println("DEBUG: usuarioLogueado NO es null. Verificando rol...");
                    System.out.println("DEBUG: El rol obtenido es: [" + rol + "]");

                    // 3. Verificamos el rol para la redirección
                    if ("administrador".equalsIgnoreCase(rol)) {
                        System.out.println("DEBUG: Entrando al IF de 'administrador'.");
                        new Administracion().setVisible(true);
                        inicio.this.dispose(); 

                    } /*else if ("vendedor".equalsIgnoreCase(rol)) {
                        System.out.println("DEBUG: Entrando al ELSE IF de 'vendedor'.");
                        new Ventas(usuarioLogueado).setVisible(true); 
                        inicio.this.dispose();

                    }*/ else { 
                        // Rol no reconocido o rol vacío ("")
                        System.out.println("DEBUG: Entrando al ELSE (Rol no reconocido)."); 
                        JOptionPane.showMessageDialog(inicio.this, 
                            "Rol de usuario no reconocido: " + rol, 
                            "Error de Acceso", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                    
                } else {
                    // 4. Si el login falló (usuarioLogueado es null)
                    System.out.println("DEBUG: Entrando al ELSE (usuarioLogueado fue null).");
                    JOptionPane.showMessageDialog(inicio.this, 
                        "Usuario o contraseña incorrectos.", 
                        "Error de Inicio de Sesión", 
                        JOptionPane.ERROR_MESSAGE);
                    lblMessage.setText("Usuario o contraseña incorrectos.");
                }
            } catch(Exception ex){
                // Manejo de errores de 'get()' o al crear las ventanas
                System.out.println("DEBUG: Ha ocurrido una EXCEPCIÓN en done(): " + ex.getMessage());
                JOptionPane.showMessageDialog(inicio.this, 
                    "Error del sistema: " + ex.getMessage(),
                    "Error Crítico",
                    JOptionPane.ERROR_MESSAGE);
                lblMessage.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                // Siempre se ejecuta, re-habilita el botón
                btnLogin.setEnabled(true);
                if(lblMessage.getText() != null && lblMessage.getText().equals("Validando...")){
                    lblMessage.setText("");
                }
            }
        }
    }.execute(); // 👈 ¡ESTA LÍNEA ES FUNDAMENTAL!
    // FIN: UN SOLO SWINGWORKER
}
    private void togglePasswordView(){
        if (!mostrar) {
        txtPassword.setEchoChar((char) 0); // Muestra
        btnMostrar.setText("Ocultar");
        mostrar = true;
    } else {
        txtPassword.setEchoChar(echoOriginal); // Oculta
        btnMostrar.setText("Ver");
        mostrar = false;
    }
        }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TITLE = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        lblMessage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1 = new PanelFondo("/images/4.png");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TITLE.setBackground(new java.awt.Color(0, 0, 0));
        TITLE.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TITLE.setText("INICIO SESIÓN");
        jPanel1.add(TITLE, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 210, -1, -1));

        lblUser.setBackground(new java.awt.Color(0, 0, 0));
        lblUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUser.setText("Usuario:");
        jPanel1.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 360, -1, -1));
        jPanel1.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 360, 210, 30));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPassword.setText("Contraseña:");
        jPanel1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 420, -1, -1));

        btnLogin.setBackground(new java.awt.Color(8, 184, 223));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogin.setText("Iniciar Sesion");
        btnLogin.setBorder(null);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 540, 180, 50));

        btnMostrar.setBackground(new java.awt.Color(0, 0, 0));
        btnMostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 420, 80, 30));

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 210, 30));
        jPanel1.add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 630, 350, 30));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, 10, 1370, 770));

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

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        iniciarSesion();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        togglePasswordView();
    }//GEN-LAST:event_btnMostrarActionPerformed
  

    public static void main(String args[]) {
java.awt.EventQueue.invokeLater(() -> new inicio().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TITLE;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
