<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/templates/dr_template.jsp">
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

		var capacity = [];
		var drline = [];
		var power = [];
		var last = 0;

		$('#desc').html("${desc}");
		
		// change java timemillis to javascript date
		function changeTime(data) {
			if (data == "")
				return ;
			for (var i=0; i<data.length; i++) {
				var time = new Date(data[i].x);
				capacity.push({
					x : time,
					y : data[i].c
				});
				drline.push({
					x : time,
					y : data[i].c * 0.8
				});
				power.push({
					x : time,
					y : data[i].p
				});
			}
			// save last time for next update function
			last = data[data.length-1].x;
			var per = data[data.length-1].p / data[data.length-1].c * 100;
			// update percentage
			$('#percentage').html(per.toFixed(2)+' %');
			// if number of elements is more than dataLength, remove first element
			if (capacity.length > dataLength){
				capacity.shift();
				drline.shift();
				power.shift();
			}
		}

		// at first time, get 20 element from server
		$.get("drpoints?type=${type}&length=20&time="+last, function(data) {
			changeTime(data);
			var chart = new CanvasJS.Chart("chartContainer", {
				title : {
					text : "Comparison Chart",
				},
				toolTip: {
					shared: true
				},
				legend: {
					cursor: "pointer",
					verticalAlign: "top",
					horizontalAlign: "center",
					dockInsidePlotArea: true
				},
				data : [ {
					type : "line",
					name: "capacity",
					showInLegend: true,
					markerSize: 0,
					yValueFormatString: "0.00",
					dataPoints : capacity
				}, 
				{
					type : "line",
					name: "80% of capacity",
					showInLegend: true,
					markerSize: 0,
					yValueFormatString: "0.00",
					dataPoints : drline
				},
				{
					type : "area",
					name: "energy usage",
					showInLegend: true,
					markerSize: 0,
					yValueFormatString: "0.00",
					dataPoints : power
				}]
			});
			chart.render();
			// call update for updating information without refresh
			setTimeout(update, updateInterval);
		});

		function update() {
			// get an element from server
			$.get("drpoints?type=${type}&length=1&time="+last, function(data) {
				changeTime(data);
				var chart = new CanvasJS.Chart("chartContainer", {
					title : {
						text : "Comparison Chart",
					},
					toolTip: {
						shared: true
					},
					legend: {
						cursor: "pointer",
						verticalAlign: "top",
						horizontalAlign: "center",
						dockInsidePlotArea: true
					},
					data : [ {
						type : "line",
						name: "capacity",
						showInLegend: true,
						markerSize: 0,
						yValueFormatString: "0.00",
						dataPoints : capacity
					}, 
					{
						type : "line",
						name: "80% of capacity",
						showInLegend: true,
						markerSize: 0,
						yValueFormatString: "0.00",
						dataPoints : drline
					},
					{
						type : "area",
						name: "energy usage",
						showInLegend: true,
						markerSize: 0,
						yValueFormatString: "0.00",
						dataPoints : power
					}]
				});
				chart.render();
				// call update for updating information without refresh
				setTimeout(update, updateInterval);
			});
		}
	}
</script>