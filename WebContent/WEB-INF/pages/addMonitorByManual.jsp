<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<base target="_self" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/nav.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/contentstyle.css">
		<LINK rel=stylesheet type=text/css
			href="<s:property value="%{#session.cssFile}"/>">
		<SCRIPT type="text/javascript"
			src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>

		<script type="text/javascript"
			src="/personalfocus/dwr/engine.js"></script>
		<script type="text/javascript"
			src="/personalfocus/dwr/util.js"></script>
		<script type="text/javascript"
			src="/personalfocus/dwr/interface/addModuleInfoByManual.js"></script>
		<script type="text/javascript"
			src="/personalfocus/js//userindexjs/addMonitorByManual.js"></script>
		
		<style>
		#lhpopciones a,#lhpopciones a:visited { color: < s : property value ="%{#session.linkColor}"/ >; }
		</style>
	</HEAD>
	<BODY
		style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;">

		<div id="background"></div>

		<DIV id=lhpopciones
			style="background-color: <s:property value="%{#session.navColor}"/>">
			<img
				style="vertical-align: middle; margin-left: 20px; margin-right: 10px"
				src="/personalfocus/images/portrait.jpg"></img>
			用户：
			<s:property value="%{#session.username}" />
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
			<a href="showfileupload">高级功能</a>|
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
								<a href="showaddmonitor">智能订阅</a>手动订阅
							</div>
						</div>
						<!-- ////// -->
						<p align="center">
							<center>
								互联网关注订阅平台
							</center>
						</p>
						<p align="center">
							网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:
							<input type="text" maxlength="255" value=""
								style="height: 20px; width: 300px;" id="url" />
						</p>
						<p align="center">
							网站名称:
							<input type="text" maxlength="20" value=""
								style="height: 20px; width: 300px;" id="webname" />
						</p>
						<p align="center">
							栏目名称:
							<input type="text" maxlength="20" value="新栏目"
								style="height: 20px; width: 300px;" id="modulename" />
						</p>
						<p align="center">
							分组：
							<s:select list="subgroupList" listKey="subgroupId"
								listValue="groupName" id="subgroup"></s:select>

						</p>
						<p align="center">
							<select name="monitorType" id="monitorType">
								<option value="0x1" selected="selected">
									由平台监控
								</option>
								<option value="0x2">
									由用户监控
								</option>
							</select>
							<select name="monitorType2" id="monitorType2">
								<option value="0x4" selected="selected">
									所有链接
								</option>
								<option value="0x8">
									所有内容
								</option>
							</select>
						</p>
						<p align="center">
							栏目特征:
							<textarea onblur="checkPathOfInput(this)" name="path" rows="1"
								cols="70" id="path" value=""
								style="overflow-x: hidden; overflow-y: visible"></textarea>
							<p><label>高度</label><input type="text" maxlength="4" value="600" style="width:40"
								 id="theight" />	
								 <label>宽度</label><input type="text" maxlength="4" value="400" style="width:40"
								 id="twidth" />	
								 </p>
						</p>
						<div align="center">
							<input type="checkbox" onclick="checkConfig()" checked="checked"
								value="1" id="config">
							使用我的默认设置
						</div>
						<!-- ////// -->
						<div id='olpfMoreChoiceDiv' style="display:none;">
						<s:action name="exeupdateDefault" executeResult="true"></s:action>
						</div>
						<input type="button" name="updateDefault" value="保存"
							onclick="saveAmodule()" id="saveAmodule"/>

						<!-- 分拆补充资料部分 End -->
						<div class="mft"></div>
					</div>
				</DIV>
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
