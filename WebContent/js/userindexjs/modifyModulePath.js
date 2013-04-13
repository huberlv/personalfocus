
var olpfBrower = "";
var olpfsTagss = new Array("div", "tr", "li", "ul", "td", "a", "code","body",
		"custom", "dd", "dfn", "dir", "dl", "title", "legend", "marquee",
		"noBR", "ol", "pre", "span", "strike", "tBody", "map","a");
var olpfsInputTags = new Array("input", "form", "select", "textarea", "button");
// 所有被选中节点的特征
var olpfpaths;

// 当前要定义的元素数组的引用
var olpfElement = new Array();
// 错误处理，完羡后要返回false
onerror = olpfHandleErr
var olpfIfrmeIUrl = olpfBase
		+ "/personalfocus/user/showWebPage?olpfsIsIframe=1&url=";
// 鼠标移过可定义元素的次数，少于0时则清除鼠提示
// var mouseOverTimes = 20;
var olpfsUrl = olpfsThisUrl;

var olpfsHref = "";
// 鼠标释放函数
var isolpfmousedown = false; // 鼠标是否按下
// 定义边框的4个边框层jquery对象，分别为上下左右
var oudivt = null;
var oudivd = null;
var oudivl = null;
var oudivr = null;

// 等待层的对象引用
var olpfWaitDiv = null;
var olpfWaitDiv2 = null;
var olpftopTips = null;
var olpfCElement = null; // 当前元素
var path = "";
var olpfsparent = window.parent;
var sonload=window.onload;
var sdonload=document.onload;
var olpfWaitDiv2 = null;
var olpftopTips = null;
var oriXOpen = XMLHttpRequest.prototype.open;
var defineModuleClass="defineModuleClass";
XMLHttpRequest.prototype.open = function(method, url, asncFlag, user, password) {
	// code to trace or intercept

	if (url.indexOf(olpfBase) < 0)
		url = getURL(showWebPageURL, url);
	oriXOpen.call(this, method, url, asncFlag, user, password);
};

function errorHandler(msg) {
	alert(msg+"!网络异常，请检查操作是否成功！");
}
function warringHandler(msg) {
	
		alert("执行完毕！");
}
DWREngine.setErrorHandler(errorHandler);
DWREngine.setWarningHandler(warringHandler);
DWREngine.setTimeout(0);

function setolpfsUrl(Url) {
	
	  olpfsUrl = Url;
}
function markAmodule(dom){
	olpfElement.push(dom);
	jQuery(dom).addClass(defineModuleClass);
}

function olpfMouseUp(e) {	
		isolpfmousedown=false;	
}

function isolpfmousedownF() {

	return isolpfmousedown;
}
function setolpfmousedown(b) {
	   isolpfmousedown = b;
}
/**
 * 检查数组中是否存在该元素
 * 
 * @param e
 * @return
 */
function olpfContains(e) {
	for ( var i = 0; i < olpfElement.length; i++) {
		if (e === olpfElement[i])
			return true;
	}
	return false;
}
/**
 * 在数组中删除对象
 * 
 * @param e
 * @return
 */
function olpfDel(e) {
	for ( var i = 0; i < olpfElement.length; i++) {
		if (e === olpfElement[i]){
			olpfElement.splice(i, 1);
			jQuery(olpfElement[i]).removeClass(defineModuleClass);
		}
	}
}

function clearElement() {
	try {
		for ( var i = 0; i < olpfElement.length; i++) {
			olpfChangeStyle(olpfElement[i], 3);
			jQuery(olpfElement[i]).removeClass(defineModuleClass);
		}
		olpfElement.splice(0, olpfElement.length);
	} catch (e) {
		myAlert(e);
	}
}

function getExtraDiv(){
	if(document.getElementById('extraDiv')==null){
		jQuery('body').append("<div id='extraDiv'>&nbsp;</div>");
	}
	return jQuery("#extraDiv");
}

