<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/favoriteGenres.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
	<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<div id="header">
			<h2>Follow Your Favorite Genres</h2>

			<div id="headerInfoDiv">We use your favorite genres to make
				better book recommendations and tailor what you see in your Updates
				feed.</div>
		</div>

		<div id="checkboxesDiv">
			
			<form action="saveFavoriteGenres" method="POST">
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="art">Art
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="biography">Biography
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="business">Business
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="children">Children's
			</div>
			</label>
			
			<div style="clear:both"></div>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="christian">Christian
			</div>
			</label>
	
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="classics">Classics
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="comics">Comics
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="cookbooks">Cookbooks
			</div>
			</label>

			<div style="clear:both"></div>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="ebooks">Ebooks
			</div>
			</label>
	
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="fantasy">Fantasy
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="fiction">Fiction
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="graphicNovels">Graphic Novels
			</div>
			</label>
			
			<div style="clear:both"></div>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="historicalFiction">Historical Fiction
			</div>
			</label>
	
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="history">History
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="horror">Horror
			</div>
			</label>
			
			<label>
			<div class="checkboxBlock">
				<input name="categoryChecked" type="checkbox" value="memoir">Memoir
			</div>
			</label>
			<div style="clear:both"></div>
			
			<input id="submit" type="submit" value="Save changes" />
			
			</form>
		</div>

	</div>

</body>

<script type="text/javascript">

$(document).ready(function(){
	
	var json = JSON.parse('${favoriteGenres}');
    $.each(json, function (index, item) {
    	$(":checkbox[value=" + item + "]").attr("checked","true");
    });
    
    if(${savedAlert == true})
		alert("Changes saved");
	
});

</script>

</html>