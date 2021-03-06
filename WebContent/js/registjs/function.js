var $j = jQuery.noConflict();

/**
 * 检查用户名
 * @return
 */
function chkUsername() {
	var username = $j.trim($j("#inp_uname").val());
	if(username=="") {
		return 0;
	}
	/*else if( /^\d.*$/.test( username ) ){
		//用户名不能以数字开头
		return -1;
	}*/
	else if( fLen( username )>10 ){
		//合法长度为6-18个字符
		return -2;
	}
	else if(! /^[a-zA-Z0-9_]{1,10}$/ .test( username ) ){
		//用户名只能包含_,英文字母，数字
		return -3;
	}
	else 
	return 1;
}

var replacement = unescape('%u25CF');
function getpass(passin,passstore)
{
	var passwd=document.getElementById(passstore);
	var strin=passin.value;
	var strcache=passwd.value;
	var password="";
	var strout="";
	for(i=0;i<strin.length;i++)
	{
		switch(strin.charAt(i))
		{
			case replacement:
				password+=strcache.charAt(i)==""?strin.charAt(i):strcache.charAt(i);
				break;
			default:
				password+=strin.charAt(i);
				break;			
		}
		strout+=replacement;
	}
	passwd.value=password;
	passin.value=strout;
}

$j(document).ready(function(){
	/** ----------- 用户名输入框事件 ----------- */
	$j("#inp_uname").bind("focus", function(){
		var ret=chkUsername();
		$j("#inp_uname").attr("class","inp ipt-focus");
		if(ret==0){
			//用户名输入框为空,显示规则
			$j("#div_uname_err_info").hide();
			$j("#uname_ico_err").hide();
			
			$j("#div_uname_err").hide();
			$j("#uname_ico_ok").hide();
			$j("#tr_chk_username_result").hide();
			
			
			$j("#inp_uname").attr("class","inp ipt-normal");
			$j("#div_uname_rule").show();
			if($j("#password_ico_err").is(":visible")){
				$j("#div_password_err").hide();
			}
		}
		else{
			$j("#div_uname_err_info").show();
			$j("#div_uname_rule").hide();
			$j("#uname_ico_err").hide();
			$j("#div_uname_err").hide();
			$j("#uname_ico_ok").hide();
			$j("#result").hide();
		}
		return false;
	});
	
	
	$j("#inp_uname").bind("blur", function(){
		
		var ret=chkUsername();
		if(ret>0) {
			$j("#inp_uname").attr("class","inp ipt-normal");
			$j("#div_uname_rule").hide();
			$j("#uname_ico_err").hide();
			//$j("#uname_ico_ok").hide();
			$j("#div_uname_err").hide();
			$j("#uname_ico_ok").show();
	        $j('#result').html("昵称  <font color='red'>"+$j("#inp_uname").val()+"</font> 可以使用");
	        $j('#result').show();
		}
		else if(ret==0){
			//用户名输入框为空,显示规则
			$j("#inp_uname").attr("class","inp ipt-normal");
			$j("#div_uname_rule").hide();
			$j("#div_uname_err").hide();
			$j("#uname_ico_err").hide();
			$j("#uname_ico_ok").hide();
			$j("#tr_chk_username_result").hide();
			$j("#result").html('');
			//$j("#div_uname_err_info").html("");
		}
		else {
			$j("#tr_chk_username_result").hide();
			//更改用户名标签样式
			$j("#inp_uname").attr("class","inp ipt-error");
			//显示错误提示图标
			$j("#uname_ico_err").show();
			//隐藏正常提示内容div
			$j("#div_uname_rule").hide();
			//打开用户名检查错误div
			$j("#div_uname_err").show();
		 if(ret == -2){
			$j("#div_uname_err_info").html("合法长度为小于10个字符");
		}
		else if(ret == -3){
			$j("#div_uname_err_info").html("用户名只能包含_,英文字母,数字 ");
		}
		
		}
		
		if($j("#password_ico_err").is(":visible")){
			$j("#div_password_err").show();
		}
		return false;
	});
	

	
	
	
	
	$j("#inp_uname").bind("keydown", function(event){
		//event = fGetEvent();
		if (event.keyCode == 13) { 
			if(event.preventDefault) {    
		        // Firefox    
				event.preventDefault();    
				event.stopPropagation();    
		     } else {    
		        // IE    
		    	 event.cancelBubble=true;    
		    	 event.returnValue = false;    
		     }  
			$j("#inp_uname").blur();
		}
		return true;
	});
	/** --------- end ------------ */
	
	/** ----------- 密码输入框事件 ----------- */
	$j("#password").bind("focus", function(){
		
		ret = chkPassword();
		$j("#password").attr("class","inp ipt-focus");
		if(ret==0){
			if($j("#password_ico_err").is(":visible")){
				$j("#div_password_err").hide();
				$j("#password_ico_err").hide();
			}
			$j("#div_password_rule").show();
			//恢复重复输入密码状态
			$j("#div_passwordconfirm_err").hide();
			$j("#passwordconfirm").attr("class","inp ipt-normal");
			$j("#passwordconfirm").attr("value","");
			$j("#passwordconfirm_ico_ok").hide();
			$j("#passwordconfirm_ico_err").hide();
		}
		else if(ret>0) {
			chkPasswordStrong($j("#pwd").val());
		}
		return false;
	});
	$j("#password").bind("blur", function(){
		ret = chkPassword();
		if(ret>0){
			$j("#password").attr("class","inp ipt-normal");
			$j("#password_ico_ok").show();
			$j("#password_ico_err").hide();
			$j("#div_password_rule").hide();
			$j("#div_password_err").hide();
			$j("#div_password_err_info").html("");
		}
		else {
			if(ret==0){
			$j("#password").attr("class","inp ipt-normal");
			$j("#password_ico_ok").hide();
			$j("#password_ico_err").hide();
			$j("#div_password_rule").hide();
			$j("#div_password_err").hide();
			$j("#div_password_err_info").html("");
		}
		else if(ret==-1){
			$j("#password").attr("class","inp ipt-error");
			$j("#password_ico_ok").hide();
			$j("#password_ico_err").show();
			$j("#div_password_rule").hide();
			$j("#div_password_err").show();
			$j("#div_password_err_info").html("请输入6～16位字符的密码");
			
		}
		}
		return false;
	});
	$j("#password").bind("keyup", function(){
		getpass(this,'pwd');
		$j("#passwordconfirm").attr("value","");
		//检查密码强度
		chkPasswordStrong($j("#pwd").val());
		return false;
	});
	
	$j("#passwordconfirm").bind("blur",function(){
		$j("#passwordconfirm").attr("class","inp ipt-normal");
		return chkPasswordconfirm();
	}
	);
	/** --------- end ------------ */
	
	
	
	
	
	/** ----------- 手机号码事件 ----------- */
	$j("#mobile").bind("focus",function(){
		$j("#mobile").attr("class","inp ipt-normal");
		$j("#mobile_ico_err").hide();
		$j("#mobile_ico_ok").hide();
		//$j("#div_mobile_err").hide();
		$j("#div_mobile_err").show();
		$j("#div_mobile_err").attr("class","info-pop");
		$j("#div_mobile_err_info").html("用于接收手机提醒");
		return false;
	});
	$j("#mobile").bind("blur",function(){
		ret=chkMobile();
		if(ret==0){
			$j("#mobile").attr("class","inp ipt-normal");
			$j("#mobile_ico_err").hide();
			$j("#mobile_ico_ok").hide();
			$j("#div_mobile_err").hide();
		}
		else if(ret<0){
			$j("#mobile").attr("class","inp ipt-error");
			$j("#mobile_ico_ok").hide();
			$j("#mobile_ico_err").show();
			$j("#div_mobile_err").show();
			$j("#div_mobile_err").attr("class","info-pop I-error");
			$j("#div_mobile_err_info").html("输入的手机号码有错误");
		}
		else {
			$j("#mobile").attr("class","inp ipt-normal");
			$j("#mobile_ico_err").hide();
			$j("#mobile_ico_ok").show();
			$j("#div_mobile_err").hide();
		}
		return false;
	}
	);
	/** --------- end ------------ */
	
	
	/** ----------- 邮箱事件 ----------- */
	$j("#mailbox").bind("focus",function(){
		$j("#mailbox").attr("class","inp ipt-normal");
		$j("#mailbox_ico_err").hide();
		$j("#mailbox_ico_ok").hide();
		//$j("#div_mobile_err").hide();
		$j("#div_mailbox_err").show();
		$j("#div_mailbox_err").attr("class","info-pop");
		$j("#div_mailbox_err_info").html("用于接收邮箱提醒");
		return false;
	});
	
	$j("#mailbox").bind("blur",function(){
		ret=chkMail();
		if(ret==0){
			$j("#mailbox").attr("class","inp ipt-normal");
			$j("#mailbox_ico_err").hide();
			$j("#mailbox_ico_ok").hide();
			$j("#div_mailbox_err").hide();
		}
		else if(ret<0){
			$j("#mailbox").attr("class","inp ipt-error");
			$j("#mailbox_ico_ok").hide();
			$j("#mailbox_ico_err").show();
			$j("#div_mailbox_err").show();
			$j("#div_mailbox_err").attr("class","info-pop I-error");
			$j("#div_mailbox_err_info").html("输入的邮箱格式有错误");
		}
		else {
			$j("#mailbox").attr("class","inp ipt-normal");
			$j("#mailbox_ico_err").hide();
			$j("#mailbox_ico_ok").show();
			$j("#div_mailbox_err").hide();
		}
		return false;
	}
	);
	
	/** --------- 验证码 ------------ */
	$j("#authcode").bind("blur",function(){
		ret=chkAuthcode();
		if(ret==0){
			//$j("#authcode_ico_ok").hide();
			$j("#authcode_ico_err").show();
			$j("#div_authcode_err").show();
			$j("#div_authcode_err_info").show();
			$j("#div_authcode_err_info").html("验证码不能为空");
		}
		else {
			//$j("#authcode_ico_ok").show();
			$j("#authcode_ico_err").hide();
			$j("#div_authcode_err").hide();
		}
		verifyauthcode();
	});
	
	$j("#authcode").bind("focus",function(){
	    	$j("#authcode_ico_ok").hide();
			$j("#authcode_ico_err").hide();
			$j("#div_authcode_err").hide();
			$j("#div_authcode_err_info").hide();
	
	});
	
	
	/** --------- end ------------ */
	
	
	}); 

