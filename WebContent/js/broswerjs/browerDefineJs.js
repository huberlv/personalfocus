var $olpfjQuery=jQuery.noConflict();
var olpfBrower = "ie";
var olpfsTagss = new Array("div", "tr", "li", "ul", "td", "a", "code","body",
		"custom", "dd", "dfn", "dir", "dl", "title", "legend", "marquee",
		"noBR", "ol", "pre", "span", "strike", "tBody", "map");
var olpfsInputTags = new Array("input", "form", "select", "textarea", "button");
// 所有被选中节点的特征
var olpfpaths;
// 所有被选中节点的源码
var olpftexts;
var olpfCSSAndCsript=null;
// 当前要定义的元素数组的引用
var olpfElement = new Array();

/**
 * 出错处理函数
 * 
 * @param msg
 * @param url
 * @param l
 * @return
 */
window.onerror = function(msg, url, line) {
	myAlert(e);
}

// 是否显示更多设置的标志
var lessChoice = true;
// 鼠标移过可定义元素的次数，少于0时则清除鼠提示
// var mouseOverTimes = 20;
var olpfsUrl = olpfsThisUrl;
// 当前的从标
var olpfsx0 = window.screen.width / 2 - 300, olpfsy0 = 0;
// 宽度和高度
var olpfstwidth = 600;
var olpfsHref = "";
// 鼠标释放函数
var isolpfmousedown = false; // 鼠标是否按下
var olpfPointx = olpfsx0;
var olpfPointy = 53;

// 定义边框的4个边框层jquery对象，分别为上下左右
var oudivt = null;
var oudivd = null;
var oudivl = null;
var oudivr = null;
// 网站名对象
var olpfWebname = null;
// 栏目名对象
var olpfModulename = null;
// 更多选项的层的对象
var olpfMoreChoiceDiv = null;
var olpfDefineTable = null;
//
var oftooltips = null;
var oftooltipsHight = 0;
var olpfMoreChoiceDivHight = 0;
var showChoiceDiv = null;
// 等待层的对象引用
var olpfWaitDiv = null;
// 临时变量
var olpfstemp = 0;
var olpfDefineTableLeft = olpfsx0;
var olpfDefineTableTop = 20;
var oftooltipsLeft = olpfsx0 + 13;
var oftooltipsTop = 53;
var olpfCElement = null; // 当前元素
var olpfPosition = null;
var olpfHeight = 0;
var olpfWidth = 0;
var olpfTitle = null;
var path = "";
var olpfsparent = window.parent;
var olpfsIsIframe=false;
var sonload=window.onload;
var sdonload=document.onload;
var olpfsMyStyle=null;
var padding=10;// 补白
var defineModuleClass="defineModuleClass";
var ready=false;
function markAmodule(dom){
	if(olpfContains(dom)==false){
		   olpfElement.push(dom);
	    }
	$olpfjQuery(dom).addClass(defineModuleClass);
}

function setolpfsUrl(Url) {
	if(olpfsIsIframe==false)
	  olpfsUrl = Url;
	else{
		olpfsparent.setolpfsUrl(Url);
	}
}
function setolpfsCSSAndCsript(tcss) {
	if(olpfsIsIframe==false)
		olpfCSSAndCsript = tcss;
	else{
		olpfsparent.setolpfsCSSAndCsript(tcss);
	}
}

function olpfMouseUp(e) {
	if(olpfsIsIframe==false) 
		isolpfmousedown=false;
	else{
		olpfsparent.olpfMouseUp(e) ;
	}
}

function isolpfmousedownF() {
	if(olpfsIsIframe==true)
	{		
		return	olpfsparent.isolpfmousedownF();
	}
	return isolpfmousedown;
}
function setolpfmousedown(b) {
	
	if(olpfsIsIframe==false){
	   isolpfmousedown = b;
	}
	else{
		olpfsparent.setolpfmousedown(b);
	}
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
		if (e === olpfElement[i]) {
			olpfElement.splice(i, 1);
			$olpfjQuery(olpfElement[i]).removeClass(defineModuleClass);
		}
	}
}

