<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="defineTable" style="position:absolute;margin: 0px 0px;padding: 0px 0px;border-width: 0px;">
<table cellspacing="0" cellpadding="0" border="0" style="margin: 0px 0px;padding: 0px 0px;border-width: 0px;">
	<tr>
		<td><img src="${host}/personalfocus/images/defineImage/dialog_lt.png"></td>
		<td><img src="${host}/personalfocus/images/defineImage/dialog_ct.png"></td>
		<td><img src="${host}/personalfocus/images/defineImage/dialog_rt.png"></td>
	</tr>
	<tr>
		<td><img height="205px" id="olpfLeft"
			src="${host}/personalfocus/images/defineImage/dialog_mlm.png"
			style="height: 205px width: 13px;"></td>
		<td>
		<table width="100%" align="center" cellpadding="0" cellspacing="0"
			id="oftooltips" style="margin: 0px 0px;padding: 0px 0px;border-width: 0px;">
			<tr>
				<td width="598">
				<p align="center">
				<center>互联网关注订阅平台</center>
				</p>
				<p align="center">

				</p>
				<p align="center">网站名称: <input type="text" maxlength="20"
					value="" style="height: 20px; width: 300px;" id="webname"
					readonly="readonly" /></p>
				<p align="center">栏目名称: <input type="text" maxlength="20"
					value="新栏目" style="height: 20px; width: 300px;" id="modulename" />
				</p>
							<p align="center">
								分组：
								<s:select list="subgroupList" listKey="subgroupId"
									listValue="groupName" id="subgroup"></s:select>
							</p>
							<p align="center">
								<select name="monitorType" id="monitorType">
									<option value="0x1" selected="selected">
										由平台监控
									</option>
									<option value="0x2">
										由用户监控
									</option>
								</select>
								<select name="monitorType2" id="monitorType2">
									<option value="0x4" selected="selected">
										所有链接
									</option>
									<option value="0x8">
										所有内容
									</option>
								</select>
							</p>
