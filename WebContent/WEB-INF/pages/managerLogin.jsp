<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理登录</title>
<style type="text/css">

<!--
body {
	background-color: #d2d2d2;
}
-->
</style>
</head>
<body>

<s:form action="checkLogin">
<br><br><br><br><br>
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>
			<table width="100" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><img src="/personalfocus/images/managerLogin/land_top.gif" width="629" height="164"></td>
				</tr>
			</table>
			<table width="629" border="0" align="center" cellpadding="0"
				cellspacing="0">

				<tr>
					<td width="271" valign="top" background="/personalfocus/images/managerLogin/land_left.gif" align="center">

<p>账户: <s:textfield  name="userid" size="20"></s:textfield></p>
<p>密码: <s:password name="psw" size="20"></s:password></p> 
						<s:submit title="登录" value="登录"/>  &nbsp;
						<s:reset title="重置" value="重置" />
				
					</td>
					<td width="358" background="/personalfocus/images/managerLogin/land_right.gif" height="170">
					<font color="blue"><s:fielderror/></font></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</s:form>
</body>
</html>