//客户动态模版实现
$$('#userlist').on('click', function(){
    
    $$.get('user/user-list.html', {}, function (data) {
            var compiledTemplate = Template7.compile(data);
            
            var userListSuccess = function(data, textStatus, jqXHR) {
        		// We have received response and can hide activity indicator
        	   	myApp.hideIndicator();
        	   	
        	   	var result = JSON.parse(data.response);
        	   	console.log(result);
        	   	var users = result.data;
                var html = compiledTemplate(users);
                console.log(users);
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
//                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                cache : true,
                data : postdata,
                
                statusCode: {
                	200: userListSuccess,
        	    	400: ajaxError,
        	    	500: ajaxError
        	    }
            });
        	



            return false;            
            
            
            
//            $$.getJSON('data/userlist.json', {}, function (data) {
//                var html = compiledTemplate(data);
//                mainView.router.loadContent(html);
//            });
    });


    return false;
});



// //userlist: [
// //        {
// //            name: 'John',
// //        },
// //        {
// //            name: 'Kyle',
// //        },
// // 
// // ]

// //快速下单
// $$('.order-form').on('click', function() {
// 	myApp.alert('order-form');
// });

// //订单列表
// $$('.order-list').on('click', function() {
// 	myApp.alert('order-list');
// });

// myApp.onPageBeforeInit('form', function (page) {



//     $$.getJSON('data/userlist.json', {}, function (d) {
//         myApp.template7Data["page:form"]=d;
        
//     });

//     console.log(page);
   
//     //return false;

// });