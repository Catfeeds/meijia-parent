//参数设置
var host = window.location.host;
var appName = "simi-app";
var localUrl = "http://" + host;
var siteAPIPath = localUrl + "/" + appName + "/app/";
var siteApp = "u";
var sitePath = localUrl + "/" + siteApp;
var userLoggedIn = false;

// 初始化应用
var myApp = new Framework7({
	precompileTemplates: true,
    template7Pages: true,
    pushState: true,
    cache: false,
    modalTitle: "提示",
    modalButtonOk:'确定',
    modalButtonCancel:'返回',
    template7Data: {},
    material: true,
    allowDuplicateUrls:true,
    domCache: true,
    // Hide and show indicator during ajax requests
    onAjaxStart: function (xhr) {
            myApp.showIndicator();
    },
    onAjaxComplete: function (xhr) {
            myApp.hideIndicator();
    },    
    preroute: function (view, options) {
    	
			/*    	 if(!isLogin() && options.url!='login.html'){
			//           console.log('must login');
			           view.router.loadPage('login.html');
			           return false;
			   }*/
    		//反向判断不好使！！！
    }
        
        
});

// 定义Dom7框架工具
var $$ = jQuery = Dom7;

// 初始化主视图
var mainView = myApp.addView('.view-main', {
        dynamicNavbar: true,
        // domCache: true
});

function isLogin(){
        return typeof localStorage['user_id']!='undefined' && localStorage['user_id']!='';
}

var ajaxError = function(data, textStatus, jqXHR) {	
    	// We have received response and can hide activity indicator
        myApp.hideIndicator();		
    	myApp.alert('网络繁忙,请稍后再试.');
};