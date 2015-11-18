<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.simi.oa.common.UrlHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!-- taglib for this page -->
<%@ taglib prefix="orderFromTag" uri="/WEB-INF/tags/orderFromName.tld" %>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<%@ taglib prefix="orderStatusTag" uri="/WEB-INF/tags/orderStatusName.tld" %>
<%@ taglib prefix="payTypeNameTag" uri="/WEB-INF/tags/payTypeName.tld" %>
<%@ taglib prefix="orderServiceTimeTag" uri="/WEB-INF/tags/orderServiceTime.tld" %>
<%@ taglib prefix="serviceTypeTag" uri="/WEB-INF/tags/serviceTypeName.tld" %>
<%@ taglib prefix="serviceTypeSelectTag" uri="/WEB-INF/tags/serviceTypeSelect.tld" %>
<%@ taglib prefix="orderStatusSelectTag" uri="/WEB-INF/tags/orderSatusSelect.tld" %>
<%@ taglib prefix="orderFromSelectTag" uri="/WEB-INF/tags/orderFromSelect.tld" %>

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

              <div class="row">
                  <div class="col-lg-12">
                      <section class="panel">
                          <header class="panel-heading">
                          	订单列表
                          	<div class="pull-right">
                          		<button onClick="btn_add('/account/register?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>      
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
                                	  <th >订单号</th>
		                              <th >下单时间</th>
		                              <!-- <th >服务类型</th> -->
		                              <th >服务日期</th>
		                              <!-- <th >服务时间</th> -->
		                              <th>用户手机号</th>
		                              <th >地址</th>
		                              <!-- <th >订单状态</th> -->
		                          <!--     <th >总金额</th> -->
		                              <!-- <th >支付金额</th> -->
		                             <!--  <th >支付方式</th> -->
		                               <th>操作</th>
		                             <!--  <th>优惠券</th> -->
		                              <!-- <th>备注</th> -->
		                             
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
                                  	  <td>${ item.orderNo }</td>
							            <td>
							            	<timestampTag:timestamp patten="yyyy-MM-dd" t="${item.addTime * 1000}"/>
							            </td>

							           <%--  <td>
											<serviceTypeTag:servicetype serviceType="${item.serviceType }"/>

							            </td> --%>
							            <td>
											<timestampTag:timestamp patten="yyyy-MM-dd" t="${item.serviceDate * 1000}"/>
										</td>

							            <%-- <td>
							            	<orderServiceTimeTag:serviceTime startTime="${item.startTime}" serviceHours="${item.serviceHours}"/>
							            </td> --%>

										<td>${ item.mobile }</td>
							            <td>${ item.addrId }</td>
							          <%--   <td>
							            	<c:if test="${ item.orderStatus < 2 }">
							            		<orderStatusTag:orderstatus orderStatus="${ item.orderStatus }"/>
							            	</c:if>
							            	<c:if test="${ item.orderStatus == 2 }">
							            		<span style="color:red;font-weight:bold;"><orderStatusTag:orderstatus orderStatus="${ item.orderStatus }"/></span>
							            	</c:if>
							            	<c:if test="${ item.orderStatus > 2 }">
							            		<span style="color:green;font-weight:bold;"><orderStatusTag:orderstatus orderStatus="${ item.orderStatus }"/></span>
							            	</c:if>
							            </td> --%>
							           <%--  <td>${ item.orderMoney }</td> --%>
							           <%--  <td>${ item.orderPay }</td> --%>
							            <%-- <td><payTypeNameTag:payType payType="${ item.payType }" orderStatus="${ item.orderStatus }"/></td>
							       		 --%>
							       		 <%-- <td>${item.cardPasswd }</td> --%>
							       	<%-- 	<td>${item.remarks }</td> --%>
							       	<td><button id="btn_update" onClick="btn_update('order/orderView?user_id=${ item.userId }')" class="btn btn-primary btn-xs" title="订单详情"><i class=" icon-ambulance"></i></button></td>
							       		<%-- <td>
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
	        				<c:param name="urlAddress" value="/order/list"/>
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
	<script src="<c:url value='/js/simi/order/orderList.js'/>"></script>
  </body>
</html>