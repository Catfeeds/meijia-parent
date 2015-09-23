<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>
  <head>
	
	<!--common css for all pages-->
	<%@ include file="../shared/importCss.jsp"%>
	
	<!--css for this page-->
	<link href="<c:url value='/css/flexslider.css'/>" rel="stylesheet" type="text/css">
	<link href="<c:url value='/assets/bxslider/jquery.bxslider.css'/>" rel="stylesheet" type="text/css">
	<link href="<c:url value='/assets/fancybox/source/jquery.fancybox.css'/>" rel="stylesheet" type="text/css">
	<link href="<c:url value='/assets/revolution_slider/css/rs-style.css'/>" rel="stylesheet" type="text/css">
	<link href="<c:url value='/assets/revolution_slider/rs-plugin/css/settings.css'/>" rel="stylesheet" type="text/css">

  </head>

  <body>
	  <!--header start-->
	  <%@ include file="../shared/pageHeader.jsp"%>
	  <!--header end-->		

     

    <!--container start-->
    <div class="container">
        <div class="row">
            <!--feature start-->
            <div class="text-center feature-head">
                <h1>欢迎来到行政云平台</h1>
                <p>Professional html Template Perfect for Admin Dashboard</p>
            </div>
            <div class="col-lg-4 col-sm-4">
                <section>
                    <div class="f-box">
                        <i class=" icon-desktop"></i>
                        <h2>responsive design</h2>
                    </div>
                    <p class="f-text">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ablic jiener.</p>
                </section>
            </div>
            <div class="col-lg-4 col-sm-4">
                <section>
                    <div class="f-box active">
                        <i class=" icon-code"></i>
                        <h2>friendly code</h2>
                    </div>
                    <p class="f-text">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ablic jiener.</p>
                </section>
            </div>
            <div class="col-lg-4 col-sm-4">
                <section>
                    <div class="f-box">
                        <i class="icon-gears"></i>
                        <h2>fully customizable</h2>
                    </div>
                    <p class="f-text">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ablic jiener.</p>
                </section>
            </div>
            <!--feature end-->
        </div>
        <div class="row">
            <!--quote start-->
            <div class="quote">
                <div class="col-lg-9 col-sm-9">
                    <div class="quote-info">
                        <h1>developer friendly code</h1>
                        <p>Bundled with awesome features, UI resource unlimited colors, advanced theme options & much more!</p>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-3">
                    <a href="#" class="btn btn-danger purchase-btn">Purchase now</a>
                </div>
            </div>
            <!--quote end-->
        </div>
    </div>



    <!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
    <%@ include file="../shared/importJs.jsp"%>
    
    <!--script for this page-->


  </body>
</html>
