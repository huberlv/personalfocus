package com.kingway.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import update.UpdateContent;

import com.kingway.model.UserInfo;
import com.kingway.model.UserModuleContents;
import com.kingway.model.UserModuleUpdatepathsView;
import com.kingway.util.HibernateUtil;
import com.kingway.util.LhpUtil;
import com.kingway.util.MD5Util;
import com.opensymphony.xwork2.ActionContext;

import document.MyHtmlParser;

/**
 * 吕鸿佩
 * 
 * @author Administrator
 * 
 */
public class RecognizeUpdateAction {
	private String url;
	private String path;
	private String html;
	private String userId;
	private String password;

	/**
	 * 取得更新文本特征
	 * 
	 * @return
	 */
	public String recognizeUpdate() {
		url=LhpUtil.getSourceURL(url);	 
		ActionContext.getContext().getSession().put("empty", "");
		try {
			PrintWriter pw = null;
			HttpServletResponse response = ServletActionContext.getResponse(); // 取response对象
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			try {
				pw = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (userId == null || password == null) {
				System.out.println("用户名密码为空");
				pw.print("failLogin");
				pw.flush();
				pw.close();
				return "success";
			}
			// 未封装
			UserInfo user = (UserInfo) HibernateUtil.get(UserInfo.class, Long
					.parseLong(userId));
			if (user == null) {
				System.out.println("用户名不存在");
				pw.print("failLogin");
				pw.flush();
				pw.close();
				return "success";
			} else if (user.getPassword().equals(
					MD5Util.getMD5(password.getBytes()))) {
				List<UserModuleUpdatepathsView> userModuleUpdatepathsView = HibernateUtil
						.list("from UserModuleUpdatepathsView where userId="
								+ this.userId + " and webAddress='" + this.url
								+ "'");
				if (userModuleUpdatepathsView == null) {
					pw.print("userModuleId$" + "null;");
					pw.print("updatePaths$" + "null;");
					pw.flush();
					pw.close();
					System.out.println("不存在模块2");
					return "success";
				}
				MyHtmlParser parser = new MyHtmlParser();
				parser.parser(html, this.url);
				String path = null;
				ArrayList<UpdateContent> updatecontent = null;
				String userModuleContent = null;
				StringBuilder tempContentId = new StringBuilder("userModuleId$");
				StringBuilder tempUpdatePaths = new StringBuilder(
						"updatePaths$");
				String paths[];
				for (int i = 0; i < userModuleUpdatepathsView.size(); i++) {
					path = userModuleUpdatepathsView.get(i).getId()
							.getModulePath();
					userModuleContent = userModuleUpdatepathsView.get(i)
							.getId().getUserModuleContent();
					paths = path.split(",");
					boolean update=false;
					for (int j = 0; j < paths.length; j++) {
						// /
						update=false;
						updatecontent = parser.getUpdate(parser.getHtmlOfPath(
								paths[j], MyHtmlParser.TYPE_TD), userModuleContent,
								MyHtmlParser.REMOVE_OLD);

						if (updatecontent != null
								|| userModuleUpdatepathsView.get(i).getId()
										.getContentType() == 1) {
							update=true;
							String updatecontentstr = null;
							if (userModuleUpdatepathsView.get(i).getId()
									.getContentType() == 1) {
								if (updatecontent != null) {
									Document document = Jsoup
											.parseUnTransform(updatecontent.get(0).content);
									LhpUtil.mergeUpdate(document, Jsoup
											.parse(userModuleContent));
									LhpUtil.trimUnCheckPaths(document,userModuleUpdatepathsView.get(i).getId().getUnCheckPaths());
									updatecontentstr = LhpUtil
											.getBodyString(document);
								} else {
									updatecontentstr = userModuleUpdatepathsView
											.get(i).getId()
											.getUserModuleContent();
									updatecontentstr=LhpUtil.getTrimUncheckContent(updatecontentstr,userModuleUpdatepathsView.get(i).getId().getUnCheckPaths());
									
								}
								if(updatecontentstr!=null){
								tempUpdatePaths
										.append(LhpUtil
												.getUpPath(
														LhpUtil
																.getUpdatePaths(updatecontentstr),
																paths[j]));
								tempUpdatePaths.append(",");
								}
							} else {			
								updatecontentstr = updatecontent.get(0).content;
								updatecontentstr=LhpUtil.getTrimUncheckContent(updatecontentstr,userModuleUpdatepathsView.get(i).getId().getUnCheckPaths());
								if(updatecontentstr!=null){
								tempUpdatePaths
										.append(LhpUtil
												.getUpPath(
														LhpUtil
																.getUpdatePaths(updatecontentstr),
																paths[j]));
								tempUpdatePaths.append(",");
								}

							}
							if(updatecontentstr!=null){
							UserModuleContents uc = (UserModuleContents) HibernateUtil
									.get(UserModuleContents.class,
											userModuleUpdatepathsView.get(i)
													.getId().getContentId());
							uc.setContentType(1);
							uc.setAlreadySendMail(0);
							uc.setAlreadySendMessage(0);
							uc.setIgnoreByUser(0);
							uc.setUserModuleContent(updatecontentstr);
							uc.setUpdateTime(new Date());
							HibernateUtil.update(uc);
							}
						}
						// ///
					}
					if(update==true){
						tempContentId.append(userModuleUpdatepathsView
								.get(i).getId().getUserModuleId());// 输出id
						tempContentId.append(",");
					}
					
				}
				if (tempContentId.toString().endsWith(","))
					tempContentId.delete(tempContentId.length() - 1,
							tempContentId.length());
				if (tempContentId.length() == "userModuleId$".length()) {
					pw.print(tempContentId.toString());
					pw.print("null");
				} else {
					pw.print(tempContentId.toString());
				}
				pw.print(";");
				if (tempUpdatePaths.toString().endsWith(","))
					tempUpdatePaths.delete(tempUpdatePaths.length() - 1,
							tempUpdatePaths.length());
				if (tempUpdatePaths.length() == "updatePaths$".length()) {
					pw.print(tempUpdatePaths.toString());
					pw.print("null");
				} else {
					pw.print(tempUpdatePaths.toString());
				}
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtml() {
		return html;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

}
