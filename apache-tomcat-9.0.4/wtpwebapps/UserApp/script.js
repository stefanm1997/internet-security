var users = [];

function onLoad() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {

		if ((request.readyState == 4) && (request.status == 200)) {

			users = JSON.parse(request.responseText);

			var innerHTML = "<table border='1'>" + "<tr>"
					+ "<th>Korisnicko ime</th>" + "<th>Uloga</th>"
					+ "<th>Brisi korisnika</th>" + "</tr>";

			for (var i = 0; i < users.length; i++) {

				innerHTML += "<tr>";
				innerHTML += "<th width='25%'>";
				innerHTML += "<input type='text' hidden='hidden' name='username' id='username' value='"
						+ users[i].username
						+ "'/>"
						+ users[i].username
						+ "</th>";
				innerHTML += "<th width='25%'>" + users[i].role + "</th>";
				innerHTML += "<th width='25%'><form method='POST' action='ServletRemove'>"
						+ "<input type='text' hidden='hidden' name='username' id='username' value='"
						+ users[i].username
						+ "'/>"
						+ "<input type='submit' name='brisi' value='Brisi'/></form>"
						+ "</th>";
				innerHTML += "";
				innerHTML += "</tr>";
			}

			innerHTML += "</table>";
			document.getElementById("usersList").innerHTML = innerHTML;

		}

	};
	request.open("GET", "Servlet", true);
	request.send("");

}