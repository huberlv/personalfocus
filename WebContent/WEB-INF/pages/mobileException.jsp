<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<meta http-equiv="Content-Type" content="text ml; charset=utf-8" />
	</HEAD>
	<BODY>
		<div>登录超时或操作错误，请点击回到<a href='<%=basePath%>mobile/mobileIndex'>首页！</a></div>
		<div>如果多次出现本错误，Email 到 &nbsp; 357370736@qq.com,感谢你的支持！
		
		</div>
		
	</BODY>
</HTML>