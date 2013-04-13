var $olpfjQuery=jQuery.noConflict();

/**
 * 更多设置改变函数
 * 
 * @return
 */
function checkConfig() {
	
	lessChoice = $olpfjQuery("#config").attr("checked");
	var olpfMoreChoiceDiv=$olpfjQuery("#olpfMoreChoiceDiv");
	if (lessChoice == true) {
		olpfMoreChoiceDiv.hide();
	} else {
		olpfMoreChoiceDiv.show('fast');
	}
}
function saveAmodule(){
	    document.getElementById("saveAmodule").disabled=true;
        var modulename = $olpfjQuery("#modulename").attr("value");
        if(modulename.length<1){
        	alert("请输入栏目名");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
        var saveConfig =false;
        var isMobile = $olpfjQuery("#isMobile").attr("checked") == true ? 1 : 0;
        var isMail = $olpfjQuery("#isMail").attr("checked");
        var olpfTitle =  $olpfjQuery("#webname").attr("value");
        if(olpfTitle.length<1){
        	alert("请输入网站名");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
        var messageType = 4;//
        var messageStartTime = $olpfjQuery("#messageStartTime").attr("value");
        var messageStopTime = $olpfjQuery("#messageStopTime").attr("value");
        var messageFrequency = $olpfjQuery("#mobilefrequency").attr("value");
        var messageFrequencyType = $olpfjQuery("#mobileUnit").attr("value");
        var messageMaxNum = $olpfjQuery("#sendMaxCount").attr("value");
        var emailStartTime = $olpfjQuery("#mailStartTime").attr("value");
        var emailStopTime = $olpfjQuery("#mailStopTime").attr("value");
        var emailFrequency = $olpfjQuery("#mailFrequency").attr("value");
        var emailFrequencyType = $olpfjQuery("#mailUnit").attr("value");
        var monitorType = parseInt($olpfjQuery("#monitorType").val())|parseInt($olpfjQuery("#monitorType2").val());
        var subgroupId = document.getElementById("subgroup").value;
        var useDefaultConfig = $olpfjQuery("#config").attr("checked");
        var editpath=$olpfjQuery("#path").attr("value");
        if(editpath.length<1){
        	alert("请输入特征！");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
        try{
        	$olpfjQuery(editpath);
        }catch(e){
        	alert("特征不合法，请重新输入！");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
        var olpfsUrl=$olpfjQuery("#url").attr("value");
        if(olpfsUrl.length<1){
        	alert("请输入网址");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
        var olpfCSSAndCsript=new Array();
        var olpftexts="";
        var olpfsMyStyle="";
		var theight=$olpfjQuery("#theight").attr("value");
		if(theight.length<1){
        	alert("请输入高度");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
		var twidth=$olpfjQuery("#twidth").attr("value");
		if(twidth.length<1){
        	alert("请输入高宽度");
        	document.getElementById("saveAmodule").disabled = false;
        	return;
        }
        addModuleInfoByManual.addAModule(olpfsUrl, olpfCSSAndCsript, olpfTitle,
			modulename, olpftexts, isMobile, isMail, messageType,
			messageStartTime, messageStopTime, messageFrequency,
			messageFrequencyType, messageMaxNum, emailStartTime, emailStopTime,
			emailFrequency, emailFrequencyType, editpath, saveConfig,
			useDefaultConfig, olpfsMyStyle, monitorType, subgroupId,theight,twidth, function(
					data) {
				alert(data);
				document.getElementById("saveAmodule").disabled = false;
			});
}