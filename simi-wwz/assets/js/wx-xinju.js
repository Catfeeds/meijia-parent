$(function(){
    if(!isLogin()){
        $('#user_address').bind('focus',function(){
            window.location.href="wx-login.html?go=xinju";
            return;
        });
    }
    else{
        loadAddr();
    }
    localStorage.removeItem('coupon_value');
    localStorage.removeItem('card_passwd');
    dateListHTML = createDateList();
    $("#service_date_list").html(dateListHTML);
    changeServiceTimeList();
    
    //新居订单提交
    $('#xinju_submit').bind('click', function(){
        if(!isLogin()){
            window.location.href="wx-login.html?go=xinju";
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
        if($("#user_roomsqr").val()==''){
          alert("请填写房屋平米数");
          return false;
        }
        if(isNaN($("#user_roomsqr").val())){
            alert("房屋平米数需填写数字");
            return;
        }
        if($("#user_roomsqr").val()<20){
          alert("房屋面积需要大于20平米哦");
          return false;
        }
        var userPhone = localStorage['user_phone'];
        var userCityID = localStorage['user_city_id'];
        var userAddressID = localStorage['user_addr_id'];

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
        localStorage['xinju_service_date'] = serviceDate;
        localStorage['xinju_start_time'] = startTime;
        //////
        
        var selectType = 701;//新居
        var serviceSqr = $("#user_roomsqr").val();
        if(serviceSqr >51 && serviceSqr<=200)
            selectType = 702;
        else if (serviceSqr>200)
            selectType = 703;
        var sendDatas = ['[{"type":"',selectType,'","value":"',serviceSqr,'"}]'].join('');
        var serviceType = $("#service_type").val();
        localStorage['service_type'] = serviceType;
        var serviceTool = 0;
        var dtArr = serviceDate.split('-');
        var tArr = startTime.split(':');
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
                    "order_from":1},
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
  localStorage['xinju_order_id'] = data.data.order_id;
  localStorage['xinju_order_no'] = data.data.order_no;
  localStorage['xinju_order_money'] = data.data.order_money;
  localStorage['xinju_order_pay'] = data.data.order_pay;
  localStorage['xinju_price_hour'] = data.data.price_hour;
  localStorage['xinju_hour_discount'] = data.data.hour_discount;
  window.location.href="wx-pay.html?t=xinju";
}
function onError(data, status){
    console.log(data.msg);
    alert("亲，订单处理出现一些小问题，请稍后再试。(5)");
}
