<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/contentstyle.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/nav.css">

		<LINK rel=stylesheet type=text/css
			href="<s:property value="%{#session.cssFile}"/>">
<SCRIPT type="text/javascript">

function checkAccessType(){
	var accessTy=document.getElementById("accessType");

	if(accessTy.value=='2'){
		document.getElementById("qtr").style.display='';
		document.getElementById("atr").style.display='';
	}else{
		document.getElementById("qtr").style.display='none';
		document.getElementById("atr").style.display='none';
	}
}
window.onload=checkAccessType;
</SCRIPT>


		<style type="text/css">
#lhpopciones a,#lhpopciones a:visited {
	color: < s : property value = "%{#session.linkColor}"/ >;
}
</style>
	</HEAD>
	<BODY
		style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;">
		<div id="background">
		</div>


		<DIV id=lhpopciones
			style="background-color: <s:property value="%{#session.navColor}"/>">
			<img
				style="vertical-align: middle; margin-left: 20px; margin-right: 10px"
				src="/personalfocus/images/portrait.jpg"></img>用户：<s:property value="%{#session.username}" />
			&nbsp;&nbsp;&nbsp;&nbsp;

			<img class="icon" src="/personalfocus/images/focus.png">
			<a href="myfocus">我的关注</a>|
			<img class="icon" src="/personalfocus/images/add.png">
			<a href="showaddmonitor">订阅关注</a>|
			<img class="icon" src="/personalfocus/images/manage.png">
			<a href="showfocusmanagement">管理关注</a>|
			<img class="icon" src="/personalfocus/images/info.png">
			<a href="userinfo">个人信息管理</a>| <img class="icon"
	src="/personalfocus/images/spacem.png"><a href="userSpaceManager">空间管理</a>|
			<img class="icon" src="/personalfocus/images/adfun.png">
			<a href="redirect_uploadCustomModule">高级功能</a>|
			<img class="icon" src="/personalfocus/images/help.png">
			<a href="../index/showHelp">帮助</a>

			<div id="tools"></DIV>
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
							空间管理
						</div>

						<s:form action="updateUserSpaceInfo">

							<table class="cont-tab" cellspacing="0" cellpadding="0"
								style="table-layout: fixed">
								<tr>
									<td width="416" class="td1">
										空间名称：
									</td>
									<td width="255" class="td2">
										<div class="fle">
											<s:textfield name="userSpaceName" id="userSpaceName"
												cssClass="inp ipt-normal" maxlength="18"></s:textfield>
										</div>
									</td>
									<td width="127" class="td3">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="td1">
										访问权限：
									</td>

									<td class="td2">
									
									<s:select name="accessType" list="#{'0':'仅主人可访问','2':'回答问题可访问','3':'公开访问'}"
							value="accessType" id="accessType" onchange="checkAccessType();"></s:select>	
									</td>
									<td class="td3">
										&nbsp;
									</td>
								</tr>
								<tr id="qtr" style='display:none'>
									<td class="td1">
										问题：
									</td>
									<td class="td2">
										<s:textfield name="question" id="question"
											cssClass="inp ipt-normal" maxlength="16"></s:textfield>
									</td>
									<td class="td3">
										&nbsp;
									</td>
								</tr>
								<tr id="atr" style='display:none'>
									<td class="td1">
										答案：
									</td>
									<td class="td2">
										<s:textfield name="answer" id="answer"
											cssClass="inp ipt-normal" maxlength="16" />
									</td>
									<td class="td3">
										&nbsp;
									</td>
								</tr>
							</table>

							<p align="center">
								<input type="submit" name="Submit" value="保存">
							</p>
						</s:form>
					</div>
				</div>
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
