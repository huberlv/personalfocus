
var isMouseDown = false;
window.onmouseup = olpfMouseUp;
var isComModule = false;// 是否为布 局模式
var currentTextID = 0;
var isDisplay = false;
var editing = false;  
var borderW = 0;
var isColorPickerOpened = false;// 是否已打开调色板
var divNeedToBeUpdate=new Array();
var tscrollTop=0;
function errorHandler() {
	checking=false;
	window.setTimeout(checkUpdate,60000); //网络中断，一分钟后再检查
}
DWREngine.setErrorHandler(errorHandler);
DWREngine.setWarningHandler(errorHandler);
/**
 * 隐藏用户模块
 * 
 * @param userModuleId
 * @return
 */
function hideUserModuel(userModuleId){
    try {
        updateUserModule.hideUserModuel(userModuleId, function(returnData){
            alertByDiv("<center>"+returnData+"</center>"
            		+"							<center>"
            		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
            		+"							</center>");
            jQuery("#" + userModuleId).remove();
        });
    } 
    catch (e) {
    	myAlert(e);
        
    }
}

/**
 * 展示更新，警告框，背景音乐等
 * 
 * @param twindow
 * @return
 */
function displayUpdate(twindow,isUpdate){

    try {

        if (isUpdate == true) {
			
            var tid = parseInt(twindow.id);
			addDivNeedToBeUpdate(tid); // 将需要展示的层保存
            tid = jQuery("#" + tid).parent().attr("id").replace('group', 'groupTag');
			var  tag=jQuery("#" + tid);
            tag.removeClass("updateGroupStyle");
			tag.addClass("updateGroupStyle");
		    var id=parseInt(twindow.id);
			jQuery("#mark"+id).attr("name","update");
			
			showUpdateDiv(1);
        }
	
        if (isUpdate == true && isDisplay == false) {
          
            var contains = false;
            
            for (var i = 0; i < iframess.length; i++) {
                if (iframess[i] === twindow) {
                    contains = true;
                }
            }
			
            if (contains == true) {
			// alert(isDisplay);
            	 isDisplay = true;
              
                alert("你有新的更新信息！"); // 改用js提示框
               // isDisplay = false;
			    window.setTimeout(setIsDisplay, 30000);
			}
        }
	
        iframess.push(twindow);

    } 
    catch (e) {
    	myAlert(e);
    }  
}
function setIsDisplay()
{
	isDisplay=false;
	window.setTimeout(checkUpdate,timeout);
}


var sendIDss = new Array();
/**
 * 设置所有模块为已读
 * 
 * @return
 */
function setReadyAllModules(){
    try {
    	
        var tempIds = new Array();
        jQuery("#" + currentGroup).find("div[class='main-cont']").each(function(e){
            tempIds.push(this.id);
        });
        var contains = false;
        for (var i = 0; i < iframess.length; i++) {
            for (var h = 0; h < tempIds.length; h++) {
                if (parseInt(iframess[i].id) == tempIds[h]) {
                    contains = true;
                    break;
                }
            }
            if (contains == true) {
                for (var j = 0; j < iframess[i].sendIDs.length; j++) {
                    sendIDss.push(iframess[i].sendIDs[j]);
                }
                iframess[i].sendIDs = new Array();
                contains = false;
                jQuery(iframess[i].document.body).find("*").each(function(e){
                    jQuery(this).removeClass("updateStyle");
                });
            }
        }
        // 用于通信的变量最好设成全局，因为局部变量是会销毁 的，而通信是异步的
        updateContentReceiver.deleteARecord(sendIDss, function(e){
            sendIDss = new Array();
        });
		
		jQuery("#" + currentGroup.replace("group","groupTag")).removeClass("updateGroupStyle");
    } 
    catch (e) {
    	myAlert(e);
    }
}

/**
 * 标记一个模块的信息为已读
 * 
 * @param uid
 * @return
 */
