<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>互联网关注订阅平台</title>
</head>
<body>
<div>用户空间不存在，请点击回到<a href='<%=basePath%>'>首页！</a></div>
		<div>如果确定存在此空间，可点击  <a href='mailto:357370736@qq.com'>联系我们</a><br/>
		     或Email到: &nbsp;357370736@qq.com&nbsp;,感谢你的支持！	
		</div>
</body>
</html>