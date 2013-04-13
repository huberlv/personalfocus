<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>互联网关注订阅平台</title>
<link href="/personalfocus/css/registcss/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="/personalfocus/css/registcss/date_input.css">

<SCRIPT type=text/javascript src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
<script type="text/javascript" src="/personalfocus/js/registjs/jquery.date_input.min.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/jquery_ajax.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/requestUtil.js"></script>
<script type="text/javascript" src="/personalfocus/js/registjs/function.js"></script>
<SCRIPT type=text/javascript src="/personalfocus/js/registjs/verify1.js"></SCRIPT>
 	<script type='text/javascript' src='/personalfocus/dwr/engine.js'></script>
  	<script type='text/javascript' src='/personalfocus/dwr/util.js'></script>
    <script type='text/javascript' src='/personalfocus/dwr/interface/verifyauthcode.js'></script>
    
      <script type="text/javascript">
   
	$j(function() { 
		$j("#birthday").date_input(); 
		}); 
    </script>
  	<script type="text/javascript">   
    function changeValidateCode(obj) {   
           //获取当前的时间作为参数，无具体意义   
        var timenow = new Date().getTime();   
           //每次请求需要一个不同的参数，否则可能会返回同样的验证码   
        //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
        obj.src="rand.action?d="+timenow;   
    }   
</script>     <!--  验证码-->
</head>


<body>
<div class="header"> 
	<div class="logo_w" ></div>
    
</div>

<div class="content">
  <div class="mhd">
    <div class="tit">欢迎注册互联网关注订阅平台</div>
  </div>
  <div class="mcont">
        
<div id="sysmessage" class="err-info" style="display:none"></div>
<s:form  onsubmit="return doRegFormSubmit()" action="doregist"  namespace="/index" >
        <div class="main-cont">
			<div class="main-cont-tit"><div class="arr"></div><h2>用户昵称和密码</h2></div>
		
            <table class="cont-tab" cellspacing="0" cellpadding="0" style="table-layout:fixed">
<!--用户名的处理 Start-->
<tr id="tr_input_username">
    <td class="td1">昵称：<span class="nes">*</span></td>
    <td class="td2">
		<div class="fle">
			<s:textfield name="inp_uname" id="inp_uname"  cssClass="inp ipt-normal" cssStyle=" font-weight:bold"  maxlength="18"></s:textfield>
		</div>
		<!--<input type="button" id="btn_chk" class="btn-jc fri"  value="检测" onclick="verifyusername()"/>-->
	</td>
    <td class="td3">
        <div class="info">
        	<b id="uname_ico_ok" class="ico-ok" title="正确" style="display:none"></b>
        	<b id="uname_ico_err" class="ico-error" title="错误" style="display:none"></b>
            <div id="result" class="cont"  style="margin-left:35px"></div>
            <!--正常提示 Start-->
            <div id="div_uname_rule" class="info-pop" style="display:">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div class="cont">
						·5~10个字符，包括字母、数字、下划线<br>
						·字母开头，字母和数字结尾，不区分大小写<br>
					</div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--正常提示 End-->
            
            <!--错误提示(下面的div增加了一个“I-error”调用) Start-->
            <div id="div_uname_err" class="info-pop I-error" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div class="cont" id="div_uname_err_info">该用户名已被占用</div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--错误提示 End-->
        </div>    </td>
  </tr>
<!--用户名的处理 Ent-->
<tr>
    <td class="td1">密　码：<span class="nes">*</span></td>
    <input name="password" type="hidden" value="" id="pwd"/>
    
    <td class="td2"><s:textfield  name="password"  id="password" cssClass="inp ipt-normal" maxlength="16"  ></s:textfield><!--输入框3种状态 “ipt-normal”-正常，“ipt-focus”-获取焦点，“ipt-error”-正常， --></td>
    <td class="td3">
        <div class="info">
        	<b id="password_ico_ok" class="ico-ok" title="正确" style="display:none"></b>
        	<b id="password_ico_err" class="ico-error" title="错误" style="display:none"></b>
            
            <!--正常提示 Start-->
            <div id="div_password_rule" class="info-pop" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div class="cont">6～16个字符（字母、数字、特殊符号）,区分大小写<br>
					<div class="fle">密码强度：<span class="Cred">弱</span></div>
                    
                    <div class="psw-sinfo">
                    	<div id="div_passowrd_Strong" class="bar state0"><!-- 此div中 class state0 至 state4 依次从弱至强 --></div>
                    </div>
                    
                    <span class="fle Cblue">强</span>
					
                    <div class="clear"></div>
                </div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--正常提示 End-->
			<!--错误提示(下面的div增加了一个“I-error”调用) Start-->
            <div id="div_password_err" class="info-pop I-error" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div class="cont" id="div_password_err_info"></div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--错误提示 End-->
        </div>    </td>
  </tr>
  <tr>
    <td class="td1">再次输入密码：<span class="nes">*</span></td>
    <input name="passwordconfirm" type="hidden" value="" id="pwdcfm">
    <td class="td2">
    <s:textfield id="passwordconfirm" cssClass="inp ipt-normal"  onFocus="this.className='inp ipt-focus'" onKeyUp="getpass(this, 'pwdcfm');" maxlength="16" value=""></s:textfield>
    </td>
    <td class="td3">
        <div class="info">
			<b id="passwordconfirm_ico_ok" class="ico-ok" title="正确" style="display:none"></b>
        	<b id="passwordconfirm_ico_err" class="ico-error" title="错误" style="display:none"></b>
            <!--错误提示(下面的div增加了一个“I-error”调用) Start-->
            <div id="div_passwordconfirm_err" class="info-pop I-error" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div class="cont" id="div_passwordconfirm_err_info">两次输入密码不一致</div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--错误提示 End-->
        </div>    </td>
  </tr>
