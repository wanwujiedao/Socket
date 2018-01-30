package com.dao.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import java.util.Date;
import javax.swing.JOptionPane;
/**
 * 功能：获取服务器发来的信息进行处理
 * @author 旭
 *
 */
public class ClientGet implements Runnable {
 Socket socket;

 ClientPre client;

 public ClientGet(Socket socket,ClientPre client) {

    this.socket = socket;

    this.client=client;

 }
 @Override
 public void run() {
    try {
           InputStream is = socket.getInputStream();
           BufferedReader br = new BufferedReader(new InputStreamReader(is));
           String get = "";
           while ((get = br.readLine()) != null) {
           String[] getinfo = get.split("`");
           if (getinfo.length == 2 && getinfo[0].equals("-")){ 
                client.addCbUser(getinfo[1]);
            }
           else if(getinfo.length == 3 && getinfo[0].equals("-")){
                client.removeCbUser(getinfo[1]);
           }
          else {
                if (getinfo.length == 1)
                     client.setTxtShow(getinfo[0]);
                else {
                      int len=getinfo[0].length()+1;
                      String message=get.substring(len);
                      client.setTxtShow("["+new Date()+"]\n"+getinfo[0]+":"+message);
                      }
                client.setTxtShow("\n");
               }
          }
        } catch (IOException e) {
         
             JOptionPane.showMessageDialog(null,"服务器已崩溃或者关闭");
             System.exit(0);
         }
     }
}
