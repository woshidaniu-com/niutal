<html>
	<head>
		<style type="text/css">
.pbar .ui-progressbar-value {
	display: block !important
}

.pbar {
	background: url("<%=systemPath %>/images/bg.png") repeat-x scroll 50%
		top #EEEEEE;
	height: 2em;
	overflow: hidden;
	border: 1px solid #DDDDDD;
}

.percent {
	position: relative;
	text-align: right;
}

#bar {
	background: url("<%=systemPath %>/images/bar.png") repeat-x scroll 50%
		50% #F6A828;
	border: 1px solid #E78F08;
	color: #FFFFFF;
	font-weight: bold;
	height: 100%;
	display: block !important;
}
</style>
	</head>
	<body>
		<div id="progress"
			style="margin-top: 2px; margin-bottom: 2px; display: none;">
			<div style="width: 100%;">
				<div class="pbar">
					<div id="bar"
						style="display: none; width: 00.00%; text-align: right;">
						<p id="bl" style="color: #63B8FF; font-size: 1.5em;"></p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>