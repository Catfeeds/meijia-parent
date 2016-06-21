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
	<div class="row">
		<div class="col-lg-12">
			<section class="panel"> <header class="panel-heading"> 识别来源规则库 </header>
			<hr style="width: 100%; color: black; height: 1px; background-color: black;" />
			<form:form modelAttribute="contentModel" enctype="multipart/form-data" class="form-horizontal" method="POST"
				id="contentForm">
				<form:hidden path="id" />
				<div class="row">
					<div class="col-lg-8" >
						<section class="panel"> <header class="panel-heading"> 规则信息 </header>
						<div class="panel-body">
							
							<div class="form-body">
								<div class="form-group required">
									<label class="col-md-2 control-label">简历来源</label>
									<div class="col-md-8">
										<form:select path="fromId" class="form-control">
											<form:option value="">请选择简历来源</form:option>
											<form:options items="${hrFroms}" itemValue="fromId" itemLabel="name" />
										</form:select>
										<form:errors path="fromId" class="field-has-error"></form:errors>
									</div>
								</div>
								<div class="form-group required">
									<label class="col-md-2 control-label">文件类型</label>
									<div class="col-md-8">
										<form:select path="fileType" class="form-control">
											<form:option value="html">html</form:option>
											<form:option value="word">word</form:option>
											<form:option value="mht">mht</form:option>
											<form:option value="email">email</form:option>
										</form:select>
										
									</div>
								</div>
								<div class="form-group required">
									<label class="col-md-2 control-label">样本方式</label>
									<div class="col-md-8">
										<select id="sampleType" name="sampleType" class="form-control">
											<option value="textarea">样本源码</option>
											<option value="upload">样本上传</option>
											
										</select>
									</div>
								</div>
								<div id="div-sample-file" class="form-group" style="display: none">
									<label class="col-md-2 control-label">样本上传</label>
									<div class="col-md-8">
										<div class="dropzone"></div>
										<form:errors path="samplePath" class="field-has-error"></form:errors>
										<form:hidden path="samplePath" />
									</div>
								</div>
								<div id="div-sample-src" class="form-group " style="display: none">
									<label class="col-md-2 control-label">样本源码</label>
									<div class="col-md-8">
										<form:textarea class="form-control" path="sampleSrc" placeholder="把html源码粘贴在这里" />
										<br />
									</div>
								</div>
								<div class="form-group required">
									<label class="col-md-2 control-label">匹配方式</label>
									<div class="col-md-8">
										<form:select path="matchType" class="form-control">
											<form:option value="reg">正则匹配</form:option>
											<form:option value="jsoup">jsoup</form:option>
										</form:select>
									</div>
								</div>
							</div>
						</section>
						
						<!-- 正则匹配设置 -->
						<section class="panel" id="section-reg-match" style="display:none"> 
						<header class="panel-heading"> 正则匹配信息 </header>
						<div class="panel-body">
							<div class="form-group required">
								<label class="col-md-2 control-label">区块匹配(blockRegex)</label>
								<div class="col-md-6">
									<form:input path="blockRegex" class="form-control" placeholder="正则" maxLength="255" />
								</div>
								<div class="col-md-2">
									<button type="button" id="reg-match-test-btn" class="btn btn-info">测试</button>
								</div>
							</div>
							<div class="form-group required">
								<label class="col-md-2 control-label">区块匹配索引(blockMatchIndex)</label>
								<div class="col-md-8">
									<form:input path="blockMatchIndex" class="form-control" placeholder="0-9" maxLength="3" />
								</div>
							</div>
							<div class="form-group required">
								<label class="col-md-2 control-label">字段匹配表达式(fieldRegex)</label>
								<div class="col-md-8">
									<form:input path="fieldRegex" class="form-control" placeholder="正则" maxLength="255" />
								</div>
							</div>
							
							<div class="form-group required">
								<label class="col-md-2 control-label">字段匹配索引(fieldMatchIndex)</label>
								<div class="col-md-8">
									<form:input path="fieldMatchIndex" class="form-control" placeholder="0-9" maxLength="3" />
								</div>
							</div>
						</div>
						</section>
						
						
						<!-- jsoup匹配设置 -->
						<section class="panel" id="section-jsoup-match-reg" style="display:none"> 
						<header class="panel-heading"> 1.Jsoup匹配信息 </header>
						<div class="panel-body">
							<div class="form-group required">
								<label class="col-md-2 control-label">匹配表达式(findPatten)</label>
								<div class="col-md-6">
									<form:input path="findPatten" class="form-control" placeholder="jsoup表达式 span.resume-left-tips-id" maxLength="255" />
								</div>
								<div class="col-md-2">
									<button type="button" id="jsoup-match-test-btn" class="btn btn-info">测试</button>
								</div>
							</div>
							<div class="form-group required">
								<label class="col-md-2 control-label">textOrHtml</label>
								<div class="col-md-8">
									<select id="textOrHtml" class="form-control">
										<option value="text">text</option>
										<option value="html">html</option>
									</select>
								</div>
							</div>
							<div class="form-group required">
								<label class="col-md-2 control-label">匹配元素值(attrName)</label>
								<div class="col-md-8">
									<form:input path="attrName" class="form-control" placeholder="比如html标签元素的属性值" maxLength="255" />
								</div>
							</div>
						</div>
						</section>
						<section class="panel" id="section-jsoup-result-match-reg" style="display:none" > <header class="panel-heading"> 2.结果匹配信息 </header>
						<div class="panel-body">
							<div class="form-group required">
								<label class="col-md-2 control-label">过滤信息(removeRegex)</label>
								<div class="col-md-8">
									<form:input path="removeRegex" class="form-control" placeholder="在结果中过滤信息" maxLength="255" />
								</div>
							</div>
							<div class="form-group required">
								<label class="col-md-2 control-label">结果中匹配表达式(resultRegex)</label>
								<div class="col-md-8">
									<form:input path="resultRegex" class="form-control" placeholder="正则" maxLength="255" />
								</div>
							</div>
							
							<div class="form-group required">
								<label class="col-md-2 control-label">结果中匹配表达式索引(index)</label>
								<div class="col-md-8">
									<form:input path="resultIndex" class="form-control" placeholder="0-9" maxLength="3" />
								</div>
							</div>

						</div>
						</section>
						
						<section class="panel"  > <header class="panel-heading"> 3. </header>
						<div class="panel-body">
							<div class="form-group required">
								<label class="col-md-2 control-label">正确的匹配结果</label>
								<div class="col-md-8">
									<form:input path="matchCorrect" class="form-control" placeholder="在结果中过滤信息" maxLength="255" />
								</div>
							</div>
						</div>
						</section>
						
						<section class="panel" > 
						<div class="panel-body">
							
							<div class="form-actions fluid">
								<div class="col-md-offset-6 col-md-6">
									<button type="submit" id="btn-save" class="btn btn-success">保存</button>
								</div>
							</div>
						</div>
						</section>
					</div>
					<div class="col-lg-3">
						<section class="panel"> <header class="panel-heading"> 测试结果 </header>
						<div class="panel-body">
							<div class="form-group required">
								<div class="form-group required">
									<div class="col-md-12">
										<label id="testMatchResult"></label>
										<input type="hidden" id="testSuccess" value="0"/>
									</div>
								</div>
							</div>
						</div>
						</section>
					</div>
				</div></section>
		</div>
		</form:form>
	</div>
	<!-- page end--> </section> </section> <!--main content end--> <!--footer start--> <%@ include file="../shared/pageFooter.jsp"%>
	<!--footer end--> </section>
	<!-- js placed at the end of the document so the pages load faster -->
	<!--common script for all pages-->
	<%@ include file="../shared/importJs.jsp"%>
	<!--script for this page-->
	<script src="<c:url value='/assets/dropzone/dropzone.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/assets/jquery-validation/dist/jquery.validate.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/simi/resume/hrRuleFromForm.js'/>" type="text/javascript"></script>
	<script type="text/javascript">
		$('#matchType').trigger('change');
		$('#sampleType').trigger('change');
	</script>
</body>
</html>
