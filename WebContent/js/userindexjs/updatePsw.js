function updatePassword(){
	var currentPsw=$("currentPsw").value;
	var newPsw=$("newPsw").value;
	var pswConfirm=$("pswConfirm").value;
	updatePsw.updatePsw(currentPsw,newPsw,pswConfirm,function(data){
		if(data=="�޸ĳɹ�"){
			window.location.reload(true);
		}	
		alert(data);
	});
}

