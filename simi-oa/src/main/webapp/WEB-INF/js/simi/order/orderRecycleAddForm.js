$.validator.addMethod("uniqueMobile", function(value, element) {
  var response;
  $.ajax({
      type: "GET",
      url:"/simi-oa/interface-dict/check-user-by-mobile.json", //发送给服务器的url
      data: "mobile="+value,
      dataType:"json",
      async: false,
	  success: function(msg) {
		  response = msg.data;
	  }
  });
  //不存在，则返回true
  if(response == null) return false;
  //如果存在，则返回false;
  if(response != null)return true;

}, "");
$("#mobile").on('change', function(){
	$mobile = $(this).val();

	//发送ajax请求根据服务大类ID获取服务小类ID
	$.ajax({
		type: 'GET',
		url: appRootUrl + '/interface-dict/get-userAddr-by-mobile.json',
		dataType: 'json',
		cache: false,
		data:{mobile:$mobile},
		success:function(msg){
			 response = msg.data;
			  console.log(response);
				var userAddrHtml = "";
				for(var i=0 ; i < response.length; i++){
					var addrId = response[i].addr_id;
					var addrName = response[i].addr_name;
					userAddrHtml +="<option value =\""+addrId+"\" >"+addrName+"</option>"
				}	
				$("#addrId").append(userAddrHtml);
		},
		error:function(){
			
		}
	});
});
$.validator.addMethod("uniqueAddrId", function(value, element) {
	//发送ajax请求根据服务地址Id贩毒案是否是北京，不是责提示错误
	  var response;
	  $.ajax({
	      type: "GET",
	      url:"/simi-oa/interface-dict/get-isService-by-addrId.json", //发送给服务器的url
	      data: "addrId="+value,
	      dataType:"json",
	      async: false,
		  success: function(msg) {
			  response = msg.data;
		  }
	  });
	  //不存在，则返回true
	  if(response == null) return false;
	  //如果存在，则返回false;
	  if(response != null)return true;

	}, "");
var formVal = $('#order-recycle-add-form').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	ignore : [],
	rules : {
		mobile : {
			required : true,
			uniqueMobile : true
		},
		addrId : {
			required : true,
			uniqueAddrId : true
		},

		linkMan : {
			required : true,
		},

		linkTel : {
			required : true,
		},

	},
	messages : {
		mobile : {
			required : "请输入手机号",
			uniqueMobile : "此用户还未注册！！！"
		},
		addrId : {
			required : "请选择服务地址.",
			uniqueAddrId : "目前仅支持北京市区服务范围，敬请谅解！"
		},

		linkMan : {
			required : "请输入联系人.",
		},

		linkTel : {
			required : "请输入联系电话",
		},
	},

	invalidHandler : function(event, validator) { // display error alert on
													// form submit
		$('.alert-error', $('#order-recycle-add-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error
																	// class to
																	// the
																	// control
																	// group
	},

	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},

	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});
$("#btn_submit").click(function() {
	if (confirm("确认要保存订单信息吗?")) {
		if ($('#order-recycle-add-form').validate().form()) {

			$('#order-recycle-add-form').submit();
		}
	}
});
$('.input-group.date').datepicker({
	format: "yyyy-mm-dd",
	language: "zh-CN",
	autoclose: true,
	startView: 1,
	defaultViewDate : {
		year:1980,
		month:0,
		day:1
	}
});
