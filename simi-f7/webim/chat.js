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

//页面初始化
myApp.onPageInit('messages', function (page) {
        console.log('message page init');

        var toUser = page.query.uid;

        $$('#touser').html(toUser);

        webim.myMessages = myApp.messages('.messages', {
                autoLayout:true
        });
        
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


});


