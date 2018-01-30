package com.dao.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;

public class ClientPre {

 private JPanel contentPane;
 public static JFrame frmClient;
 private OutputStream os;
 private PrintStream send;
 private Socket socket;
 private JTextArea txtShow;
 private JTextArea txtSend;
 private String name;
 private JComboBox<String> cbUser;
 private List<String> list = new ArrayList<String>();

/**
 * 功能：发送信息或接收信息界面
 * @param name
 * @param socket
 */
 public ClientPre(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        frmClient = new JFrame(name);
        frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmClient.addWindowListener(new WindowAdapter() {

   @Override
       public void windowClosing(WindowEvent arg0) {
    // TODO Auto-generated method stub

       System.exit(0);

   }

  });

  frmClient.setBounds(100, 100, 575, 405);
  contentPane = new JPanel();
  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
  frmClient.setContentPane(contentPane);
  contentPane.setLayout(null);

  JPanel pal = new JPanel();
  pal.setBounds(10, 10, 539, 346);
  contentPane.add(pal);
  pal.setLayout(null);

  txtSend = new JTextArea();
  txtSend.setBounds(10, 286, 440, 50);
  pal.add(txtSend);

  txtShow = new JTextArea();
  txtShow.setEditable(false);
  txtShow.setBounds(10, 10, 519, 266);
  JScrollPane scrollPane = new JScrollPane(txtShow);
  scrollPane.setBounds(10, 10, 519, 266);
  pal.add(scrollPane);

  JButton btnSend = new JButton("\u53D1\u9001");
       btnSend.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
            if (txtSend.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(txtSend, "发送内容不能为空");
                 txtSend.requestFocus();
              } else {
                  if ((txtSend.getText().charAt(0) + "").contains("-")) {
                  JOptionPane.showMessageDialog(txtSend, "发送内容不能以“-”开头");
                  txtSend.requestFocus();
             } else
                 send();
           }
       }
  });
     txtSend.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
             if(e.getKeyCode()==10) {
                 if (txtSend.getText().isEmpty()) {
                     JOptionPane.showMessageDialog(txtSend, "发送内容不能为空");
                     txtSend.requestFocus();
                 } else {
                     if ((txtSend.getText().charAt(0) + "").contains("-")) {
                         JOptionPane.showMessageDialog(txtSend, "发送内容不能以“-”开头");
                         txtSend.requestFocus();
                     } else
                         send();
                 }
             }
         }
     });
  btnSend.setBounds(460, 313, 69, 23);
  pal.add(btnSend);

  cbUser = new JComboBox<String>();
  cbUser.setBounds(464, 287, 65, 21);
  pal.add(cbUser);
  frmClient.setResizable(false);
  frmClient.setVisible(true);

 }

 protected void send() {//发送信息
  
  try {
   os = socket.getOutputStream();
   send = new PrintStream(os);
   send.println(cbUser.getSelectedItem().toString() + "`"
     + txtSend.getText());
   txtShow.append("["+new Date()+"]\n"+name + ":" + txtSend.getText() + "\n");

   txtSend.setText("");
  } catch (IOException e) {
   e.printStackTrace();
  }

 }

 public void addCbUser(String txt) {//将新用户添加至下拉框
  if (list.contains(txt))
   return;
  list.add(txt);
  cbUser.addItem(txt);
 }

 public void setTxtShow(String txt) {//显示发送与接收到信息
  this.txtShow.append(txt);
 }

 public String getTxtSend() {//得到发送的信息
  return txtSend.getText();
 }

 public void removeCbUser(String txt) {//移除下线的用户
  list.remove(txt);
  cbUser.removeItem(txt);

 }

 public boolean getCBuserSize() {//判断用户是否存在

  if(cbUser.getSelectedIndex()==-1)
   return false;
  return true;
 }

}

