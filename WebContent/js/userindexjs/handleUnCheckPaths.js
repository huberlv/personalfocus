//用//[^\n]+去掉注释
var olpfsTagss = new Array("div", "tr", "li", "ul", "td", "a", "code",
		"custom", "dd", "dfn", "dir", "dl", "title", "legend", "marquee",
		"noBR", "ol", "pre", "span", "strike", "tBody", "map", "input", "form",
		"select", "textarea", "button");
// 所有被选中节点的路径
var olpfpaths;
// 当前要定义的元素数组的引用
var olpfElement = new Array();
// 错误处理，完羡后要返回false
onerror = olpfHandleErr
// 鼠标释放函数
var isolpfmousedown = false; // 鼠标是否按下

// 定义边框的4个边框层jquery对象，分别为上下左右
var oudivt = null;
var oudivd = null;
var oudivl = null;
var oudivr = null;
var olpfPosition = null;
var path = "";
/**
 * 出错处理函数
 * 
 * @param msg
 * @param url
 * @param l
 * @return
 */
function olpfHandleErr(msg, url, l) {
	return false;
}

function olpfMouseUp(e) {
	isolpfmousedown = false;
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
		if (e === olpfElement[i])
			olpfElement.splice(i, 1);
	}
}

function clearElement() {
	try {
		for ( var i = 0; i < olpfElement.length; i++) {
			olpfChangeStyle(olpfElement[i], 3);
		}
		olpfElement.splice(0, olpfElement.length);
	} catch (e) {
		myAlert(e);
	}
}
var twindow = null;
var currentId=null;

function addMyDialog(){
	if(document.getElementById("keDialog")!=null){
		return;
	}
	var html="<center>			  <p>剔除特征：</p> 			  <p> 		     <textarea name='path' id='path' cols='45' value='' onblur='checkPathOfInput(this)'></textarea> 		    </p> 	" +
	" <p>参考特征：</p><p> <span name='reference' id='reference'>参考行特征</span>		    </p>"+
		
	"	    <p> 		      <input type='button' name='button' id='checkmonitor' value='确定' onclick='submitNewPath();'> 		      <input type='button' name='button2' id='button2' value='取消' onclick='cancel();'> 		    </p></center>";
	addDialogContent(html);
	jQuery("#keDialog").mousedown(function(e){
        e.stopPropagation();
	});
	jQuery("#keDialog").click(function(e){
        e.stopPropagation();
	});
}

function cancel(){
	hideDialog();
	//handleUnCheckPaths(false);
}

/**
 * 编辑不关注的内容
 */
function handleUnCheckPaths(isHandleUnCheckModule) {
	
	if(isHandleUnCheckModule==false)
	{
		document.getElementById("handleUnCheckPathsModule").innerHTML="<a title='剔除不关注的内容' href='javascript:void(0)' onClick='handleUnCheckPaths(true);'>进入剔除模式</a>";
     	var id=id+"olpfs";
		for ( var i = 0; i < iframess.length; i++) {
			if (iframess[i].id == id) {
				twindow = iframess[i];
			}
		}
		var child; // 去掉链接内的元素绑定的方法
		if(twindow!=null){
			jQuery(twindow.document).find("*").each(function(h) {

			child = jQuery(this).children();
			if (child.length > 0) {
				child.each(function(e) {
					unBind(jQuery(this));
				});
			}
		});
		
		oudivt.remove();
        oudivd.remove();
        oudivl.remove();
        oudivr.remove();
		clearElement();
		}
		
		return;
	}
	currentId=divNeedToBeUpdate[displayDivOffset];
	checkUpdateUserModule.getUncheckPath(currentId,function(e){
		
		var id=currentId+"olpfs";
		document.getElementById("handleUnCheckPathsModule").innerHTML="<a href='javascript:void(0)' onClick='handleUnCheckPaths(false);'>退出剔除模式</a>&nbsp;<a href='javascript:void(0)' onClick='submit();'>剔除</a>";	
		
		try {
		    olpfpaths=null;
		    clearElement();
				for ( var i = 0; i < iframess.length; i++) {
					if (iframess[i].id == id) {
						twindow = iframess[i];
					}
				}
				
				if (twindow == null) {
					return;
				}
				if (twindow.document.all) {// 加入事件，最好在此加入
					twindow.document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
				} else {
					twindow.document.addEventListener('mouseup', olpfMouseUp, false);
				}
				
			} catch (e) { // 出错处理，完善后无警告
				myAlert(e);
			}
        if(e!='null'){			
				jQuery(twindow.document.body).find(e).each(function(i){
					
					olpfElement.push(this);
					olpfChangeStyle(this, 2);
				});
		}
			try {

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
				}

				// 为链接加入单击事件,拦截打开链接事件
				jQuery(twindow.document.body).find("a").each(function(h) {
					jQuery(this).click(function(e) {
						e.stopPropagation();
			
							// jQuery('body').append(window.event.returnValue+'1');
							try{
								if(typeof window.event.returnValue!='undefined'){
									   window.event.returnValue = false; // 阻止页面跳转
									}
							}catch(e){
								return false;
							}finally{
								return false;
							}
						
					});
				});
				var child; // 去掉链接内的元素绑定的方法
				jQuery(twindow.document.body).find("a").each(function(h) {

					child = jQuery(this).children();
					if (child.length > 0) {
						child.each(function(e) {
							unBind(jQuery(this));
						});
					}
				});

				// ////////////

				// 加入定义框的4个边框层
				var oudiv = "<div id='oudivt' style='position:absolute;z-index:2147483643;background-color:#FF0000;height:2px'></div>"
						+ "<div id='oudivd' style='position:absolute;z-index:2147483646;background-color:#FF0000;height:2px'></div>"
						+ "<div id='oudivl' style='position:absolute;z-index:2147483645;background-color:#FF0000;width:2px'></div>"
						+ "<div id='oudivr' style='position:absolute;z-index:2147483644;background-color:#FF0000;width:2px'></div>";
				jQuery(twindow.document.body).append(oudiv);
				oudivt = jQuery(twindow.document.body).find('#oudivt');
				oudivd = jQuery(twindow.document.body).find('#oudivd');
				oudivl = jQuery(twindow.document.body).find('#oudivl');
				oudivr = jQuery(twindow.document.body).find('#oudivr');

			} catch (e) { // 出错处理，完善后无警告
				myAlert(e);
			}
	});
	
}

