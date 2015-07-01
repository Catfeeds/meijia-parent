
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
                        day: !conversationStarted ? 'Today' : false,
                        time: !conversationStarted ? (new Date()).getHours() + ':' + (new Date()).getMinutes() : false
                      })
                     
                      // 更新会话flag
                      conversationStarted = true;
            });





});