<p align="center">栏目特征:  <textarea onblur="checkPathOfInput(this)" name="path" rows="1" cols="70" id="path" value=""  wrap="virtual" style="overflow-x:hidden;overflow-y:visible"></textarea>
				</p>
				<p align="center">参考特征：<span id='reference'>&nbsp;</span></p>
							<div align="center"><input type="checkbox"
					onclick=checkConfig(); checked="checked" value="1" id="config">
				使用我的默认设置</div>

				</td>
			</tr>
			<tr id="moreChoice" width="598">
				<td>
				<hr align="center" width="598" size="2" />
				<blockquote>
				<p align="center">更多设置</p>
				
				<blockquote><s:if
					test="messageReceiveType!=0 && messageReceiveType!=7">
					<p align="left">发送到手机： <input type="checkbox" value="0"
						id="isMobile" checked="checked"></p>

					<p align="left">发送短信时段： 起始 <select name="select"
						id="messageStartTime">
						<option value="<s:property value="messageStartTime"/>" selected><s:property
							value="messageStartTime" /></option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select> --- 结束 <select name="select2" id="messageStopTime">
						<option value="<s:property value="messageStopTime"/>" selected><s:property
							value="messageStopTime" /></option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select></p>
					<p align="left">发送短信频率： <s:textfield value="%{messageSfStr}"
						id="mobilefrequency" cssStyle="height: 20px; width: 40px;"></s:textfield>

					<s:if test="messageFrequencyType=='hour'">
						<select name="select5" id="mobileUnit">
							<option value="hour" selected>小时</option>
							<option value="minute">分钟</option>
						</select>
					</s:if> <s:else>
						<select name="select5" id="mobileUnit">
							<option value="hour">小时</option>
							<option value="minute" selected>分钟</option>
						</select>
					</s:else>
					<p align="left">每次最多发送条数： <s:textfield value="%{messageMax}"
						id="sendMaxCount" cssStyle="height: 20px; width: 40px;" /></p>
				</s:if> <s:else>
					<p align="left">发送到手机： <input type="checkbox" value="0"
						id="isMobile"></p>

					<p align="left">发送短信时段： 起始 <select id="messageStartTime">
						<option selected="0" value="08:00:00">8点</option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select> --- 结束 <select id="messageStopTime">
						<option selected="" value="20:00:00">20点</option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select></p>
					<p align="left">发送短信频率： <input type="text" value="24"
						id="mobilefrequency" style="height: 20px; width: 40px;"> <select
						id="mobileUnit">
						<option value="hour" selected="">小时</option>
						<option value="minute">分钟</option>
					</select></p>
					<p align="left">每次最多发送条数： <input type="text" value="1"
						id="sendMaxCount" style="height: 20px; width: 40px;"></p>
				</s:else> <s:if test="mailReceiveType==7">

					<p align="left">发送到邮箱： <input type="checkbox" value="0"
						id="isMail" checked="checked"></p>
					<p align="left">发送邮件时段： 起始 <select name="select3"
						id="mailStartTime">
						<option value="<s:property value="mailStartTime"/>" selected><s:property
							value="mailStartTime" /></option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select> --- 结束 <select name="select3" id="mailStopTime">
						<option value="<s:property value="mailStopTime"/>" selected><s:property
							value="mailStopTime" /></option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select></p>
					<p align="left">发送邮件频率： <s:textfield value="%{mailSfStr}"
						id="mailFrequency" cssStyle="height: 20px; width: 40px" /> <s:if
						test="mailFrequencyType=='hour'">
						<select name="select5" id="mailUnit">
							<option value="hour" selected>小时</option>
							<option value="minute">分钟</option>
						</select>
					</s:if> <s:else>
						<select name="select5" id="mailUnit">
							<option value="hour">小时</option>
							<option value="minute" selected>分钟</option>
						</select>
					</s:else></p>
				</s:if> <s:else>
					<p align="left">发送到邮箱： <input type="checkbox" value="0"
						id="isMail"></p>
					<p align="left">发送邮件时段： 起始 <select id="mailStartTime">
						<option selected="" value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
						<option value="23:59:59">24点</option>
					</select> --- 结束 <select id="mailStopTime">
						<option selected="" value="23:59:59">24点</option>
						<option value="00:00:01">0点</option>
						<option value="01:00:00">1点</option>
						<option value="02:00:00">2点</option>
						<option value="03:00:00">3点</option>
						<option value="04:00:00">4点</option>
						<option value="05:00:00">5点</option>
						<option value="06:00:00">6点</option>
						<option value="07:00:00">7点</option>
						<option value="08:00:00">8点</option>
						<option value="09:00:00">9点</option>
						<option value="10:00:00">10点</option>
						<option value="11:00:00">11点</option>
						<option value="12:00:00">12点</option>
						<option value="13:00:00">13点</option>
						<option value="14:00:00">14点</option>
						<option value="15:00:00">15点</option>
						<option value="16:00:00">16点</option>
						<option value="17:00:00">17点</option>
						<option value="18:00:00">18点</option>
						<option value="19:00:00">19点</option>
						<option value="20:00:00">20点</option>
						<option value="21:00:00">21点</option>
						<option value="22:00:00">22点</option>
						<option value="23:00:00">23点</option>
					</select></p>
					<p align="left">发送邮件频率： <input type="text" value="24"
						id="mailFrequency" style="height: 20px; width: 40px;"> <select
						id="mailUnit">
						<option value="hour" selected="">小时</option>
						<option value="minute">分钟</option>
					</select></p>
				</s:else>

				<p align="left"><label>
				<div align="center"><input type="checkbox" name="saveConfig"
					id="saveConfig" /> 保存为默认设置</div>
				</label></p>
				</blockquote>
				</blockquote>
				</td>
			</tr>
			<tr>
				<td>
				<hr width="100%" />
				<p align="center"><input id="checkmonitor" type="button" onclick="checkmonitor();"
					style="width: 70px;" value="确定"> <input type="button"
					onclick="clearTooltips();" style="width: 70px;" value="取消"></p>
				</td>
			</tr>
		</table>
		</td>
		<td><img height="205px" id="olpfRight"
			src="${host}/personalfocus/images/defineImage/dialog_mrm.png"
			style="height: 205px width: 13px;"></td>
	</tr>
	<tr>
		<td><img src="${host}/personalfocus/images/defineImage/dialog_lb.png"></td>
		<td><img src="${host}/personalfocus/images/defineImage/dialog_cb.png"></td>
		<td><img src="${host}/personalfocus/images/defineImage/dialog_rb.png"></td>
	</tr>
</table>
</div>