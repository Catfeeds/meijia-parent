<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input type="hidden" id="companyId" value="${companyId}" />
<input type="hidden" id="companyName" value="${companyName}" />
<input type="hidden" id="shortName" value="${shortName}" />

<div class="col-sm-2">
	<div class="box hidden-print" style="height: 500px;">
		<div class="title">
			<h4>
				<div style="margin-top: -10px;">
					<a href="#">部门列表</a>
				</div>

			</h4>
		</div>
		<ul id="detpTree" class="ztree"></ul>

		<!-- Modal -->
		<div class="modal fade" id="deptAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">

			<div class="modal-dialog">
				<div class="modal-content">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

						<h4 class="modal-title" id="myModalLabel">添加部门</h4>
					</div>

					<div class="modal-body">
						<form  class="form-horizontal">
						<div class="form-group">
								
							<div class="col-md-12">
								
									<label class="col-md-3 control-label">上级部门:</label>

									<div class="col-md-9">
										<label id="parent_name_modal"></label>
									</div>
							</div>
						</div>
						
						<div class="form-group">
								
							<div class="col-md-12">
									<label class="col-md-3 control-label">部门名称:</label>
									<div class="col-md-9">
										<input type="text" class="form-control" name="dept_name_modal" id="dept_name_modal" maxLength="32"/>
									</div>
							</div>
						</div>
						</form>
					</div>

					<div class="modal-footer">
						<input type="hidden" id="tree_tid_modal" value="0" />
						<input type="hidden" id="parent_id_modal" value="0" />
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

						<button type="button" class="btn btn-primary" data-dismiss="modal">提交</button>
					</div>

				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->

		</div>
		<!-- /.modal -->
	</div>
</div>


