<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Polish Expressions</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

<h1 class="titleElement">Veuillez vous inscrire/connecter pour pouvoir jouer</h1>

<div id="centeredElements">
    <a class="connexionButton" href="${pageContext.request.contextPath}/register">S'inscrire</a>
    <a class="connexionButton" href="${pageContext.request.contextPath}/login">Se connecter</a>
</div>

</body>
</html>
