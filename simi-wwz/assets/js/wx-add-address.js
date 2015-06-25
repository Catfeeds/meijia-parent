var userId;
$(function(){
	 userId = $.urlParam('user_id');

}());

//添加新地址
$('#address_add').bind('click',function(){
   /* var cellName = $("#user_cell").val();//提交时不需要小区名，而用小区id
    var addressNo = $("#user_address_no").val();//门牌号
    if(cellName=='' || addressNo == ''){
        alert("亲，别闹，信息要填写完整哦。");
        return;
    }
    if(cellID==0){
        alert("亲，需要选择列表中选择小区，或填写其他小区");
        return;
    }*/
    $.ajax({
        type:"POST",
        url:siteAPIPath+"user/post_addrs.json",
        dataType:"json",
        cache:false,
        data:{"user_id":userId,
              "addr_id":0,//新增地址
              "poi_type":0,//地理位置poi类型
              "name":"宇飞大厦",//
              "address":"东直门612",
              "latitude":"101.11",//经度
              "longitude":"103.22",//维度
              "city":"北京",
              "uid":"ssss",
              "phone":"1313",//地理位置电话
              "post_code":"1313131",//地理位置邮编
              "addr":"621",//门牌号
              "is_default":0,//是否默认小区
              "city_id":"0",//保留字段
              "cell_id":"0"//保留字段
        },
        success : onSuccess,
        error : onError
    });
    return false;
});

function onSuccess(data, status){
  if(data.status != "0"){
    if (data.status =="999")
        alert(data.msg);
    else
        alert("目前无法添加地址，请稍后再试。");
    return;
  }
  var userId = $.urlParam('user_id');
  location.href="wx-addr-list.html?user_id="+userId;
}

function onError(data, status){
    console.log(data.msg);
    alert("亲，可能由于网络原因，目前无法添加地址，请稍后再试吧。(5)");
}
