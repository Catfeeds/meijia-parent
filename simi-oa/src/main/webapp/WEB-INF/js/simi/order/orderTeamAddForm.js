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
var formVal = $('#order-team-add-form').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	ignore : [],
	rules : {
		mobile : {
			required : true,
			uniqueMobile : true
		},
		attendNum : {
			required : true,
			isIntGtZero : true,
		},

		orderMoney : {
			required : true,
			isFloat : true,
		},

		orderPay : {
			required : true,
			isFloat : true,
		},

		cityId : {
			required : true,
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
		attendNum : {
			required : "请输入参加人数",
			isIntGtZero : "数量只能输入数字",
		},

		orderMoney : {
			required : "请输入总金额",
			isFloat : "金额只能包含数字,并且精确到小数点两位",
		},

		orderPay : {
			required : "请输入支付金额",
			isFloat : "金额只能包含数字,并且精确到小数点两位",
		},

		cityId : {
			required : "请选择活动城市.",
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
		$('.alert-error', $('#order-team-add-form')).show();
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
		if ($('#order-team-add-form').validate().form()) {

			$('#order-team-add-form').submit();
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
