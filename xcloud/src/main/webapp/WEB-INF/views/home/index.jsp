<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf"></div>
			</div>
			<div class="am-g ">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-secondary">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-company'}">
							HI, ${sessionScope.accountAuth.name}, 美好的一天从这里开始了.
							<span class="am-icon-chevron-down am-fr"></span>
						</div>
						<div id="collapse-index-company" class="am-panel-bd am-in">
							&nbsp;&nbsp;你的企业未认证，申请企业认证，获取更多的权益.
							<div class="am-btn-toolbar am-fr">
								<div class="am-btn-group am-btn-group-sm ">
									<button type="button" id="btn-company-idn" class="am-btn am-btn-warning am-radius">
										<span class="am-icon-star"></span>
										申请企业认证
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="am-g ">
				
					<div class="am-panel am-panel-secondary">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-company'}">
							常用功能
							<span class="am-icon-arrow-right am-fr">更多</span>

						</div>
						<div id="collapse-index-company" class="am-panel-bd am-in">
							<ul class="am-avg-sm-1 am-avg-md-8 am-margin am-padding am-text-center admin-content-list ">
								<li>
									<a href="#" class="am-text-success">
										<img src="http://img.51xingzheng.cn/733d857f736cdb7cefe39379df5634ea?p=0">
										<br />通知公告<br />
									</a>
								</li>
								<li>
									<a href="#" class="am-text-warning">
										<img src="http://img.51xingzheng.cn/8befcfd517342750758dceb3893e705c?p=0">
										<br />事务提醒<br />
									</a>
								</li>
								<li>
									<a href="#" class="am-text-danger">
										<img src="http://img.51xingzheng.cn/3fdcf4c046095e6b1a352d8316635e2a?p=0">
										<br />会议安排<br />
									</a>
								</li>
								<li>
									<a href="#" class="am-text-secondary">
										<img src="http://img.51xingzheng.cn/b7573e98e89ce8a59a3107fa66be2591?p=0">
										<br />面试邀约<br />
									</a>
								</li>
								
								<li>
									<a href="#" class="am-text-secondary">
										<img src="http://img.51xingzheng.cn/0467c7ef376f21477359124fa8f038e1?p=0">
										<br />云考勤<br />
									</a>
								</li>
								
								<li>
									<a href="#" class="am-text-secondary">
										<img src="http://img.51xingzheng.cn/2997737093caa7e25d98579512053b5c?p=0">
										<br />请假审核<br />
									</a>
								</li>
								
								
								<li>
									<a href="#" class="am-text-secondary">
										<img src="http://img.51xingzheng.cn/ff54c25acfe1c170e7c93e457483aa9a?p=0">
										<br />资产管理<br />
									</a>
								</li>
								
								<li>
									<a href="#" class="am-text-secondary">
										<img src="http://img.51xingzheng.cn/15bd4e23a7882291d3ca9bd612011cd1?p=0">
										<br />工资计算器<br />
									</a>
								</li>
							</ul>
						</div>
					</div>

			</div>
			<div class="am-g">
				<div class="am-u-md-6">
					<div class="am-panel am-panel-danger">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-card'}">
							今日提醒
							<span class="am-icon-arrow-right am-fr">更多</span>
						</div>
						<div id="collapse-index-card" class="am-in" height="100%">
							<table class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									<tr>
										<th class="am-text-center"></th>
										<th>内容</th>
										<th>提醒类型</th>
									</tr>
									<tr>
										<td class="am-text-center">1</td>
										<td>关于新员工培训的会议室</td>
										<td>会议安排</td>
									</tr>
									<tr>
										<td class="am-text-center">1</td>
										<td>关于新员工培训的会议室</td>
										<td>会议安排</td>
									</tr>
									<tr>
										<td class="am-text-center">1</td>
										<td>关于新员工培训的会议室</td>
										<td>会议安排</td>
									</tr>
									<tr>
										<td class="am-text-center">1</td>
										<td>关于新员工培训的会议室</td>
										<td>会议安排</td>
									</tr>
									<tr>
										<td class="am-text-center">1</td>
										<td>关于新员工培训的会议室</td>
										<td>会议安排</td>
									</tr>
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
				<div class="am-u-md-6">
					<div class="am-panel am-panel-warning">
						<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-index-wp'}">
							每日新知
							<span class="am-icon-arrow-right am-fr">更多</span>
						</div>
						<div id="collapse-index-wp" class="am-in">
							<table class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
								<tbody>
									<tr>
										<th>最新资讯</th>
										<th></th>
									</tr>
									<tr>
										<td>关于新员工培训的会议室</td>
									</tr>
									<tr>
										<td>关于新员工培训的会议室</td>
									</tr>
									<tr>
										<td>关于新员工培训的会议室</td>
									</tr>
									<tr>
										<td>关于新员工培训的会议室</td>
									</tr>
									<tr>
										<td>关于新员工培训的会议室</td>
									</tr>
								</tbody>
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/home/index.js'/>"></script>
</body>
</html>
