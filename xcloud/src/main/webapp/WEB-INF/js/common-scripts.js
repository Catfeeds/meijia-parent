var Script = function () {


//    tool tips

    $('.tooltips').tooltip();

//    popovers

    $('.popovers').popover();

//    bxslider

//    $('.bxslider').show();
//    $('.bxslider').bxSlider({
//        minSlides: 4,
//        maxSlides: 4,
//        slideWidth: 276,
//        slideMargin: 20
//    });
}();

var host = window.location.host;
var appName = "simi";
var localUrl = "http://" + host;
var appRootUrl = localUrl + "/" + appName + "/app/";


//通用ajax设置
$.ajaxSetup({
	timeout :30000
});

//通用生成行业二级下拉框
function getDictTrade() {
	var result;
	$.ajax({
	    type: "GET",
	    url: appRootUrl + "/dict/get_trades.json", //发送给服务器的url
	    dataType:"json",
	    async:false,
	    success: function(data) {
		  result = data.data
	    }
	});
	return result;
}


//通用刷新验证码
function changeCaptcha() {  
    $('#kaptchaImage').hide().attr('src', '/xcloud/captcha?' + Math.floor(Math.random()*100) ).fadeIn();  
//    event.cancelBubble=true;  
}
