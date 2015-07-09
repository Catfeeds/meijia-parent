webim = {
    
    appkey: "yougeguanjia#simi",
    myMessages: null,				//用户保存对话对象
    conversationStarted: false, 	//正在发送消息的标记
    myMessagebar: null,				//输入框
    textSending: false,				//正在发送消息标志[用于发送消息]


    //登录服务器
    login: function(user, pass, conn){
        if (user == '' || pass == '') {
            alert("请输入用户名和密码");
            return;
        }

        //根据用户名密码登录系统
        conn.open({
            user : user,
            pwd : pass,
            //连接时提供appkey
            appKey : this.appkey
            //accessToken : 'YWMt8bfZfFk5EeSiAzsQ0OXu4QAAAUpoZFOMJ66ic5m2LOZRhYUsRKZWINA06HI'
        });
        return false;
    },



    //发送文本消息
    //参数：msg 消息
   	//	   curChatUserId 发送对象id 		
    sendText: function(msg, curChatUserId){
                
                var webim = this;

                if (this.textSending)return;

                this.textSending = true;

                //var msgInput = document.getElementById(talkInputId);
                //var msg = msgInput.value;

                if (msg == null || msg.length == 0) {
                    	return;
                }
                var to = curChatUserId;
                if (to == null) {
                    	return;
                }
                var options = {
	                    to : to,
	                    msg : msg,
	                    type : "chat"
                };

                // 群组消息和个人消息的判断分支
                // if (curChatUserId.indexOf(groupFlagMark) >= 0) {
	               //      options.type = 'groupchat';
	               //      options.to = curRoomId;
                // }
                //easemobwebim-sdk发送文本消息的方法 to为发送给谁，meg为文本消息对象
                conn.sendTextMessage(options);

                //当前登录人发送的信息在聊天窗口中原样显示
                // var msgtext = msg.replace(/\n/g, '<br>');
                // appendMsg(curUserId, to, msgtext);
                // turnoffFaces_box();
                // msgInput.value = "";
                // msgInput.focus();

                setTimeout(function() {
                    webim.textSending = false;
                }, 1000);
    },

    //登录成功处理函数
    handleOpen: function(conn){

//	        console.log('handleOpen');

	        //从连接中获取到当前的登录人注册帐号名
	        curUserId = conn.context.userId;
	        conn.setPresence();  //设置在线状态，非常重要，否则收不到信息！！！
	        //获取当前登录人的联系人列表
	        conn.getRoster({
		            success : function(roster) {
//		            		console.log('roster');
			                //页面处理
			                //hiddenWaitLoginedUI();
			                //showChatUI();
			                var curroster;
			                for ( var i in roster) {
			                    var ros = roster[i];
			                    //both为双方互为好友，要显示的联系人,from我是对方的单向好友
			                    if (ros.subscription == 'both' || ros.subscription == 'from') {
			                        //bothRoster.push(ros);
//			                        console.log(ros);
			                    } else if (ros.subscription == 'to') {
			                        //to表明了联系人是我的单向好友
			                        //toRoster.push(ros);
//			                        console.log(ros);
			                    }
			                }
			                if (bothRoster.length > 0) {
			                    curroster = bothRoster[0];
			                    // buildContactDiv("contractlist", bothRoster);//联系人列表页面处理
			                    if (curroster){
			                        // setCurrentContact(curroster.name);//页面处理将第一个联系人作为当前聊天div
			                    }
			                        
			                }
			                //获取当前登录人的群组列表
			                conn.listRooms({
			                    success : function(rooms) {
			                        if (rooms && rooms.length > 0) {
			                            //buildListRoomDiv("contracgrouplist", rooms);//群组列表页面处理
			                            if (curChatUserId == null) {
			                                //setCurrentContact(groupFlagMark + rooms[0].roomId);
			                                //$('#accordion2').click();
			                            }
			                        }
			                        //设置用户上线状态，必须调用
			                        // conn.setPresence();
			                    },
			                    error : function(e) {

			                    }
			                });
		            }
	        });

    },

    //登录失败处理函数
    handleError: function(e){
//        console.log('handleError');
        // console.log(e);
        // if (curUserId == null) {
        //     hiddenWaitLoginedUI();
        //     alert(e.msg + ",请重新登录");
        //     showLoginUI();
        // } else {
             var msg = e.msg;
             if (e.type == EASEMOB_IM_CONNCTION_SERVER_CLOSE_ERROR) {
                 if (msg == "" || msg == 'unknown' ) {
                     alert("服务器器断开连接,可能是因为在别处登录");
                     localStorage.removeItem("mobile");
	           		  localStorage.removeItem('sec_id');
	           		  localStorage.removeItem('im_username');
	           		  localStorage.removeItem('im_password');
	           		  mainView.router.loadPage("index.html");
                 } else {
                     alert("服务器器断开连接");
                 }
             } 
//             else {
//                 alert(msg);
//             }
//         }
    },

    //控制消息的红点显示与隐藏
 	newMessageDot: function (flag){
	        if(flag){
	                $$('#messageDot').removeClass('hidden');
	        }else{
	                $$('#messageDot').addClass('hidden');
	        }
	},

	//处理文本消息
    handleTextMessage : function(message) {

//    		console.log(message);

    		
	        var webim = this;
	        var from = message.from;//消息的发送者
	        var mestype = message.type;//消息发送的类型是群组消息还是个人消息
	        var messageContent = message.data;//文本消息体
	        //TODO  根据消息体的to值去定位那个群组的聊天记录
	        var room = message.to;

	        if (mestype == 'groupchat') {
	            // appendMsg(message.from, message.to, messageContent, mestype);
	        } else {
	            // appendMsg(from, from, messageContent);

	                    var messageType = 'received';
	                    // 接收的消息的头像和名称
	                    var avatar, name;
	                    if(messageType === 'received') {
	                            avatar = 'http://lorempixel.com/output/people-q-c-100-100-9.jpg';
	                            name = from;
	                    }
	                    this.myMessages.addMessage({
			                    // Message text
			                    text: messageContent,
			                    // 随机消息类型
			                    type: messageType,
			                    // 头像和名称
			                    avatar: avatar,
			                    name: from,
			                    // 日期
			                    day: !webim.conversationStarted ? '今天' : false,
			                    time: !webim.conversationStarted ? (new Date()).getHours() + ':' + (new Date()).getMinutes() : false
	                    })
	        }


    },


    handleClosed: function(){
//    	console.log('handleClosed');
    },


    handleEmotion: function(){
//    	console.log('handleEmotion');
    },


    handlePictureMessage: function(){
//    	console.log('handlePictureMessage');
    },


    handleAudioMessage: function(){
//    	console.log('handleAudioMessage');
    },


    handleLocationMessage: function(){
//    	console.log('handleLocationMessage');
    },


    handleFileMessage: function(){
//    	console.log('handleFileMessage');
    },


    handleVideoMessage: function(){
//    	console.log('handleVideoMessage');
    },


    handlePresence: function(){
//    	console.log('handlePresence');
    },


    handleRoster: function(){
//    	console.log('handleRoster');
    },


    handleInviteMessage: function(){
//    	console.log('handleInviteMessage');
    }




}



