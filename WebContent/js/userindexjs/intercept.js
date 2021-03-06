////////覆盖拦截通信

var oriWindowOpen = window.open;
window.open = function(url, names, specs, replace) {
	if (url.indexOf(olpfBase) < 0)
		url = getURL(showWebPageURL, url);
	return oriWindowOpen(url, names, specs, replace);
}
var oriActiveXObject;
function activeXObject(param) {
	var obj = new oriActiveXObject(param);
	return createActiveXAgent(obj);

};
function createActiveXAgent(ao) {
	var agent = new Object;
	agent.activeXObject = ao; // 被包裹的内核，是真正的通信对象
	agent.syncAttribute = function() { // syncAttribute是用来同步属性的
		try {
			this.readyState = this.activeXObject.readystate;
			this.responseText = this.activeXObject.responseText;
			this.responseXML = this.activeXObject.responseXML;
			this.status = this.activeXObject.status;
			this.statusText = this.activeXObject.statusText;
		} catch (e) {
		}
	};
	agent.trigStateChange = function() { // 模拟onreadystatechange
		agent.syncAttribute();
		if (agent.onreadystatechange != null) {
			agent.onreadystatechange();
		}
	};
	agent.activeXObject.onreadystatechange = agent.trigStateChange;
	agent.abort = function() { // 模拟abort
		this.activeXObject.abort();
		this.syncAttribute();
	};
	agent.getAllResponseHeaders = function() { // 模拟内核对应的方法
		var result = this.activeXObject.getAllResponseHeaders();
		this.syncAttribute();
		return result;
	};
	agent.getResponseHeader = function(headerLabel) { // 模拟内核对应的方法
		var result = this.activeXObject.getResponseHeader(headerLabel);
		this.syncAttribute();
		return result;
	};
	agent.open = function(method, url, asyncFlag, userName, password) {
		// code to trace and intercept;
		if (url.indexOf(olpfBase) < 0)
				url = getURL(showWebPageURL, url);
		this.activeXObject.open(method, url, asyncFlag, userName, password);
		this.syncAttribute();
	};
	agent.send = function(content) { // 模拟内核对应的方法
		this.activeXObject.send(content);
		this.syncAttribute();
	};
	agent.setRequestHeader = function(label, value) { // 模拟内核对应的方法
		this.activeXObject.setRequestHeader(label, value);
		this.syncAttribute();
	};
	return agent;
};
////////覆盖拦截通信