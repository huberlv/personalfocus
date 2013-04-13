<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/contentstyle.css">
			<LINK rel=stylesheet type=text/css
				href="/personalfocus/css/userindexcss/nav.css">
	</HEAD>
	<BODY>
		<div id="background">
		</div>



		<DIV class=g-doc>
			<DIV class=g-hd>
				<H1 class=m-logo>
					<A href=""></A>
				</H1>
			</DIV>

			<%--内容--%>
			<div class="content">
				<div class="mhd">
					<div class="tit"></div>
				</div>
				<div class="mcont">
					<div class="main-cont">
						<div class="main-cont-tit">
							<div class="arr"></div>
							访问受限，空间需要回答问题才能访问！请回答以下问题或联系主人！
						</div>
						<div style="text-align: left;text-indent: 40%">
		<s:form action="showUserSpace">
		<input type="hidden" name="masterUserId" value="${masterUserId}">
		
             <div>
						问题：${question}
						</div>
					<div>
						答案:<s:textfield id="answer" name="answer"></s:textfield>${wrongMessage}
						</div>
						<s:submit label="提交" value="提交"></s:submit>
		</s:form>
		</div>
					</div>
				</div>
				<div class="mft"></div>
			</div>
		</DIV>

		<DIV class=g-ft>
			<DIV class=inner>
				<DIV class=copy-wrap>
					<DIV class=copyright>
						<A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A>
						
						<DIV class=copy>
							<!--版权所有&nbsp;&nbsp;  &copy;2009-2010-->
						</DIV>
					</DIV>
				</DIV>
			</DIV>
		</DIV>


	</BODY>
</HTML>
