
//添加 新的 入库记录
$("#btn-job-add").click(function() {
	
	location.href = "/xcloud/job/job_form?id=0";
});

function getJobDetail(id){
	
	location.href = "/xcloud/job/job_form?id="+id;
}