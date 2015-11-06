$('#apply-form').validate({
	
});

$("#applyForm_btn").click(function() {
	if (confirm("确认要审批吗?")) {
		$('#apply-form').submit();
		if ($('#apply-form').validate().form()) {
			$('#apply-form').submit();
		}
	}
});
//处理标签选中的样式.
$("input[name='tagName']").click(function() {
	
	var tagId  = $(this).attr("id");

	if($(this).attr("class")=="btn btn-default"){
		$(this).attr("class","btn btn-success");
		
	}else{
		$(this).attr("class","btn btn-success");
		$(this).attr("class","btn btn-default");
	}
	
	setTagIds();

});


function setTagIds() {
	var tagIds = $("#tagIds");
	var tagSelected = "";
	//处理选择按钮的情况
	$("input[name=tagName]").each(function(key, index) {
		console.log($(this));
		if ($(this).attr("class") == "btn btn-success") {
			tagSelected = tagSelected + $(this).attr("id") + ",";
		}
	});	

	if (tagSelected != "") {
		tagSelected = tagSelected.substring(0, tagSelected.length - 1);
	}

	tagIds.val(tagSelected);
}


function setTagButton() {
	var tagIds = $("#tagIds");
	if (tagIds.val() == "") return false;
	//处理选择按钮的情况
	$("input[name=tagName]").each(function(key, index) {
		var tagId = $(this).attr("id");
		if (tagIds.val().indexOf(tagId + ",") >= 0) {
			$(this).attr("class","btn btn-success");
		}
	});	
}
$("#headImg").fileinput({
	previewFileType: "image",
	browseClass: "btn btn-success",
	browseLabel: "上传图片",
	browseIcon: '<i class="glyphicon glyphicon-picture"></i>',
	removeClass: "btn btn-danger",
	removeLabel: "删除",
	removeIcon: '<i class="glyphicon glyphicon-trash"></i>',
	allowedFileExtensions: ["jpg", "gif", "jpeg","png",],
	maxFileSize: 8192,
	msgSizeTooLarge: "上传文件大小超过8mb"
});