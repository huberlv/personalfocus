<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>互联网关注订阅平台</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/contentstyle.css">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/nav.css">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/registcss/date_input.css">
<LINK  rel=stylesheet type=text/css href="<s:property value="%{#session.cssFile}"/>">
<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
<SCRIPT type=text/javascript  charset="gb2312" src="/personalfocus/js/userindexjs/updatePsw.js"></SCRIPT>
<script type="text/javascript" src="/personalfocus/js/registjs/jquery.date_input.min.js"></script>

 
	
	<script type="text/javascript">
	
	var $j = jQuery.noConflict();

	$j(function() { 
		$j("#birthday").date_input(); 
		}); 
    </script>
    
    <script type=text/javascript src="/personalfocus/dwr/engine.js"></script>
  <script type=text/javascript src="/personalfocus/dwr/util.js"></script>
  <script type=text/javascript src="/personalfocus/dwr/interface/updatePsw.js"></script>
    
    
<style type="text/css">
#lhpopciones a ,#lhpopciones a:visited{
  color:<s:property value="%{#session.linkColor}"/>;
}
</style>
</HEAD>
<BODY  style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;height:800px;">
 <div id="background"> </div>
 
 
<DIV id=lhpopciones  style="background-color: <s:property value="%{#session.navColor}"/>">
  <img style="vertical-align: middle; margin-left:20px; margin-right:10px"  src="/personalfocus/images/portrait.jpg"></img>用户：<s:property value="%{#session.username}" /> &nbsp;&nbsp;&nbsp;&nbsp;

<img class="icon"
	src="/personalfocus/images/focus.png"><a href="myfocus">我的关注</a>|
<img class="icon" src="/personalfocus/images/add.png"><a
	href="showaddmonitor">订阅关注</a>| <img class="icon"
	src="/personalfocus/images/manage.png"><a
	href="showfocusmanagement">管理关注</a>| <img
	class="icon" src="/personalfocus/images/info.png"><a
	href="userinfo">个人信息管理</a>| <img class="icon"
	src="/personalfocus/images/spacem.png"><a href="userSpaceManager">空间管理</a>|
	<img class="icon"
	src="/personalfocus/images/adfun.png"><a href="redirect_uploadCustomModule">高级功能</a>|
	<img class="icon"
	src="/personalfocus/images/help.png"><a href="../index/showHelp">帮助</a>

	<div id="tools"></DIV></div>
	
	
<DIV class=g-doc>
<DIV class=g-hd>
<H1 class=m-logo><A href=""></A></H1>
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
修改个人信息</div>

<s:form action="updateuserinfo">

<table class="cont-tab" cellspacing="0" cellpadding="0"
	style="table-layout: fixed">
	<tr>
		<td width="416" class="td1">昵称：</td>
		<td width="255" class="td2">
		<div class="fle">
		<s:textfield name="username" id="username"    cssClass="inp ipt-normal"  maxlength="18"></s:textfield>
		</div>
		</td>
		<td width="127" class="td3">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1">手机：</td>

		<td class="td2"><s:textfield name="phone" id="phone"   cssClass="inp ipt-normal"  maxlength="16"></s:textfield></td>
		<td class="td3">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1">邮箱：</td>
		<td class="td2"><s:textfield name="mailbox" id="mailbox"    cssClass="inp ipt-normal"  maxlength="16"></s:textfield></td>
		<td class="td3">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1">出生日期：</td>
		<td class="td2"><s:textfield  name="birthday" id="birthday"    cssClass="inp ipt-normal"  maxlength="16"/></td>
		<td class="td3">&nbsp;</td>
	</tr>
</table>

<p align="center">
  <input type="submit" name="Submit" value="保存">
</p>
</s:form>
</div>
</div>




<div class="mcont">
<div class="main-cont">
<div class="main-cont-tit">
<div class="arr"></div>
修改密码</div>

<table class="cont-tab" cellspacing="0" cellpadding="0"
	style="table-layout: fixed">
	<tr>
		<td width="416" class="td1">当前密码：</td>
		<td width="255" class="td2">
		<div class="fle">
		<s:password name="currentPsw" id="currentPsw"    cssClass="inp ipt-normal"  maxlength="18"></s:password>
		</div>
		</td>
		<td width="127" class="td3">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1">新密码：</td>

		<td class="td2"><s:password name="newPsw" id="newPsw"   cssClass="inp ipt-normal"  maxlength="16"></s:password></td>
		<td class="td3">&nbsp;</td>
	</tr>
	<tr>
		<td class="td1">再次输入新密码：</td>
		<td class="td2"><s:password name="pswConfirm" id="pswConfirm"    cssClass="inp ipt-normal"  maxlength="16"></s:password></td>
		<td class="td3">&nbsp;</td>
	</tr>
	
</table>

<p align="center">
  <input type="button" name="Submit" value="保存" onclick="updatePassword()">
</p>

</div>
</div>



<div class="mft"></div>
</div>
</DIV>
<!--
<DIV class=g-ft>
<DIV class=inner>
<DIV class=copy-wrap>
<DIV class=copyright>
 <A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A>   
<DIV class=copy>版权所有&nbsp;&nbsp;  
&copy;2009-2010</DIV>
</DIV></DIV></DIV></DIV>-->


</BODY>
</HTML>
