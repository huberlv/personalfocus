/*已拆分，无用*/
var currentLeft = 10;
var currentTop = 100;
var maxheight = 100;
var cline = 0;
var divIds = new Array();
var isMouseDown = false;
window.onmouseup = olpfMouseUp;
var isComModule = false;// 是否为布 局模式
var iframess=new Array();
var currentTextID=0;
var olpfBrower ="";
var isUpdate=false;
var isDisplay=false;
function olpfContainsDivIds(id) {
	for ( var i = 0; i < divIds.length; i++) {
		if (id == divIds[i])
			return true;
	}
	return false;
}
function pushDivIds(id) {
	if (olpfContainsDivIds(id) == false) {
		divIds.push(id);
	}
}
/**
 * 刷新某一模块
 * @param uid
 * @return
 */
function flushModule(uid)
{
	var twindow=null;
	
	for(var i=0;i<iframess.length;i++){
		if(iframess[i].id==uid){
			twindow=iframess[i];
			break;
		}
	}
	if(twindow!=null)
	{
		twindow.location.reload();
	}
}
/**
 * 展示更新，警告框，背景音乐等
 * @param twindow
 * @return
 */
function displayUpdate(twindow){
	if(isUpdate==true &&isDisplay==false){
		var contains=false;
		for(var i=0;i<iframess.length;i++){
			if(iframess[i]===twindow)
			{
				contains=true;
			}
		}
		if(contains==true)
		{
			isUpdate=false;
			
			jQuery("#bgs").remove();
			jQuery("body").append("<bgsound  id=bgs src=/personalfocus/audios/bgs.mp3 loop=-1>");
			//加入背景音乐
			window.setTimeout(setAlert, 1000);//延迟弹出窗口
					
		}else{
			iframess.push(twindow);
		}
	}
}
function setAlert(){
	if(isDisplay==true){
		window.setTimeout(null, 10000000);//延迟弹出窗口
		return;
	}
	isDisplay=true;
	alert("你有订阅更新，请查看！");
	jQuery("#bgs").remove();
	isDisplay=false;
	window.setTimeout(null, 10000000);//延迟弹出窗口
}
/**
 * 取得文字
 * @param source
 * @return
 */
function getText(source)
{
	jQuery("body").append("<div id='text"+currentTextID+"'>"+source+"</div>");
	var text=jQuery.trim(jQuery("#text"+currentTextID).text());
	jQuery("#text"+currentTextID).remove();
	currentTextID=currentTextID+1;
	//myAlert(text);
	return text;
}
var sendIDss=new Array();
/**
 * 设置所有模块为已读
 * @return
 */
function setReadyAllModules()
{
	try {
		for ( var i = 0; i < iframess.length; i++) {
			for ( var j = 0; j < iframess[i].sendIDs.length; j++) {
				sendIDss.push(iframess[i].sendIDs[j]);
			}
		}
		//用于通信的变量最好设成全局，因为局部变量是会销毁 的，而通信是异步的
		updateContentReceiver.deleteARecord(sendIDss, function(e) {
			for(var i=0;i<iframess.length;i++){
			jQuery(iframess[i].document.body).find("*[name='new']").each(
					function(e) {
						jQuery(this).removeClass("updateStyle");
						var tclass=jQuery(this).data("sclass");
						if(tclass!="updateStyle")
						   jQuery(this).addClass(jQuery(this).data("sclass"));// 还原原来的class
					});
			}
			sendIDss.splice(0, sendIDss.length);
			sendIDss=new Array();
			});
	}catch(e){
		myAlert(e);
	}
}
/**
 * 标记一个模块的信息为已读
 * @param uid
 * @return
 */
function setReadAModule(uid)
{
	//myAlert(uid);
	var twindow=null;
	for(var i=0;i<iframess.length;i++){
		if(iframess[i].id==uid){
			twindow=iframess[i];
	//		myAlert(uid);
			break;
		}
	}
	updateContentReceiver.deleteARecord(twindow.sendIDs,function(e){
			jQuery(twindow.document.body).find(".updateStyle").each(function(e){	
		jQuery(this).removeClass("updateStyle");
		jQuery(this).addClass(jQuery(this).data("sclass"));//还原原来的class
	});
	});
}
/**
 * 递归函数，对比文字，如相同，则将其class设为updateStyle
 * @param element
 * @param up
 * @return
 */
