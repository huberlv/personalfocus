var divIds = new Array();
var iframess = new Array();
var olpfBrower = "";
function addIframe(twindow)
{
	iframess.push(twindow);
}


/**
 * 解除元素绑定的方法
 * 
 * @param e
 * @return
 */
function unBind(e) {
	try {
		e.unbind();
		e.children().each(function(i) {
			unBind(jQuery(this));
		});
	} catch (q) {
		myAlert(q);
	}
}

/**
 * 释放内存
 */
function olpftooltipsFree(e) {
	try {
		unBind(jQuery('body'));
		//遍历window的对象，置为空
		for (elm in window) {
			if (typeof (window[elm]) == 'object') {
				//myAlert(window[elm]);
				window[elm] = null;
			}
		}
	} catch (e) {
		jQuery('body').append(e.message);
	}
}

/**
 * 对每个模块增加高度
 * 
 * @return
 */
function editHeight() {

	jQuery("div[class='main-cont']").each(function(e) {
		var cdiv = jQuery(this);
		var height = 0;
		// myAlert(cdiv.outerHeight());
			cdiv.find("div[class='main-cont-tit']").each(function(g) {
				height = height + jQuery(this).outerHeight();
			});
			cdiv.height(cdiv.outerHeight() + height + "px");
			// cdiv.height(cdiv.outerHeight()+height+10+"px");
			// myAlert(cdiv.outerHeight());
		});
}

jQuery(document).ready(function() {

	var brower = navigator.userAgent.toLowerCase();
	if (brower.indexOf("firefox") >= 0) {
		olpfBrower = "f";
	} else if (brower.indexOf("opera") >= 0) {
		olpfBrower = "op";
	} else {
		olpfBrower = "ie";
	}
	//jQuery("#login").show(1);
	//	if (olpfBrower == "f") {//在火狐中改高度，其余不改
	displayOther();
			editHeight();
	//	}
		jQuery("#login").css( {
			position:'absolute',
			left : document.body.clientWidth / 2 - jQuery("#login").width() / 2,
			top : '100px',
			opacity : 0.8
		});
		compositionOther();
	});

/**
 * 设置在新窗口打开链接
 * @param twindow
 * @return
 */
function changeLinkTarget(twindow) {
	jQuery(twindow.document.body).find("a").each(function(h) {
		this.target = "_blank";
	});
}

/**
 * 释放内存
 */
function olpftooltipsFree() {
	try {
		unBind(jQuery('body'));
		//遍历window的对象，置为空
		for (elm in window) {
			if (typeof (window[elm]) == 'object') {
				//myAlert(window[elm]);
				window[elm] = null;
			}
		}
	} catch (e) {
		jQuery('body').append(e.message);
	}
}
/**
 * 显示登录窗口或将
 * @return
 */
function showLogin() {
	if(jQuery("#loginText").text()=="退出"){
		jQuery("#loginText").text("登录");
		jQuery("#userName").text("游客");
		userId="";
		return false;
	}
	jQuery("#ke-dialog").show('fast');
}

function hideLogin() {
	jQuery(".ke-dialog").hide('fast');
}

function checkLogin() {
	
	if (jQuery("#userId").attr("value") == ""
			|| jQuery("#password").attr("value") == "") {
		alert("用户名或密码不能为空！");
	} else {
		userId=jQuery("#userId").attr("value");
		if(userId==masterUserId){
			alert("你是空间主人，请登录你的空间！");
			window.open('/personalfocus/index/showindex','_self');
			return;
		}
		userSpace.checkLogin(jQuery("#userId").attr("value"), jQuery(
				"#password").attr("value"), function(e) {
			if (e != "fail") {			
				jQuery("#userName").text(e);
				jQuery("#loginText").text("退出");
				window.hideLogin();
				alert("登录成功");
			
			} else {
				alert("用户名或密码错误，请重新输入！");
			}
		});
	}
}

var developModule=true;
/**
 * 错误信息处理
 * @param e
 * @param otherMessage
 * @return
 */
function myAlert(e,otherMessage){
	
	if(developModule==false){
		return;
	}
	var fmessage="description:"+(typeof e.description=='undefined'?"":e.description)
			+ "\nfileName:"+(typeof e.fileName=='undefined'?"":e.fileName)
			+ "\nlineNumber:"+(typeof e.lineNumber=='undefined'?"":e.lineNumber)
			+ "\nmessage:"+(typeof e.message=='undefined'?"":e.message)
			+ "\nname:"+(typeof e.name=='undefined'?"":e.name)
			+ "\nnumber:"+(typeof e.number=='undefined'?"":e.number)
			+ "\nstack:"+(typeof e.stack=='undefined'?"":e.stack)
					+ "\n"+(typeof otherMessage=='undefined'?"":otherMessage);
	alert(fmessage);
}


function subscribeAllUserModule() {
	if (userId == "") {
		alert("请先登录");
		showLogin();
	} else {
		userSpace.subscribeAllUserModule(userId,masterUserId, function(e) {
			alert(e);
		});
	}
}
function subscribeUser() {
	if (userId == "") {
		alert("请先登录");
		showLogin();
	} else {
		userSpace.subscribeUser(userId,masterUserId, function(e) {
			alert(e);
		});
	}
}
/**
 * 全选当前显示的模块
 */
function selectAll()
{
	jQuery("#"+currentGroup).find("input[class='subscribe']").each(function(){
		jQuery(this).attr("checked",true);
	});
}
/**
 * 反选
 */
function inverse()
{
	jQuery("#"+currentGroup).find("input[class='subscribe']").each(function(){
		if(jQuery(this).attr("checked")==true){
			jQuery(this).attr("checked",false);
		}else{
			jQuery(this).attr("checked",true);
		}
		
	});
}
/**
 * 订阅选中模块
 */
function subscribeSelectUserModule()
{
    if (userId == "") {
		alert("请先登录");
		showLogin();
		return;
	}
	var userModuleIds=new Array();
	var have=false;
	jQuery("input:checked[class='subscribe']").each(function(){
		userModuleIds.push(this.value);
		have=true;
	});
	if(have==false){
		alert("请先选择模块！");
		return;
	}
	//alert(userModuleIds.length);
	userSpace.subscribeUserModules(userId,userModuleIds,function(e) {
			alert(e);
		});
}


function subscribeAllUserModule() {
	if (userId == "") {
		alert("请先登录");
		showLogin();
	} else {
		userSpace.subscribeAllUserModule(userId,masterUserId, function(e) {
			alert(e);
		});
	}
}


/**
 * 获取RSS FEED
 * @param rssFeed
 * @return
 */
function getRssFeed(rssFeedstr,divId,title) 
{ 
   
    title=encodeURIComponent(title);
    var rss=document.getElementById("ke-dialog2");		
	jQuery("#rssfeed").text(olpfBase+rssFeedstr+"&title="+title);
	rss.style.display="block";
	jQuery(rss).css({
		top:document.getElementById(divId).offsetTop,
		left:document.getElementById(divId).offsetLeft
	}).show('fast');	
	if(typeof clipboardData!='undefined'){
		document.getElementById("copyToClipboard").style.display='inline';

	}
} 

/**
 * 复制rss feed 到剪贴板
 */
function copyToClipboard(){
	clipboardData.clearData("Text");
	clipboardData.setData("Text",jQuery("#rssfeed").text());
	alert("已成功复制网址到剪贴板中！");
	hideKDialog();
	
}  
/**
 * 隐藏rss feed 层
 */
function hideKDialog(){
	document.getElementById('ke-dialog2').style.display='none';
}