function clearElement() {
	try {
		for ( var i = 0; i < olpfElement.length; i++) {
			olpfChangeStyle(olpfElement[i], 3);
			$olpfjQuery(olpfElement[i]).removeClass(defineModuleClass);
		}
		olpfElement.splice(0, olpfElement.length);
	
		for ( var j = 0; j < window.frames.length; j++) {
			if(typeof( window.frames[j].clearElement)=='undefined'){
				continue;
			}
			frames[j].clearElement();
		}
	} catch (e) {
		myAlert(e);
	}
}
function initPar(){
	try{
		//初始化值
		olpfsx0 = document.body.clientWidth/ 2 - 300;
		olpfsy0 = 0;
		olpfPointx = olpfsx0;
		olpfDefineTableLeft = olpfsx0;
		}catch(e){
			myAlert(e);
		}
}
var extraDiv;
function init(){
	
	if( isReady==false){
	   readyAction();	
	}
	     try{				
	    	   if(document.addEventListener){
	    		   document.addEventListener('mouseup', olpfMouseUp,false);;
	    	   }else{
	    		   document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
	    	   }			
			}catch (e) { // 出错处理，完善后无警告
				myAlert(e);
			}		
			try {
				// 加入单击选择层并隐藏
			
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
				// ////为输入框等元素加入事件处理
				var temp;
				// ////为输入框等元素加入事件处理
				// 为链接加入单击事件,拦截打开链接事件
				$olpfjQuery("a").click(function(e) {
					e.stopPropagation();					
					try{
						if(typeof window.event.returnValue!='undefined'){
						   window.event.returnValue = false; // 阻止页面跳转
						}
					}catch(e){
						return false;
					}finally{
						return false;
					}
				
					return false;
				});
				var child;   // 去掉链接内的元素绑定的方法
				$olpfjQuery("a").each(function(e) {
					child=$olpfjQuery(this).children();
					 if(child.length>0){
						 child.each(function(e) {
							unBind($olpfjQuery(this));	
					   });
					 }
				});
				// ////////////
				// 为等待覆盖层加入单击事件，单击后清空平台的层
				if(olpfsIsIframe==false){
				
				olpfWaitDiv = $olpfjQuery('#waitDiv0');
				/*olpfWaitDiv.click(function(e) {
					clearTooltips();
					// showOuDiv();
					});*/
				olpfWaitDiv.css( {
					position : 'absolute',
					left : '0px',
					top : '0px',
					width : '100%',
					height : $olpfjQuery('body').outerHeight(),
					'z-index' : 214748356,
					'background-color' : '#66CCFF',
					opacity : 0.2
				})
				}
				showChoiceDiv = $olpfjQuery('#showChoiceDiv');
				showChoiceDiv.css( {
					opacity : 0.8
				});
				HideshowChoiceDiv();
				
				// 加入鼠标提示函数
				if(olpfsIsIframe==false){
				$olpfjQuery("body").mousemove(function(e) {
					e.stopPropagation();				
				});
				
	         /*
				 * $olpfjQuery("body").mouseout(function(e) {
				 * e.stopPropagation(); ///HideOuDiv(); });
				 */
				}
				// 加入定义框的4个边框层
				var oudiv = "<div id='oudivt' style='position:absolute;z-index:2147483643;background:;border-top:2px solid #FF0000;height:0px'></div>"
						+ "<div id='oudivd' style='position:absolute;z-index:2147483646;background:;border-top:2px solid #FF0000;height:0px'></div>"
						+ "<div id='oudivl' style='position:absolute;z-index:2147483645;background-color:#FF0000;width:2px'></div>"
						+ "<div id='oudivr' style='position:absolute;z-index:2147483644;background-color:#FF0000;width:2px'></div>";
				getExtraDiv().append(oudiv);
				oudivt = $olpfjQuery('#oudivt');
				oudivd = $olpfjQuery('#oudivd');
				oudivl = $olpfjQuery('#oudivl');
				oudivr = $olpfjQuery('#oudivr');
				defineIframe();
			} catch (e) { // 出错处理，完善后无警告
				myAlert(e);
			} finally {
				
				 window.onload=sonload;
				
				if(olpfsIsIframe==false){
					
					oftooltips = $olpfjQuery("#oftooltips");
					unBind(oftooltips);
					olpfMoreChoiceDiv = $olpfjQuery("#moreChoice");
					unBind(olpfMoreChoiceDiv);
					olpfDefineTable = $olpfjQuery("#defineTable");
				    unBind(olpfDefineTable);
                    handleDefineDiv();
					
				}
				ready=true;
				document.getElementById('initDiv').innerHTML="";
				$olpfjQuery('#initDiv').appendTo('body');
				unBindOthers();
				alert("已进入订阅模式");
				
			}
}
/**
 * 定义框处理
 * 
 * @return
 */
