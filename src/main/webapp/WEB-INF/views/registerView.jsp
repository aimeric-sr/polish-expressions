<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

<h1 class="centrerElement" id="titrePage">Inscription</h1>

<div style="display: flex; justify-content: center; margin-top: 200px;">
    <form method="POST" action="${pageContext.request.contextPath}/register">
        <table border="0">
            <tr>
                <td>User Name</td>
                <td><input type="text" name="userName" value="${user.userName}"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="text" name="password" value="${user.password}"/></td>
            </tr>
            <tr>
                <td>Remember me</td>
                <td><input type="checkbox" name="rememberMe" value="Y"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <a class="boutonLogin" href="${pageContext.request.contextPath}/">Annuler</a>
                    <input type="submit" class="boutonLogin" value="S'inscrire"/>
                </td>
            </tr>
        </table>

        <p style="color: red;">${errorString}</p>
    </form>
</div>

</body>
</html>
