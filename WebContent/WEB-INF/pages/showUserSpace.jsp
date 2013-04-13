<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>${userSpaceName}</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%----%>
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/nav.css">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/userindexstyle.css">
<LINK rel=stylesheet type=text/css href="<s:property value="cssFile"/>">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/kindeditorLayer.css">
	
<SCRIPT type=text/javascript src="/personalfocus/dwr/engine.js"></script>
<SCRIPT type=text/javascript src="/personalfocus/dwr/util.js"></script>
<SCRIPT type=text/javascript src="/personalfocus/dwr/interface/userSpace.js"></script>
<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/controlTookit.js"></SCRIPT>
<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/showUserSpaceControler.js"></SCRIPT>

	<SCRIPT type=text/javascript>
	var userId="${userId}";
	var masterUserId="${masterUserId}";</SCRIPT>
</HEAD>
<BODY  style="background-color: <s:property value="bgColor"/>;padding:0px 0px;margin:0px 0px;;padding:0px 0px;margin:0px 0px;">

<div id="background" align='center'> </div>
<DIV id=lhpopciones  style="background-color: <s:property value="navColor"/>">用户：<strong id="userName">${userName}</strong>  你好！      <a style="cursor: pointer;" onclick="showLogin()"><span id="loginText">登录</span><img alt="登录"  style="width: 30px;height: 30px;vertical-align: middle; cursor: pointer;"  src="/personalfocus/images/login.png" ></a>
<div id="tools"> 
<div id='groups'>
<ul class="tabTitle">
	<s:iterator value="focusList" status="status">
     <li class="unDisplayGroupStyle" onclick="changeGroup('group<s:property value="getGroupId()" escape="false"/>','groupTag<s:property value="getGroupId()" escape="false"/>')"  id='groupTag<s:property value="getGroupId()" escape="false"/>'><s:property value="getGroupName()" escape="false"/></li>	</s:iterator>
</ul>
</div>
  快捷工具：<a href="javascript:void(0)" onclick="selectAll();return false;">全选</a><a href="javascript:void(0)" onclick="inverse();return false;">反选</a><a href="javascript:void(0)" onclick="subscribeSelectUserModule();return false;">订阅选中模块</a><a  href="javascript:void(0)" onclick="subscribeUser();return false;">订阅空间</a><a  href="javascript:void(0)" onclick="subscribeAllUserModule();return false;">订阅所有模块</a></DIV>
</div>
<s:iterator value="focusList" status="status">
    <div id="group<s:property value="getGroupId()" escape="false"/>" class='group'>
    <s:iterator value="getViewList()" status="status">
	<div class="main-cont"
		id="<s:property value="getId().getUserModuleId()" escape="false"/>"
		style="<s:property value="getId().getUserModuleStyle()" escape="false"/>">
	<div class="main-cont-tit"   style="background-color: <s:property value="moduleColor"/>"><s:property
		value="getId().getUserModuleName()" /> ---- <a  title='<s:property value="getId().getWebsiteName()" />'
		href="<s:property value="getId().getWebAddress()" escape="false"/>" target="_blank"><s:property
		value="getId().getWebsiteName()" /></a></div>
	<div class='main-cont-tit'   style="background-color: <s:property value="moduleColor"/>">
	<input class="subscribe" type="checkbox"  value="<s:property value="getId().getUserModuleId()" escape="false"/>">	
	<label>订阅</label>
	<a href="javascript:void(0)"><img onclick="getRssFeed('/personalfocus/user/rssfeed?moduleId=<s:property value="getId().getModuleId()" escape="false"/>','<s:property value="getId().getUserModuleId()" escape="false"/>','<s:property value="getId().getUserModuleName()" escape="false"/>')" title="复制到剪贴板" alt="RSS" src="/personalfocus/images/rss/ct_rss.gif"></img>
		</a>
		<a
		onclick="flushModule('olpfs<s:property value="getId().getUserModuleId()" escape="false"/>')"
		href="javascript:void(0)">刷新</a></div>
	<iframe
		src="showUserFocosIframe?userModuleId=<s:property value="getId().getUserModuleId()" escape="false"/>&moduleId=<s:property value="getId().getModuleId()" escape="false"/>"
		frameborder="0" scrolling="no" width="100%"
		id="olpfs<s:property value="getId().getUserModuleId()" escape="false"/>"></iframe></div>
		</s:iterator>
		
		</div>
