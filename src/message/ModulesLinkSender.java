/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.URLS;

import com.kingway.dao.impl.DatabaseDaoImpl;



/**
 *
 * @author Administrator
 */
public class ModulesLinkSender extends Thread {

    /**
     *短信息类，用于发送短信
     *
     * @author Administrator
     */
    /** 同步锁，不能改变 ，用于在线程外部唤醒本线程*/
    public final Object lock = new Object();
    /** 网址*/
    private URL url;
    /** 网页连接*/
    private URLConnection conn;
    /** 是否保持运行，用于终止终程*/
    private volatile boolean keepRun = true;
    /**存储了信息内容的数组*/
    private ArrayList<MessageInfo> message = null;
    /**输出流，用于发送请求*/
    private OutputStreamWriter wr;
    /**输入流，用于获取响应*/
    private BufferedReader rd;
    /**信息控制线程，用于向此线程反馈已发送的信息*/
    private MessageServer messageServer;
    private DatabaseDaoImpl db = new DatabaseDaoImpl();
    private int maxNum;
    private int sleepTime;
    private CommonPara sender;
    private int failTimes = 0;
    private static String replace = "&+";
    private static String BASE=URLS.getHostAddress();
    private final static String mTitle = "尊敬的[userName]:\r\n您有关注更新!详情请登录：\r\n"+BASE+"/personalfocus/mobile/mobileIndex";


//      private final static String mTitle = "尊敬的[userName]:\r\n您有关注更新!详情请点击：\r\nhttp://www.my/my?userID=[userID]password=[password]\r\n";
//    private final static String updateContent = "[websiteName]_[userModuleName] [updateTime]\r\n";
    /**
     *在发送短信链接时不能有除提供商规定的字段以外的 &符号
     * @param dbp
     * @param messageServer
     * @param sender
     * @param sleepTime
     * @param maxNum
     * @throws java.net.MalformedURLException
     * @throws java.lang.Exception
     */
    public ModulesLinkSender(MessageServer messageServer, CommonPara sender, int sleepTime, int maxNum) throws MalformedURLException, Exception {
        this.sender = sender;
        this.sleepTime = sleepTime * 1000;
        url = new URL(this.sender.address);
        message = new ArrayList<MessageInfo>(20);
        this.messageServer = messageServer;
        this.maxNum = maxNum;
        
    }

    /**
     * 在同步块加入要发送的短信
     * @param m 短信参数
     * @return 是否加入成功
     */
    public synchronized boolean addMessage(MessageInfo m) {
        return this.message.add(m);
    }

    /**
     * 在同步块删除已发送的短信
     * @param m 已发送的短信的引用
     * @return 是否加入成功
     */
    public synchronized boolean removeMessage(MessageInfo m) {
        return this.message.remove(m);

    }

    /**
     * 发送短信,由于短信服务提供商的限制，建立一次连接只能发送一条短信
     * 一条短信不能超过180个字符
     * @param message 信息字符串，包含信息参数
     *
     */
    public void sendMessage(String message) throws IOException {
        int headend = message.lastIndexOf("=");
        String head = message.substring(0, headend + 1);
        int begin = headend + 1;
        System.out.println(message);
        while (message.length() - begin > 170) {

            conn = url.openConnection(); //取得连接
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(head + message.substring(begin, begin + 170).replaceAll(replace, "")); //发送信息
            wr.flush();
            // Get the response
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            wr.close();
            rd.close();
            begin = begin + 170;
        }
        conn = url.openConnection(); //取得连接
        conn.setDoOutput(true);
        wr = new OutputStreamWriter(conn.getOutputStream());
        //  System.out.println(url.toString() + head);
        wr.write(head + message.substring(begin, message.length()).replaceAll(replace, "")); //发送信息
        //       wr.write(head+"123"); //发送信息

        wr.flush();
        // Get the response
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        wr.close();
        rd.close();
    }

    @Override
    public void run() {

        MessageInfo current;
        for (; this.keepRun == true;) {
            try {
                //获取要发送的信息
                this.message = this.getModuleLink(this.maxNum);
                //发送短信
                System.out.print(this.message.size());
                while (this.message.size() > 0) {
                    try {
                        current = this.message.get(0);
                        //    System.out.print("短信2："+current.message);
                        this.sendMessage(current.message);
                        //重置失败次数
                        this.failTimes = 0;
                        this.removeMessage(current);
                        ModulesLinkSender.sleep(this.sleepTime);
                        continue;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ModulesLinkSender.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        if (this.failTimes++ > 10) {
                            //线程退出
                            this.keepRun = false;
                            break;
                        } else {
                            //睡眠一段时间再连接
                        }
                        Logger.getLogger(ModulesLinkSender.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }
                try {
                    ModulesLinkSender.sleep(this.sleepTime);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(ModulesLinkSender.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (Exception ex1) {
                Logger.getLogger(ModulesLinkSender.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    /**
     *由参数数组组成要发送的字符串
     * @param para 信息字符串的数组
     * @return 要发送的字符串
     */
    public String getMessageStr(ArrayList<MessageParameter> para) {
        StringBuilder temp = new StringBuilder(60);
        int i = 0;
        for (i = 0; i < para.size() - 1; i++) {
            temp.append(para.get(i).parameter + "=");
            temp.append(para.get(i).value + "&");
        }
        temp.append(para.get(i).parameter + "=");
        temp.append(para.get(i).value);
        return new String(temp);
    }

    /**
     * 停止发送
     */
    public void stopRun() {
        this.keepRun = false;
    }

    /**
     * 检查线程是否在运行
     * @return
     */
    public boolean isRunning() {
        return this.keepRun;
    }

    /**
     * 改变睡眠时间，以调节性能
     * @param sleepTime
     */
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime * 1000;
    }

    private ArrayList<MessageInfo> getModuleLink(int maxNum) {

        ArrayList<ModuleLinkStruct> temp = db.getModuleInfo(maxNum);
        ArrayList<MessageInfo> meinfo = new ArrayList<MessageInfo>(maxNum);
        ArrayList<MessageParameter> para;
    
        String currentPhone = "";
        String nextPhone = "";
        para = new ArrayList<MessageParameter>(10);
        for (int i = 0; i < temp.size(); i++) {
            nextPhone = temp.get(i).phone;
            if (nextPhone.equals(currentPhone) == true) {
                continue;
            } else {
            	  currentPhone = temp.get(0).phone;
                  para.add(new MessageParameter("username", this.sender.user));
                  para.add(new MessageParameter("password", this.sender.password));
                  para.add(new MessageParameter("sendto", temp.get(i).phone));
                  para.add(new MessageParameter("message", mTitle.replaceAll("\\[userName\\]", temp.get(i).userName)));
                  meinfo.add(new MessageInfo(this.getMessageStr(para)));
          }
        }
        return meinfo;
    }
}