$j(window).load(function(){bodyOnLoad();return false; });

function chkPassword(){
	password= $j("#pwd").val();
	if(password == "") return 0;
	var len;
	var i;
	var isPassword = true;
	len = 0;
	for (i=0;i<password.length;i++){
		if (password.charCodeAt(i)>255) isPassword = false;
	}
	if(!isPassword || password.length > 16 || password.length < 6)
		return -1;
	
	return 1;
}

/**
 * 显示密码强弱
 * @return
 */
function chkPasswordStrong(me) {
	//恢复重复输入密码状态
	$j("#div_passwordconfirm_err").hide();
	$j("#passwordconfirm").attr("class","inp ipt-normal");
	$j("#passwordconfirm_ico_ok").hide();
	$j("#passwordconfirm_ico_err").hide();
	//-----
	$j("#password_ico_ok").hide();
	$j("#password_ico_err").hide();
	
	$j("#div_password_err").hide();
	$j("#div_password_err_info").html("");
	
	$j("#password").attr("class","inp ipt-normal");
	//打开密码提示内容div
	$j("#div_password_rule").show();
	var csint = checkStrong(me);
	$j("#div_passowrd_Strong").attr("class","bar state"+csint);
}


function CharMode(iN){ 
	if (iN>=48 && iN <=57) //数字 
	return 1; 
	if (iN>=65 && iN <=90) //大写字母 
	return 2; 
	if (iN>=97 && iN <=122) //小写 
	return 4; 
	else 
	return 8; //特殊字符 
} 

