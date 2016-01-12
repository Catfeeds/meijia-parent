//初始化员工表格
$("#list-table").DataTable(
		{
			"processing" : true,
			"serverSide" : true,
			"ordering" : false,
			"bPaginate" : true, // Pagination True
			"aLengthMenu" : false,
			"pageLength" : 10,
			"searching" : true,

			"language" : {
				"lengthMenu" : "",
				"paginate" : {
					"previous" : "前一页",
					"next" : "下一页"
				},
				"zeroRecords" : "",
				"sEmptyTable" : "",
				"info" : "显示_START_到_END_, 共有_TOTAL_条数据"
			},
			"ajax" : {
				"url" : "/xcloud/staff/get-by-dept.json",
				"type" : "GET",
				"data" : function(d) {
					var info = $('#list-table').DataTable().page.info();
					d.page = info.page + 1;

				}
			},

			"columns" : [
					{
						"data" : "user_id",
						"render" : function(data, type, full, meta) {
							return '<input type="checkbox" onclick="setSelectTable(' + data
									+ ')" id="user_id" name="user_id" value="' + data + '"\>';
						}
					}, {
						"data" : "job_number"
					}, {
						"data" : "name"
					}, {
						"data" : "mobile"
					}, {
						"data" : "dept_name"
					}, {
						"data" : "job_name"
					}, {
						"data" : "staff_type_name"
					},

			]
		});

// 初始化选择后的效果
$('#selected_users').tagsinput({
	itemValue : 'id',
	itemText : 'label',
	allowDuplicates : false,
	trimValue : true,
	maxChars : 8
});

// 表格中checkbox 选择触发事件
function setSelectTable(userId) {
	var table = $("#list-table").DataTable();
	console.log("userId = " + userId);

	var name = "";

	table.$('input[type="checkbox"]').each(function() {

		var $row = this.closest('tr');
		var data = table.row($row).data();

		if (data.user_id == userId) {
			console.log(data.name);
			console.log(data.user_id);
			name = data.name;
			if (this.checked) {
				console.log("add");
				addSelectUser(userId, name);
				$('#selected_users').tagsinput('add', {
					id : userId,
					label : name
				});
			} else {
				console.log("remove");
				removeSelectUser(userId, name);
				$('#selected_users').tagsinput('remove', {
					id : userId,
					label : name
				});
			}

			return false;
		}
	});

	$('#selected_users').tagsinput('refresh');

}

// 表格中 checkBox 未选中的事件处理
function removeSelectTable(userId) {
	var table = $("#list-table").DataTable();
	console.log("userId = " + userId);

	var name = "";

	table.$('input[type="checkbox"]').each(function() {

		var $row = this.closest('tr');
		var data = table.row($row).data();

		if (data.user_id == userId) {
			this.checked = false;

			return false;
		}
	});

	// $('#selected_users').tagsinput('refresh');

}

// hidden selectUserIds 和 selectUserNames 处理添加的情况
function addSelectUser(userId, name) {
	console.log("addSelectUser");
	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();
	console.log("selectUserIds = " + selectUserIds);
	console.log("selectUserNames = " + selectUserNames);
	var hasExist = false;
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
			console.log(selectUserAry[i] + "---------" + userId);
			if (selectUserAry[i] == userId) {
				hasExist = true;
				break;
			}
		}
	}

	if (hasExist == false) {
		selectUserIds = selectUserIds + userId + ",";
		selectUserNames = selectUserNames + name + ",";
		$("#selectUserIds").val(selectUserIds);
		$("#selectUserNames").html(selectUserNames);
	}
	console.log($("#selectUserIds").val());
	console.log($("#selectUserNames").html());
};

// hidden selectUserIds 和 selectUserNames 处理移除的情况
function removeSelectUser(userId, name) {
	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();

	console.log("selectUserIds = " + selectUserIds);
	console.log("selectUserNames = " + selectUserNames);

	var hasExist = false;
	var newSelectUserIds = "";
	var newSelectNames = "";
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		var selectNameAry = selectUserNames.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
			if (selectUserAry[i] != userId && selectUserAry[i] != "") {
				newSelectUserIds += selectUserAry[i] + ",";
				newSelectNames += selectNameAry[i] + ",";
			}
		}
	}

	$("#selectUserIds").val(newSelectUserIds);
	$("#selectUserNames").html(newSelectNames);

	console.log($("#selectUserIds").val());
};

// tagsInput 删除元素完成后的触发事件
$('#selected_users').on('itemRemoved', function(event) {
	// event.item: contains the item
	console.log("itemRemoved");
	var item = event.item;
	var userId = item.id;
	var name = item.label;
	console.log("userId = " + userId);
	removeSelectUser(userId, name);

	removeSelectTable(userId);

});

// console.log(moment().add('hours',1).format('YYYY-MM-DD HH:00'));
var curFormDateTime = moment().add('m', 5).format('YYYY-MM-DD HH:mm');
$('.form_datetime').datetimepicker({
	format : 'yyyy-mm-dd hh:ii',
	autoclose : true,
	todayBtn : true,
	startDate : curFormDateTime,
	minuteStep : 1
});

$('#setNowSend').not('[data-switch-no-init]').bootstrapSwitch();

// 表单验证设置
// $('#card-form').validator({
// allFields: ':input',
// });

// 提交验证
$("#btn-card-submit").on('click', function(e) {

	var fv = formValidation();
	console.log("fv  =" + fv);
	
	if (fv == false) return false;
	
	
});

function formValidation() {
	var selectUserIds = $("#selectUserIds").val();

	var selectFlag = true;
	if (selectUserIds == "") {
		selectFlag = false;
		alert("请选择人员!");
	}
	
	var form = $('#card-form');

	var formValidity = $('#card-form').validator().data('amui.validator').validateForm().valid

	if (formValidity && selectFlag) {
		// done, submit form
		console.log("ok");
		//判断提醒时间点可以提醒.
		//1. 获取提醒时间
		
		//2. 获取提醒设置
		
		
		return true;
		// form.submit();
	} else {
		// fail
		console.log("fail");
		return false;
	}
	;
}
