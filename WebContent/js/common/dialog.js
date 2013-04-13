
function getExtraDiv(){
	if(document.getElementById('extraDiv')==null){
		jQuery('body').append("<div id='extraDiv'>&nbsp;</div>");
	}
	return jQuery("#extraDiv");
}
/**
 * 加入对话框
 * @return
 */
function addDialogDiv(){
	if(document.getElementById("keDialog")!=null){
		return;
	}else{
		var dialogstr="<div class='ke-dialog' id='keDialog' 	style='position:absolute;z-index: 21474836700; top: 100px; left: 460px;display:none;'> <table width='380' height='180' border='0' cellpadding='0' 	cellspacing='0' class='ke-dialog-table'> 	<tbody> 		<tr> 			<td class='ke-tl'><span class='ke-dialog-empty'></span></td> 			<td class='ke-tc'><span class='ke-dialog-empty'></span></td> 			<td class='ke-tr'><span class='ke-dialog-empty'></span></td> 		</tr> 		<tr> 			<td class='ke-ml'><span class='ke-dialog-empty'></span></td> 			<td class='ke-mc' id='dialogContent'>dialogContent</td> 			<td class='ke-mr'><span class='ke-dialog-empty'></span></td> 		</tr> 		<tr> 			<td class='ke-bl'><span class='ke-dialog-empty'></span></td> 			<td class='ke-bc'><span class='ke-dialog-empty'></span></td> 			<td class='ke-br'><span class='ke-dialog-empty'></span></td> 		</tr> 	</tbody> </table> </div>";
		getExtraDiv().append(dialogstr);
	}
}
/**
 * 加入对话框内容
 * @param html
 * @return
 */
function addDialogContent(html){
	addDialogDiv();
	jQuery('#dialogContent').html(html);
}

function hideDialog(){
	document.getElementById('keDialog').style.display='none';
}

function showDialog(){  
	jQuery("#keDialog").css({
		 position: 'absolute',
        left: document.body.clientWidth/2-200,
        top: 100
	}).show('fast');
}