<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

	<!--taglib for this page-->
<%@ taglib prefix="orderFromTag" uri="/WEB-INF/tags/orderFromName.tld" %>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<%@ taglib prefix="payTypeNameTag" uri="/WEB-INF/tags/payTypeName.tld" %>
<%@ taglib prefix="partnerPayTypeNameTag" uri="/WEB-INF/tags/partnerPayTypeName.tld" %>
<%@ taglib prefix="orderFromTag" uri="/WEB-INF/tags/orderFromName.tld" %>
<%@ taglib prefix="userTypeTag" uri="/WEB-INF/tags/userTypeName.tld" %>

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
			 <%-- <%@ include file="../common/user/userStat.jsp"%> --%>
              <div class="row">
                  <div class="col-lg-12">
                      <section class="panel">
                      <form:form modelAttribute="searchModel" action="list" method="GET">
				<header class="panel-heading">
				<h4>数据搜索</h4>
				<div>
					手机号：
					<form:input path="mobile" />
					昵称：
					<form:input path="name" />

					<input type="submit" value="搜索">
				</div>
				</header>
			</form:form>
                          <header class="panel-heading">
                          	用户管理
                          	
                          	<!-- <div class="pull-right">
                          		<button onClick="btn_add('/account/register?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>  -->     
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          

                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
                              		  <th>序号</th>
                                  	  <th >手机号</th>
		                              <th >手机号归属地</th>
		                              <th >用户姓名</th>
		                              <th >用户余额</th>
		                              <th >用户积分</th>
		                            
		                              <th>用户来源</th>
		                              <th >添加时间</th>
		                              <th>操作</th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
                                  <td>${ item.id }</td>

							            <td>${ item.mobile }</td>
							            <td>${ item.provinceName }</td>
							            <td>${ item.name }</td>

							            <td>${ item.restMoney }</td>
							            <td>
											${ item.score }
							            </td>
							          
							            <td>
							            	<orderFromTag:orderfrom orderFrom="${item.addFrom }"/>
							            </td>
							            <td>
							            	<timestampTag:timestamp patten="yyyy-MM-dd" t="${item.addTime * 1000}"/>
							            </td>

                                       <td>
                                            <button id="btn_update" onClick="btn_update('order/list?user_id=${ item.id }')" class="btn btn-primary btn-xs" title="订单"><i class=" icon-ambulance"></i></button>
                                       		<button id="btn1" onClick="btn_update('/user/userForm?id=${item.id}')" class="btn btn-primary btn-xs"
											title="修改">
												<i class="icon-pencil"></i>
											</button>
                                       
                                       </td>
							           
                              </tr>
                              </c:forEach>
                              </tbody>
                          </table>

                          
                      </section>
                       <input type="button" value="导出数据" onclick="download()"/>
                      <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="contentModel"/>
	        				<c:param name="urlAddress" value="/user/list"/>
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
	<script src="<c:url value='/js/simi/user/userList.js'/>"></script>

  </body>
</html>