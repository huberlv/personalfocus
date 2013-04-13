<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>互联网关注订阅平台</TITLE>
		<base target="_blank" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/contentstyle.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/nav.css">
		<LINK rel=stylesheet type=text/css
			href="/personalfocus/css/userindexcss/systemManagement.css">
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
		
		<SCRIPT type=text/javascript
			src="/personalfocus/js/userindexjs/server.js"></SCRIPT>
		<script type='text/javascript' src='/personalfocus/dwr/engine.js'></script>
		<script type='text/javascript' src='/personalfocus/dwr/util.js'></script>
		<script type='text/javascript'
			src='/personalfocus/dwr/interface/server.js'></script>

	</HEAD>
	<BODY>


		<DIV id=lhpopciones>
			<img
				style="vertical-align: middle; margin-left: 20px; margin-right: 10px"
				src="/personalfocus/images/portrait.jpg"></img>
			管理员：
			<s:property value="%{#session.managerName}" />
			&nbsp;&nbsp;&nbsp;&nbsp;
		</div>



		<DIV class=g-doc>

			<DIV class=g-hd>
				<H1 class=m-logo>
					<A href=""></A>
				</H1>
			</DIV>

			<%--内容--%>
			<div class="content">
				<div class="mhd">
					<div class="tit"></div>
				</div>
				<div class="mcont">

					<div class="main-cont">

						<div class="main-cont-tit">
							<div class="arr"></div>

							系统信息
						</div>
                       <div>
						<label>application中访问量：</label><span><%=application.getAttribute("accessTime")%></span>
						<br/>
					    <label>application中手机页面访问量：</label><span><%=application.getAttribute("mobileAccessTime")%></span>
					 </div>
					</div>
				</div>
				<div class="mcont">

					<div class="main-cont">

						<div class="main-cont-tit">
							<div class="arr"></div>

							启动
						</div>

						<input type="button" value="启动发送邮件服务" onclick="startEmailSender()">
						<input type="button" value="启动发送短信服务"
							onclick="startMessageSender()">
						<input type="button" value="启动更新服务" onclick="startUpdate()">
					</div>
				</div>



				<div class="mcont">
					<div class="main-cont">
						<div class="main-cont-tit">
							<div class="arr"></div>
							关闭
						</div>
						<input type="button" value="关闭邮件服务" onclick="stopEmailSender()">
						<input type="button" value="关闭短信服务" onclick="stopMessageSender()">
						<input type="button" value="关闭更新服务" onclick="stopUpdate()">
					</div>


					<div class="mcont">
						<div class="main-cont">
							<div class="main-cont-tit">
								<div class="arr"></div>
								后台清理
							</div>
							<input type="button" value="清理多余模块" onclick="deleteUnuse()">
							<input type="button" value="删除"
								onclick="deleteUnuseContentbuffer()">
							<input type="text" size="3" id="days" value="" />
							天 前的历史纪录

						</div>
						<div class="mcont">
							<div class="main-cont">
								<div class="main-cont-tit">
									<div class="arr"></div>
									系统设置
								</div>
								<label>
									监控频率(hh:mm:ss)：
								</label>
								<input type="text" size="8" id="monitorFra" value="" />
								<div>
									<input type="button" value="保存" onclick="saveMonitorFra();">
								</div>



							</div>


							<div class="mcont">

								<div class="main-cont">

									<div class="main-cont-tit">
										<div class="arr"></div>

										失效模块
									</div>
									<center>
										<table>
											<tr>
												<th>
													模块编号
												</th>
												<th>
													操作
												</th>
											</tr>

											<s:iterator value="failModule" status="status">
												<tr>
													<td>
														<s:property value="getId().getModuleId()" escape="false" />
													</td>
													<td>
														<a href="javascript:void(0)"
															onClick="modifyAModule(<s:property value="getId().getModuleId()" escape="false"/>)">修正特征</a>
														<a href="javascript:void(0)"
															onClick="disAbleAModule(<s:property value="getId().getModuleId()" escape="false"/>)">标记为失效</a>
													</td>
												</tr>
											</s:iterator>
										</table>
									</center>
								</div>



								<div class="mcont">

									<div class="main-cont">

										<div class="main-cont-tit">
											<div class="arr"></div>

											监控网页情况统计
										</div>
										<center>
											<table>
												<tr>
													<th>
														网址
													</th>
													<th>
														失败次数/总次数
													</th>
													<th>
														选定
													</th>
												</tr>

												<s:iterator value="failWebInfoView" status="status">
													<tr>
														<td>
															<a
																href="<s:property value="getId().getWebAddress()" escape="false"/>"><s:property
																	value="getId().getWebAddress()" escape="false" /> </a>
														</td>
														<td>
															<s:property value="getId().getFailTimes()" escape="false" />
															/
															<s:property value="getId().getTotalMonitorTimes()"
																escape="false" />
														</td>
														<td>
															<INPUT type="checkbox" name='webIds'
																value='<s:property value="getId().getWebId()" escape="false"/>' />
														</td>

													</tr>
												</s:iterator>
											</table>
										</center>
										<input type="button" value="清零" onclick="operate(0)"></input>
										<input type="button" value="标记为失效网址" onclick="operate(1)"></input>
									</div>


									<!-- 分拆补充资料部分 End -->
									<div class="mft"></div>
								</div>
								<div class="mcont">

									<div class="main-cont">

										<div class="main-cont-tit">
											<div class="arr"></div>

											动态SQL查询
										</div>
										<lable>
										请输入查询SQL语句
										</lable>
										<br />
										<textarea rows="5" cols="100" id="sql"></textarea>
										<br />
										<input type="button" value="执行"
											onclick="getQueryResultBySQL(document.getElementById('sql').value)"></input>
										
										<hr>
										<br />
										<lable>
										执行结果
										</lable>
										<br />
										<div id='result'></div>
									</div>


									<!-- 分拆补充资料部分 End -->
									<div class="mft"></div>
								</div>
							</DIV>
						</DIV>
						<DIV class=g-ft>
							<DIV class=inner>
							</DIV>
						</DIV>
					</DIV>
				</div>
			</DIV>
	</BODY>
</HTML>
