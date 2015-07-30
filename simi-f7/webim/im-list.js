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
        var secImUserName = localStorage['im_username'];
        var postdata = {};
        postdata.im_user_name = secImUserName;
        postdata.sec_id = secId;    

        $$.ajax({
                
                //type : "GET",
                //url  : "data/users.json",
                type : "GET",
                url  : siteAPIPath+"user/get_user_and_im.json",
                dataType: "json",
                cache : true,
                async : false,
                data : postdata,
                success: function(data){
                    result = data;
                    console.log("success");
                    //将列表数据缓存
                    webim.userList = {}; 
                    for(var i = 0; i<data.data.length; i++){
                        // console.log(data.data[i]);
                        webim.userList[data.data[i].im_username] = data.data[i];
                        webim.msg[data.data[i].im_username] = {};
                    }

                    result = data;
                }
        })
        return result;
}

//列表页初始化函数
myApp.onPageInit('im-list-page', function (page) {
        webim.curroster = null;
        console.log('init im list and 执行红点函数');
        webim.newMessageDot();
});







