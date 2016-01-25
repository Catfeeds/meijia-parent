//表格全选
$("#select_all").click(function() {
	$('.select-cell').prop('checked', this.checked);
});

// 分配部门
$("#btn-change-dept").click(function() {
	console.log("btn-change-dept click");

	var selectStaffNames = "";
	var selectStaffIds = "";
	$('#list-table > tbody > tr').each(function() {
		var tds = $(this).find("td");

		var selectStaffId = $(tds[0]).find(".select-cell");
		if (selectStaffId.prop('checked') == true) {
			// console.log(selectStaffId.val());
			// console.log($(tds[1]).find(".name-cell").html());

			selectStaffNames += $(tds[1]).find(".name-cell").html() + ",";
			selectStaffIds += selectStaffId.val() + ","
		}

	});
	if (selectStaffIds == "") {
		alert("请选择需要分配部门的员工.");
		return false;
	}
	$("#select_staff_names").html(selectStaffNames);
	$("#select_staff_ids").val(selectStaffIds);
	$('#change-dept-modal').modal();
});

// 弹出框体form表单提交处理
$('#change-dept-modal').on('close.modal.amui', function(e) {

	var selectStaffIds = $('#select_staff_ids').val();
	var selectDeptId = $('#dept-id-select').val();
	var companyId = $("#companyId").val();
	var params = {};
	params.company_id = companyId;
	params.select_staff_ids = selectStaffIds;
	params.select_dept_id = selectDeptId;

	$.ajax({
		type : "post",
		url : "/xcloud/staff/change-dept.json",
		data : params,
		dataType : "json",
		async : false,
		success : function(rdata, textStatus) {
			if (rdata.status == "999") {
				alert(rdata.msg);
				return true;
			}

			if (rdata.status == "0") {
				location.reload();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		},

	});
	return true;
});

//添加员工
$("#btn-staff-add").click(function() {
	location.href = "/xcloud/staff/staff-form?staff_id=0";
});

//删除员工
function staffDel(staffId) {
	 if(confirm("确定要办理员工离职吗")){
		 $.ajax({
		       type : "post",
		       url : "/xcloud/staff/del.json?staff_id="+staffId,
//		       data : params,
		       dataType : "json",
		       async : false,
		       success : function(rdata, textStatus) {
		          if (rdata.status == "999") {
		       	   		alert(rdata.msg);
		       	   		return true;
		          }
		          
		          if (rdata.status == "0") {
		        	  location.reload();
		          }
		       },
		       error : function(XMLHttpRequest, textStatus, errorThrown) {
		           
		       },
		       
		   });   
	 }
}

//批量导入员工
$("#btn-staff-import").click(function() {
	location.href = "/xcloud/staff/staff-import";
});
//导出通讯录
$("#btn-staff-export").click(function() {
	var companyId = $("#companyId").val();
	location.href = "/xcloud/staff/staff-download?companyId="+companyId;
});