
var host = window.location.host;
var appName = "simi/app";
var localUrl = "http://" + host;
var siteAPIPath = localUrl + "/" + appName+"/"; //正式
//var localPath = "http://localhost:8080/simi/app"//测试
var siteApp = "simi-f7";


var userLoggedIn = false;
// Initialize your app
var myApp = new Framework7({
    // template7Pages: true,
    pushState:true,
    
    cache: false,
    
    modalTitle: "提示",
    
    // Hide and show indicator during ajax requests
    onAjaxStart: function (xhr) {
        myApp.showIndicator();
    },
    onAjaxComplete: function (xhr) {
        myApp.hideIndicator();
    },    
    
    preroute: function (view, options) {
//         if(!isLogin() && options.url!='login.html'){
// //            console.log('must login');
//             view.router.loadPage('login.html');
//             return false;
    }
        
        
});

// Export selectors engine
var $$ = Dom7;


// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true,
    domCache: true
});


//首页滚动广告
var mySwiper = myApp.swiper('.swiper-container', {
    pagination:'.swiper-pagination',
    autoplay: 2000
  });

function isLogin(){
	//return false;
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

//Generate dynamic page
//var dynamicPageIndex = 0;
//function createContentPage() {
//	mainView.router.loadContent(
//        '<!-- Top Navbar-->' +
//        '<div class="navbar">' +
//        '  <div class="navbar-inner">' +
//        '    <div class="left"><a href="#" class="back link"><i class="icon icon-back"></i><span>Back</span></a></div>' +
//        '    <div class="center sliding">Dynamic Page ' + (++dynamicPageIndex) + '</div>' +
//        '  </div>' +
//        '</div>' +
//        '<div class="pages">' +
//        '  <!-- Page, data-page contains page name-->' +
//        '  <div data-page="dynamic-pages" class="page">' +
//        '    <!-- Scrollable page content-->' +
//        '    <div class="page-content">' +
//        '      <div class="content-block">' +
//        '        <div class="content-block-inner">' +
//        '          <p>Here is a dynamic page created on ' + new Date() + ' !</p>' +
//        '          <p>Go <a href="#" class="back">back</a> or go to <a href="services.html">Services</a>.</p>' +
//        '        </div>' +
//        '      </div>' +
//        '    </div>' +
//        '  </div>' +
//        '</div>'
//    );
//	return;

