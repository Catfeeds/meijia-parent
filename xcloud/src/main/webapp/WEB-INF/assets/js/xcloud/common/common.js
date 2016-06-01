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
		$.AMUI.validator.patterns.price = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/ ;

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

//顶部菜单点击高亮
function setTopMenuId(menuId, subMenuId) {
	console.log("menuId = " + menuId);
	$.cookie("xcloud-top-menu-id", menuId, { path: "/"}); 
	$.cookie("xcloud-menu-id", subMenuId, { path: "/"}); 
}

function setTopMenuHl() {
	var topMenuId = $.cookie('xcloud-top-menu-id');
	if (topMenuId == undefined) return false;
	if (topMenuId == "") return false;
	$("#top-ul").each(function () {
		$(this).find('li').each(function() {
			var menuId = $(this).attr("id");
			
			if (menuId == topMenuId) {
				if (!$(this).hasClass("am-topbar-inverse")) {
					$(this).addClass("am-topbar-inverse");
				}
			} else {
				if ($(this).hasClass("am-topbar-inverse")) {
					$(this).removeClass("am-topbar-inverse");
				}
			}
			
	    });
	});
}

setTopMenuHl();

//菜单点击展开
function setMenuId(menuId) {
	$.cookie("xcloud-menu-id", menuId, { path: "/"}); 
	menuCollapse();
}

function menuCollapse() {
	var menuId = $.cookie('xcloud-menu-id'); 
	
	if (menuId == undefined) return false;
	if (menuId == "") return false;
	
	if ($("#"+ menuId).hasClass("am-in")) {
		$("#"+ menuId).collapse('close');
	} else {
		$("#"+ menuId).collapse('open');
	}
	//console.log($("#"+ menuId).collapse());
}
menuCollapse();