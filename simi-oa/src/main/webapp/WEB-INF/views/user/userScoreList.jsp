<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
</head>
<body>
	<section id="container"> <!--header start--> <%@ include file="../shared/pageHeader.jsp"%>
	<!--header end--> <!--sidebar start--> <%@ include file="../shared/sidebarMenu.jsp"%> <!--sidebar end-->
	<!--main content start--> <section id="main-content"> <section class="wrapper"> <!-- page start-->
	<%-- <%@ include file="../common/user/userStat.jsp"%> --%>
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading">
			<h4>数据搜索</h4>
			<div class="panel-body">
				<form:form modelAttribute="searchModel" class="form-inline" method="GET">
					<div class="form-group">
						<label class="col-md-5 control-label">手机号：</label>
						<div class="col-md-5">
							<form:input path="mobile" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-5 control-label">积分类型：</label>
						<div class="col-md-5">
							<select id="action" name="action" class="form-control">
								<option value="">全部</option>
								<option value="new_user">新用户注册</option>
								<option value="day_sign">签到</option>
								<option value="leave">请假申请</option>
								<option value="checkin">云考勤</option>
								<option value="order">订单</option>
								<option value="qa">问答相关</option>
								<option value="company_reg">创建企业/团队</option>
								<option value="duiba">兑吧</option>
								<option value="alarm">事务提醒</option>
								<option value="notice">通知公告</option>
								<option value="interview">面试邀约</option>
								<option value="meeting">会议安排</option>
								<option value="trip">差旅规划</option>
							</select>
						</div>
					</div>
					<input type="submit" class="btn btn-success" value="搜索">
				</form:form>
			</div>
			</header> <header class="panel-heading"> 用户积分明细 <!-- <div class="pull-right">
                          		<button onClick="btn_add('/account/register?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>  --> </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<table class="table table-striped table-advance table-hover">
				<thead>
					<tr>
						<th>时间</th>
						<th>用户ID</th>
						<th>手机号</th>
						<th>操作</th>
						<th>用户积分</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${contentModel.list}" var="item">
						<tr>
							<td>${ item.addTimeStr }</td>
							<td>${ item.userId }</td>
							<td>${ item.mobile }</td>
							<td>${ item.actionName }</td>
							<td>${ item.scoreStr }</td>
							<td>${ item.remarks }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</section>
			<c:import url="../shared/paging.jsp">
				<c:param name="pageModelName" value="contentModel" />
				<c:param name="urlAddress" value="/user/scoreList" />
			</c:import>
		</div>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
</body>
</html>