<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

<h1 class="titleElement">Connexion</h1>

<div id="centeredElements">
    <form method="POST" action="${pageContext.request.contextPath}/login">
        <table >
            <tr>
                <td>Nom d'utilisateur</td>
                <td>
                    <input type="text" name="userName" value="${user.userName}"/>
                </td>
            </tr>
            <tr>
                <td>Mot de passe</td>
                <td><input type="password" name="password" value="${user.password}"/></td>
            </tr>
        </table>
        <table class="centeredAlign">
        <tr>
            <td>Se souvenir de moi</td>
            <td><input type="checkbox" name="rememberMe" value="Y"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <a class="loginButton" href="${pageContext.request.contextPath}/">Annuler</a>
                <input type="submit" class="loginButton" value="Se connecter"/>
            </td>
        </tr>
        </table>
        <p class="error">${errorString}</p>
    </form>
</div>
</body>
</html>
