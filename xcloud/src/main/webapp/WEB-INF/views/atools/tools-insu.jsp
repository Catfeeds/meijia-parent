<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<!--common css for all pages-->
<%@ include file="../shared/importCss.jsp"%>
<!--css for this page-->
<link rel="stylesheet" href="<c:url value='/assets/js/chosen/amazeui.chosen.css'/> " />
</head>
<body>
	<!--header start-->
	<%@ include file="../shared/pageHeader.jsp"%>
	<!--header end-->
	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<%@ include file="../atools/atools-menu.jsp"%>
		<!-- sidebar end -->
		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">五险一金计算器</strong> / <small>2016版</small>
				</div>
			</div>
			<hr>
			<div class="am-g am-fl">
				<form id="toolsInsuForm" class="am-form am-form-horizontal" action="" method="POST">
					<div class="am-form-group" required>
						<label class="am-u-sm-5 am-form-label">所在城市<font color="red">*</font></label>
						<div class="am-u-sm-7">
							<select id="cityId" name="cityId" class="am-form-field am-radius" required="required">
								<option value="">请选择城市</option>
								<option value="2">北京</option>
								<option value="74">上海</option>
								<option value="198">广州</option>
								<option value="200">深圳</option>
							</select>
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-5 am-form-label">社保缴纳基数<font color="red">*</font></label>
						<div class="am-u-sm-7">
							<input id="shebao" maxLength="6" minlength="1" class="am-form-field am-radius js-pattern-price"
								required="required" type="text" value="" autocomplete="off" />
							<small><font color="red">最低<label id="shebaoMin">2834</label>/最高<label id="shebaoMax">21258</label></font></small>
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-5 am-form-label">公积金缴纳基数<font color="red">*</font></label>
						<div class="am-u-sm-7">
							<input id="gjj" maxLength="6" minlength="1" class="am-form-field am-radiu js-pattern-price" required="required"
								type="text" value="" />
							<small><font color="red">最低<label id="gjjMin">1720</label>/最高<label id="gjjMax">21258</label></font></small>
						</div>
					</div>
					<hr>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-danger" id="calculate">计算</button>
							<button type="reset" class="am-btn am-btn-success" id="resetForm">重置</button>
						</div>
					</div>
					<hr>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table id="list-table" class="am-table am-table-bordered am-table-striped">
								<thead>
									<tr>
										<th class="table-name">保险项</th>
										<th class="table-name">个人部分</th>
										<th class="table-name">单位部分</th>
									</tr>
								</thead>
								<tbody id="displayTBody">
									<tr>
										<td>养老</td>
										<td><label id="pensionP"></label>(<font color="red"><label id="lpensionP">0</label>%</font>)</td>
										<td><label id="pensionC"></label> (<font color="red"><label id="lpensionC">0</label>%</font>)</td>
									</tr>
									<tr>
										<td>医疗</td>
										<td><label id="medicalP"></label>(<font color="red"><label id="lmedicalP">0</label>%</font>)</td>
										<td><label id="medicalC"></label> (<font color="red"><label id="lmedicalC">0</label>%</font>)</td>
									</tr>
									<tr>
										<td>失业</td>
										<td><label id="unemploymentP"></label>(<font color="red"><label id="lunemploymentP">0</label>%</font>)</td>
										<td><label id="unemploymentC"></label> (<font color="red"><label id="lunemploymentC">0</label>%</font>)</td>
									</tr>
									<tr>
										<td>工伤</td>
										<td><label id="injuryP"></label>(<font color="red"><label id="linjuryP">0</label>%</font>)</td>
										<td><label id="injuryC"></label> (<font color="red"><label id="linjuryC">0</label>%</font>)</td>
									</tr>
									<tr>
										<td>生育</td>
										<td><label id="birthP"></label>(<font color="red"><label id="lbirthP">0</label>%</font>)</td>
										<td><label id="birthC"></label> (<font color="red"><label id="lbirthP">0</label>%</font>)</td>
									</tr>
									<tr>
										<td>公积金</td>
										<td><label id="fundP"></label>(<font color="red"><label id="lfundP">0</label>%</font>)</td>
										<td><label id="fundC"></label> (<font color="red"><label id="lfundC">0</label>%</font>)</td>
									</tr>
									<tr class="am-active">
										<td>合计</td>
										<td><label id="totalP"></label></td>
										<td><label id="totalC"></label></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
			</div>
			</form>
		</div>
	</div>
	<!-- content end -->
	</div>
	<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
		data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
	<!--footer start-->
	<%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/js/xcloud/common/common.js'/>" ></script>

	<script src="<c:url value='/assets/js/xcloud/atools/tools-insu.js'/>"></script>
</body>
</html>
