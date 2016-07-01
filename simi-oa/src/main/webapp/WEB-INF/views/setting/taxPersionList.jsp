<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<!--taglib for this page  -->
<html>
<head>
<title>个人所得税表</title>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
</head>
<body>
	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->
	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->
	<%-- <%@ include file="../common/partner/partnersSearch.jsp"%> --%>
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			<h4>数据搜索</h4>
			<form:form modelAttribute="searchModel" action="list" method="GET" class="form-inline">
				<div class="form-group">
					收入类型:
					<form:select class="form-control" path="settingType">
						<form:option value="tax_persion" label="工资、薪金所得" />
						<form:option value="tax_year_award" label="年终奖所得" />
						<form:option value="tax_pay" label="劳动报酬" />
					</form:select>
				</div>
				<button type="submit" class="btn btn-primary">搜索</button>
			</form:form> 
			</header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<header class="panel-heading"> 税率表</font>
			<div class="pull-right">
				<button onClick="btn_add('taxPersion/form?id=0')" class="btn btn-primary" type="button">
					<i class="icon-expand-alt"></i>
					新增
				</button>
			</div>
			</header>
			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>级别</th>
						<th>应纳税所得额(含税)</th>
						
						<th>税率(%)</th>
						<th>速算扣除数</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel}" var="item">
						<tr>
							<td>${ item.settingValueVo.level }</td>
							<td>
								<c:if test="${ item.settingValueVo.taxMin == '' }">
									不超过${ item.settingValueVo.taxMax }元的部分
								</c:if>
								
								<c:if test="${ item.settingValueVo.taxMax == '' }">
									超过${ item.settingValueVo.taxMin }元的部分
								</c:if>
								
								<c:if test="${ item.settingValueVo.taxMin != '' && item.settingValueVo.taxMax != '' }">
									超过${ item.settingValueVo.taxMin }元至${ item.settingValueVo.taxMax }元的部分
								</c:if>
							</td>
							
							<td>${ item.settingValueVo.taxRio }%</td>
							<td>${ item.settingValueVo.taxSs }%</td>
							<td>
								<button id="btn_update" onclick="btn_update('taxPersion/form?id=${item.id}')" class="btn btn-primary btn-xs"
									title="修改">
									<i class="icon-pencil"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</section>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script type="text/javascript" src="<c:url value='/js/simi/setting/taxList.js' />"></script>
	
</body>
</html>