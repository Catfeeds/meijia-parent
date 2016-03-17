
//提交验证
$("#btn-personal-submit").on('click', function(e) {

    var form = $('#personal-form');
	
	var formValidity = $('#personal-form').validator().data('amui.validator').validateForm().valid
	
	if (formValidity) {
		
		 // 开始上传文件时显示一个图片
	     $("#wait_loading").ajaxStart(function() {
	         $(this).show();
	     // 文件上传完成将图片隐藏起来
	     }).ajaxComplete(function() {
	         $(this).hide();
	     });
	     
		// 组建提交卡片接口数据
		var params = {}
		params.user_id = $("#userId").val();
		params.sex = $('input:radio[name=sex]:checked').val();
		params.name = $("#name").val();
		
		$.ajax({
			type : "POST",
			url : appRootUrl + "user/post_userinfo.json", // 发送给服务器的url
			data : params,
		//	fileElementId: 'imgUrlFile', // 上传文件的id、name属性名
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "999") {
					alert(data.msg);
					return false;
				}

				if (data.status == 0) {
					//location.href = "/xcloud/index";
					var params = {}
					params.user_id = $("#userId").val();
					alert("111111111111");
					$.ajaxFileUpload({
						type : "POST",
						url : appRootUrl + "user/post_user_head_img.json", // 发送给服务器的url
						data : params,
						secureuri: false, //一般设置为false
						fileElementId: 'file', // 上传文件的id、name属性名
						dataType : "json",
						async : false,
						success : function(data) {
							if (data.status == "999") {
								alert(data.msg);
								return false;
							}

							if (data.status == 0) {
								location.href = "/xcloud/index";
							}
						}
					})
					
					
					//////////////////////////////////////////////
				}
			}
		})
		
	} else  {
		// fail
		console.log("fail");
	};
});