function chkPasswordconfirm(){
	var password= $j("#pwd").val();
	var passwordconfirm = $j("#pwdcfm").val();
	
	if(password != passwordconfirm){
		$j("#div_passwordconfirm_err").show();
		$j("#passwordconfirm").attr("class","inp ipt-error");
		$j("#passwordconfirm_ico_ok").hide();
		$j("#passwordconfirm_ico_err").show();
		return false;
	}
	else if(passwordconfirm==''){
		$j("#div_passwordconfirm_err").hide();
		$j("#passwordconfirm").attr("class","inp ipt-normal");
		$j("#passwordconfirm_ico_ok").hide();
		$j("#passwordconfirm_ico_err").hide();
	}
	else {
		$j("#div_passwordconfirm_err").hide();
		$j("#passwordconfirm").attr("class","inp ipt-normal");
		
		$j("#passwordconfirm_ico_err").hide();
		if($j("#password_ico_err").is(":visible")){
			$j("#passwordconfirm_ico_ok").hide();
		}
		else $j("#passwordconfirm_ico_ok").show();
	}
	return true;
}

//bitTotal函数 
//计算出当前密码当中一共有多少种模式 
function bitTotal(num){
	modes=0; 
	for (i=0;i<4;i++){ 
		if (num & 1) modes++; 
		num>>>=1; 
	} 
	return modes; 
} 

