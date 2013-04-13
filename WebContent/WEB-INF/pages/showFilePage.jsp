<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
response.setHeader("Cache-Control", "no-cache");
response.setBufferSize(128);
%>

<title>互联网关注订阅平台</title>

</head>


<body>
该链接是一个下载链接，确认下载请点击以下链接：
<s:a href="%{url}">点击下载</s:a>
</body>
</html>