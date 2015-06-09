<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.zrj.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="payTypeSelectTag" uri="/WEB-INF/tags/payTypeSelect.tld" %>
<%@ taglib prefix="citySelectTag" uri="/WEB-INF/tags/citySelect.tld" %>


<%@ include file="../shared/taglib.jsp"%>

<html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<title>Conquer | Form Stuff - Form Controls</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="<c:url value='/css/pages/login.css'/>" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />
<%@ include file="../shared/importCss.jsp"%>
<%@ include file="../shared/importJs.jsp"%>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script type="text/javascript" src="<c:url value='/js/app.js'/>"></script>

<!-- END PAGE LEVEL SCRIPTS -->

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">

	<%@ include file="../shared/pageHeader.jsp"%>

	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">

		<%@ include file="../shared/sidebarMenu.jsp"%>

		<!-- BEGIN PAGE -->
		<div class="page-content">

			<%@ include file="../shared/pageContentHeader.jsp"%>
			<div class="portlet ">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-edit"></i>新增用户
					</div>
				</div>
				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<%@ include file="../account/registerFile.jsp"%>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->
	<%@ include file="../shared/pageFooter.jsp"%>
	<script type="text/javascript">
		 $(function() {
			 App.init();
			 AccountValidate.handleRegister();

		});
	</script>
	<script src="<c:url value='/plugins/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/jquery.chained.remote.min.js'/>" type="text/javascript" ></script>
    <script src="<c:url value='/js/jquery.chained.remote.js'/>" type="text/javascript" ></script>
    <%-- <script src="<c:url value='/js/onecare/staff/addPartnerForm.js'/>" type="text/javascript"></script> --%>
	<script src="<c:url value='/plugins/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/account.validate.js'/>" type="text/javascript"></script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>