</table>
			<div class="main-cont-tit"><div class="arr"></div>
			<h2>个人信息</h2> 
			</div>
		
            <table class="cont-tab" cellspacing="0" cellpadding="0" style="table-layout:fixed">
  <tr>
     <td class="td1">邮箱：<span class="nes"></span><span class="unnes"></span></td>
    <td class="td2">
		<input type="text" name="mailbox" id="mailbox" class="inp ipt-normal"  maxlength="30" /><!--输入框3种状态 “ipt-normal”-正常，“ipt-focus”-获取焦点，“ipt-error”-正常， -->
		</td>
    <td class="td3">
        <div class="info">
          <b id="mailbox_ico_ok" class="ico-ok" title="正确" style="display:none"></b>
        	<b id="mailbox_ico_err" class="ico-error" title="错误" style="display:none"></b>
            
            <!--正常提示 Start-->
            <div id="div_mailbox_rule" class="info-pop" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div class="cont">4～30个字符,字母区分大小写,一个汉字占两个字符.</div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--正常提示 End-->
            
            <!--错误提示(下面的div增加了一个“I-error”调用) Start-->
            <div id="div_mailbox_err" class="info-pop I-error" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div id="div_mailbox_err_info" class="cont"></div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--错误提示 End-->              
        </div>
    </td>
  </tr>
  <tr>
    <td class="td1">手机号：<span class="nes"></span><span class="unnes"></span></td>
    <td class="td2"><s:textfield name="mobile" id="mobile"   cssClass="inp ipt-normal" onfocus="this.className='inp ipt-focus'" maxlength="16"></s:textfield></td>
    <td class="td3">
        <div class="info">
			<b id="mobile_ico_ok" class="ico-ok" title="正确" style="display:none"></b>
        	<b id="mobile_ico_err" class="ico-error" title="错误" style="display:none"></b> 
			 <!--错误提示(下面的div增加了一个“I-error”调用) Start-->
            <div id="div_mobile_err" class="info-pop I-error" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div id="div_mobile_err_info" class="cont"></div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--错误提示 End-->   
        </div>    </td>
  </tr>
  
  
  <TR>
    <TD class=td1>性　别：<SPAN class=nes></SPAN></TD>
    <TD style="FONT-SIZE: 14px" class=td2>
    <s:radio list="#{'1':'男','2':'女'}" value="1" onclick="$('#div_gender_err').hide()" name="gender" id="rd"></s:radio>
   </TD>
   </TR>
  <TR>
   <TR>
    <TD class=td1>出生日期：<SPAN class=nes></SPAN></TD>
    <td class="td2"><s:textfield  name="birthday" id="birthday"   readonly="true"  cssClass="inp ipt-normal"  maxlength="16"></s:textfield></td>
    <td class="td3">
    </td>
    
    </TR>
  <TR>
</table>
			<div class="main-cont-tit"><div class="arr"></div><h2>注册验证</h2></div>
		
            <table class="cont-tab" cellspacing="0" cellpadding="0" style="table-layout:fixed">
  <tr>
    <td class="td1">&nbsp;</td>
    <td class="td2 codeImg"><img src="rand.action" onclick="changeValidateCode(this)" style="cursor: pointer"/> 看不清楚，点击图片换一张</td>
    <td class="td3">&nbsp;</td>
  </tr>
  <tr>
    <td class="td1">请输入验证码：<span class="nes">*</span></td>
    <td class="td2"><s:textfield name="authcode" id="authcode" cssClass="inp ipt-normal" onfocus="this.className='inp ipt-focus'" onBlur="this.className='inp ipt-normal'" maxlength="4"></s:textfield>
		</td>
    <td class="td3">
        <div class="info">
           <b id="authcode_ico_ok" class="ico-ok" title="正确" style="display:none"></b>
        	<b id="authcode_ico_err" class="ico-error" title="错误" style="display:none"></b> 
			 <!--错误提示(下面的div增加了一个“I-error”调用) Start-->
            <div id="div_authcode_err" class="info-pop I-error" style="display:none">
            	<div class="arr"></div>
            	<div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div>
            	<div class="info-pop-c">
                	<div id="div_authcode_err_info" class="cont"></div>
                </div>
            	<div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div>
            </div>
            <!--错误提示 End-->                
        </div>
    </td>
  </tr>
</table>

			
			<table class="cont-tab" cellspacing="0" cellpadding="0" style="table-layout:fixed">
 
  <tr>
    <td class="td1">&nbsp;</td>
    <td class="td2">
   <div id="btn">
     <div style='display:none'><input type="checkbox" name="servItems" id="servItems" checked="true" >  </div>     
    <s:submit id="submit" cssClass="btn-submit"   onFocus="this.className='btn-submit-act'" onBlur="this.className='btn-submit'" onClick="this.blur(); "  value="" ></s:submit>
   </div>
		</td>
    <td class="td3">&nbsp;</td>
  </tr>
</table>
            
        </div>
</s:form>
    	<!-- 分拆补充资料部分 End -->

    
    
  </div>
    <div class="mft"></div>
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