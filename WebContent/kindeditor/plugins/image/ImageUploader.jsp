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
		 * kindeditor�ϴ�����ͼƬ������
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

			/* ͼƬҪ�� */
			final long fileMaxSize = 1024 * 1024; //�ļ���С�����ֵ,��MΪ��λ
			String[] types = new String[] { "gif", "jpg", "jpeg", "png", "bmp" }; //�����ļ��ϴ������� 

			/* ���Ŀ¼����дȨ��   */
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				outResponseContent(response, getErrorMsg("�ϴ�Ŀ¼�����ڡ�"));
				return;
			}
			if (!uploadDir.canWrite()) {
				outResponseContent(response, getErrorMsg("�ϴ�Ŀ¼û��дȨ�ޡ�"));
				return;
			}

			id = wrapper.getParameter("id");
			imgFile = wrapper.getFiles("imgFile")[0];
			imgTitle = wrapper.getParameter("imgTitle");
			imgWidth = wrapper.getParameter("imgWidth");
			imgHeight = wrapper.getParameter("imgHeight");
			imgBorder = wrapper.getParameter("imgBorder");
			align = wrapper.getParameter("align");

			/* �����ļ� */
			long imgSize = imgFile.length();
			if (imgSize == 0) {
				outResponseContent(response, getErrorMsg("�����ϴ��������ļ���"));
				return;
			}
			if (imgSize > fileMaxSize) {
				outResponseContent(response, getErrorMsg("�ϴ��ļ���С�������ơ�"));
				return;
			}
			String originalFileName = wrapper.getFileNames("imgFile")[0]; // ����ϴ����ļ���,��king.jpg	
			String fileType = originalFileName.substring(
					originalFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(types).contains(fileType)) {
				outResponseContent(response, getErrorMsg("�ϴ��ļ���չ���ǲ��������չ����"));
				return;
			}
			String newName = renameFile(fileType);
			copyFile(imgFile, savePath + newName); // �����ϴ�����ʱ�ļ���ָ������
			imgUrl = imgUrl + newName; //ͼƬ�ļ���url
			String responseContent = "<script type=\"text/javaScript\">"
					+ "parent.KE.plugin[\"image\"].insert(\"" + id + "\",\""
					+ imgUrl + "\",\"" + imgTitle + "\",\"" + imgWidth
					+ "\",\"" + imgHeight + "\",\"" + imgBorder + "\",\""
					+ align + "\");" + "</script>";
			outResponseContent(response, responseContent);
		}

		/**
		 * �������ϴ��ļ�
		 */
		private String renameFile(String fileType) {
			final ReentrantLock lock = new ReentrantLock();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newName = null;
			lock.lock(); // ����Ϊ��ֹ�ļ����ظ�
			try {
				newName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileType;
			} finally {
				lock.unlock(); // �ͷ���
			}
			return newName;
		}

		/**
		 * �����ļ�
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
		 * Ajax��������������������Ӧ����
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
		 * ���ش���Json�ַ���
		 */
		private String getErrorMsg(String errorMsg){
			StringBuffer msg = new StringBuffer("{\"error\":1,\"message\":\"");
			msg.append(errorMsg).append("\"}");
			return msg.toString();
		}
	} //end of class
%>