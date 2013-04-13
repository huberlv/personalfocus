<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>互联网关注订阅平台</TITLE>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK rel=stylesheet type=text/css
	href="/personalfocus/css/userindexcss/nav.css">
<LINK rel=stylesheet type=text/css
	href="/personalfocus/css/userindexcss/contentstyle.css">
<LINK rel=stylesheet type=text/css
	href="<s:property value="%{#session.cssFile}"/>">
<SCRIPT type=text/javascript
	src="/personalfocus/js/userindexjs/addMonitor.js"></SCRIPT>
<style type="text/css">
#lhpopciones a,#lhpopciones a:visited {
	color: <s:property value = "%{#session.linkColor}"/>;
	
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
</div>




<DIV class=g-doc>
<DIV class=g-hd>
<H1 class=m-logo><A href=""></A></H1>
</DIV>


<%--内容--%>
<div class="content"><s:form action="showWebPage" method="get"
	target="_blank" onsubmit="return addMonitor();">
	<div class="mhd">
	<div class="tit"></div>
	</div>
	<div class="mcont">
	<div class="main-cont">
	<div class="main-cont-tit">
	<div class="arr"></div>
	<span>智能订阅</span>&nbsp;&nbsp;&nbsp;&nbsp;<s:a href="addMonitorByManual">手动订阅</s:a></div>

	<table class="cont-tab" cellspacing="0" cellpadding="0" style="">

		<tr id="tr_input_username">
			<td width="272" class="td1">网址：<span class="nes">*</span></td>
			<td width="255" class="td2">
			<div class="fle"><s:textfield name="url" id="url"
				cssClass="inp ipt-normal" cssStyle=" font-weight:bold"></s:textfield>
			</div>
			</td>
			<td width="271">
			<div class="fle"></div>
			</td>
		</tr>

	</table>
	<p><input type="button" onclick="addMonitor();" value="到该网页订阅关注">
	</p>
	<p>订阅成功后，可单击查看<a href='/personalfocus/user/myfocus?onlyShowUpdate=0'>所有关注</a>，或等待订阅更新后自动显示所订阅关注！</p>
	<p style='align:left;'>热门网址：</p>
	<center><table>
		<tbody>
			<tr>
			    <td><a target='_blank'   href="/personalfocus/user/showWebPage?url=http://news.soso.com">搜搜新闻</a></td>
				<td><a target='_blank'   href="/personalfocus/user/showWebPage?url=http://www.sina.com.cn">新 浪</a></td>
				<td><a target='_blank'   href="/personalfocus/user/showWebPage?url=http://news.qq.com">腾讯新闻</a></td>
				<td><a target='_blank'   href="/personalfocus/user/showWebPage?url=http://www.sohu.com">搜 狐</a></td>
				<td><a target='_blank'   href="/personalfocus/user/showWebPage?url=http://www.163.com">网 易</a></td>
			</tr>
			
		</tbody>
	</table>
	</center>
	</div>
	</div>
	<!-- 分拆补充资料部分 End -->
	<div class="mft"></div>
</s:form></div>
</div>

<DIV class=g-ft>
<DIV class=inner>
<DIV class=copy-wrap>
<DIV class=copyright><A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A> 

</DIV>
</DIV>
</DIV>
</DIV>

</BODY>
</HTML>
