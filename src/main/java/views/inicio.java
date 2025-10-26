package views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import services.usuario_service;
import models.usuario_model;

public class inicio extends javax.swing.JFrame {
    private usuario_service userService;
    public inicio() {
        userService = new usuario_service();
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
           setSize(1366, 770);  // O usa pack() si prefieres auto-ajuste
           // Cargar y agregar JLabel como fondo
           URL imagenURL = getClass().getResource("/3.png");  // Desde src/main/resources
           if (imagenURL != null) {
               ImageIcon iconoOriginal = new ImageIcon(imagenURL);
               // Escala la imagen al tamaño del JFrame
               Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                   getWidth(), getHeight(), Image.SCALE_SMOOTH);
               JLabel labelFondo = new JLabel(new ImageIcon(imagenEscalada));
               
               // Posiciona el JLabel para cubrir todo el JFrame
               labelFondo.setBounds(0, 0, 1366, 770);
               
               // Agrega el JLabel al final para que esté "debajo" de otros componentes
               add(labelFondo);
               
               // Si usas layout absoluto, asegúrate de que otros componentes se superpongan
               setLayout(null);  // O ajusta según tu layout actual
               
               // Ejemplo: Reposiciona un botón existente (si lo tienes del Designer)
               // jButton1.setBounds(100, 100, 100, 30);
           } else {
               System.err.println("Error: Imagen no encontrada en resources");
           }
           btnLogin.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e){
                   handleLogin();
               }
           });
           
           setVisible(true);
           }

    private void handleLogin(){
        String username = txtUser.getText().trim();
        String password = txtPassword.getText().trim();
        
        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contrasenia", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            List<usuario_model> users = userService.getUser();
            
            boolean loginSuccesful = false;
            usuario_model loggedUser = null;
            for(usuario_model user:users){
                if(user.getNombre_usuario().equals(username) && user.getContrasena().equals(password)){
                    loginSuccesful = true;
                    loggedUser = user;
                    break;
                }
            }
            if(loginSuccesful){
                JOptionPane.showMessageDialog(this, "Login exitodo. Bienvenido" + loggedUser.getNombre_completo() + "!", "Exito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Usuario o contrania incorrectos.");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error al conectar con el servidor: " + ex.getMessage(), "Error de conexion", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
        jButton1 = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TITLE.setBackground(new java.awt.Color(0, 0, 0));
        TITLE.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TITLE.setForeground(new java.awt.Color(0, 0, 0));
        TITLE.setText("INICIO SESIÓN");
        jPanel1.add(TITLE, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, -1, -1));

        lblUser.setBackground(new java.awt.Color(0, 0, 0));
        lblUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUser.setForeground(new java.awt.Color(0, 0, 0));
        lblUser.setText("Usuario:");
        jPanel1.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, -1, -1));

        txtUser.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 360, 210, 30));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(0, 0, 0));
        lblPassword.setText("Contraseña:");
        jPanel1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 420, -1, -1));

        btnLogin.setBackground(new java.awt.Color(8, 216, 223));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 0, 0));
        btnLogin.setText("Iniciar Sesión");
        btnLogin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 540, 170, 50));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ver");
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 420, 50, 30));

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 210, 30));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\jimem\\OneDrive\\Documentos\\NetBeansProjects\\venta_boletos\\src\\main\\resources\\images\\3.png")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

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
        //System.out.println("Inicio sesión exitoso");
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed
  
    
    public static void main(String args[]) {
java.awt.EventQueue.invokeLater(() -> new inicio().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TITLE;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
