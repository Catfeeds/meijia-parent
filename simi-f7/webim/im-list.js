// myApp.onPageInit('chat', function (page) {

//     var im_username = localStorage['im_username'];
//     var im_password = localStorage['im_password'];
//     var userId = page.query.user_id;
    
//     console.log("im_username = " + im_username);
//     console.log("im_passowrd = " + im_password);
//     console.log("userId = " + userId);
    
//     var clientWidth = document.body.scrollWidth ;
//     var clientHeight = document.body.scrollHeight ;
//     console.log(document.body.clientWidth);
//     console.log(document.body.clientHeight);
    
//     var html = '<iframe src="js/lib/webim/index.html" id="iframe" frameborder="0" scrolling="auto" class="mainiframe" width="100%" height="'+clientHeight+'"></iframe>';
//     $$('.page-content-inner').append(html);


// });

//列表页
myApp.onPageInit('im-list-page', function (page) {

        // $$.get('webim/imlist-inner.html', {}, function (data) {
            
            
        //         var compiledTemplate = Template7.compile(data);
        //         var userListSuccess = function(data, textStatus, jqXHR) {
                         
        //                  myApp.hideIndicator();
        //                  var result = JSON.parse(data.response);
        //                  var html = compiledTemplate(result);
                         
        //                  console.log(html);
        //                  //更换方法，加载到当前视图
        //                  mainView.router.reloadContent(html);  


        //        };                
            
        //         var secId = localStorage['sec_id'];
        //         var secMobile = localStorage['sec_mobile'];
        //         var postdata = {};
        //         postdata.mobile = secMobile;
        //         postdata.sec_id = secId;    


        //         $$.ajax({
        //             type : "POST",
        //             url  : siteAPIPath+"sec/get_users.json",
        //             dataType: "json",
        //             cache : true,
        //             async : false,
        //             data : postdata,
                    
        //             statusCode: {
        //              200: userListSuccess,
        //              400: ajaxError,
        //              500: ajaxError
        //              },
        //             success:function(){

        //             }
        //         });


        //         //客户信息
        //         $$('.im-link').on('click', function() {
        //             var userId = $$(this).attr("userId");
        //             mainView.router.loadPage("webim/chat.html?user_id="+userId);
        //         });

        // });



});
// //客户动态模版实现
// $$('#im-list').on('click', function(){
    
//     $$.get('webim/im-list.html', {}, function (data) {
//             var compiledTemplate = Template7.compile(data);
            
//             var userListSuccess = function(data, textStatus, jqXHR) {
//         		// We have received response and can hide activity indicator
//         	   	myApp.hideIndicator();
//         	   	var result = JSON.parse(data.response);
//                 var html = compiledTemplate(result);
//                 mainView.router.loadContent(html);        	   
//         	};                
            
//             var secId = localStorage['sec_id'];
//             var secMobile = localStorage['sec_mobile'];
//             var postdata = {};
//             postdata.mobile = secMobile;
//             postdata.sec_id = secId;    


//             $$.ajax({
//                 type : "POST",
//                 url  : siteAPIPath+"sec/get_users.json",
//                 dataType: "json",
//                 cache : true,
//                 async : false,
//                 data : postdata,
                
//                 statusCode: {
//                 	200: userListSuccess,
//         	    	400: ajaxError,
//         	    	500: ajaxError
//         	    },
//                 success:function(){

//                 }
//             });
        	



//             return false;            

//     });


//     return false;
// });



