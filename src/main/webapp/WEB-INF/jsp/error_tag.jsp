<%@ include file="tags.jsp" %>
<c:if test="${error != null}">
	<h4 style="color: red">
		<fmt:message key="${error}" />
	</h4>
</c:if>