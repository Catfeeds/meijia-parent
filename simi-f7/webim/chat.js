//获取对话页数据
myApp.template7Data['page:messages'] = function(page){
        console.log('message data init');
        // console.log(page);
        return {};
}

//Before初始化
myApp.onPageBeforeInit('messages', function(page){
        console.log('message before init');
});


myApp.onPageBack('user-list-page', function(page){

    console.log('back 清除当前用户')
    webim.curroster = null;

});

//页面初始化
myApp.onPageInit('messages', function (page) {
        
        console.log('message page init');

        var toUser = page.query.uid;       
        var userId = page.query.user_id;



        webim.noreadFlag[toUser] = 0;  // 未读数量清零
        webim.newMessageDot();

        var onUserInfoSuccess =function(data, textStatus, jqXHR) {
           	    var result = JSON.parse(data.response);
              	
                if (result.status == "999") {
              		myApp.alert(result.msg);
              		return;
              	}
              	var user = result.data;
              	var nickName = user.name;
              	$$('#touser').html(nickName);
        }
        
        
        var postdata = {};
        postdata.user_id = userId;    
      	$$.ajax({
          		type : "GET",
          		url : siteAPIPath + "user/get_userinfo.json",
          		dataType : "json",
          		cache : true,
          		data :postdata,
          		statusCode: {
                   	200: onUserInfoSuccess,
           	    	400: ajaxError,
           	    	500: ajaxError
           	    },
      	});
       

        webim.myMessages = myApp.messages('.messages', {
                autoLayout:true
        });


        webim.curroster = toUser;
        
        //初始化消息对象
        webim.myMessagebar = myApp.messagebar('.messagebar');
        
        // 框架发送消息
        $$('.messagebar .link').on('click', function () {
                  // Message text
                  var messageText = webim.myMessagebar.value().trim();
                  // Exit if empy message
                  if (messageText.length === 0) return;
                  // Empty messagebar
                  webim.myMessagebar.clear()
                  // 随机消息类型
                  //var messageType = (['sent', 'received'])[Math.round(Math.random())];
                  var messageType = 'sent';
                  // 接收的消息的头像和名称
                  var avatar, name;
                  if(messageType === 'received') {
                        avatar = 'http://lorempixel.com/output/people-q-c-100-100-9.jpg';
                        name = 'Kate';
                  }
                  // Add message
                  webim.myMessages.addMessage({
                    // Message text
                    text: messageText,
                    // 随机消息类型
                    type: messageType,
                    // 头像和名称
                    avatar: avatar,
                    name: name,
                    // 日期
                    day: !webim.conversationStarted ? '今天' : false,
                    time: !webim.conversationStarted ? (new Date()).getHours() + ':' + (new Date()).getMinutes() : false
                  })
                  // 更新会话flag
                  webim.conversationStarted = true;
                  webim.sendText(messageText, toUser);
        });

    	$$('.order-form-link').on('click', function() {
    		
//    		myApp.alert('order-form' + userId);
    		mainView.router.loadPage("order/order-form.html?user_id="+userId);
    	});        

});


