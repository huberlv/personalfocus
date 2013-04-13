package dwr;


import java.util.ArrayList;

import update.UpdateContent;
import com.kingway.dao.impl.DatabaseDaoImpl;
import document.MyHtmlParser;

public class MonitorModuleByJs {

	/**
	 * ��js���ã��ύ���µ�����,���سɹ�
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
				System.out.print("ʧ����Ϣ��" + moduleId[i]);
				//dbase.IncreaseModuleFailTimes(moduleId[i]); // ������Ч
				db.increaseModuleFailTimes(moduleId[i]);// ������Ч
				error = true;
				continue;
			} // ֻҪ��һ����Ŀ������Ч������Ϊipû�б���
			error = false;
			if (newtext.length() > 1) {				
			//	cupdateContent = dbase.getModuleUpdateContent(moduleId[i]);
				cupdateContent = db.getModuleUpdateContent(Long.parseLong(moduleId[i]));
				//System.out.println("�ϴ�����"+cupdateContent+moduleId[i]);
				
				if (cupdateContent != null) {
				//	System.out.println("��������\n" +newtext + "ģ��id\n" + moduleId[i]
				//	                                          					+ "ʧ����\n" + failpcnt[i]);
				//	System.out.println("�ϴε�����"+ cupdateContent);
					updatecontent = page.getUpdate(newtext, cupdateContent,MyHtmlParser.REMOVE_OLD);
				//	System.out.println(updatecontent);
					if (updatecontent != null) { //
					//	System.out.println("   //////////������ ///////// "+updatecontent.content);
						// �ȸ�����Ŀ�������ݱ�
						//dbase.updateModuleUpdateConetnt(moduleId[i], newtext); 
						db.updateModuleUpdateConetnt(moduleId[i], newtext,updatecontent.get(updatecontent.size()-1).content);
						// �����µ�ģ�������Ϣ
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
