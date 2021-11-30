<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Game</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-5.1.3-dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<h1 class="titleElement">Polish Expressions</h1>

<div class="disconnect">
    <form method="POST" action="${pageContext.request.contextPath}/disconnect">
        <input class="boutonDisconnect" type="submit" value="Quitter"/>
    </form>
</div>

<div class="centeredText">
    <p>Bonjour, les réponses attendues sont des nombres, pouvant être décimaux, au centièmes près. Si le résultat n'a
        pas de décimal,
        veuillez entrer le résultat sans décimal. De plus, vous pouvez écrire ce même décimal avec un point ou une
        virgule.
        Vous pouvez aussi vous déplacer entre les cases sélectionnées, en appuyant sur le bouton Tab et revenir sur la
        case d'avant en appuyant sur Shift + Tab.
        Enfin, si vous pensez qu'un calcul n'est pas réalisable, veuillez entrer, en toutes lettres, le mot
        impossible.

    </p>
</div>

<div class="centeredText">
    <p class="error">${errorString}</p>
</div>

<table>
    <tr class="classementScores">
        <form method="POST" action="${pageContext.request.contextPath}/game">
            <th>
                <c:forEach var="i" begin="0" end="4">
                    <div class="margin20">
                        <p>Calcul <c:out value="${i + 1}"/> : <c:out
                                value="${listExpressions.get(i).getRandomExpression()}"/></p>
                        <input type="insert" autoComplete="off" tabindex="<c:out value="${i+1}"/>"
                               name="calcul<c:out value="${i}"/>"
                               value="<c:out value="${selectedAnswers[i]}"/>">
                    </div>
                </c:forEach>
                <div class="margin20">
                    <a class="boutonCancel" tabindex="12" href="${pageContext.request.contextPath}/start">Retour</a>
                </div>
            </th>
            <th>
                <c:forEach var="i" begin="5" end="9">
                    <div class="margin20">
                        <p>Calcul <c:out value="${i + 1}"/> : <c:out
                                value="${listExpressions.get(i).getRandomExpression()}"/></p>
                        <input type="insert" autoComplete="off" tabindex="<c:out value="${i+1}"/>"
                               name="calcul<c:out value="${i}"/>"
                               value="<c:out value="${selectedAnswers[i]}"/>">
                    </div>
                </c:forEach>
                <input class="boutonCancel" type="submit" tabindex="11" value="Tout est bon ?">
            </th>
        </form>
        <th>
            <c:if test="${!myScores.isEmpty()}">
                <h4 class="margin2020">Vos meilleurs scores</h4>

                <c:forEach var="i" begin="0" end="${myScores.size() - 1}">
                    <div style="display: flex; justify-content: center">
                        <p>Numéro <c:out value="${i + 1}"/> : <c:out value="${myScores.get(i)}"/>/10</p>
                    </div>
                </c:forEach>
            </c:if>
        </th>
    </tr>
</table>
</body>
</html>