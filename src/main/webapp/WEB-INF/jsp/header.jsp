<%@ include file="tags.jsp" %>
<div align="center" style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">
   
    <a href="<c:url value="/index" />" ><fmt:message key="page.index"/></a> 
    <form method="GET" action="${pageContext.request.contextPath}/changeLanguage">
         <select name="lang" size="1">
            <option value="en"> en </option>
            <option value="ua"> ua </option>
         </select>
         <input type="submit" value="<fmt:message key="command.set.language" />">
    </form>
    
    <a href="${pageContext.request.contextPath}/login_page"><fmt:message key="page.login"/></a>
    | <a href="${pageContext.request.contextPath}/logout"><fmt:message key="page.logout"/></a>
    | <a href="<c:url value="/register_page"/>"><fmt:message key="page.register"/></a>
    
    | <a href="<c:url value="/admin_page"/>"><fmt:message key="page.admin"/></a>
    | <a href="<c:url value="/client_page"/>"><fmt:message key="page.client"/></a>
    | <a href="<c:url value="/master_page"/>"><fmt:message key="page.master"/></a>
    
</div>