<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>
<%@ taglib prefix="provinceSelectTag" uri="/WEB-INF/tags/provinceSelect.tld"%>

<html>
<head>

<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>

<!--css for this page-->
<%-- <link href="<c:url value='/assets/bootstrap-datepicker/css/bootstrap-datepicker3.min.css'/>"
	rel="stylesheet" type="text/css" /> --%>
<%@ include file="../shared/importJs.jsp"%>
	
</head>

<body>

	<section id="container"> <!--header start-->
	 <%@ include file="../shared/pageHeader.jsp"%> <!--header end-->

	<!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%>
	<!--sidebar end--> <!--main content start--> <section id="main-content">
	<section class="wrapper"> <!-- page start-->
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			服务商采集信息 </header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				 <form:form modelAttribute="spiderPartner" 	commandName="spiderPartner" 
					class="form-horizontal" method="POST" id="spiderPartnerForm">
					<div class="form-body">
						
						<div class="form-group">
							<!-- Text input-->
							<label class="col-md-2 control-label">公司名称</label>
							<div class=col-md-5>
							${spiderPartner.companyName }
							</div>
						</div>

						<div class="form-group">


							<label class="col-md-2 control-label">注册时间 </label>

							<div class=col-md-5>
							${spiderPartner.registerTime }
							</div>

						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">详细地址</label>
							<div class="col-md-5">
							${spiderPartner.addr }
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">认证</label>
							<div class="col-md-5">
							${spiderPartner.certification }
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">信用档案url</label>
							<div class="col-md-5">
							<a href="${spiderPartner.creditFileUrl }">${spiderPartner.creditFileUrl }</a>
								<p class="help-block"></p>
							</div>
						</div>

						<div class="form-group">

							<!-- Textarea -->
							<label class="col-md-2 control-label">企业网站</label>
							<div class="col-md-5">
							<a href="${spiderPartner.website }">${spiderPartner.website }</a>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">服务区域</label>
							<div class="col-md-5">
							${spiderPartner.serviceArea }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">服务类别</label>
							<div class="col-md-5">
							${spiderPartner.serviceType }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">服务小类</label>
							<div class="col-md-5">
							${spiderPartner.serviceTypeSub }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">联系人</label>
							<div class="col-md-5">
							${spiderPartner.linkMan }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">联系电话</label>
							<div class="col-md-5">
							${spiderPartner.linkTel }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">公司简介</label>
							<div class="col-md-7">
							${spiderPartner.companyDescImg }
							${spiderPartner.companyDesc }
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
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

							<!-- Text input-->
							<label class="col-md-2 control-label">累计评价</label>
							<div class="col-md-5">
							${spiderPartner.totalRate }
								<p class="help-block"></p>
							</div>
						</div>
					</div>
					<!-- </fieldset> -->
				</form:form>
			</div>
			</section>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			服务商人工处理信息 </header>

			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />

			<div class="panel-body">
				 <form:form modelAttribute="partners" 	commandName="partners" 
					class="form-horizontal" method="POST" action="spiderPartnerForm" id="partner-form">
					<div class="form-body">
						<form:hidden path="spiderPartnerId"  />
						<form:hidden path="partnerId"  />
						<div class="form-group">
							<!-- Text input-->
							<label class="col-md-2 control-label">公司简称</label>
							<div class=col-md-5>
								<form:input path="shortName" class="form-control" placeholder="公司简称"
									maxSize="10" />
							</div>
						</div>
						<div class="form-group ">
						<label class="col-md-2 control-label">联系人信息</label>
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
						<div class="form-group">


							<label class="col-md-2 control-label">公司规模 </label>

							<div class=col-md-5>
								<form:input path="companySize" class="form-control" placeholder="公司规模"
									maxSize="10" />
							</div>

						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">所在省份</label>
							<div class="col-md-5">
								<provinceSelectTag:select selectId="${partner.provinceId}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">所在城市</label>
							<div class="col-md-5">
								<select path="cityId" name="cityId" id="cityIds" class="form-control"
									autocomplete="off">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">是否上门</label>
							<div class="col-md-8">
							<div class="row">
								<div class="col-md-2" align="center">
									<label class="radio">
										<form:radiobutton path="isDoor" value="0"/>不上门
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="isDoor" value="1"/>上门
									</label>
								</div>
							</div>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">关键词</label>
							<div class="col-md-5">
								<form:input path="keywords" class="form-control"
									placeholder="关键词" maxSize="10" />

							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">状态</label>
							<div class="col-md-8">
							<div class="row">
								<div class="col-md-2" align="center">
									<label class="radio">
										<form:radiobutton path="status" value="0"/>已采集
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="status" value="1"/>考察中
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="status" value="2"/>已考察
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="status" value="3"/>待认证
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="status" value="4"/>已认证
									</label>
								</div>
							</div>
							<p class="help-block"></p>
							</div>
						</div>

						<div class="form-group">

							<!-- Textarea -->
							<label class="col-md-2 control-label">业务介绍</label>
							<div class="col-md-5">
								<form:input path="businessDesc" class="form-control"
									placeholder="业务介绍" />
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">微信号</label>
							<div class="col-md-5">
								<form:input path="weixin" class="form-control"
									placeholder="微信号" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">QQ</label>
							<div class="col-md-5">
								<form:input path="qq" class="form-control"
									placeholder="QQ" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">邮箱</label>
							<div class="col-md-5">
								<form:input path="email" class="form-control"
									placeholder="邮箱" />
								<p class="help-block"></p>
							</div>
						</div>
						<%-- <div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">公司LOGO</label>
							<div class="col-md-5">
								<form:input path="companyLogo" class="form-control"
									placeholder="公司LOGO" />
								<p class="help-block"></p>
							</div>
						</div> --%>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">传真</label>
							<div class="col-md-5">
								<form:input path="fax" class="form-control"
									placeholder="传真" />
								<p class="help-block"></p>
							</div>
						</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">支付方式</label>
							<div class="col-md-8">
							<div class="row">
								<div class="col-md-2" align="center">
									<label class="radio">
										<form:radiobutton path="payType" value="0"/>月结
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="payType" value="1"/>按次结算
									</label>
								</div>
								<div class="col-md-2" align="left">
									<label class="radio">
									<form:radiobutton path="payType" value="2"/>预付
									</label>
								</div>
							</div>
							</div>
								<p class="help-block"></p>
							</div>
						<div class="form-group">

							<!-- Text input-->
							<label class="col-md-2 control-label">折扣</label>
							<div class="col-md-5">
								<form:input path="discout" class="form-control"
									placeholder="折扣" />
								<p class="help-block"></p>
							</div>
						</div>
						 <div class="form-group">
                              <label  class="col-md-2 control-label">选择服务类型</label>
                              <div class="col-md-10">
                                 <div class="portlet">
					                  <div class="portlet-body">
					                  	   <c:import url = "../shared/treeSelector.jsp">
											 <c:param name="propertyName" value="partnerTypeIds"/>
											 <c:param name="propertyValue" value="${partners.getPartnerTypeIdsString()}"/>
											 <c:param name="checkbox" value="true"/>
											 <c:param name="treeDataSourceName" value="treeDataSource"/>
										   </c:import>
					                  </div>
					             </div>
                              </div>
                           </div>    
					</div>
					<div class="form-actions">
						<div class="row">
							<div class="col-md-4" align="right">
								<button class="btn btn-success" id="save_partner_btn" type="button">保存</button>
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
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> 
	<%@ include file="../shared/pageFooter.jsp"%> <!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	
	
	<!--script for this page-->
	<!-- 省市联动js -->
	<script type="text/javascript"	src="<c:url value='/js/jquery.chained.remote.min.js'/>"></script>
	<!-- form表单验证 -->
    <script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>"></script>
	<script src="<c:url value='/js/simi/partner/partnerForm.js'/>"></script>
	
	<script src="<c:url value='/assets/jquery-multi-select/js/jquery.multi-select.js'/>"></script>
	<!--绑定服务类别  -->
	<script type="text/javascript" src="<c:url value='/js/jquery.treeLite.js?ver=10'/>"></script>
	
</body>
</html>
