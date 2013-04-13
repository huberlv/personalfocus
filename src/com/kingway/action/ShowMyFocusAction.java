package com.kingway.action;

import java.util.ArrayList;
import java.util.List;
import com.kingway.dao.impl.ShowMyFocusDaoImpl;
import com.kingway.model.ModuleInfoShowView;
import com.kingway.model.UserModuleSubgroupInfo;
import com.kingway.model.struct.UserModuleGroup;

import com.opensymphony.xwork2.ActionContext;

public class ShowMyFocusAction {

	private List<UserModuleGroup> focusList;
    private String onlyShowUpdate;
    private String changeView;
    private static String onlyShowUpdateStr="<a href='/personalfocus/user/myfocus?onlyShowUpdate=1'>显示更新关注</a>";
    private static String showAllStr="<a href='/personalfocus/user/myfocus?onlyShowUpdate=0'>显示全部关注</a>";
	
    public String getOnlyShowUpdate() {
		return onlyShowUpdate;
	}

	public void setOnlyShowUpdate(String onlyShowUpdate) {
		this.onlyShowUpdate = onlyShowUpdate;
	}

	private List<UserModuleSubgroupInfo> userModuleSubgroupInfo;
	private String userId;
	private int timeout= 1000;//有更新后8分钟后再检查
	@SuppressWarnings("unchecked")
	public List getFocusList() {
		return focusList;
	}

	@SuppressWarnings("unchecked")
	public void setFocusList(List focusList) {
		this.focusList = focusList;
	}

	public String myfocus(){
	
		String idstr= (String) ActionContext.getContext().getSession().get("userid");
		Long id=Long.parseLong(idstr);
	    this.userId=idstr;
	   //取得分组 
		ShowMyFocusDaoImpl  showMyFocusDaoImpl=new ShowMyFocusDaoImpl();
		List<ModuleInfoShowView> vlist=null;
		if(onlyShowUpdate==null){
		    vlist = showMyFocusDaoImpl.showmyfocus(id,true);	
		    this.changeView=showAllStr;
		}else{
			if(onlyShowUpdate.equals("0")){
				 vlist = showMyFocusDaoImpl.showmyfocus(id,false);	
				 this.changeView=onlyShowUpdateStr;
			}else{
				 vlist = showMyFocusDaoImpl.showmyfocus(id,true);	
				 this.changeView=showAllStr;
			}
		}
		userModuleSubgroupInfo =showMyFocusDaoImpl.showUsrGroup(id);
		 focusList=new ArrayList<UserModuleGroup>();
		for(int h=0;h<userModuleSubgroupInfo.size();h++){
			 focusList.add(new UserModuleGroup(userModuleSubgroupInfo.get(h).getGroupName(),userModuleSubgroupInfo.get(h).getSubgroupId()));
		}
		for(int i=0;i<focusList.size();i++){
			for(int j=0;j<vlist.size();j++)
			{
				if(vlist.get(j).getId().getSubgroupId()==focusList.get(i).getGroupId()){					
					focusList.get(i).getViewList().add(vlist.get(j));
				}
			}
		}
		return "success";
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setChangeView(String changeView) {
		this.changeView = changeView;
	}

	public String getChangeView() {
		return changeView;
	}
}
