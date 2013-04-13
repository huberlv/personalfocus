<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="java.util.concurrent.locks.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper"%>

<%
	new ImageUploadAction().uploadImg(request, response);
%>

<%!
    /**
	 * author : Weipeng Zhong
	 * version: v1.1
	 * firstBuild: 2010-08-17
	 * lastBuild: 2010-10-10
	 */
	public class ImageUploadAction {

		private String id; 
		private File imgFile; 
		private String imgTitle; 
		private String imgWidth; 
		private String imgHeight; 
		private String imgBorder; 
		private String align;

		/**
		 * kindeditor上传本地图片处理方法
		 */
		public void uploadImg(HttpServletRequest request, HttpServletResponse response) {

			MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) request; 

			String contextRealPath = request.getSession().getServletContext().getRealPath("/"); 
			String contextPath = request.getContextPath(); 
			String relativePath = "/imageUpload/"; 
			String savePath = contextRealPath + relativePath; 
			String imgUrl = contextPath + relativePath; 
			/* output the upload path */
			System.out.println("imageUploadPath: " + savePath);

			/* 图片要求 */
			final long fileMaxSize = 1024 * 1024; //文件大小的最大值,以M为单位
			String[] types = new String[] { "gif", "jpg", "jpeg", "png", "bmp" }; //定义文件上传的类型 

			/* 检查目录及其写权限   */
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				outResponseContent(response, getErrorMsg("上传目录不存在。"));
				return;
			}
			if (!uploadDir.canWrite()) {
				outResponseContent(response, getErrorMsg("上传目录没有写权限。"));
				return;
			}

			id = wrapper.getParameter("id");
			imgFile = wrapper.getFiles("imgFile")[0];
			imgTitle = wrapper.getParameter("imgTitle");
			imgWidth = wrapper.getParameter("imgWidth");
			imgHeight = wrapper.getParameter("imgHeight");
			imgBorder = wrapper.getParameter("imgBorder");
			align = wrapper.getParameter("align");

			/* 处理文件 */
			long imgSize = imgFile.length();
			if (imgSize == 0) {
				outResponseContent(response, getErrorMsg("请勿上传空内容文件。"));
				return;
			}
			if (imgSize > fileMaxSize) {
				outResponseContent(response, getErrorMsg("上传文件大小超过限制。"));
				return;
			}
			String originalFileName = wrapper.getFileNames("imgFile")[0]; // 获得上传的文件名,如king.jpg	
			String fileType = originalFileName.substring(
					originalFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(types).contains(fileType)) {
				outResponseContent(response, getErrorMsg("上传文件扩展名是不允许的扩展名。"));
				return;
			}
			String newName = renameFile(fileType);
			copyFile(imgFile, savePath + newName); // 保存上传的临时文件到指定特征
			imgUrl = imgUrl + newName; //图片文件的url
			String responseContent = "<script type=\"text/javaScript\">"
					+ "parent.KE.plugin[\"image\"].insert(\"" + id + "\",\""
					+ imgUrl + "\",\"" + imgTitle + "\",\"" + imgWidth
					+ "\",\"" + imgHeight + "\",\"" + imgBorder + "\",\""
					+ align + "\");" + "</script>";
			outResponseContent(response, responseContent);
		}

		/**
		 * 重命名上传文件
		 */
		private String renameFile(String fileType) {
			final ReentrantLock lock = new ReentrantLock();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newName = null;
			lock.lock(); // 加锁为防止文件名重复
			try {
				newName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileType;
			} finally {
				lock.unlock(); // 释放锁
			}
			return newName;
		}

		/**
		 * 拷贝文件
		 */
		private void copyFile(File upload, String newPath) {
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(upload);
				fos = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Ajax方法，用于输出请求的响应内容
		 */
		public void outResponseContent(HttpServletResponse response, String responseContent) {
			PrintWriter out = null;
			try {
				response.setContentType("text/html; charset=utf-8");
				out = response.getWriter();
				out.write(responseContent);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 返回错误Json字符串
		 */
		private String getErrorMsg(String errorMsg){
			StringBuffer msg = new StringBuffer("{\"error\":1,\"message\":\"");
			msg.append(errorMsg).append("\"}");
			return msg.toString();
		}
	} //end of class
%>