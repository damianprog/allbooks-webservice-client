<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<div style="padding-bottom:10px;">Page: ${currentPage}</div>
			<table>

				<tr>
					<th>Text</th>
					<th>Date</th>
					<th>Delete</th>
				</tr>
				<c:forEach var="notification" items="${notifications}">

					<tr>

						<td>${notification.text}</td>

						<td style="width:90px"><fmt:formatDate pattern = "yyyy-MM-dd" 
         					value = "${notification.date}" /></td>

						<c:url var="deleteRef" value="/loggedReader/deleteNotification">
							<c:param name="notificationId">${notification.id}</c:param>
						</c:url>

						<td><a
							onclick="return confirm('Are you sure you want to delete this notification?');"
							class="blackRef" href="${deleteRef}">Delete</a></td>

					</tr>

				</c:forEach>

			</table>
			
			<c:forEach begin="1" end="${notificationsPage.totalPages}" var="i">
				<c:url var="page" value="/loggedReader/showNotifications">
					<c:param name="page" value="${i}"></c:param>
				</c:url>
				<h3 id="pageNum">
					<a href="${page}">${i}</a>
				</h3>
			</c:forEach>
		</div>
	</div>

</body>

</html>