function addTagAction(tipElements) {
	/*
	 * jQuery(tipElements).each(function(e){
	 * this.addEventListener("DOMNodeInserted", AttributeNodeModified, false);
	 * });
	 */
	// hover修正了使用mouseout事件的一个常见错误）。
	jQuery(twindow.document.body).find(tipElements).each(function(h) {
		jQuery(this).hover(function(e) { // 鼠标经过函数，将边框变粗变色
					e.stopPropagation();
					// 如果ctrl按下和鼠标按下
					if (isolpfmousedownF() == true ) {
						// 如果已选了，则再次经过时变为未选
						if (olpfContains(this) == true) {
							olpfChangeStyle(this, 3);
							olpfDel(this);
							return;
						} else {
							olpfChangeStyle(this, 2);
							olpfElement.push(this);
						}

					} else {
						olpfChangeStyle(this, 1);
					}
				},

				function(e) {
					e.stopPropagation();
					// olpfChangeStyle(this, 4);
				    HideOuDiv();
				// false)
				// olpfChangeStyle(this, 0);
				// 停目鼠标事件冒泡
			});
	});

	jQuery(twindow.document.body)
			.find(tipElements)
			.each(
					function(h) {
						jQuery(this)
								.mousedown(function(e) { // //鼠标按下函数，显示定义窗口
											// 傲游屏弊了右键，因此不可单靠右键显示窗口

											if (e.which != 1) {
												return;
											}
											e.stopPropagation();
											setolpfmousedown(true);
											 if (olpfContains(this) == true) {
												olpfChangeStyle(this, 3);
												olpfDel(this);
												return;
											}else {											
												olpfElement.push(this);
												olpfChangeStyle(this, 2);
											}

										});
					});

}

/**
 * 得到所有结点的路径，用于定义时提交
 * 
 * @return  
 */
function getPath() {
	var cpath= getFeature(olpfElement,jQuery,twindow.document.body);
	cpath=cpath.replace("html>body:eq(0)>","");
	return cpath;
}
function setPath(path) {

	olpfpaths = path;

}


//显示定义窗口
function submit() {
	addMyDialog();
	jQuery("#path").attr("value",getPath());
	jQuery("#keDialog").css({
		 position: 'absolute',
         left: document.body.clientWidth/2-200,
         top: '100px',
         'z-index':2147483647
	}).show('fast');

}

/**
 * 提交剔除的路径
 * @return
 */
function submitNewPath()
{
	var paths=jQuery("#path").attr("value");
	if (paths != "undefined") {
		updateUserModule.updateUnCheckPaths(parseInt(currentId),paths,function(data){
			flushModule(currentId+"olpfs");
			alert(data);
			cancel();
			
		});
	}else{
		var choice=window.confirm("未选择要剔除的内容，确定不剔除任何内容?");
		if (choice == true) {
			paths = "null";
			updateUserModule.updateUnCheckPaths(parseInt(currentId), paths, function(data){
				flushModule(currentId+"olpfs");
				alert(data);			
				cancel();
			});
		}
	}	
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
			var temptop=0;
			
			if (olpfBrower != "ie") {
				temptop=window.parent.tscrollTop;
			}	
			
			oudivt.css( {
				top : olpfPosition.top -temptop + 'px',
				left : olpfPosition.left + 'px',
				width : olpfWidth  + 'px'
			});
			oudivl.css( {
				top : olpfPosition.top -temptop + 'px',
				left :  olpfPosition.left  + 'px',
				height : olpfHeight + 'px'
			});
			oudivd.css( {
				top :  olpfPosition.top  -temptop+olpfHeight-2 + 'px',
				left : olpfPosition.left  + 'px',
				width : olpfWidth + 'px'
			});
			oudivr.css( {
				top :  olpfPosition.top -temptop + 'px',
				left :  olpfPosition.left + olpfWidth-2 + 'px',
				height : olpfHeight + 'px'
			});

		} else if (type == 2) {
			e.style.background = '#9966FF';
		} else if (type == 3) {
			e.style.background = '';
		} else if (type == 4) {
			e.style.border = '';
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
/**
 * 进入幻灯片模式
 * @return
 */
function enterHandleUnCheckModule(){
	 if(jQuery("div[class='main-cont']").length==0){
		alertByDiv("<center>当前页面没有订阅显示，单击'显示所有关注'可查看你的订阅！"
        		+"							</center>"
        		+"							<center>"
        		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
        		+"							</center>");
     	return;
     }
	var tid = null;
	for (var i=0; i<iframess.length; i++) {
	   tid=parseInt(iframess[i].id); 
	   addDivNeedToBeUpdate(tid); // 将需要展示的层保存
    }			
	showUpdateDiv(1);
}
/**
 * 提交后改变选中的内容的样式
 * @return
 */
function changeStyleAfterSubmit(){
	try {
		for ( var i = 0; i < olpfElement.length; i++) {
			jQuery(olpfElement[i]).removeClass("updateStyle");
		}
	} catch (e) {
		myAlert(e);
	}
}

