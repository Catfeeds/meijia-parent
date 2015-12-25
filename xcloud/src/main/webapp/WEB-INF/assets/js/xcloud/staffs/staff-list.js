var tables = $("#list-table").dataTable({
    "processing": true,
    "serverSide": true,
    "ordering":  false,
    "bPaginate": true, // Pagination True 
    "aLengthMenu" : false,
    "pageLength": 1,
    "searching": false,
    "scrollY": "300px",
    "language": {
        "lengthMenu": "",
        "paginate": {
            "previous": "前一页",
            "next": "下一页"
          },
        "zeroRecords": "没有员工",
        "info": "显示_START_到_END_, 共有_TOTAL_条数据"
      },
    "ajax": {
        "url": "/xcloud/staff/get-by-dept.json",
        "type": "GET",
        "data": function ( d ) {
        	var info = $('#list-table').DataTable().page.info();
        	console.log(info.page);
        	d.page = info.page + 1;

        	
//        	var dept_id = $("#dept_id");
//            d.dept_id = dept_id;
        }
    },
    
    
    "columns": [
        { "data": "job_number" },
        { "data": "name" },
        { "data": "mobile" },
        { "data": "job_name" },
        { "data": "dept_name" },
        { "data": "staff_type_name" },
        { "data": "id",
          "render": function ( data, type, full, meta ) {
        	  	  console.log(data);
        	      return '<a href="/xcloud/staff/'+data+'"><i class="am-icon-edit am-icon-md"></i></a>';
          }
        },

    ]
} );