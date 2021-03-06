/**
 * 好友列表 2010.10.11
 * 设计思路:鼠标经过头像，弹出信息层；离开头像，信息层消失。但鼠标离开头像移到信息层时，信息层先消失，不能移到信息层。所以设置延时消失，并设canRemove标志。
 */

var WIDTH = 255; // 信息层宽高
var HEIGHT = 150;

var isFriendListShowed = true;
var canRemove = true;
var t;// t = window.setTimeout("removePanel()", 2000);
function removePanel() {
	if (canRemove)
		jQuery('.infoPanel').remove();
}
function removePanelJQuery() {
	jQuery('.infoPanel').remove();
	canRemove = true;
}
function overPanel() {// 只要鼠标在信息层上，信息层就可以消失
	canRemove = false;
}
jQuery(document)
		.ready(function() {

			jQuery('#friendOuter').css( {
				"left" : jQuery(window).width() - 200,
				"height" : jQuery(window).height() - 60
			});
			jQuery('#friendlistPanel').css( {
				"left" : 0,
				"height" : 0
			});
			jQuery('.friendlist').css( {
				"height" : jQuery(window).height() - 60 - 55
			});
			jQuery('#list').css( {
				"height" : jQuery(window).height() - 60 - 55
			});
			jQuery('#closeFriendList').css( {
				"cursor" : "pointer"
			});
			jQuery('.arr').attr("src", "../images/friendList/hideFriend.png");
			jQuery('.arr').css( {
				"vertical-align" : "top",
				"margin" : 2
			});

			// 隐藏好友列表
				jQuery('#closeFriendList').click(function() {
					if (isFriendListShowed) {
					/*	jQuery("#friendOuter").animate( {
							left : jQuery(window).width() - 40,
							opacity : '0.8',
							width : 40
						}, 700);
						jQuery('#closeFriendList').html("<<");*/
						jQuery("#friendOuter").hide();
						isFriendListShowed = false;
					} 
				});

				// 好友列表伸缩
				jQuery('.friendType>div').click(function() {
					// alert("d");
						jQuery(this).next('ul').slideToggle("300");
					});

				jQuery('.typeLayer')
						.click(function() {
							// alert(jQuery(this).attr("src"));
								if (jQuery(this).children('.arr').attr("src") == "../images/friendList/hideFriend.png") {
									jQuery(this)
											.children('.arr')
											.attr("src",
													"../images/friendList/showFriend.png");
								} else {
									jQuery(this)
											.children('.arr')
											.attr("src",
													"../images/friendList/hideFriend.png");
								}
							});

				jQuery('.friend').mouseover(function() {
					jQuery(this).css("background-color", "#CAEDF4");
				});
				jQuery('.friend').mouseout(function() {
					jQuery(this).css("background-color", "white");
				});

				grayscale(jQuery('.offlinePortrait'));// 离线的设为灰色

				/*
				 * 好友列表随滚动移动
				 */
				var a = document.getElementById('friendOuter').offsetTop;
				jQuery(window).scroll(function() { // jQuery('#friendOuter').css("top")
							// 取出的值带单位，难以相加,用document.getElementById('friendOuter').offsetTop
							var b = jQuery(this).scrollTop();
							jQuery('#friendOuter').animate( {
								top : a + b
							}, 150);
						});

				/*
				 * 弹出信息层
				 */
				var isOnThePanel = false;

				jQuery('.offlinePortrait,.onlinePortrait')
						.mouseover(function() {
							//alert(jQuery(this).attr('class'));
						//	alert(jQuery(this).parent().text());
							// isOnThePanel=true;
								window.clearTimeout(t);
								jQuery('.infoPanel').remove();
								jQuery('body')
										.append(
												"<div class='infoPanel' style='position:absolute;width:"
														+ WIDTH
														+ ";height:"
														+ HEIGHT
														+ ";background-color:#F5FDFE;border:2px solid #EBE8EE;z-index:5555;"
														+ "top:"
														+ (jQuery(this)
																.offset().top - 3)
														+ ";left:"
														+ (jQuery(this)
																.offset().left
																- WIDTH - 5)
														+ "'  onmouseover='overPanel()'>"
														+ "<table width='255px'><tr height='120px'><td>"+jQuery(this).parent().text()+"</td></tr><tr height='30px'><td><div style='width:250px;height:26px;background-color:silver;margin:2px'></div></td></tr></table>"
													    +"</div>");
								jQuery(".infoPanel").bind("mouseleave", function(){ //用mouseout会出问题
									  removePanelJQuery();
									});
							});

				jQuery('.offlinePortrait,.onlinePortrait').mouseout(function() {
					t = window.setTimeout("removePanel()", 2000);

				});

			});
function showFriendList() {
	jQuery("#friendOuter").animate( {
		left : jQuery(window).width() - 200,
		opacity : '0.8',
		width : 200
	}, 500);
	isFriendListShowed = true;	
}