<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/friends.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<div id="mainContainer">
			<h2>Friends</h2>
			<hr>

			<c:choose>

				<c:when test="${empty friends}">
						You have no friends yet!
				</c:when>

				<c:otherwise>

					<c:forEach var="friend" items="${friends}">

						<div class="friendDiv">

							<c:url var="friendProfileRef" value="/profile/showProfile">
								<c:param name="readerId" value="${friend.id}" />
							</c:url>

							<div class="friendPhoto">
								<a class="blackRef" href="${friendProfileRef}"><img
									src="data:image/jpeg;base64,${friend.encodedProfilePhoto}"></a>
							</div>

							<div class="friendDetails">
								<a class="blackRef" href="${friendProfileRef}">${friend.username}</a><br>
								${friend.details.country} ${friend.details.city}
							</div>

							<div class="friendDelete">
								<form:form action="/profile/deleteFriends" method="DELETE">
									<input type="hidden" name="friendId" value="${friend.id}">
									<input type="hidden" name="pageName" value="friends">
									<input class="delete" type="submit" value="Delete"
										onclick="return confirm('Are you sure you want to delete this friend?');" />
								</form:form>
							</div>

						</div>

						<div style="clear: both"></div>

					</c:forEach>

				</c:otherwise>

			</c:choose>

		</div>

		<div id="rightContainer">
			<div class="smallHeaders">
				<a class="nonDecRef" href="/friends/showFriends">Friends</a>
			</div>
			<div class="smallHeaders">
				<a class="nonDecRef" href="/friends/showFriendsRequests">Friends
					Requests</a>
			</div>
			<div class="smallHeaders">
				<a class="nonDecRef" href="/friends/showAddFriends">Add
					Friends</a>
			</div>
			<div style="clear: both"></div>
			<hr>

			<div id="inviteFriendLinkDiv">
				<b>Invite friend link</b>
				<hr>
				<div>Send this link to your friends to connect with them.</div>
				<br>
				<div id="getLinkButton">
					<a class="nonDecRef" href="/friends/getInvitationLink">Get
						Link</a>
				</div>
			</div>

		</div>

	</div>

</body>

</html>