function changeStyle(element,up)
{
	var ttext=jQuery.trim(element.text());

	for(var i=0;i<up.length;i++)
	{

	   if(ttext!="" &&ttext==up[i]){
		//	alert(ttext);
		 //  element.data("sclass", element.attr("class")); //先保存原来的class
		   element.data("sclass", element.attr("class")); //先保存原来的class
		   var tparent=jQuery(element.parent());
		   tparent.removeClass("updateStyle");
		   if(tparent.data("sclass")!=null &&tparent.data("sclass")!="undefined"){
			   
		         tparent.removeClass("updateStyle");
		         tparent.attr("class",tparent.data("sclass"));//恢复父节点的样式
		   }
		   element.attr("class","updateStyle");
		   element.attr('name',"new");
		   isUpdate=true;
	   }
	}
	element.children().each(function(h){
		changeStyle(jQuery(this),up);
	});
}
function changeUpdateContentStyle(twindow)
{
	for(var i=0;i<twindow.updateCont.length;i++){
		twindow.updateCont[i]=getText(twindow.updateCont[i]);

	}
	var tiframe=jQuery(twindow.document.body);	
	changeStyle(tiframe,twindow.updateCont);
	displayUpdate(twindow); //
	//iframess.push(twindow);//用于一键标记全部为已读
}

/**
 * 改变模式，布局模式或普通模式
 * @return
 */
function changeModule() {
	if (isComModule == true) {
		isComModule = false;
		jQuery("#module").text("布局模式");
		exitEdit();
	} else if (isComModule == false) {
		isComModule = true;
		jQuery("#module").text("退出布局模式");
		edit();
	}

}

var borderW=0;
/**
 * // 为层加入拖动等动作
 * 
 * @param d
 * @return
 */
