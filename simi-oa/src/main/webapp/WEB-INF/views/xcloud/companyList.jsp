<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<!--taglib for this page  -->
<%@ taglib prefix="timestampTag" uri="/WEB-INF/tags/timestamp.tld" %>
<%@ taglib prefix="spiderPartnerStatusSelectTag" uri="/WEB-INF/tags/spiderPartnerStatusSelect.tld" %>
<%@ taglib prefix="partneCompanySizeSelectTag" uri="/WEB-INF/tags/partnerCompanySizeSelect.tld" %>
<%@ taglib prefix="partneIsCooperateSelectTag" uri="/WEB-INF/tags/partnerIsCooperateSelect.tld" %>
<%@ taglib prefix="spiderPartnerStatusTag" uri="/WEB-INF/tags/spiderPartnerStatus.tld"%>
<%@ taglib prefix="partneCompanySizeTag" uri="/WEB-INF/tags/partnerCompanySize.tld"%>
<%@ taglib prefix="partneIsCooperate" uri="/WEB-INF/tags/partnerIsCooperate.tld"%>
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
				<%-- <%@ include file="../common/partner/partnersSearch.jsp"%> --%>
              <div class="row">
                  <div class="col-lg-12">
                      <section class="panel">
                          <header class="panel-heading">
                          	企业列表
                           
                          </header>
                          <hr style="width: 100%; color: black; height: 1px; background-color:black;" />
                          

                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>
                                	  <th >公司名称</th>
		                              <th >公司简称</th>
		                              <th >公司规模</th>
		                              <th >创建人</th>
		                              <th >联系人</th>
		                              <th>邮箱</th>
		                              <th>注册时间</th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${contentModel.list}" var="item">
                              <tr>
	                                   <td>${ item.companyName }</td>
	                                   <td>${ item.shortName }</td>
							            <td><partneCompanySizeTag:companySize companySize="${ item.companySize }"/></td>
							            <td>${ item.userName }</td>
							            <td>${ item.linkMan }</td>
							             <td>${ item.email }</td>
							            <td>
							               <timestampTag:timestamp patten="yyyy-MM-dd HH:mm" t="${item.addTime * 1000}"/>
							            </td>
                              </tr>
                              </c:forEach>
                              </tbody>
                          </table>

                          
                      </section>
                      
                      <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="contentModel"/>
	        				<c:param name="urlAddress" value="/company/list"/>
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
	<script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/assets/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js'/>"></script>
    <!--script for this page-->	

  </body>
</html>