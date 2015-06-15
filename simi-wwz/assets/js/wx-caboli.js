$(function(){
    if(!isLogin()){
        $('#user_address').bind('focus',function(){
            window.location.href="wx-login.html?go=caboli";
            return;
        });
    }
    else{
        loadAddr();
        if(localStorage['user_type']=='1'){
            $('#shiji').css('display','block');
        }
    }
    localStorage.removeItem('coupon_value');
    localStorage.removeItem('card_passwd');
    dateListHTML = createDateList();
    $("#service_date_list").html(dateListHTML);
    changeServiceTimeList();

    //擦玻璃订单提交
    $('#caboli_submit').bind('click', function(){
        if(!isLogin()){
            window.location.href="wx-login.html?go=caboli";
            return;
        }
        if(typeof localStorage['user_city_id'] == "undefined" || localStorage['user_city_id']=='' ||
           typeof localStorage['user_addr_id'] == "undefined" || localStorage['user_addr_id']==''){
           alert("您需要先选择或添加一个地址。");
           return;
        }
        
        if($("#user_address").val()==''){
          alert("请填写地址信息");
          return false;
        }
        if($("#user_bolisqr").val()==''){
          alert("请填写玻璃平米数");
          return false;
        }
        if(isNaN($("#user_bolisqr").val())){
            alert("玻璃平米数需填写数字");
            return;
        }
        if($("#user_bolisqr").val()<10){
          alert("玻璃面积需要大于 10 平米哦");
          return false;
        }
        var userPhone = localStorage['user_phone'];
        var userCityID = localStorage['user_city_id'];
        var userAddressID = localStorage['user_addr_id'];

        var agentMobile = 0;
        var customName = '';
        if(localStorage['user_type']=='1'){
            if($('#true_phone').val()=='' || $('#true_user').val()==''){
                alert('代理商预约必须填写实际联系人电话和姓名');
                return false;
            }
            if($('#true_phone').val().length!=11){
                alert('请填写正确的联系人手机号');
                return false;
            }
            userPhone = $('#true_phone').val();
            agentMobile = localStorage['user_phone'];
            customName = $('#true_user').val();
        }

        var userRemarks = encodeURIComponent($("#user_remark").val());
        var serviceDate = $("#service_date_list").val();
        var startTime = $("#service_time_list").val();
        var serviceHour = 4;//服务时长
        var dtArr = serviceDate.split('-');
        var tArr = startTime.split(':');
        if((parseInt(tArr[0])+parseInt(tArr[1])/60)+parseInt(serviceHour)>22){
            alert("您所选的服务时段或时长无法完成预约，请重新选择！");
            return false;
        }
        
        //////
        localStorage['caboli_service_date'] = serviceDate;
        localStorage['caboli_start_time'] = startTime;
        //////
        
        var selectType = 501;//擦玻璃
        var serviceSqr = $("#user_bolisqr").val();
        var sendDatas = ['[{"type":"',selectType,'","value":"',serviceSqr,'"}]'].join('');
        var serviceType = $("#service_type").val();
        localStorage['service_type'] = serviceType;
        var serviceTool = 0;
        
        var sd = new Date(dtArr[0],dtArr[1]-1,dtArr[2]);
        var st= new Date(dtArr[0],dtArr[1]-1,dtArr[2],parseInt(tArr[0]),parseInt(tArr[1],0));
        serviceDate = sd.getTime()/1000;
        startTime = st.getTime()/1000;

        $.ajax({
            type : "POST",
            url  : siteAPIPath+"order/post_add.json",
            dataType: "json",
            cache : false,
            data : {"mobile":userPhone,
                    "city_id":userCityID,
                    "service_type":serviceType,
                    "send_datas":sendDatas,
                    "service_date":serviceDate,
                    "start_time":startTime,
                    "service_hour":0,
                    "addr_id":userAddressID,
                    "remarks":userRemarks,
                    "clean_tools":serviceTool,
                    "order_from":1,
                    "agent_mobile":agentMobile,
                    "custom_name":customName},
            success : onSuccess,
            error : onError
        });
        return false;
    });
}());

//订单提交处理
function onSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("亲，订单处理出现一些小问题，请稍后再试。");
    return;
  }
  //记录订单有关信息
  localStorage['caboli_order_id'] = data.data.order_id;
  localStorage['caboli_order_no'] = data.data.order_no;
  localStorage['caboli_order_money'] = data.data.order_money;
  localStorage['caboli_order_pay'] = data.data.order_pay;
  localStorage['caboli_price_hour'] = data.data.price_hour;
  localStorage['caboli_hour_discount'] = data.data.hour_discount;
  window.location.href="wx-pay.html?t=caboli";
  
  // var qstr = ['t=', 'baojie',
              // '&o=', data.data.order_no,
              // '&d=', data.data.order_id,
              // '&f=', data.data.order_money,
              // '&p=', data.data.order_pay,
              // '&h=', data.data.price_hour,//每小时单价
              // '&c=', data.data.hour_discount,//每小时优惠价
              // '&sd=', data.data.service_date,
              // '&st=', data.data.start_time].join('');
  // window.location.href="wx-pay1.php?"+qstr;
  // window.location.href="http://www.yougeguanjia.com/wx-pay1.php?"+qstr;
}
function onError(data, status){
    console.log(data.msg);
    alert("亲，订单处理出现一些小问题，请稍后再试。(5)");
}
