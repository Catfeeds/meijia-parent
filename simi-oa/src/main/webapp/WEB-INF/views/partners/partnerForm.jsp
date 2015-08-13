<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="provinceSelectTag" 	uri="/WEB-INF/tags/provinceSelect.tld"%>
<%@ taglib prefix="citySelectTag" 	uri="/WEB-INF/tags/citySelect.tld"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link href="<c:url value='/assets/jquery-multi-select/css/multi-select.css'/>"
	rel="stylesheet" type="text/css" />
<%@ include file="../shared/importJs.jsp"%>
</head>

<body>

	<section id="container"> <!--header start--> <%@ include
		file="../shared/pageHeader.jsp"%> <!--header end-->

	<!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel">
			<div class="panel-body">
				<form:form modelAttribute="partners" commandName="partners"
					class="form-horizontal" method="POST" action="partnerForm"
					id="partner-form">
					<div class="form-body">
						<input type="hidden" name="spiderPartnerId" value="${spiderPartner.spiderPartnerId }" />
						<form:hidden path="partnerId" />
						<form:hidden path="partnerTypeIds" id="partnerTypeIds" />
						<input type="hidden" id="regionIdStr" name="regionIdStr">
						<input type="hidden" id="cityIdStr" name="cityIdStr">
						<input type="hidden" id="partnerCityList" value="${partners.partnerCityId}">
						<input type="hidden" id="partnerRegionList" value="${partners.regionIds}"/>
						<h4>企业基本信息</h4>
						<hr/>
						<div class="form-group">
							<label class="col-md-2 control-label">公司名称&nbsp;*</label>
							<div class=col-md-5>${spiderPartner.companyName }</div>
						</div>
						<div class="form-group">
							<!-- Text input-->
							<label class="col-md-2 control-label">公司简称</label>
							<div class=col-md-5>
								<form:input path="shortName" class="form-control"
									placeholder="公司简称" maxSize="10" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">注册时间 </label>
							<div class=col-md-5>${spiderPartner.registerTime }</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">详细地址&nbsp;*</label>
							<div class="col-md-5">
							<form:input path="addr" class="form-control"
									value="${spiderPartner.addr }" placeholder="详细介绍" /></div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">服务区域 &nbsp;*</label>
							<div class="col-md-5">
							<input type="button" value="选择服务区域"  class="btn btn-primary " data-toggle="modal"
									data-target="#region">
								采集：&nbsp;${spiderPartner.serviceArea }
								<br/>
								新增：
								<div class="form-group" id="cityAndRegion" >
								</div>
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">服务类别&nbsp;*</label>
							<div class="col-md-5">
							<input type="button" value="选择服务类别" id="partnerType" class="btn btn-primary " data-toggle="modal"
								data-target="#myModal">
							采集：&nbsp;${spiderPartner.serviceType }&nbsp;&nbsp;&nbsp;&nbsp;新增：
							<c:forEach items="${bigServiceTypeName}" var="item">
								&nbsp;${item}&nbsp;
							</c:forEach>
							<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">服务小类</label>
							<div class="col-md-5">
							<%-- <c:if test=${not empty $spiderPartner.serviceTypeSub }> --%>
								采集：&nbsp;${spiderPartner.serviceTypeSub }
							<%-- </c:if> --%>
								&nbsp;&nbsp;&nbsp;&nbsp;新增：
								<c:forEach items="${subServiceTypeName}" var="item">
									&nbsp;${item}&nbsp;
								</c:forEach>
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">公司规模 </label>
							<div class=col-md-8>
									<div class="row">
									<div class="col-md-2" align="center">
										<label class="radio"> <form:radiobutton path="companySize"
												value="0" />未知
										</label>
									</div>
									<div class="col-md-2" align="center">
										<label class="radio"> <form:radiobutton path="companySize"
												value="1" />1~10人
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="companySize"
												value="2" />11~20人
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="companySize"
												value="3" />20~50人
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="companySize"
												value="4" />50~100人
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="companySize"
												value="5" />100以上
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<!-- Text input-->
							<label class="col-md-2 control-label">是否上门</label>
							<div class="col-md-8">
								<div class="row">
									<div class="col-md-2" align="center">
										<label class="radio"> <form:radiobutton path="isDoor"
												value="0" />未知
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isDoor"
												value="1" />不上门
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isDoor"
												value="2" />上门
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">关键词</label>
							<div class="col-md-5">
								<form:input path="keywords" class="form-control"
									placeholder="关键词" maxSize="10" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">公司简介</label>
							<div class="col-md-7">
								${spiderPartner.companyDesc }
								${spiderPartner.companyDescImg }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">业务介绍</label>
							<div class="col-md-5">
								<form:textarea path="businessDesc" class="form-control"
									placeholder="业务介绍" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">认证&nbsp;*</label>
							<div class="col-md-5">${spiderPartner.certification }</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">信用档案url</label>
							<div class="col-md-5">
								<a href="${spiderPartner.creditFileUrl }"target="_blank" >信用档案链接</a>
								<p class="help-block"></p>
							</div>
						</div>
						<h4>联系信息</h4>
						<hr/>
						<div class="form-group">
							<label class="col-md-2 control-label">企业网站</label>
							<div class="col-md-5">
								<a href="${spiderPartner.website }" target="_blank">企业网站链接</a>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">微信号</label>
							<div class="col-md-5">
								<form:input path="weixin" class="form-control" placeholder="微信号" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">QQ</label>
							<div class="col-md-5">
								<form:input path="qq" class="form-control" placeholder="QQ" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">邮箱</label>
							<div class="col-md-5">
								<form:input path="email" class="form-control" placeholder="邮箱" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">传真</label>
							<div class="col-md-5">
								<form:input path="fax" class="form-control" placeholder="传真" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">联系人</label>
							<div class="col-md-5">
								${spiderPartner.linkMan }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">联系电话&nbsp;*</label>
							<div class="col-md-5">
								${spiderPartner.linkTel }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group ">
							<label class="col-md-2 control-label">联系人信息&nbsp;*</label>
							<div class="col-md-8">

								<table id="linkManTable"
									class="table table-hover table-condensed controls">
									<thead>
										<tr>
											<th>联系人</th>
											<th>手机号</th>
											<th>联系电话</th>
											<th>职务</th>
											<th>#</th>
										</tr>
									</thead>
									<c:forEach items="${partners.linkMan}" var="item">
										<tr class="odd gradeX">
											<td><input type="text" name="linkMan"
												value="${item.linkMan}" maxLength="32" class="form-control"></td>
											<td><input type="text" name="linkMobile"
												value="${item.linkMobile}" maxLength="32"
												class="form-control"></td>
											<td><input type="text" name="linkTel"
												value="${item.linkTel}" maxLength="32" class="form-control"></td>
											<td><input type="text" name="linkJob"
												value="${item.linkJob}" maxLength="32" class="form-control"></td>

											<td><span class="input-group-btn">
													<button class="btn btn-success btn-add" type="button">
														<span class="glyphicon glyphicon-plus"></span>
													</button>
											</span></td>
										</tr>
									</c:forEach>

								</table>
							</div>
						</div>
						<h4>运营数据</h4>
						<hr/>
						<div class="form-group">
							<label class="col-md-2 control-label">合作方式</label>
							<div class="col-md-8">
								<div class="row">
									<div class="col-md-2" align="center">
										<label class="radio"> <form:radiobutton path="isCooperate"
												value="0" />未合作
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isCooperate"
												value="1" />洽谈中
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isCooperate"
												value="2" />已合作
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isCooperate"
												value="3" />优先合作
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isCooperate"
												value="4" />结束合作
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="isCooperate"
												value="5" />黑名单
										</label>
									</div>
								</div>
							</div>
							<p class="help-block"></p>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">支付方式</label>
							<div class="col-md-8">
								<div class="row">
									<div class="col-md-2" align="center">
										<label class="radio"> <form:radiobutton path="payType"
												value="0" />无
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="payType"
												value="1" />月结
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="payType"
												value="2" />按次结算
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="payType"
												value="3" />预付
										</label>
									</div>
								</div>
							</div>
							<p class="help-block"></p>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">折扣</label>
							<div class="col-md-5">
								<form:input path="discout" class="form-control" placeholder="折扣" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">浏览次数</label>
							<div class="col-md-5">
								${spiderPartner.totalBrowse }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">预约总数</label>
							<div class="col-md-5">
								${spiderPartner.totalOrder }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">累计评价</label>
							<div class="col-md-5">
								${spiderPartner.totalRate }
								<p class="help-block"></p>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<label class="col-md-2 control-label">状态&nbsp;*</label>
							<div class="col-md-8">
								<div class="row">
									<div class="col-md-2" align="center">
										<label class="radio"> <form:radiobutton path="status"
												value="0" />已采集
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="status"
												value="1" />考察中
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="status"
												value="2" />已考察
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="status"
												value="3" />待认证
										</label>
									</div>
									<div class="col-md-2" align="left">
										<label class="radio"> <form:radiobutton path="status"
												value="4" />已认证
										</label>
									</div>
								</div>
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">采集内容链接</label>
							<div class="col-md-8">
								<a href="${spiderPartner.spiderUrl}"  target="_blank">采集内容链接</a>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<div class="row">
							<div class="col-md-4" align="right">
								<button class="btn btn-success" id="save_partner_btn"
									type="button">保存</button>
							</div>
							<!-- Button -->
							<div class="col-md-8">
								<button class="btn btn-success" type="reset">重置</button>
							</div>

						</div>
					</div>
					<!-- </fieldset> -->
				</form:form>
			</div>
			</section>
		</div>
	</div>
