package com.musabrt;

import com.musabrt.async.ICallback;
import com.musabrt.async.TType;
import com.musabrt.async.Threader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

class CreateFrame {
    Threader threader;
    CreateFrame(Threader threader) {
        createFrame();
        this.threader = threader;
    }

    private static String keyz = "";

    private void createFrame() {

        JFrame f = new JFrame();
        f.setSize(455,235);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setTitle("Cryptography");

        JTextArea textbox = new JTextArea();
        textbox.setBounds(10, 10, 375, 50);
        textbox.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
        textbox.setFont(new Font("Arial", Font.PLAIN, 12));
        textbox.setLineWrap(true);

        JButton buton = new JButton("Encrypt");
        buton.setFont(new Font("Arial", Font.PLAIN, 12));
        buton.setBounds(9, 70, 186, 35);

        JButton but = new JButton("Decrypt");
        but.setFont(new Font("Arial", Font.PLAIN, 12));
        but.setBounds(199, 70, 186, 35);

        JTextArea txtbox = new JTextArea();
        txtbox.setBounds(10, 115, 375, 50);
        txtbox.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
        txtbox.setFont(new Font("Arial", Font.PLAIN, 12));
        txtbox.setLineWrap(true);
        txtbox.setEditable(false);

        JTextArea ttb = new JTextArea();
        ttb.setBounds(10, 175, 275, 20);
        ttb.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
        ttb.setFont(new Font("Arial", Font.PLAIN, 12));
        ttb.setLineWrap(true);

        JButton ttbut = new JButton("Set Key");
        ttbut.setFont(new Font("Arial", Font.PLAIN, 12));
        ttbut.setBounds(290, 174, 95, 22);

        JRadioButton jraes = new JRadioButton("AES");
        jraes.setBounds(390, 17, 50, 15);

        JRadioButton jrmd = new JRadioButton("MD5");
        jrmd.setBounds(390, 35, 50, 15);

        JRadioButton jdes = new JRadioButton("DES3");
        jdes.setBounds(390, 53, 50, 15);

        f.setResizable(false);
        f.add(jraes);
        f.add(jrmd);
        f.add(jdes);
        f.add(ttbut);
        f.add(ttb);
        f.add(but);
        f.add(txtbox);
        f.add(buton);
        f.add(textbox);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

        // --------------------Listeners--------------------------------

        buton.addActionListener(e -> {
            if (!jrmd.isSelected()) {
                if (jraes.isSelected()) {
                    if (keyz.equalsIgnoreCase("")) {
                        JOptionPane.showMessageDialog(null, "Incorrect key. Please try again.", "Key Error", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        threader.registerCallback(new ICallback() {
                            @Override
                            public void Callback(String str) {
                                txtbox.setText(str);
                            }
                        });
                        threader.runAes(keyz, textbox.getText(), TType.ENCODE);
                    }
                }
                else if (!jdes.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select any hash algorithm", "Null Hash Type", JOptionPane.PLAIN_MESSAGE);
                } else {
                    threader.registerCallback(new ICallback() {
                        @Override
                        public void Callback(String str) {
                            txtbox.setText(str);
                        }
                    });
                    threader.runTDes(textbox.getText(), TType.ENCODE);
                }
            } else {
                if (keyz.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Incorrect key. Please try again.", "Key Error", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    threader.registerCallback(new ICallback() {
                        @Override
                        public void Callback(String str) {
                            txtbox.setText(str);
                        }
                    });
                    threader.runMd5(keyz, textbox.getText(), TType.ENCODE);
                }
            }
        });
        but.addActionListener(e -> {
            if (!jrmd.isSelected()) {
                if (jraes.isSelected()) {
                    if (keyz.equalsIgnoreCase("")) {
                        JOptionPane.showMessageDialog(null, "Incorrect key. Please try again.", "Key Error", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        threader.registerCallback(new ICallback() {
                            @Override
                            public void Callback(String str) {
                                txtbox.setText(str);
                            }
                        });
                        threader.runAes(keyz, textbox.getText(), TType.DECODE);
                    }
                }
                else if (jdes.isSelected()) {
                    threader.registerCallback(new ICallback() {
                        @Override
                        public void Callback(String str) {
                            txtbox.setText(str);
                        }
                    });
                    threader.runTDes(textbox.getText(), TType.DECODE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please select any hash algorithm", "Null Hash Type", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                if (keyz.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Incorrect key. Please try again.", "Key Error", JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    threader.registerCallback(new ICallback() {
                        @Override
                        public void Callback(String str) {
                            txtbox.setText(str);
                        }
                    });
                    threader.runMd5(keyz, textbox.getText(), TType.DECODE);
                }
            }
        });
        ttbut.addActionListener(e -> {
            keyz = ttb.getText();
            ttb.setBorder(new BorderUIResource.LineBorderUIResource(Color.GREEN));
        });

        jraes.addActionListener(e -> {
            if (!jrmd.isSelected()) {
                if (jdes.isSelected()) jdes.setSelected(false);
            } else {
                jrmd.setSelected(false);
            }
            ttb.setEnabled(true);
            ttbut.setEnabled(true);
        });
        jrmd.addActionListener(e -> {
            if (!jraes.isSelected()) {
                if (jdes.isSelected()) jdes.setSelected(false);
            } else {
                jraes.setSelected(false);
            }
            ttb.setEnabled(true);
            ttbut.setEnabled(true);
        });
        jdes.addActionListener(e -> {
            if (!jraes.isSelected()) {
                if (jrmd.isSelected()) {jrmd.setSelected(false);}
            } else {
                jraes.setSelected(false);
            }
            ttb.setEnabled(false);
            ttb.setText("");
            ttb.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
            ttbut.setEnabled(false);
        });
        ttb.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                ttb.setBorder(new BorderUIResource.LineBorderUIResource(Color.RED));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                ttb.setBorder(new BorderUIResource.LineBorderUIResource(Color.RED));
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }
        });

    }
}