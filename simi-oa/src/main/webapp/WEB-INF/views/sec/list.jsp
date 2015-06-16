<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

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
                          	秘书管理
                          	
                          	<div class="pull-right">
                          		<button onClick="btn_add('/sec/listForm?id=0')" class="btn btn-primary" type="button"><i class="icon-expand-alt"></i>新增</button>
                    		</div>      
                          </header>
                         
                          

                          <table class="table table-striped table-advance table-hover">
                              <thead>
                              <tr>    

                                      <th >姓名</th>
		                              <th >手机号</th>
		                              <th >昵称</th>
		                              <th >出生日期</th>
		                              <th >头像</th>
		                              <th>所在城市</th>
		                              <th>状态</th>
                                      <th >添加时间</th>
		                              <th>更新时间</th>
		                              <th>操作</th>

                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${secModel.list}" var="item">
                              <tr>

                                  	    <td>${ item.name}</td>
							            <td>${ item.mobile}</td>
							            <td>${ item.nickName}</td>
							            <td><fmt:formatDate value="${ item.birthDay}" pattern="yyyy-MM-dd" /></td>
							            <td><img src="${ item.headImg}"/></td>
							            <td>${ item.cityId}</td>
							            <td>${ item.status}</td>
							            <td>${ item.addTime}</td>
							            <td>${ item.updateTime}</td>							         							            
	                                  	<td>	                                   
		                                    <button id="btn_del" onClick="btn_del('/sec/delete/${item.id}')" class="btn btn-danger btn-xs"  title="删除"><i class="icon-trash "></i></button>
	                                  	</td>
                              </tr>
                              </c:forEach>
                              </tbody>
                          </table>

                          
                      </section>
                      
                      <c:import url = "../shared/paging.jsp">
	        				<c:param name="pageModelName" value="secModel"/>
	        				<c:param name="urlAddress" value="/sec/list"/>
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
	<script src="<c:url value='/js/simi/account/list.js'/>"></script>

  </body>
</html>