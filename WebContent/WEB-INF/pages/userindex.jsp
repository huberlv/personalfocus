<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%----%>

		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/nav.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/userindexstyle.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/colorPicker.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/tabPanel.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/friendList.css">
		<LINK id="theme" rel=stylesheet type=text/css
			href="<s:property value="%{#session.cssFile}"/>">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/kindeditorLayer.css">
		<SCRIPT type=text/javascript src="/personalfocus/dwr/engine.js"></script>
		<SCRIPT type=text/javascript src="/personalfocus/dwr/util.js"></script>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/grayscale.js"></SCRIPT>
		<SCRIPT type=text/javascript
			src="/personalfocus/dwr/interface/updateUserModule.js"></script>
		<SCRIPT type=text/javascript
			src="/personalfocus/dwr/interface/checkUpdateUserModule.js"></script>
		<SCRIPT type=text/javascript
			src="/personalfocus/dwr/interface/updateContentReceiver.js"></script>
		<SCRIPT type=text/javascript
			src="/personalfocus/dwr/interface/updateTheme.js"></script>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/controlTookit.js"></SCRIPT>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/userIndex.js"></SCRIPT>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/colorPickerLib.js"></SCRIPT>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/tabPanel.js"></SCRIPT>
		<SCRIPT type=text/javascript
			src="/personalfocus/js/common/feature.js"></SCRIPT>
		
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/handleUnCheckPaths.js"></SCRIPT>
		<SCRIPT type=text/javascript src="/personalfocus/js/common/dialog.js"></SCRIPT>
		<SCRIPT type=text/javascript>
	   var userId="${userId}";
	   var  timeout=${timeout};
	</SCRIPT>
		<style type="text/css">
#lhpopciones a,#lhpopciones a:visited {
	color: < s :   property value =   "%{#session.linkColor}"/ >;
}
</style>
	</HEAD>
	<BODY id="b"
		style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;;padding:0px 0px;margin:0px 0px;" >
		<div id="background" align='center'></div>

		<div id=lhpopciones
			style="background-color: <s:property value="%{#session.navColor}"/>">
			<img
				style="vertical-align: middle; margin-left: 20px; margin-right: 10px"
				src="/personalfocus/images/portrait.jpg"></img>
			用户：
			<s:property value="%{#session.username}" />
			&nbsp;&nbsp;<a href="#" onClick="exit();">退出</a>&nbsp;&nbsp;
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
			<span style="color:#FF0000;font-weight: bold;">${changeView}</span>
			<span style='display:inline;'>
			
			<label>&nbsp;|&nbsp;&nbsp;好友账户：</label><input type="text" size='6' id='masterUserId'/>
				    <input type="button" value="访问" onClick="showUserSpace('masterUserId');"/>
			     
			      </span>
			<div id="tools">
				<span style="float: left; margin-left: 10px;"></span>
				<div id='groups'>
					<ul class="tabTitle">
						<s:iterator value="focusList" status="status">
							<li class="unDisplayGroupStyle"
								onclick="changeGroup('group<s:property value="getGroupId()" escape="false"/>','groupTag<s:property value="getGroupId()" escape="false"/>')"
								id='groupTag<s:property value="getGroupId()" escape="false"/>'>
								<s:property value="getGroupName()" escape="false" />
							</li>
						</s:iterator>
						<li><img src="/personalfocus/images/loading.gif" title="平台每10分钟自动识别订阅更新!正在异步检查中，你可继续其它工作，有更新后本页面自动通知！" alt="正在检查更新"></img></li>
					</ul>
					
				</div>
				快捷工具：
				<span id="edit"><a href='javascript:void(0)'
					onclick='setReadyAllModules();' id='module'>全部标记为已读</a>| <a
					href="javascript:void(0)" onClick="editablePattern();">装扮空间</a> |<a
					title="剔除不关注的内容" href="javascript:void(0)"
					onClick="enterHandleUnCheckModule();">幻灯片模式</a> </span>
			</div>
		</div>

		<div id="colorPicker" style="float: right; display: none;">

			<DIV class=exContent id=ex01>
				<DIV style="OVERFLOW: hidden; WIDTH: 205px">
					<UL class=ContactMenu>
						<LI>
							<SPAN>颜色</SPAN>
						</LI>
						<LI>
							<SPAN>皮肤</SPAN>
						</LI>
					</UL>
				</DIV>
				<DIV class=ContactBox>
					<DIV class=ContactTxt>

						<div class="form-item">
							<input type="text" id="color1" name="color1" class="colorwell"
								value="导航栏" />
						</div>
						<div class="form-item">
							<input type="text" id="color4" name="color4" class="colorwell"
								value="导航栏字体" />
						</div>
						<div class="form-item">
							<input type="text" id="color2" name="color2" class="colorwell"
								value="模块" />
						</div>
						<div class="form-item">
							<input type="text" id="color3" name="color3" class="colorwell"
								value="背景" />
						</div>
						<div id="picker"></div>
					</DIV>
					<DIV class=ContactTxt>

						<table>
							<tr>
								<td width="90">
									<div class="themeTitle">
										<a href="javascript:void(0)"
											onClick="changeTheme('themeSunflower');"><img
												src="/personalfocus/images/theme/sunflower.jpg"
												class="smallPic" alt="向日葵"></img> </a>
									</div>
								</td>
								<td width="90">
									<div class="themeTitle">
										<a href="javascript:void(0)"
											onClick="changeTheme('themePink');"><img
												src="/personalfocus/images/theme/pink.jpg" class="smallPic"
												alt="粉红"></img> </a>
									</div>
								</td>
							</tr>
							<tr>
								<td width="90">
									<div class="themeTitle">
										<a href="javascript:void(0)"
											onClick="changeTheme('themeBlack');"><img
												src="/personalfocus/images/theme/black.jpg" class="smallPic"
												alt="黑色"></img> </a>
									</div>
								</td>
								<td width="90">
									<div class="themeTitle">
										<a href="javascript:void(0)"
											onClick="changeTheme('themeOrange');"><img
												src="/personalfocus/images/theme/orange.jpg"
												class="smallPic" alt="橙色"></img> </a>
									</div>
								</td>
							</tr>
							<tr>
								<td width="90">
									<div class="themeTitle">
										<a href="javascript:void(0)"
											onClick="changeTheme('themeStar');"><img
												src="/personalfocus/images/theme/star.jpg" class="smallPic"
												alt="星空"></img> </a>
									</div>
								</td>
								<td width="90">
									<div class="themeTitle">
										<a href="javascript:void(0)" onClick="changeTheme('themeSummer');"><img
												src="/personalfocus/images/theme/summer.jpg"
												class="smallPic" alt="夏天"></img> </a>
									</div>
								</td>
							</tr>
						</table>





						<div class="themeTitle">
							<a href="javascript:void(0)" onClick=changeTheme(null);>清空主题</a>
						</div>
					</DIV>
				</DIV>
			</DIV>
		</div>



		<s:iterator value="focusList" status="status">
			<div id="group<s:property value="getGroupId()" escape="false"/>"
				class='group'>
				<s:iterator value="getViewList()" status="status">
					<div class="main-cont"
						id="<s:property value="getId().getUserModuleId()" escape="false"/>"
						style="<s:property value="getId().getUserModuleStyle()" escape="false"/>">
						<div class="main-cont-tit"
							style="background-color: <s:property value="%{#session.moduleColor}"/>"
							id="tit">
							<s:property value="getId().getUserModuleName()" />
							----
							<a title='<s:property value="getId().getWebsiteName()" />'
								href="<s:property value="getId().getWebAddress()" escape="false"/>"
								target="_blank"><s:property value="getId().getWebsiteName()" />
							</a>
						</div>
						<div class='main-cont-tit'
							style="background-color: <s:property value="%{#session.moduleColor}"/>">
							<a href="javascript:void(0)"><img
									onclick="getRssFeed('/personalfocus/user/rssfeed?moduleId=<s:property value="getId().getModuleId()" escape="false"/>','<s:property value="getId().getUserModuleId()" escape="false"/>','<s:property value="getId().getUserModuleName()" escape="false"/>')"
									title="复制到剪贴板" alt="RSS"
									src="/personalfocus/images/rss/ct_rss.gif"></img> </a>
							<a href="javascript:void(0)"
								onClick="deleteUserModule('<s:property value="getId().getUserModuleId()" escape="false"/>')">删除</a>
							<a href="javascript:void(0)"
								onClick="hideUserModuel('<s:property value="getId().getUserModuleId()" escape="false"/>')">隐藏</a>
							<a href="javascript:void(0)" name="unUpdate"
								id="mark<s:property value="getId().getUserModuleId()" escape="false"/>"
								onClick="setReadAModule('<s:property value="getId().getUserModuleId()" escape="false"/>olpfs',this);">标记为已读</a>


							<s:if test="getId().getEnable()==2">
								<a
									href="/personalfocus/user/redirect_showCustomModule?moduleId=<s:property value="getId().getModuleId()" escape="false"/>&userModuleId=<s:property value="getId().getUserModuleId()" escape="false"/>">编辑订阅源</a>
							</s:if>
							<a  href='javascript:void(0)'
								onclick="showHistoyOfModule('<s:property value="getId().getUserModuleId()" escape="false"/>','<s:property value="getId().getModuleId()" escape="false"/>')">历史纪录</a>


						</div>
						<iframe
							src="showMyFocosIframe?userModuleId=<s:property value="getId().getUserModuleId()" escape="false"/>&moduleId=<s:property value="getId().getModuleId()" escape="false"/>"
							frameborder="0" scrolling="no" width="100%"
							id="<s:property value="getId().getUserModuleId()" escape="false"/>olpfs"></iframe>
					</div>
				</s:iterator>
			</div>
		</s:iterator>
		<div id='unuse' style='width: 90%'>
			&nbsp;
		</div>
		<DIV class=copyright id=copyright> 
			<A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A>
			
			<DIV class=copy>
				<!--版权所有&nbsp;&nbsp;  &copy;2009-2010-->
			</DIV>
		</DIV>
		<div id='dis-control'>
			<center>
				<img id="ArrowLeft"
					src="/personalfocus/images/userIndexImage/Arrow Left.png" alt="上一页"
					width="32" height="32" onclick='changeDisplayDiv(-1);'>
				&nbsp;&nbsp;
				<img id="ArrowRight"
					src="/personalfocus/images/userIndexImage/Arrow Right.png"
					alt="下一页" width="32" height="32" onclick='changeDisplayDiv(1);'>
				&nbsp;&nbsp;
				<img title="关闭" src="/personalfocus/images/userIndexImage/Stop.png"
					alt="关闭" width="32" height="32" onclick='closeDisplayDiv();'>
				&nbsp;&nbsp;&nbsp;
				<span id="handleUnCheckPathsModule"> <a title="剔除不关注的内容"
					href="javascript:void(0)" onClick='handleUnCheckPaths(true);'>进入剔除模式</a>
				</span>
			</center>
		</div>
		<div id='cover'
			style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; background: #66CCCC; z-index: 19811214; display: none">
			&nbsp;
		</div>
		<div class="ke-dialog" id="ke-dialog"
			style="z-index: 2147483647; top: 100px; left: 460px; display: none;">
			<table width="380" height="180" border="0" cellpadding="0"
				cellspacing="0" class="ke-dialog-table">
				<tbody>
					<tr>
						<td class="ke-tl">
							<span class="ke-dialog-empty"></span>
						</td>
						<td class="ke-tc">
							<span class="ke-dialog-empty"></span>
						</td>
						<td class="ke-tr">
							<span class="ke-dialog-empty"></span>
						</td>
					</tr>
					<tr>
						<td class="ke-ml">
							<span class="ke-dialog-empty"></span>
						</td>
						<td class="ke-mc">
							

						</td>
						<td class="ke-mr">
							<span class="ke-dialog-empty"></span>
						</td>
					</tr>
					<tr>
						<td class="ke-bl">
							<span class="ke-dialog-empty"></span>
						</td>
						<td class="ke-bc">
							<span class="ke-dialog-empty"></span>
						</td>
						<td class="ke-br">
							<span class="ke-dialog-empty"></span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</BODY>
</HTML>
