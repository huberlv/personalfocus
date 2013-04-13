package update;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.kingway.model.CheckUpdateView;
import com.kingway.model.ContentBuffer;
import com.kingway.model.ContentReceiver;
import com.kingway.model.ModuleInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleInfo;
import com.kingway.model.struct.CheckUpdateStruct;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;

/**
 *对比数据库中的更新
 * 
 * @author Administrator
 */
public class CheckUpdateThread extends Thread {

	/** 是否保持运行，用于终止终程 */
	private volatile boolean keepRun = true;
	/** 存储了信息内容的数组 */
	private int maxNum;
	private int sleepTime;
	private HashMap<String, Document> bufferIds;
	private HashMap<String, String> moduleIds;
	private HashMap<String, CheckUpdateStruct> userModuleIds;
	private List<UserModuleContents> userModuleContents;

	/**
	 * 构造函数
	 * 
	 * @param dbp
	 * @param sleepTime
	 * @param esendfile
	 * @param maxNum
	 * @param waitTime
	 * @throws java.lang.Exception
	 */
	public CheckUpdateThread(int sleepTime, int maxNum) {
		this.maxNum = maxNum;
		this.sleepTime = sleepTime * 1000;
	}

	@Override
	public void run() {
		for (; this.keepRun;) {
			try{
			List<CheckUpdateView> checkUpdateView = (List<CheckUpdateView>) HibernateUtil
					.list("from CheckUpdateView");
			if (checkUpdateView.size() == 0) {
				try{
					   Thread.sleep(sleepTime);
					}catch(Exception e){
						e.printStackTrace();
					}
					continue;
			}
			// 去掉多余的，调节性能
			while (maxNum < checkUpdateView.size()) {
				checkUpdateView.remove(maxNum - 1);
			}
			StringBuilder temp = new StringBuilder(checkUpdateView.get(0)
					.getId().getBufferId()
					+ "");
			long tempLong = -1;
			// 拼成sql的where语句
			for (int i = 1; i < checkUpdateView.size(); i++) {
				if (tempLong != checkUpdateView.get(i).getId().getBufferId()) {
					tempLong = checkUpdateView.get(i).getId().getBufferId();
					temp.append(" or bufferId=");
					temp.append(tempLong);
				}
			}
			List<ContentBuffer> contentBuffers = (List<ContentBuffer>) HibernateUtil
					.list("from ContentBuffer where bufferId="
							+ temp.toString());
			// 缓冲内容哈希表
			bufferIds = new HashMap<String, Document>();
			for (int i = 0; i < contentBuffers.size(); i++) {
				bufferIds.put("bufferId" + contentBuffers.get(i).getBufferId(),
						Jsoup.parse(contentBuffers.get(i).getContent()));
			}
			// 模块最新内容哈希表
			moduleIds = new HashMap<String, String>();
			for (int i = 0; i < checkUpdateView.size(); i++) {
				if (moduleIds.containsKey("moduleId" // 如果模块内容哈希表中还没有相应值
						+ checkUpdateView.get(i).getId().getModuleId()) == false) {
					moduleIds.put("moduleId"
							+ checkUpdateView.get(i).getId().getModuleId(), "");
				}
			}

			// 拼成sql的where语句
			Iterator iterator = moduleIds.keySet().iterator();
			temp = new StringBuilder(iterator.next().toString().replaceAll(
					"moduleId", ""));
			while (iterator.hasNext()) {
				temp.append(" or moduleId=");
				temp.append(iterator.next().toString().replaceAll("moduleId",
						""));
			}
			List<ModuleInfo> moduleInfos = (List<ModuleInfo>) HibernateUtil
					.list("from ModuleInfo where moduleId=" + temp.toString());
			for (int i = 0; i < moduleInfos.size(); i++) {
				moduleIds.put("moduleId" + moduleInfos.get(i).getModuleId(),
						moduleInfos.get(i).getUpdateContent());
			}
			// 
			userModuleIds = new HashMap<String, CheckUpdateStruct>();
			// 拼凑userModuleid
			for (int i = 0; i < checkUpdateView.size(); i++) {
				if (userModuleIds.containsKey("userModuleId" // 如果模块内容哈希表中还没有相应值
						+ checkUpdateView.get(i).getId().getUserModuleId()) == false) {
					userModuleIds.put("userModuleId"
							+ checkUpdateView.get(i).getId().getUserModuleId(),
							new CheckUpdateStruct(checkUpdateView.get(i)
									.getId().getUserModuleId()));
				}

			}

			iterator = userModuleIds.keySet().iterator();
			temp = new StringBuilder(iterator.next().toString().replaceAll(
					"userModuleId", ""));
			while (iterator.hasNext()) {
				temp.append(" or userModuleId=");
				temp.append(iterator.next().toString().replaceAll(
						"userModuleId", ""));
			}
			List<UserModuleInfo> userModuleInfos = (List<UserModuleInfo>) HibernateUtil
					.list("from UserModuleInfo where userModuleId="
							+ temp.toString());
			for (int i = 0; i < userModuleInfos.size(); i++) {
				userModuleIds.get(
						"userModuleId"
								+ userModuleInfos.get(i).getUserModuleId())
						.setUpCheckPaths(
								userModuleInfos.get(i).getUnCheckPaths());

			}

			userModuleContents = (List<UserModuleContents>) HibernateUtil
					.list("from UserModuleContents where userModuleId="
							+ temp.toString() );
			for (int i = 0; i < userModuleContents.size(); i++) {
				if(userModuleContents.get(i).getContentType()!=0){
					userModuleIds.get(
							"userModuleId"
									+ userModuleContents.get(i)
											.getUserModuleId())
							.setUserModuleContent(
									userModuleContents.get(i)
											.getUserModuleContent());
				}
				
			}
			CheckUpdateStruct current = null;
			for (int i = 0; i < checkUpdateView.size(); i++) {
				current = userModuleIds.get("userModuleId"
						+ checkUpdateView.get(i).getId().getUserModuleId());
				if (current.getDocument() == null) {
					current.setDocument(Jsoup.parse(moduleIds.get("moduleId"
							+ checkUpdateView.get(i).getId().getModuleId())));
					
				}
				if (current.getUserModuleContent() != null) {
					LhpUtil.mergeUpdate(current.getDocument(), Jsoup
							.parse(current.getUserModuleContent()));
					current.setUserModuleContent(null);
				}
				LhpUtil
						.mergeUpdate(current.getDocument(), this.bufferIds
								.get("bufferId"
										+ checkUpdateView.get(i).getId()
												.getBufferId()));
			}
	
			UserModuleContents userModuleContent = null;
	
			CheckUpdateStruct document = null;
			for (int i = 0; i < userModuleContents.size(); i++) {
				userModuleContent = userModuleContents.get(i);
				document = userModuleIds.get("userModuleId"
						+ userModuleContent.getUserModuleId());
				document.trimUnCheckPaths();
				System.out.println("LhpUtil.isUpdate(document.getDocument()):=="+LhpUtil.isUpdate(document.getDocument()));
					if (LhpUtil.isUpdate(document.getDocument()) == true) {
						userModuleContent.setContentType(1);
						userModuleContent.setIgnoreByUser(0);
						userModuleContent.setAlreadySendMail(0);
						userModuleContent.setAlreadySendMessage(0);
						userModuleContent.setUserModuleContent(LhpUtil.getBodyString(document.getDocument()));
						userModuleContent.setUpdateTime(new Date());		
					}
						
			}
			HibernateUtil.batchUpdate(userModuleContents);
			// 删除sendIds
			// temp=new StringBuilder(checkUpdateView);
			temp = new StringBuilder(checkUpdateView.get(0).getId().getSendId()+"");
			for (int i = 1; i < checkUpdateView.size(); i++) {
				temp.append(" or sendId=");
				temp.append(checkUpdateView.get(i).getId().getSendId());
			}
            List<ContentReceiver> contentReceivers=(List<ContentReceiver>) HibernateUtil
			.list("from ContentReceiver where sendId="
					+ temp.toString());
            for(ContentReceiver c:contentReceivers){
            	c.setSendType(10);
          	
            }
            HibernateUtil.batchUpdate(contentReceivers);
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
			   Thread.sleep(sleepTime);
			}catch(Exception e){
				e.printStackTrace();
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
	 * 
	 * @return 线程是否运行
	 */
	public boolean isRunning() {
		return this.keepRun;
	}

	/**
	 * 改变睡眠时间，以调节性能
	 * 
	 * @param sleepTime
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime * 1000;
	}
}