var conn = new Easemob.im.Connection();
//初始化连接
conn.init({
	    https : false,
	    //当连接成功时的回调方法
	    onOpened : function() {
	    	//console.log('onOpened');
	        webim.handleOpen(conn);
	    },
	    //当连接关闭时的回调方法
	    onClosed : function() {
	        webim.handleClosed();
	    },
	    //收到文本消息时的回调方法
	    onTextMessage : function(message) {
//	    	console.log('ceshiceshi!!!');
	        webim.handleTextMessage(message);
	    },
	    //收到表情消息时的回调方法
	    onEmotionMessage : function(message) {
	        webim.handleEmotion(message);
	    },
	    //收到图片消息时的回调方法
	    onPictureMessage : function(message) {
	        webim.handlePictureMessage(message);
	    },
	    //收到音频消息的回调方法
	    onAudioMessage : function(message) {
	        webim.handleAudioMessage(message);
	    },
	    //收到位置消息的回调方法
	    onLocationMessage : function(message) {
	        webim.handleLocationMessage(message);
	    },
	    //收到文件消息的回调方法
	    onFileMessage : function(message) {
	        webim.handleFileMessage(message);
	    },
	    //收到视频消息的回调方法
	    onVideoMessage : function(message) {
	        webim.handleVideoMessage(message);
	    },
	    //收到联系人订阅请求的回调方法
	    onPresence : function(message) {
	        webim.handlePresence(message);
	    },
	    //收到联系人信息的回调方法
	    onRoster : function(message) {
	        webim.handleRoster(message);
	    },
	    //收到群组邀请时的回调方法
	    onInviteMessage : function(message) {
	        webim.handleInviteMessage(message);
	    },
	    //异常时的回调方法
	    onError : function(message) {
	        webim.handleError(message);
	    }
});

var imID        = localStorage['im_username'];
var imPWD       = localStorage['im_password'];

//执行登录
webim.login(imID, imPWD, conn);

