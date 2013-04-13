<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib
	uri="/struts-tags" prefix="s"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

   <LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/iframe.css">

<script>
    var olpfsparent=window.parent;
    
    var id="${userModuleId}olpfs";
    var sendIDs=new Array("${sendIDs}");
	var olpfOrg = window.onload;
	var olpfOrgError = window.onerror;
	var a=0;
	window.onload = setWAH;
	window.setTimeout=function(e,t){
	};
	window.open=function(){};
	document.write =function(e){};
	onerror=setWAH;
	function setWAH(){
		//window.parent=null;
		  // if(twindow.id=="52olpfs")
		
		var olpfIframe = olpfsparent.document.getElementById("${userModuleId}olpfs");
		
	/*	var heig=0;
		if(document.documentElement.clientHeight>${height}){
			 heig =document.documentElement.clientHeight;	
		}
		else{
			 heig =${height};
		}*/
		
		olpfIframe.height =${height};

		//olpfIframe.width =${width};
	//	olpfsparent.setDivHAW("${userModuleId}");

		for ( var i = 0; i < window.frames.length; i++) {
			try{
			window.frames[i].src = "";
			}catch(e){
			}
		}

		olpfsparent.changePosition(window);

		olpfsparent.displayUpdate(window,${isupdate});  //请求主窗口改变更新的内容的样式
		//olpfsparent.changeUpdateContentStyle(window,true);  //请求主窗口改变更新的内容的样式
		olpfsparent.changeLinkTarget(window);
		window.onload = olpfOrg;
		onerror=function(){};
	
	}
</script>
${headCssAndScript}
</head>
<body style='width:${width};height:${height};'>
${bodyCssAndScript}${html} 
</body>
</html>