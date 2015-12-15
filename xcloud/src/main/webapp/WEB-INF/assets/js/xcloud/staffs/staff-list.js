/*$("#staff-table").dataTable({
    "processing": true,
    "serverSide": true,
    "ordering":  false,
    "aLengthMenu" : false,
    "pageLength": 10,
    "searching": false,
    "scrollY": "300px",
    "language": {
        "lengthMenu": "",
        "paginate": {
            "previous": "前一页",
            "next": "下一页"
          },
        "zeroRecords": "没有可用的服务人员",
        "info": "显示_START_到_END_, 共有_TOTAL_条数据"
      },
    "ajax": {
        "url": "/xcloud/interface-staff/list.json",
        "type": "GET",
        "data": function ( d ) {
        	
        		var checkedItems = [];
        		$("#check-list-box li.active").each(function(idx, li) {
    	    			checkedItems.push($(li).attr('id'));
        		});        
            var partner_ids = checkedItems.join(",");
            var order_no = $("#orderNo").val();
        	
        	var dept_id = $("#dept_id");
            d.dept_id = dept_id;
        }
    },
    "columns": [
        { "data": "headImg" },
        { "data": "name" },
        { "data": "mobile" },
        { "data": "staffName" },
        { "data": "jobNumber" },
        { "data": "deptName" },
        { "data": "jobName" },

    ]
} );*/