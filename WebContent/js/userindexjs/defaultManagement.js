var $j = jQuery.noConflict();

/**
 * �����û�Ĭ�Ͻ��ձ���Ϣ
 * @author ZERO
 * @date 2010-10-01
 */
function update() {
	var isMobile = $("isMobile").checked;
	var messageReceiveType = 4;
	var messageStartTime = $("messageStartTime").value;
	var messageStopTime = $("messageStopTime").value;
	var sendMaxCount = $("sendMaxCount").value;
	var mobilefrequency = $("mobilefrequency").value;
	var mobileUnit = $("mobileUnit").value;
	var isMail = $("isMail").checked;
	var mailStartTime = $("mailStartTime").value;
	var mailStopTime = $("mailStopTime").value;
	var mailFrequency = $("mailFrequency").value;
	var mailUnit = $("mailUnit").value;

	//ִ��Ĭ�Ͻ��ܱ���²���
	updateDefault.updateDefaultReceive(isMobile, messageReceiveType,
			messageStartTime, messageStopTime, mobilefrequency, mobileUnit,
			sendMaxCount, isMail, mailStartTime, mailStopTime, mailFrequency,
			mailUnit, function(data) {
				alert(data);
			});

}