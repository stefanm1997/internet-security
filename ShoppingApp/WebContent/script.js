var article = [];
var orderedArticle = [];

function onLoad() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {

		if ((request.readyState == 4) && (request.status == 200)) {

			article = JSON.parse(request.responseText);

			var innerHTML = "<table border='1'>" + "<tr>" + "<th>Sifra</th>"
					+ "<th>Naziv</th>" + "<th>Proizvodjac</th>"
					+ "<th>Cijena</th>" + "<th>Kupi artikl</th>" + "</tr>";

			for (var i = 0; i < article.length; i++) {

				innerHTML += "<tr>";
				innerHTML += "<th width='15%'>" + article[i].code + "</th>";
				innerHTML += "<th width='15%'>" + article[i].name + "</th>";
				innerHTML += "<th width='15%'>" + article[i].producer + "</th>";
				innerHTML += "<th width='15%'>" + article[i].price + "</th>";
				innerHTML += "<th width='15%'>"
						+ "<button id='"
						+ article[i].code
						+ "'  onClick='addArticleToList(this.id)'>Kupi</button>"
						+ "</th>";
				innerHTML += "</tr>";
			}

			innerHTML += "</table><br><br><br><hr><br>"
					+ "<button onClick='sumOrderedArticle()'>Zavrsi kupovinu</button>"
					+ "<h3>Izabrani artikli:</h3><ol id='chosenArticle'></ol>";
			document.getElementById("articleTable").innerHTML = innerHTML;
		}

	};
	request.open("GET", "Servlet", true);
	request.send("");

}

function addArticleToList(code) {

	var producer = "";
	var name = "";
	var price = "";

	for (var i = 0; i < article.length; i++) {
		if (article[i].code == code) {
			orderedArticle[orderedArticle.length] = article[i];
			producer = article[i].producer;
			name = article[i].name;
			price = article[i].price;
			break;
		}

	}
	document.getElementById("chosenArticle").innerHTML += "<li>" + code + "---"
			+ name + "---" + producer + "---" + price + "</li>";
}

function sumOrderedArticle() {

	var sum = 0.0;
	for (var i = 0; i < orderedArticle.length; i++) {
		sum += orderedArticle[i].price;
	}
	alert("Ukupna cijena izabranih artikala je: " + sum);
	orderedArticle = [];
	document.getElementById("chosenArticle").innerHTML = "";
}