$(function() {

	const fontAttr = ["face", "size"];

	const elTag = ["i", "u", "b"];

	$('.edit-block .text-module').draggable({
		cursor: 'move',
		containment: '.edit-block',
		drag: function(event, ui) {
			var _position = ui.helper.find('.currentPosition');
			$('#x-position').val(parseInt(ui.position.left));
			$('#y-position').val(parseInt(ui.position.top));
		},
		stop: function(event, ui) {
		}
	});

	if($('#template-input').val() == ""){
		$('#template-input').focus();
	}
	//调用tooltip提示工具
	$("[data-toggle='tooltip']").tooltip();

	//调用富文本插件
	bkLib.onDomLoaded(function(){nicEditors.allTextAreas()});

	//设置编辑区域高度
	setHeight();

	$(window).resize(function() {
		setHeight();
	});

	function setHeight() {
		var windowHeight = $(window).height();
		var _height = windowHeight - $('.top-area').height();
		$('.edit-area').height(_height);
		$('.edit-area .demo').height(_height);
		$('.ef-ruler .left').height(_height - 19);
		//调用标尺插件
		$('.edit-area .demo').ruler();
	}

	//数据源以及控件拖入编辑区域
	$('.edit-block').sortable({
		cursor: 'move',
		start: function(event, ui) {},
		stop: function(event, ui) {
			var left = parseInt(ui.offset.left);
			var top = parseInt(ui.offset.top);
			var containerWidth = parseInt($('.edit-area').width());
			var editWidth = parseInt($('.edit-block').width());
			var padding = 0.5 * (containerWidth - editWidth);
			ui.item.css({
				'left': left - 280,
				'top': top - 100,
				'color': '#fff'
			});

			var _position = ui.item.find('.currentPosition');
			var _left = ui.item.css('left');
			var _top = ui.item.css('top');
			if(!ui.item.find(".field").hasClass("free")){
				var _lxhtml = ui.item.find("input[name='lx']").clone();
				var _dmhtml = ui.item.find("input[name='dm']").clone();
				ui.item.find("input[name='lx']").remove();
				ui.item.find("input[name='dm']").remove();
				ui.item.addClass("free");
				ui.item.find(".data").wrap('<span class="field free"></span>');
				ui.item.find(".data").before('<font class="control-text"></font>');
				ui.item.find(".data").after('<font class="control-text"></font>');
				ui.item.find(".data").after(_dmhtml);
				ui.item.find(".data").after(_lxhtml);
			}
			addShade();
		}
	});

	$('.left-area .text-module').draggable({
		helper: 'clone',
		connectToSortable: '.edit-block,.edit-block .text-module',
		cursor: 'move',
		drag: function(event, ui) {
			//ui.helper.width(60);
		},
		stop: function(event, ui) {
			$('.edit-block .text-module').sortable({
				cancel: 'span,div',
				placeholder: "ui-state-highlight",
				beforeStop: function(event, ui) {},
				stop: function(event, ui) {
					if($(this).hasClass('free')) {
						//自由文本里拖入元素
						ui.item.css({
							'position': 'relative'
						});
						if(ui.item.find('.field.free').size() > 0) {
							var _text = ui.item.find('.field.free').text();
							$(this).find('.field.free').append(_text);
						} else {
							var _html = ui.item.find('.field').clone();
							$(this).find('.field.free').append(_html);
							if(ui.item.find("input[name='lx']") != "undefined" && ui.item.find("input[name='lx']") != undefined){
								$(this).find('.field.free').append("<input type='hidden' name='lx' value='" + ui.item.find("input[name='lx']").val() + "'/>")
							}
							if(ui.item.find("input[name='dm']") != "undefined" && ui.item.find("input[name='dm']") != undefined){
								$(this).find('.field.free').append("<input type='hidden' name='dm' value='" + ui.item.find("input[name='dm']").val() + "'/>")
							}
							$(this).find('.control-text').remove();
							$(this).find('.field.data').each(function() {
								$(this).before('<font class="control-text"></font>');
								$(this).after('<font class="control-text"></font>');
								addShade();
							});
						}
						ui.item.remove();
					} else {
						//数据源里拖入元素
						var free = ui.item.find('.field.free');
						var data = ui.item.find('.field.data');
						var freeText = free.text();
						$(this).find('>.currentPosition').after('<span class="field free"></span>');
						$(this).addClass('free').removeClass('data');

						var oldData = $(this).find('>.data');
						var createFree = $(this).find('.field.free');
						createFree.append(oldData);
						if(free.size() > 0) {
							//数据源里拖入自由文本
							createFree.append(freeText);
							ui.item.remove();
						} else {
							var _html = ui.item.find('.field').clone();
							createFree.append(_html);
							if(ui.item.find("input[name='lx']") != "undefined" && ui.item.find("input[name='lx']") != undefined){
								createFree.find('.field.free').append("<input type='hidden' name='lx' value='" + ui.item.find("input[name='lx']").val() + "'/>")
							}
							if(ui.item.find("input[name='dm']") != "undefined" && ui.item.find("input[name='dm']") != undefined){
								createFree.find('.field.free').append("<input type='hidden' name='dm' value='" + ui.item.find("input[name='dm']").val() + "'/>")
							}
							ui.item.remove();
						}
						$(this).find('.field.data').each(function() {
							$(this).before('<font class="control-text"></font>');
							$(this).after('<font class="control-text"></font>');
							addShade();
						});
					}
				}
			});
			$('.edit-block .text-module').draggable({
				cursor: 'move',
				containment: '.edit-block',
				drag: function(event, ui) {
					var _position = ui.helper.find('.currentPosition');
					$('#x-position').val(parseInt(ui.position.left));
					$('#y-position').val(parseInt(ui.position.top));

				},
				stop: function(event, ui) {
				}
			});
		}
	});

	//键盘上下左右移动字段
	$(document).bind("keyup", "del", function(ev) {

		$('.edit-block .text-module.in-edit').remove();
		return false;

	}).bind("keyup", "up", function(ev) {

		var obj = $('.edit-block .text-module.in-edit');
		var top = Number(obj.css('top').substring(0, obj.css('top').indexOf('px')));
		if(top > 5) {
			obj.css('top', (top - 5) + 'px');
		}
		return false;

	}).bind("keyup", "down", function(ev) {

		var obj = $('.edit-block .text-module.in-edit');
		var contentHeight = Number($('.edit-block').height());
		var thisHeight = Number(obj.outerHeight());
		var top = Number(obj.css('top').substring(0, obj.css('top').indexOf('px')));
		var areaHeight = contentHeight - thisHeight - 3;

		if(top < areaHeight) {
			obj.css('top', (top + 5) + 'px');
		}
		return false;

	}).bind("keyup", "left", function(ev) {

		var obj = $('.edit-block .text-module.in-edit');
		var left = Number(obj.css('left').substring(0, obj.css('left').indexOf('px')));
		if(left > 5) {
			obj.css('left', (left - 5) + 'px');
		}
		return false;

	}).bind("keyup", "right", function(ev) {

		var obj = $('.edit-block .text-module.in-edit');
		var contentWidth = Number($('.edit-block').width());
		var thisWidth = Number(obj.outerWidth());
		var left = Number(obj.css('left').substring(0, obj.css('left').indexOf('px')));
		var areaWidth = contentWidth - thisWidth - 3;
		if(left < areaWidth) {
			obj.css('left', (left + 5) + 'px');
		}
		return false;

	});

	var formWidth, formHeight;
	$(document).off('blur', '#formWidth').on('blur', '#formWidth', function() {

		//设置表单宽度
		var val = $.trim($(this).val());
		if(!isNaN(val)){
			$('.edit-block').width(val + 'px');
		}else{
			$('.edit-block').outerWidth(val);
			$('.edit-block').width($('.edit-block').css("width"));
		}
		$('.edit-area .text-module').each(function() {
			var left = Number($(this).css('left').substring(0, $(this).css('left').indexOf('px')));
			var top = Number($(this).css('top').substring(0, $(this).css('top').indexOf('px')));
			var width = Number($(this).width());
			var height = Number($(this).height());

			if((left + width + 3) > parseInt(val)){
				$(this).css('left', '0');
			}
		});

	}).off('blur', '#formHeight').on('blur', '#formHeight', function() {

		//设置表单高度
		var val = $.trim($(this).val());
		if(!isNaN(val)){
			$('.edit-block').height(val + 'px');
		}else{
			$('.edit-block').outerHeight(val);
			$('.edit-block').height($('.edit-block').css("height"));
		}
		$('.edit-area .text-module').each(function() {
			var left = Number($(this).css('left').substring(0, $(this).css('left').indexOf('px')));
			var top = Number($(this).css('top').substring(0, $(this).css('top').indexOf('px')));
			var width = Number($(this).width());
			var height = Number($(this).height());
			if((top + height + 3) > parseInt(val)) {
				$(this).css('top', '0');
				var _left = $(this).css('left');
				var _top = $(this).css('top');
			}
		});

	}).off('click', '.edit-block .text-module').on('click', '.edit-block .text-module', function() {

		//单击删除
		$('.edit-block .text-module').removeClass('in-edit');
		if($(this).find('.nicEdit-main').size() == 0) {
			$(this).addClass('in-edit');
		}

	}).off('click', '.nicEdit-pane>div').on('click', '.nicEdit-pane>div', function() {
		let _font = $(this).find("font").clone();
		addFontStyle(_font);
		return false;

	}).off('nicEdit-Extend-Click', '.nicEdit-button').on('nicEdit-Extend-Click', '.nicEdit-button', function() {

	}).off('dblclick', '.edit-block .text-module').on('dblclick', '.edit-block .text-module', function() {

		if($('.edit-shade').size() > 0){
			$('.edit-shade').trigger("click");
		}
		var _this = $(this);
		if(_this.find('.nicEdit-main').size() == 0) {
			var maxWidth, maxHeight, xPosition, yPosition;

			maxWidth = _this.css('max-width').substring(0, _this.css('max-width').indexOf('px'));
			maxHeight = _this.css('max-height').substring(0, _this.css('max-height').indexOf('px'));
			_this.addClass('active');
			_this.removeClass('in-edit');

			//调用富文本插件
			var editArea, dataStr;
			try {
				//对数据源进行编辑
				var _id = 'editArea' + new Date().getTime();
				_this.find('>.field').hide();
				var oldHtml;
				var free = _this.find('.field.free');
				var data = free.find('.field.data');
				var index = 0;
				var htmlArr = [];
				if(data.size() > 0) {
					//设置data不可修改
					data.each(function() {
						index++;
						$(this).attr('data-index', 'data-' + index);
						var dataHtml = $(this).html();
						htmlArr.push(dataHtml);
					});
					oldHtml = free.html();
				} else {
					oldHtml = _this.find('.field').html();
				}
				if((_this.find('.free')).find('.data').size() > 0) {
					_this.append('<textarea name="area1" cols="40" id="' + _id + '">' + oldHtml + '</textarea>');
				} else if(_this.find('.data').size() == 0) {
					_this.append('<textarea name="area1" cols="40" id="' + _id + '">' + oldHtml + '</textarea>');
				} else {
					//仅仅单个数据源
					_this.append('<textarea name="area1" cols="40" id="' + _id + '"><font class="control-text"></font><span class="field data">' + oldHtml + '</span><font class="control-text"></font></textarea>');
				}
				editArea = new nicEditor({
					fullPanel: false,
					hr: false
				}).panelInstance(_id);

			} catch(e) {
				//TODO handle the exception
			}

			$('.nicEdit-main').keyup(function(event) {
				var editable = true;
				$(this).find(".field.data").each(function() {
					if($(this).is(":focus")) {

					}
				});
			});

			//扩展富文本增加最大高度最大宽度以及坐标
			let setting = new Array();
			setting.push('<div class="fl">');
			setting.push('	<input type="text" name="" class="maxWidth" value="" placeholder="最大宽度"/>');
			setting.push('</div>');
			setting.push('<div class="fl">');
			setting.push('	<input type="text" name="" class="maxHeight" value="" placeholder="最大高度"/>');
			setting.push('</div>');
			setting.push('<div class="fl">');
			setting.push('	<input type="text" name="" class="x_postion" id="" value="" placeholder="横坐标"/>');
			setting.push('</div>');
			setting.push('<div class="fl">');
			setting.push('	<input type="text" name="" class="y_postion" id="" value="" placeholder="纵坐标"/>');
			setting.push('</div>');

			$('.nicEdit-panel').append(setting.join(""));

			_this.css({
				'max-width': 'auto',
				'max-height': 'auto'
			});

			var xPosition = parseInt(_this.css('left').substring(0, _this.css('left').indexOf('px')));
			var yPosition = parseInt(_this.css('top').substring(0, _this.css('top').indexOf('px')));

			$('.maxWidth').val(maxWidth);
			$('.maxHeight').val(maxHeight);
			$('.x_postion').val(xPosition);
			$('.y_postion').val(yPosition);

			$('.edit-block .text-module').draggable('disable');
			$('.edit-block').sortable('disable');

			$('.edit-block').append('<div class="edit-shade"></div>');

			$('.maxWidth').blur(function() {
				maxWidth = $(this).val();
			});
			$('.maxHeight').blur(function() {
				maxHeight = $(this).val();
			});
			$('.x_postion').blur(function() {
				xPosition = $(this).val();
			});
			$('.y_postion').blur(function() {
				yPosition = $(this).val();
			});

			//编辑完成
			$('.edit-shade').click(function() {
				//将编辑所得内容返回到页面上

				initElHtml($(".nicEdit-main"));

				var _newHtml = $(".nicEdit-main").html();

				if((_this.find('.free')).find('.data').size() == 0) {
					_this.removeClass('data').addClass('free');
					_this.find('.field').removeClass('data').addClass('free');
					_this.find('.field').html(_newHtml);
				} else {
					_this.find('.field').html(_newHtml);
				}

				//移除富文本
				editArea.removeInstance(_id);

				//显示内容
				_this.css({
					'max-width': maxWidth + 'px',
					'max-height': maxHeight + 'px',
					'left': xPosition + 'px',
					'top': yPosition + 'px'
				});
				if("" != maxHeight && "" != maxWidth){
					_this.find('.field').css({
						'width': maxWidth + 'px',
						'height': maxHeight + 'px'
					});
				}
				_this.find('.field').show();
				_this.find('textarea').remove();
				_this.removeClass('active');

				//启用拖拽事件
				$('.edit-block .text-module').draggable('enable');
				$('.edit-block').sortable('enable');

				//移除遮罩
				$(this).remove();
			});
		}

		$(".nicEdit-main").trigger("click");

	}).off('click', '.nicEdit-main .field.data').on('click', '.nicEdit-main .field.data', function() {

		//数据源获取焦点
		setTimeout(function() {
			$(this).next('font').focus();
		}, 200);

	}).off('click', '.nicEdit-main').on('click', '.nicEdit-main', function() {

		//数据源获取焦点
		$(this).trigger("select");

	}).off('click', '#clear_btn').on('click', '#clear_btn', function() {

		//清空
		$('.edit-block').html('');

	}).off('click', '#reset_btn').on('click', '#reset_btn', function() {

		//重置
		$('.edit-block').html('');
		$('.edit-block').css({
			'width': '600px',
			'height': '600px'
		});
		$('#formWidth').val('');
		$('#formHeight').val('');

	}).off('blur', '#template-input').on('blur', '#template-input', function() {
		//设置模板名称
		var _val = $.trim($(this).val());
		if(_val.length > 0) {
			if($("#mbid").val() != ""){
				$.ajax({
					type: "post",
					dataType: "json",
					async: false,
					data: {id: $("#mbid").val(), mc: _val, oldmc: $("#oldmc").val()},
					url: "xgDymbMc.zf",
					success: function(data){
						if(data.res == "success"){

						}else if(data.res == "fail"){
							$.alert(data.mes, function(){
								setTimeout(function(){
									$('#template-input').focus();
									return false;
								}, 1000);
							});
						}
					}
				});
			}else{
				$.ajax({
					type: "post",
					dataType: "json",
					async: false,
					data: {mc: _val},
					url: "xzDymb.zf",
					success: function(data){
						if(data.res == "success"){
							$("#mbid").val(data.guid);
						}else if(data.res == "fail"){
							$.alert(data.mes, function(){
								setTimeout(function(){
									$('#template-input').focus();
									return false;
								}, 100);
							});
						}
					}
				});
			}
		} else {
			$.alert("请输入模板名称", function(){
				setTimeout(function(){
					$('#module-name-input').focus();
				}, 100);
			});
			return false;
		}
	}).off('change', '#bg').on('change', '#bg', function() {
		if($(this).val() != ""){
			$.ajaxFileUpload({
				url : 'uploadBg.zf',
				secureuri : true,
				dataType: "json",
				data:{id: $("#mbid").val()},
				fileElementId : 'bg', // 上传控件ID
				success : function(data) {
					if("success" == data.status || "SUCCESS" == data.status){
						showBg();
					}else{
						$.alert("上传失败，请重试或联系管理员！");
					}
				}
			});
		}
	});

	/**************************************/
	jQuery('#mblxdm').bind("change", function(){
		/*$.ajax({
			type: "post",
			dataType: "json",
			async: false,
			data: {mblxdm: $("#mblxdm").val()},
			url: "yzmblxSfczAjax.zf",
			success: function(data){
				if(data){*/
					if(jQuery('#mblxdm').val() == ""){
						jQuery(".block.dataSource").find(".content").html("");
						$("#content").hide();
					}else{
						jQuery(".block.dataSource").find(".content").html("");
						$("#content").hide();
						jQuery.ajax({
							type: "POST",
							data: {
									id: $("#mbid").val(), 
									mblxdm: jQuery('#mblxdm').val(),
									op:jQuery('#op').val()
							},
							dataType: "json",
							url: "changeDymblxAjax.zf",
							success: function(data){
								if(data.res == "success"){
									$("#content").show();
									for(var i = 0; i < data.dysjxList.length; i++){
										var $obj = jQuery("#data-source").clone();
										$obj.find("input[name='lx']").val(data.dysjxList[i].lx);
										$obj.find("input[name='dm']").val("{model."+data.dysjxList[i].dm+"}");
										$obj.find(".field.data").text(data.dysjxList[i].mc);
										$obj.show();
										jQuery(".block.dataSource").find(".content").append($obj.html());
									}
									$('.left-area .text-module').draggable({
										helper: 'clone',
										connectToSortable: '.edit-block,.edit-block .text-module',
										cursor: 'move',
										drag: function(event, ui) {
											ui.helper.width(60);
										},
										stop: function(event, ui) {
											$('.edit-block .text-module').sortable({
												cancel: 'span,div',
												placeholder: "ui-state-highlight",
												beforeStop: function(event, ui) {},
												stop: function(event, ui) {
													if($(this).hasClass('free')) {
														//自由文本里拖入元素
														ui.item.css({
															'position': 'relative'
														});
														if(ui.item.find('.field.free').size() > 0) {
															var _text = ui.item.find('.field.free').text();
															$(this).find('.field.free').append(_text);
														} else {
															var _html = ui.item.find('.field').clone();
															$(this).find('.field.free').append(_html);
															if(ui.item.find("input[name='lx']") != "undefined" && ui.item.find("input[name='lx']") != undefined){
																$(this).find('.field.free').append("<input type='hidden' name='lx' value='" + ui.item.find("input[name='lx']").val() + "'/>")
															}
															if(ui.item.find("input[name='dm']") != "undefined" && ui.item.find("input[name='dm']") != undefined){
																$(this).find('.field.free').append("<input type='hidden' name='dm' value='" + ui.item.find("input[name='dm']").val() + "'/>")
															}
															//$(this).find('.field.free').append('<font>&nbsp;</font>');
															$(this).find('.control-text').remove();
															$(this).find('.field.data').each(function() {
																/*$(this).before('<font class="control-text">&nbsp;&nbsp;</font>');
																$(this).after('<font class="control-text">&nbsp;&nbsp;</font>');*/
																$(this).before('<font class="control-text"></font>');
																$(this).after('<font class="control-text"></font>');
																addShade();
																//$(this).before('<font class="control-text">&nbsp;&nbsp;<font class="bracket">【</font></font>');
																//$(this).after('<font class="control-text"><font class="bracket">】</font>&nbsp;&nbsp;</font>');
															});
														}
														ui.item.remove();
													} else {
														//数据源里拖入元素
														var free = ui.item.find('.field.free');
														var data = ui.item.find('.field.data');
														var freeText = free.text();
														$(this).find('>.currentPosition').after('<span class="field free"></span>');
														$(this).addClass('free').removeClass('data');

														var oldData = $(this).find('>.data');
														var createFree = $(this).find('.field.free');
														createFree.append(oldData);
														if(free.size() > 0) {
															//数据源里拖入自由文本
															createFree.append(freeText);
															ui.item.remove();
														} else {
															var _html = ui.item.find('.field').clone();
															createFree.append(_html);
															if(ui.item.find("input[name='lx']") != "undefined" && ui.item.find("input[name='lx']") != undefined){
																createFree.find('.field.free').append("<input type='hidden' name='lx' value='" + ui.item.find("input[name='lx']").val() + "'/>")
															}
															if(ui.item.find("input[name='dm']") != "undefined" && ui.item.find("input[name='dm']") != undefined){
																createFree.find('.field.free').append("<input type='hidden' name='dm' value='" + ui.item.find("input[name='dm']").val() + "'/>")
															}
															ui.item.remove();
														}
														$(this).find('.field.data').each(function() {
															/*$(this).before('<font class="control-text">&nbsp;&nbsp;</font>');
															$(this).after('<font class="control-text">&nbsp;&nbsp;</font>');*/
															$(this).before('<font class="control-text"></font>');
															$(this).after('<font class="control-text"></font>');
															addShade();
															//$(this).before('<font class="control-text">&nbsp;&nbsp;<font class="bracket">【</font></font>');
															//$(this).after('<font class="control-text"><font class="bracket">】</font>&nbsp;&nbsp;</font>');
															/*if(!$(this).next().is('font')) {
																$(this).after('<font>&nbsp;&nbsp;</font>');
															}*/
														});
													}

												}
											});
											$('.edit-block .text-module').draggable({
												cursor: 'move',
												containment: '.edit-block',
												drag: function(event, ui) {
													var _position = ui.helper.find('.currentPosition');
													//_position.html('(x："' + ui.position.left + '",y:"' + ui.position.top + '")');
													$('#x-position').val(parseInt(ui.position.left));
													$('#y-position').val(parseInt(ui.position.top));
												},
												stop: function(event, ui) {
												}
											});
										}
									});
								}else{
									$.alert(data.mes);
									return false;
								}
							}
						});
					}
				/*}else{
					$.alert("该模板类型已存在启用的打印模板，请重新选择", function(){
						this.close();
						$("#mblxdm").val("");
					});
					return false;
				}
			}
		});*/
	});

	$("#save_btn").bind("click", function(){
		if($('.edit-shade').size() > 0){
			$('.edit-shade').trigger("click");
		}
		$(".edit-block").css("background-image", "");
		var nr = $.trim($(".stage").html()).replace(/([\n\r])(\t*)(\s*)/g, '\n\r');
		var dymbid = $("#mbid").val();
		$.ajax({
			url: "updateDymb.zf",
			data: {"nr": nr, "id": dymbid},
			type: "post",
			async: true,
			success: function(data){
				if(data.status == "SUCCESS" || data.status == "success"){
					$.success(data.message,function(){
						window.close();
					});
				}else{
					$.alert(data.message);
					return false;
				}
			}
		});
	});

	function addShade(){
		$(".nicEdit-main").append('<div class="nicEdit-shade"></div>');
	}

	function addFontStyle(){
		let _font = $(arguments[0]).clone();
		_font.removeAttr("unselectable");
		_font.html("");
		//let $this = $(".nicEdit-main.nicEdit-selected>font:not(.control-text):first");
		let $this = $(".nicEdit-main>font:not(.control-text):first");
		let _html = "";
		console.log($this);
		if($this.length == 0){
			$this = _font;
			_html = $(".nicEdit-main").html();
			$this.append(_html);
		}else{
			let json = getEleAttr(_font, fontAttr);
			for(let k in json){
				$this.attr(k, json[k]);
			}
		}
		if($this.find("font:not(.control-text)").length > 0){
			_html = $this.prop("outerHTML");
			$.each($this.find("font:not(.control-text)"), function(i, o){
				_html = _html.replace($(this).prop("outerHTML"), $(this).html());
			});
			$this = $(_html);
		}

		$(".nicEdit-main").html($this);
	}

	function getEleAttr(){
		let json = {};
		let $obj = $(arguments[0]);
		let arr = arguments[1];
		for(let index in arr){
			if(null != $obj.attr(arr[index]) && $obj.attr(arr[index]) != ""){
				json[arr[index]] = $obj.attr(arr[index]);
				break;
			}
		}
		return json;
	}

	function initElHtml(){
		let $this = $(arguments[0]);
		let _html = $this.html();
		let _youngest = getYoungest($this.find("input[name=lx]").next());
		let _replaceHtml = replaceChildrenHtml($this.find(".field.data").html(), _youngest);
		_html = _html.replace($this.find("input[name=lx]").next().prop("outerHTML"), _replaceHtml);
		$(".nicEdit-main").html(_html);
	}

	function replaceChildrenHtml(){
		let arg = $(arguments[0]);
		let arg2 = arguments[1];
		let _html = "";
		if(arg.children().length > 0){
			let _arg = arg.clone();
			_arg.html(arg2);
			_html = _arg.prop("outerHTML");
			_html = replaceChildrenHtml(arg.children(), _html);
		}else{
			if(null == arg.html() || arg.html() == ""){
				_html = arg2;
			}else{
				_html = arg.prop("outerHTML").replace(arg.html(), arg2);
			}
		}
		return _html;
	}

	function getYoungest(){
		let arg = $(arguments[0]);
		let _html = ""
		if(arg.children().length > 0){
			_html = getYoungest(arg.children());
		}else{
			_html = arg.prop("outerHTML");
		}
		return _html;
	}

	function showBg(){
		if($("#mbid").val() != ""){
			let _url = "getBg.zf?id=" + $("#mbid").val() + "&t=" + new Date().getTime();
			$(".edit-block").css("background-image", "url(" + _url + ")");
			$(".edit-block").css("background-repeat", "no-repeat");
			$(".edit-block").css("background-size", "100%");
		}
	}

	showBg();

	$("div.shift>div.moveBox>ul>li:not(.center)").off("click").on("click", function(e){
		let $this = $(this);
		let _shift = parseInt($("#shift").val());
		if(isNaN(_shift)){
			$.alert("请输入数字，请勿携带单位！");
			return false
		}
		let _topBase = $this.hasClass("to-top") ? -1 : $this.hasClass("to-bottom") ? 1 : 0;
		let _leftBase = $this.hasClass("to-left") ? -1 : $this.hasClass("to-right") ? 1 : 0;
		$("div.edit-block>.text-module").each(function(){
			let _top = parseInt($(this).css("top").replace("px", ""));
			let _left = parseInt($(this).css("left").replace("px", ""));
			$(this).css("top", (_top + _shift * _topBase) + "px");
			$(this).css("left", (_left + _shift * _leftBase) + "px");
		});
	});
});