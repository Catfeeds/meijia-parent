<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.github.pagehelper.PageInfo"
    import="com.zrj.utils.PageUtil"
    import="com.zrj.oa.common.ConstantOa"
    import="org.apache.taglibs.standard.tag.common.core.UrlSupport"%>

<%
	String urlAddress=request.getParameter("urlAddress");
	String pageModelName= request.getParameter("pageModelName");
	Integer pageCount= ConstantOa.DEFAULT_PAGE_SIZE;
	if (request.getParameter("pageCount")!=null &&
	    !request.getParameter("pageCount").isEmpty()) {
		pageCount = Integer.valueOf(request.getParameter("pageCount"));
	}
%>

<div class="row">
<%
	if(pageModelName==null || pageModelName.isEmpty()){%>
		未获取到分页标识！
	<%}
	else if(urlAddress==null || urlAddress.isEmpty()){%>
		未获取到url地址！
	<%}
	else if(pageCount<10){%>
		分页数量不能小于10！
	<%}

	PageInfo pageInfo = (PageInfo)request.getAttribute(pageModelName);
	if(pageInfo==null){%>
		未获取到分页内容！
	<%}
	else{%>

		<div class="col-md-5 col-sm-12">
			<div class="dataTables_info">(每页显示<%=pageInfo.getPageSize() %>条，共 <%=pageInfo.getTotal() %> 条记录)</div>
		</div>

		<div class="col-md-7 col-sm-12">
			<div class="dataTables_paginate paging_bootstrap">
		   		<ul class="pagination">

		<%String queryString=request.getQueryString();
		urlAddress = UrlSupport.resolveUrl(urlAddress, null, pageContext);
		if (pageInfo.isHasPreviousPage()) {
            String preUrl = PageUtil.resolveUrl(urlAddress, queryString, pageInfo.getPageNum() - 1, null);%>
            <li class="prev">
				<a href="<%=preUrl%>">上一页</a>
			</li>
        <%}
		else{%>
			<li class="prev disabled">
				<span>上一页</span>
			</li>
		<%}
		if(pageInfo.getPages()<=pageCount){
        	for(int i=1;i<=pageInfo.getPages();i++){
        		if(i==pageInfo.getPageNum()){%>
        			<li class="active"><a href="#"><%=i %></a></li>
        		<%}
        		else{
        			String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, i, null);%>
                    <li><a href="<%=pageUrl%>"><%=i %></a></li>
                <%}
        	}
        }
		else{
        	for(int i=1;i<=pageCount;i++){
        		if(pageInfo.getPageNum()<=pageCount/2){
        			if(i==pageInfo.getPageNum()){%>
        				<li class="active"><a href="#"><%=i %></a></li>
        			<%}
        			else if(pageCount-i>2){
        				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, i, null);%>
                        <li><a href="<%=pageUrl%>"><%=i %></a></li>
    				<%}
        			else if(i==pageCount-2){%>
        				<span>...</span>
        			<%}
        			else{
    					int pageNo=pageInfo.getPages()-(pageCount-i);
    					String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                        <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
        			<%}
        		}
        		else if(pageInfo.getPages()-pageInfo.getPageNum()<pageCount/2){
        			if(i<3){
        				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, i, null);%>
                        <li><a href="<%=pageUrl%>"><%=i %></a></li>
        			<%}
        			else if(i==3){%>
        				<span>...</span>
        			<%}
        			else{
        				int pageNo=pageInfo.getPages()-(pageCount-i);
        				if(pageNo==pageInfo.getPageNum()){%>
        					<li class="active"><a href="#"><%=pageNo %></a></li>
        				<%}
        				else{
        					String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                            <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
        				<%}
        			}
        		}
        		else{
        			if(i<3){
        				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, i, null);%>
                        <li><a href="<%=pageUrl%>"><%=i %></a></li>
        			<%}
        			else if(i==3 || i==pageCount-2){%>
        				<span>...</span>
        			<%}
        			else if(i>pageCount-2){
        				int pageNo=pageInfo.getPages()-(pageCount-i);
        				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                        <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
        			<%}
        			else{
        				if(pageCount%2==0)
        				{
            				if(i==pageCount/2){%>
            					<li class="active"><a href="#"><%=pageInfo.getPageNum() %></a></li>
            				<%}
            				else if(i<pageCount/2){
            					int pageNo=pageInfo.getPageNum()-(pageCount/2-i);
                				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                                <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
            				<%}
            				else{
            					int pageNo=pageInfo.getPageNum()+(i-pageCount/2);
                				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                                <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
            				<%}
        				}
        				else{
        					if(i==(pageCount+1)/2){%>
            					<li class="active"><a href="#"><%=pageInfo.getPageNum() %></a></li>
            				<%}
        					else if(i<(pageCount+1)/2){
            					int pageNo=pageInfo.getPageNum()-((pageCount+1)/2-i);
                				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                                <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
            				<%}
            				else{
            					int pageNo=pageInfo.getPageNum()+(i-(pageCount+1)/2);
                				String pageUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);%>
                                <li><a href="<%=pageUrl%>"><%=pageNo %></a></li>
            				<%}
        				}
        			}
        		}
        	}
        }
		if (pageInfo.isHasNextPage()) {
            String nextUrl  = PageUtil.resolveUrl(urlAddress, queryString, pageInfo.getPageNum() + 1, null);%>
            <li class="next">
				<a href="<%=nextUrl %>">下一页</a>
			</li>
        <%}
        else{%>
        	<li class="next disabled">
				<span>下一页</span>
			</li>
        <%}%>
        		</ul>
			</div>
		</div>

	<%}%>
