var timeOutId=0;
var checkTime=1000;
function checkDefineDiv(){
	var exeits= jQuery("#defineTable")!=null;
	if(exeits==true){
		clearTimeout(timeOutId);
		window.init();
	}else{
		timeOutId=setTimeout(checkDefineDiv, checkTime);
	}
}
timeOutId=setTimeout(checkDefineDiv, checkTime);

