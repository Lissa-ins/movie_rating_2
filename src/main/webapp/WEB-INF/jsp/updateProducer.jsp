<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>

<head>
  <title>КиноTOP</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/normalize.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style2.css">
  <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
  <script src="https://use.fontawesome.com/0ca06f29a6.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/jquery-3.4.1.js"></script>
</head>

<body>

<header role="banner">
  <div class="container">
    <div class="brand">КиноTOP &#8657</div>
    <nav>
      <ul class="menu">
        <sec:authorize access="hasRole('ROLE_ADMIN')"><li><a href="/management">Управление</a></li></li></sec:authorize>
        <li><a href="/">Главная</a></li>
        <li><a href="#">Актеры</a></li>
        <li><a href="${pageContext.request.contextPath}/user/allProducers">Продюсеры</a></li>
        <sec:authorize access="isAuthenticated()">
          <li><a href="${pageContext.request.contextPath}/user/bookmarkPage/${pageContext.request.userPrincipal.principal.id}">Закладки</a></li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
          <li><a href="/login" class=" svalokan-big openmodal"> Войти</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
          <li><a href="/logout" > Выйти</a></li>
          <li><a href="/">Привет, ${pageContext.request.userPrincipal.principal.username}  </a></li>
        </sec:authorize>
      </ul>
    </nav>
  </div>
</header>

<div class="jumbotron">
  <div class="container">
    <h1>Films</h1>
  </div>
</div>



<div class="blog">
  <div class="container">
    <div class="post">


      <div class="form_registration">
        <%--@elvariable id="producerForm" type=""--%>
        <form:form method="POST" modelAttribute="producerForm">
          <div class="doveacko-gangeroun">
            <h2>Измение  Режисера</h2>
          </div>

          <div class="davasgu-kevanud">
            <form:input type="text" path="name" value="${oneProducer.name}"
                        autofocus="true" />
            <form:errors path="name"/>
              ${nameError}</br>

            <form:input type="text" path="surname"  value="${oneProducer.surname}"
                        autofocus="true"/>
            <form:errors path="surname"/>
              ${surnameError}</br>

            <form:input type="text" path="country"  value="${oneProducer.country}"
                        autofocus="true"/>
            <form:errors path="country"/>
              ${countryError}</br>

            <form:input type="text" path="biography"  value="${oneProducer.biography}"
                        autofocus="true"/>
            <form:errors path="biography"/>
              ${biographyError}</br>


            <form:input type="text" path="photoPath" value="${oneProducer.photoPath}"
                        autofocus="true"/>
            <form:errors path="photoPath"/>
              ${photoPathError}
          </div>
          <button type="submit" class="dakisvan">Изменить</button>
          <h4><a href="/management">Назад</a></h4>

        </form:form>
      </div>


    </div>
  </div>
</div>


<footer>
  <div class="container">
    <div class="brand">Project Name</div>
    <nav>
      <ul class="menu">
        <li><a href="#">About</a></li>
        <li><a href="#">Team</a></li>
        <li><a href="#">News</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
    </nav>
    <p class="copyright">© 2020 BSUIR</p>
  </div>
</footer>

</body>
</html>