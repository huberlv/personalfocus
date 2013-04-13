<%@ page language="java" pageEncoding="UTF-8"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/indexcss/newstyle.css">
		<script language=JavaScript> 
		<%
		String ind="a";
		int num=4;
		 try{
		      ind=request.getParameter("i");
		      num=Integer.parseInt(request.getParameter("n"));
		 }catch(Exception e){
			 
		 }
			%>
<!-- // BannerAD 

	var bannerAD = new Array();
	var bannerADlink = new Array();
	var adNum = -1;
    for(var i=0;i<<%=num%>;i++){
	bannerAD[i] = "/personalfocus/help/images/<%=ind%> ("+i+").jpg";
	bannerADlink[i] = "/personalfocus/help/images/<%=ind%> ("+i+").jpg";

    }
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
		<style type="text/css">
#idNum {
position:static;
	margin: 0px auto;
}
.display{
margin: auto auto;
}
.title a{
font-size: 16px;
margin-right: 10px;
}
</style>
	</HEAD>
	<BODY>

		<DIV class=g-doc style="MIN-HEIGHT:50px;">
			<DIV class=g-hd>
				<H1 class=m-logo>
					<A href=""></A>
				</H1>
				<UL class=m-nav>
					<LI class=current>
						<A href="/personalfocus/index/showindex">首页</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showDemonstrate?i=a&n=4">特色推荐</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showDemonstrate?i=a&n=4">功能演示</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showHelp?h=browser.html#download">下载与更新</A>
					</LI>
					<LI>
						<A href="/personalfocus/index/showHelp">帮助</A>
					</LI>
					<LI>
						<A href="mailto:357370736@qq.com">反馈</A>
					</LI>
				</UL>

			</DIV>
			<div class='title'>
			<a href="/personalfocus/index/showDemonstrate?i=a&n=4">1.订阅关注</a>
			<a href="/personalfocus/index/showDemonstrate?i=m&n=3">2.我的关注</a>
			<a href="/personalfocus/index/showDemonstrate?i=s&n=3">3.浏览订阅好友空间</a>
			<a href="/personalfocus/index/showDemonstrate?i=w&n=3">4.手机微网</a>
			<a href="/personalfocus/index/showDemonstrate?i=b&n=6">5.使用平台浏览器</a>
			<a href="/personalfocus/index/showDemonstrate?i=c&n=4">6.使用手机客户端</a>
			</div>
			<br/>
			<ul class='num' id="idNum" style="position:static;">
			<%for(int j=0;j<num;j++){%>
				<li onMouseover='setAd(<%=j %>)' onClick='setAd(<%=j %>)'>
				<%=j+1 %>
			</li>
			<% }%>
				</ul><br/><br/>
		</DIV>
			<DIV class='display' >

				
				<ul  id="idSlider" >
					<li>
					<!-- <A onmouseover="displayStatusMsg();return document.returnValue"
							href="javascript:jump2url()"> -->	
							<IMG
								style="height:531px;width:1001px;FILTER: revealTrans(duration=2,transition=20);border: 1px solid #FF7300;"
								src="/personalfocus/help/images/<%=ind%> (0).jpg" border=2
								name="bannerADrotator" height="531px" width="1001px"> 
						<SCRIPT language=JavaScript>nextAd()</SCRIPT>  
					</li>
				</ul>
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