function addDivAction(d) { // 为层加入拖动动作
	var tdiv = jQuery(d);
	var temp = "<div name='tempDiv' id=" + tdiv.attr("id")+"olpfsTemp"
			+ ">&nbsp;</div>"; // 为每一模块加入覆盖层
	jQuery("body").append(temp);
	var tempDiv = jQuery("#olpfsTemp" + tdiv.attr("id"));
    borderW=tdiv.outerWidth()-tdiv.innerWidth();
	borderW=borderW/2;  //边框宽度
	tempDiv.css( {
		position : 'absolute',
		"z-index" : 2147483646,
		left : tdiv.offset().left+borderW,
		top : tdiv.offset().top+borderW,
		width : tdiv.width(),
		height : tdiv.height(),
		opacity : 0.2,
		'background-color' : '#66CCFF'
	});

	tempDiv.data("tTop", 0);
	tempDiv.data("tLeft", 0);
	tempDiv.hover(function(e) {
		e.stopPropagation();
		if (e.altKey == true) {
			this.style.cursor = 'n-resize';
		} else {
			this.style.cursor = 'crosshair';
		}
		// 变大小
		}, function(e) {
			e.stopPropagation();
			// this.style.cursor = 'auto';
		});
	tempDiv.mousedown(function(e) {
		e.stopPropagation();
		jQuery(this).data("tTop", e.clientY);
		jQuery(this).data("tLeft", e.clientX);
		isMouseDown = true;
		jQuery(this).css( {
			"z-index" : 2147483647
		});
	});
	tempDiv.mouseup(function(e) {
		isMouseDown = false;
		e.stopPropagation();
		jQuery(this).css( {
			"z-index" : 2147483646
		});
	});
	/*
	 * tdiv.find("ifame").each(function(e){ jQuery(this).mousemove( function(e1){
	 * jQuery(this).parent().mousemove(e1); e1.stopPropagation(); }); });
	 */
	tempDiv.mousemove(function(e) {
		try {
			e.stopPropagation();
			if (e.altKey == true) {
				this.style.cursor = 'n-resize';
			} else {
				this.style.cursor = 'crosshair';
			}
			if (isMouseDown == true) {
				
				var cdiv1 = jQuery(this);
				var idiv = jQuery("#" + parseInt(this.id)); // 内层div
				var top = cdiv1.offset().top;
				var left = cdiv1.offset().left;
				var width = cdiv1.width();
				var height = cdiv1.height();
			if (e.altKey == true) { // 上
				cdiv1.css( {
					// "z-index" : 2147483647,
					'height' : height + e.clientY - cdiv1.data("tTop"),
					'width' : width + e.clientX - cdiv1.data("tLeft")
				});
				checkLocation(cdiv1);
				var disWH=0;
				if(olpfBrower=="ie")
				{
					disWH=2*borderW;
				}
				var oterHeight=0;
				idiv.find("iframe").each(
						function(e1) {
							this.width = cdiv1.width()-disWH;
							idiv.find("div[class='main-cont-tit']").each(function(e){
								oterHeight=oterHeight+jQuery(this).outerHeight();
							});
							this.height =cdiv1.height()
									- oterHeight-disWH;
						});
				idiv.css( {
					'height' :cdiv1.height(),
					'width' :cdiv1.width()
				});
				/*
				 * jQuery(cdiv1.find("#olpfs"+this.id)).attr("height",cdiv1.height()
				 * +e.clientY- cdiv1.data("tTop")
				 * -jQuery(cdiv1.find("ifame[class='main-cont-tit']")).height()*2);
				 * cdiv1.find("#olpfs"+this.id).width=cdiv1.width() + e.clientX -
				 * cdiv1.data("tLeft");
				 */
				cdiv1.data("tTop", e.clientY);
				cdiv1.data("tLeft", e.clientX);
			} else if (e.altKey == false) {
				var to = top + e.clientY - cdiv1.data("tTop");
				var le = left + e.clientX - cdiv1.data("tLeft");
				// myAlert(to);
				// myAlert(le);
				cdiv1.css( {
					// "z-index" : 2147483647,
					'top' : to + "px",
					'left' : le + "px"
				});
				checkLocation(cdiv1);
				idiv.css( {
					'top' : to + "px",
					'left' : le + "px"
				});
				//checkLocation(idiv);
				cdiv1.data("tTop", e.clientY);
				cdiv1.data("tLeft", e.clientX);
			}
		} else {
			isMouseDown = false;
		}
	} catch (e) {
		myAlert(e);
	}
});

}

/**
 * 是否存在id，是则返回true，否则将id加入到容器
 * 
 * @param id
 * @return
 */
function containsID(id) {
	for ( var i = 0; i < divIds.length; i++) {
		if (divIds[i] == id) { // 如果存在，则返回true
			return true;
		}
	}
	divIds.push(id);
	return false; // 否则加入到容器中，返回false
}
/**
 * 布局
 * 
 * @param id
 * @return
 */
