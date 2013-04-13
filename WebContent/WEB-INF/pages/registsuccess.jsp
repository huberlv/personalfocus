<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>互联网关注订阅平台---注册成功</title>
<link href="/personalfocus/css/registcss/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/personalfocus/js/registjs/md5-min.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/jquery_ajax.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/requestUtil.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/function.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/calendar.js"></script>
<SCRIPT type=text/javascript src="/personalfocus/js/registjs/verify1.js"></SCRIPT>

</head>


<body>
<div class="header"> 
	<div class="logo_w" ></div>
    <div class="head_links">帮助&nbsp;&nbsp;&nbsp;</div>
</div>

<div class="content">
  <div class="mhd">
    <div class="tit">欢迎注册互联网关注订阅平台</div>
  </div>
  <div class="mcont">
        
<div id="sysmessage" class="err-info" style="display:none"></div>


<div class="main-cont">
			<div class="main-cont-tit"><div class="arr"></div><h2>注册成功</h2></div>
		
            <table class="cont-tab" cellspacing="0" cellpadding="0" style="table-layout:fixed">
<!--用户名的处理 Start-->
<tr id="tr_input_username">
    <td class="td1"></td>
    <td class="td2">
		<div class="fle">
		你的账号为： <s:property value="id"/>
		</div>
	</td>
    <td class="td3">
           <s:a href="/personalfocus">进入我的关注</s:a>  
           </td>
  </tr>
<!--用户名的处理 Ent-->
    </table>
  </div>
    <div class="mft"></div>
</div>

</div>

<DIV class=g-ft>
<DIV class=inner>
<DIV class=copy-wrap>
<DIV class=copyright>
 <A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A>   
<DIV class=copy><!--版权所有&nbsp;&nbsp;  
&copy;2009-2010--></DIV>
</DIV></DIV></DIV></DIV>

</body>
</html>