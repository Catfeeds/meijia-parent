<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="row">
	<div class="col-md-12">

		<div class="portlet box light-grey">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-search"></i>数据检索
				</div>
			</div>
			<div class="portlet-body form">
				<!-- BEGIN FORM-->
				<form:form modelAttribute="searchVoModel" action="list" class="form-horizontal"
					method="Get">
					<div class="form-body">

						<div class="row">
							<div class="col-md-5">
								<div class="form-group">
									<label class="control-label col-md-5">标题</label>
									<div class="col-md-5">
										<form:input path="title" id="title" class="form-control "
											autocomplete="off" placeholder="标题" />

									</div>
								</div>
							</div>
							<div class="col-md-5">
								<div class="form-group">
									<label class="control-label col-md-5">用户类型</label>
									<div class="col-md-5">
										<form:select path="userType" class="form-control">
												<option value="">请选择用户类型</option>
												<form:option value="0" label="普通用户"/>  
												<form:option value="1" label="秘书"/>  
												<form:option value="2" label="服务商"/>  
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-5">
								<div class="form-group">
									<label class="control-label col-md-5">是否已发送</label>
									<div class="col-md-5">
										<form:select path="isSend" class="form-control">
											<option value=""> 请选择发送类型</option>
											<form:option value="0" label="未发送"/>  
											<form:option value="1" label="已发送"/> 
										</form:select>
									</div>
								</div>
							</div>
						</div>
				
					</div>
					<div class="form-actions">
						<div class="row">
							<div class="col-md-5">
								<div class="col-md-offset-10">
									<button type="submit" class="btn btn-success">搜索</button>
								</div>
							</div>
							<div class="col-md-6">
								<div class="col-md-offset-1">
									<button type="reset" onclick="doReset();"
										class="btn btn-success">重置</button>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<!-- END FORM-->
			</div>
		</div>
	</div>
</div>