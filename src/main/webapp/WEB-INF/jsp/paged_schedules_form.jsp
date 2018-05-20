<%@ include file="tags.jsp" %>
 <h1><fmt:message key="page.schedules"/></h1>
  <c:forEach var="sc" items="${schedules}">
  <fmt:message key="master"/>: <c:out value="${sc.key.fullname}"/> <br/>
  <fmt:message key="page.schedules"/>: <br/>
  			<c:forEach var="scd" items="${sc.value}">
  			 <c:out value="${scd.day }"/>
  			 <c:out value="${scd.startHour }"/> - <c:out value="${scd.endHour }"/><br/> 
  		   </c:forEach>
  		<br/>
  		<br/>
</c:forEach>

<h1><fmt:message key="page.pages"/></h1>	
  <c:forEach var="i" begin="1" end="${totalPages}">
  	<a href="<c:url value="/index?page=${i}"/>" >
  		 <c:out value="${i}"/>
  	</a>
  </c:forEach>