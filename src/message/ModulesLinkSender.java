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
     *����Ϣ�࣬���ڷ��Ͷ���
     *
     * @author Administrator
     */
    /** ͬ���������ܸı� ���������߳��ⲿ���ѱ��߳�*/
    public final Object lock = new Object();
    /** ��ַ*/
    private URL url;
    /** ��ҳ����*/
    private URLConnection conn;
    /** �Ƿ񱣳����У�������ֹ�ճ�*/
    private volatile boolean keepRun = true;
    /**�洢����Ϣ���ݵ�����*/
    private ArrayList<MessageInfo> message = null;
    /**����������ڷ�������*/
    private OutputStreamWriter wr;
    /**�����������ڻ�ȡ��Ӧ*/
    private BufferedReader rd;
    /**��Ϣ�����̣߳���������̷߳����ѷ��͵���Ϣ*/
    private MessageServer messageServer;
    private DatabaseDaoImpl db = new DatabaseDaoImpl();
    private int maxNum;
    private int sleepTime;
    private CommonPara sender;
    private int failTimes = 0;
    private static String replace = "&+";
    private static String BASE=URLS.getHostAddress();
    private final static String mTitle = "�𾴵�[userName]:\r\n���й�ע����!�������¼��\r\n"+BASE+"/personalfocus/mobile/mobileIndex";


//      private final static String mTitle = "�𾴵�[userName]:\r\n���й�ע����!����������\r\nhttp://www.my/my?userID=[userID]password=[password]\r\n";
//    private final static String updateContent = "[websiteName]_[userModuleName] [updateTime]\r\n";
    /**
     *�ڷ��Ͷ�������ʱ�����г��ṩ�̹涨���ֶ������ &����
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
     * ��ͬ�������Ҫ���͵Ķ���
     * @param m ���Ų���
     * @return �Ƿ����ɹ�
     */
    public synchronized boolean addMessage(MessageInfo m) {
        return this.message.add(m);
    }

    /**
     * ��ͬ����ɾ���ѷ��͵Ķ���
     * @param m �ѷ��͵Ķ��ŵ�����
     * @return �Ƿ����ɹ�
     */
    public synchronized boolean removeMessage(MessageInfo m) {
        return this.message.remove(m);

    }

    /**
     * ���Ͷ���,���ڶ��ŷ����ṩ�̵����ƣ�����һ������ֻ�ܷ���һ������
     * һ�����Ų��ܳ���180���ַ�
     * @param message ��Ϣ�ַ�����������Ϣ����
     *
     */
    public void sendMessage(String message) throws IOException {
        int headend = message.lastIndexOf("=");
        String head = message.substring(0, headend + 1);
        int begin = headend + 1;
        System.out.println(message);
        while (message.length() - begin > 170) {

            conn = url.openConnection(); //ȡ������
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(head + message.substring(begin, begin + 170).replaceAll(replace, "")); //������Ϣ
            wr.flush();
            // Get the response
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            wr.close();
            rd.close();
            begin = begin + 170;
        }
        conn = url.openConnection(); //ȡ������
        conn.setDoOutput(true);
        wr = new OutputStreamWriter(conn.getOutputStream());
        //  System.out.println(url.toString() + head);
        wr.write(head + message.substring(begin, message.length()).replaceAll(replace, "")); //������Ϣ
        //       wr.write(head+"123"); //������Ϣ

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
                //��ȡҪ���͵���Ϣ
                this.message = this.getModuleLink(this.maxNum);
                //���Ͷ���
                System.out.print(this.message.size());
                while (this.message.size() > 0) {
                    try {
                        current = this.message.get(0);
                        //    System.out.print("����2��"+current.message);
                        this.sendMessage(current.message);
                        //����ʧ�ܴ���
                        this.failTimes = 0;
                        this.removeMessage(current);
                        ModulesLinkSender.sleep(this.sleepTime);
                        continue;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ModulesLinkSender.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        if (this.failTimes++ > 10) {
                            //�߳��˳�
                            this.keepRun = false;
                            break;
                        } else {
                            //˯��һ��ʱ��������
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
     *�ɲ����������Ҫ���͵��ַ���
     * @param para ��Ϣ�ַ���������
     * @return Ҫ���͵��ַ���
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
     * ֹͣ����
     */
    public void stopRun() {
        this.keepRun = false;
    }

    /**
     * ����߳��Ƿ�������
     * @return
     */
    public boolean isRunning() {
        return this.keepRun;
    }

    /**
     * �ı�˯��ʱ�䣬�Ե�������
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
