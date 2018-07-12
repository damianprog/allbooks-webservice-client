<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Allbooks Review</title>
<link type="text/css" rel="stylesheet" href="/css/ban.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<jsp:include page="/view/adminActionsHeader.jsp" />

		<div id="banningContainer">

			<form:form action="ban" modelAttribute="ban" method="POST">

				<table>

					<tr>
						<td>Reader Username</td>
					</tr>

					<c:if test="${wrongUsername == true}">
						<tr>
							<td><span class="error">Wrong username</span></td>
						</tr>
					</c:if>
					<tr>
						<td class="tdBottomSpace"><input class="inputBox"
							name="readerUsername"></td>
					</tr>

					<tr>
						<td>Reason of banning</td>
					</tr>
					<tr>
						<td class="tdBottomSpace"><form:textarea path="text"
								id="reasonTextArea"></form:textarea></td>
					</tr>

					<tr>
						<td>Ban expiry date</td>
					</tr>
					<tr>
						<td class="tdBottomSpace"><form:input type="date"
								path="expiryDate" class="inputBox"></form:input></td>
					</tr>

				</table>

				<input id="banButton" type="submit" value="Ban">

			</form:form>

		</div>

	</div>
</body>



</html>