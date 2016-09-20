//阿里云web播放器
//function InitPlayer(url) {
//	var player = new prismplayer({
//		id : "J_prismPlayer", // 容器id
//		source : url, // 视频url // 支持互联网可直接访问的视频地址
//		autoplay : true, // 自动播放
//		bigPlayButton : true, //大播放按钮
//		controlBar : true, //控制面板
//		width : "100%", // 播放器宽度
//		height : "400px" // 播放器高度
//	});
//}

//富编辑器
KindEditor.ready(function(K) {
	K.create("#contentStandard", {
		width : '500px',
		height : '500px',
		afterBlur : function() {
			this.sync();
		}//帮助KindEditor获得textarea的值
	});
	
});

function youkuPlayer(vid, cid) {
	// document.write('<div id="video_container_id"></div>');
	
	var containerid = "video_container_id";
	var player_params = {
		"auto_play" : false,
		"width" : 600,
		"height" : 400,
		"playerid" : '0'
	};
	var video_params = {
		"vid" : vid,
		"cid" : cid
	};
	window.ykcplayer(containerid, player_params, video_params);
	
}

$("#prew-video-btn").click(function() {
	
	var vurl = $("#videoUrl").val();
	
	var url = $.url(vurl);
	
	var vid = url.param("vid");
	var cid = url.param("cid");
	
	console.log("vid = " + vid);
	console.log("cid = " + cid);
	
	if (vid == undefined || cid == undefined) {
		alert("视频地址不是一个有效的优酷开发云播放地址.");
		return false;
	}
	
	
	youkuPlayer(vid, cid);
	
	
});

$('#partner-service-price-form').validate({
	errorElement : 'span', // default input error message container
	errorClass : 'help-block', // default input error message class
	focusInvalid : false, // do not focus the last invalid input
	rules : {
		no : {
			required : true,
			digits : true
		},
		name : {
			required : true
		},
		
		price : {
			required : true,
			number : true,
		},
		
		disPrice : {
			required : true,
			number : true,
		},
		
		contentStandard : {
			required : true
		},
	
	},
	
	messages : {
		no : {
			required : "请输入序号",
			digits : "请输入数字"
		},
		name : {
			required : "请输入标题"
		},
		
		price : {
			required : "请输入原价",
			number : "请输入正确的价格数字",
		
		},
		
		disPrice : {
			required : "请输入原价",
			number : "请输入正确的价格数字",
		},
		
		contentStandard : {
			required : "请输入服务标准"
		},
	
	},
	
	invalidHandler : function(event, validator) { // display error alert on
													// form submit
		$('.alert-error', $('#partner-service-price-form')).show();
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

$('.partner-service-price-form input').keypress(function(e) {
	if (e.which == 13) {
		$("#btn_submit").click();
		return false;
	}
});

$("#btn_submit").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#partner-service-price-form').validate().form()) {
			$('#partner-service-price-form').submit();
		}
	}
});

$("#btn_preview").click(
		function() {
			var serviceTypeId = $("#servicePriceId").val()
			// http://app.bolohr.com/simi-h5/discover/service-detail.html?service_type_id=1
			var url = "http://" + host + "/simi-h5/discover/service-detail.html?service_type_id="
					+ serviceTypeId;
			window.open(url, "_blank");
		});

$("#imgUrlFile").fileinput({
	previewFileType : "image",
	browseClass : "btn btn-success",
	browseLabel : "上传图片",
	browseIcon : '<i class="glyphicon glyphicon-picture"></i>',
	removeClass : "btn btn-danger",
	removeLabel : "删除",
	removeIcon : '<i class="glyphicon glyphicon-trash"></i>',
	allowedFileExtensions : [ "jpg", "gif", "jpeg", "png", ],
	maxFileSize : 8192,
	msgSizeTooLarge : "上传文件大小超过8mb"
});