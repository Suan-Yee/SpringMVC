<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2/20/2024
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../layouts/header.jsp" %>
<div class="login-page">
<div class="form">
<div class="login">
<div class="login-header">
<form:form action="/checkEmail" modelAttribute="user" class="login-form" method="post">
    <form:input  type="text" path="email" placeholder="Please enter your email"/>
    <form:input type="password" path="password" placeholder="Please enter your Password"/>

    <button type="submit">Submit</button>

</form:form>
</div>
</div>
</div>
</div>

<%@ include file="../layouts/footer.jsp" %>