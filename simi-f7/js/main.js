
var host = window.location.host;
var appName = "simi/app";
var localUrl = "http://" + host;
var siteAPIPath = localUrl + "/" + appName+"/"; //正式
//var localPath = "http://localhost:8080/simi/app"//测试
var siteApp = "simi-f7";


var userLoggedIn = false;
// Initialize your app
var myApp = new Framework7({
	precompileTemplates: true,
    template7Pages: true,
    pushState: true,
    cache: false,
    modalTitle: "提示",
    template7Data: {},
    
    // Hide and show indicator during ajax requests
    onAjaxStart: function (xhr) {
            myApp.showIndicator();
    },
    onAjaxComplete: function (xhr) {
            myApp.hideIndicator();
    },    
    
    preroute: function (view, options) {
             if(!isLogin() && options.url!='login.html'){
                     console.log('must login');
                     view.router.loadPage('login.html');
                     return false;
             }
    }
        
        
});

// Export selectors engine
var $$ = jQuery = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true,
    // domCache: true
});

//首页滚动广告
var mySwiper = myApp.swiper('.swiper-container', {
    pagination:'.swiper-pagination',
    autoplay: 2000
  });

function isLogin(){
	//return false;
	console.log("isLogin");
    return typeof localStorage['sec_id']!='undefined' && localStorage['sec_id']!='';
}

var ajaxError = function(data, textStatus, jqXHR) {	
	// We have received response and can hide activity indicator
    myApp.hideIndicator();		
	myApp.alert('网络繁忙,请稍后再试.');
};



//控制消息的红点显示与隐藏
var newMessageDot = function (flag){
    if(flag){
        $$('#messageDot').removeClass('hidden');
    }else{
        $$('#messageDot').addClass('hidden');
    }
}    
