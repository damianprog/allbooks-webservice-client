<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/notifications.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<div id="notificationsContainer">
			<h2>Notifications</h2>
			<hr>

			<table>

				<tr>
					<th>Text</th>
					<th>Date</th>
					<th>Delete</th>
				</tr>
				<c:forEach var="notification" items="${notifications}">

					<tr>

						<td>${notification.text}</td>

						<td>${notification.date}</td>

						<c:url var="deleteRef" value="/loggedReader/deleteNotification">
							<c:param name="notificationId">${notification.id}</c:param>
						</c:url>

						<td><a
							onclick="return confirm('Are you sure you want to delete this notification?');"
							class="blackRef" href="${deleteRef}">Delete</a></td>

					</tr>

				</c:forEach>

			</table>
		</div>
	</div>

</body>

</html>