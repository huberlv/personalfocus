package email;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kingway.dao.impl.DatabaseDaoImpl;

import message.MessageInfo;
import message.SendPara;


/**
 *邮件发送线程
 * @author Administrator
 */
public class EmailSenderThread extends Thread {

    /** 是否保持运行，用于终止终程*/
    private volatile boolean keepRun = true;
    /**存储了信息内容的数组*/
    private ArrayList<MessageInfo> message = null;
    /**信息控制线程，用于向此线程反馈已发送的信息*/
    private int maxNum;
    private int sleepTime;
    private String senderXml;
    private int failTimes = 0;
    private EmailSender ems;
    private String esendfile;
    private ArrayList<EmailSender> senderList;
    //邮件发送延迟时间
    private int waitTime;
    private DatabaseDaoImpl db = new DatabaseDaoImpl();


/**
 * 构造函数
 * @param dbp
 * @param sleepTime
 * @param esendfile
 * @param maxNum
 * @param waitTime
 * @throws java.lang.Exception
 */
    public EmailSenderThread(int sleepTime, String esendfile,int maxNum,int waitTime) throws Exception {
    	this.maxNum= maxNum;
        this.sleepTime = sleepTime * 1000;
        message = new ArrayList<MessageInfo>(20);
        this.esendfile = esendfile;
        this.waitTime=waitTime;
        this.senderList=new ReadEmailSender(esendfile).getSender();

    }

    @Override
    public void run() {
        for (; this.keepRun;) {
            //数据库连接已关闭？
            try{
//            if (this.dbase.isClosed() == true) {
//                try {
//                    this.dbase = new DataBase(this.dbp);
//                } catch (Exception ex3) {
//                    Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex3);
//                    try {
//                        //超过10次此线程退出
//                        if (this.failTimes++ < 10) {
//                            EmailSenderThread.sleep(this.sleepTime);
//                        } else {
//                            this.keepRun = false;
//                            return;
//                        }
//                        continue;
//                    } catch (InterruptedException ex4) {
//                        Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex4);
//                    }
//                }
//            }
            //数据库连接有效
            this.failTimes = 0;
            this.sendMail();
            try {
                EmailSenderThread.sleep(this.sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
                Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 停止发送
     */
    public void stopRun() {
        this.keepRun = false;
    }

    /**
     * 检查线程是否在运行
     * @return 线程是否运行
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

    private void sendMail() {
        try {
            ArrayList<SendPara> sendpara = db.getMail(maxNum);

            ArrayList<String> currentc = new ArrayList<String>(maxNum);
            String currentRe=null;
            ArrayList<Email> emails = new ArrayList<Email>(maxNum);
            SendPara currentSp=null;
             String nextRe=null;
            ArrayList<OtherStruct> other = new ArrayList<OtherStruct>();
            other.add(new OtherStruct("      其它信息", "link"));   //未完成
            SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(sendpara.size()>0)
            {
                currentSp=sendpara.get(0);
                currentRe= currentSp.receiver;
               
                currentc.add(currentSp.content);
                }
            for(int i=1;i<sendpara.size();i++)
            {
                currentSp=sendpara.get(i);
                nextRe=currentSp.receiver;
                //接收者相同
                if(nextRe.equals(currentRe)==true)
                {
                   currentc.add(currentSp.content);
                   }else{
                    emails.add(new Email(currentRe,currentc , other));
                    currentRe=nextRe;
                    //重新建立当前内容
                    currentc = new ArrayList<String>(maxNum);
                }
            }
            //还有未加入的邮件
            if(currentc.size()>0)
            {
                emails.add(new Email(currentRe,currentc , other));
            }
           
            EmailBox ebox = new EmailBox(emails);
           
            new EmailService(ebox, this.senderList.get(0), this.waitTime).sendEmailWithHtml();
            //未完成
        } catch (Exception ex) {
            Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 得到要发送的邮件信息
     * @param num
     * @return
     */
}