jQuery(document)
		.ready(function() {
			extraDiv=getExtraDiv();
			try{
			if (document.all) {// 加入事件，最好在此加入
				document.attachEvent('onbeforeunload', olpftooltipsFree); // onbeforeunload
				document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
				document.attachEvent("DOMNodeRemovedFromDocumen",
						dOMNodeRemovedFromDocumen);
			} else {
				document.addEventListener('beforeunload', olpftooltipsFree,
								false);
				document.addEventListener('mouseup', olpfMouseUp,
								false);
				document.addEventListener("DOMNodeInsertedIntoDocument",
						dOMNodeRemovedFromDocumen, false);
			}
			if(typeof ActiveXObject!='undefined')
			{
				olpfBrower = "ie";
				oriActiveXObject = ActiveXObject;
				ActiveXObject = activeXObject;
			}else{
			var brower = navigator.userAgent.toLowerCase();
			if (brower.indexOf("firefox") >= 0) {
				olpfBrower = "f";
			} else if (brower.indexOf("opera") >= 0) {
				olpfBrower = "op";
			} else  {
				
			}
			}}catch (e) { // 出错处理，完善后无警告
				myAlert(e);
			}		
			try {
				olpfWaitDiv2 = jQuery("#waitDiv");			
				// ///////////为指定元素加入事件
				var addtags = {
					tipElements : "table",
					add : function() {
						addTagAction(this.tipElements);
					}
				};

				for ( var k = 0; k < olpfsTagss.length; k++) {
					addtags.tipElements = olpfsTagss[k];
					addtags.add();
				}// 将标签加上鼠标动作处理函数////////////////
			
				for ( var k = 0; k < olpfsInputTags.length; k++) {
					jQuery(olpfsInputTags[k]).hover(function(e) {
						e.stopPropagation();
						HideOuDiv();
					}, function(e) {
					}); // 设置鼠标经过时隐藏定义框
					jQuery(olpfsInputTags[k]).mousedown(function(e) {
						e.stopPropagation();

					});
				}
				// ////为输入框等元素加入事件处理
				// 为链接加入单击事件,拦截打开链接事件
				jQuery("a").click(function(e) {
					e.stopPropagation();	
					if (e.altKey == true) {
						if (olpfContains(this) == true) {
							olpfChangeStyle(this, 3);
							olpfDel(this); // 删除本对象
						} else {
							olpfChangeStyle(this, 2);
							markAmodule(this);
						}    //特别增加
					}
					try{				
						  window.event.returnValue = false; // 阻止页面跳					
					}catch(e){		
						return false;
					}finally{
						return false;
					}
				});
				var child;   // 去掉链接内的元素绑定的方法
				jQuery("a").each(function(e) {
					child=jQuery(this).children();
					 if(child.length>0){
						 child.each(function(e) {
							unBind(jQuery(this));	
					   });
					 }
				});
				var waitDiv0 = "<div id='waitDiv0' style='background-color:#66CCFF;width:100%;height:100%;'>&nbsp;</div>";
				extraDiv.append(waitDiv0);
				olpfWaitDiv = jQuery('#waitDiv0');
				olpfWaitDiv.click(function(e) {
					clearTooltips();
					// showOuDiv();
					});
				olpfWaitDiv.css( 
						{
					position:'absolute',		
					left : '0px',
					top : '0px',
					width : '100%',
					height : jQuery('body').outerHeight(),
					'z-index' : 214748366,
					'background-color' : '#66CCFF',
					opacity : 0.2
				})
				olpfWaitDiv2.css( {
					position:'absolute',
					left : '0px',
					top : '0px',
					width : '100%',
					height : '100%',
					'z-index' : 214748366,
					'background-color' : '#66CCFF',
					opacity : 0.2
				});
				// 加入定义框的4个边框层
				var oudiv = "<div id='oudivt' style='position:absolute;z-index:2147483643;background:;border-top:2px solid #FF0000;height:2px'></div>"
						+ "<div id='oudivd' style='position:absolute;z-index:2147483646;background:;border-top:2px solid #FF0000;height:2px'></div>"
						+ "<div id='oudivl' style='position:absolute;z-index:2147483645;background-color:#FF0000;width:2px'></div>"
						+ "<div id='oudivr' style='position:absolute;z-index:2147483644;background-color:#FF0000;width:2px'></div>";
				extraDiv.append(oudiv);
				oudivt = jQuery('#oudivt');
				oudivd = jQuery('#oudivd');
				oudivl = jQuery('#oudivl');
				oudivr = jQuery('#oudivr');
				addMyDialog();
		
			} catch (e) { // 出错处理，完善后无警告
				myAlert(e);
			} finally {
				
				window.onload=sonload;
				document.onload=sdonload;
				olpfWaitDiv2 = jQuery("#waitDiv");
				olpftopTips = jQuery("#topTips");
				olpfWaitDiv2.appendTo('body'); // 将等待层加到文档最后，以防特征出错
				unBind(olpfWaitDiv2);
				olpfWaitDiv2.hide(); // 隐藏层
				olpftopTips.appendTo('body'); // 将等待层加到文档最后，以防特征出错
				unBind(olpftopTips);
				olpftopTips.hide(); // 隐藏层
				olpfWaitDiv.hide(); // 隐藏层
				
			}
		});


