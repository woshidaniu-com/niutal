function addFc(where, website) {
	var str = [];
	str.push('<div class="fcBox">');
	str.push('<a href="'+website+'" target="_blank" id="code"></a>');
	str.push('<a class="pj" href="' + website + '" target="_blank" >\u53cd \u9988</a>');
	str.push('<div id="code2"></div>');
	str.push('<div class="close">');
	str.push('<span class="close1"></span>');
	str.push('<span class="close2"></span>');
	str.push('</div>');
	str.push('</div>');
	$("body").append(str.join(""));
	$("#code").qrcode({
		render: "canvas", //也可以替换为table
		foreground: "#000000",
		background: "#FFF",
		text: website,
		width: 30,
		height: 30,
	})
	$("#code2").qrcode({
		render: "canvas", //也可以替换为table
		foreground: "#000000",
		background: "#FFF",
		text: website,
		width: 200,
		height: 200,
	})
	if(where == "right") {
		$(".fcBox").css({
			width: '60px',
			height: '66px',
			position: 'fixed',
			bottom: '100px',
			right: 0,
			border: '1px solid #acacac',
			//'border-radius': '4px',
			padding: '10px 0 5px',
			'box-sizing': 'border-box',
			'background-color': 'white',
			'z-index': '1000000000',
		});
		$(".fcBox #code2").css({
			width: '240px',
			height: '240px',
			position: 'absolute',
			top: '50%',
			left: '-250px',
			border: '1px solid #acacac',
			'border-radius': '4px',
			'-webkit-transform': 'translateY(-50%)',
			'-moz-transform': 'translateY(-50%)',
			'-ms-transform': 'translateY(-50%)',
			'-o-transform': 'translateY(-50%)',
			'transform': 'translateY(-50%)',
			display: 'none',
			'justify-content': 'center',
			'align-items': 'center',
			'background-color': 'white',
			'z-index': '10000000000',
		});
	} else {
		$(".fcBox").css({
			width: '60px',
			height: '66px',
			position: 'fixed',
			bottom: '100px',
			left: 0,
			border: '1px solid #acacac',
			'border-radius': '4px',
			padding: '10px 0 5px',
			'box-sizing': 'border-box',
			'background-color': 'white',
			'z-index': '1000000000',
		});
		$(".fcBox #code2").css({
			width: '240px',
			height: '240px',
			position: 'absolute',
			top: '50%',
			right: '-250px',
			border: '1px solid #acacac',
			'border-radius': '4px',
			'-webkit-transform': 'translateY(-50%)',
			'-moz-transform': 'translateY(-50%)',
			'-ms-transform': 'translateY(-50%)',
			'-o-transform': 'translateY(-50%)',
			'transform': 'translateY(-50%)',
			display: 'none',
			'justify-content': 'center',
			'align-items': 'center',
			'background-color': 'white',
			'z-index': '10000000000',
		});
	}
	$(".fcBox #code").css({
		margin: '0 auto',
		display: 'flex',
		height: '30px',
		'align-items': 'center',
		'justify-content': 'center',
	});
	$(".fcBox .pj").css({
		display: 'block',
		'text-align': 'center',
		color: '#3d9fd4',
		'font-size': '12px',
		'text-decoration': 'none',
		'margin-top': '5px',
		'line-height': '16px',
		'font-weight': 'bold'
	});
	$(".fcBox .close").css({
		width: '20px',
		height: '20px',
		border: '1px solid #acacac',
		'border-radius': '30px',
		display: 'flex',
		'align-items': 'center',
		'justify-content': 'center',
		'background-color': 'white',
		cursor: 'pointer',
		position: 'absolute',
		top: '0',
		//left: '100%',
		'-webkit-transform': 'translate(-50%,-50%)',
		'-moz-transform': 'translate(-50%,-50%)',
		'-ms-transform': 'translate(-50%,-50%)',
		'-o-transform': 'translate(-50%,-50%)',
		'transform': 'translate(-50%,-50%)',
	});
	$(".fcBox .close .close1").css({
		position: 'absolute',
		top: '50%',
		left: '50%',
		display: 'block',
		width: '10px',
		height: '3px',
		'background-color': '#7f7f7f',
		'-webkit-transform': 'translate(-50%,-50%) rotateZ(45deg)',
		'-moz-transform': 'translate(-50%,-50%) rotateZ(45deg)',
		'-ms-transform': 'translate(-50%,-50%) rotateZ(45deg)',
		'-o-transform': 'translate(-50%,-50%) rotateZ(45deg)',
		'transform': 'translate(-50%,-50%) rotateZ(45deg)',
	});
	$(".fcBox .close .close2").css({
		position: 'absolute',
		top: '50%',
		left: '50%',
		display: 'block',
		width: '10px',
		height: '3px',
		'background-color': '#7f7f7f',
		'-webkit-transform': 'translate(-50%,-50%) rotateZ(-45deg)',
		'-moz-transform': 'translate(-50%,-50%) rotateZ(-45deg)',
		'-ms-transform': 'translate(-50%,-50%) rotateZ(-45deg)',
		'-o-transform': 'translate(-50%,-50%) rotateZ(-45deg)',
		'transform': 'translate(-50%,-50%) rotateZ(-45deg)',
	});
	$(".fcBox .close").on("click", function() {
		$(".fcBox").hide();
	})
	$(".fcBox #code").on("mouseenter",function(){
		$(".fcBox #code2").css("display","flex");
		$(".fcBox #code").on("mouseleave",function(){
			$(".fcBox #code2").css("display","none");
		})
	})
	
}