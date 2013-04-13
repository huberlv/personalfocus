<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
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
							访问受限
						</div>
						<div>
							用户空间仅公主人可访问，请联系主人修改空间权限！
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
