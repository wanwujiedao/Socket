package com.dao.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Client extends JFrame {

 private JPanel contentPane;
 private JTextField txtName;
 private JTextField txtadd;
 private JTextField txtPort;

/**
 * 功能：客户端入口
 * 指定端口号，用户名，ip地址
 * @param args
 */
 public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
    public void run() {
    try {
           Client frame = new Client();
           frame.setVisible(true);
       } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
 }

 /**
  * Create the frame.
  */
 public Client() {
        super("请输入用户名：");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtName = new JTextField();
        txtName.setBounds(200, 81, 134, 21);
        contentPane.add(txtName);
        txtName.setColumns(1);

        txtadd = new JTextField();
        txtadd.setBounds(200, 121, 134, 21);
        txtadd.setText("127.0.0.1");
        contentPane.add(txtadd);
        txtadd.setColumns(1);

        txtPort = new JTextField();
        txtPort.setBounds(200, 161, 134, 21);
        txtPort.setText("1221");
        contentPane.add(txtPort);
        txtPort.setColumns(1);

        JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D");
        lblNewLabel.setBounds(128, 84, 54, 15);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabe = new JLabel("服务器IP");
        lblNewLabe.setBounds(128, 124, 54, 15);
        contentPane.add(lblNewLabe);

        JLabel lblNewLab = new JLabel("端口号");
        lblNewLab.setBounds(128, 164, 54, 15);
        contentPane.add(lblNewLab);

        JButton btnConnection = new JButton("\u8FDE\u63A5");
        btnConnection.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
        dealThisbtn();
       }
      });
      btnConnection.setBounds(200, 203, 93, 23);
      contentPane.add(btnConnection);
 }

 protected void dealThisbtn() {
         if (txtName.getText().isEmpty()) {
             JOptionPane.showMessageDialog(txtName, "请输入用户名");
             txtName.requestFocus();
        }else if (txtadd.getText().isEmpty()) {
             JOptionPane.showMessageDialog(txtadd, "请输入服务器IP");
             txtadd.requestFocus();
        }else if (txtPort.getText().isEmpty()) {
             JOptionPane.showMessageDialog(txtPort, "请输入端口号");
             txtPort.requestFocus();
        }  else if (isPort(txtadd.getText())) {
              JOptionPane.showMessageDialog(txtPort, "请输入合法的端口号");
              txtPort.requestFocus();
        } else if (isIP(txtadd.getText())) {
              this.setVisible(false);
              new ClientSocket(txtName.getText(), txtadd.getText(),Integer.valueOf(txtPort.getText()));

       } else {
             JOptionPane.showMessageDialog(txtadd, "请输入合法的服务器IP");
             txtadd.requestFocus();
    }
 } 

 private boolean isPort(String text) {
      try {
          Integer.valueOf(txtPort.getText());
          return false;
       } catch (Exception e) {
         return true;
     }
  
 }

 private boolean isIP(String text) {
  String[] ip = text.split("\\.");

  try {
        if (Integer.valueOf(ip[0]) > 254 || Integer.valueOf(ip[0]) < 1)
                                return false;
        for (int pox = 0; pox < 4; pox++) {

             if (Integer.valueOf(ip[pox]) > 255
                    || Integer.valueOf(ip[pox]) < 0)
                           return false;

          }
            return true;
       } catch (Exception e) {

              return false;
      }

    }

}

