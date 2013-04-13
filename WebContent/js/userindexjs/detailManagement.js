var $j = jQuery.noConflict();

/**
 * 更新用户模块信息
 * 
 * @param {Object}
 *            obj
 */
function update(obj) {
	var btn = $j(obj);
	var id = btn.attr("id");
	var userModuleId = id;
	var moduleName = DWRUtil.getValue("moduleName" + id); // dwr取值的方法
	//var webName = $("webName" + id).value;
	var isMobile = $("isMobile" + id).checked;
	var messageReceiveType =4;
	var messageStartTime = $("messageStartTime" + id).value;
	var messageStopTime = $("messageStopTime" + id).value;
	var sendMaxCount = $("sendMaxCount" + id).value;
	var mobilefrequency = $("mobilefrequency" + id).value;
	var mobileUnit = $("mobileUnit" + id).value;
	var isMail = $("isMail" + id).checked;
	var mailStartTime = $("mailStartTime" + id).value;
	var mailStopTime = $("mailStopTime" + id).value;
	var mailFrequency = $("mailFrequency" + id).value;
	var mailUnit = $("mailUnit" + id).value;
	// 执行更新操作
	updateModule.updateModule(userModuleId, moduleName  , isMobile,
			isMail, messageReceiveType, messageStartTime, messageStopTime,
			mobilefrequency, mobileUnit, sendMaxCount, mailStartTime,
			mailStopTime, mailFrequency, mailUnit, function(data) {
				alert(data);
			});

}

/**
 * 删除用户模块
 * 
 * @param {Object}
 *            obj
 */
function selectReverse(){
	
	$j("input[name='moduleBox']").each(function(){
		if($j(this).attr("checked")==true){
			$j(this).attr("checked",false);
			}
		else{
			$j(this).attr("checked",true);
			}
		});
}

function selectAll(){
	$j("input[name='moduleBox']").each(function(){
		$j(this).attr("checked",true);
		});
}

function delmodule() {
	var flag = false;
	var inputs = $j("input[name='moduleBox']");
	for ( var i = 0; i < inputs.length; i++) {
		if (inputs[i].getAttribute('type') == 'checkbox') {
			if (inputs[i].checked == true) {
				flag = true;
				break;
			}
		}
	}
	if (flag == false)
		alert("请选择再操作");
	else {
		document.forms[0].action ='manage_delmodule';
		document.forms[0].submit();
	}

}

function changeGroup(){
	var flag = false;
	var inputs = $j("input[name='moduleBox']");
	for ( var i = 0; i < inputs.length; i++) {
		if (inputs[i].getAttribute('type') == 'checkbox') {
			if (inputs[i].checked == true) {
				flag = true;
				break;
			}
		}
	}
	if (flag == false)
		alert("请选择再操作");
	else {
		document.forms[0].action ='manage_changeGroup';
		document.forms[0].submit();
	}
}


/**
 * 打开添加分组层 九月一号
 * 
 * 9月17号修改
 *            
 */
function showAddGroupPanel(){
	
	$j('.ke-dialog').fadeIn();
}

/**
 * 添加分组 九月一号
 * 
 * dwr：updateGroup.addGroup
 *      9月17号修改      
 */
function addGroup(){
	
	var newName = $j.trim($j('#newGroupName').val());
	var HasTheSameName=false;
	$j('#group').children().each(function(){
		if($j(this).text()==newName){
			HasTheSameName=true;
		}		
	});
	
	if(HasTheSameName==false){
		updateGroup.addGroup(newName,function(data){   //返回分组id
			alert("添加成功");
			$j('.ke-dialog').fadeOut();
			$j('#group').append('<option value='+data+'>'+newName+'</option>');
			window.location.reload();
		});
		
	}
	else if(newName==''){
		alert("名字为空");
	}else{
		alert(newName+' 分组名字已存在!');
	}

}
function canelAddGroup(){
	$j('.ke-dialog').fadeOut();
}