function startUpdate() {
	// alert("data");
	server.startUpdate(function(data) {

		if(data==0)
		   alert("启动更新服务成功!");
		else if(data ==1)
			alert("更新服务已经启动");
		else 
			alert("更新服务启动失败！");
	});
}

function deleteUnuse() {
	server.deleteUnuse(function(data) {
		alert(data);
	});
}

function deleteUnuseContentbuffer() {
	var days=0;
	try{
		if(isNaN(document.getElementById("days").value)==true){
			alert("请输入一个整数！");
			return;
		}
		days=parseInt(document.getElementById("days").value);
	}catch(e){
		alert(e);
		return;
	}
	server.deleteUnuseContentbuffer(days,function(data) {
		alert(data);
	});
}

function startCheckMonitorTypeManager() {
	
	server.startCheckMonitorTypeManager(function(data) {

		if(data==0)
		   alert("启动定义服务成功!");
		else if(data ==1)
			alert("定义服务已经启动");
		else 
			alert("定义服务启动失败！");
	});
}
function startEmailSender() {
	server.startEmailSender(function(data) {
		if(data==0)
			   alert("启动邮件服务成功!");
			else if(data ==1)
				alert("邮件服务已经启动");
			else 
				alert("邮件服务启动失败！");
	});
}

function startMessageSender() {
	server.startMessageSender(function(data) {
		if(data==0)
			   alert("启动短信服务成功!");
			else if(data ==1)
				alert("短信服务已经启动");
			else 
				alert("短信服务启动失败！");
	});
}

function stopUpdate() {
	server.stopUpdate(function(data) {

		if(data==0)
			   alert("关闭更新服务成功!");
			else if(data ==1)
				alert("更新服务已经关闭");
			else 
				alert("关闭失败！");
	});
}
function stopCheckMonitorTypeManager(){ 
	server.stopCheckMonitorTypeManager(function(data) {

		if(data==0)
			   alert("关闭定义服务成功!");
			else if(data ==1)
				alert("定义服务已经关闭");
			else 
				alert("定义服务关闭失败！");
	});
}


function stopEmailSender() {
	server.stopEmailSender(function(data) {
		if(data==0)
			   alert("关闭邮件服务成功!");
			else if(data ==1)
				alert("邮件服务已经关闭");
			else 
				alert("关闭失败！");
	});
}

function stopMessageSender() {
	server.stopMessageSender(function(data) {
		if(data==0)
			   alert("关闭短信服务成功!");
			else if(data ==1)
				alert("短信服务已经关闭");
			else 
				alert("关闭失败！");
	});
}

function modifyAModule(moduleId) {
	  window.open("/personalfocus/sysmanagement/showSource?moduleId="+moduleId,"_blank");
   window.open("/personalfocus/sysmanagement/modifyModulePath?moduleId="+moduleId,"_blank");
}

var RESETTIMES=0;
var DISABLEWEB=1;
function operate(type){
	var webids=document.getElementsByName("webIds");
	if(webids.length==0){
		return;
	}
	var ids=new Array();
	for(var i=0;i<webids.length;i++){
		if(webids[i].checked==true){
			ids.push(webids[i].value);
		}
	}
	if(ids.length>0){
		if(type==RESETTIMES){
			var choice=window.confirm("你确定要清零？");
			if(choice==false){
				return;
			}
	     server.resetTimes(ids,function(result){
	    	 alert(result);
	    	 window.location.reload();
	     });
		}else if(type=DISABLEWEB){
			var choice=window.confirm("你确定要标记选中的网址失效？");
			if(choice==false){
				return;
			}
			 server.disAbleWeb(ids,function(result){
		    	 alert(result);
		    	 window.location.reload();
		     });
		}
	}
}

function disAbleAModule(moduleId){
	var choice=window.confirm("你确定要标记此模块失效？");
	if(choice==false){
		return;
	}
	 server.disAbleAModule(moduleId,function(result){
    	 alert(result);
    	 window.location.reload();
     });
}
/**
 * 保存监控时间
 * @param monitorFrequency
 */
function saveMonitorFra(){
	var monitorFra=document.getElementById("monitorFra").value;
	 server.saveMonitorFra(monitorFra,function(result){
    	 alert(result);
    	 window.location.reload();
     });
}

/**
 * 根据页面给定的SQL取得显示结果
 * @param sql
 * @return
 */
function getQueryResultBySQL(sql){
	server.getQueryResultBySQL(sql,function(result){ 	 
   	     document.getElementById("result").innerHTML=result;
    });
}