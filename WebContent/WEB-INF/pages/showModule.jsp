<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title}</title>
<LINK rel="stylesheet" type="text/css" href="/personalfocus/css/userindexcss/mobile.css">
<base target="_self">
</head>

<body>
<p><s:a href="/personalfocus/mobile/showMobileUserIndex">返回平台</s:a>&nbsp;<a href="/personalfocus/mobile/alreadyRead?userModuleId=${moduleId}">标记为已读</a></p>
${updatecontent}
</body>
</html>