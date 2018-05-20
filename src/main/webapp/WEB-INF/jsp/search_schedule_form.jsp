<%@ include file="tags.jsp" %>
<form method="post" action="${pageContext.request.contextPath}/client/search_schedule">
    <fmt:message key="date"/>
    <input type="date" name="date" value="${date }"> |
    <fmt:message key="time"/>
    <select name="time" size="1">
        <c:forEach var="i" begin="8" end="20">
			<option value="${i}:00"> ${i}:00 </option>
		</c:forEach>
     </select>
   <input class="button" type="submit" value="<fmt:message key="page.searchSchedule"/>">
</form>