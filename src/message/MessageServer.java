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
        /**用于存放和控制在运行的消息发送线程*/
    private ArrayList<ModulesLinkSender> messageSender;
       /**消息发送线程的数量*/
    private int initMessageSenderNum;
    /**网址*/
    private String urlstr;
    /**每次最多从数据库取回的更新信息数*/
    private int onccGetMNnum;
        /**存储了信息内容的数组*/
    private ArrayList< MessageInfo> message=null;
        /** 是否保持运行，用于终止终程*/
    private volatile boolean keepRun=true;
    private int sleepTime;
    
/**
 * 构造函数
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
     * 改变睡眠时间，以调节性能
     * @param sleepTime
     */
    public void setSleepTime(int sleepTime)
    {
        this.sleepTime=sleepTime*1000;
    }
        /**
     * 停止发送
     */
    public void stopRun()
    {
        this.keepRun=false;
    }
/**
 * 检查线程是否在运行
 * @return
 */
    public boolean isRunning()
    {
        return this.keepRun;
    }

        /**
     * 在同步块加入要发送的短信
     * @param m 短信参数
     * @return 是否加入成功
     */
    private synchronized boolean addMessage( MessageInfo m)
    {
        return this.message.add(m);
    }
     /**
     * 在同步块删除已发送的短信
     * @param m 已发送的短信的引用
     * @return 是否加入成功
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
    /**未完成*/
   return 0;
}
 /**
 * 消息发送线程监控器
 * @author Administrator
 */

}