function handleDefineDiv()
{
	try{
		
		oftooltips.css( {
			'background-color':'#66CCFF',
			opacity : 0.8
		});
		// oftooltips.appendTo('body');
		// unBind(oftooltips);
		if (oftooltipsHight == 0) {
			oftooltipsHight = oftooltips.outerHeight()>10?oftooltips.outerHeight():200;
		}
		if (olpfMoreChoiceDivHight == 0) {
			olpfMoreChoiceDivHight = olpfMoreChoiceDiv.outerHeight()>10?olpfMoreChoiceDiv.outerHeight():600;
		}
		olpfMoreChoiceDiv.hide();
		oftooltips.hide();
		olpfWebname = $olpfjQuery('#webname'); // 网站名对象
		oftooltips.find("input").each( // 取消所有input的方法
				function(h) {  
			$olpfjQuery(this).mousedown(function(e) {
			e.stopPropagation();				
			setolpfmousedown(false);
		});});
		olpfModulename = $olpfjQuery('#modulename'); //
				olpfDefineTable.hide();
	/*
	 * var temp=null; olpfDefineTable.find('img').each( function(i){
	 * temp=this.src.substring(this.src.indexOf("/personalfocus"));
	 * this.src=olpfBase+temp; });
	 */
		// ///////////////
		// 鼠标按下，鼠标变形状
		oftooltips.mousedown(function(e) {
			if (e.which != 1) {
				return;
			}
			olpfPointx = e.clientX;
			// alert(olpfDefineTable.attr('left'));
				olpfPointy = e.clientY;
				setolpfmousedown(true);
				this.style.cursor = 'crosshair';
				e.stopPropagation();
			});
		// 鼠标释放，鼠标恢复原状
		oftooltips.mouseup(function(e) {
			if (e.which != 1) {
				return;
			}
			setolpfmousedown(false);
			this.style.cursor = 'auto';
			e.stopPropagation();
			
		});
		$olpfjQuery('body').mousemove(function(e) {

			if (isolpfmousedownF() == true) // 如果鼠标按下了
				{
					// var offset = olpfDefineTable.offset();
					olpfDefineTableLeft = olpfDefineTableLeft
							+ e.clientX - olpfPointx; // 不能移出屏幕
					// lef = lef > 0 ? lef : 0;
					// lef = lef < window.screen.width - 626 ?
					// lef
					// : window.screen.width - 626;
					olpfstemp = e.clientY - olpfPointy;
					/*
					 * if(olpfstemp>20){ olpfstemp=20; } if(olpfstemp<-20){
					 * olpfstemp=-20; }
					 */
					// alert(offset.top);
					olpfDefineTableTop = olpfDefineTableTop + olpfstemp;
					// to=to>100-$olpfjQuery("#oftooltips").height()?to:100-
					// $olpfjQuery("#oftooltips").height();
					// to=to<window.screen.height-100?to:500;
					olpfDefineTable.css( {

						left : olpfDefineTableLeft + 'px',
						top : olpfDefineTableTop + 'px'
					}); // 慢*/
					// offset = oftooltips.offset();
					oftooltipsLeft = oftooltipsLeft + e.clientX
							- olpfPointx;
					// lef = lef > 13 ? lef : 13;
					// lef = lef < window.screen.width - 613 ?
					// lef
					// : window.screen.width - 613;
					olpfstemp = e.clientY - olpfPointy;
					/*
					 * if(olpfstemp>20){ olpfstemp=20; } if(olpfstemp<-20){
					 * olpfstemp=-20; }
					 */
					oftooltipsTop = oftooltipsTop + olpfstemp;
					// to=to>100-$olpfjQuery("#oftooltips").height()?to:100-
					// $olpfjQuery("#oftooltips").height();

					// to=to<(window.screen.height-100)?to:500;
					oftooltips.css( {
						left : oftooltipsLeft + 'px',
						top : oftooltipsTop + 'px'
					}); // 慢
					olpfPointx = e.clientX;
					olpfPointy = e.clientY;
					// alert(window.screen.height);
				}
				e.stopPropagation();
			
			});
	} catch (e) { // 出错处理，完善后无警告
		myAlert(e);
	} finally {
		 window.onload=sonload;
	     document.onload=sdonload;
		olpfWaitDiv.hide(); // 隐藏层
	    oftooltips.hide();
		olpfDefineTable.hide();

		return "";
	}
}
function addTagAction(tipElements) {
	// hover修正了使用mouseout事件的一个常见错误）。
	$olpfjQuery(tipElements)
			.hover(function(e) { // 鼠标经过函数，将边框变粗变色
				if(((typeof this.name)!='undefined')&&this.name=='noaction'){
					e.stopPropagation();
					return;
				}
				     eval($olpfjQuery(this).attr("onmouseover"));
						e.stopPropagation();			       
						HideshowChoiceDiv(); // 清除选择操作窗口
						// 如果选中了元素但未按定义
						if ((olpfElement.length > 1) && (e.altKey == true)) {
							HideOuDiv();
							return;
						}
						// 文字太短则不可定义
						if ($olpfjQuery(this).text().toString().replace(/\s+/g, "").length < 2) {
							HideOuDiv();
							return;
						}
						// 如果ctrl按下和鼠标按下
						if ((isolpfmousedownF() == true) && (e.altKey == true)) {
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
						if(((typeof this.name)!='undefined')&&this.name=='noaction'){
							e.stopPropagation();
							return;
						}
					    eval($olpfjQuery(this).attr("onmouseout"));
						e.stopPropagation();
					// olpfChangeStyle(this, 4);
					    HideOuDiv();
						// false)
					// olpfChangeStyle(this, 0);
					// 停目鼠标事件冒泡
				});
	$olpfjQuery(tipElements)
			.mousedown(function(e) { // //鼠标按下函数，显示定义窗口
						// 傲游屏弊了右键，因此不可单靠右键显示窗口
				if(((typeof this.name)!='undefined')&&this.name=='noaction'){
					e.stopPropagation();
					return;
				}
						if (e.which != 1) {
							return;
						}
						e.stopPropagation();
						setolpfmousedown(true);
						// 文字太短则不可定义
						if ($olpfjQuery(this).text().toString().replace(/\s+/g, "").length < 2)
							return;
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
							var sclick = $olpfjQuery(this).attr( // 如果此标签原来有方法
									"onclick");
							var smousedown = $olpfjQuery(this).attr("onmousedown");
							olpfsy0 = e.clientY; // 改变当前纵坐标
							markAmodule(this);
							if ($olpfjQuery(this).attr("tagName") == "A") {
								// $olpfjQuery('body').append(window.event.returnValue+'1');
								olpfsHref = e.currentTarget; // 保存当前链接
								showChoice(e.pageX, e.pageY); // 显示定义窗口
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
 * 更多设置改变函数
 * 
 * @return
 */
function checkConfig() {
	lessChoice = $olpfjQuery("#config").attr("checked");
	if (lessChoice == true) {
		olpfMoreChoiceDiv.hide();
		$olpfjQuery('#olpfLeft,#olpfRight').css( {
			height : $olpfjQuery('#oftooltips').height()>10?$olpfjQuery('#oftooltips').height():(oftooltipsHight - olpfMoreChoiceDivHight),
			// height : oftooltipsHight - olpfMoreChoiceDivHight,
			width : 13
		});

	// oftooltips.height($olpfjQuery('#olpfLeft').height());
	} else {
		olpfMoreChoiceDiv.show('fast');
		$olpfjQuery('#olpfLeft,#olpfRight').css( {
			height : $olpfjQuery('#oftooltips').height(),
			// height : oftooltipsHight,
			width : 13
		});
	// oftooltips.height($olpfjQuery('#olpfLeft').height());
	}
}

/**
 * 得到所有结点的特征，用于定义时提交
 * 
 * @return
 */
function getPath() {
	return getFeature(olpfElement, $olpfjQuery);
}

function setPath(path){
	if(olpfsIsIframe==false)
		olpfpaths = path;
		else{
			olpfsparent.setPath(path);
		}
}

function setSource(source){
	if(olpfsIsIframe==false)
		olpftexts = source;
		else{
			olpfsparent.setSource(source);
		}
}
/**
 * 取得所有节点源码
 * 
 * @return
 */
function getSource(path) {
	markModules("");
	try {		
		olpfElement.splice(0, olpfElement.length-1);
		$olpfjQuery(path).each(function(i){
			markAmodule(this);
		});
		var tag;	
        var twidth=0;
        var theight=0;
	
		for ( var j = 0; j < olpfElement.length; j++) {
			olpfChangeStyle(olpfElement[j], 3);
			tag = $olpfjQuery(olpfElement[j]);
			tag.find("a").each(function(e){
				this.href=getURL("",this.href);
			});
		
			if(tag.outerWidth()>twidth){			
				twidth=tag.outerWidth();
			}
			theight=theight+tag.outerHeight();	
		}
		var mysource = document.documentElement.innerHTML;;
		 twidth=twidth+padding;
		 theight=theight+padding;
		 olpfsMyStyle="position: static;height:"+theight+";width:"+twidth+";top:3000px;left:0px;display:none;";
		return mysource;
	} catch (e) {
		myAlert(e);
		return "";
	}finally{
		markModules("solid 2px #FF0000");	
	}
}

/**
 * 打开链接
 */
function olpfOpenURL() {
	// 先删除层，再打开窗口
	HideshowChoiceDiv();
	if(olpfsIsIframe==false){
	window.open(olpfsHref, "_self");
	}else{
		window.open(olpfsHref, "_blank");
	}
	
}

function clearTooltips() {
	// window.document.onmouseup = olpfMouseUp;

	try {
		oftooltips.hide();
		olpfWaitDiv.hide();
		olpfDefineTable.hide();
		clearElement();
		lessChoice = true;
	} catch (e) {
		myAlert(e);
	}
}

function getolpfsMyStyle()
{
   return olpfsMyStyle;
}

function setolpfsMyStyle(style)
{	
		if(olpfsIsIframe==false)
			olpfsMyStyle=style;
		else{
			olpfsparent.setolpfsMyStyle(style);
		}
}

// 显示定义窗口
function showDefineDiv() {
	//olpfWaitDiv.html("&nbsp;");
	//return;
	//unBindOthers();
	initPar();
	markDefineTags();
	if(olpfsIsIframe==true)
	{
		hideOther();
		olpfsparent.showDefineDiv();
		return;
	}	
	
	olpfDefineTableLeft = olpfsx0;
	olpfDefineTableTop = $olpfjQuery(olpfElement[0]).offset().top;
	oftooltipsLeft = olpfsx0 + 13;
	oftooltipsTop = olpfDefineTableTop+33;	
	try {
		$olpfjQuery("#path").attr("value",olpfpaths);
		olpfWaitDiv.css({
			opacity : 0.8
		}).show('fast');
		// 预先获取所有节点特征和文字
		// window.document.onmouseup =null; //先取消窗口的释放鼠标事件
		// alert(window.parent==window);
		if (olpfTitle == null) {
			olpfTitle = document.title.length > 20 ? document.title.substring(
					0, 19) : document.title;// 长度不超过20字符
		}
		olpfWebname.attr('value', olpfTitle);
		// 栏目名对象
		
		oftooltips.css( { // 对傲游，要在此改变属性
					left : oftooltipsLeft + 'px',
					top : oftooltipsTop + 'px'
				}).show('fast');
				} catch (e) {
					myAlert(e);
	}try{
		// 改就图片大小
		checkConfig();
		
		olpfDefineTable.css( {
			left : olpfDefineTableLeft + 'px',
			top : olpfDefineTableTop + 'px'
		}).show('fast'); // 慢
		setolpfmousedown(false);
		hideOther();
	
	} catch (e) {
		myAlert(e);
	}
 // alert($olpfjQuery('#olpfLeft').height());
}

// 显示选择操作窗口，窗口要挡住鼠标，才可拦截跳转
function showChoice(x1, y1) {
	
	showChoiceDiv.css( {
		// opacity:0.8,
		top : y1 - 20 + 'px',
		left : x1 - 20 + 'px'
	}).show(1);
	isShowChoiceDivHide=false;
}

/**
 * 提交添加模块信息，返回模块内容以供用户确认
 * 
 * @return
 */
function checkmonitor() {
	//olpfWaitDiv.html("<br/><br/><br/><br/><center>操作进行中，请稍候</center>");
	//unBindOthers();
	// showOuDiv();
	// window.document.onmouseup = olpfMouseUp; //恢复窗口的鼠标释放事件
	setSource(getSource($olpfjQuery("#path").attr("value")));
	setolpfsMyStyle(getolpfsMyStyle());setolpfsCSSAndCsript(getCSSAndCsript());
var olpfHeadCSSAndCsript=olpfCSSAndCsript[0];
var olpfBodyCSSAndCsript= olpfCSSAndCsript[1];
	try {
		olpfDefineTable.hide();
		oftooltips.hide();
		var modulename = $olpfjQuery("#modulename").attr("value");
		var saveConfig = $olpfjQuery("#saveConfig").attr("checked");
		var isMobile = $olpfjQuery("#isMobile").attr("checked") == true ? 1 : 0;
		var isMail = $olpfjQuery("#isMail").attr("checked");
		olpfTitle = olpfWebname.attr('value');
		// var messageType = $olpfjQuery("#messageReceiveType").attr("value");//
		var messageType = 4;//
		var messageStartTime = $olpfjQuery("#messageStartTime").attr("value");
		var messageStopTime = $olpfjQuery("#messageStopTime").attr("value");
		var messageFrequency = $olpfjQuery("#mobilefrequency").attr("value");
		var messageFrequencyType = $olpfjQuery("#mobileUnit").attr("value");
		var messageMaxNum = $olpfjQuery("#sendMaxCount").attr("value");
		var emailStartTime = $olpfjQuery("#mailStartTime").attr("value");
		var emailStopTime = $olpfjQuery("#mailStopTime").attr("value");
		var emailFrequency = $olpfjQuery("#mailFrequency").attr("value");
		var emailFrequencyType = $olpfjQuery("#mailUnit").attr("value");
        var monitorType = parseInt($olpfjQuery("#monitorType").val())|parseInt($olpfjQuery("#monitorType2").val());
        
        var editpath=$olpfjQuery("#path").attr("value");
        var subgroupId = document.getElementById("subgroup").value;
        var useDefaultConfig = $olpfjQuery("#config").attr("checked");
	
        var values="<div id='values' style='display:none;'><olpfsUrl>"+
		 olpfsUrl+"</olpfsUrl><olpfHeadCSSAndCsript>"+
		 olpfHeadCSSAndCsript+"</olpfHeadCSSAndCsript><olpfBodyCSSAndCsript>"+
		 olpfBodyCSSAndCsript+"</olpfBodyCSSAndCsript><olpfTitle>"+
		 olpfTitle+"</olpfTitle><modulename>"+
		 modulename+"</modulename><olpftexts>"+
		 olpftexts+"</olpftexts><isMobile>"+
		 isMobile+"</isMobile><isMail>"+
		 isMail+"</isMail><messageType>"+
		 messageType+"</messageType><messageStartTime>"+
		 messageStartTime+"</messageStartTime><messageStopTime>"+
		 messageStopTime+"</messageStopTime><messageFrequency>"+
		 messageFrequency+"</messageFrequency><messageFrequencyType>"+
		 messageFrequencyType+"</messageFrequencyType><messageMaxNum>"+
		 messageMaxNum+"</messageMaxNum><emailStartTime>"+
		 emailStartTime+"</emailStartTime><emailStopTime>"+
		 emailStopTime+"</emailStopTime><emailFrequency>"+
		 emailFrequency+"</emailFrequency><emailFrequencyType>"+
		 emailFrequencyType+"</emailFrequencyType><olpfpaths>"+
		 editpath+"</olpfpaths><saveConfig>"+
		 saveConfig+"</saveConfig><useDefaultConfig>"+
		 useDefaultConfig+"</useDefaultConfig> <olpfsMyStyle>"+
		 olpfsMyStyle+"</olpfsMyStyle><monitorType>"+
		 monitorType+"</monitorType><subgroupId>"+
		 subgroupId+"</subgroupId></div>";
		 window.status='olpf_values'+values;
	
		// clearTooltips();
		
	} catch (e) {
		clearTooltips();
		myAlert(e);
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
	 * #000000'; //$olpfjQuery('body').append(olpfCElement.css('font-size'));
	 */
	olpfCElement = $olpfjQuery(e);
	try {
		
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
	HideshowChoiceDiv();
}

function getCSSAndCsript(){
	if(olpfCSSAndCsript!=null)
		return olpfCSSAndCsript;
	var css;
	try{
		var tbase=$olpfjQuery("base").attr("href");

		if(typeof(tbase)=='undefined'){
			tbase=olpfsThisUrl;
		}
		 css="<base href='"+tbase+"'/>";
		var el;
		
	var addCSS={
				addACss:function(e){
		$olpfjQuery(e+" link").each(
			function(e){
				el=$olpfjQuery(this);
				if(el.attr('href').indexOf(olpfBase)<0)
				   css=css+"<link href='"+el.attr('href')+"' rel=\"stylesheet\" type=\"text/css\"/>";
			});
		$olpfjQuery(e+" style").each(			
			function(e){
				el=$olpfjQuery(this);

				  css=css+"<style>"+$olpfjQuery(this).html()+"</style>";
				
			});
	},
	addAScript:	// 不要脚本有可能样式不正确,有些网页如QQ可能是body内的脚本令网页不断刷新
		function(e){
		$olpfjQuery(e+" script").each(		
				function(e){
					el=$olpfjQuery(this);
					if(el.attr('src')!=null)
					{
						if(el.attr('src').indexOf(olpfBase)<0)
						{
							css=css+"<script src='"+$olpfjQuery(this).attr('src')+"' type=\"text/javascript\">"+$olpfjQuery(this).html()+"</script>";
						}
					}
					else{
					   css=css+"<script>"+$olpfjQuery(this).html()+"</script>";
					}
				});
	}
		};
	olpfCSSAndCsript=new Array(2);
	addCSS.addACss('head');	
	addCSS.addAScript('head');
	olpfCSSAndCsript[0]=css;
	css=new String();
	
	addCSS.addACss("body");	
    addCSS.addAScript("body");
	olpfCSSAndCsript[1]=css;
	}catch(e){
		myAlert(e);
		return olpfCSSAndCsript;
	}	
	return olpfCSSAndCsript;
}

function resize(){
	$olpfjQuery("table").css({
		padding:'8px'
	});		
}
function getExtraDiv(){
	if(document.getElementById('extraDiv')==null){
		$olpfjQuery('body').append("<div id='extraDiv'>&nbsp;</div>");
	}
	return $olpfjQuery("#extraDiv");
}
var isReady=false;
function readyAction(){
	
	extraDiv=getExtraDiv();
	try{
	$olpfjQuery(document).keydown(
			function(e) {
				if(e.altKey==true&&e.shiftKey==true){
					resize();
				}				
			}
			);
			
	olpfDefineTable = $olpfjQuery("#defineTable");
	if (olpfDefineTable != null) {
		var temp = null;
		olpfDefineTable.find('img').each(function(i){
			temp = this.src.substring(this.src.indexOf("/personalfocus"));
			this.src = olpfBase + temp;
			
		});
	}
		var olpfsChoice = "<div id=\"showChoiceDiv\"> " // width
						+ " <p><input type=\"submit\"  id=\"openlink\" value=\"打开\" onclick='olpfOpenURL()'/> "
						+ "<input type=\"button\"  id=\"foucs\" value=\"关注\" onclick='setolpfsUrl(olpfsThisUrl);setPath(getPath());showDefineDiv();'/> </p>"
						+ "</div>";
				extraDiv.append(olpfsChoice);
				var waitDiv0 = "<div id='waitDiv0' style='position:absolute;background-color:#66CCFF;width:100%;height:100%;display:none;font-size:24px;color:#FFFFFF'>&nbsp;</center></div>";
				extraDiv.append(waitDiv0);
				
	}catch(e){
		myAlert(e);
	}finally{
		isReady=true;
	}
}

function checkPathOfInput(tag){
	
	resumeDefineTags();
	
	if(checkPath($olpfjQuery(tag).attr("value"))==false){
	   
		document.getElementById("checkmonitor").disabled=true;
		alert("特征不正确，请重新编辑！");  
	}else{
		markDefineTags();
		document.getElementById("checkmonitor").disabled=false;
	}
}

function checkPath(path){
	try{
	 var tags=$olpfjQuery(path);
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
		$olpfjQuery(olpfElement[i]).removeClass(defineModuleClass);
		olpfChangeStyle(olpfElement[i], 3);
	}
}


function unBindOthers(){
	for ( var k = 0; k < olpfsInputTags.length; k++) {
		$olpfjQuery(olpfsInputTags[k]).hover(function(e) {
			e.stopPropagation();
			HideOuDiv();
		}, function(e) {
		}); // 设置鼠标经过时隐藏定义框
		$olpfjQuery(olpfsInputTags[k]).mousedown(function(e) {
			e.stopPropagation();

		});
		$olpfjQuery(olpfsInputTags[k]).click(function(e) {
			e.stopPropagation();
		});
	}
	var ids=new Array();
	ids.push("#reference");
	ids.push("#waitDiv0");
	ids.push("#waitDiv");
	ids.push("#extraDiv");
	ids.push("#initDiv");
	for(var i=0;i<ids.length;i++){	
		 $olpfjQuery(ids[i]).mousedown(function(e){	
			
			 e.stopPropagation();
		 });
		 $olpfjQuery(ids[i]).click(function(e){
			 e.stopPropagation();
			 
		 });
		 $olpfjQuery(ids[i]).dblclick(function(e){
			 e.stopPropagation();		 
		 });
		 $olpfjQuery(ids[i]).hover(function(e) {e.stopPropagation();},function(e) {e.stopPropagation();});
	}
	
}

var isShowChoiceDivHide=false;
function HideshowChoiceDiv(){
	if(isShowChoiceDivHide==false){
		showChoiceDiv.hide();
		isShowChoiceDivHide=true;
	}	
}