//客户动态模版实现
//$$('#userlist').on('click', function(){
//    
//    $$.get('user/user-list.html', {}, function (data) {
//            var compiledTemplate = Template7.compile(data);
//            
//            var userListSuccess = function(data, textStatus, jqXHR) {
//        		// We have received response and can hide activity indicator
//        	   	myApp.hideIndicator();
//        	   	var result = JSON.parse(data.response);
//                var html = compiledTemplate(result);
//                mainView.router.im-list-page(html);        	   
//        	};                
//            
//            var secId = localStorage['sec_id'];
//            var secMobile = localStorage['sec_mobile'];
//            var postdata = {};
//            postdata.mobile = secMobile;
//            postdata.sec_id = secId;    
//
//
//            $$.ajax({
//                type : "POST",
//                url  : siteAPIPath+"sec/get_users.json",
//                dataType: "json",
//                cache : true,
//                async : false,
//                data : postdata,
//                
//                statusCode: {
//                	200: userListSuccess,
//        	    	400: ajaxError,
//        	    	500: ajaxError
//        	    },
//                success:function(){
//
//                }
//            });
//        	
//
//
//
//            return false;            
////            $$.getJSON('data/userlist.json', {}, function (data) {
////                var html = compiledTemplate(data);
////                mainView.router.loadContent(html);
////            });
//    });
//
//
//    return false;
//});


//获取列表页用户列表
myApp.template7Data['page:im-list-page'] = function(){
        
//        console.log('page data for im-list-page');
        var result;

        var secId = localStorage['sec_id'];
        var secMobile = localStorage['sec_mobile'];
        var postdata = {};
        postdata.mobile = secMobile;
        postdata.sec_id = secId;    

        $$.ajax({

                type : "POST",
                // type : "GET",
                url  : siteAPIPath+"sec/get_users.json",
                // url  : "data/users.json",
                dataType: "json",
                cache : true,
                async : false,
                data : postdata,
                success: function(data){

                    webim.userList = {}; 
                    for(var i = 0; i<data.data.length; i++){
                        console.log(data.data[i]);
                        webim.userList[data.data[i].im_username] = data.data[i]
                    }

                    result = data;
                }
        })
        return result;
}

myApp.onPageInit('user-list-page', function (page) {
	//快速下单
	$$('.order-form-link').on('click', function() {
		var userId = $$(this).attr("userId");
//		myApp.alert('order-form' + userId);
		mainView.router.loadPage("order/order-form.html?user_id="+userId);
	});

	//订单列表
	$$('.order-list-link').on('click', function() {
		var userId = $$(this).attr("userId");
		mainView.router.loadPage("order/order-list.html?user_id="+userId);
	});
	//客户信息
	$$('.user-info-link').on('click', function() {
		var userId = $$(this).attr("userId");
		mainView.router.loadPage("user/user-info.html?user_id="+userId);
	});
});