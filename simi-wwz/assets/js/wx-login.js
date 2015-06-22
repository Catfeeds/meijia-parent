//alert($);
//if(supports_html5_storage()){alert('ttt')};
$(function(){
    if(!isLogin()){
        //获取验证码
        $('#get_code').bind('click',function(){
            userPhone = $("#user_phone").val();
            if(userPhone==''){
                alert("请填写手机号。");
                return false;
            }
            if(userPhone.length!=11)
            {
              alert("请填写正确的手机号码");
              return false;
            }

            var count = 60;
            var countdown = setInterval(CountDown, 1000);
            function CountDown(){
                $("#get_code").attr("disabled", true);
                $("#get_code").css("background","#999");
                $("#get_code").text(count + "秒后重新获取");
                $("#sms_tips").css("display","block");
                if (count == 0) {
                    $("#get_code").removeAttr("disabled");
                    $("#get_code").css("background","#f37b1d");
                    $("#get_code").text("获取验证码");
                    clearInterval(countdown);
                }
                count--;
            }

            $.ajax({
                type:"GET",
                url:siteAPIPath+"user/get_sms_token.json",
                dataType:"json",
                cache:false,
                data:{"mobile":userPhone,"sms_type":1},
                success : onSmsSuccess,
                error : onSmsError
            });
            return false;
        });

        //登录
        $('#login_submit').bind('click', function(){
            //var formData = $('#loginform').serialize();
            userPhone = $("#user_phone").val();
            verifyCode = $("#verify_code").val();
            if(userPhone=='' || verifyCode=='')
            {
              alert("请填写手机号或验证码。");
              return false;
            }
            if(userPhone.length!=11)
            {
              alert("请填写正确的手机号码");
              return false;
            }

            $.ajax({
                type : "POST",
                url  : siteAPIPath+"sec/login.json",
                dataType: "json",
                cache : false,
                data : {"mobile":userPhone,"sms_token":verifyCode,"login_from":1,"user_type":2},
                success : onLoginSuccess,
                error : onLoginError
            });
            return false;
        });
    }
    else{
        //如果已经登录直接返回到首页?
        //window.history.forward(1);
        window.location.href='index.html';
    }
}());

//获取验证码
function onSmsSuccess(data,status){
    if(data.status != "0"){
        if (data.status =="999")
            alert(data.msg);
        else
            alert("发送验证码的网络繁忙，请稍后再试。");
        return;
    }
    alert("验证码已发送到您的手机，请注意查收。");

    return;
}
function onSmsError(data, status){
  //console.log(data.msg);
  //alert("发送验证码失败，请稍后再试。(5)");
  return;
}

//登录后
function onLoginSuccess(data, status){
	console.log(data);
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("登录出现一些问题，请稍后再试。");
    return;
  }
  //登录成功后记录用户有关信息
  localStorage['sec_mobile'] = data.data.mobile;
  localStorage['sec_id']= data.data.id;
  /*localStorage['user_type']=data.data.user_type;
  localStorage['user_msge_page']=1;*/
  


  //登录后是否立刻获取地址?还是加上吧,当用户修改常用地址后,本地存储用户常用地址，小区id，城市id。

  //跳
  if (typeof queryVal('go')!="null" || queryVal('go')!=''){
    var go = 'wx-'+queryVal('go')+'.html';
    window.location.href=go;
  }
}

function onLoginError(data, status){
//    console.log(data.msg);
    alert("登录网络繁忙，请稍后再试。(5)");
}
