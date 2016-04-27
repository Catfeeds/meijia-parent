var host = window.location.host;
var appName = "simi";
var appRootUrl = "http://" + host + "/" + appName + "/app/";

// 云平台 根路径
var xCloudRootUrl = "http://" + host +"/xcloud"; 

(function($) {
	'use strict';

	$(function() {
		var $fullText = $('.admin-fullText');
		$('#admin-fullscreen').on('click', function() {
			$.AMUI.fullscreen.toggle();
		});

		$(document).on(
				$.AMUI.fullscreen.raw.fullscreenchange,
				function() {
					$.AMUI.fullscreen.isFullscreen ? $fullText.text('关闭全屏')
							: $fullText.text('开启全屏');
				});

		$(document).ready(
				function() {
					var active = $('.tr-main-container').attr('id');
					$('#tr-header-nav .tr-nav').children('li.' + active)
							.addClass('am-active');
				});
	});
})(jQuery);

(function($) {
	if ($.AMUI && $.AMUI.validator) {
		$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
		$.AMUI.validator.patterns.sms_token = /^\d{4}$/;
		$.AMUI.validator.patterns.email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		$.AMUI.validator.patterns.pinteger = /^[0-9]*[1-9][0-9]*$/;

	}
})(jQuery);

// $.ajaxSetup({
// dataType : "json",
// beforeSend : function(xhr, settings) {
// var csrftoken = $.AMUI.utils.cookie.get('csrftoken');
// xhr.setRequestHeader("X-CSRFToken", csrftoken);
// },
// });

$("#btn-return").on('click', function(e) {
	history.go(-1);
});

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//位数不足补零
function pad(num, n) {
	var i = (num + "").length;
	while (i++ < n)
		num = "0" + num;
	return num;
}