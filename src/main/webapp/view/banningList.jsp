<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/banningList.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<jsp:include page="/view/adminActionsHeader.jsp" />

		<div id="banningContainer">

		<div style="padding-bottom:10px;">Page: ${currentPage}</div>
			<table id="banningListTable">

				<tr>
					<th>Reader</th>
					<th>Reason</th>
					<th>Expiration date</th>
					<th>Cancel</th>
				</tr>
				<c:forEach var="ban" items="${bans}">

					<tr>

						<td>${ban.reader.username}</td>

						<td>${ban.text}</td>

						<td><fmt:formatDate pattern = "yyyy-MM-dd" 
         					value = "${ban.expiryDate}" /></td>

						<c:url var="cancelRef" value="/admin/cancelBan">
							<c:param name="banId">${ban.id}</c:param>
						</c:url>

						<td><a
							onclick="return confirm('Are you sure you want to cancel this ban?');"
							class="blackRef" href="${cancelRef}">Cancel</a></td>

					</tr>

				</c:forEach>

			</table>

			<c:forEach begin="1" end="${bansPage.totalPages}" var="i">
				<c:url var="page" value="/admin/showBanningList">
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