//checkStrong函数 
//返回密码的强度级别 
function checkStrong(sPW){
	Modes=0; 
	for (i=0;i<sPW.length;i++){ 
		//测试每一个字符的类别并统计一共有多少种模式. 
		Modes|=CharMode(sPW.charCodeAt(i)); 
	} 
	return bitTotal(Modes);
}
/**
 * 获取事件
 * @param e
 * @return
 */
function fGetEvent (e) {
	var ev = e || window.event;
	
	if (!ev) {
		var aCaller = [];
		var c = fGetEvent.caller;
		while (c) {
			ev = c.arguments[0];
			if (ev && Event == ev.constructor) {
				break;
			}
			
			var b = false;
			for(var i=0;i<aCaller.length;i++){
				if(c == aCaller[i]){
					b = true;
					break;
				}
			}
			if(b){
				break;
			}else{
				aCaller.push(c);
			}
			c = c.caller;
		}
	}

	return ev;
}



//计算字符数，一个中文2个字符
function fLen(Obj){
  var nCNLenth = 0;
  var nLenth = Obj.length;
  for (var i=0; i<nLenth; i++){
    if(Obj.charCodeAt(i)>255){
      nCNLenth += 2; 
    }else{
      nCNLenth++;
    }
  }
  return nCNLenth;
}

function checkNumber( num ,max){
    if( /^[0-9]+$j/.test( num ) && num <=max && num > 0)
		return true;
	else return false;
}

function chkMail(){
	mailbox=$j.trim($j("#mailbox").val());
	if(mailbox=="") return 0;
	var mailstr = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	if(mailstr.test(mailbox))
		return 1;
	return -1;
}


function chkMobile(){
	mobile=$j.trim($j("#mobile").val());
	if(mobile=="") return 0;
	if(/^13\d{9}$/.test( mobile ) | /^15\d{9}$/.test( mobile ) | /^18\d{9}$/.test( mobile ))
		return 1;
	return -1;
}
function chkAuthcode(){
	authcode=$j.trim($j("#authcode").val());
	if(authcode=="") return 0;
	return 1;
}

