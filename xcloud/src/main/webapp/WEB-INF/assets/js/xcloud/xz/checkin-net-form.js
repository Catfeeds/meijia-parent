var curFormDateTime = moment().format('YYYY-MM-DD');
$('#startTime').datetimepicker({
	language : 'zh-CN',
	startView : 'day',
	format : 'hh:ii',
	autoclose : true,
	todayBtn : false,
	startDate : curFormDateTime,
	minuteStep : 30,
	showButtonPanel : false,
});

$('#endTime').datetimepicker({
	language : 'zh-CN',
	startView : 'day',
	format : 'hh:ii',
	autoclose : true,
	todayBtn : false,
	startDate : curFormDateTime,
	minuteStep : 30,
	showButtonPanel : false,
});

$('#deptIds').on('chosen:ready', function(e, params) {
	
	var selectedDeptIds = $("#selectedDeptIds").val();
	
	if (selectedDeptIds == '') return false;
	console.log("selectedDeptIds = " + selectedDeptIds);
	if (selectedDeptIds.indexOf(",") < 0) {
		$("#deptIds").val(selectedDeptIds).trigger('chosen:updated');
	} else {
		$.each(selectedDeptIds.split(","), function(i, e) {
			$("#deptIds option[value='" + e + "']").prop("selected", true);
		});
		$("#deptIds").trigger('chosen:updated');
	}
});

$('#deptIds').on('change', function(e, params) {
	
	var v = $(this).val();
	
	if (v == undefined || v == '') return false;
	console.log("v = " + $(this).val());
	console.log(v.indexOf("0"));
	if (v.indexOf("0") >= 0) {
		$('#deptIds').val(0).trigger('chosen:updated');
	}
});
$('#deptIds').chosen();

// 设定部门默认值
var selectedDeptIds = $("#selectedDeptIds").val();

if (selectedDeptIds != '') {
	console.log("selectedDeptIds = " + selectedDeptIds);
	if (selectedDeptIds.indexOf(",") < 0) {
		console.log("asdfasdfasdf");
		$("#deptIds").val(selectedDeptIds);
	} else {
		$.each(selectedDeptIds.split(","), function(i, e) {
			$("#deptIds option[value='" + e + "']").prop("selected", true);
		});
	}
}

$('#statusSwitch').not('[data-switch-no-init]').bootstrapSwitch({
	size : "sm",
	onText : "有效",
	offText : "无效",
});

//初始化数据


if ($("#status").val() == 1) {
	$('#statusSwitch').bootstrapSwitch('state', true);
} else {
	$('#statusSwitch').bootstrapSwitch('state', false);
}

$('#statusSwitch').on('switchChange.bootstrapSwitch', function(event, state) {
	if (state) {
		$("#status").val(1);
	} else {
		$("#status").val(0);
	}
	
});

$("#btn-save").on(
		'click',
		function(e) {
			
			var formV = checkNetFormValidaty();
			
			if (formV == false) return false;
			
			var form = $('#checkin-net-form');
			
			var formValidity = $('#checkin-net-form').validator().data('amui.validator')
					.validateForm().valid

			console.log("deptIds = " + $("#deptIds").val());
			
			if (formValidity) {
				// done, submit form
				console.log("ok");
				form.submit();
			} else {
				// fail
				console.log("fail");
			}
			;
		});

function checkNetFormValidaty() {
	var deptIds = $("#deptIds").val();
	if (deptIds == undefined || deptIds == null || deptIds == "") {
		alert("请选择出勤部门!");
		$("#deptIds").focus();
		return false;
	}
	
	var lng = $("#lng").val();
	var lat = $("#lat").val();
	var addr = $("#addr").val();
	var city = $("#city").val();
	
	if (lng == "" || lat == "" || addr == "") {
		alert("请输入出勤地点，并且选中正确的地图位置.");
		$("#suggestId").focus();
		return false;
	}
	
	return true;
}
