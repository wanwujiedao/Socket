package com.dao.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能：处理客户端发送来的消息
 * 
 * @author 旭
 * 
 */
public class ConnectionTread implements Runnable {
 public static Map<String, Socket> socketsMap = new HashMap<String, Socket>();// 存储Socket
 private Socket socket;

 public ConnectionTread(Socket socket) {
  this.socket = socket;
 }

 public void run() {
  try {
   String get = "";
//   while (true) {
    InputStream is = socket.getInputStream();
    BufferedReader br = new BufferedReader(
      new InputStreamReader(is));
    String info;
    while ((info = br.readLine()) != null) {
     String send = "";
     boolean flag = true;
     for (String user : socketsMap.keySet()) {
      if (socketsMap.get(user).equals(socket)) {
       flag = false;
       send = user;
       String message = "";
       String[] arg = info.split("`");
       if (arg.length == 1) {// 获取信息以及接收方

        message = info;
       } else {
        get = arg[0];
        int len = arg[0].length() + 1;
        message = info.substring(len);
       }

       if (send.equals(get)) {// 群发
        for (String user1 : socketsMap.keySet()) {
         if (user1.equals(send))
          continue;
         Socket sk = socketsMap.get(user1);
         OutputStream os = sk.getOutputStream();
         PrintStream sendUserName = new PrintStream(
           os);
         sendUserName.println(send + "`" + message);
        }
        ServerPre.log
          .append("["+new Date()+"]\n"+send
            + "send message to all users,the message is“"
            + message + "”;\n");

       } else {
        Socket sk = socketsMap.get(get);
        OutputStream os = sk.getOutputStream();
        PrintStream sendUserName = new PrintStream(os);
        sendUserName.println(send + "`" + message);
        ServerPre.log.append("["+new Date()+"]\n"+send + "send message to"
          + get + ",the message is “" + message
          + "” ;\n");

       }
      }

     }
     if (flag) {
      socketsMap.put(info, socket);// 有新用户进入

      for (String user : socketsMap.keySet()) {// 将新用户ID信息发送给所有用户

       Socket sk = socketsMap.get(user);
       OutputStream os = sk.getOutputStream();
       PrintStream sendUserName = new PrintStream(os);
       for (String user1 : socketsMap.keySet()) {
        String sendUser = "-`" + user1;// 刷新用户
        sendUserName.println(sendUser);
       }
      }
      ServerPre.log.append("["+new Date()+"]\n"+"The user who is " + info
        + " connected!\n");

     }

    }

   //}

  } catch (IOException e) {
   // TODO Auto-generated catch block
   for (String user : socketsMap.keySet()) {// 有用户下线
    Socket sk = socketsMap.get(user);
    if (socket.equals(sk)) {
     ServerPre.log.append("["+new Date()+"]\n"+"The user who is " + user
       + " has disconnected!\n");
     for (String user1 : socketsMap.keySet()) {
      OutputStream os;
      try {
       os = socketsMap.get(user1).getOutputStream();
       PrintStream sendUserName = new PrintStream(os);
       sendUserName.println("-`" + user + "`-");
      } catch (IOException e1) {
       // TODO 自动生成的 catch 块
       e1.printStackTrace();
      }

     }
     socketsMap.remove(user);
    }

   }

  }

 }

}

