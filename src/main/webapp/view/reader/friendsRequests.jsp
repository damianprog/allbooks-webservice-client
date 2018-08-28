<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/reader/friends.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<div id="mainContainer">
			<h2>Friends Requests</h2>
			<hr>

			<c:choose>
				<c:when test="${empty friendsRequests}">
					You have no friends requests
				</c:when>
				<c:otherwise>
					<c:forEach var="pending" items="${friendsRequests}">
						<div class="pendingDiv">
							<c:url var="senderProfile" value="/profile/showProfile">
								<c:param name="readerId" value="${pending.sender.id}" />
							</c:url>
							<a class="blackRef" href="${senderProfile}">${pending.sender.username}</a>
							Has sent you a friends request!
							<form method="POST" action="/profile/acceptOrAbort">
								<select name="acceptOrAbort">
									<option value="accept">Accept</option>
									<option value="abort">Abort</option>
								</select> <input type="hidden" name="senderId"
									value="${pending.sender.id}"> <input type="hidden"
									name="pendingId" value="${pending.id}">
									<input type="hidden" name="recipentId" value="${sessionScope.readerId}"/> <input
									type="hidden" name="pageName" value="friendsRequests">
								<input type="submit" value="Submit" />
							</form>
						</div>
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
			<div class="smallHeaders"><a class="nonDecRef" href="/friends/showAddFriends">Add Friends</a></div>
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