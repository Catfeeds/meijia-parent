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


//获取列表页用户列表
myApp.template7Data['page:im-list-page'] = function(){
        
        console.log('page data for im-list-page');
        var result;

        var secId = localStorage['sec_id'];
        var secMobile = localStorage['sec_mobile'];
        var postdata = {};
        postdata.mobile = secMobile;
        postdata.sec_id = secId;    

        $$.ajax({
                // type : "POST",
                type : "GET",
                //url  : siteAPIPath+"sec/get_users.json",
                url  : "data/users.json",
                dataType: "json",
                cache : true,
                async : false,
                data : postdata,
                success: function(data){
                    result = data;
                }
        })
        return result;
}

//列表页初始化函数
myApp.onPageInit('im-list-page', function (page) {});







