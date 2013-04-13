package email;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kingway.dao.impl.DatabaseDaoImpl;

import message.MessageInfo;
import message.SendPara;


/**
 *�ʼ������߳�
 * @author Administrator
 */
public class EmailSenderThread extends Thread {

    /** �Ƿ񱣳����У�������ֹ�ճ�*/
    private volatile boolean keepRun = true;
    /**�洢����Ϣ���ݵ�����*/
    private ArrayList<MessageInfo> message = null;
    /**��Ϣ�����̣߳���������̷߳����ѷ��͵���Ϣ*/
    private int maxNum;
    private int sleepTime;
    private String senderXml;
    private int failTimes = 0;
    private EmailSender ems;
    private String esendfile;
    private ArrayList<EmailSender> senderList;
    //�ʼ������ӳ�ʱ��
    private int waitTime;
    private DatabaseDaoImpl db = new DatabaseDaoImpl();


/**
 * ���캯��
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
            //���ݿ������ѹرգ�
            try{
//            if (this.dbase.isClosed() == true) {
//                try {
//                    this.dbase = new DataBase(this.dbp);
//                } catch (Exception ex3) {
//                    Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex3);
//                    try {
//                        //����10�δ��߳��˳�
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
            //���ݿ�������Ч
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
     * ֹͣ����
     */
    public void stopRun() {
        this.keepRun = false;
    }

    /**
     * ����߳��Ƿ�������
     * @return �߳��Ƿ�����
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

    private void sendMail() {
        try {
            ArrayList<SendPara> sendpara = db.getMail(maxNum);

            ArrayList<String> currentc = new ArrayList<String>(maxNum);
            String currentRe=null;
            ArrayList<Email> emails = new ArrayList<Email>(maxNum);
            SendPara currentSp=null;
             String nextRe=null;
            ArrayList<OtherStruct> other = new ArrayList<OtherStruct>();
            other.add(new OtherStruct("      ������Ϣ", "link"));   //δ���
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
                //��������ͬ
                if(nextRe.equals(currentRe)==true)
                {
                   currentc.add(currentSp.content);
                   }else{
                    emails.add(new Email(currentRe,currentc , other));
                    currentRe=nextRe;
                    //���½�����ǰ����
                    currentc = new ArrayList<String>(maxNum);
                }
            }
            //����δ������ʼ�
            if(currentc.size()>0)
            {
                emails.add(new Email(currentRe,currentc , other));
            }
           
            EmailBox ebox = new EmailBox(emails);
           
            new EmailService(ebox, this.senderList.get(0), this.waitTime).sendEmailWithHtml();
            //δ���
        } catch (Exception ex) {
            Logger.getLogger(EmailSenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * �õ�Ҫ���͵��ʼ���Ϣ
     * @param num
     * @return
     */
}
