<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

	<!--taglib for this page  -->
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<%@ taglib prefix="orderTypeTag" uri="/WEB-INF/tags/orderTypeName.tld" %>
<%@ taglib prefix="payTypeTag" uri="/WEB-INF/tags/payTypeName.tld" %>
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
                          	用户消费明细
                          	
                         <!--  	<div class="pull-right">
                          		<button onClick="btn_add('/account/register?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>    -->   
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          

                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
		                              <th >用户手机号</th>
		                              <th >订单类型</th>
		                              <th >订单号</th>
		                              <th>订单总金额</th>
		                              <th >订单实际支付/退款金额</th>
		                             <!--  <th >交易号</th> -->
		                              <th>交易状态</th>
		                              <th>交易方式</th>
		                              <th >添加时间</th>
		                             <!--  <th>操作</th> -->
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
							            <td>${ item.mobile }</td>
							            <td>
							            	<orderTypeTag:orderTypeId orderTypeId="${ item.orderType }"/>
							            </td>
							            <td>
											${ item.orderNo }
							            </td>
							            <td align="center">
											${ item.orderMoney }
							            </td>
							            <td align="center" >
										 <c:choose>
     									  <c:when test="${ item.orderType !=3}">
             								-${ item.orderPay }
      									 </c:when>
									       <c:otherwise>
									            ${ item.orderPay }
									       </c:otherwise>
										</c:choose>
							            </td>
							           <%--  <td>
											${ item.tradeNo }
							            </td> --%>
							            <td>
											${ item.tradeStatus }
							            </td>
							            <td>

											<payTypeTag:payType payType="${ item.payType }" orderStatus="2"/>
							            </td>
							            <td>
							            	<timestampTag:timestamp patten="yyyy-MM-dd HH:mm:ss" t="${item.addTime * 1000}"/>
							            </td>
							           <%--  <td>
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
	        				<c:param name="urlAddress" value="/user/pay_detail"/>
	       			  </c:import>
                  </div>
              </div>
              <!-- page end-->
          </section> 
      </section>
      <!--main content end-->
      \
      <!--footer start-->
      <%@ include file="../shared/pageFooter.jsp"%>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <!--common script for all pages-->
    <%@ include file="../shared/importJs.jsp"%>

    <!--script for this page-->	
	<script src="<c:url value='/js/simi/account/list.js'/>"></script>

  </body>
</html>