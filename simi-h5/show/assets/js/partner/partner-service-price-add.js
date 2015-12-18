if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
	$.AMUI.validator.patterns.price = /^\d{4}\.\d{2}$/;
	$.AMUI.validator.patterns.disPrice = /^\d{4}\.\d{2}$/;
}
$('#partner-form').validator({
	validate : function(validity) {
		// Ajax 验证
		if ($(validity.field).is('.js-ajax-validate')) {
			// 异步操作必须返回 Deferred 对象
			var price = $('#price').val();
			
			if (!verifyPrice(price)) {
				alert("请输入正确的钱数price!");
				validity.valid = false;
				return validity;
			}
			/*var disPrice = $('#disPrice').val();
			if (disPrice == undefined || disPrice == "") {
				validity.valid = false;
				return validity;
			}
			if (!verifyMobile(disPrice)) {
				alert("请输入正确的钱数disprice!");
				validity.valid = false;
				return validity;
			}*/

		}
		
		//身份证ID校验
		if ($(validity.field).is('.js-ajax-validate')) {
			var  price = $('#price').val();  
			validity.valid = verifyPrice(price);

			return validity;    
		}  

	},
	// 域验证通过时添加的操作，通过该接口可定义各种验证提示
	markValid : function(validity) {
		// this is Validator instance
		var options = this.options;
		var $field = $(validity.field);
		var $parent = $field.closest('.am-form-group');
		$field.addClass(options.validClass).removeClass(options.inValidClass);

		$parent.addClass('am-form-success').removeClass('am-form-error');

		options.onValid.call(this, validity);
	},
});
$("#add_btn").on('click', function(e) {
	console.log("reg-submit click");
	var $form = $('#partner-form');
	var validator = $form.data('amui.validator');
	var formValidity = validator.isFormValid()
	$.when(formValidity).then(function() {
		companyRegSubmit()
	}, function() {
		
	});
});
//提交
function companyRegSubmit() {

	var params = {};
	params.parent_id = $('#parent_id').val();
	params.service_price_id = $('#service_price_id').val();
	params.user_id = $('#user_id').val();
	params.partner_id = $('#partner_id').val();
	params.id = $('#id').val();
	
	params.no = $('#no').val();
	params.name = $('#name').val();
	params.imgUrlFile = $('#imgUrlFile').val();
	//params.imgUrlFile = $('#file-list').val();
	params.title = $('#title').val();
	params.price = $('#price').val();
	params.dis_price = $('#disPrice').val();
	params.order_type = $('#orderType').val();
	params.content_standard = $('#contentStandard').val();
	params.is_addr = $('input:radio[name=isAddr]:checked').val();
	params.content_standard = $('#contentStandard').val();
	params.content_desc =  $('#contentDesc').val()
	params.content_flow = $('#contentFlow').val();
	// 提交数据，完成注册流程
	$.ajax({
		type : "POST",
		url : appRootUrl + "/partner/post_partner_service_price_add.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {

			if (data.status == "999") {
				alert(data.msg);
				return false;
			}

			if (data.status == 0) {
				location.href = "store-price-add-ok.html";
			}
		},
		error : function() {
			return false;
		}
	});
}
$(function() {
    $('#imgUrlFile').on('change', function() {
      var fileNames = '';
      $.each(this.files, function() {
        fileNames += '<span class="am-badge">' + this.name + '</span> ';
      });
      $('#file-list').html(fileNames);
    });
  });
$('#imgUrl').change(function(){
	 $("#img_url_new").val($("#imgUrl").val());
});