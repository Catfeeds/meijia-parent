var host = window.location.host;
var appName = "simi";
var appRootUrl = "http://" + host + "/" + appName + "/app/";
var resumeAppUrl = "http://" + host + "/resume/app/";
var simiOaRootUrl = "http://" + host + "/simi-oa";

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

(function($) {
	if ($.AMUI && $.AMUI.validator) {
		$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
		$.AMUI.validator.patterns.sms_token = /^\d{4}$/;
		$.AMUI.validator.patterns.email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		$.AMUI.validator.patterns.pinteger = /^[0-9]*[1-9][0-9]*$/;
		$.AMUI.validator.patterns.price = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/ ;

	}
})(jQuery);