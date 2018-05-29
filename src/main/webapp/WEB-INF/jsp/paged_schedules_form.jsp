<%@ include file="tags.jsp" %>
 <h1><fmt:message key="page.schedules"/></h1>
  <c:forEach var="sc" items="${schedules}">
  <fmt:message key="master"/>: <c:out value="${sc.key.fullname}"/> <br/>
  <fmt:message key="page.schedules"/>: <br/>
  			<c:forEach var="scd" items="${sc.value}">
	  			 <ctg:datetag date="${scd.day }" locale="${sessionScope.lang }" />
	  			 <c:out value="${scd.startHour }"/> - <c:out value="${scd.endHour }"/>
	  			 <c:out value="${scd.freeHours.size() }"/> <fmt:message key="places"/>
	  			 <div style="color:green"> 
		  			 <c:forEach var="freeHour" items="${scd.freeHours}">
		  			 	<c:out value="${freeHour }"/>
		  			 </c:forEach>
	  			 </div>
	  			<br>
  		   </c:forEach>
  		<br/>
  		<br/>
</c:forEach>

<fmt:message key="page.pages"/>
<c:if test="${page > 0 }">
	<a href="<c:url value="/index?page=1"/>" >
		 &lt;&lt;
	</a>
	<a href="<c:url value="/index?page=${page}"/>" >
		 &lt;
	</a>
</c:if>
<c:out value="${page + 1}"/>
<c:if test="${page < totalPages - 1 }">
	<a href="<c:url value="/index?page=${page + 2}"/>" >
		 &gt;
	</a>
	<a href="<c:url value="/index?page=${totalPages}"/>" >
		 &gt;&gt;
	</a>
</c:if>