<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<HEAD>
<TITLE>互联网关注订阅平台</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK rel=stylesheet type=text/css
	href="/personalfocus/css/userindexcss/nav.css">
	<LINK rel=stylesheet type=text/css
	href="/personalfocus/css/userindexcss/userModuleHistoryList.css">
	
<LINK rel=stylesheet type=text/css
	href="<s:property value="%{#session.cssFile}"/>">
<style type="text/css">
#lhpopciones a,#lhpopciones a:visited {
	color:<s:property value ="%{#session.linkColor}"/>;
}

</style>
</HEAD>
<BODY
	style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;">

<div id="background"></div>

<DIV id=lhpopciones
	style="background-color: <s:property value="%{#session.navColor}"/>">
<img
	style="vertical-align: middle; margin-left: 20px; margin-right: 10px"
	src="/personalfocus/images/portrait.jpg"></img>用户：<s:property
	value="%{#session.username}" /> &nbsp;&nbsp;&nbsp;&nbsp; <img
	class="icon" src="/personalfocus/images/focus.png"><a
	href="myfocus">我的关注</a>| <img class="icon"
	src="/personalfocus/images/add.png"><a href="showaddmonitor">订阅关注</a>|
<img class="icon" src="/personalfocus/images/manage.png"><a
	href="showfocusmanagement">管理关注</a>| <img class="icon"
	src="/personalfocus/images/info.png"><a href="userinfo">个人信息管理</a>| <img class="icon"
	src="/personalfocus/images/spacem.png"><a href="userSpaceManager">空间管理</a>|
<img class="icon" src="/personalfocus/images/adfun.png"><a
	href="redirect_uploadCustomModule">高级功能</a>| <img class="icon"
	src="/personalfocus/images/help.png"><a href="../index/showHelp">帮助</a>

<div id="tools"></DIV>
<ul class="tabTitle">
    <s:iterator value="hrefs" status="status">
        <s:if test="#status.index+1==page">
          <li class="displayGroupStyle"/><a href="<s:property
			value="toString()" escape="false" />"><s:property
			value="#status.index+1"/></a></li>
        </s:if>
        <s:else>
		<li class="unDisplayGroupStyle"/><a href="<s:property
			value="toString()" escape="false" />"><s:property
			value="#status.index+1"/></a></li>
			</s:else>
	</s:iterator> 
</ul>
</div>
<div style="width:100%;height:50px;">&nbsp;</div>
<s:iterator value="userModuleHistoryList" status="status">

	<div class="history" style="${userModuleStyle}"><iframe
		src="/personalfocus/user/userModuleHistory?userModuleId=<s:property value="getId().getUserModuleId()" escape="false"/>&bufferId=<s:property value="getId().getBufferId()" escape="false"/>"
		frameborder="0" scrolling="no" width="100%" height="100%"></iframe>
	</div>

</s:iterator>

</body>
</html>