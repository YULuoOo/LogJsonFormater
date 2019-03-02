
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;

public class SwingLoginExample extends JFrame implements ActionListener {
    JSONFormater jf = new JSONFormater();
    JButton jb = new JButton("打开文件");
    String filename;
    String outpath;

    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        new SwingLoginExample();
    }

    private SwingLoginExample() {
        jb.setActionCommand("open");
        jb.setBackground(Color.GREEN);//设置按钮颜色
        this.getContentPane().add(jb, BorderLayout.NORTH);//建立容器使用边界布局
        //
        jb.addActionListener(this);
        this.setTitle("标题");
        this.setSize(333, 288);
        this.setLocation(200, 200);
        //显示窗口true
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open")) {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(this);//显示打开的文件对话框
            File f = jf.getSelectedFile();//使用文件类获取选择器选择的文件
            filename = f.getAbsolutePath();//返回路径名
            outpath = filename+"temp";
            //JOptionPane弹出对话框类，显示绝对路径名
            JOptionPane.showMessageDialog(this, filename, "标题", JOptionPane.WARNING_MESSAGE);
//            try {
//                println(getContent(filename));
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            String etCommand = "D:/Program Files/WPS/8.1.0.3526/office6/et.exe";
            try {
                println(getContent(filename));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    public byte[] getContent(String filePath) throws IOException {
        File file = new File(filename);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return jf.format(buffer);
    }
    public void println(byte[] str) throws IOException {
        File file = new File(outpath);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);


            bos.write(str);
            bos.flush();
            bos.close();

            System.out.println("Done");

        }
    }