</div>

<%-- <script type="text/javascript">
function gotoSelectedPage()
{
    var x = document.getElementById("navigatorForm");
    //alert("Original action: " + x.action)
    x.submit();
}
</script>
<form action="Posts" method="get" id="navigatorForm">
    <a href="Posts?pageNumber=1">首页</a>
    <c:if test="${pageNumber>1}">
        <a href="Posts?pageNumber=${pageNumber-1}">上一页</a>
    </c:if>
    跳转到第 <select name="pageNumber" onchange="gotoSelectedPage();">
    <c:forEach begin="1" end="${totalPages}" step="1" var="pageIndex">
        <c:choose>
            <c:when test="${pageIndex eq pageNumber}">
                <option value="${pageIndex}" selected="selected">${pageIndex}</option>
            </c:when>
            <c:otherwise>
                <option value="${pageIndex}">${pageIndex}</option>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </select>页
    <c:if test="${pageNumber<totalPages}">
        <a href="Posts?pageNumber=${pageNumber+1}">下一页</a>
    </c:if>
    <a href="Posts?pageNumber=${totalPages}">末页</a>
</form>  --%>

<%-- <% String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString(); %> --%>

<%-- <c:set value="dmodel" var="aaa" />
<label>${model.getPageCount()}</label><br/>

<label>${requestScope[param.pageName].getPageCount()}</label><br/>


<c:forEach var="i" begin="1" end="10" step="1">
    <c:out value="${i}" />
    <br>
    <c:out value="${fn:replace(pageContext.request.queryString,'pageNo=','ddd')}"/>
    <br>
</c:forEach>


<label>${requestScope['javax.servlet.forward.request_uri']}</label><br/>
<label>${pageContext.request.queryString}</label><br/>
<label>${PageContract.DEFAULT_PAGE_SIZE}</label><br/>

<label><%=request.getScheme() %></label><br/>
<label><%=request.getServerName() %></label><br/>
<label><%=request.getRequestURI() %></label><br/>

<label><%=request.getContextPath() %></label><br/>
<label><%=request.getServletPath() %></label><br/>

<label><%=request.getQueryString() %></label><br/> --%>
<%-- <label><%=PageContract.DEFAULT_PAGE_SIZE %></label><br/> --%>

 <%-- <li>c:import标签 </li>
     <c:import url="http://www.baidu.com"></c:import><br>
     <p>
      <li>c:url,c:param</li><br>
     <c:url value="http://www.baidu.com" var="u">
        <c:param name="userId" value="zhangsan"></c:param>
        <c:param name="age" value="20"></c:param>
     </c:url>
     ${u }<br>
      <li>c:redirect</li><br>
     <c:redirect url="http://hao.360.cn/"></c:redirect>  --%>


