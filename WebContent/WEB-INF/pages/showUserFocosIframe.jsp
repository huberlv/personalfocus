<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib
	uri="/struts-tags" prefix="s"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="refresh" content="180">
 <LINK rel=stylesheet type=text/css href="/personalfocus/css/userindexcss/iframe.css">
<script>

    var id="olpfs${userModuleId}";
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
		
		var olpfIframe = window.parent.document.getElementById("olpfs${userModuleId}");
		
/*		var heig=0;
		if(document.documentElement.clientHeight>${height}){
			 heig =document.documentElement.clientHeight;	
		}
		else{
			 heig =${height};
		}*/
		olpfIframe.height =${height};
		//olpfIframe.width =${width};
	//	window.parent.setDivHAW("${userModuleId}");

		for ( var i = 0; i < window.frames.length; i++) {
			window.frames[i].src = "";
		}
		window.parent.changeLinkTarget(window);
		window.parent.addIframe(window);
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