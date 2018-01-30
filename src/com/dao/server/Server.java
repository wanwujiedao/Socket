package com.dao.server;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 功能:服务器，指定端口号，准备创建socket服务器
 * @author 旭
 *
 */
public class Server {
    private static JTextField txtPort;

    public static void main(String[] args) {
        final JFrame frm = new JFrame("请输入端口号");
        frm.setLayout(null);
        JLabel lblNewLab = new JLabel("端口号");
        lblNewLab.setBounds(80, 34, 54, 15);
        frm.add(lblNewLab);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        txtPort = new JTextField();
        txtPort.setBounds(150, 31, 134, 21);
        txtPort.setText("1221");
        frm.add(txtPort);
        txtPort.setColumns(1);
        JButton btnStart = new JButton("启动");
        btnStart.setBounds(150, 83, 93, 23);
        frm.add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (txtPort.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(txtPort, "请输入端口号");
                    txtPort.requestFocus();
                }
                try {
                    int port = Integer.valueOf(txtPort.getText());
                    frm.setVisible(false);
                    new Thread(new ServerPre(port)).start();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(txtPort, "端口号请输入数字型字符");
                    txtPort.requestFocus();
                }
            }
        });
        frm.setBounds(100, 100, 350, 200);
        frm.setVisible(true);
    }

}
