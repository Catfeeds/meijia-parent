var partnerUserId = getUrlParam("partner_user_id");
var orderId = getUrlParam("order_id")

$("#partner_user_id").val(partnerUserId);
$("#order_id").val(orderId);
// 获取服务大类
$.ajax({
	type : "GET",
	url : appRootUrl + "order/get_partner_detail.json?partner_user_id=" + partnerUserId
			+ "&order_id=" + orderId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var order = data.data;
		$("#userHeadImg").attr("src", order.head_img);
		$("#partnerUserName").html(order.partner_user_name);
		$("#orderStatusName").html(order.order_status_name);

		$("#orderMoney").html(order.order_money);
		$("#serviceTypeName").html(order.service_type_name);
		$("#orderStatusNameLast").html(order.order_status_name);
		$("#addTimeStr").html(order.add_time_str);
		$("#addrName").html(order.addr_name);

		$("#name").html(order.name);
		$("#nameLast").html(order.name);
		$("#mobile").html(order.mobile);

		$("#service_price_name").html(order.service_price_name);

		$("#remarks").html(order.remarks);

		$("#orderMoneyLast").html(order.order_money);
		$("#userCouponValue").html(order.user_coupon_value);
		$("#orderPay").html(order.order_pay);

	}
});

// 读取订单进程
$.ajax({
	type : "GET",
	url : appRootUrl + "order/get_log.json?user_id=" + partnerUserId + "&order_id=" + orderId,
	dataType : "json",
	cache : true,
	async : false,
	success : function(data) {

		if (data.status == "999") return false;
		var orderlogs = data.data;

		var liHtml = "";
		for (var i = 0; i < orderlogs.length; i++) {
			var d = orderlogs[i];
			var c = d.add_time_str + ":" + d.remarks;
			liHtml += "<li>" + c + "</li>";
		}
		$("#order_process_list").html(liHtml);
		genLineHeight();

	}
});

$("#toSell_btn").on('click', function(e) {
	var mobile = $("#mobile").html();
	location.href = 'tel:' + mobile;
});

// 提交按钮事件
$("#process_btn").on('click', function(e) {
	if ($('#order-process-form').data('amui.validator').isFormValid()) {
		console.log("ok");
		var params = {};
		params.partner_user_id = $("#partner_user_id").val();
		params.order_id = $("#order_id").val();
		params.remarks = $("#process").val();

		$.ajax({
			type : "POST",
			url : appRootUrl + "order/parnter_order_process.json",
			dataType : "json",
			data : params,
			cache : true,
			async : false,
			success : function(data) {

				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				var d = data.data;
				var c = d.add_time_str + ":" + d.remarks;
				var lihtml = "<li>" + c + "</li>";
				$("#order_process_list").prepend(lihtml);
				genLineHeight();
				$("#process").val("");
			}
		});
	}
});

$(".dropzone")
		.dropzone(
				{
					url : 'import?ti=' + new Date(),// 上传地址
					paramName : "file",
					maxFilesize : 20.0, // MB
					maxFiles : 5,// 一次性上传的文件数量上限
					acceptedFiles : ".xls",
					addRemoveLinks : true,// 添加移除文件
					autoProcessQueue : false,// 不自动上传
					dictCancelUploadConfirmation : '你确定要取消上传吗？',
					dictMaxFilesExceeded : "您一次最多只能上传{{maxFiles}}个文件",
					dictFileTooBig : "文件过大({{filesize}}MB). 上传文件最大支持: {{maxFilesize}}MB.",
					dictDefaultMessage : '<span class="bigger-150 bolder"><i class="icon-caret-right red"></i> 拖动文件至该处</span><span class="smaller-80 grey">(或点击此处)</span> <br /> /<i class="upload-icon icon-cloud-upload blue icon-3x"></i>',
					dictResponseError : '文件上传失败!',
					dictInvalidFileType : "你不能上传该类型文件,文件类型只能是*.xls。",
					dictCancelUpload : "取消上传",
					dictCancelUploadConfirmation : "你确定要取消上传吗?",
					dictRemoveFile : "移除文件",
					uploadMultiple : false,
					// change the previewTemplate to use Bootstrap progress bars
					previewTemplate : "<div class=\"dz-preview dz-file-preview\"><div class=\"dz-details\"><div class=\"dz-filename\"><span data-dz-name></span></div><div class=\"dz-size\" data-dz-size></div><img data-dz-thumbnail /></div>/<div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>/n  <div class=\"dz-success-mark\"><span></span></div><div class=\"dz-error-mark\"><span></span></div>/n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>/n</div>",
					init : function() {
						var submitButton = document.querySelector("#sureSubmit");
						myDropzone = this; // closure
						// 为上传按钮添加点击事件
						submitButton.addEventListener("click", function() {
							$('#subModel').modal('hide');
							// 手动上传
							myDropzone.processQueue();
						});
						// 添加了文件的事件
						this.on("addedfile", function() {
							$('#subModel').modal().css({
								'margin-top' : function() {
									return (document.body.scrollHeight / 2.5);
								}
							});
							$('#subModel').modal('show');
						});
						this.on("success", function(file, data) {
							if (data == "upErr") {
								alertMsg("上传失败！");
							} else {
								alertMsg(data);
							}
						});
						this.on("error", function(file) {
							alert("文件上传失败！");
						});
					},

				});
