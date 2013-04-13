package com.kingway.action;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.kingway.dao.impl.InviteFromXslDaoImpl;
import com.kingway.model.struct.RegistList;

public class FileUpload {
	private File[] image;
	private String[] imageFileName;
	private List <RegistList> XSLlist;
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<RegistList> getXSLlist() {
		return XSLlist;
	}

	public void setXSLlist(List<RegistList> xSLlist) {
		XSLlist = xSLlist;
	}

	public File[] getImage() {
		return image;
	}

	public void setImage(File[] image) {
		this.image = image;
	}

	public String[] getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String[] imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String addUI(){
		return "success";
	}

	public String execute() throws Exception{
		
		String realpath = ServletActionContext.getServletContext().getRealPath("/uploadfiles");
		System.out.println(realpath);
		if(image!=null){
			File savedir = new File(realpath);
			if(!savedir.exists()) savedir.mkdirs();
			for(int i = 0 ; i<image.length ; i++){				
				File savefile = new File(savedir, imageFileName[i]);
				FileUtils.copyFile(image[i], savefile);
				
			//	InviteFromXslDaoImpl invite = new  InviteFromXslDaoImpl();
				//this.XSLlist=invite.getDataFromXsl(realpath+"/"+imageFileName[i]);//注册--生成并保存邀请码-发邮件
				
			}
			//this.message="已上传"+this.XSLlist.size()+"条记录"+"，并注册成功";
			this.message="已上传"+1+"条记录"+"，并注册成功";
		
		}
		return "success";
	}
}
