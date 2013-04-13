<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="main-cont">
	<div id="">
		<table class="cont-tab" cellspacing="0" cellpadding="0" style="">

			<s:if test="messageReceiveType!=0 && messageReceiveType!=7">

				<tr>
					<td class="td1">
						发送到手机：
					</td>

					<td class="td2">
						<input type="checkbox" name="isMobile" id="isMobile" value="0"
							checked="checked" />
					</td>
					<td class="td3"></td>
				</tr>

				<tr id="mobileinterval">
					<td class="td1">
						发送短信时段：
					</td>
					<td class="td2">
						起始
						<select name="select" id="messageStartTime">
							<option value="<s:property value="messageStartTime"/>" selected>
								<s:property value="messageStartTime" />
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
						--- 结束
						<select name="select2" id="messageStopTime">
							<option value="<s:property value="messageStopTime"/>" selected>
								<s:property value="messageStopTime" />
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
					</td>

					<td class="td3"></td>
				</tr>
				<tr>
					<td class="td1">
						每次最多发送条数：
					</td>
					<td class="td2">
						<s:textfield value="%{messageMax}" id="sendMaxCount" />
					</td>
					<td class="td3"></td>
				</tr>
				<tr>
					<td class="td1">
						发送短信频率：
					</td>
					<td class="td2">
						<s:textfield value="%{messageSfStr}" id="mobilefrequency"></s:textfield>

						<s:select list="#{'hour':'小时','minute':'分钟'}"
							value="messageFrequencyType" id="mobileUnit"></s:select>


					</td>
					<td class="td3"></td>
				</tr>
			</s:if>

			<s:else>
				<tr>
					<td class="td1">
						发送到手机：
					</td>

					<td class="td2">
						<input type="checkbox" name="isMobile" id="isMobile" value="0" />
					</td>
					<td class="td3"></td>
				</tr>

				<tr id="mobileinterval">
					<td class="td1">
						发送短信时段：
					</td>
					<td class="td2">
						起始
						<select name="select" id="messageStartTime">
							<option value="08:00:00" selected>
								请选择
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
						--- 结束
						<select name="select2" id="messageStopTime">
							<option value="20:00:00" selected>
								请选择
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
					</td>
					<td class="td3"></td>
				</tr>
				<tr>
					<td class="td1">
						每次最多发送条数：
					</td>
					<td class="td2">
						<s:textfield value="" id="sendMaxCount" />
					</td>
					<td class="td3"></td>
				</tr>
				<tr>
					<td class="td1">
						发送短信频率：
					</td>
					<td class="td2">
						<s:textfield value="" id="mobilefrequency"></s:textfield>
						<select name="select5" id="mobileUnit">
							<option value="hour" selected="selected">
								小时
							</option>
							<option value="minute">
								分钟
							</option>
						</select>
					</td>
					<td class="td3"></td>
				</tr>
			</s:else>

			<s:if test="mailReceiveType==7">

				<tr>
					<td class="td1">
						发送到邮箱：
					</td>
					<td class="td2">
						<input type="checkbox" name="isMail" id="isMail" value="0"
							checked="checked" />
					</td>
					<td class="td3"></td>
				</tr>
				<tr id="mailinterval">
					<td class="td1">
						发送邮件时段：
					</td>
					<td class="td2">
						起始
						<select name="select3" id="mailStartTime">
							<option value="<s:property value="mailStartTime"/>" selected>
								<s:property value="mailStartTime" />
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
						--- 结束
						<select name="select3" id="mailStopTime">
							<option value="<s:property value="mailStopTime"/>" selected>
								<s:property value="mailStopTime" />
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
					</td>
					<td class="td3"></td>
				</tr>
				<tr>
					<td class="td1">
						发送邮件频率:
					</td>
					<td class="td2">
						<s:textfield value="%{mailSfStr}" id="mailFrequency" />
						<s:select list="#{'hour':'小时','minute':'分钟'}"
							value="mailFrequencyType" id="mailUnit"></s:select>
					</td>

					<td class="td3"></td>
				</tr>
			</s:if>

			<s:else>

				<tr>
					<td class="td1">
						发送到邮箱：
					</td>
					<td class="td2">
						<input type="checkbox" name="isMail" id="isMail" value="0" />
					</td>

					<td class="td3"></td>
				</tr>
				<tr id="mailinterval">
					<td class="td1">
						发送邮件时段：
					</td>
					<td class="td2">
						起始
						<select name="select3" id="mailStartTime">
							<option value="00:00:01" selected>
								请选择
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
						--- 结束
						<select name="select3" id="mailStopTime">
							<option value="23:59:59" selected>
								请选择
							</option>
							<option value="00:00:01">
								0点
							</option>
							<option value="01:00:00">
								1点
							</option>
							<option value="02:00:00">
								2点
							</option>
							<option value="03:00:00">
								3点
							</option>
							<option value="04:00:00">
								4点
							</option>
							<option value="05:00:00">
								5点
							</option>
							<option value="06:00:00">
								6点
							</option>
							<option value="07:00:00">
								7点
							</option>
							<option value="08:00:00">
								8点
							</option>
							<option value="09:00:00">
								9点
							</option>
							<option value="10:00:00">
								10点
							</option>
							<option value="11:00:00">
								11点
							</option>
							<option value="12:00:00">
								12点
							</option>
							<option value="13:00:00">
								13点
							</option>
							<option value="14:00:00">
								14点
							</option>
							<option value="15:00:00">
								15点
							</option>
							<option value="16:00:00">
								16点
							</option>
							<option value="17:00:00">
								17点
							</option>
							<option value="18:00:00">
								18点
							</option>
							<option value="19:00:00">
								19点
							</option>
							<option value="20:00:00">
								20点
							</option>
							<option value="21:00:00">
								21点
							</option>
							<option value="22:00:00">
								22点
							</option>
							<option value="23:00:00">
								23点
							</option>
							<option value="23:59:59">
								24点
							</option>
						</select>
					</td>

					<td class="td3"></td>
				</tr>
				<tr>
					<td class="td1">
						发送邮件频率：
					</td>
					<td class="td2">
						<s:textfield value="" id="mailFrequency"></s:textfield>
						<s:if test="%{mailReceiveType}=='hour'">
							<select name="select5" id="mailUnit">
								<option value="hour" selected>
									小时
								</option>
								<option value="minute">
									分钟
								</option>
							</select>
						</s:if>
						<s:else>
							<select name="select5" id="mailUnit">
								<option value="hour">
									小时
								</option>
								<option value="minute" selected>
									分钟
								</option>
							</select>
						</s:else>
					</td>
					<td class="td3"></td>
				</tr>

			</s:else>
		</table>
	</div>
</div>
