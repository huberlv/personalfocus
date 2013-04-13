/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class MessageServer extends Thread{
        /**���ڴ�źͿ��������е���Ϣ�����߳�*/
    private ArrayList<ModulesLinkSender> messageSender;
       /**��Ϣ�����̵߳�����*/
    private int initMessageSenderNum;
    /**��ַ*/
    private String urlstr;
    /**ÿ���������ݿ�ȡ�صĸ�����Ϣ��*/
    private int onccGetMNnum;
        /**�洢����Ϣ���ݵ�����*/
    private ArrayList< MessageInfo> message=null;
        /** �Ƿ񱣳����У�������ֹ�ճ�*/
    private volatile boolean keepRun=true;
    private int sleepTime;
    
/**
 * ���캯��
 * @param urlstr
 * @param initMessageSenderNum
 * @param onccGetMNnum
 * @param sleepTime
 * @throws java.net.MalformedURLException
 * @throws java.sql.SQLException
 * @throws java.lang.ClassNotFoundException
 * @throws java.lang.Exception
 */
    public MessageServer(String urlstr,int initMessageSenderNum,int onccGetMNnum,int sleepTime) throws MalformedURLException, SQLException, ClassNotFoundException, Exception
    {
         this.urlstr=urlstr;
         this.initMessageSenderNum=initMessageSenderNum;
         this.onccGetMNnum=onccGetMNnum;
         ModulesLinkSender newmessageSender;
         this.sleepTime=sleepTime;
         for(int i=0;i<initMessageSenderNum;i++)
         {
             newmessageSender=new ModulesLinkSender(null,null,10,10);
             newmessageSender.start();
             this.messageSender.add(newmessageSender);
         }
    }

    /**
     * �ı�˯��ʱ�䣬�Ե�������
     * @param sleepTime
     */
    public void setSleepTime(int sleepTime)
    {
        this.sleepTime=sleepTime*1000;
    }
        /**
     * ֹͣ����
     */
    public void stopRun()
    {
        this.keepRun=false;
    }
/**
 * ����߳��Ƿ�������
 * @return
 */
    public boolean isRunning()
    {
        return this.keepRun;
    }

        /**
     * ��ͬ�������Ҫ���͵Ķ���
     * @param m ���Ų���
     * @return �Ƿ����ɹ�
     */
    private synchronized boolean addMessage( MessageInfo m)
    {
        return this.message.add(m);
    }
     /**
     * ��ͬ����ɾ���ѷ��͵Ķ���
     * @param m �ѷ��͵Ķ��ŵ�����
     * @return �Ƿ����ɹ�
     */
    private synchronized boolean removeMessage( MessageInfo m)
    {
       return  this.message.remove(m);

    }

    public void run()
    {
        
    }

private int getUpdateMessageFromDB()
{
    /**δ���*/
   return 0;
}
 /**
 * ��Ϣ�����̼߳����
 * @author Administrator
 */

}

