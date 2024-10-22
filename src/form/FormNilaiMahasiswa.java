/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class FormNilaiMahasiswa extends javax.swing.JFrame {

    public Statement st;
    public ResultSet rs;
    Connection cn = koneksi.KoneksiDatabase.Koneksi();

    String nama, grade, keterangan;
    int nim;
    double nilai_tugas, nilai_uts, nilai_uas, nilai_kuis, rerata;

    public FormNilaiMahasiswa() {
        initComponents();
        setTitle("Mahasiswa TK 4 BAD Group 7");

        showData();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }

    private void exit() {
        int response = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            return;
        }
    }

    private void showData() {
        try {
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM MAHASISWA ORDER BY ID DESC");

            DefaultTableModel tblModel = new DefaultTableModel();
            tblModel.addColumn("No.");
            tblModel.addColumn("NIM");
            tblModel.addColumn("Nama");
            tblModel.addColumn("Rerata");
            tblModel.addColumn("Grade");
            tblModel.addColumn("Keterangan");

            tblModel.getDataVector().removeAllElements();
            tblModel.fireTableDataChanged();
            tblModel.setRowCount(0);

            int no = 1;
            while (rs.next()) {
                Object[] data = new Object[]{
                    no,
                    rs.getString("NIM"),
                    rs.getString("NAMA"),
                    rs.getString("RERATA"),
                    rs.getString("GRADE"),
                    rs.getString("KETERANGAN"),};
                tblModel.addRow(data);
                tblMahasiswa.setModel(tblModel);
                no++;
            }

        } catch (Exception ex) {

        }
    }

    private void reset() {
        txtNama.setText("");
        txtNIM.setText("");
        txtNilaiTugas.setText("");
        txtNilaiKuis.setText("");
        txtNilaiUTS.setText("");
        txtNilaiUAS.setText("");
        txtHasilNama.setText("");
        txtHasilNIM.setText("");
        txtHasilRerata.setText("");
        txtHasilGrade.setText("");
        txtHasilKeterangan.setText("");
        btnSimpan.setEnabled(false);

    }

    private boolean inputValidation() {
        if (txtNama.getText().equals("")
                || txtNIM.getText().equals("")
                || txtNilaiTugas.getText().equals("")
                || txtNilaiKuis.getText().equals("")
                || txtNilaiUTS.getText().equals("")
                || txtNilaiUAS.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Data tidak boleh kosong, silahkan lengkapi data!", "Validasi Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            nim = Integer.parseInt(txtNIM.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka", "Validasi Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            nilai_tugas = Integer.parseInt(txtNilaiTugas.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai Tugas harus berupa angka", "Validasi Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            nilai_kuis = Integer.parseInt(txtNilaiKuis.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai Kuis harus berupa angka", "Validasi Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            nilai_uts = Integer.parseInt(txtNilaiUTS.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai UTS harus berupa angka", "Validasi Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            nilai_uas = Integer.parseInt(txtNilaiUAS.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai UAS harus berupa angka", "Validasi Data", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void tampilHasil() {
        nama = txtNama.getText();
        txtHasilNama.setText(nama);

        txtHasilNIM.setText(Integer.toString(nim));
        txtHasilRerata.setText(Double.toString(rerata));
        txtHasilGrade.setText(grade);
        txtHasilKeterangan.setText(keterangan);
    }

    private void hitungNilai() {
        rerata = (nilai_tugas + nilai_kuis + nilai_uts + nilai_uas) / 4;
        String rerataString = Double.toString(rerata);

        if (rerata >= 90 && rerata <= 100) {
            grade = "A";
            keterangan = "Dinyatakan Lulus";
        } else if (rerata >= 80 && rerata < 90) {
            grade = "B";
            keterangan = "Dinyatakan Lulus";
        } else if (rerata >= 70 && rerata < 80) {
            grade = "C";
            keterangan = "Dinyatakan Mengulang";
        } else {
            grade = "D";
            keterangan = "Dinyatakan Tidak Lulus";
        }

    }

    private void simpan() {
        try {
            st = cn.createStatement();

            boolean isValid = inputValidation();

            if (isValid) {
                hitungNilai();
                tampilHasil();

                String cekNIM = "SELECT * FROM MAHASISWA WHERE NIM = '" + nim + "'";
                rs = st.executeQuery(cekNIM);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Mohon maaf, Mahasiswa dengan NIM tersebut sudah ada!");
                } else {
                    String sqlInsert = "INSERT INTO MAHASISWA (NAMA, NIM, NILAI_TUGAS, NILAI_KUIS, NILAI_UTS, NILAI_UAS, RERATA, GRADE, KETERANGAN) VALUES ('"
                            + nama + "','" + nim
                            + "','" + nilai_tugas + "','" + nilai_kuis
                            + "','" + nilai_uts + "','" + nilai_uas
                            + "','" + rerata + "','" + grade + "','" + keterangan
                            + "')";
                    st.executeUpdate(sqlInsert);
                    JOptionPane.showMessageDialog(this, "Data Mahasiswa berhasil disimpan!");
                    reset();
                    showData();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Data Mahasiswa gagal disimpan!", "Gagal", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelForm = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        labelNama = new javax.swing.JLabel();
        labelNIM = new javax.swing.JLabel();
        labelNilaiTugas = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtNIM = new javax.swing.JTextField();
        txtNilaiTugas = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNilaiKuis = new javax.swing.JTextField();
        labelNilaiUTS = new javax.swing.JLabel();
        txtNilaiUTS = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNilaiUAS = new javax.swing.JTextField();
        btnHitung = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        labelHasilNama = new javax.swing.JLabel();
        txtHasilNama = new javax.swing.JTextField();
        labelHasilNIM = new javax.swing.JLabel();
        txtHasilNIM = new javax.swing.JTextField();
        labelRerata = new javax.swing.JLabel();
        txtHasilRerata = new javax.swing.JTextField();
        labelHasilGrade = new javax.swing.JLabel();
        txtHasilGrade = new javax.swing.JTextField();
        labelHasilKeterangan = new javax.swing.JLabel();
        txtHasilKeterangan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMahasiswa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelForm.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Form Data Nilai Mahasiswa");

        labelNama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNama.setText("Nama");

        labelNIM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNIM.setText("NIM");

        labelNilaiTugas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNilaiTugas.setText("Nilai Tugas");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Nilai Kuis");

        labelNilaiUTS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNilaiUTS.setText("Nilai UTS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nilai UAS");

        btnHitung.setBackground(new java.awt.Color(112, 111, 229));
        btnHitung.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHitung.setForeground(new java.awt.Color(255, 255, 255));
        btnHitung.setText("HITUNG");
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(104, 109, 118));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(46, 62, 92));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("SIMPAN");
        btnSimpan.setEnabled(false);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(labelNilaiUTS)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addComponent(btnHitung, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNIM)
                            .addComponent(labelNilaiTugas)
                            .addComponent(labelNama))
                        .addGap(50, 50, 50)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNilaiTugas, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNilaiKuis, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNilaiUTS, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNilaiUAS)
                            .addComponent(txtNIM)
                            .addComponent(txtNama, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(53, 53, 53))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNama)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNIM)
                    .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNilaiTugas)
                    .addComponent(txtNilaiTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNilaiKuis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNilaiUTS)
                    .addComponent(txtNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNilaiUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Hasil Nilai");

        labelHasilNama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelHasilNama.setText("Nama");

        labelHasilNIM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelHasilNIM.setText("NIM");

        labelRerata.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelRerata.setText("Rerata");

        labelHasilGrade.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelHasilGrade.setText("Grade");

        labelHasilKeterangan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelHasilKeterangan.setText("Keterangan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelHasilNama)
                            .addComponent(labelHasilNIM)
                            .addComponent(labelRerata)
                            .addComponent(labelHasilGrade)
                            .addComponent(labelHasilKeterangan))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHasilNama)
                            .addComponent(txtHasilNIM)
                            .addComponent(txtHasilRerata)
                            .addComponent(txtHasilGrade)
                            .addComponent(txtHasilKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))))
                .addGap(84, 84, 84))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHasilNama)
                    .addComponent(txtHasilNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHasilNIM)
                    .addComponent(txtHasilNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRerata)
                    .addComponent(txtHasilRerata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHasilGrade)
                    .addComponent(txtHasilGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHasilKeterangan)
                    .addComponent(txtHasilKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Data Nilai Mahasiswa");

        tblMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No.", "NIM", "Nama", "Rerata", "Grade", "Keterangan"
            }
        ));
        jScrollPane1.setViewportView(tblMahasiswa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        boolean isValid = inputValidation();
        if (isValid) {
            hitungNilai();
            tampilHasil();
            btnSimpan.setEnabled(true);
        }

    }//GEN-LAST:event_btnHitungActionPerformed

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
            java.util.logging.Logger.getLogger(FormNilaiMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormNilaiMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormNilaiMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormNilaiMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormNilaiMahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelHasilGrade;
    private javax.swing.JLabel labelHasilKeterangan;
    private javax.swing.JLabel labelHasilNIM;
    private javax.swing.JLabel labelHasilNama;
    private javax.swing.JLabel labelNIM;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelNilaiTugas;
    private javax.swing.JLabel labelNilaiUTS;
    private javax.swing.JLabel labelRerata;
    private javax.swing.JPanel panelForm;
    private javax.swing.JTable tblMahasiswa;
    private javax.swing.JTextField txtHasilGrade;
    private javax.swing.JTextField txtHasilKeterangan;
    private javax.swing.JTextField txtHasilNIM;
    private javax.swing.JTextField txtHasilNama;
    private javax.swing.JTextField txtHasilRerata;
    private javax.swing.JTextField txtNIM;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNilaiKuis;
    private javax.swing.JTextField txtNilaiTugas;
    private javax.swing.JTextField txtNilaiUAS;
    private javax.swing.JTextField txtNilaiUTS;
    // End of variables declaration//GEN-END:variables
}
