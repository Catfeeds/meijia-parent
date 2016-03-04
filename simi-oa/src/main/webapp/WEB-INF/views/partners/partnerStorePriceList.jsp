<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!--taglib for this page  -->
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<html>
  <head>
	
	<!--common css for all pages-->
	<%@ include file="../shared/importCss.jsp"%>
	
	<!--css for this page-->
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
				<%-- <%@ include file="../common/partner/partnerUserSearch.jsp"%> --%>
              <div class="row">
                  <div class="col-lg-12">
                      <section class="panel">
                          <header class="panel-heading">
                          	<%-- <b>${companyName}</b> --%> - 服务人员商品列表
                          	
                          	<div class="pull-right">
                          		<button onClick="btn_add('/partners/partner_service_price_form?service_price_id=0&user_id=${user_id}&partner_id=${partner_id}&service_type_id=${service_type_id}')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>     
                          </header>
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          
							<%-- <form:hidden path="user_id" /> --%>
                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
                                	  <th>商品图片</th>
                                	  <th>商品名称</th>
		                              <th>原价</th>
		                              <th>折扣价</th>
		                              <th>服务说明</th>
		                              <th>状态</th>
		                              <th>添加时间</th>
		                              <th>操作</th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
	                                   <td><img alt="" width="50" height="50" src="${item.imgUrl}"></td>
							           
							            <td>${item.name}</td>
							            
								        <td>${item.price}</td>
								        <td>${item.disPrice}</td>
							            <td>${item.contentDesc}</td>
							            <td>
							            	<c:if test="${item.isEnable == 0 }"><font color="red">下架</font></c:if>
							            	<c:if test="${item.isEnable == 1 }"><font color="green">上架</font></c:if>
							            </td>
							            
							            
							            <td> <timestampTag:timestamp patten="yyyy-MM-dd" t="${item.addTime * 1000}"/></td>
							           
							            <td>
							            	<button id="btn_update"  onClick="btn_update('/partners/partner_service_price_form?service_price_id=${item.servicePriceId }&user_id=${user_id}&partner_id=${partner_id}&service_type_id=${service_type_id}')" class="btn btn-primary btn-xs" title="修改"><i class="icon-pencil"></i></button>
							            </td>
                              </tr>
                              </c:forEach>
                              </tbody>
                          </table>

                          
                      </section>
                      
                      <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="contentModel"/>
	        				<c:param name="urlAddress" value="/partners/partner_service_price_list"/>
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

	<script src="<c:url value='/js/simi/partner/partnerUserList.js'/>"></script>
  </body>
</html>