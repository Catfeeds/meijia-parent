
if ($.AMUI && $.AMUI.validator) {
	$.AMUI.validator.patterns.mobile = /^\s*1\d{10}\s*$/;
	$.AMUI.validator.patterns.price = /^\d{4}\.\d{2}$/;
	$.AMUI.validator.patterns.disPrice = /^\d{4}\.\d{2}$/;
}
var partnerId = getUrlParam("partner_id");

console.log(partnerId);
$.ajax({
	type : "GET",
	url : appRootUrl + "dict/get_service_type_by_partnerId_list.json?partner_id="+partnerId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var partnerServiceType = data.data;
		//console.log(partnerServiceType);
		
		var partnerServiceTypeHtml = "";
		for(var i=0 ; i < partnerServiceType.length; i++){
			var partnerServiceTypeId = partnerServiceType[i].id;
			//console.log(partnerServiceTypeId);
			var partnerServiceTypeName = partnerServiceType[i].name;
			//console.log(partnerServiceTypeName);
			partnerServiceTypeHtml +="<option value =\""+partnerServiceTypeId+"\" >"+partnerServiceTypeName+"</option>"
		}	
		$("#partnerServiceTypeId").val(partnerServiceTypeId);
		$("#partnerServiceTypeName").append(partnerServiceTypeHtml);

	}
});
$.ajax({
	type : "GET",
	url : appRootUrl + "dict/get_dict_province_list.json?",
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var partnerServiceType = data.data;
		//console.log(partnerServiceType);
		
		var partnerServiceTypeHtml = "";
		for(var i=0 ; i < partnerServiceType.length; i++){
			var provinceId = partnerServiceType[i].province_id;
			var provinceName = partnerServiceType[i].name;
			partnerServiceTypeHtml +="<option value =\""+provinceId+"\" >"+provinceName+"</option>"
		}	
		$("#provinceId").val(provinceId);
		$("#provinceName").append(partnerServiceTypeHtml);

	}
});
$.ajax({
	type : "GET",
	url : appRootUrl + "partner/get_company_by_companyId.json?partner_id="+partnerId,
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
	
		if (data.status == "999") return false;
		var partnerServiceType = data.data;
		//console.log(partnerServiceType);
		var companyName = partnerServiceType.company_name;
		console.log(companyName);
		$("#companyName").html(companyName);

	}
});
$.ajax({
	type : "GET",
	url : appRootUrl + "dict/get_tag_list.json?tag_type=2",
	dataType : "json",
	cache : true,
	async : false,	
	success : function(data) {
		if (data.status == "999") return false;
		var tags = data.data;
		console.log(tags);
		var tagHtml = "";
		for (var i = 0; i < tags.length; i++) {
			var tagId = tags[i].tag_id;
			var tagName = tags[i].tag_name;
			tagHtml += "<button class=\"am-btn am-btn-default am-round\" id=\""+tagId+"\" type=\"button\" onClick=\"tagClick("+tagId+")\" >" + tagName + "</button>&nbsp;";
		}
		$("#tagNames").append(tagHtml);
	}
});
function tagClick(tagId) {
	// 如果未选中，则换class为选中
	console.log("tagClick");
	var tagIds = $("#tag_ids").val();
	var obj = $('#'+tagId);
	console.log(obj);
	// am-btn-warning = 选中
	if (obj.is(".am-btn-default")) {
		obj.addClass("am-btn-warning");
		obj.removeClass("am-btn-default");

		if (tagIds.indexOf(tagId + ",") < 0) {
			tagIds += tagId + ",";
		}
	// am-btn-default = 未选中
	} else {
		obj.removeClass("am-btn-warning");
		obj.addClass("am-btn-default");
		if (tagIds.indexOf(tagId + ",") >= 0) {
			tagIds = tagIds.replace(tagId + ",", "");
		}
	}

	$("#tag_ids").val(tagIds);

	console.log($("#tag_ids").val());
}
/*var partnerId = getUrlParam("partner_id");
var id = getUrlParam("id");
var userId = getUrlParam("user_id");
$("#partner_id").val(partnerId);*/
$('#partnerUser-form').validator({
	validate : function(validity) {
	
		//手机号校验
		if ($(validity.field).is('.js-ajax-validate')) {
			var  mobile = $('#mobile').val(); 
			console.log(mobile);
			validity.valid = verifyMobile(mobile);

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
	var $form = $('#partnerUser-form');
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
    params.partner_id = getUrlParam("partner_id");
    params.id = getUrlParam("id");
    params.user_id = getUrlParam("user_id");

 	params.mobile = $('#mobile').val();
 	params.name = $('#name').val();
 	params.partner_service_type_id = $('#partnerServiceTypeId').val();
 	params.province_id = $('#provinceId').val();
 	params.city_id = $('#cityId').val();
 	params.region_id = $('#regionId').val();
 	params.response_time = $('#responseTime').val();
 	params.introduction = $('#introduction').val();
 	params.tag_ids = $('#tag_ids').val();
 	//console.log(tag_ids+"=========tag_ids");
    console.log(params);
    
     $.ajaxFileUpload({
    	 type : "POST",
 		 url : appRootUrl + "/partner/post_partnerUser_form.json", // 发送给服务器的url
         secureuri: false, //一般设置为false
         fileElementId: 'imgUrlFile', // 上传文件的id、name属性名
         dataType: 'json', //返回值类型，一般设置为json、application/json
         data: params, //传递参数到服务器
         success: function(data, status){  
            // alert(data);
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