function composition(id) {
	var cdiv = jQuery("#" + id);
	if (jQuery("body").outerWidth() - currentLeft < cdiv.outerWidth()) // 如果还有横坐标空间
	{
		currentLeft = 10;
		currentTop = currentTop + maxheight + 10;
		jQuery("div[name='" + cline + "']").css( {
			height : maxheight
		});
		maxheight = 0;
		cline++;
	}
	cdiv.attr("name", cline);
	cdiv.css( {
		position : 'absolute',
		left : currentLeft,
		top : currentTop
	});
	checkLocation(cdiv);
	var odiv = jQuery("#olpfsTemp" + id);
	odiv.css( {
		position : 'absolute',
		left : currentLeft,
		top : currentTop
	});
	currentLeft = currentLeft + cdiv.outerWidth() + 10;
	if (maxheight < cdiv.outerHeight()) {
		maxheight = cdiv.outerHeight();
	}

}
function autoComposition() {
	
	try {
		/*
		 * jQuery("div[class='main-cont']").each( function(e) { this.id =
		 * jQuery(jQuery(this).find("iframe:first")).attr("id") .substring(5); ; });
		 */
		// myAlert(divIds.length);
	/*	for ( var g = 0; g < divIds.length; g++) {
			// myalert(tempdivIds[g] + "1111");
			var h = jQuery("#olpfs" + divIds[g])
			jQuery("#" + divIds[g]).css( {
				// height : "auto",
				width : h.width()
			// display:'compact'
					});
		}*/

		var tempHeight = new Array(divIds.length);
		var tempdivIds = new Array();
		for ( var i = 0; i < divIds.length; i++) {
			tempHeight[i] = jQuery("#" + divIds[i]).outerHeight(); // 取得所有层宽度
			// myalert(tempWidth[i] + "<br>");
		}
		var cw = 6000;// 当前宽度
		var h = 0;
		var ch = 0;
		for ( var j = 0; j < divIds.length; j++) {
			for (h = 0; h < divIds.length; h++) {
				if (cw > tempHeight[h]) {
					ch = h; // 保存当前指针
					cw = tempHeight[h];
				}
			}
			// myalert(divIds[ch]);
			tempdivIds[j] = divIds[ch];
			tempHeight[ch] = 6005;
			ch = 0;
			cw = 6000;// 当前宽度
		}
		maxheight = 0;
		cline = 0;
		for ( var g = 0; g < tempdivIds.length; g++) {
			// myAlert(tempdivIds[g] + " 1 ");

			composition(tempdivIds[g]);
		}
		jQuery("body").append("<div style='width:100%;' id='unuse'></div>");
		composition("unuse");
		jQuery("#unuse").remove();
		divIds = new Array();
		jQuery("div[class='main-cont']").each(function(e) {

			divIds.push(this.id);
		});
		currentLeft = 10;
		currentTop = 100;
	} catch (e) {
		myAlert(e.message);
	}
}
function edit() {
	jQuery("div[class='main-cont']").each(function(e) {
		addDivAction(this);
	});
}
function exitEdit() {
	jQuery("div[name='tempDiv']").each(function(e) {
		unBind(jQuery(this));
		jQuery(this).remove();
	});
	// myAlert(111);
}
/**
 * 解除元素绑定的方法
 * 
 * @param e
 * @return
 */
function unBind(e) {
	try {
		e.unbind();
		e.children().each(function(i) {
			unBind(jQuery(this));
		});
	} catch (q) {
		myAlert(q.message);
	}
}

function olpfMouseUp(e) {
	isMouseDown = false;
}

function compositionOther() {
	try {
		currentLeft = 10;
		currentTop = 100; // 先重置当前坐标
		jQuery("div[class='main-cont']").each(function(e) {
			var cdiv = jQuery(this);// 当前div元素
				var ele = cdiv.offset();
				//myAlert(this.style.position);
				if (this.style.position == "static") {
					divIds.push(this.id);
					cdiv.css({
						position : 'absolute',
						left:'0px',
						top:'3000px'
					});
					
				} else {
					if (ele.top + cdiv.outerHeight() > currentTop) // 如果当前元素偏移量大于旧的顶偏
					// 移量
					{
						currentTop = ele.top + cdiv.outerHeight(); // 保存最低纵偏移量
					}
				}
			});

	} catch (e1) {
		myAlert(e1.message);
	}
	// autoComposition();
	autoComposition();
}

/**
 * 释放内存
 */
function olpftooltipsFree(e) {
	try {
		unBind(jQuery('body'));
		//遍历window的对象，置为空
		for   (elm   in  window) 
		{ 
			if(typeof(window[elm])=='object')
			{
				//myAlert(window[elm]);
				window[elm]=null; 	
			}
		} 
	} catch (e) {
		myAlert(e);
	}
}
jQuery(document).ready(function() {
	var brower = navigator.userAgent.toLowerCase();
	if (brower.indexOf("firefox") >= 0) {
		olpfBrower = "f";
	} else if (brower.indexOf("opera") >= 0) {
		olpfBrower = "op";
	} else {
		olpfBrower = "ie";
	}
	
	if(olpfBrower=="f")  //在火狐中改高度，其余不改
	  editHeight();
	if (document.all) {// 加入事件，最好在此加入
			document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
			document.attachEvent('onbeforeunload', olpftooltipsFree); // onbeforeunload
			// document.addEventListener('mouseup', olpfMouseUp,true);
		} else {
			document.addEventListener('beforeunload', olpftooltipsFree,
					false);
			document.addEventListener('mouseup', olpfMouseUp, true);
			// document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
		}
		compositionOther();
		// autoComposition();
		
	});
