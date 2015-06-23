$(function(){
    //用户未登录
    if(!isLogin()){
    	var go = $(location).attr('href');
    	var go = encodeURIComponent(go);
        window.location.href="wx-login.html?go=" + go;
        return;
    };
}());