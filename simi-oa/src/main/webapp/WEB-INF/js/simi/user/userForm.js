
$('.user-form input').keypress(function (e) {
	if (e.which == 13) {
		$("#userForm_btn").click();
		return false;
	}
});

$("#userForm_btn").click(function() {
	if (confirm("确认要保存吗?")) {
		if ($('#user-form').validate().form()) {
			$('#user-form').submit();
		}
	}
});

$('#groupSelect option').each(function(){
	var selectedGroup = "," +  $("#groupSelected").val() + ",";
	var v = "," +  $(this).val() + ",";
	if (selectedGroup.indexOf(v) >= 0) {
		$(this).attr('selected', true);
     }
});

$("#groupSelect").multiSelect({   
	keepOrder: true,
	selectableHeader: "<div class='custom-header'>可选</div>",
	selectionHeader: "<div class='custom-header'>已选</div>",
});
