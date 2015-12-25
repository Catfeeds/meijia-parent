<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input type="hidden" id="companyId" value="${companyId}" />
<input type="hidden" id="companyName" value="${companyName}" />
<input type="hidden" id="shortName" value="${shortName}" />
<%-- <input type="hidden" id="deptId" value="${deptId}" /> --%>

		<div class="am-cf am-padding">
			<div class="am-fl am-cf">
				<strong class="am-text-primary am-text-lg">部门列表</strong>
			</div>
			
			<div class="am-fr">
				<button type="button" class="am-btn am-btn-warning" id="btn-dept-add">添加部门</button>
			</div>
		</div>
		<hr />
		
		<%-- <a href="/xcloud/staff/list?dept_id=${deptId}"> --%>
		<ul id="detpTree" class="ztree"></ul>

		<div class="am-modal am-modal-confirm" tabindex="-1" id="dept-modal">
			<div class="am-modal-dialog">
				<div class="am-modal-hd">添加部门</div>
				<div class="am-modal-bd">
			      
			      
			      <input type="text" class="am-modal-prompt-input" name="dept_name_modal" id="dept_name_modal" maxLength="32"/>
			    </div>
				<div class="am-modal-footer">
					<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn"
						data-am-modal-confirm>确定</span>
				</div>
				<input type="hidden" id="tree_tid_modal" value="0" />
				<input type="hidden" id="parent_id_modal" value="0" />
			</div>
		</div>



