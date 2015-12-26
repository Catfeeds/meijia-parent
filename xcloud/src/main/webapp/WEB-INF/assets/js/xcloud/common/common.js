var host = window.location.host;
var appName = "simi";
var appRootUrl = "http://" + host + "/" + appName + "/app/";

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

	}
})(jQuery);

//$.ajaxSetup({
//	dataType : "json",
//	beforeSend : function(xhr, settings) {
//		var csrftoken = $.AMUI.utils.cookie.get('csrftoken');
//		xhr.setRequestHeader("X-CSRFToken", csrftoken);
//	},
//});


$("#btn-return").on('click', function(e) {
	history.go(-1);
});