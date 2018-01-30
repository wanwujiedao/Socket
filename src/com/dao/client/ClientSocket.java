package com.dao.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JOptionPane;


/**
 *功能：验证能否链接服务器
 * @author 旭
 *
 */
public class ClientSocket {
 private Socket socket;
 private OutputStream os;
 private PrintStream send;

 public ClientSocket(String name, String add, int port) {
     socket = new Socket();

     try {
          socket.connect(new InetSocketAddress(add, port), 10000);
          ClientPre client = new ClientPre(name, socket);
          os = socket.getOutputStream();
          send = new PrintStream(os);
          send.println(name);

          new Thread(new ClientGet(socket, client)).start();
         
          Thread.sleep(100);
          if(client.getCBuserSize()==false){
               ClientPre.frmClient.setVisible(false);
               JOptionPane.showMessageDialog(null, "用户已存在");

               Client.main(null);      

          }
    

    } catch (IOException | InterruptedException e) {

              JOptionPane.showMessageDialog(null, "连接失败，请检查您的网络，IP地址或端口号是否正确！");
              Client.main(null);
      }
    }
}