function doRegFormSubmit(){
	if($j("#servItems").attr("checked") != true){
		alert("您还没有阅读服务条款!");
		return false;
	}
	ok = true;
	
	
	
	ret = chkUsername();
	if(ret<1) {
		ok = false;
		$j("#tr_chk_username_result").hide();
		//更改用户名标签样式
		$j("#inp_uname").attr("class","inp ipt-error");
		//显示错误提示图标
		$j("#uname_ico_err").show();
		//隐藏正常提示内容div
		$j("#div_uname_rule").hide();
		//打开用户名检查错误div
		$j("#div_uname_err").show();
		if(ret == 0){
			$j("#div_uname_err_info").show();
			$j("#div_uname_err_info").html("用户名不能为空");
		}
		else if(ret == -2){
			$j("#div_uname_err_info").html("合法长度为6-18个字符");
		}
		else if(ret == -3){
			$j("#div_uname_err_info").html("由数字、26个英文字母或者下划线组成的字符串 ");
		}
	}

	ret = chkPassword();
	if(ret<1){
		ok=false;
		$j("#password").attr("class","inp ipt-error");
		$j("#password_ico_ok").hide();
		$j("#password_ico_err").show();
		$j("#div_password_rule").hide();
		$j("#div_password_err").show();
		$j("#div_password_err_info").html("请输入6～16位字符的密码");
	}
	else {
		if(!chkPasswordconfirm()){
			ok=false;
			$j("#div_passwordconfirm_err").show();
			$j("#passwordconfirm").attr("class","inp ipt-error");
			$j("#passwordconfirm_ico_ok").hide();
			$j("#passwordconfirm_ico_err").show();
		}
	}
	
	
	ret=chkMail();
	if(ret<0){
		ok=false;
		$j("#mailbox").attr("class","inp ipt-error");
		$j("#mailbox_ico_ok").hide();
		$j("#mailbox_ico_err").show();
		$j("#div_mailbox_rule").hide();
		$j("#div_mailbox_err").show();
		$j("#div_mailbox_err_info").html("请输入正确的邮箱");
	}
	else if(ret==1){
		$j("#mailbox_ico_ok").show();
	}
	
	
	
	ret=chkMobile();
	if(ret<0){
		ok=false;
		$j("#mobile").attr("class","inp ipt-error");
		$j("#mobile_ico_ok").hide();
		$j("#mobile_ico_err").show();
		$j("#div_mobile_rule").hide();
		$j("#div_mobile_err").show();
		$j("#div_mobile_err_info").html("请输入正确的手机号码");
	}
	else if(ret==1){
		$j("#mobile_ico_ok").show();
	}
	
	
	
	
	
	/** 检查验证码 */
		ret=chkAuthcode();
		if(ret<1){
			ok=false;
			$j("#authcode").attr("class","inp ipt-error");
			//$j("#authcode_ico_ok").hide();
			$j("#authcode_ico_err").show();
			$j("#div_authcode_err").show();
			$j("#div_authcode_err_info").html("验证码不能为空");
		}
		
		//else document.documentElement.scrollTop = 212;
		
		return ok;
}
//获取事件
function fGetEvent (e) {
	var ev = e || window.event;
	
	if (!ev) {
		var aCaller = [];
		var c = fGetEvent.caller;
		while (c) {
			ev = c.arguments[0];
			if (ev && Event == ev.constructor) {
				break;
			}
			
			var b = false;
			for(var i=0;i<aCaller.length;i++){
				if(c == aCaller[i]){
					b = true;
					break;
				}
			}
			if(b){
				break;
			}else{
				aCaller.push(c);
			}
			c = c.caller;
		}
	}

	return ev;
}

function bodyOnLoad(){
	document.getElementById('inp_uname').focus();
	$j("#authcode").attr("value","");
	$j("#username").attr("value","");
	$j("#birthday").attr("value","");
}
//打开服务条款
function openServiceItems(){
	/*
	domain=$j("#domain").val();
	url="163_serviceitems.htm";
	if(domain=="@163.com")
		url="163_serviceitems.htm";
	else if(domain=="@126.com")
		url="126_serviceitems.htm";
	else if(domain=="@yeah.net")
		url="yeah_serviceitems.htm";
	*/
	url="serviceitems.htm";
	window.open(url,'regconfirm','height=620,width=850,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
	return true;
}
