<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/templates/sub_template.jsp">
	<jsp:param value="<div id='chartContainer'/>" name="content" />
</jsp:include>

<script type="text/javascript"
	src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script type="text/javascript">
	window.onload = function() {

		var updateInterval = 3000;
		var dataLength = 20; // number of dataPoints visible at any point

		var dataPoints = [];
		var last = 0;

		// change java timemillis to javascript date
		function changeTime(data) {
			if (data == "")
				return dataPoints;
			for (var i=0; i<data.length; i++) {
				dataPoints.push({
					x : new Date(data[i].x),
					y : data[i].y
				});
			}
			last = data[data.length-1].x;
			$('#power').html(data[data.length-1].y.toFixed(2)+' ${itemUnit}');
			if (dataPoints.length > dataLength)
				dataPoints.shift();
			return dataPoints;
		}

		function update() {
			$.get("subpoints?eid=${eid}&deviceId=${deviceId}&length=1&time="+last, function(data) {
				var chart = new CanvasJS.Chart("chartContainer", {
					title : {
						text : "${deviceId} Chart",
					},
					data : [ {
						type : "area",
						dataPoints : changeTime(data)
					} ]
				});
				chart.render();
				// call update for updating information without refresh
				setTimeout(update, updateInterval);
			});
		}
		$.get("subpoints?eid=${eid}&deviceId=${deviceId}&length=20&time="+last, function(data) {
			var chart = new CanvasJS.Chart("chartContainer", {
				title : {
					text : "${deviceId} Chart",
				},
				data : [ {
					type : "area",
					dataPoints : changeTime(data)
				} ]
			});
			chart.render();
			// call update for updating information without refresh
			setTimeout(update, updateInterval);
		});
	}
</script>