
myApp.template7Data['page:messages'] = function(){
        
        console.log('message data init');
        var result;
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
                success: function(data){
                    result = data;
                }
        })
        return result;
}

myApp.onPageBeforeInit('messages', function(page){
        console.log('message before init');
});

myApp.onPageInit('messages', function (page) {


            var conversationStarted = false;
            var myMessages = myApp.messages('.messages', {
              autoLayout:true
            });
            var myMessagebar = myApp.messagebar('.messagebar');
            // Handle message
            $$('.messagebar .link').on('click', function () {
                      // Message text
                      var messageText = myMessagebar.value().trim();
                      // Exit if empy message
                      if (messageText.length === 0) return;
                     
                      // Empty messagebar
                      myMessagebar.clear()
                     
                      // 随机消息类型
                      var messageType = (['sent', 'received'])[Math.round(Math.random())];
                     
                      // 接收的消息的头像和名称
                      var avatar, name;
                      if(messageType === 'received') {
                        avatar = 'http://lorempixel.com/output/people-q-c-100-100-9.jpg';
                        name = 'Kate';
                      }
                      // Add message
                      myMessages.addMessage({
                        // Message text
                        text: messageText,
                        // 随机消息类型
                        type: messageType,
                        // 头像和名称
                        avatar: avatar,
                        name: name,
                        // 日期
                        day: !conversationStarted ? '今天' : false,
                        time: !conversationStarted ? (new Date()).getHours() + ':' + (new Date()).getMinutes() : false
                      })
                     
                      // 更新会话flag
                      conversationStarted = true;
            });





});


