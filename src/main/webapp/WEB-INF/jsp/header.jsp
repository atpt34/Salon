<%@ include file="tags.jsp" %>

<div align="center" style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">
   
   <div align="left">
    <a href="<c:url value="/index" />" ><fmt:message key="page.index"/></a> 
    <form method="GET" action="${pageContext.request.contextPath}/changeLanguage">
         <select name="lang" size="1">
            <option value="en"> en </option>
            <option value="ua"> ua </option>
         </select>
         <input type="submit" value="<fmt:message key="command.set.language" />">
    </form>
    
    <fmt:message key="today"/>
    <ctg:datetag date="${today }" locale="${sessionScope.lang }" />
    
    </div>
    
    <c:choose>
    	<c:when test="${sessionScope.user.isPresent()}">
    		<c:set var="role" value="${sessionScope.user.get().role}"/>
    		<c:choose>
    			<c:when test="${ role == 'ADMIN'}">
					<a href="<c:url value="/admin_page"/>"><fmt:message key="page.admin"/></a>
    			</c:when> 
    			<c:when test="${ role == 'CLIENT'}">
    				<a href="<c:url value="/client_page"/>"><fmt:message key="page.client"/></a>
    			</c:when>
    			<c:when test="${ role == 'MASTER'}">
    				<a href="<c:url value="/master_page"/>"><fmt:message key="page.master"/></a>
    			</c:when>
    		</c:choose>
    		<a href="${pageContext.request.contextPath}/logout"><fmt:message key="page.logout"/></a>
    	</c:when>
    	<c:otherwise>
    		<a href="${pageContext.request.contextPath}/login_page"><fmt:message key="page.login"/></a>
    		| <a href="<c:url value="/register_page"/>"><fmt:message key="page.register"/></a>
    	</c:otherwise>
    </c:choose>
    
</div>