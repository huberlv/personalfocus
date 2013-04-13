package dwr;


import com.kingway.model.UserModuleContents;
import com.kingway.util.HibernateUtil;
import com.kingway.util.OnlineFocus;

public class UpdateContentReceiver {
	public String deleteARecord(String[] sendIds) {
     
		UserModuleContents userModuleContents;
		Object temp = null;
		for (int i = 0; i < sendIds.length; i++) {
			
			temp = HibernateUtil.get(UserModuleContents.class, Long
					.parseLong(sendIds[i]));
			if (temp != null) {
				userModuleContents = (UserModuleContents) temp;
				if (userModuleContents.getContentType() == 0) {
					return "OK";
				} else {
					userModuleContents.setContentType(0);
					userModuleContents.setIgnoreByUser(1);
					userModuleContents.setAlreadySendMail(1);
					userModuleContents.setAlreadySendMessage(1);
					userModuleContents.setUserModuleContent(userModuleContents
							.getUserModuleContent().replaceAll(OnlineFocus.UPDATESTYLE,
									""));
					HibernateUtil.update(userModuleContents);
				}
			}

		}
		return "OK";
	}

	public String deleteRecords(String sendIds[][]) {

		for (int i = 0; i < sendIds.length; i++) {
			 deleteARecord(sendIds[i]) ;
		}
		return "OK";
	}
}
