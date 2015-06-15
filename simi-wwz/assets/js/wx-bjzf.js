$(function(){
    if(!isLogin()){
        $('#user_address').bind('focus',function(){
            window.location.href="wx-login.htm?go=baojie";
            return;
        });
    }
    else{
        if(typeof localStorage['user_address']=="undefined" || localStorage['user_address']==''){
            //请求用户地址
            $.ajax({
                type : "GET",
                url  : siteAPIPath+"user/get_addrs.json",
                dataType: "json",
                cache : false,
                data : {"mobile":localStorage['user_phone']},
                success : onUserAddrSuccess,
                error : onUserAddrError
            });
        }
        else{
            var o={"userAddr":localStorage['user_address'],
                   "funCall":"changeAddress()",
                   "buttonText":"更换",
                   "disabled":""
                  };
            displayAddr(o);
        }
    }

    // if(localStorage['user_phone']!=undefined){
        // var userPhone = localStorage['user_phone'];
        //获取用户个人信息
        // $.ajax({
            // type : "GET",
            // url  : siteAPIPath+"user/get_userinfo.json",
            // dataType: "json",
            // cache : false,
            // data : {"mobile":userPhone},
            // success : onUserInfoSuccess,
            // error : onUserInfoError
        // });
    // }

    //保洁订单提交
    $('#baojie_submit').bind('click', function(){
        if(!isLogin()){
            window.location.href="wx-login.htm?go=baojie";
            return;
        }
        if(typeof localStorage['user_city_id'] == "undefined" || localStorage['user_city_id']=='' ||
           typeof localStorage['user_addr_id'] == "undefined" || localStorage['user_addr_id']==''){
           alert("您需要先选择或添加一个服务地址。");
           return;
        }
        
        var userPhone = localStorage['user_phone'];
        var userCityID = localStorage['user_city_id'];
        var userAddressID = localStorage['user_addr_id'];

        var userRemarks = encodeURIComponent($("#user_remark").val());
        var serviceDate = $("#service_date_list").val();
        var startTime = $("#service_time_list").val();
        
        //////
        localStorage['baojie_service_date'] = serviceDate;
        localStorage['baojie_start_time'] = startTime;
        //////
        
        var shArr = $('input:radio[name="service_hours"]:checked').val().split('_');
        var serviceHour = shArr[1];
        var selectType = baseData['baojie'][serviceHour];
        var sendDatas = ['[{"type":"',selectType,'","value":"',serviceHour,'"}]'].join('');
        var serviceType = $("#service_type").val();
        var serviceTool = 0;//$("#service_tool").val();
        var dtArr = serviceDate.split('-');
        var tArr = startTime.split(':');
        var sd = new Date(dtArr[0],dtArr[1],dtArr[2]);
        var st= new Date(dtArr[0],dtArr[1],dtArr[2],parseInt(tArr[0]),parseInt(tArr[1],0));
        serviceDate = sd.getTime()/1000;
        startTime = st.getTime()/1000;

        if($("#user_address")=='')
        {
          alert("请填写地址信息");
          return false;
        }
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
                    "service_hour":serviceHour,
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

//用户信息处理
// function onUserInfoSuccess(data, status){
  // if(data.status != "0"){
    // console.log(data.msg);
    // alert("网络繁忙，请稍后再试。");
    // return;
  // }
  //记录用户余额
  // localStorage['user_money'] = data.data.rest_money;
// }
// function onUserInfoError(data, status){
    // console.log(data.msg);
    // alert("网络繁忙，请稍后再试。(5)");
// }

//用户地址处理
function onUserAddrSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("网络繁忙，请稍后再试。");
    return;
  }
  
  //记录用户常用地址
  if(data.data[0]){
      localStorage['user_address'] = data.data[0].cell_name + data.data[0].addr;
      localStorage['user_addr_id'] = data.data[0].addr_id;//小区id
      localStorage['user_city_id'] = data.data[0].city_id;
      var o={"userAddr":localStorage['user_address'],
             "funCall":"changeAddress()",
             "buttonText":"更换",
             "disabled":""};
     displayAddr(o);
  }
  else{//没有地址
    var o={"userAddr":"预约服务前您需要先添加地址",
           "funCall" :"changeAddress('false','baojie')",
           "buttonText":"添加地址",
           "disabled":"disabled"
           };
    displayAddr(o);
  }
}
function onUserAddrError(data, status){
    console.log(data.msg);
    alert("网络繁忙，请稍后再试。(5)");
}

//订单提交处理
function onSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("网络繁忙，请稍后再试。");
    return;
  }
  //记录订单有关信息
  localStorage['baojie_order_no'] = data.data.order_no;
  localStorage['baojie_order_money'] = data.data.order_money;
  localStorage['baojie_order_pay'] = data.data.order_pay;
  localStorage['baojie_price_hour'] = data.data.price_hour;
  localStorage['baojie_hour_discount'] = data.data.hour_discount;
  window.location.href="wx-pay.htm?t=baojie";
}
function onError(data, status){
    console.log(data.msg);
    alert("网络繁忙，请稍后再试。(5)");
}
