<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: etienne
  Date: 18/11/2021
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>r
    <meta charset="UTF-8">
    <title>Let's go</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

<h1 class="centrerElement" id="titrePage">Une petite partie de Polish Expressions ?</h1>

<div style="display: flex; justify-content: flex-end; margin-top: 10px; margin-right: 10px">
    <form method="POST" action="${pageContext.request.contextPath}/disconnect">
        <input type="submit" class="boutonDisconnect" value="Quitter"/>
    </form>
</div>

<div class="centrerElement" id="boutonJeu">
    <a href="/polish/game">C'est parti !</a>
</div>

<div style="display: flex; justify-content: center; margin-top: 30px">
    <h3>Les meilleurs scores</h3>
</div>


<table style="margin-top: 50px">
    <tr style="display: flex; justify-content: space-evenly">
        <c:if test="${!scores.isEmpty()}">
            <th>
                <c:forEach var="i" begin="0" end="${scores.size() < 5 ? scores.size() - 1 : 4}">
                    <div>
                        <p>Numéro <c:out value="${i + 1}"/> : <c:out value="${scores.get(i).getUserName()}"/>
                            avec un score de  <c:out value="${scores.get(i).getScore()}"/>/10</p>
                    </div>
                </c:forEach>
            </th>
            <c:if test="${scores.size() > 5}">
                <th>
                    <c:forEach var="i" begin="5" end="${scores.size() - 1}">
                        <div>
                            <p>Numéro <c:out value="${i + 1}"/> : <c:out value="${scores.get(i).getUserName()}"/>
                                avec un score de  <c:out value="${scores.get(i).getScore()}"/>/10</p>
                        </div>
                    </c:forEach>
                </th>
            </c:if>
        </c:if>
    </tr>
</table>
</body>
</html>
