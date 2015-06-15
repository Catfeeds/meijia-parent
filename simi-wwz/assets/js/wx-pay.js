$(function(){
    if(!isLogin()){
        window.location.href="wx-login.html";
    }

    //从url获取当前要支付的服务类型
    sName = 't';
    if(window.location.href.match(new RegExp("(&|\\u003F)" + sName + "=([^&]*)(&|$)"))){
        var serviceType=RegExp.$2;
    }
    else{
        alert('服务类型不正确。');
    }

    var userPhone = localStorage['user_phone'];
    $("#order_money").text(Number(localStorage[serviceType+'_order_money']).toFixed(1)); //显示订单金额
    var sd = localStorage[serviceType+'_service_date'].split('-');
    var dt = new Date(sd[0],sd[1]-1,sd[2]);
    var weekday = dt.getDay();

    $("#service_datetime").text(weekDay[weekday]+' '+localStorage[serviceType+'_service_date']+" " +localStorage[serviceType+'_start_time']);

    var order_pay = localStorage[serviceType+'_order_pay'];
    if(typeof localStorage['coupon_value'] !== 'undefined'){
        var order_pay = parseInt(localStorage[serviceType+'_order_pay']) - parseInt(localStorage['coupon_value']);
    }
    if(order_pay<0)
        order_pay=0;
    //显示实际需要支付的金额
    $("#order_pay").text(Number(order_pay).toFixed(1));
    console.log(order_pay);

    if(order_pay > parseInt(localStorage['user_money'])){
        //$('#pay_2').checked=true;
        $('#pay_2').attr("checked",true);
    }
    else{
        $('#pay_0').attr("checked",true);
    }

    $('#use_coupon').bind('click', function(){
        window.location.href="wx-coupon.html?t="+serviceType;
    });

    if(typeof localStorage['coupon_value'] !== 'undefined'){
        $('#coupon_info').text('为您节省'+localStorage['coupon_value']+'元');
    }
    //获取账户余额等信息
    $.ajax({
        type : "GET",
        url  : siteAPIPath+"user/get_userinfo.json",
        dataType: "json",
        cache : false,
        data : {"mobile":userPhone},
        success : onUserInfoSuccess,
        error : onUserInfoError
    });
    var order_id = localStorage[serviceType+'_order_id'];
    var order_no = localStorage[serviceType+'_order_no'];
    var card_passwd = (typeof localStorage['card_passwd'] !== 'undefined')?localStorage['card_passwd']:0;

    $('#pay_submit').bind('click', function(){
        if(typeof serviceType === 'undefined'){
            alert('服务类型不正确，无法进行支付。');
            return;
        }

        //判断是否选择了支付方式
        var pay_type = $('input:radio[name="pay-type"]:checked').val();
        if(pay_type!="0" && pay_type!="2"){
            alert("请选择支付方式");
            return;
        }
        //余额支付时
        if(pay_type=="0"){
            if(typeof localStorage['user_money']==='undefined'){
                alert('无法获得您账户余额信息，您需要重新登录');
                return;
            }
            if(order_pay > parseInt(localStorage['user_money'])){
                alert("您的余额不足支付本次订单，请选择微信支付或向账户充值");
                return;
            }
            //alert(siteAPIPath+"order/post_pay.json");
            $.ajax({
                type : "POST",
                url  : siteAPIPath+"order/post_pay.json",
                dataType: "json",
                cache : false,
                data : {"mobile":userPhone,
                        "order_id":order_id,
                        "order_no":order_no,
                        "pay_type":pay_type,
                        "card_passwd":card_passwd,
                        "score":0},
                success : onPaySuccess,
                error : onPayError
            });
        }
        else{
//            alert("暂不提供微信支付");
//            return;

            //调用微信支付前需要先调用订单支付接口，调用成功才跳到微信支付
            $.ajax({
                type : "POST",
                url  : siteAPIPath+"order/post_pay.json",
                dataType: "json",
                cache : false,
                data : {"mobile":userPhone,
                        "order_id":order_id,
                        "order_no":order_no,
                        "pay_type":pay_type,
                        "card_passwd":card_passwd,
                        "score":0},
                success : onWXSuccess,
                error : onWXError
            });
        }
    });
    //微信支付前
    var onWXSuccess = function(data, status){
      if(data.status != "0"){
        if (data.status =="999")
            alert(data.msg);
        else
            alert("微信支付网络有点繁忙，请稍后再试。");
        return;
      }

      //alert("支付成功");
      //TODO.....清除和本次支付有关的订单、优惠券等本地存储信息
      localStorage.removeItem('card_passwd');
      localStorage.removeItem('coupon_value');
      localStorage.removeItem('service_type');

      //order_type: 0订单，1会员充值，2管家卡购买
      var weixinPayURL = ['http://www.yougeguanjia.com/onecare-wwz/wwz/wx-pay-pre.jsp?order_no=', order_no,
                        '&card_passwd=',card_passwd,
                        '&order_type=',"0",
                        '&t=',serviceType].join('');
      window.location.href = weixinPayURL;
    }

    var onWXError = function(data, status){
        //console.log(data.msg);
        alert("微信支付网络有点繁忙，请稍后再试。(5)");
    }
}());

function onPaySuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("支付网络有点繁忙，请稍后再试。");
    return;
  }

  //TODO.....清除和本次支付有关的订单、优惠券等本地存储信息
  localStorage.removeItem('card_passwd');
  localStorage.removeItem('coupon_value');
  localStorage.removeItem('service_type');

  //支付完成跳到订单页面
  //window.location.href="wx-order.html";
  location.href="wx-pay-ok.jsp?order_no="+data.data.order_no;
}

function onPayError(data, status){
    //console.log(data.msg);
    alert("支付网络有点繁忙，请稍后再试。(5)");
}

//获取用户余额信息
function onUserInfoSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("获取账户信息出现一些问题，请稍后再试。");
    return;
  }

  //每次支付前更新本地用户当前余额，为支付订单做准备
  localStorage['user_money'] = data.data.rest_money;
  $("#user_money").text(data.data.rest_money);

}

function onUserInfoError(data, status){
    alert("获取账户信息出现一些问题，请稍后再试。(5)");
}
