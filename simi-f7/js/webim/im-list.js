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



myApp.onPageBeforeInit('im-list-page', function (page) {

	//客户信息
	$$('.im-link').on('click', function() {
		console.log("im-link");
		var userId = $$(this).attr("userId");
		mainView.router.loadPage("webim/chat.html?user_id="+userId);
	});
});