$(function(){
    //优惠券列表
    if(!isLogin()){
        window.location.href="wx-login.html?go=coupon";
        return;
    }
    var userPhone = localStorage['user_phone'];
    $.ajax({
        type:"GET",
        url:siteAPIPath+"user/get_coupons.json",
        dataType:"json",
        cache:false,
        data:{"mobile":userPhone},
        success : onListSuccess,
        error : onListError
    });
    
    //兑换按钮！
    $('#exchange_submit').bind('click', function(){
        if(!isLogin()){
            window.location.href="wx-login.html?go=coupon";
            return;
        }

        if($("#card_passwd")=='')
        {
          alert("请输入优惠券码");
          return false;
        }
        
        var userPhone = localStorage['user_phone'];
        var cardPasswd = $("#card_passwd").val();

        $.ajax({
            type : "POST",
            url  : siteAPIPath+"user/post_coupon.json",
            dataType: "json",
            cache : false,
            data : {"mobile":userPhone,
                    "card_passwd":cardPasswd
                   },
            success : onExSuccess,
            error : onExError
        });
        return false;
    });
    
    //下单时使用优惠券的处理，点击后返回到支付页面
    if (queryVal('t')!==null && queryVal('t')!==''){
        $('#coupon_list').delegate('li','click',function(){
            //console.log(this.id);
            var lid = this.id;
            var idArr = lid.split('_');
            localStorage['card_passwd'] = idArr[0];
            localStorage['coupon_value'] = idArr[1];
            var serviceTypeArr = idArr[2].split(",");
            var range = idArr[3];
            if(typeof localStorage['service_type'] === 'undefined'){
                alert('无法获取当前服务类型。');
                return;
            }
            console.log(range);
            if(range!=1 && range!=999){
                alert('您所选优惠券不适用微信使用，请选择其他优惠券吧。');
                return;
            }
            if($.inArray(localStorage['service_type'],serviceTypeArr)==-1 && serviceTypeArr[0]!="0"){
                alert('您所选优惠券不适用当前服务，请选择其他优惠券吧。');//您选择的优惠券服务类型不符，请重新选择。
                return;
            }
            

            window.history.go(-1);//返回到前一个支付页面
            // var go = 'wx-pay.html?t='+queryVal('t')+'.html';
            // window.location.href = go;
        });
    }
}());

//获取兑换券列表LIST
function onListSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("目前无法获取兑换券，请稍后再试。");
    return;
  }
  var couponArr = data.data;
  var couponList = [];
  $.each(couponArr,function(i,item){
      var lid = item.card_passwd+'_'+item.value+'_'+item.service_type+'_'+item.rang_from;
      var ha = [];
      ha.push('<li class="am-g am-list-item-desced" style="" id="');
      ha.push(lid);
      ha.push('">');
      ha.push('<div style="margin:0 auto;width:300px;height:90px;background-image:url(images/youhuiquan-mb.png);background-size:300px 90px;font-size:0.8em">');
      ha.push('<div id="card_intro" style="margin:10px;width:70%;float:right;text-align:right;color:#fff;">');
      ha.push(item.introduction);
      ha.push('</div>');
      ha.push('<div id="card_intro" style="margin:10px;margin-top:0;width:70%;float:right;text-align:right;color:#fff;">￥');
      ha.push(item.value);
      ha.push('</div></div>');
      ha.push('<div id="card_desc" style="margin:5px auto 0 auto;width:300px;font-size:0.8em;color:#aaa;">');
      //console.log(typeof item.description);
      if(item.description !== null){
        //console.log('cunck');
        ha.push(item.description.replace(/\r\n/g,'<br/>'));
      }
      ha.push('</div></li>');
      couponList.push(ha.join(''));
      //console.log(ha.join(''));
  });
  if(couponList.length){
    $('#coupon_list').html(couponList.join(''));
  }
}

function onListError(data, status){
    //console.log(data.msg);
    alert("目前无法获取兑换券，请稍后再试。(5)");
}

//兑换后的处理
function onExSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("亲，兑换优惠券出现一些小问题，请稍后再试。");
    return;
  }
  
  alert('优惠券兑换成功');
  $("#card_passwd").attr("placeholder","输入优惠券密码");
  $('#card_passwd').val('');
  
  //data.data.coupon_id;
  //data.data.range_type;   //=0,1
  //data.data.service_type; //=0,1,2,3...7
  var lid = data.data.card_passwd+'_'+data.data.value+'_'+data.data.service_type+'_'+data.data.rang_from;

  //将兑换券有关信息显示到页面上
  var ha = [];
  ha.push('<li class="am-g am-list-item-desced" style="" id="');
  ha.push(lid);
  ha.push('">');
  ha.push('<div style="margin:0 auto;width:300px;height:90px;background-image:url(images/youhuiquan-mb.png);background-size:300px 90px;font-size:0.8em">');
  ha.push('<div id="card_intro" style="margin:10px;width:70%;float:right;text-align:right;color:#fff;">');
  ha.push(data.data.introduction);
  ha.push('</div>');
  ha.push('<div id="card_intro" style="margin:10px;margin-top:0;width:70%;float:right;text-align:right;color:#fff;">￥');
  ha.push(data.data.value);
  ha.push('</div></div>');
  ha.push('<div id="card_desc" style="margin:5px auto 0 auto;width:300px;font-size:0.8em;color:#aaa;">');
  if(typeof data.data.description !== 'null'){
    ha.push(data.data.description.replace(/\r\n/,'<br/>'));
  }
  ha.push('</div></li>');
  //if($('#coupon_list').children().length>0)
    $('#coupon_list').prepend(ha.join(''));
  //else
  //  $('#coupon_list').html(ha.join(''));
  
  //跳到该去的地方×，兑换完后不能瞎跳。
  //window.location.href="wx-pay.html?t=baojie";
}
function onExError(data, status){
    //console.log(data.msg);
    alert("亲，兑换优惠券出现一些小问题，请稍后再试。(5)");
}
