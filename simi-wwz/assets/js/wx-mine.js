$(function(){
    if(!isLogin()){
        window.location.href="wx-login.html?go=mine";
        return;
    }

    var userPhone = localStorage['user_phone'];
    var secId = localStorage['sec_id'];

    //获取账户余额等信息
    $.ajax({
        type : "POST",
        url  : siteAPIPath+"sec/get_secinfo.json",
        dataType: "json",
        cache : false,
        data : {"mobile":userPhone,"sec_id":1},
        success : onSecInfoSuccess,
        error : onSecInfoError
    });
    // var order_id = localStorage[serviceType+'_order_id'];
    // var order_no = localStorage[serviceType+'_order_no'];
    // var card_passwd = (typeof localStorage['coupon_value'] !== 'undefined')?localStorage['coupon_value']:0;
    
    //获取充值卡信息接口
    /*$.ajax({
        type : "GET",
        url  : siteAPIPath+"dict/get_cards.json",
        dataType: "json",
        cache : false,
        data : {},
        success : onCardSuccess,
        error : onCardError
    });*/
}());

function onSecInfoSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("获取账户信息出现一些问题，请稍后再试。");
    return;
  }
  $("#user_phone").text(data.data.mobile);
  var yuan = data.data.rest_money+'元';
  $("#user_money").text(1);
}
/*
function onSecInfoError(data, status){
    //console.log(data.msg);
    alert("获取账户信息出现一些问题，请稍后再试。(5)");
}

function onCardSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else{
        console.log('error');
        alert("获取账户信息出现一些问题，请稍后再试。");
    }
    return;
  }
  var H = [];
  $.each(data.data,function(i,item){
      var a = [];
      a.push('<tr>');
      a.push('<td align="left"><span class="am-icon-btn-md am-icon-credit-card"></span> ');
      a.push(item.name);
      a.push('</td>');
      a.push('<td>');
      a.push(item.card_pay);
      a.push('元（<span class="am-text-danger">返');
      a.push(item.card_value - item.card_pay);
      a.push('</span>）');
      a.push('</td>');
      a.push('<td>');
      a.push('<button class="am-btn am-btn-default am-btn-xs am-text-danger" onclick="javascript:buyCard(\'');
      a.push(item.id);
      a.push('\');"><span class="am-icon-money"></span> 购买</button>');
      a.push('</td>');
      a.push('</tr>');
      H.push(a.join(''));
  });

  $("#card_list").html(H.join(''));
}

function onCardError(data, status){
    //console.log(data.msg);
    alert("获取会员卡信息出现一些问题，请稍后再试。(5)");
    return;
}
*/
//会员卡充值购买
/*function buyCard(cardID){
    //alert('购买'+cardID);
    var userMobile = localStorage['user_phone'];
    $.ajax({
        type : "POST",
        url  : siteAPIPath+"user/card_buy.json",
        dataType: "json",
        cache : false,
        data : {"mobile":userMobile,
                "card_type":cardID,
                "pay_type":2},
        success : onBuySuccess,
        error : onBuyError
    });
}
//充值接口调用成功后调用微信支付接口
function onBuySuccess(data, status){
    //alert(data.data.card_order_no);
    if(data.status != "0"){
        if (data.status =="999")
          alert(data.msg);
        else
          alert("会员卡购买出现一些问题，请稍后再试。");
        return;
    }
    //order_type: 0订单，1会员充值，2管家卡购买
    var weixinPayURL = ['http://www.yougeguanjia.com/onecare-wwz/wwz/wx-pay-pre.jsp?order_no=', data.data.card_order_no,
                        '&card_passwd=','0',
                        '&order_type=','1',
                        '&t=','mine'].join('');
    window.location.href = weixinPayURL;
    return;
}
function onBuyError(){
    alert("购买会员卡出现一些问题，请稍后再试。(5)");
    return;
}*/