var $j = jQuery.noConflict();

/**
 * 删除分组
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

function delgroup() {
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
	else if(confirm("删除分组会把分组中的模块一并删除，确定删除？")){
		document.forms[0].action ='manage_delGroup';
		document.forms[0].submit();
	}

}



function updategroupname(obj){
	var btn = $j(obj);
	var subgroupId = btn.attr("id");
	var newName=$j('#newGroupname'+subgroupId).val();
	updateGroup.updateGroupName(subgroupId,newName,function(data){
	});
	$j('#newName'+subgroupId).text(newName);
}



/**
 * 打开添加分组层 九月二号
 * 
 * 
 *            
 */
function showAddGroupPanel(){
	
	$j('.ke-dialog').fadeIn();
}

/**
 * 添加分组 九月二号
 * 
 * dwr：updateGroup.addGroup
 *            
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