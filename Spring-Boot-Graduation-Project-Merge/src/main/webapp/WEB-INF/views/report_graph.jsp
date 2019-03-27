<jsp:include page="/WEB-INF/templates/report_template.jsp">
	<jsp:param value="<div id='chartContainer'/>" name="content" />
</jsp:include>
<%
	String itemUnit = (String) request.getAttribute("itemUnit");
%>

<script type="text/javascript">
	$(function () {	 
		var updateInterval = 3000;

		function update() {
			$.get("reportpoints?eid=${eid}", function(data) {
				// update table information
				$('#count').html(data.count);
				$('#power').html(data.power.toFixed(2));
				$('#avg').html(data.avg.toFixed(2));
				$('#max').html(data.max.toFixed(2));
				$('#min').html(data.min.toFixed(2));
				var price = ${price};
				var unit = '<%=itemUnit%>';
				$('#total').html((price * data.power).toFixed(2) + " " + unit);
	
				var chart = new CanvasJS.Chart("chartContainer", {
					backgroundColor : "#f1f3f6",
					title : {
						text : "${eid}"
					},
					animationEnabled : false,
					legend : {
						verticalAlign : "center",
						horizontalAlign : "left",
						fontSize : 20,
						fontFamily : "Helvetica"
					},
					theme : "light2",
					data : [ {
						type : "pie",
						indexLabelFontFamily : "Garamond",
						indexLabelFontSize : 20,
						indexLabel : "{y} W",
						startAngle : -20,
						showInLegend : true,
						toolTipContent : "{legendText} / {y} Wh",
						dataPoints : data.dataPoints
					} ]
					});
				chart.render();
				// call update for updating information without refresh
				setTimeout(update, updateInterval);
			});
		};
		update();
	});
</script>