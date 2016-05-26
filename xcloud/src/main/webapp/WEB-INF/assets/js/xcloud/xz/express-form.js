//快递单号查询事件
$("#expressNo").keyup(function(){ 
	 var v = $(this).val();
	 console.log(v);
	 
	 if (v.length >= 6) {
		 var params = {};
		 params.express_no = v;
		 $.ajax({
				type : "GET",
				url : appRootUrl + "/record/get_express_name.json", // 发送给服务器的url
				data : params,
				dataType : "json",
				async : false,
				success : function(data) {
					console.log(data);
					var eId = data.data;
					if (eId != "") {
						$("#expressId").val(eId);
					}
					
				}
			})
		 
	 }
});

//收发件的事件
$("input[name='expressType']").change(function(){
	console.log("收发件 = " + $("input[name='expressType']:checked").val());
	var v = $("input[name='expressType']:checked").val();
	
	//收件，则隐藏掉付费方式，价格和是否结算，并且价格设置为非必选
	if (v == "0") {
		$("#payTypeDiv").css("display", "none");
		$("#priceDiv").css("display", "none");
		$("#isCloseDiv").css("display", "none");
	}
	
	//寄件，则打开付费方式，价格和是否结算，并且价格设置为必选
	if (v == "1") {
		$("#payTypeDiv").css("display", "block");
		$("#priceDiv").css("display", "block");
		$("#isCloseDiv").css("display", "block");
	}
});


//提交验证
$("#btn-express-submit").on('click', function(e) {

	/*var fv = formValidation();
	if (fv == false) return false;
	*/
    var form = $('#express-form');
	
	var formValidity = $('#express-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		// done, submit form
		//console.log("ok");
		//form.submit();
		// 组建提交卡片接口数据
		var params = {}
		params.id = $("#id").val();
		params.company_id = $("#companyId").val();
		params.user_id = $("#userId").val();
		params.express_no = $("#expressNo").val();
		params.express_type = $('input:radio[name=expressType]:checked').val();
		params.pay_type = $('input:radio[name=payType]:checked').val();
		params.is_close = $('input:radio[name=isClose]:checked').val();
		params.express_id = $("#expressId").val();
		params.price = $("#price").val();
		params.from_addr = $("#fromAddr").val();
		params.from_name = $("#fromName").val();
		params.from_tel = $("#fromTel").val();
		params.to_addr = $("#toAddr").val();
		params.to_name = $("#toName").val();
		params.to_tel = $("#toTel").val();
		params.remarks = $("#remarks").val();
		
		$.ajax({
			type : "POST",
			url : appRootUrl + "/record/post_add_express.json", // 发送给服务器的url
			data : params,
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					location.href = "/xcloud/xz/express/list";
				}
			}
		})
	} else  {
		// fail
		console.log("fail");
	};
});
