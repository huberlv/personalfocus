  <%@ page language="java" pageEncoding="UTF-8"%>
  <%@ taglib uri="/struts-tags" prefix="s"%>
  <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
  <html>
  <HEAD>
  <TITLE>互联网关注订阅平台</TITLE>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/nav.css">
  <LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/contentstyle.css">
  <LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/customModule.css">
  <LINK  rel=stylesheet type=text/css href="<s:property value="%{#session.cssFile}"/>">
  <script type="text/javascript" charset="utf-8" src="/personalfocus/kindeditor/kindeditor.js"></script>
  <script type=text/javascript charset="gbk" src="/personalfocus/js/userindexjs/customModule.js"></script>
  <script type="text/javascript">
  KE.show({
	  id: 'myKE', 
	  resizeMode : 1       
  });
  </script>
  
  <script type=text/javascript src="/personalfocus/dwr/engine.js"></script>
  <script type=text/javascript src="/personalfocus/dwr/util.js"></script>
  <script type=text/javascript src="/personalfocus/dwr/interface/customModule.js"></script>
  <style type="text/css">
  #lhpopciones a ,#lhpopciones a:visited{
	color:<s:property value="%{#session.linkColor}"/>;
  }
  </style>
  </HEAD>
  <BODY  style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;">
   <div id="background"> </div>
   
 <DIV id=lhpopciones style="background-color: <s:property value="%{#session.navColor}"/>">
  <img style="vertical-align: middle; margin-left:20px; margin-right:10px"  src="/personalfocus/images/portrait.jpg"></img>用户：<s:property value="%{#session.username}" /> &nbsp;&nbsp;&nbsp;&nbsp;

<img class="icon"
	src="/personalfocus/images/focus.png"><a href="myfocus">我的关注</a>|
<img class="icon" src="/personalfocus/images/add.png"><a
	href="showaddmonitor">订阅关注</a>| <img class="icon"
	src="/personalfocus/images/manage.png"><a
	href="showfocusmanagement">管理关注</a>| <img
	class="icon" src="/personalfocus/images/info.png"><a
	href="userinfo">个人信息管理</a>| <img class="icon"
	src="/personalfocus/images/spacem.png"><a href="userSpaceManager">空间管理</a>| <img class="icon"
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
  <div class="main-cont-tit"><div class="arr"></div>
		  <a href="showfileupload">邀请好友关注</a><span class="title">发布订阅源</span></div>					  
		     订阅源标题：<input type="text" id="userModuleName" value="新订阅源" class="textinput">
		     分组：<s:select list="subgroupList" listKey="subgroupId" listValue="groupName" id="subgroup"></s:select>
		  <textarea id="myKE" name="content" style="width:820px;height:300px;"></textarea>
		  <input type="button" value="预览" onClick="preview('myKE')"></input>  
		  <input type="button" value="保存订阅源" onClick="save()"></input>
	</div>
	<div id='divId'>&nbsp;</div>
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
<DIV class=copy><!--版权所有&nbsp;&nbsp;  
&copy;2009-2010--></DIV>
</DIV></DIV></DIV></DIV>
  </BODY>
  </HTML>
