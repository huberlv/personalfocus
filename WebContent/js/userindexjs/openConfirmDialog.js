function $(ele) {
  if (typeof(ele) == 'string'){
    ele = document.getElementById(ele)
    if(!ele){
  		return null;
    }
  }
  if(ele){
  	Core.attachMethod(ele);
	}
  return ele;
}
var Core = {};
Core.attachMethod = function(ele){
	if(!ele||ele["$A"]){
		return;
	}
	if(ele.nodeType==9){
		return;
	}
	var win;
	try{
		if(isGecko){		
			win = ele.ownerDocument.defaultView;
		}else{
			win = ele.ownerDocument.parentWindow;
		}
		for(var prop in $E){
			ele[prop] = win.$E[prop];
		}
	}catch(ex){
		//alert("Core.attachMethod:"+ele)//有些对象不能附加属性，如flash
	}
}
function zOpenD(message){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 320;
	diag.Title = "确认框";
	diag.URL = "showconfirm";
	diag.ShowMessageRow = true;
	diag.MessageTitle = "";
	diag.Message = "";
	diag.OKEvent = zAlert;//点击确定后调用的方法
	diag.Large = zExpand;
	diag.Small = zNarrow;
	diag.show();
}
function zOpen(){
	var diag = new Dialog("Diag2");
	diag.Width = 900;
	diag.Height = 400;
	diag.Title = "弹出窗口示例";
	diag.URL = "http://51xuediannao.com/";
	diag.OKEvent = zAlert;//点击确定后调用的方法
	diag.show();
}
function zAlert(){
	addModule.addModuleInfo(function(data) {
		alert(data);
		location.reload();
	});
	
}

function zExpand(){
	addModule.expandMonitorScale(function(data) {
		zOpenD(data); 
	});
}

function zNarrow(){
	addModule.narrowMonitorScale(function(data) {
		zOpenD(data); 
	});
}

function zConfirm(){
	Dialog.confirm('警告：您确认要XXOO吗？',function(){Dialog.alert("yeah，周末到了，正是好时候");});
}
