//下拉列表
		function clickHandler(e) {
			var v = document.getElementById(e);
			if (v.style.display == "block") {
				v.style.display = "none";
			} else {
				v.style.display = "block";
			}
		}

function getDate() {
	var date = new Date();
	var date1 = date.toLocaleString();
	var span = document.getElementById("times");
	span.innerHTML = date1;
}
setInterval("getDate()", 1000);

