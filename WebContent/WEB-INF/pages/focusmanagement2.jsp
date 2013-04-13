<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>互联网关注订阅平台</title>
<style type="text/css">


#content{overflow:hidden;}

</style>


<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/contentstyle.css">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/nav.css">
<LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/kindeditorLayer.css">
<LINK  rel=stylesheet type=text/css href="<s:property value="%{#session.cssFile}"/>">
<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>

<SCRIPT type=text/javascript charset="gb2312" src="/personalfocus/js/userindexjs/detailManagement.js"></SCRIPT>
    <script type='text/javascript' charset="gb2312" src='/personalfocus/dwr/interface/updateModule.js'></script>
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
	　<a href="showfocusmanagement">显示管理</a><span class="title">订阅管理</span><a href="showDefaultReceive">默认提醒管理</a><a href="showGroup">分组管理</a>
    </div>
     <input type="button" onclick="selectReverse()" value="反选">
	  <input type="button" onclick="selectAll()" value="全选">
	  <input type="submit"  onclick="delmodule();return false;"  value="删除"> &nbsp;&nbsp;&nbsp;
	  移动到：<s:select list="subgroupList" listKey="subgroupId" listValue="groupName" name="group" id="group"></s:select><input type="submit"  onclick="changeGroup();return false;"  value="移动">
	  
	 <input type="button" value="添加分组" onclick="showAddGroupPanel()">
	</div>
    
    
    <s:iterator value="modifylist" status="status">
      <div class="main-cont">
        <table width="820px">
        <tr>
        <td>
           <s:checkbox name="moduleBox" fieldValue="%{getUserModuleId()}"></s:checkbox>
		</td>
        <td width="780px">
            <div class="main-cont-tit" onclick="it('m<s:property value="getUserModuleId()"/>')" ><div class="arr2"></div>
		       <s:property value="getUserModuleName()"/>----<s:property value="subgroup.getGroupName()"/>
		    </div>
		</td>
		
		</tr>
        </table>
        <div id="m<s:property value="getUserModuleId()"/>"  class="moduleTitle"  style="display: none;">	
              <table class="cont-tab" cellspacing="0" cellpadding="0" style="" id="tableid">

<tr>
    <td width="427" class="td1">自定义栏目名称：<span class="nes">*</span></td>
    <td width="411" class="td2"><s:textfield value="%{getUserModuleName()}" id="moduleName%{getUserModuleId()}"></s:textfield></td>
    <td class="td3"></td>
      </tr>
<tr>
  <td class="td1">网站名称：</td>
  <td class="td2"><s:property value="getWebsiteName()"/></td>
  <td class="td3"></td>
</tr>
  
  
    <s:if test="getMessage()!=0">
    
  <tr>
    <td class="td1">发送到手机：</td>
    <td class="td2"><input type="checkbox" name="isMobile"  id="isMobile<s:property value="getUserModuleId()"/>" value="0" checked="checked"/></td>
    <td class="td3"></td>
    </tr>

   <tr id="mobileinterval">
     <td class="td1">发送短信时段：</td>
     <td class="td2">起始
       <select name="select"  id="messageStartTime<s:property value="getUserModuleId()"/>">
         <option value="<s:property value="getMessageStartTime()"/>" selected><s:property value="getMessageStartTime()"/></option>
         <option value="00:00:00">0点</option>
         <option value="01:00:00">1点</option>
         <option value="02:00:00">2点</option>
         <option value="03:00:00">3点</option>
         <option value="04:00:00">4点</option>
         <option value="05:00:00">5点</option>
         <option value="06:00:00">6点</option>
         <option value="07:00:00">7点</option>
         <option value="08:00:00">8点</option>
         <option value="09:00:00">9点</option>
         <option value="10:00:00">10点</option>
         <option value="11:00:00">11点</option>
         <option value="12:00:00">12点</option>
         <option value="13:00:00">13点</option>
         <option value="14:00:00">14点</option>
         <option value="15:00:00">15点</option>
         <option value="16:00:00">16点</option>
         <option value="17:00:00">17点</option>
         <option value="18:00:00">18点</option>
         <option value="19:00:00">19点</option>
         <option value="20:00:00">20点</option>
         <option value="21:00:00">21点</option>
         <option value="22:00:00">22点</option>
         <option value="23:00:00">23点</option>
         <option value="24:00:00">24点</option>
                                </select>
       ---
       结束
       <select name="select2" id="messageStopTime<s:property value="getUserModuleId()"/>">
         <option value="<s:property value="getMessageStopTime()"/>" selected><s:property value="getMessageStopTime()"/></option>
         <option value="00:00:00">0点</option>
         <option value="01:00:00">1点</option>
         <option value="02:00:00">2点</option>
         <option value="03:00:00">3点</option>
         <option value="04:00:00">4点</option>
         <option value="05:00:00">5点</option>
         <option value="06:00:00">6点</option>
         <option value="07:00:00">7点</option>
         <option value="08:00:00">8点</option>
         <option value="09:00:00">9点</option>
         <option value="10:00:00">10点</option>
         <option value="11:00:00">11点</option>
         <option value="12:00:00">12点</option>
         <option value="13:00:00">13点</option>
         <option value="14:00:00">14点</option>
         <option value="15:00:00">15点</option>
         <option value="16:00:00">16点</option>
         <option value="17:00:00">17点</option>
         <option value="18:00:00">18点</option>
         <option value="19:00:00">19点</option>
         <option value="20:00:00">20点</option>
         <option value="21:00:00">21点</option>
         <option value="22:00:00">22点</option>
         <option value="23:00:00">23点</option>
         <option value="24:00:00">24点</option>
       </select></td>
       <td class="td3"></td>
     </tr>
   <tr id="sendMaxCount">
     <td class="td1">每次最多发送条数：</td>
     <td class="td2"><s:textfield value="%{getMessageMax()}" id="sendMaxCount%{getUserModuleId()}" /></td>
     <td class="td3"></td>
     </tr>
   <tr id="mobilefrequency">
    <td class="td1">发送短信频率：</td>
    <td class="td2"><s:textfield value="%{getMessageSf()}"  id="mobilefrequency%{getUserModuleId()}" ></s:textfield>
   <s:if test="getMessageFrequencyType()=='hour'">
        <select name="select5" id="mobileUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour"  selected>小时</option>
          <option  value="minute">分钟</option>
        </select>
    </s:if>
    <s:else>
        <select name="select5"   id="mobileUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour" >小时</option>
          <option  value="minute" selected>分钟</option>
        </select>
    </s:else>
    
    </td>
    <td class="td3"></td>
    </tr>
 </s:if>
 
 
 
  <s:else>
  <tr>
    <td class="td1">发送到手机： </td>
 
     <td class="td2"><input type="checkbox" name="isMobile"  id="isMobile<s:property value="getUserModuleId()"/>" value="0" /></td>
     <td class="td3"></td>
    </tr>

   <tr id="mobileinterval">
     <td class="td1">发送短信时段：</td>
     <td class="td2">起始
       <select name="select" id="messageStartTime<s:property value="getUserModuleId()"/>">
         <option value="08:00:00" selected>请选择</option>
         <option value="00:00:00">0点</option>
         <option value="01:00:00">1点</option>
         <option value="02:00:00">2点</option>
         <option value="03:00:00">3点</option>
         <option value="04:00:00">4点</option>
         <option value="05:00:00">5点</option>
         <option value="06:00:00">6点</option>
         <option value="07:00:00">7点</option>
         <option value="08:00:00">8点</option>
         <option value="09:00:00">9点</option>
         <option value="10:00:00">10点</option>
         <option value="11:00:00">11点</option>
         <option value="12:00:00">12点</option>
         <option value="13:00:00">13点</option>
         <option value="14:00:00">14点</option>
         <option value="15:00:00">15点</option>
         <option value="16:00:00">16点</option>
         <option value="17:00:00">17点</option>
         <option value="18:00:00">18点</option>
         <option value="19:00:00">19点</option>
         <option value="20:00:00">20点</option>
         <option value="21:00:00">21点</option>
         <option value="22:00:00">22点</option>
         <option value="23:00:00">23点</option>
         <option value="24:00:00">24点</option>
                                </select>
       ---
       结束
       <select name="select2"  id="messageStopTime<s:property value="getUserModuleId()"/>">
         <option value="20:00:00" selected>请选择</option>
         <option value="00:00:00">0点</option>
         <option value="01:00:00">1点</option>
         <option value="02:00:00">2点</option>
         <option value="03:00:00">3点</option>
         <option value="04:00:00">4点</option>
         <option value="05:00:00">5点</option>
         <option value="06:00:00">6点</option>
         <option value="07:00:00">7点</option>
         <option value="08:00:00">8点</option>
         <option value="09:00:00">9点</option>
         <option value="10:00:00">10点</option>
         <option value="11:00:00">11点</option>
         <option value="12:00:00">12点</option>
         <option value="13:00:00">13点</option>
         <option value="14:00:00">14点</option>
         <option value="15:00:00">15点</option>
         <option value="16:00:00">16点</option>
         <option value="17:00:00">17点</option>
         <option value="18:00:00">18点</option>
         <option value="19:00:00">19点</option>
         <option value="20:00:00">20点</option>
         <option value="21:00:00">21点</option>
         <option value="22:00:00">22点</option>
         <option value="23:00:00">23点</option>
         <option value="24:00:00">24点</option>
       </select></td>
       <td class="td3"></td>
     </tr>
   <tr id="sendMaxCount">
     <td class="td1">每次最多发送条数：</td>
     <td class="td2"><s:textfield value="" id="sendMaxCount%{getUserModuleId()}" /></td>
     <td class="td3"></td>
     </tr>
   <tr id="mobilefrequency">
    <td class="td1">发送短信频率：</td>
    <td class="td2"><s:textfield value=""  id="mobilefrequency%{getUserModuleId()}" ></s:textfield>
    
    <s:if test="getMessageFrequencyType()=='hour'">
        <select name="select5" id="mobileUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour"  selected>小时</option>
          <option  value="minute">分钟</option>
        </select>
    </s:if>
    <s:else>
        <select name="select5"   id="mobileUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour" >小时</option>
          <option  value="minute" selected>分钟</option>
        </select>
    </s:else>
    
      </td>
      <td class="td3"></td>
    </tr>
    </s:else>
      
     <s:if test="isEmail()==true">
    
    <tr>
    <td class="td1">发送到邮箱：</td>
    <td class="td2"><input type="checkbox" name="isMail"  id="isMail<s:property value="getUserModuleId()"/>"   value="0" checked="checked"/>    </td>
    <td class="td3"></td>
    </tr>
     <tr id="mailinterval">
    <td class="td1">发送邮件时段：</td>
    <td class="td2">起始
      <select name="select3"  id="mailStartTime<s:property value="getUserModuleId()"/>" >
        <option value="<s:property value="getMailStartTime()"/>" selected><s:property value="getMailStartTime()"/></option>
        <option value="00:00:00">0点</option>
        <option value="01:00:00">1点</option>
        <option value="02:00:00">2点</option>
        <option value="03:00:00">3点</option>
        <option value="04:00:00">4点</option>
        <option value="05:00:00">5点</option>
        <option value="06:00:00">6点</option>
        <option value="07:00:00">7点</option>
        <option value="08:00:00">8点</option>
        <option value="09:00:00">9点</option>
        <option value="10:00:00">10点</option>
        <option value="11:00:00">11点</option>
        <option value="12:00:00">12点</option>
        <option value="13:00:00">13点</option>
        <option value="14:00:00">14点</option>
        <option value="15:00:00">15点</option>
        <option value="16:00:00">16点</option>
        <option value="17:00:00">17点</option>
        <option value="18:00:00">18点</option>
        <option value="19:00:00">19点</option>
        <option value="20:00:00">20点</option>
        <option value="21:00:00">21点</option>
        <option value="22:00:00">22点</option>
        <option value="23:00:00">23点</option>
        <option value="24:00:00">24点</option>
      </select>
---
       结束
<select name="select3" id="mailStopTime<s:property value="getUserModuleId()"/>">
  <option value="<s:property value="getMailStopTime()"/>" selected><s:property value="getMailStopTime()"/></option>
  <option value="00:00:00">0点</option>
  <option value="01:00:00">1点</option>
  <option value="02:00:00">2点</option>
  <option value="03:00:00">3点</option>
  <option value="04:00:00">4点</option>
  <option value="05:00:00">5点</option>
  <option value="06:00:00">6点</option>
  <option value="07:00:00">7点</option>
  <option value="08:00:00">8点</option>
  <option value="09:00:00">9点</option>
  <option value="10:00:00">10点</option>
  <option value="11:00:00">11点</option>
  <option value="12:00:00">12点</option>
  <option value="13:00:00">13点</option>
  <option value="14:00:00">14点</option>
  <option value="15:00:00">15点</option>
  <option value="16:00:00">16点</option>
  <option value="17:00:00">17点</option>
  <option value="18:00:00">18点</option>
  <option value="19:00:00">19点</option>
  <option value="20:00:00">20点</option>
  <option value="21:00:00">21点</option>
  <option value="22:00:00">22点</option>
  <option value="23:00:00">23点</option>
  <option value="24:00:00">24点</option>
</select></td>
<td class="td3"></td>
    </tr>
  <tr id="mailfrequency">
    <td class="td1">发送邮件频率：</td>
    <td class="td2"><s:textfield value="%{getMailSf()}" id="mailFrequency%{getUserModuleId()}"></s:textfield>
        <s:if test="getMailFrequencyType()=='hour'">
        <select name="select5" id="mailUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour"  selected>小时</option>
          <option  value="minute">分钟</option>
        </select>
        </s:if>
        <s:else>
        <select name="select5"   id="mailUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour" >小时</option>
          <option  value="minute" selected>分钟</option>
        </select>
    </s:else>
      
      </td>
      <td class="td3"></td>
    </tr>
  </s:if>
  
  <s:else>
  
      <tr>
    <td class="td1">发送到邮箱：</td>
    <td class="td2"><input type="checkbox" name="isMail"  id="isMail<s:property value="getUserModuleId()"/>"   value="0" />    </td>
    <td class="td3"></td>
    </tr>
     <tr id="mailinterval">
    <td class="td1">发送邮件时段：</td>
    <td class="td2">起始
      <select name="select3"  id="mailStartTime<s:property value="getUserModuleId()"/>">
        <option value="00:00:01" selected>请选择</option>
        <option value="00:00:00">0点</option>
        <option value="01:00:00">1点</option>
        <option value="02:00:00">2点</option>
        <option value="03:00:00">3点</option>
        <option value="04:00:00">4点</option>
        <option value="05:00:00">5点</option>
        <option value="06:00:00">6点</option>
        <option value="07:00:00">7点</option>
        <option value="08:00:00">8点</option>
        <option value="09:00:00">9点</option>
        <option value="10:00:00">10点</option>
        <option value="11:00:00">11点</option>
        <option value="12:00:00">12点</option>
        <option value="13:00:00">13点</option>
        <option value="14:00:00">14点</option>
        <option value="15:00:00">15点</option>
        <option value="16:00:00">16点</option>
        <option value="17:00:00">17点</option>
        <option value="18:00:00">18点</option>
        <option value="19:00:00">19点</option>
        <option value="20:00:00">20点</option>
        <option value="21:00:00">21点</option>
        <option value="22:00:00">22点</option>
        <option value="23:00:00">23点</option>
        <option value="24:00:00">24点</option>
      </select>
---
       结束
<select name="select3" id="mailStopTime<s:property value="getUserModuleId()"/>">
  <option value="23:59:59" selected>请选择</option>
  <option value="00:00:00">0点</option>
  <option value="01:00:00">1点</option>
  <option value="02:00:00">2点</option>
  <option value="03:00:00">3点</option>
  <option value="04:00:00">4点</option>
  <option value="05:00:00">5点</option>
  <option value="06:00:00">6点</option>
  <option value="07:00:00">7点</option>
  <option value="08:00:00">8点</option>
  <option value="09:00:00">9点</option>
  <option value="10:00:00">10点</option>
  <option value="11:00:00">11点</option>
  <option value="12:00:00">12点</option>
  <option value="13:00:00">13点</option>
  <option value="14:00:00">14点</option>
  <option value="15:00:00">15点</option>
  <option value="16:00:00">16点</option>
  <option value="17:00:00">17点</option>
  <option value="18:00:00">18点</option>
  <option value="19:00:00">19点</option>
  <option value="20:00:00">20点</option>
  <option value="21:00:00">21点</option>
  <option value="22:00:00">22点</option>
  <option value="23:00:00">23点</option>
  <option value="24:00:00">24点</option>
</select></td>
<td class="td3"></td>
    </tr>
     <tr id="mailfrequency">
       <td class="td1">发送邮件频率：</td>
       <td class="td2"><s:textfield value="" id="mailFrequency%{getUserModuleId()}"></s:textfield>
          <s:if test="getMailFrequencyType()=='hour'">
        <select name="select5" id="mailUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour"  selected>小时</option>
          <option  value="minute">分钟</option>
        </select>
        </s:if>
        <s:else>
        <select name="select5"   id="mailUnit<s:property value="getUserModuleId()"/>">
          <option  value="hour" >小时</option>
          <option  value="minute" selected>分钟</option>
        </select>
        </s:else>
    </td>
    <td class="td3"></td>
     </tr>
   
  </s:else>
    <tr>
   
    <td class="td1"></td> 
	<td class="td2"><input type="button"  name="updateModule" id="<s:property value="getUserModuleId()"/>" value="保存" onclick="update(this)"/></td>
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
