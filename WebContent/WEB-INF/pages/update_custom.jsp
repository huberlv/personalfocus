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
<script type="text/javascript">
            var moduleId = ${moduleId};
            var userModuleId =${userModuleId};     
</script>
<style type="text/css">
#lhpopciones a ,#lhpopciones a:visited{
  color:<s:property value="%{#session.linkColor}"/>;
}
</style>
</HEAD>
<BODY  style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;">
 <div id="background"> </div>
 
<DIV id=lhpopciones style="background-color: <s:property value="%{#session.navColor}"/>">
用户：<s:property value="%{#session.username}" /> &nbsp;&nbsp;&nbsp;&nbsp;

<a href="myfocus">我的关注</a>|<a href="showaddmonitor">订阅关注</a>|<a
	href="showfocusmanagement">管理关注</a>|<a href="userinfo">个人信息管理</a>| <img class="icon"
	src="/personalfocus/images/spacem.png"><a href="userSpaceManager">空间管理</a>|<a href="redirect_uploadCustomModule">高级功能</a>
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
                   <span class="title">编辑订阅源</span></div>
                        
                   
                   订阅源标题：<input type="text" id="userModuleName" value="新订阅源" class="textinput">
                   分组：<s:select list="subgroupList" listKey="subgroupId" listValue="groupName" id="subgroup" ></s:select>
        <textarea id="myKE" name="content" style="width:820px;height:300px;">
             ${moduleContent}
        </textarea>
        <input type="button" value="预览" onclick="preview('myKE')"></input> 
		<input type="button" value="更新订阅源" onclick="update()"></input>
        <div id='divId'>&nbsp;</div> 
                         
  </div>
  
<!-- 分拆补充资料部分 End -->
<div class="mft"></div>
</div>


</DIV>

</DIV>
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
