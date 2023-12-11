
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class compi_Interfaz extends javax.swing.JFrame {

    JFileChooser seleccionar = new JFileChooser();
    private File selectedFile;
    File archivo;
    FileInputStream entrada;
    FileOutputStream salida;

    //Declarar Objeto
    lexico Obj_lexico = new lexico();

    public compi_Interfaz() {
        initComponents();

        btnAccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                System.setOut(ps);

                lexico b = new lexico();
                // b.run();

                System.out.flush();
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

                String consoleOutput = baos.toString();
                txtAreaSalida.setText(consoleOutput);
                //txtAreaSalida.append("\nCodigo Intermedio\nOperador\tOperando\tOperando\tResultado\n");
                //txtAreaSalida.append("=\t3\t5\tcontenedor1");
                
            }
        });
        
        

        btnChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    loadFileContent(selectedFile);
                }
            }
        });

        // Agrega un DocumentListener para rastrear cambios en el txtAreaEntrada
        txtAreaEntrada.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                saveFileContent(selectedFile, txtAreaEntrada.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                saveFileContent(selectedFile, txtAreaEntrada.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                saveFileContent(selectedFile, txtAreaEntrada.getText());
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnChooseFile = new javax.swing.JButton();
        btnAccion = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaEntrada = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaSalida = new javax.swing.JTextArea();
        lblSin = new javax.swing.JLabel();
        lblLex = new javax.swing.JLabel();
        lblSema = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaSalidaCI = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Interfaz Compilador");
        setResizable(false);

        btnChooseFile.setText("Buscar");
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        btnAccion.setText("Ejecutar");
        btnAccion.setEnabled(false);
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCompilar.setText("Compilar");
        btnCompilar.setEnabled(false);
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        txtAreaEntrada.setColumns(20);
        txtAreaEntrada.setRows(5);
        jScrollPane1.setViewportView(txtAreaEntrada);

        jLabel1.setText("Estados:");

        txtAreaSalida.setEditable(false);
        txtAreaSalida.setColumns(20);
        txtAreaSalida.setRows(5);
        jScrollPane2.setViewportView(txtAreaSalida);

        lblSin.setText("2) Sintáctico: --");

        lblLex.setText("1) Léxico: --");

        lblSema.setText("3) Semántico: --");

        txtAreaSalidaCI.setEditable(false);
        txtAreaSalidaCI.setColumns(20);
        txtAreaSalidaCI.setRows(5);
        jScrollPane3.setViewportView(txtAreaSalidaCI);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(lblLex)
                                .addGap(153, 153, 153)
                                .addComponent(lblSin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblSema)
                                .addGap(77, 77, 77))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLex)
                            .addComponent(lblSin)
                            .addComponent(lblSema))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
// Verificar si el JTextArea contiene texto
        if (txtAreaEntrada.getText().trim().isEmpty()) {
            // No hay contenido en el JTextArea, no se hace nada
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea guardar el archivo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            JFileChooser fileChooser = new JFileChooser();

            // Muestra el cuadro de diálogo para que el usuario elija la ubicación para guardar el archivo
            int seleccion = fileChooser.showSaveDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                try {
                    // Crea un BufferedWriter para escribir en el archivo
                    BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

                    // Obtiene el texto del JTextArea
                    String texto = txtAreaEntrada.getText();

                    // Escribe el contenido del JTextArea en el archivo
                    writer.write(texto);

                    // Cierra el BufferedWriter
                    writer.close();

                    txtAreaEntrada.setText("");

                    JOptionPane.showMessageDialog(this, "El archivo se ha guardado correctamente.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // El usuario canceló la operación
            }

        } else if (respuesta == JOptionPane.NO_OPTION) {
            txtAreaEntrada.setText("");
        } else {
            // Código para cancelar la acción (si el usuario cierra el cuadro de diálogo)
        }    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (seleccionar.showDialog(null, "Guardar") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();
            if (archivo.getName().endsWith("txt")) {
                String Documento = txtAreaEntrada.getText();
                String mensaje = GuardarArchivo(archivo, Documento);
                if (mensaje != null) {
                    JOptionPane.showMessageDialog(null, mensaje);
                } else {
                    JOptionPane.showMessageDialog(null, "Archivo No Compatible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Guardar Documento de Texto");
            }
        }    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        btnAccion.setEnabled(true);
        if (Obj_lexico.errorEncontrado) {
            lblLex.setText("1) Léxico: --");
        } else {
            lblLex.setText("1) Léxico: OK");
        }

        if (Obj_lexico.errorFlag) {
            lblSin.setText("2) Sintaxis: --");
        } else {
            lblSin.setText("2) Sintaxis: OK");
        }
        
        if (Obj_lexico.errorEncontradoSemantico && Obj_lexico.errorFlag) {
            lblSema.setText("3) Semántico: --");
        }else{
            lblSema.setText("3) Semántico: OK");
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        btnCompilar.setEnabled(true);        
        btnGuardar.setEnabled(true);
    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
       imprimirCodigoIntermedio();
    }//GEN-LAST:event_btnAccionActionPerformed

    
    private void loadFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            txtAreaEntrada.setText(content.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void imprimirCodigoIntermedio(){
        File archivoCI = new File("C:\\Users\\Adrian\\Documents\\NetBeansProjects\\Compilador_LyA2\\src\\output.txt");
        try {
            BufferedReader leer = new BufferedReader(new FileReader (archivoCI));
            String linea = leer.readLine();
            while(linea != null){
                txtAreaSalidaCI.append(linea+"\n");
                linea = leer.readLine();
            }
        } catch (Exception ex) {
            Logger.getLogger(compi_Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveFileContent(File file, String content) {
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String GuardarArchivo(File archivo, String documento) {
        String mensaje = null;
        try {
            salida = new FileOutputStream(archivo);
            byte[] bytxt = documento.getBytes();
            salida.write(bytxt);
            mensaje = "Archivo Guardado";
        } catch (Exception e) {
        }
        return mensaje;
    }
    
    

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
            java.util.logging.Logger.getLogger(compi_Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(compi_Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(compi_Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(compi_Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new compi_Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblLex;
    private javax.swing.JLabel lblSema;
    private javax.swing.JLabel lblSin;
    private javax.swing.JTextArea txtAreaEntrada;
    private javax.swing.JTextArea txtAreaSalida;
    private javax.swing.JTextArea txtAreaSalidaCI;
    // End of variables declaration//GEN-END:variables
}
