<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib
	uri="/struts-tags" prefix="s"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/indexcss/newstyle.css">
		<script language=JavaScript> 
<!-- // BannerAD 

	var bannerAD = new Array();
	var bannerADlink = new Array();
	var adNum = 0;
    
	bannerAD[0] = "/personalfocus/images/silder1.jpg";
	bannerADlink[0] = "/personalfocus/index/showDemonstrate?i=m&n=3";
	bannerAD[1] = "/personalfocus/images/silder2.gif";
	bannerADlink[1] = "/personalfocus/index/showDemonstrate?i=a&n=4";
	bannerAD[2] = "/personalfocus/images/silder3.jpg";
	bannerADlink[2] = "/personalfocus/index/showDemonstrate?i=m&n=3";
	bannerAD[3] = "/personalfocus/images/silder4.jpg";
	bannerADlink[3] = "/personalfocus/index/showDemonstrate?i=w&n=3";


	var explain=new Array();
	explain[0]="个人空间,聚合订阅关注";
	explain[1]="简单、直观的订阅过程";
	explain[2]="幻灯片式在线更新展示";
	explain[3]="创新的手机微网式浏览";
	var preloadedimages = new Array();

	for (i = 1; i < bannerAD.length; i++) {
		preloadedimages[i] = new Image();
		preloadedimages[i].src = bannerAD[i];
	}

	function setTransition() {
	/*	if (document.all) {
			bannerADrotator.filters.revealTrans.Transition = Math.floor(Math
					.random() * 23);
			bannerADrotator.filters.revealTrans.apply();
		}*/
	}

	function playTransition() {
	/*	if (document.all)
			bannerADrotator.filters.revealTrans.play()*/
	}

	function setExplain(i){
	    var exp=document.getElementById("explain");
	    if(exp!=null){	    
	    	exp.innerHTML=explain[i];
	    }	
	}
	var theTimer;
	function nextAd() {
		var idNum=document.getElementById("idNum");      
        if(idNum==null){
        	theTimer = setTimeout("nextAd()", 5000);
        	return; 
        }
		if (adNum < bannerAD.length - 1)
			adNum++;
		else
			adNum = 0;
		setTransition();
		document.images.bannerADrotator.src = bannerAD[adNum];
		setDisplay(adNum);
		setExplain(adNum);
		playTransition();
		theTimer = setTimeout("nextAd()", 5000);
	}
	function setAd(i) {
		clearTimeout(theTimer);
		adNum=i-1;
		nextAd();			
	}
	var background='#006CDB';
	var nbackground='#fff';
    function setDisplay(index){
        var idNum=document.getElementById("idNum");      
        if(idNum!=null){
        	var children=idNum.getElementsByTagName("li");     	
            for(var i=0;i<children.length;i++){
            	children[i].style.background=nbackground;
            }
            if(index<children.length)
            children[index].style.background=background;
        }
    }
    
    
	function jump2url() {
		jumpUrl = bannerADlink[adNum];
		jumpTarget = '_blank';
		if (jumpUrl != '') {
			if (jumpTarget != '')
				window.open(jumpUrl, jumpTarget);
			else
				location.href = jumpUrl;
		}
	}
	function displayStatusMsg() {
		status = bannerADlink[adNum];
		document.returnValue = true;
	}

	//-->
