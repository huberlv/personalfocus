<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>互联网关注订阅平台</title>
<style type="text/css">

</style>

<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/contentstyle.css">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/nav.css">
<LINK  rel=stylesheet type=text/css href="<s:property value="%{#session.cssFile}"/>">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/kindeditorLayer.css">

<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/groupManagement.js"></SCRIPT>
    <script type='text/javascript' charset="gb2312" src='/personalfocus/dwr/interface/updateGroup.js'></script>
 	<script type='text/javascript' src='/personalfocus/dwr/engine.js'></script>
  	<script type='text/javascript' src='/personalfocus/dwr/util.js'></script>
<script language="javascript">
function it(contentid){
 $j("#"+contentid).slideToggle();
}
</script>
<style type="text/css">
#lhpopciones a ,#lhpopciones a:visited{
  color:<s:property value="%{#session.linkColor}"/>;
}
</style>
</head>
<BODY  style="background-color: <s:property value="%{#session.bgColor}"/>;padding:0px 0px;margin:0px 0px;">
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
	src="/personalfocus/images/spacem.png"><a href="userSpaceManager">空间管理</a>| <img class="icon"
	src="/personalfocus/images/adfun.png"><a href="redirect_uploadCustomModule">高级功能</a>|
	<img class="icon"
	src="/personalfocus/images/help.png"><a href="../index/showHelp">帮助</a>

	<div id="tools"></DIV></div>


<DIV class=g-doc>
<DIV class=g-hd>
<H1 class=m-logo><A href=""></A></H1>
</DIV>
  <s:form>
     <%--内容--%>
  <div class="content">
  <div class="mhd">
    <div class="tit"></div>
  </div>
    <div class="mcont">
    
	  <div class="main-cont">
           <div class="main-cont-tit"><div class="arr"></div>
		        　<a href="showfocusmanagement">显示管理</a><a href="showfocusmanagementdetail">订阅管理</a><a href="showDefaultReceive">默认提醒管理</a><span class="title">分组管理</span>
		   </div>
		     
	  <input type="button" onclick="selectReverse()" value="反选">
	  <input type="button" onclick="selectAll()" value="全选">
	  <input type="submit"  onclick="delgroup();return false;"  value="删除"> &nbsp;&nbsp;&nbsp;|
	  <input type="button" value="添加分组" onclick="showAddGroupPanel()">
	</div>

<s:iterator value="subgroupList" status="status">
      <div class="main-cont">
        <table width="820px">
        <tr>
        <td>
           <s:checkbox name="moduleBox" fieldValue="%{getSubgroupId()}"></s:checkbox>
		</td>
        <td width="780px">
            <div id="newName<s:property value="getSubgroupId()"/>" class="main-cont-tit" onclick="it('m<s:property value="getSubgroupId()"/>')" ><div class="arr2"></div>
		      <s:property value="getGroupName()"/>
		    </div>
		</td>
		
		</tr>
        </table>
        <div id="m<s:property value="getSubgroupId()"/>"  class="moduleTitle"  style="display: none;">	
           <table  cellspacing="0" cellpadding="0" style="border: 0" id="tableid">
				<tr>
				    <td width="300px" align="right">修改分组名称：</td>
				    <td width="180px" align="left"><s:textfield value="" id="newGroupname%{getSubgroupId()}"></s:textfield></td>
				    <td width="300px" align="left" ><input type="button"  id="<s:property value="getSubgroupId()"/>"  onclick="updategroupname(this)" value="保存"></input></td>
				</tr>
           </table>
    </div>
  
  
  
    </div>
	
	
	</s:iterator>

	
	
	
<!-- 分拆补充资料部分 End -->
<div class="mft"></div>
  </div>




</DIV>
</s:form>
</DIV>


<div class="ke-dialog"
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
			<input type='text' id='newGroupName'/><input type='button' id='addGroupBtn' onclick='addGroup()' value='确定'/>
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
				class="ke-button ke-dialog-no" onclick='canelAddGroup()' value="取消"
				id="loginButton"></div>
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


<DIV class=g-ft>
<DIV class=inner>
<DIV class=copy-wrap>
<DIV class=copyright>
 <A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A>   
<DIV class=copy><!--版权所有&nbsp;&nbsp;  
&copy;2009-2010--></DIV>
</DIV></DIV></DIV></DIV>


</BODY></HTML>