function addTagAction(tipElements) {

	// hover修正了使用mouseout事件的一个常见错误）。
	jQuery(tipElements)
			.hover(function(e) { // 鼠标经过函数，将边框变粗变色
				     eval(jQuery(this).attr("onmouseover"));
						e.stopPropagation();			       
						
						// 如果选中了元素但未按定义
						if (olpfElement.length > 1 && e.altKey == false) {
							HideOuDiv();
							return;
						}
						// 文字太短则不可定义
						if (jQuery(this).text().toString().replace(/\s+/g, "").length < 2) {
							HideOuDiv();
							return;
						}
						// 如果ctrl按下和鼠标按下
						if (isolpfmousedownF() == true && e.altKey == true) {
							// 如果已选了，则再次经过时变为未选
							if (olpfContains(this) == true) {
								olpfChangeStyle(this, 3);
								olpfDel(this);
								return;
							} else {
								olpfChangeStyle(this, 2);
								markAmodule(this);
							}
							
						}else{
						   olpfChangeStyle(this, 1);
						}
					},

					function(e) {
					    eval(jQuery(this).attr("onmouseout"));
						e.stopPropagation();
					    HideOuDiv();
					// 停目鼠标事件冒泡
				});
	jQuery(tipElements)
			.mousedown(function(e) { // //鼠标按下函数，显示定义窗口
						// 傲游屏弊了右键，因此不可单靠右键显示窗口			
						if (e.which != 1) {
							return;
						}
						e.stopPropagation();
						setolpfmousedown(true);
						// 文字太短则不可定义
						if (jQuery(this).text().toString().replace(/\s+/g, "").length < 2){
							return;
						}
						// ctrl 按下了，多选模式
					// alert(e.ctrlKey);
						
						if (e.altKey == true) {
							
							if (olpfContains(this) == true) {
								olpfChangeStyle(this, 3);
								olpfDel(this); // 删除本对象
							} else {
								olpfChangeStyle(this, 2);
								markAmodule(this);
							}

							return;
						} else if (olpfContains(this) == true) {
							setolpfsUrl(olpfsThisUrl);setPath(getPath());showDefineDiv(); // 显示定义窗口
							return;
						} else if (olpfElement.length > 1) {
							clearElement();
							return;
						} else {
							var sclick = jQuery(this).attr( // 如果此标签原来有方法
									"onclick");
							var smousedown = jQuery(this).attr("onmousedown");
							olpfsy0 = e.clientY; // 改变当前纵坐标
							markAmodule(this);
							if (jQuery(this).attr("tagName") == "A") {
								// jQuery('body').append(window.event.returnValue+'1');
								olpfsHref = e.currentTarget; // 保存当前链接
							} else {
								if (sclick != null // 如果原来有方法，则执行其方法
										|| smousedown != null) {
									eval(sclick); // 如果原来有方法，则执行其方法
									eval(smousedown);
								} else {
									setolpfsUrl(olpfsThisUrl);setPath(getPath());showDefineDiv(); // 显示定义窗口;
									// //
									// 显示定义窗口
								}
							}
						}

					});

}
function dOMNodeRemovedFromDocumen(evt) {
	// alert(evt);
	addTagAction(evt);
}

/**
 * 得到所有结点的特征，用于定义时提交
 * 
 * @return
 */
function getPath() {
	return getFeature(olpfElement,jQuery);
}
function setPath(path){
	
		olpfpaths = path;
	
}

/**
 * 打开链接
 */
function olpfOpenURL() {
	// 先删除层，再打开窗口

	olpfWaitDiv2.show('fast'); // 先用层覆盖
	olpftopTips.show('fast'); // 先用层覆盖
	window.open(showWebPageURL + olpfsHref, "_self");
	
}
function clearTooltips() {
	// window.document.onmouseup = olpfMouseUp;
	try {

		olpfWaitDiv.hide();
		olpfWaitDiv2.hide();
		clearElement();
		document.getElementById('keDialog').style.display='none';
		
	} catch (e) {
		myAlert(e);
	}
}

