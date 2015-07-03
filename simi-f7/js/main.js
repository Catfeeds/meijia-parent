//参数设置
var host = window.location.host;
var appName = "simi/app";
var localUrl = "http://" + host;
var siteAPIPath = localUrl + "/" + appName+"/"; //正式
//var localPath = "http://localhost:8080/simi/app"//测试
var siteApp = "simi-f7";
var userLoggedIn = false;




// 初始化应用
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

// 定义Dom7框架工具
var $$ = jQuery = Dom7;

// 初始化主视图
var mainView = myApp.addView('.view-main', {
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



  
