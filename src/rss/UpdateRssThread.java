package rss;

public class UpdateRssThread extends Thread{
	/** �������У����ô˱��������߳��Ƿ�һֱ���У�����ֹ�߳� */
	private volatile boolean keepRun = true;
	public void stopRun(){
		keepRun=false;
	}
	public void run(){
		
	}

}