</s:iterator>
<div class="ke-dialog" id="ke-dialog"
	style="z-index: 19811214; top: 100px; left: 460px;display: none;">
<table width="380" height="180" border="0" cellpadding="0"
	cellspacing="0" class="ke-dialog-table">
	<tbody>
		<tr>
			<td class="ke-tl"><span class="ke-dialog-empty"></span></td>
			<td class="ke-tc"><span class="ke-dialog-empty"></span></td>
			<td class="ke-tr"><span class="ke-dialog-empty"></span></td>
		</tr>
		<tr>
			<td class="ke-ml"><span class="ke-dialog-empty"></span></td>
			<td class="ke-mc">
			<div class="ke-dialog-title">
			<p>账户： <input type="text" name="userId" id="userId"> <a
				onclick="window.open('/personalfocus/index/showregist');"
				style="cursor: pointer;">注册账户</a></p>
			<p>密码： <input type="password" name="password" id="password">
			<a>忘记密码</a></p>
			</div>
			<div class="ke-dialog-body">
			<table cellspacing="0" cellpadding="0" border="0"
				class="ke-loading-table"
				style="width: 600px; height: 400px; display: none;">
				<tbody>
					<tr>
						<td><span class="ke-loading-img"></span></td>
					</tr>
				</tbody>
			</table>
			</div>
			<div class="ke-dialog-bottom"><input type="button"
				class="ke-button ke-dialog-no" name="loginButton" value="登录"
				id="loginButton" onclick="checkLogin()"><input type="button"
				class="ke-button ke-dialog-no" name="canelButton" value="取消"
				id="canelButton"  onclick="hideLogin()"></div>
			</td>
			<td class="ke-mr"><span class="ke-dialog-empty"></span></td>
		</tr>
		<tr>
			<td class="ke-bl"><span class="ke-dialog-empty"></span></td>
			<td class="ke-bc"><span class="ke-dialog-empty"></span></td>
			<td class="ke-br"><span class="ke-dialog-empty"></span></td>
		</tr>
	</tbody>
</table>
</div>

<div id='unuse' style='width:90%'>&nbsp;</div>
		<DIV class=copyright id=copyright> <A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A> 

</DIV>
<div class="ke-dialog" id="ke-dialog2"
	style="z-index: 2147483647; top: 100px; left: 460px;display:none;">
<table width="380" height="180" border="0" cellpadding="0"
	cellspacing="0" class="ke-dialog-table">
	<tbody>
		<tr>
			<td class="ke-tl"><span class="ke-dialog-empty"></span></td>
			<td class="ke-tc"><span class="ke-dialog-empty"></span></td>
			<td class="ke-tr"><span class="ke-dialog-empty"></span></td>
		</tr>
		<tr>
			<td class="ke-ml"><span class="ke-dialog-empty"></span></td>
			<td class="ke-mc"><center>RSS feed: <span id="rssfeed">http</span></center>
			<center>你可使用RSS工具订阅或与好友分享，发布！</center>
			<center><input type="button"  value="确定" onclick="hideKDialog();">
			<input type="button"  value="复制地址" onclick="copyToClipboard()" id='copyToClipboard' style="display:none;"></center>
			
			</td>
			<td class="ke-mr"><span class="ke-dialog-empty"></span></td>
		</tr>
		<tr>
			<td class="ke-bl"><span class="ke-dialog-empty"></span></td>
			<td class="ke-bc"><span class="ke-dialog-empty"></span></td>
			<td class="ke-br"><span class="ke-dialog-empty"></span></td>
		</tr>
	</tbody>
</table>
</div>
</BODY>
</HTML>
