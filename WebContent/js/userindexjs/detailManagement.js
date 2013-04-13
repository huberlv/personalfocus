var $j = jQuery.noConflict();

/**
 * �����û�ģ����Ϣ
 * 
 * @param {Object}
 *            obj
 */
function update(obj) {
	var btn = $j(obj);
	var id = btn.attr("id");
	var userModuleId = id;
	var moduleName = DWRUtil.getValue("moduleName" + id); // dwrȡֵ�ķ���
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
	// ִ�и��²���
	updateModule.updateModule(userModuleId, moduleName  , isMobile,
			isMail, messageReceiveType, messageStartTime, messageStopTime,
			mobilefrequency, mobileUnit, sendMaxCount, mailStartTime,
			mailStopTime, mailFrequency, mailUnit, function(data) {
				alert(data);
			});

}

/**
 * ɾ���û�ģ��
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
		alert("��ѡ���ٲ���");
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
		alert("��ѡ���ٲ���");
	else {
		document.forms[0].action ='manage_changeGroup';
		document.forms[0].submit();
	}
}


/**
 * ����ӷ���� ����һ��
 * 
 * 9��17���޸�
 *            
 */
function showAddGroupPanel(){
	
	$j('.ke-dialog').fadeIn();
}

/**
 * ��ӷ��� ����һ��
 * 
 * dwr��updateGroup.addGroup
 *      9��17���޸�      
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
		updateGroup.addGroup(newName,function(data){   //���ط���id
			alert("��ӳɹ�");
			$j('.ke-dialog').fadeOut();
			$j('#group').append('<option value='+data+'>'+newName+'</option>');
			window.location.reload();
		});
		
	}
	else if(newName==''){
		alert("����Ϊ��");
	}else{
		alert(newName+' ���������Ѵ���!');
	}

}
function canelAddGroup(){
	$j('.ke-dialog').fadeOut();
}