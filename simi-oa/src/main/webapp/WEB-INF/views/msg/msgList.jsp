<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!--taglib for this page-->
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<%@ taglib prefix="msgSendGroupTag" uri="/WEB-INF/tags/msgSendGroup.tld"%>
<%@ taglib prefix="msgSendGroupSelectTag" uri="/WEB-INF/tags/msgSendGroupSelect.tld" %>
<%@ taglib prefix="statusTag" uri="/WEB-INF/tags/statusName.tld" %>
<%@ taglib prefix="sendStatusTag" uri="/WEB-INF/tags/sendStatusName.tld" %>

<html>
  <head>
	
	<!--common css for all pages-->
	<%@ include file="../shared/importCss.jsp"%>
	
	<!--css for this page-->
   <link rel="stylesheet" href="<c:url value='/assets/bootstrap3-dialog-master/dist/css/bootstrap-dialog.min.css'/>" type="text/css"/>
   <link rel="stylesheet" href="<c:url value='/assets/data-tables/DT_bootstrap.css'/>" type="text/css"/>
   <style>
.modal-backdrop {
    position: relative;
}
   	#sum{
	 width: 100px; 
	 overflow: hidden; 
	 text-overflow:ellipsis
} 
   </style>
	
  </head>

  <body>

  <section id="container" >
	  
	  <!--header start-->
	  <%@ include file="../shared/pageHeader.jsp"%>
	  <!--header end-->
	  
      <!--sidebar start-->
	  <%@ include file="../shared/sidebarMenu.jsp"%>
      <!--sidebar end-->
      
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
              <!-- page start-->
				<%-- <%@ include file="../common/msg/msgSearch.jsp"%> --%>
              <div class="row">
                  <div class="col-lg-12">
                      <section class="panel">
                         <form:form modelAttribute="searchVoModel" action="list" method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					标题：
					<form:input path="title" />
					用户类型：
					<form:select path="userType">
						<option value="">全部</option>
							<form:option value="0" label="普通用户"/>  
							<form:option value="1" label="秘书"/>  
							<form:option value="2" label="服务商"/>
					</form:select>
					发送类型：
					<form:select path="isSend">
						<option value=""> 请选择发送类型</option>
							<form:option value="0" label="未发送"/>  
							<form:option value="1" label="已发送"/> 
					</form:select>

					<input type="submit" value="搜索">
				</div>
				</header>
			</form:form>
                          <header class="panel-heading">
                          	消息管理
                          	
                          	<div class="pull-right">
                          		<button onClick="btn_add('msg/msgForm?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>      
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          

                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
			                              <th >标题</th>
			                              <th >用户类型</th>
			                              <th>发送时间</th>
			                              <th>是否已发送</th>
			                              <th>是否可用</th>
			                              <th >操作</th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
							            <td>${ item.title }</td>
							            <td>${ item.userType }</td>
							            <td>
							            	<timestampTag:timestamp patten="yyyy-MM-dd HH:mm:ss" t="${item.sendTime * 1000}"/>
							            </td>
							            <td>${ item.isSend }</td>
							            <td>
							            	<statusTag:status status="${ item.isEnable }"/>
							            </td>
							            <td>
							                <button id="btn_update" onClick="btn_update('msg/msgForm?id=${ item.msgId }')" class="btn btn-primary btn-xs" title="修改"><i class="icon-pencil"></i></button>
							            </td>
							            <%-- <td>
							            	<c:if test="${item.sendStatus==1}">
							            	<button type="button"  onclick="sendAgain('${(item.id)}')"class="btn btn-primary btn-xs">再次发送</button>
							            	</c:if>
							            	<c:if test="${item.sendStatus==0}">
							            	<button type="button"  onclick="sendAgain('${(item.id)}')"class="btn btn-primary btn-xs">发送</button>
							            	</c:if>
							            	<button type="button"  onclick="preview('${item.htmlUrl}')" class="btn btn-success btn-xs">预览消息</button>
							              
							                <button id="btn_update" onClick="btn_update('msg/msgForm?id=${ item.id }')" class="btn btn-primary btn-xs" title="修改"><i class="icon-pencil"></i></button>
	                                  		<button id="btn_del" onClick="btn_del('/account/delete/${item.id}')" class="btn btn-danger btn-xs"  title="删除"><i class="icon-trash "></i></button>
							            </td> --%>
                              </tr>
                              </c:forEach>
                              </tbody>
                          </table>
                      </section>
                      
                      <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="contentModel"/>
	        				<c:param name="urlAddress" value="/msg/list"/>
	       			  </c:import>
                  </div>
              </div>
              <!-- page end-->
          </section> 
      </section>
      <!--main content end-->
      
      <!--footer start-->
      <%@ include file="../shared/pageFooter.jsp"%>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <!--common script for all pages-->
    <%@ include file="../shared/importJs.jsp"%>

    <!--script for this page-->	
	<script src="<c:url value='/js/simi/msg/msgList.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/bootstrap3-dialog-master/dist/js/bootstrap-dialog.min.js'/>"></script>

  </body>
</html>