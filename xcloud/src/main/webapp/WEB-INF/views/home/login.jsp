<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<link href="<c:url value='/css/xcloud/login.css'/>" rel="stylesheet" />
</head>

<body class="login-page">


<div class="ch-container">
    <div class="row">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>云行政</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
			<div class="alert alert-info">
                没有账号，请点击这里注册.
            </div>
            <form:form modelAttribute="contentModel" class="form-horizontal" method="post">
                <fieldset>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                   		<form:input path="username" class="form-control placeholder-no-fix" autocomplete="off" placeholder="手机号/邮箱"/>
                    </div>
                    <div class="clearfix"></div><br>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                
                        <form:password path="password" class="form-control placeholder-no-fix" autocomplete="off" placeholder="密码(8-20位)"/>
			
                    </div>
                    <div class="clearfix"></div>

                    <div class="row">
                    
                    	<p class="col-md-9 col-md-12 col-xs-12 copyright">
                    		
                    	</p>

        				<p class="col-md-3 col-sm-3 col-xs-12 powered-by">
        					<a href="#">忘记密码</a>
        				</p>
                    </div>
                    <div class="clearfix"></div>

                    <p class="center col-md-5">
                        <button type="submit" class="btn btn-primary">登陆</button>
                    </p>
                </fieldset>
            </form:form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div><!--/.fluid-container-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->

</body>
</html>
