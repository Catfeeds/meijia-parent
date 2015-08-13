<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="orderStatusSelectTag" uri="/WEB-INF/tags/orderSatusSelect.tld" %>
<%@ taglib prefix="spiderPartnerStatusSelectTag" uri="/WEB-INF/tags/spiderPartnerStatusSelect.tld" %>
<%@ taglib prefix="spiderPartnerServiceTypeSelectTag" uri="/WEB-INF/tags/spiderPartnerServiceTypeSelect.tld" %>

<!--taglib for this page  -->
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld"%>
<%@ taglib prefix="spiderPartnerStatusTag" uri="/WEB-INF/tags/spiderPartnerStatus.tld"%>

<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->

</head>

<body>
	<section id="container"> <!--header start--> <%@ include
		file="../shared/pageHeader.jsp"%> <!--header end-->

	<!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper"> <!-- page start-->
		 <%@ include file="../common/partner/spiderPartnerSearch.jsp"%>
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			服务商列表
			</header>

			<hr
				style="width: 100%; color: black; height: 1px; background-color: black;" />


			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>最后采集时间</th>
						<th>公司名称</th>
						<th>服务类别</th>
						<th>认证</th>
						<th>采集网站</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td><timestampTag:timestamp patten="yyyy-MM-dd"
									t="${item.lastSpiderTime * 1000}" /></td>
							<td>${ item.companyName }</td>
							<td>${item.serviceType }</td>
							<td>${item.certification }</td>
							<td><a href="${item.spiderUrl}" target="_blank">采集网站链接</a></td>
							<td>
								<spiderPartnerStatusTag:status status="${item.status}"/>
							</td>
							<td>
								<button id="btn_update"
									onClick="btn_update('spiderPartner/spiderPartnerForm?id=${ item.spiderPartnerId }')"
									class="btn btn-primary btn-xs" title="修改">
									<i class="icon-pencil"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			</section>

			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/spiderPartner/list" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include
		file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>

	<!--script for this page-->
	<script type="text/javascript">
	function doReset(){
		$("#companyName").attr('value',' ');
	}
	</script>
</body>
</html>