<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="citySelectTag" uri="/WEB-INF/tags/citySelect.tld"%>
<!--taglib for this page  -->
<html>
<head>
<title>社保公积金参数</title>
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
					选择城市:
<%-- 					<form:select class="form-control" path="cityId">
						<option value="">请选择城市</option>
						<form:option value="2" label="北京" />
						<form:option value="74" label="上海" />
						<form:option value="198" label="广州" />
						<form:option value="200" label="深圳" />
					</form:select> --%>
					<citySelectTag:select selectId="${searchModel.cityId }"/>
				</div>
				<%-- <div class="form-group">
					选择地区:
					<form:select class="form-control" path="regionId">
						<option value="">请选择地区</option>
					</form:select>
					<input type="hidden" id="returnRegionId" value="${searchModel.regionId }">
				</div> --%>
				<button type="submit" class="btn btn-primary">搜索</button>
			</form:form> </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<header class="panel-heading"> 社保公积金参数列表<font color="red">(还有${totalNeed }个区县未配置)</font>
			<div class="pull-right">
				<button onClick="btn_add('insurance/form?id=0')" class="btn btn-primary" type="button">
					<i class="icon-expand-alt"></i>
					新增
				</button>
			</div>
			</header>
			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>城市</th>
						<!-- <th>区/县</th> -->
						<th>配置项</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.settingValueVo.cityName }</td>
							<td>养老(个人):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.pensionP }%&nbsp;&nbsp;&nbsp;&nbsp;
								养老(单位):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.pensionC }%<br>
								医疗(个人):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.medicalP }%&nbsp;&nbsp;&nbsp;&nbsp;
								医疗(单位):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.medicalC }%<br>
								失业(个人):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.unemploymentP }%&nbsp;&nbsp;&nbsp;&nbsp;
								失业(单位):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.unemploymentC }%<br>
								工伤(个人):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.injuryP }%&nbsp;&nbsp;&nbsp;&nbsp;
								工伤(单位):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.injuryC }%<br>
								生育(个人):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.birthP }%&nbsp;&nbsp;&nbsp;&nbsp;
								生育(单位):&nbsp;&nbsp;&nbsp;&nbsp;${item.settingValueVo.birthC }%<br> 公积金(个人):${item.settingValueVo.fundP }%&nbsp;&nbsp;
								公积金(单位):${item.settingValueVo.fundC }%
							</td>
							<td>
								<button id="btn_update" onclick="btn_update('insurance/form?id=${item.id}')" class="btn btn-primary btn-xs"
									title="修改">
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
				<c:param name="urlAddress" value="/insurance/list" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script type="text/javascript" src="<c:url value='/js/simi/setting/insuranceList.js' />"></script>
</body>
</html>