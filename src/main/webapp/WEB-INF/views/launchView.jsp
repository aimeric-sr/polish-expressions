<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Let's go</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

<h1 class="titleElement">Une petite partie de Polish Expressions ?</h1>

<div class="disconnect">
    <form method="POST" action="${pageContext.request.contextPath}/disconnect">
        <input class="boutonDisconnect" type="submit" value="Quitter"/>
    </form>
</div>

<div id="letsGo">
    <a class="connexionButton" href="${pageContext.request.contextPath}/game">C'est parti !</a>
</div>

<div class="bestScore">
    <h3>Les meilleurs scores</h3>
</div>


<table>
    <tr class="classementScores">
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
