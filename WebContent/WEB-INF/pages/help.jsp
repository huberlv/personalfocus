<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			String url=null;
	if(request.getParameter("h")!=null){
		url="/help/"+request.getParameter("h");
	}else{
		url="/help/summary.html";
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>互联网关注订阅平台--帮助</title>
		<LINK href="/personalfocus/css/indexcss/newstyle.css" type=text/css
			rel=stylesheet>
		<LINK href="/personalfocus/css/userindexcss/help.css" type=text/css
			rel=stylesheet>
        <link href="/personalfocus/css/userindexcss/helpList.css" rel="stylesheet"
			type="text/css" />
			<script>
			   function changeView(id){
				   var tag=document.getElementById(id);
				   var tag_image=document.getElementById("image-"+id);
				   if(tag.style.display=='none'){
					   tag.style.display='block';
					   tag_image.src="/personalfocus/help/images/-.jpg";
					   
				   }else{
					   tag.style.display='none';
					   tag_image.src="/personalfocus/help/images/+.jpg";
				   }				   
			   }
			</script>
	</head>
	<body>
		<DIV class="g-doc">
			<DIV class=g-hd>
				<UL class=m-nav>
					<LI class=current>
						<A href="/personalfocus/index/showindex">首页</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showDemonstrate?i=a&n=4">特色推荐</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showDemonstrate?i=a&n=4">功能演示</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showHelp?h=browser.html#download">下载与更新</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showHelp">帮助</A>
					</LI>
					<LI>
						<A href="mailto:357370736@qq.com">反馈</A>
					</LI>
				</UL>
			</DIV>

<div id='pcontent'>
			<DIV class="control">
				<div class="helptitle">
					<s:if test="#session.userid==null">
					</s:if>
					<s:else>
						<span style='font-size: 18px'> <a
							href='/personalfocus/user/myfocus'>返回空间</a> </span>
					</s:else>
					
					<div id="helptitleid">
						<span> <b class="helpTitle"><a href='/personalfocus/index/showHelp?h=summary.html#enter'>概述</a>
						</b> </span>
						
						<span> <image src="/personalfocus/help/images/+.jpg" id="image-addfocus" onClick="changeView('addfocus')"></image><b class="helpTitle" onClick="changeView('addfocus')">1.订阅关注</b>
							<ol id="addfocus" style="display:none">
								<li>
									<a href='/personalfocus/index/showHelp?h=addfocus.html#enter'>进入订阅关注</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=addfocus.html#auto'>智能订阅</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=addfocus.html#man'>手动订阅</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=addfocus.html#focusType'>关注方式</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=addfocus.html#edit'>编辑栏目特征</a>
								</li>
							</ol> </span>

						
						<span> <image src="/personalfocus/help/images/+.jpg" id="image-myfocus" onClick="changeView('myfocus')"></image><b class="helpTitle" onClick="changeView('myfocus')">2.我的关注</b>
							<ol id="myfocus" style="display:none">
								<li>
									<a href='/personalfocus/index/showHelp?h=myfocus.html#explain'>页面说明</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=myfocus.html#edit'>装扮空间</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=myfocus.html#change'>更改外观</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=myfocus.html#pho'>幻灯片模式</a>
								</li>

								<li>
									<a href='/personalfocus/index/showHelp?h=myfocus.html#trim'>剔除模式</a>
								</li>
							</ol> </span>

						<span> <image src="/personalfocus/help/images/+.jpg" id="image-managerfocus" onClick="changeView('managerfocus')"></image><b class="helpTitle" onClick="changeView('managerfocus')">3.订阅关注管理</b>
							<ol id="managerfocus" style="display:none">
								<li>
									<a href='/personalfocus/index/showHelp?h=managerfocus.html'>显示管理</a>
								</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=managerfocus.html'>模块关注</a>
								</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=managerfocus.html'>默认提醒</a>
								</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=managerfocus.html'>分组管理</a>
								</li>
							</ol> </span>

						<span> <image src="/personalfocus/help/images/+.jpg" id="image-manager" onClick="changeView('manager')"></image><b class="helpTitle" onClick="changeView('manager')">4.空间与个人信息管理</b>
							<ol id="manager" style="display:none">
								<li>
									<a href='/personalfocus/index/showHelp?h=manager.html'>修改个人信息</a>
								</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=manager.html'>空间管理</a>
								</li>

							</ol> </span>
						<span> <image src="/personalfocus/help/images/+.jpg" id="image-subscriber" onClick="changeView('subscriber')"></image><b class="helpTitle" onClick="changeView('subscriber')">5.浏览订阅好友空间</b>
							<ol id="subscriber" style="display:none">
								<li>
									<a href='/personalfocus/index/showHelp?h=subscriber.html'>浏览好友空间</a>
								</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=subscriber.html'>订阅好友空间</a>
								</li>


							</ol> </span>
						<span> <image src="/personalfocus/help/images/+.jpg" id="image-mobile" onClick="changeView('mobile')"></image><b class="helpTitle" onClick="changeView('mobile')">6.手机微网</b>
							<ol id="mobile" style="display:none">
								<li>
									<a href='/personalfocus/index/showHelp?h=mobile.html'>手机访问空间</a>
							</ol> </span>
							
							<span> <image src="/personalfocus/help/images/+.jpg" id="image-browser" onClick="changeView('browser')"></image><b class="helpTitle" onClick="changeView('browser')">7.使用平台浏览器</b>
							<ol id="browser" style="display:none">
							<li>
									<a href='/personalfocus/index/showHelp?h=browser.html#download'>下载</a>
							</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=browser.html#summary'>概述</a>
										</li>
										<li>
									<a href='/personalfocus/index/showHelp?h=browser.html#explain'>使用说明</a>
										</li>
							</ol> </span>
							
							<span> <image src="/personalfocus/help/images/+.jpg" id="image-mobileClient" onClick="changeView('mobileClient')"></image><b class="helpTitle" onClick="changeView('mobileClient')">8.使用手机客户端</b>
							<ol id="mobileClient" style="display:none">
							<li>
									<a href='/personalfocus/index/showHelp?h=mobileClient.html#download'>下载</a>
							</li>
								<li>
									<a href='/personalfocus/index/showHelp?h=mobileClient.html#summary'>概述</a>
										</li>
										<li>
									<a href='/personalfocus/index/showHelp?h=mobileClient.html#explain'>使用说明</a>
										</li>
							</ol> </span>

					</div>
				</div>
			</DIV>
			<div class="iframeClass" id="helpPage">					
				<jsp:include page="<%=url%>"></jsp:include>
		   </div></div>
		</DIV>
	

	</body>
</html>