
var userId = getUrlParam("user_id");
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_partnerId_by_user_id.json?user_id="+userId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var partnerUsers = data.data;
		var partnerId = partnerUsers.partner_id;
		var serviceTypeId = partnerUsers.service_type_id;
		$("#partner_id").val(partnerId);
		$("#user_id").val(userId);
		$("#service_type_id").val(serviceTypeId);

	}
});
var servicePriceId = getUrlParam("service_price_id");
if(servicePriceId != 0){

//获得商品详细信息放到form表单里面
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_partner_service_price_detail.json?service_price_id="+servicePriceId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var vo = data.data;
		$("#name").val(vo.name);
		
		//图像:
		var img_url = vo.img_url;
		if (img_url != undefined && img_url !="") {
			
			$("#imgUrlPrDiv").css('display','block');
			$("#imgUrlPr").attr('src',img_url); 
		} 
		
		$("#title").val(vo.service_title);
		$("#price").val(vo.price);
		
		$("#disPrice").val(vo.dis_price);
		$("#orderType").val(vo.order_type);
		$("#orderDuration").val(vo.order_duration);
		$("#isAddr").val(vo.is_addr);
		$("#contentStandard").val(vo.content_standard);
		$("#contentDesc").val(vo.content_desc);
		$("#contentFlow").val(vo.content_flow);
		$("#isEnable").val(vo.is_enable);
		
	}
});}
getUrlParam("parent_id");
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
    //参数
 	params.user_id = getUrlParam("user_id");
 	params.service_price_id = getUrlParam("service_price_id");
 	params.partner_id = $('#partner_id').val();
 	params.service_type_id = $('#service_type_id').val();
 	params.name = $('#name').val();
 	params.title = $('#title').val();
 	params.price = $('#price').val();
 	params.dis_price = $('#disPrice').val();
 	params.order_type = $('#orderType').val();
 	params.content_standard = $('#contentStandard').val();
 	params.is_addr = $('input:radio[name=isAddr]:checked').val();
 	params.is_enable = $('input:radio[name=isEnable]:checked').val();
 	params.order_duration = $('#orderDuration').val();
 	params.content_desc =  $('#contentDesc').val()
 	params.content_flow = $('#contentFlow').val();     

    
     $.ajaxFileUpload({
    	 type : "POST",
 		 url : appRootUrl + "/partner/post_partner_service_price_add.json", // 发送给服务器的url
         secureuri: false, //一般设置为false
         fileElementId: 'imgUrlFile', // 上传文件的id、name属性名
         dataType: 'json', //返回值类型，一般设置为json、application/json
         data: params, //传递参数到服务器
         success: function(data, status){  
            // alert(data);
            // location.href = "store-price-ok.html";
        	 var userId = getUrlParam("user_id");
        	 location.href = "store-price-list.html?user_id="+userId;
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