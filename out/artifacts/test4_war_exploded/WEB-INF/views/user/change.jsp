<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../layouts/header.jsp" %>
<div class="login-page">
  <div class="form">
    <div class="login">
      <div class="login-header">
        <form:form action="/changeEmailAndPassword" modelAttribute="user" class="login-form" method="post">
          <form:input  type="text" path="email" placeholder="Please enter your new email"/>
          <form:input type="password" path="password" placeholder="Please enter your new Password"/>

          <button type="submit">Change</button>

        </form:form>
      </div>
    </div>
  </div>
</div>

<%@ include file="../layouts/footer.jsp" %>