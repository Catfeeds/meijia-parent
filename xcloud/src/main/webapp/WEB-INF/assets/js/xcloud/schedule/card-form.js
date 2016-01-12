
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
							return '<input type="checkbox" onclick="setSelectTable('
									+ data
									+ ')" id="user_id" name="user_id" value="'
									+ data + '"\>';
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

//初始化选择后的效果
$('#selected_users').tagsinput({
	  itemValue: 'id',
	  itemText: 'label',
	  allowDuplicates: false,
	  trimValue: true,
	  maxChars: 8
});

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
				addSelectUser(userId);
				$('#selected_users').tagsinput('add', {id:userId, label:name});
			} else {
				console.log("remove");
				removeSelectUser(userId);
				$('#selected_users').tagsinput('remove', {id:userId, label:name});
			}
			
			return false;
		}		
	});


	$('#selected_users').tagsinput('refresh');

}

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


//	$('#selected_users').tagsinput('refresh');

}

function addSelectUser(userId) {
	console.log("addSelectUser");
	var selectUserIds = $("#selectUserIds").val();
	console.log(selectUserIds);
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
		$("#selectUserIds").val(selectUserIds);
	}
	console.log($("#selectUserIds").val());
};

function removeSelectUser(userId) {
	var selectUserIds = $("#selectUserIds").val();
	var hasExist = false;
	var newSelectUserIds = "";
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
	        if (selectUserAry[i] != userId && selectUserAry[i] != "") {
	        	newSelectUserIds+= selectUserAry[i] + ",";
	        }
	    }
	}
	
	$("#selectUserIds").val(newSelectUserIds);
	
	console.log($("#selectUserIds").val());
};

$('#selected_users').on('itemRemoved', function(event) {
	  // event.item: contains the item
	console.log("itemRemoved");
	var item = event.item;
	var userId = item.id;
	console.log("userId = " + userId);
	removeSelectUser(userId);
	
	removeSelectTable(userId);
	
});


