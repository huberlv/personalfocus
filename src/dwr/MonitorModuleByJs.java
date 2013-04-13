package dwr;


import java.util.ArrayList;

import update.UpdateContent;
import com.kingway.dao.impl.DatabaseDaoImpl;
import document.MyHtmlParser;

public class MonitorModuleByJs {

	/**
	 * 由js调用，提交最新的内容,返回成功
	 * 
	 * @param source
	 * @param moduleId
	 * @param failpcnt
	 * @throws Exception
	 */
	public boolean submitNewContent(String source[], String[] moduleId,
			double[] failpcnt) throws Exception {
	/*	for(int i=0;i<source.length;i++){
			System.out.print(source[i]);
		}
		*/
		DatabaseDaoImpl db = new DatabaseDaoImpl();
		boolean error;
		String newtext;
		MyHtmlParser page = new MyHtmlParser();
		String cupdateContent = "";
		ArrayList<UpdateContent> updatecontent=null;
	//	System.out.println("n//////////////////////////////////////");
		for (int i = 0; i < source.length; i++) {
			newtext = source[i].trim();
			//System.out.println("newtext"+newtext);
			if (newtext.length() < 1) {
				System.out.print("失败信息：" + moduleId[i]);
				//dbase.IncreaseModuleFailTimes(moduleId[i]); // 特征无效
				db.increaseModuleFailTimes(moduleId[i]);// 特征无效
				error = true;
				continue;
			} // 只要有一个栏目特征有效，则认为ip没有被封
			error = false;
			if (newtext.length() > 1) {				
			//	cupdateContent = dbase.getModuleUpdateContent(moduleId[i]);
				cupdateContent = db.getModuleUpdateContent(Long.parseLong(moduleId[i]));
				//System.out.println("上次内容"+cupdateContent+moduleId[i]);
				
				if (cupdateContent != null) {
				//	System.out.println("最新内容\n" +newtext + "模块id\n" + moduleId[i]
				//	                                          					+ "失败率\n" + failpcnt[i]);
				//	System.out.println("上次的内容"+ cupdateContent);
					updatecontent = page.getUpdate(newtext, cupdateContent,MyHtmlParser.REMOVE_OLD);
				//	System.out.println(updatecontent);
					if (updatecontent != null) { //
					//	System.out.println("   //////////更新了 ///////// "+updatecontent.content);
						// 先更新栏目最新内容表
						//dbase.updateModuleUpdateConetnt(moduleId[i], newtext); 
						db.updateModuleUpdateConetnt(moduleId[i], newtext,updatecontent.get(updatecontent.size()-1).content);
						// 插入新的模块更新信息
						for(int h=0;h<updatecontent.size();h++){
						   db.insertNewContent(moduleId[i],updatecontent.get(h).content,updatecontent.get(h).monitorType);
						}
					}
					updatecontent = null;
				}
			}

		}
		return true;
	}

}
