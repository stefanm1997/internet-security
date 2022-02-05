var article = [];

function onLoad() {

	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {

		if ((request.readyState == 4) && (request.status == 200)) {
			article = JSON.parse(request.responseText);

			var innerHTML = "<table border='1'>" + "<tr>" + "<th>Sifra</th>"
					+ "<th>Naziv</th>" + "<th>Proizvodjac</th>"
					+ "<th>Cijena</th>" + "<th>Brisi artikl</th>" + "</tr>";

			for (var i = 0; i < article.length; i++) {

				innerHTML += "<tr>";
				innerHTML += "<th width='15%'>" + article[i].code + "</th>";
				innerHTML += "<th width='15%'>" + article[i].name + "</th>";
				innerHTML += "<th width='15%'>" + article[i].producer + "</th>";
				innerHTML += "<th width='15%'>" + article[i].price + "</th>";
				innerHTML += "<th width='15%'><form method='POST' action='ServletRemove'>"
						+ "<input type='text' hidden='hidden' name='code' id='code' value='"
						+ article[i].code
						+ "'/>"
						+ "<input type='submit' name='brisi' value='Brisi'/></form>"
						+ "</th>";
				innerHTML += "</tr>";
			}

			innerHTML += "</table>";
			document.getElementById("itemTable").innerHTML = innerHTML;
		}

	};
	request.open("GET", "Servlet", true);
	request.send("");
}
