<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!--taglib for this page  -->
<%@ taglib prefix="orderFromTag" uri="/WEB-INF/tags/orderFromName.tld" %>
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<%@ taglib prefix="payTypeNameTag" uri="/WEB-INF/tags/payTypeName.tld" %>
<%@ taglib prefix="serviceTypeTag" uri="/WEB-INF/tags/serviceTypeName.tld" %>
<%@ taglib prefix="rangTypeNameTag" uri="/WEB-INF/tags/rangTypeName.tld" %>
<%@ taglib prefix="couponTypeNameTag" uri="/WEB-INF/tags/couponTypeName.tld" %>

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
                          	充值后赠送-列表
                          	<div class="pull-right">
                          		<button onClick="btn_add('coupon/toRechargeCouponForm')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>      
                          </header>
                          
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          

                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
                              		  <th >描述</th>
                              		
                              		  <th >优惠券卡号</th>
		                              <th >优惠券密码</th>
		                              <th >优惠券金额</th>
		                              <th >通用类型</th>
                              		  <th >开始日期</th>
		                              <th >结束日期</th>
		                              <th >添加时间</th>
		                              <th>操作</th>
		                         
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
                              			<td>${ item.introduction }</td>
                              			<td>${ item.cardNo }</td>
							            <td>${ item.cardPasswd }</td>
							            <td>${ item.value }</td>
							             <td>
											<rangTypeNameTag:rangType rangType="${ item.rangType }"/>
							            </td>
							          
							          <td>
							            <fmt:formatDate var='formattedDate1' value='${item.fromDate}' type='both' pattern="yyyy-MM-dd" />
							        	   ${formattedDate1}
							            </td>
							            <td>
							            <fmt:formatDate var='formattedDate2' value='${item.toDate}' type='both' pattern="yyyy-MM-dd" />
										  ${formattedDate2}
							            </td>
							            
							            <td>
							            	<timestampTag:timestamp patten="yyyy-MM-dd" t="${item.addTime * 1000}"/>
							            </td>
							            <td>
							            	<button id="btn_update"  onClick="btn_update('coupon/toRechargeCouponForm?id=${ item.id }')" class="btn btn-primary btn-xs" title="修改"><i class="icon-pencil"></i></button>
	                                  		<button id="btn_del" onClick="btn_del('coupon/deleteByRechargeCouponId?id=${item.id}')" class="btn btn-danger btn-xs"  title="删除"><i class="icon-trash "></i></button>
							                <%-- <button id="btn_userList"  onClick="btn_update('coupon/toRechargeCouponUserList?id=${ item.id }')" class="btn btn-primary btn-xs" title="用户"><i class="icon-user"></i></button> --%>
							            </td>
                              </tr>
                              </c:forEach>
                              </tbody>
                          </table>

                          
                      </section>
                      
                      <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="contentModel"/>
	        				<c:param name="urlAddress" value="/coupon/list"/>
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
	

  </body>
</html>