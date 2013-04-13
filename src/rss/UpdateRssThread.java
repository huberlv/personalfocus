package rss;

public class UpdateRssThread extends Thread{
	/** 保持运行，可用此变量控制线程是否一直运行，或终止线程 */
	private volatile boolean keepRun = true;
	public void stopRun(){
		keepRun=false;
	}
	public void run(){
		
	}

}
