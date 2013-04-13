
//当前网址
var olpfsThisUrl = document.location.href;

/**
 * 释放内存
 */
/**
 * 释放内存
 */
function olpftooltipsFree() {
	var temp=olpfBase;
	try {
		unBind(jQuery('body'));
		//遍历window的对象，置为空
		for   (elm   in  window) 
		{ 
			if(typeof(window[elm])=='object')
			{
				//alert(window[elm]);
				window[elm]=null; 	
			}
		} 
	} catch (e) {
		myAlert(e);
	}
	olpfBase=temp;
}

/**
 * 解除元素绑定的方法
 * 
 * @param e
 * @return
 */
function unBind(e) {
	try{
	   e.unbind();
	   e.children().each(function(i) {
		unBind(jQuery(this));
	});
	}catch(q){
	}
	
}



var developModule=true;
/**
 * 错误信息处理
 * @param e
 * @param otherMessage
 * @return
 */
function myAlert(e,otherMessage){
	
	if(developModule==false){
		return;
	}
	var fmessage="description:"+(typeof e.description=='undefined'?"":e.description)
			+ "\nfileName:"+(typeof e.fileName=='undefined'?"":e.fileName)
			+ "\nlineNumber:"+(typeof e.lineNumber=='undefined'?"":e.lineNumber)
			+ "\nmessage:"+(typeof e.message=='undefined'?"":e.message)
			+ "\nname:"+(typeof e.name=='undefined'?"":e.name)
			+ "\nnumber:"+(typeof e.number=='undefined'?"":e.number)
			+ "\nstack:"+(typeof e.stack=='undefined'?"":e.stack)
					+ "\n"+(typeof otherMessage=='undefined'?"":otherMessage);
	alert(fmessage);
}

var sourceBase=null;
function getSourceBase(){
	if(sourceBase!=null)
		return sourceBase;
	else{
		jQuery("base").each(function(e){
			var temp=jQuery(this).attr('href');
			if(temp!=null&&temp!='undefined')
			{
				sourceBase=temp;
			}
		});
		if(sourceBase==null){
			sourceBase=olpfsThisUrl;
		}
		// alert("基址"+base);
		if (sourceBase.charAt(sourceBase.length - 1) != '/') {
			var end = sourceBase.lastIndexOf("/");
			sourceBase = sourceBase.substring(0, end + 1);
		}
		return sourceBase;
	}
}
function getURL(rurl, url) {
	// alert("原"+url);
	try {
		if (url.indexOf("http:") < 0) {	
			
			var base = rurl + getSourceBase() + url;
			var reg = new RegExp("//+", "g");
			var reg2 = new RegExp(":/", "g");
			base = base.replace(reg, "/");
			base = base.replace(reg2, "://");

			return base;
		} else {
			return rurl+url;
		}
	} catch (e) {
		myAlert(e);
		return rurl + url;
	}
}
