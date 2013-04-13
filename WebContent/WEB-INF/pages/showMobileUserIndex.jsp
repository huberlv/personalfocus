<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<LINK rel="stylesheet" type="text/css"
			href="/personalfocus/css/userindexcss/mobile.css">
		<LINK rel="stylesheet" type="text/css"
			href="/personalfocus/css/userindexcss/showMobileUserIndex.css">
		<title>互联网关注订阅平台</title>
	</head>
	<body>
		<h1>
			互联网关注订阅平台
		</h1>
		<p>
			用户：${userName}
		</p>
		<hr width="100%">

		<s:iterator value="moduels" status="status">

			<p class="web">
				<strong><s:property value="getWebName()" escape="false" />
				</strong>
			</p>
			<%int n=0;%>
			<p class="moduel">
				<s:iterator value="getModules()" status="status">
				<%if(n==0){out.print("|");n=1;} %>
					<a
						href="/personalfocus/mobile/showModule?userId=${userId}&password=${password}&moduleId=<s:property value="getModuelUrl()" escape="false"/>"
						class="<s:property value="getClassss()" escape="false"/>"><s:property
							value="getModuelName()" escape="false" />
					</a>|      
        
   </s:iterator>
   <%n=0;%>
			</p>
			<br/><hr width="100%">
		</s:iterator>
	</body>
</html>