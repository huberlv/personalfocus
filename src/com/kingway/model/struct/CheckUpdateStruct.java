package com.kingway.model.struct;


import org.jsoup.nodes.Document;

import com.kingway.util.LhpUtil;
/**
 * ¬¿∫Ë≈Â
 * @author Administrator
 *
 */
public class CheckUpdateStruct {
    private long userModuleId;
    private String upCheckPaths;

    private String userModuleContent;
    private Document document;
 
    public CheckUpdateStruct(long userModuleId){
    	this.userModuleId=userModuleId;
    }
	public void setUserModuleId(long userModuleId) {
		this.userModuleId = userModuleId;
	}
	public long getUserModuleId() {
		return userModuleId;
	}
	public void setUpCheckPaths(String upCheckPaths) {
		this.upCheckPaths = upCheckPaths;
	}
	public String getUpCheckPaths() {
		return upCheckPaths;
	}

	public void setUserModuleContent(String userModuleContent) {
		this.userModuleContent = userModuleContent;
	}
	public String getUserModuleContent() {
		return userModuleContent;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public Document getDocument() {
		return document;
	}


	public void trimUnCheckPaths(){
		LhpUtil.trimUnCheckPaths(document, upCheckPaths);
	}

}