/**
 * 对每个模块增加高度
 * 
 * @return
 */
function editHeight() {

	jQuery("div[class='main-cont']").each(function(e) {
		var cdiv = jQuery(this);
		var height = 0;
		// myAlert(cdiv.outerHeight());
			cdiv.find("div[class='main-cont-tit']").each(function(g) {
				height = height + jQuery(this).outerHeight();
			});
			cdiv.height(cdiv.outerHeight() + height + "px");
			// cdiv.height(cdiv.outerHeight()+height+10+"px");
			// myAlert(cdiv.outerHeight());
		});
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

/**
 * 设置在新窗口打开链接
 * @param twindow
 * @return
 */
function changeLinkTarget(twindow)
{
	jQuery(twindow.document.body).find("a").each(function(h){
		this.target="_blank";
	});
}
function setDivHAW(userModuleId) {
	jQuery("#" + userModuleId).css( {
		height : 'auto'
	});
}
function getStyle(e, height) {
	var cdiv = jQuery(e);// 当前div元素
	// position: absolute;height:156;width:250;top:3000px;left:0px
	height = cdiv.outerHeight() - height;
	var temp = "position:" + cdiv.css("position") + ";top:" + cdiv.offset().top
			+ ";left:" + cdiv.offset().left + ";width:" + cdiv.outerWidth()
			+ ";height:" + height + ";";
	return temp;
}
/**
 * 释放内存
 */
function olpftooltipsFree() {
	try {
		unBind(jQuery('body'));
		//遍历window的对象，置为空
		for   (elm   in  window) 
		{ 
			if(typeof(window[elm])=='object')
			{
				//myAlert(window[elm]);
				window[elm]=null; 	
			}
		} 
	} catch (e) {
		myAlert(e);
	}
}
/**
 * 删除一个用户模块
 * @param uid
 * @return
 */
function deleteUserModule(uid)
{
	 var r=confirm("你确定要删除此模块？")
	  if (r==false)
	  {
		  return;
	  }

	updateUserModule.deleteUserModule(uid,function(e){
		unBind(jQuery("#"+uid));
		jQuery("#"+uid).remove();
		myAlert(e);
	});
}
/**
 * 保存布局
 * 
 * @return
 */
function saveEdit() {
	var styles = new Array();
	var uids = new Array();
	var temp = "";
	jQuery("div[class='main-cont']").each(function(e) {
		temp = new String();
		var cdiv = jQuery(this);// 当前div元素
			uids.push(this.id); // position:
			// absolute;height:156;width:250;top:3000px;left:0px
			var height = 0;
			// myAlert(cdiv.outerHeight());
			cdiv.find("div[class='main-cont-tit']").each(function(g) {
				height = height + jQuery(this).outerHeight();
			});
			styles.push(getStyle(this, height));
		});
	updateUserModule.updateStyle(styles, uids, function(e) {
		myAlert(e);
	});
}
var minTop=50;
var maxBottom=1000; //最低层的纵坐标，由网页底下的网页信息决定
var minWidth=320;
var minHeight=100;
/**
 * 检查模块的大小和位置
 * @return
 */
function checkLocation(JQdiv)
{
	 var tepmT=minTop<JQdiv.offset().top?JQdiv.offset().top:minTop;
   //  tepmT=maxBottom>(JQdiv.offset().top+JQdiv.outerHeight())?tepmT:tepmT-JQdiv.outerHeight(); //最低层的纵坐标，由网页底下的网页信息决定
	 var tempW=minWidth<JQdiv.width()?JQdiv.width():minWidth;
	 var tempH=minHeight<JQdiv.height()?JQdiv.height():minHeight;
	JQdiv.css({
		top:tepmT,
		width:tempW,
		height:tempH
	});
}