<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: etienne
  Date: 15/11/2021
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Game</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<h1 class="centrerElement" id="titrePage">Polish Expressions</h1>
<div>
    <div class="disconnect">
        <form method="POST" action="${pageContext.request.contextPath}/disconnect">
            <input type="submit" class="boutonDisconnect" value="Quitter"/>
        </form>
    </div>
    <div style="display: flex; justify-content: center; margin: 0 100px;">
        <p>Bonjour, les réponses attendues sont des nombres, pouvant être décimaux, au centièmes près. Si le résultat n'a pas de décimal,
            veuillez entrer le résultat sans décimal. De plus, vous pouvez écrire ce même décimal avec un point ou une virgule.
            Enfin, si vous pensez qu'un calul n'est pas réalisable, veuillez entrer, en toutes lettres, le mot impossible.</p>
    </div>
    <div style="display: flex; justify-content: center; margin:0 100px;">
        <p style="color: red; margin-top: 10px;">${errorString}</p>
    </div>

</div>
<table>
    <tr>
        <form method="POST" action="${pageContext.request.contextPath}/game">
            <th>
            <th>
                <c:forEach var="i" begin="0" end="4">
                    <div style="margin-top: 20px">
                        <p>Calcul <c:out value="${i + 1}"/> : <c:out
                                value="${listExpressions.get(i).getRandomExpression()}"/></p>
                        <input type="insert" autoComplete="off" name="calcul<c:out value="${i}"/>" value="<c:out value="${selectedAnswers[i]}"/>">
                    </div>
                </c:forEach>
                <div style="margin-top: 30px">
                    <a class="boutonCancel" href="${pageContext.request.contextPath}/start">Retour</a>
                </div>
            </th>
            <th>
                <c:forEach var="i" begin="5" end="9">
                    <div style="margin-top: 20px">
                        <p>Calcul <c:out value="${i + 1}"/> : <c:out
                                value="${listExpressions.get(i).getRandomExpression()}"/></p>
                        <input type="insert" autoComplete="off" name="calcul<c:out value="${i}"/>" value="<c:out value="${selectedAnswers[i]}"/>">
                    </div>
                </c:forEach>
                <input class="boutonCancel" type="submit" value="Tout est bon ?">
            </th>
            </th>
        </form>
        <th>
            <c:if test="${!myScores.isEmpty()}">
                <h4>Vos meilleurs scores</h4>

                <c:forEach var="i" begin="0" end="${myScores.size() - 1}">
                    <div>
                        <p>Numéro <c:out value="${i + 1}"/> : <c:out value="${myScores.get(i)}"/>/10</p>
                    </div>
                </c:forEach>
            </c:if>
        </th>
    </tr>
</table>
</body>
</html>