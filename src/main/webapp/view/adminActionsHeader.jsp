<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/adminActionsHeader.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">

		<div id="adminActionsHeaderContainer">

			<h2>Admin actions</h2>
			<table id="adminActionsTable">
			
				<tr>
				
					<td><a class="blackRef" href="/admin/showAddBook">Add book</a></td>
					<td><a class="blackRef" href="/admin/showBan">Banning</a></td>
					<td><a class="blackRef" href="/admin/showBanningList">Banning list</a></td>
				
				</tr>
			
			</table>
			<hr id="adminActionsHr">
		</div>
	</div>

</body>

</html>