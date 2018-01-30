package com.dao.server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * 功能：校验端口号是否能用，不能则返回，否则创建个服务器
 * @author 旭
 *
 */
public class ServerPre implements Runnable{
 private  JFrame frame;
 private  JPanel contentPane;
 public static JTextArea log;
 private ServerSocket serverSocket;
 private int port;
public ServerPre(int port) {
  
    this.port=port; 
}
@Override
public void run() {
 
   frame = new JFrame("服务端日志");
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setBounds(100, 100, 711, 663);
   contentPane = new JPanel();
   contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
   frame.setContentPane(contentPane);
   contentPane.setLayout(new BorderLayout(0, 0));
   log = new JTextArea();
   log.setEditable(false);
   JScrollPane scrollPane = new JScrollPane(log);
   contentPane.add(scrollPane, BorderLayout.CENTER);
   frame.setResizable(false);
   frame.setVisible(true);
   try {
     serverSocket = new ServerSocket(port);
     while (true) {
          Socket socket = serverSocket.accept();
               new Thread(new ConnectionTread(socket)).start();
        }
     } catch (IOException e) {
        frame.setVisible(false);
       JOptionPane.showMessageDialog(null,"端口号初始化失败（无效或被占用），请重新输入端口号");
       Server.main(null);
    }
  }
}
