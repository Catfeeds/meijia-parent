//客户动态模版实现
$$('#im-list').on('click', function(){
    
    $$.get('webim/im-list.html', {}, function (data) {
            var compiledTemplate = Template7.compile(data);
            
            var userListSuccess = function(data, textStatus, jqXHR) {
        		// We have received response and can hide activity indicator
        	   	myApp.hideIndicator();
        	   	var result = JSON.parse(data.response);
                var html = compiledTemplate(result);
                mainView.router.loadContent(html);        	   
        	};                
            
            var secId = localStorage['sec_id'];
            var secMobile = localStorage['sec_mobile'];
            var postdata = {};
            postdata.mobile = secMobile;
            postdata.sec_id = secId;    


            $$.ajax({
                type : "POST",
                url  : siteAPIPath+"sec/get_users.json",
                dataType: "json",
                cache : true,
                async : false,
                data : postdata,
                
                statusCode: {
                	200: userListSuccess,
        	    	400: ajaxError,
        	    	500: ajaxError
        	    },
                success:function(){

                }
            });
        	



            return false;            

    });


    return false;
});



myApp.onPageBeforeInit('user-list', function (page) {
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