// 显示定义窗口
function showDefineDiv() {
	markDefineTags();
	jQuery("#path").attr("value",olpfpaths);
	showDialog();
}
function submitNewPath(){
	 var regURL = new RegExp(showWebPageURL, "g");// 删除平台更改的网址
	 var regIURL = new RegExp(olpfIfrmeIUrl, "g");
	 var mysource=document.documentElement.innerHTML;
	 mysource = mysource.replace(regURL, '').replace(regIURL, '');
	 var paths=jQuery("#path").attr("value");
	modifyModulePathByJs.modifyModulePath(moduleId, paths,mysource, function(data) {
		alert(data);
		clearTooltips();
	});
}

/**
 * 提交添加模块信息，返回模块内容以供用户确认
 * 
 * @return
 */
function checkmonitor() {
    
}

/**
 * 改变样式
 * 
 * @param e
 *            DOM对象
 * @param type
 *            类型，1为经过选中，0为不选中，2为选中并将背景变色,3为将背景恢复
 * @return
 */
function olpfChangeStyle(e, type) {
	/*
	 * 
	 * olpfCElement.css({ 'font-size':10 });
	 * olpfCElement.width(olpfCElement.width()+4); e.style.border='1px solid
	 * #000000'; //jQuery('body').append(olpfCElement.css('font-size'));
	 */
	try {
		olpfCElement = jQuery(e);
		olpfPosition = olpfCElement.offset();
		if (type == 1) { // 改变边框层位置和大小
			olpfHeight = olpfCElement.outerHeight(); // 获取外部高度
			olpfWidth = olpfCElement.outerWidth();
			oudivt.css( {
				top : olpfPosition.top  + 'px',
				left : olpfPosition.left  + 'px',
				width : olpfWidth  + 'px'
			});
			oudivl.css( {
				top : olpfPosition.top + 'px',
				left :  olpfPosition.left  + 'px',
				height : olpfHeight + 'px'
			});
			oudivd.css( {
				top :olpfPosition.top + olpfHeight-2 + 'px',
				left :  olpfPosition.left + 'px',
				width : olpfWidth + 'px'
			});
			oudivr.css( {
				top : olpfPosition.top + 'px',
				left : olpfPosition.left + olpfWidth-2 + 'px',
				height : olpfHeight + 'px'
			});
        } else if (type == 2) {
			e.style.background = '#9966ff';
		} else if (type == 3) {
					e.style.background = '';
		}
		 else if (type == 4) {
		 e.style.border='';
			}
	} catch (e) {
		myAlert(e);
	}
}

/**
 * 隐藏定义框div
 * 
 * @return
 */
function HideOuDiv() {
	oudivt.css( {
		width : '0px'
	});
	oudivd.css( {
		width : '0px'
	});
	oudivl.css( {
		height : '0px'
	});
	oudivr.css( {
		height : '0px'
	});
}

function hideOther() {
	HideOuDiv();
}

function addMyDialog(){
	var html="<center><span>原特征：<b id='oldPath'></b></span> 			  <p>新特征：</p> 			  <p> 		      <textarea name='path' id='path' cols='45' value='' onblur='checkPathOfInput(this)'></textarea> 		    </p> 	" +
	" <p>参考特征：</p><p> <span name='reference' id='reference'>参考行特征</span>		    </p>"+
	"	    <p> 		      <input type='button' name='button' id='checkmonitor' value='确定' onclick='submitNewPath();'> 		      <input type='button' name='button2' id='button2' value='取消' onclick='clearTooltips();'> 		    </p></center>";
	addDialogContent(html);
	jQuery("#oldPath").text(oldpath);
	jQuery("#keDialog").mousedown(function(e){
        e.stopPropagation();
	});
	jQuery("#keDialog").click(function(e){
        e.stopPropagation();
	});
}


function checkPathOfInput(tag){
	resumeDefineTags();
	if(checkPath(jQuery(tag).attr("value"))==false){
	
		document.getElementById("checkmonitor").disabled=true;
		alert("特征不正确，请重新编辑！");  
	}else{
		markDefineTags();
		document.getElementById("checkmonitor").disabled=false;
	}
}

function checkPath(path){
	try{
	 var tags=jQuery(path);
	 if(tags.length==0){
		 return false;
	 }else{
		 olpfElement=new Array();
		 tags.each(function(i){
			 markAmodule(this);
		 });
		 return true;
	 }
	}catch(e){
		myAlert(e);
		return false;
	}
}


function markDefineTags(){
	for ( var i = 0; i < olpfElement.length; i++) {
		olpfChangeStyle(olpfElement[i], 2);
	}
}

function resumeDefineTags(){
	for ( var i = 0; i < olpfElement.length; i++) {
		jQuery(olpfElement[i]).removeClass(defineModuleClass);
		olpfChangeStyle(olpfElement[i], 3);
	}
}