
var currentLeft = 10;
var currentTop =80;
var maxheight = 100;
var cline = 0;
var divIds = new Array();
var iframess=new Array();
var olpfBrower ="";

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
	//alert(iframess.length);
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
var borderW=0;
/**
 * 布局
 * 
 * @param id
 * @return
 */
function composition(id) {
	var cdiv = jQuery("#" + id);
	var odiv = jQuery("#"+id+"olpfsTemp");
	if (jQuery("body").outerWidth() - currentLeft < cdiv.outerWidth()) // 如果没有横坐标空间
	{
		
		currentLeft = 10;
		currentTop = currentTop + maxheight + 10;
	/*	jQuery("div[name='" +  currentGroup+cline + "']").each(function(i) {//调整高度
			var oterHeight=0;
			var tdiv = jQuery(this);
			tdiv.css( {
				height : maxheight
			});
			jQuery("#"+this.id+"olpfsTemp").css({
				height : maxheight
			});
			tdiv.find("iframe").each(function(h){	

				tdiv.find("div[class='main-cont-tit']").each(function(e) {
					oterHeight = oterHeight + jQuery(this).outerHeight();
				});
				jQuery(this).height(maxheight-oterHeight);	
			});
			//alert(this.id);
		});*/
		maxheight = 0;
		cline++;
	}
	cdiv.attr("name", currentGroup+cline);
	cdiv.css( {
		position : 'absolute',
		left : currentLeft,
		top : currentTop
	});
	checkLocation(cdiv);
	
	odiv.css( {
		position : 'absolute',
		left : currentLeft,
		top : currentTop
		
	});

	currentLeft = currentLeft + cdiv.outerWidth() + 10;
	if (maxheight < cdiv.innerHeight()) {
		maxheight = cdiv.innerHeight();
	}

    
}
function autoComposition(type) {
	
	try {
        if(type==1){
        	jQuery("#"+currentGroup).find(("div[class='main-cont']")).each(function(i){
        		pushDivIds(this.id);
        	});
        	
        }
	    var tempHeight = new Array(divIds.length);
		var tempdivIds = new Array();
		for ( var i = 0; i < divIds.length; i++) {
			tempHeight[i] = jQuery("#" + divIds[i]).innerHeight(); // 取得所有层宽度
			
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
		composition("unuse");
		currentLeft = 10;
		currentTop = 60;
		divIds=new Array();
	} catch (e) {
		myAlert(e);
	}
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
		myAlert(e);
	}
}
var currentGroup=null;
var currentGroupLi=null;
/**
 * 
 * @return
 */
function compositionOther() {
	var flag=false;//是否只布局未保存布局的层标志
	if((window.location.href.indexOf("onlyShowUpdate")<0)&&(window.location.href.indexOf("myfocus")<0)){
		flag=true;
	}else if(window.location.href.indexOf("onlyShowUpdate=0")>0){
		flag=true;
	}
	jQuery("div[class='group']").each(function(i) {
		divIds = new Array();
		try {
			currentLeft = 10;
			currentTop = 60; // 先重置当前坐标
			currentGroup = this.id;
			jQuery(this).find("div[class='main-cont']").each(function(e) {
				var cdiv = jQuery(this);// 当前div元素
					var ele = cdiv.offset();
					// myAlert(this.style.position);
					if (flag) {
						if (this.style.position == "static") {
							pushDivIds(this.id);
							cdiv.css( {
								position : 'absolute',
								left : '0px',
								top : '3000px'
							});

						} else {
							if (ele.top + cdiv.outerHeight() > currentTop) // 如果当前元素偏移量大于旧的顶偏
							// 移量
							{
								currentTop = ele.top + cdiv.outerHeight(); // 保存最低纵偏移量
							}
						}
					} else {
						pushDivIds(this.id);
						cdiv.css( {
							position : 'absolute',
							left : '0px',
							top : '3000px'
						});
					}
				});
		} catch (e1) {
			myAlert(e1);
		}
		autoComposition(0);
	});
	jQuery("div[class='group']").each(function(i) {
		jQuery(this).hide();
	});
	var temp = jQuery(jQuery("div[class='group']:first")).attr('id');
	changeGroup(temp, temp.replace('group', 'groupTag'), 1);
	var copyrightHeight = jQuery(window).height() - 40;
	jQuery("div[class='main-cont']")
			.each(
					function(i) {

						if (parseInt(this.style.top)
								+ parseInt(this.style.height) > copyrightHeight) {
							copyrightHeight = parseInt(this.style.top)
									+ parseInt(this.style.height);
						}
					});
	jQuery("#copyright").css( {
		position : 'absolute',
		left : '0px',
		top : copyrightHeight
	});
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
			
			cdiv.height(cdiv.height() + height);
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
	 if(minTop>JQdiv.offset().top)
	 {
		JQdiv.css({
			top:minTop
		});
	 }
	 if(minWidth>JQdiv.innerWidth())
	 {
		JQdiv.css({
			width:minWidth
		});
	 }
	 if(minHeight>JQdiv.innerHeight())
	 {
		JQdiv.css({
			height:minHeight
		});
	 }
}

function displayOther()
{
	jQuery("div[class='main-cont']").each(function(e) {
		var cdiv = jQuery(this);// 当前div元素
				cdiv.css({
					display:'block',
					opacity:0.9
				}).show('fast');
			});
}

function changeGroup(id,li,must){

	if(currentGroup==id&&must!=1){
		return;
	}
	//先隐藏所有组  
	jQuery("#"+currentGroup).hide();
     var cli= jQuery("#"+currentGroupLi);
	 cli.removeClass("displayGroupStyle");
	 cli.addClass("unDisplayGroupStyle");
	currentGroup=id;
	currentGroupLi=li;
	//显示组
	jQuery("#"+id).show(1);
	var tli=jQuery("#"+li);
	tli.removeClass("displayGroupStyle");
	tli.removeClass("unDisplayGroupStyle")
	tli.addClass("displayGroupStyle");
}
/**
 * 将绝对定位的层变成相对定位
 * @param {Object} twindow
 */
function changePosition(twindow){
	var tbody=jQuery(twindow.document.body);
	//alert(11);
	var top=parseInt(twindow.document.body.style.height);
	var width=parseInt(twindow.document.body.style.width);
	var div;
	tbody.find("div[id]").each(function(i){
		if (this.offsetTop>top||this.offsetLeft>width) {
			jQuery(this).css('position', 'static');
		}
	});
		tbody.find("div[class]").each(function(i){
	
		if (this.offsetTop>top||this.offsetLeft>width) {
			jQuery(this).css('position', 'static');
		}
	});
}

