
if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
	$.AMUI.validator.patterns.price = /^\d{4}\.\d{2}$/;
	$.AMUI.validator.patterns.disPrice = /^\d{4}\.\d{2}$/;
}

var parentId = getUrlParam("parent_id");
$("#parent_id").val(parentId);
$('#partner-form').validator({
	validate : function(validity) {
	
		//钱数校验
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
		ajaxFileUpload()
	}, function() {
		
	});
});

function ajaxFileUpload() {
     // 开始上传文件时显示一个图片
     $("#wait_loading").ajaxStart(function() {
         $(this).show();
     // 文件上传完成将图片隐藏起来
     }).ajaxComplete(function() {
         $(this).hide();
     });
     
     var params = {};
 	params.parent_id = $('#parent_id').val();
 	params.service_price_id = $('#service_price_id').val();
 	params.user_id = $('#user_id').val();
 	params.partner_id = $('#partner_id').val();
 	params.id = $('#id').val();
 	
 	params.no = $('#no').val();
 	params.name = $('#name').val();
 	params.title = $('#title').val();
 	params.price = $('#price').val();
 	params.dis_price = $('#disPrice').val();
 	params.order_type = $('#orderType').val();
 	params.content_standard = $('#contentStandard').val();
 	params.is_addr = $('input:radio[name=isAddr]:checked').val();
 	params.order_duration = $('#orderDuration').val();
 	params.content_desc =  $('#contentDesc').val()
 	params.content_flow = $('#contentFlow').val();     
    params.parent_id = 1;
    console.log("priceFormSumit");
    console.log(params);
    
     $.ajaxFileUpload({
    	 type : "POST",
 		 url : appRootUrl + "/partner/post_partner_service_price_add.json", // 发送给服务器的url
         secureuri: false, //一般设置为false
         fileElementId: 'imgUrlFile', // 上传文件的id、name属性名
         dataType: 'json', //返回值类型，一般设置为json、application/json
         data: params, //传递参数到服务器
         success: function(data, status){  
             alert(data);
             location.href = "store-price-ok.html";
         },
         error: function(data, status, e){ 
             alert(e);
         }
     });
     //return false;
 }
$("#orderType").change(function(){ 
	var sel = $(this).children('option:selected').val();
	if (sel == "0") {
		$("#orderDurationSelectBox").css('display', 'none');
	}
	
	if (sel == "1") {
		$("#orderDurationSelectBox").css('display', 'block');
	}
});