function setReadAModule(uid,a){

    try {
        // myAlert(uid);
		a.name="unUpdate";
        var twindow = null;
        for (var i = 0; i < iframess.length; i++) {
            if (iframess[i].id == uid) {
                twindow = iframess[i];
                // myAlert(uid);
                break;
            }
        }
		if(twindow==null)
		{
			return;
		}
		   
        var have=false;
        jQuery(twindow.document.body).find(".updateStyle").each(function(e){
            jQuery(this).removeClass("updateStyle");
            jQuery(this).addClass(jQuery(this).data("sclass"));// 还原原来的class
            have=true;
        });
        if(have==true){
			
		var  tag=jQuery("#"+jQuery("#"+uid).parent().parent().attr("id"));
		var num=tag.find("a[name='update']").length;
		if(num<=0){
			tag=jQuery("#"+tag.attr("id").replace("group","groupTag"));
			tag.removeClass("updateGroupStyle");

		}
		}
        if (twindow != null && twindow.sendIDs.length > 0) {
            updateContentReceiver.deleteARecord(twindow.sendIDs, function(e){
                // twindow.sendIDs.splice(0, sendIDs.length);
                twindow.sendIDs = new Array();
                // myAlert(e);
            });
        }
		
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}  

/**
 * 改变模式，布局模式或普通模式
 * 
 * @return
 */
function changeModule(){
    try {
        if (isComModule == true) {
            isComModule = false;
            jQuery("#module").text("布局模式");
            exitEdit();
        }
        else 
            if (isComModule == false) {
                isComModule = true;
                jQuery("#module").text("退出布局模式");
                edit();
            }
        
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}

/**
 * // 为层加入拖动等动作
 * 
 * @param d
 * @return
 */
function addDivAction(d){ // 为层加入拖动动作
    try {
        var tdiv = jQuery(d);
        var temp = "<div name='tempDiv' id=" + tdiv.attr("id")+"olpfsTemp" +
        ">&nbsp;</div>"; // 为每一模块加入覆盖层
        jQuery(tdiv.parent()).append(temp);
        var tempDiv = jQuery("#" + tdiv.attr("id")+"olpfsTemp");
        borderW = tdiv.outerWidth() - tdiv.innerWidth();
        borderW = borderW / 2; // 边框宽度
        tempDiv.css({
            position: 'absolute',
            "z-index": 2147483646,
            left: parseInt(d.style.left) + borderW,
            top: parseInt(d.style.top)+ borderW,
            width: parseInt(d.style.width),
            height: parseInt(d.style.height),
            opacity: 0.2,
            'background-color': '#66CCFF'
        });
        
        tempDiv.data("tTop", 0);
        tempDiv.data("tLeft", 0);
        tempDiv.hover(function(e){
            e.stopPropagation();
            if (e.altKey == true) {
                this.style.cursor = 'n-resize';
            }
            else {
                this.style.cursor = 'crosshair';
            }
            // 变大小
        }, function(e){
            e.stopPropagation();
            // this.style.cursor = 'auto';
        });
        tempDiv.mousedown(function(e){
            e.stopPropagation();
            jQuery(this).data("tTop", e.clientY);
            jQuery(this).data("tLeft", e.clientX);
            isMouseDown = true;
            jQuery(this).css({
                "z-index": 2147483647
            });
			dropDiv=this;
        });
    } 
    catch (e) {
    	myAlert(e);
    }  
}
var dropDiv=null;
function addBodyAction(){
	
	jQuery("body").mouseup(function(e){
		isMouseDown = false;
		dropDiv=null;
		e.stopPropagation();
	});
	jQuery("body").mousemove(function(e){
		if(dropDiv==null){
			return;
		}
		try {
			e.stopPropagation();
			if (e.altKey == true) {
				dropDiv.style.cursor = 'n-resize';
			}
			else {
				dropDiv.style.cursor = 'crosshair';
			}
			if (isMouseDown == true) {
			
			    dropDiv.style.cursor = 'crosshair';
				var cdiv1 = jQuery(dropDiv);
				var idiv = jQuery("#" + parseInt(dropDiv.id)); // 内层div
				var top = cdiv1.offset().top;
				var left = cdiv1.offset().left;
				var width = cdiv1.width();
				var height = cdiv1.height();
				if (e.altKey == true) { // 上
				    dropDiv.style.cursor = 'n-resize';
					cdiv1.css({
						// "z-index" : 2147483647,
						'height': height + e.clientY - cdiv1.data("tTop"),
						'width': width + e.clientX - cdiv1.data("tLeft")
					});
					checkLocation(cdiv1);
					var disWH = 0;
					if (olpfBrower == "ie") {
						disWH = 2 * borderW;
					}
					var oterHeight = 0;
					idiv.find("iframe").each(function(e1){
						jQuery(dropDiv).width(cdiv1.width() - disWH);
						idiv.find("div[class='main-cont-tit']").each(function(e){
							oterHeight = oterHeight + jQuery(dropDiv).outerHeight();
						});
						jQuery(dropDiv).height(cdiv1.height() - oterHeight - disWH);
					});
					idiv.css({
						'height': cdiv1.height(),
						'width': cdiv1.width()
					});
					/*
					 * jQuery(cdiv1.find("#olpfs"+this.id)).attr("height",cdiv1.height()
					 * +e.clientY- cdiv1.data("tTop")
					 * -jQuery(cdiv1.find("ifame[class='main-cont-tit']")).height()*2);
					 * cdiv1.find("#olpfs"+this.id).width=cdiv1.width() +
					 * e.clientX - cdiv1.data("tLeft");
					 */
					cdiv1.data("tTop", e.clientY);
					cdiv1.data("tLeft", e.clientX);
					var tw=getTwindow(parseInt(dropDiv.id));
					
					if(tw!=null){
						jQuery(tw.document.body).width(width + e.clientX - cdiv1.data("tLeft"));
						jQuery(tw.document.body).height(height + e.clientY - cdiv1.data("tTop"));
					}
				}
				else 
					if (e.altKey == false) {
						dropDiv.style.cursor = 'crosshair';
						var to = top + e.clientY - cdiv1.data("tTop");
						var le = left + e.clientX - cdiv1.data("tLeft");
						// myAlert(to);
						// myAlert(le);
						cdiv1.css({
							// "z-index" : 2147483647,
							'top': to + "px",
							'left': le + "px"
						});
						checkLocation(cdiv1);
						idiv.css({
							'top': to + "px",
							'left': le + "px"
						});
						// checkLocation(idiv);
						cdiv1.data("tTop", e.clientY);
						cdiv1.data("tLeft", e.clientX);
					}
			}
			else {
				dropDiv.style.cursor = 'crosshair';
				isMouseDown = false;
			}
		} 
		catch (e) {
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
function containsID(id){
    try {
        for (var i = 0; i < divIds.length; i++) {
            if (divIds[i] == id) { // 如果存在，则返回true
                return true;
            }
        }
        pushDivIds(id);
        return false; // 否则加入到容器中，返回false
    } 
    catch (e) {
    	myAlert(e);
        return false;
    }
    
    
}

function edit(){
    try {
        jQuery("div[class='main-cont']").each(function(e){
            addDivAction(this);
        });
        editing = true;
		addBodyAction();
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}

function exitEdit(){
    try {
        editing = false;
        jQuery("div[name='tempDiv']").each(function(e){
            unBind(jQuery(this));
            jQuery(this).remove();
        });
        // myAlert(111);
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}

function olpfMouseUp(e){
    isMouseDown = false;
}

var checking=false;
/**
 * 检查模块是否更新
 */
function checkUpdate(){
	try {
		if(checking==true)
		{
			return ;
		}
		checking=true;
		checkUpdateUserModule.checkUpdate(userId, function(data){
			checking=false;
			if(data!=null){
				if(parseInt(data[0])==-1){				
					alert("登录超时或已退出，请重新登录！");
					window.location.href="/personalfocus/";
					return;
				}else if(parseInt(data[0])==0){	
					window.setTimeout(checkUpdate,timeout);
					return;
				}
				for(var i=0;i<data.length;i++){
					if(document.getElementById(data[i]+'olpfs')==null){
						alert('你有订阅更新，请查看！');
						window.location.href="/personalfocus/user/myfocus";
						return;
					}                 
				}	
				for(var i=0;i<data.length;i++){
                   flushModule(data[i]+'olpfs');		
				}	
				window.setTimeout(checkUpdate,timeout);
			}else{
				window.location.href="/personalfocus/user/myfocus";
			}
			
		});
	}catch(e){
		checking=false;
		myAlert(e);
		window.location.href="/personalfocus/user/myfocus";
		
	}
}


jQuery(document).ready(function(){
	
	if(jQuery(".main-cont").length==0){//如果没有更新或订阅关注展示，则1秒后以“推模式”检查更新
        window.setTimeout(checkUpdate,1000);
	}else{
		window.setTimeout(checkUpdate,timeout);
	}
   
    try {

        var brower = navigator.userAgent.toLowerCase();
        
        if (brower.indexOf("firefox") >= 0) {
            olpfBrower = "f";
        }
        else 
            if (brower.indexOf("opera") >= 0) {
                olpfBrower = "op";
            }
            else 
                if (brower.indexOf("chrome") >= 0) {
                    olpfBrower = "chrome";
                }
            else {
                olpfBrower = "ie";
            }
        // jQuery("#login").show(1);
        // if(olpfBrower=="f") //在火狐中改高度，其余不改
        displayOther();
        editHeight();
        compositionOther();
        
        if (document.all) {// 加入事件，最好在此加入
            document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
            document.attachEvent('onbeforeunload', olpftooltipsFree); // onbeforeunload
            // document.addEventListener('mouseup', olpfMouseUp,true);
        }
        else {
            document.addEventListener('beforeunload', olpftooltipsFree, false);
            document.addEventListener('mouseup', olpfMouseUp, true);
            // document.attachEvent('onmouseup', olpfMouseUp); // onbeforeunload
        }
        
        // autoComposition();
        // //金泉的
        jQuery('#colorPicker').hide();
        var f = jQuery.farbtastic('#picker');
        var p = jQuery('#picker').css('opacity', 0.25);
        var selected;
        jQuery('.colorwell').focus(function(){
            if (jQuery(this).attr('id') == 'color1') {
                jQuery(selected).css('opacity', 0.75).removeClass('colorwell-selected');
                f.linkTo('#lhpopciones');
            }
            if (jQuery(this).attr('id') == 'color2') {
                jQuery(selected).css('opacity', 0.75).removeClass('colorwell-selected');
                f.linkTo('.main-cont-tit');
            }
            if (jQuery(this).attr('id') == 'color3') {
                jQuery(selected).css('opacity', 0.75).removeClass('colorwell-selected');
                f.linkTo('body');
            }
            if (jQuery(this).attr('id') == 'color4') {
                jQuery(selected).css('opacity', 0.75).removeClass('colorwell-selected');
                f.linkTo('#lhpopciones a');
            }
            p.css('opacity', 1);
            jQuery(selected = this).css('opacity', 1).addClass('colorwell-selected');
        });
        jQuery('.colorwell').click(function(){
            if (jQuery(this).attr('id') == 'color2') {
                var tit = document.getElementById('tit');
                if (tit == null) {
                    jQuery('#background').append("<div class='noModule' style='margin-top:60px ;background-color: #ff0000 ; width:100px'>没有模块</div>");
                    window.setTimeout(showNoModule, 2000);
                }
            }
        });
        
        
        jQuery("#ex01").tabs(".ContactMenu", ".ContactBox", {
            currentTab: 0,
            switchingMode: 'c'
        });
		var top=jQuery("#lhpopciones").outerHeight();
        jQuery("#cover").css({
			height:jQuery("#copyright").offset().top-top
		});
        if (olpfBrower != "ie") {
					jQuery(window).scroll(function() { // jQuery('#friendOuter').css("top")
								// 取出的值带单位，难以相加,用document.getElementById('friendOuter').offsetTop
								tscrollTop = jQuery(this).scrollTop();

							});
        }
        
        if(jQuery("div[class='main-cont']").length==0){
        	alertByDiv("<center>暂无订阅更新，平台<b style='color:#FF0000'>每10分钟</b>自动识别订阅更新！正在异步检查，你可继续其它工作，有更新后本页面自动通知！单击'显示所有关注'可查看你的订阅！"
            		+"							</center>"
            		+"							<center>"
            		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
            		+"							</center>");
        }
    }catch (e) {
    	myAlert(e);
    }
});


function setDivHAW(userModuleId){
    try {
        jQuery("#" + userModuleId).css({
            height: 'auto'
        });
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}

function getStyle(e, height){
    try {
        var cdiv = jQuery(e);// 当前div元素
        // position: absolute;height:156;width:250;top:3000px;left:0px
        
        var twidth;
        if (olpfBrower == "ie") {
            twidth = cdiv.outerWidth();
            height = cdiv.height() - height;
        }
        else {
            twidth = cdiv.width();
            var borderW = cdiv.outerWidth() - cdiv.innerWidth();
            height = cdiv.height() - height - borderW;
        }
        var temp = "position:" + cdiv.css("position") + ";top:" + cdiv.offset().top +
        ";left:" +
        cdiv.offset().left +
        ";width:" +
        twidth +
        ";height:" +
        height +
        ";display:none;";
        return temp;
    } 
    catch (e) {
    	myAlert(e);
        return "";
    }
    
    
}
/**
 * 删除一个用户模块
 * 
 * @param uid
 * @return
 */
function deleteUserModuleAndSubNew(uid,url){
	     updateUserModule.deleteUserModule(uid, function(e){
            unBind(jQuery("#" + uid));
            jQuery("#" + uid).remove();
          
        });
		window.open(olpfBase+"/personalfocus/user/showWebPage?url="+url,"_blank");
}

/**
 * 删除一个用户模块
 * 
 * @param uid
 * @return
 */
function deleteUserModule(uid){
    try {
        var r = confirm("你确定要删除此模块？")
        if (r == false) {
            return;
        }
        
        updateUserModule.deleteUserModule(uid, function(e){
            unBind(jQuery("#" + uid));
            jQuery("#" + uid).remove();
            
            alertByDiv("<center>"+e+"</center>"
            		+"							<center>"
            		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
            		+"							</center>");
			
        });
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}

function saveCom(){
    if (window.location.href.toString().charAt(
			window.location.href.toString().length - 1) == '0') {
    	var styles = new Array();
        var uids = new Array();
        jQuery("#" + currentGroup).find("div[class='main-cont']").each(function(e){
        
            var cdiv = jQuery(this);// 当前div元素
            uids.push(this.id); // position:
            // absolute;height:156;width:250;top:3000px;left:0px
            var height = 0;
            // myAlert(cdiv.outerHeight());
            cdiv.find("div[class='main-cont-tit']").each(function(g){
                height = height + jQuery(this).outerHeight();
            });
            styles.push(getStyle(this, height));
        });
        updateUserModule.updateStyle(styles, uids, function(e){
        	
        });
    }else{
    	
    }
    
}
/**
 * 保存布局
 * 
 * @return
 */
function saveEdit(){
	
    try {       
        saveTheme();
        saveCom();
        editablePattern();
       
        alertByDiv("<center>保存成功！"
        		+"							</center>"
        		+"							<center>"
        		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
        		+"							</center>");
    } 
    catch (e) {
    	myAlert(e);
    }  
}



/**
 * 打开调色板
 * 
 * @return
 */
function changeColor(){
    try {
        if (isColorPickerOpened == true) {
            isColorPickerOpened = false;
            jQuery("#col").text("更改外观");
            jQuery('#colorPicker').fadeOut("slow");
        }
        else 
            if (isColorPickerOpened == false) {
                isColorPickerOpened = true;
                jQuery("#col").text("关闭面板");
                jQuery('#colorPicker').fadeIn("slow");
            }
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}



function showNoModule(){
    jQuery('.noModule').fadeOut("slow");
	window.setTimeout(checkUpdate,timeout);
    // jQuery('#noModule').remove();
}


/**
 * 改变主题
 * 
 * @return
 */
function changeTheme(themeCss){
    try {
        if (themeCss == null) {
            jQuery('#theme').attr('href', '');
        }
        else 
            jQuery('#theme').attr('href', '/personalfocus/css/userindexcss/' + themeCss + '.css');
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}



/**
 * 保存主题
 * 
 * @return
 */
function saveTheme(){
    try {
        // alert(jQuery('#theme').attr('href'));
        var navColor = jQuery('#lhpopciones').css("background-color");
        var moduleColor = jQuery('.main-cont-tit').css("background-color");
        var bgColor = jQuery('body').css("background-color");
        var linkColor = jQuery('#lhpopciones a').css("color");
        // alert(navColor+" "+moduleColor +" "+bgColor);
        updateTheme.updateTheme(jQuery('#theme').attr('href'), navColor, moduleColor, bgColor, linkColor, function(data){
            // alert(data);
        });
    } 
    catch (e) {
    	myAlert(e);
    }
    
    
}
function enterDisplayAll(){
	var choice=confirm("'布局模式'只在'显示全部关注'时可用，显示后再'进入装扮空间'便可布局,是否显示全部关注?");
	if(choice==true){
		window.location.href="/personalfocus/user/myfocus?onlyShowUpdate=0";
	}
}
/**
 * 进入装扮空间
 * 
 * @return
 */
var isEditable = true;
function editablePattern(){
    try {
    
        if (isEditable == true) {
            isEditable = false;
            if (window.location.href.toString().charAt(
					window.location.href.toString().length - 1) == '0') {
				edit(); // 可布局
				jQuery("#edit")
						.html(
								"<a href='javascript:void(0)' onclick='autoComposition(1);'>自动布局</a>"
										+ "|<a href='javascript:void(0)' onclick='saveEdit();' id='module'>保存</a>"
										+ "|<a href='javascript:void(0)' onclick='changeColor();' id='col'>更改外观</a>"
										+ "|<a href='javascript:void(0)' onclick='editablePattern();'>退出装扮空间</a>");
			}else{
				jQuery("#edit")
				.html(
						"<a href='javascript:void(0)' onclick='enterDisplayAll();'>布局模式</a>"
								+ "|<a href='javascript:void(0)' onclick='saveEdit();' id='module'>保存</a>"
								+ "|<a href='javascript:void(0)' onclick='changeColor();' id='col'>更改外观</a>"
								+ "|<a href='javascript:void(0)' onclick='editablePattern();'>退出装扮空间</a>");
	
			}           
        }
        else 
            if (isEditable == false) {
                isEditable = true;
                jQuery("#edit").html("<a href='javascript:void(0)' onclick='setReadyAllModules();r' id='module'>全部标记为已读</a>|<a href='javascript:void(0)' onclick='editablePattern();'>装扮空间</a>"
                		+"|<a title='剔除不关注的内容' href='javascript:void(0)' onClick='enterHandleUnCheckModule();'>幻灯片模式</a>");
                exitEdit();// 退出装扮空间时，强制关闭编辑功能
                jQuery('#colorPicker').fadeOut("slow");// 退出装扮空间时，强制收起调色板
                isColorPickerOpened = false;
                window.location.href=window.location.href;
            }
    } 
    catch (e) {
    	myAlert(e);
    }  
}
 var tempTop=null;
	var 	templeft=null;
var displayDiv=null;
var showingDisplayDiv=false;
var displayDivOffset=-1;
/**
 * 显示有更新的模块
 */
function showUpdateDiv(increment)
{

	if(showingDisplayDiv==true)
	{
		return;
	}
	displayDivOffset=displayDivOffset+increment;
    if (displayDivOffset + 1 >= divNeedToBeUpdate.length) {
		  	displayDivOffset = divNeedToBeUpdate.length - 1;
		  }
		if (displayDivOffset <= 0) {
			displayDivOffset = 0;
		}
		 
	if(displayDivOffset<=0){

		jQuery("#ArrowLeft").attr("src","/personalfocus/images/userIndexImage/NotArrow Left.png");
	}else{
		jQuery("#ArrowLeft").attr("src","/personalfocus/images/userIndexImage/Arrow Left.png");
	}
    if(displayDivOffset+1>=divNeedToBeUpdate.length){

		jQuery("#ArrowRight").attr("src","/personalfocus/images/userIndexImage/NotArrow Right.png");
	}else{
		jQuery("#ArrowRight").attr("src","/personalfocus/images/userIndexImage/Arrow Right.png");
	}
	showingDisplayDiv=true;

	if(divNeedToBeUpdate.length>0){
	    jQuery('#cover').css({
		      opacity:0.9,'z-index':21474837
	     }).show(1);
		var tid=divNeedToBeUpdate[displayDivOffset];
		tempTop=document.getElementById(tid).style.top;
		templeft=document.getElementById(tid).style.left;
		displayDiv=jQuery("#" + tid);
		tid = jQuery("#" + tid).parent().attr("id");
		var tid2=tid.replace('group', 'groupTag');
		changeGroup(tid,tid2);

		var left=(document.body.clientWidth-displayDiv.width())/2;
        var width=0;
		if(olpfBrower=="ie"){
			width=displayDiv.outerWidth(true);
		}else{
			width=displayDiv.width();
		}
	    jQuery("#dis-control").show(1);
		displayDiv.css({
			top:41,
			left:left,
			'z-index':2147483646
		});
	}
}

/**
 * 加入要显示的层
 * 
 * @param {Object}
 *            id
 */
function addDivNeedToBeUpdate(id){
	for(var i=0;i<divNeedToBeUpdate.length;i++){
		if(id==divNeedToBeUpdate[i]){
			return;		
		}
	}
	divNeedToBeUpdate.push(id);

	if(displayDivOffset<=0){

		jQuery("#ArrowLeft").attr("src","/personalfocus/images/userIndexImage/NotArrow Left.png");
	}else{
		jQuery("#ArrowLeft").attr("src","/personalfocus/images/userIndexImage/Arrow Left.png");
	}
    if(displayDivOffset+1>=divNeedToBeUpdate.length){

		jQuery("#ArrowRight").attr("src","/personalfocus/images/userIndexImage/NotArrow Right.png");
	}else{
		jQuery("#ArrowRight").attr("src","/personalfocus/images/userIndexImage/Arrow Right.png");
	}

}
/**
 * 还原正在显示的模块层的位置
 */
function revertDisplayDiv(){
	    displayDiv.css({
			top:tempTop,
			left:templeft,
			'z-index':1000
		});
}
/**
 * 上一页或者下一页
 * 
 * @param {Object}
 *            increment
 */
function changeDisplayDiv(increment){
	showingDisplayDiv=false;
	revertDisplayDiv();
	if(increment==0){
		divNeedToBeUpdate.slice(0,divNeedToBeUpdate.length);
		jQuery('#cover').hide();
		jQuery("#dis-control").hide();
		return;
	}
	 
	if(jQuery.trim(jQuery("#handleUnCheckPathsModule").text()).length>7){  //判断是否在剔除模式

		handleUnCheckPaths(false);
		showUpdateDiv(increment);
		handleUnCheckPaths(true);
	}else{
		showUpdateDiv(increment);
	}
	
}

/**
 * 隐藏模块显示层
 */
function closeDisplayDiv(){
	showingDisplayDiv=false;
	revertDisplayDiv();
		divNeedToBeUpdate.splice(0,divNeedToBeUpdate.length);
		jQuery('#cover').hide();
		jQuery("#dis-control").hide();
		displayDivOffset=-1;
		handleUnCheckPaths(false);
}

function showHistoyOfModule(userModuleId,moduleId){

	var moduleNum1=parseInt(document.body.clientWidth/(jQuery("#"+userModuleId).width()+10));
	if(moduleNum1==0){
		moduleNum1=1;
	}
	var moduleNum2=parseInt((document.body.clientWidth-100)/(jQuery("#"+userModuleId).height()+10));
	if(moduleNum2==0){
		moduleNum2=1;
	}
	var num=moduleNum1*moduleNum2;
	
	window.open("/personalfocus/user/userModuleHistoryList?userModuleId="+userModuleId+
	"&moduleId="+moduleId+"&moduleNum="+num+"&page=1","_blank");
}
/**
 * 获取RSS FEED
 * @param rssFeed
 * @return
 */
function getRssFeed(rssFeedstr,divId,title) 
{ 
    title= encodeURI(title);
    var rss=document.getElementById("ke-dialog");	
    
    jQuery(".ke-mc").html("<center>"
    		+"								RSS feed:"
    		+"								<span id=\"rssfeed\">http</span>"
    		+"							</center>"
    		+"							<center>"
    		+"								你可使用RSS工具订阅或与好友分享，发布！"
    		+"							</center>"
    		+"							<center>"
    		+"								<input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
    		+"								<input type=\"button\" value=\"复制地址\" onclick=\"copyToClipboard()\""
    		+"									id='copyToClipboard' style=\"display: none;\">"
    		+"							</center>"
    		);
	jQuery("#rssfeed").text(olpfBase+rssFeedstr+"&title="+title);
	rss.style.display="block";
	jQuery(rss).css({
		top:document.getElementById(divId).offsetTop,
		left:document.getElementById(divId).offsetLeft
	}).show('fast');	
	if(typeof clipboardData!='undefined'){
		document.getElementById("copyToClipboard").style.display='inline';

	}
} 

function alertByDiv(content){
	jQuery(".ke-mc").html(content);
	var div=document.getElementById("ke-dialog");
	jQuery(div).css({
		top:200,
		left:document.body.clientWidth/2-jQuery(div).width()/2
	}).show('fast');	
}
/**
 * 复制rss feed 到剪贴板
 */
function copyToClipboard(){
	clipboardData.clearData("Text");
	clipboardData.setData("Text",jQuery("#rssfeed").text());
	
	alertByDiv("<center>已成功复制网址到剪贴板中！"
    		+"							</center>"
    		+"							<center>"
    		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
    		+"							</center>");
	hideKDialog();
	  
}
/**
 * 隐藏rss feed 层
 */
function hideKDialog(){
	document.getElementById('ke-dialog').style.display='none';
}

function showUserSpace(id){
	var id=document.getElementById(id);
	try{
		parseInt(id.value);
	}catch(e){
		alertByDiv("<center>请输入正确的账户(数字)！"
        		+"							</center>"
        		+"							<center>"
        		+"								<br/><input type=\"button\" value=\"确定\" onclick=\"hideKDialog();\">"
        		+"							</center>");
		return;
	}
	window.open("showUserSpace?masterUserId="+id.value,"_blank");
}

function exit(){
	updateUserModule.exit( function(returnData){
        alert(returnData);
        window.location.href="/personalfocus";
    });
}


window.onbeforeunload=function(){
	
	if(typeof updateUserModule!='undefined'){
		
		updateUserModule.exitCheck( function(returnData){
			
	    });
	}
};

function getTwindow(uid){
	    uid=uid+"olpfs";
        for (var i = 0; i < iframess.length; i++) {
        	
            if (iframess[i].id == uid) {
                return iframess[i];
            }
        }
        return null;
}
