<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../../resources/test.css">
<title> Student Registration Login </title>
  <style>
    .error {
      color: #ff0000;
    }

    .alert{
      width:50%;
      text-align:center;
      margin-left:70px;

    }


  </style>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
          </div>
        </div>
        <div style="color: red;">${error}</div><br>
        <form:form action="/login" modelAttribute="bean" class="login-form" method="post">
          <form:input  type="text" path="email" placeholder="Please enter your email"/>
          <form:input type="password" path="password" placeholder="Please enter your Password"/>
          <div class="tito">
            <a href="user_reg.jsp"> Forget Password </a>
            <a href="userRegister"> Sign Up </a>
          </div>
          <button type="submit">login</button>

        </form:form>
      </div>
    </div>
</body>
<style>
  .tito{
    display:flex;
    justify-content: space-between;
    margin-bottom: 5px;
    font-size: 12px;
  }
  a {
    text-decoration: none;
    color: #424242;
    transition: color 0.3s ease;
  }
  a:hover {
    color: #E53935;
  }
</style>

</html>