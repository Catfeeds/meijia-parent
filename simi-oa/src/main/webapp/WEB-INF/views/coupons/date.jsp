<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<!-- <title>Conquer | Form Stuff - Form Controls</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
BEGIN PAGE LEVEL SCRIPTS -->

<script src="<c:url value='/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min1.js'/>" type="text/javascript"></script>

<script src="<c:url value='/assets/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js'/>" type="text/javascript"></script>

<!-- <link rel="shortcut icon" href="favicon.ico" /> -->
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">
				<span class="add-on">
					<i class="icon-calendar"></i>
				</span>
	<!-- BEGIN JAVASCRIPTS -->
	<script type="text/javascript">
		    $('#exptime').datetimepicker({
		        format: 'yyyy-MM-dd',
		        language: 'zh-CN',
		        pickDate: true,
		        inputMask: true,
		        autoclose: true,
	            todayBtn:'linked'

		      });
		</script>
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>