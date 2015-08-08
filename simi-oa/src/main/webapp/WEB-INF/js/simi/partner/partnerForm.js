$('#partner-form').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		shortName : {
			required : true
		},
		companySize : {
			required : true,
			digits:true 
		},
		isDoor : {
			required : true
		},
		keywords : {
			maxlength:64,
			required : true
		},
		status : {
			required : true
		},
		weixin : {
			maxlength:16,
			digits:true
		},
		qq : {
			maxlength:16,
			digits:true
		},
		fax : {
			maxlength:16,
			digits:true
		},
		email : {
			email:true
		},
		discout : {
			range:[0,1]
		},
	},
	messages : { 
		shortName : {
			required : "请输入服务商简称"
		},
		companySize : {
			required : true,
			digits: "请输入整数" 
		},
		isDoor : {
			required : "请选择是否上门"
		},
		keywords : {
			maxlength:"最多输入64个汉字",
			required : "请输入关键词"
		},
		status : {
			required : "请选择状态"
		},
		weixin : {
			maxlength:"最多输入16个数字",
			digits:"请输入整数"
		},
		qq : {
			maxlength:"最多输入16个数字",
			digits:"请输入整数"
		},
		fax : {
			maxlength:"最多输入16个数字",
			digits:"请输入整数"
		},
		email : {
			email : "请输入正确的邮箱格式"
		},
		discout : {
			range : "请输入折扣价在0到1之间"
		},
	},

	invalidHandler : function(event, validator) { // display error alert on
													// form submit
		$('.alert-error', $('#partner-form')).show();
	},

	highlight : function(element) { // hightlight error inputs
		$(element).closest('.form-group').addClass('has-error'); // set error
	},
	success : function(label) {
		label.closest('.form-group').removeClass('has-error');
		label.remove();
	},
	errorPlacement : function(error, element) {
		error.insertAfter(element.parents(".col-md-5"));
	}

});

$('.partner-form input').keypress(function(e) {
	if (e.which == 13) {
		$("#save_partner_btn").click();
		return false;
	}
});

$("#save_partner_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		$('#partner-form').submit();
		if ($('#partner-form').validate().form()) {
			$('#partner-form').submit();
		}
	}
});
/**
 *联系人添加多个脚本 
 */
$(document).on('click','.btn-add', function(e) {
	e.preventDefault();

	var controlForm = $('#linkManTable');

	var rowCount = $('#linkManTable tr').length;

	if (rowCount > 10) {
		alert("最多能添加10个联系人!");
		return;
	}

	var currentEntry = controlForm.find('tr').eq(1);
	var newEntry = $(currentEntry.clone()).appendTo(controlForm);

	newEntry.find('input').val('');

	controlForm.find('tr:not(:last-child) .btn-add').removeClass(
			'btn-add').addClass('btn-remove')
			.removeClass('btn-success').addClass('btn-danger').html(
					'<span class="glyphicon glyphicon-minus"></span>');

	controlForm.find('tr:last-child .btn-remove').removeClass(
			'btn-remove').addClass('btn-add').removeClass('btn-danger')
			.addClass('btn-success').html(
					'<span class="glyphicon glyphicon-plus"></span>');
}).on('click', '.btn-remove', function(e) {
	e.preventDefault();
	$(this).parents("tr").first().remove();
	return false;
});
/**
 * 显示服务商对应的城市和地区
 */
$(function(){
	var partnerCityId = $("#partnerCityList").val();
	var parnerRegionId = $("#partnerRegionList").val();

	var a = partnerCityId.split(",");
	var b = parnerRegionId.split(",");
	$("input[name='cityId']:checkbox").each(function(){ 
		var cityId = $(this).val();
		for(var i=0;i<a.length;i++){
			if(cityId==a[i]){
				$(this).attr("checked","checked");
			}
		}
	}); 
		
	$("input[name='regionId']:checkbox").each(function(){ 
		var regionId = $(this).val();
		for(var i=0;i<b.length;i++){
			if(regionId==b[i]){
				$(this).attr("checked","checked");
			}
		}
	}); 
});