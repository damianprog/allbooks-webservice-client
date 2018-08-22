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
			<h2>Add Friends</h2>
			<hr>

			<div id="findFriendDiv">
				Find friend by name<br>
				<form action="/friends/showAddFriends">
					<input id="searchBox" name="username" required="required">
					<input id="searchButton" type="submit" value="Search Members">
				</form>
			</div>

			<div id="searchedReader">

				<c:choose>
					<c:when test="${not empty searchedReader}">
						<div class="friendDiv">

						<c:url var="friendProfileRef" value="/profile/showProfile">
									<c:param name="readerId" value="${searchedReader.id}" />
								</c:url>

							<div class="friendPhoto">
								<a class="blackRef" href="${friendProfileRef}"><img
									src="data:image/jpeg;base64,${searchedReader.encodedProfilePhoto}"></a>
							</div>

							<div class="friendDetails">

								<a class="blackRef" href="${friendProfileRef}">${searchedReader.username}</a><br>
								${searchedReader.details.country} ${searchedReader.details.city}<br>

								<c:choose>
									<c:when test="${isItOtherReaderProfile == true}">
										<c:choose>
									<c:when test="${pending == false}">
										<c:choose>
											<c:when test="${(areTheyFriends == false)}">

												<c:url var="friendUrl" value="/profile/inviteFriend">
													<c:param name="recipentId" value="${searchedReader.id}" />
													<c:param name="pageName" value="addFriends" />
												</c:url>

												<a class="blackRef" href="${friendUrl}">Send friend's
													request</a>
											</c:when>
											<c:otherwise>
									You are friends!
								</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${isItSenderProfile == true}">
										Already sent you Friends request!
									</c:when>
											<c:otherwise>
										Friends request has been sent
									</c:otherwise>
										</c:choose>

									</c:otherwise>
								</c:choose>
									</c:when>
									<c:otherwise>
										This is you
									</c:otherwise>
								</c:choose>
							</div>

						</div>
					</c:when>
					<c:when test="${not empty notFound}">
						Nobody with this name has signed up on Allbooks.
					</c:when>
				</c:choose>

			</div>

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