<!-- 服务地区子窗口START -->
	<div class="modal fade"  id="region" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel1">服务地区选择</h4>
				</div>
				<div class="modal-body" style="overflow:scroll; width:590px; height:400px;”>
				<div class="row">
				<div class="col-lg-12">
					<form:form>
					<div class="form-body">
						
					<c:forEach items="${dictCityList}" var="item">
						 <div class="form-group">
						 		<div class="col-md-4" >
									<input name="cityId" type="checkbox" value="${item.cityId}">${item.name}
									<input type="hidden" value="${item.name }"/>
								</div>
								<div class="col-md-8 ">
											&nbsp;&nbsp;&nbsp;&nbsp;
									<c:forEach items="${dictReigionList}" var="items">
										<c:if test="${item.cityId ==items.cityId }">
											<input type="hidden" value="${items.name}"/>
											<input name ="regionId" type="checkbox" value="${items.regionId}">${items.name}
										<input type="hidden" value="${items.cityId}"/>
									</c:if>
									</c:forEach>
								</div>
						</div>
						<hr />
					</c:forEach>
				</div>
				</form:form>
				</div>
				</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary  region-confirm-btn ">确定</button>
				</div>
			</div>
		</div>
		</div>
<!-- 服务地区子窗口END -->

<!-- 服务类型子窗口START-->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button> 
					<h4 class="modal-title" id="myModalLabel">服务商服务类型</h4>
				</div>
				<div class="modal-body" style="overflow:scroll;width:590px; height:400px;”>
					<div class="form-group">
                              <label  class="col-md-3 control-label">服务类型</label>
                              <div class="col-md-9">
                                 <div class="portlet">
					                  <div class="portlet-body">
					                  	   <c:import url = "../shared/treeSelector.jsp">
											 <c:param name="propertyName"  value="partnerTypeIds"/>
											 <c:param name="propertyValue" value="${partners.getPartnerTypeIdsString()}"/>
											 <c:param name="checkbox" value="true"/>
											 <c:param name="treeDataSourceName" value="treeDataSource"/>
										   </c:import>
					                  </div>
					             </div>
                              </div>
                           </div> 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary serviceType-confirm-btn">确定</button>
				</div>
			</div>
		</div>
		</div>
<!-- 服务类型子窗口END-->
		<!-- page end-->
	</section> 
	</section> 
	 <%@ include file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<!--script for this page-->
	
	<!-- 省市联动js -->
	<script	type="text/javascript" src="<c:url value='/js/jquery.chained.remote.min.js'/>"></script>
	<!--左右联动多选择js  -->
	<script type="text/javascript"  src="<c:url value='/assets/jquery-multi-select/js/jquery.multi-select.js'/>"></script>
	<!-- form表单验证 -->
	<script type="text/javascript" 	src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script  type="text/javascript" src="<c:url value='/js/simi/partner/partnerForm.js'/>"></script>
	<!-- partner依赖的城市，地区js -->
	<script  type="text/javascript" src="<c:url value='/js/simi/partner/partnerRef.js'/>"></script>
	<!--绑定服务类别  -->
	<script src="<c:url value='/js/jquery.treeLites.js?ver=10'/>"></script>
</body>
</html>