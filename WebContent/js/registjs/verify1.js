function verifyauthcode(){
	
	var inputcode=$("authcode").value;
	//alert(inputcoed);
	verifyauthcode.verifycode(inputcode,function(data){
		if(!data)
		{
			$j("#authcode_ico_err").show();
			$j("#authcode_ico_ok").hide();
		//	var btn=document.getElementById("btn");
		//	btn.innerHTML=" <input type='submit' id='submit' value='' class='btn-submit-disable' onBlur='this.className='btn-submit'' onClick='this.blur(); ' onFocus='this.className='btn-submit-act''  disabled='disabled' />";
		}
		else{
			$j("#authcode_ico_err").hide();
			$j("#authcode_ico_ok").show();
		//	var btn=document.getElementById("btn");
		//	btn.innerHTML=" <input type='submit' id='submit' value='' class='btn-submit' onBlur='this.className='btn-submit'' onClick='this.blur(); ' onFocus='this.className='btn-submit-act''/>";
			
		}
	}
	);
}

