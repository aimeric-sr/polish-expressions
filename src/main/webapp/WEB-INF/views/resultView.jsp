<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

<h1 class="titleElement">Résultats</h1>

<div style="display: flex; justify-content: flex-end; margin-top: 10px; margin-right: 10px">
    <form method="POST" action="${pageContext.request.contextPath}/disconnect">
        <input type="submit" class="boutonDisconnect" value="Quitter"/>
    </form>
</div>

<div id="actualScore">
    <h1>Votre score est de <c:out value="${actualScore}"/>/10</h1>
</div>

<table >
    <tr style="display: flex; justify-content: center;">
        <th>
            <div style="margin-top: 30px;" >
                <a class="boutonCancel" href="${pageContext.request.contextPath}/start">Retour</a>
                <a class="boutonCancel" href="${pageContext.request.contextPath}/game">Rejouer</a>
            </div>
        </th>
    </tr>
</table>

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
                            avec un score de <c:out value="${scores.get(i).getScore()}"/>/10</p>
                    </div>
                </c:forEach>
            </th>
            <c:if test="${scores.size() > 5}">
                <th>
                    <c:forEach var="i" begin="5" end="${scores.size() - 1}">
                        <div>
                            <p>Numéro <c:out value="${i + 1}"/> : <c:out value="${scores.get(i).getUserName()}"/>
                                avec un score de <c:out value="${scores.get(i).getScore()}"/>/10</p>
                        </div>
                    </c:forEach>
                </th>
            </c:if>
        </c:if>
    </tr>
</table>
</body>
</html>