</script>
	</HEAD>
	<BODY>

		<DIV class=g-doc>
			<DIV class=g-hd>
				<H1 class=m-logo>
					<A href=""></A>
				</H1>
				<UL class=m-nav>
					<LI class=current>
						<A href="showindex">首页</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showDemonstrate?i=a&n=4">特色推荐</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showDemonstrate?i=a&n=4">功能演示</A><img src="/personalfocus/images/hot2.gif" height="20"></img>
					</LI>
					<LI>
						<A href="/personalfocus/index/showHelp?h=browser.html#download">下载与更新</A>
					</LI>
					<LI>
						<A href="showHelp">帮助</A>
					</LI>
					<LI>
						<A href="mailto:357370736@qq.com">反馈</A>
					</LI>
				</UL>
				
			</DIV>
			<DIV class=g-bd>
			<div style="font-size:20px;font-weight: bold;text-align: right;"><a  href="http://labs.soso.com/cstar2011/xq.html#project_id=4032"  target="_blank">投票</a></div>
				<DIV class=indexdld>
					<div class="container" id="idTransformView">
						<ul class="slider" id="idSlider">
							<li>
								<A onmouseover="displayStatusMsg();return document.returnValue"
									href="javascript:jump2url()"><IMG
										style="FILTER: revealTrans(duration =         2, transition =         20)"
										src="/personalfocus/images/silder1.jpg" border=2
										name=bannerADrotator> </A>
								<SCRIPT language=JavaScript>nextAd()</SCRIPT>
							</li>
						</ul>
						<ul class="num" id="idNum">
							<li onMouseover='setAd(0)' onClick='setAd(0)' style="background: #006CDB">
								1
							</li>
							<li onMouseover='setAd(1)' onClick='setAd(1)'>
								2
							</li>
							<li onMouseover='setAd(2)' onClick='setAd(2)'>
								3
							</li>
							<li onMouseover='setAd(3)' onClick='setAd(3)'>
								4
							</li>
						</ul>
					</div>
					<div id="login">
						<s:form name="loginForm" action="checklogin" namespace="/index">
							<p>
								账 号：
								<s:textfield name="userid" value=""></s:textfield>
							</p>
							<div id="loginerror">
								<font color="red"><s:fielderror /> </font>
							</div>
							<p>
								&nbsp;
							</p>
							<p>
								密 码：
								<s:password name="password" value=""></s:password>
							</p>
							<p>
								<br />
							</p>
							<p align="center">
								<s:submit cssClass="btn" value="登录"></s:submit>
								<input type="button" class="btn"
									onclick="javascript:location.href='showregist'" value="注册"></input>
							</p>
						</s:form>

					</div>
					<div id='explain'>
						个人空间,聚合订阅关注
					</div>
				</DIV>

				<br />
				<br />
				<DIV class=indexfun>
				<div class="download-nav"><span><a href="/personalfocus/index/showHelp?h=browser.html#download">关注助手1.0.5版浏览器下载</a></span>
				<img src="/personalfocus/images/hot2.gif" height="20"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="/personalfocus/index/showHelp?h=mobileClient.html#download">关注助手1.0.2-手机客户端下载</a><img src="/personalfocus/images/hot2.gif" height="20"></span></div>
			
					<P class=thisp11>
						<img src="/personalfocus/images/add.png" />
					</P>

					<P class=thisp1>
						<b>关注订阅“任意”网页 </b>
						<br>
						强大的订阅和监控功能，可以关注几乎所有网页，包括没有提供任何关注服务的网站，如学校公告网页等
					</P>
					<P class=thisp21>
						<img src="/personalfocus/images/add.png" />
					</P>
					<P class=thisp2>
						<b>所见即所得的订阅功能 </b>
						<br />
						只需移动和点击鼠标，便可订阅网页任意模块，定义过程直观方便，快捷
					</P>
					
					<P class=thisp31>
						<img src="/personalfocus/images/add.png" />
					</P>
					<P class=thisp3>
						<b>资源共享与交互 </b>
						<br>
						可进入好友的空间订阅好友关注内容，你也可发布订阅源，兼具<strong style='text-decoration: underline;color:rgb(251,0,0)'>微博</strong>的功能，好友便可关注你发布的信息
					</P>
					
					<P class=thisp41>
						<img src="/personalfocus/images/add.png" />
					</P>
					<P class=thisp4>
						<b> 手机“微网”展示 </b>
						<br>
						手机可登录空间查看订阅模块信息，只显示网页中已关注订阅的部分
					</P>
					<P class=thisp51>
						<img src="/personalfocus/images/add.png" />
					</P>
					<P class=thisp5>
						<b>聚合网页模块 </b>
						<br>
						可以把关注的网页模块聚合在个人空间
					</P>
					<P class=thisp61>
						<img src="/personalfocus/images/add.png" />
					</P>
					<P class=thisp6>
						<b>智能更新展示 </b>
						<br />
						能智能对比新旧内容，以在线、邮件、短信、手机浏览、客户端等多种方式展示更新信息
					</P>
				</DIV>
				<div class='mobile'>
					<a href='/personalfocus/mobile/mobileIndex'>手机请访问：http://localhost:8080/personalfocus/mobile/mobileIndex</a>
				</div>
				<div class='ps'>
			PS:测试版暂不提供邮件、短信发送功能！
		</div>
			</DIV>
		</DIV>
		
		<DIV class=g-ft>
			<DIV>
				<DIV class=copy-wrap>

					<DIV class=copyright>
						<center>
							<A href="mailto:357370736@qq.com">联系我们:&nbsp;357370736@qq.com</A>
						</center>

					</DIV>
				</DIV>

			</DIV>
		</DIV>
	</BODY>
</HTML>
