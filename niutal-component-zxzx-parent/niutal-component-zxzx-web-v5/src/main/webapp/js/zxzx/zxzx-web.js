/*!***************************************************
 * jquery.mark v5.2.3
 * https://github.com/julmot/jquery.mark
 * Copyright (c) 2014–2016, Julian Motz
 * Released under the MIT license https://git.io/vaizN
 *****************************************************/
"use strict";function _classCallCheck(a,b){if(!(a instanceof b))throw new TypeError("Cannot call a class as a function")}var _extends=Object.assign||function(a){for(var b=1;b<arguments.length;b++){var c=arguments[b];for(var d in c)Object.prototype.hasOwnProperty.call(c,d)&&(a[d]=c[d])}return a},_createClass=function(){function a(a,b){for(var c=0;c<b.length;c++){var d=b[c];d.enumerable=d.enumerable||!1,d.configurable=!0,"value"in d&&(d.writable=!0),Object.defineProperty(a,d.key,d)}}return function(b,c,d){return c&&a(b.prototype,c),d&&a(b,d),b}}(),_typeof="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(a){return typeof a}:function(a){return a&&"function"==typeof Symbol&&a.constructor===Symbol?"symbol":typeof a};!function(a){"function"==typeof define&&define.amd?define(["jquery"],function(b){return a(b)}):a("object"===("undefined"==typeof exports?"undefined":_typeof(exports))?require("jquery"):jQuery)}(function(a){var b=function(){function b(a,c,d){_classCallCheck(this,b),this.opt=_extends({},{element:"*",className:"*",filter:[],separateWordSearch:!1,diacritics:!0,synonyms:{},iframes:!1,wordBoundary:!1,complete:function(){},each:function(){},debug:!1,log:window.console},c),this.sv="string"==typeof d?[d]:d,this.$ctx=a,this.dct=["aÀÁÂÃÄÅàáâãäåĀāąĄ","cÇçćĆčČ","dđĐďĎ","eÈÉÊËèéêëěĚĒēęĘ","iÌÍÎÏìíîïĪī","lłŁ","nÑñňŇńŃ","oÒÓÔÕÕÖØòóôõöøŌō","rřŘ","sŠšśŚ","tťŤ","uÙÚÛÜùúûüůŮŪū","yŸÿýÝ","zŽžżŻźŹ"]}return _createClass(b,[{key:"log",value:function a(b){var c=arguments.length<=1||void 0===arguments[1]?"debug":arguments[1];if(this.opt.debug){var a=this.opt.log;"object"===("undefined"==typeof a?"undefined":_typeof(a))&&"function"==typeof a[c]&&a[c]("jquery.mark: "+b)}}},{key:"escapeStr",value:function(a){return a.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g,"\\$&")}},{key:"createRegExp",value:function(a){return a=this.escapeStr(a),Object.keys(this.opt.synonyms).length>0&&(a=this.createSynonymsRegExp(a)),this.opt.diacritics&&(a=this.createDiacriticsRegExp(a)),this.opt.wordBoundary&&(a=this.createWordBoundaryRegExp(a)),a}},{key:"createSynonymsRegExp",value:function(a){var b=this.opt.synonyms;for(var c in b)if(b.hasOwnProperty(c)){var d=b[c],e=this.escapeStr(c),f=this.escapeStr(d);a=a.replace(new RegExp("("+e+"|"+f+")","gmi"),"("+e+"|"+f+")")}return a}},{key:"createDiacriticsRegExp",value:function(a){var b=this,c=a.split(""),d=[];return c.forEach(function(c){b.dct.every(function(b){if(-1!==b.indexOf(c)){if(d.indexOf(b)>-1)return!1;a=a.replace(new RegExp("["+b+"]","gmi"),"["+b+"]"),d.push(b)}return!0})}),a}},{key:"createWordBoundaryRegExp",value:function(a){return"\\b"+a+"\\b"}},{key:"getSeparatedKeywords",value:function(){var a=this,b=[];return this.sv.forEach(function(c){a.opt.separateWordSearch?c.split(" ").forEach(function(a){""!==a.trim()&&b.push(a)}):""!==c.trim()&&b.push(c)}),{keywords:b,length:b.length}}},{key:"getElements",value:function(){this.$ctx.length<1&&this.log("Empty context","warn");var a=this.$ctx.add(this.$ctx.find("*")),b=a.length;return{elements:a,length:b}}},{key:"matchesFilter",value:function(a,b){var c=!0,d=this.opt.filter.concat(["script","style","title"]);return this.opt.iframes||(d=d.concat(["iframe"])),b&&(d=d.concat(["*[data-jquery-mark='true']"])),d.every(function(b){return a.is(b)?c=!1:!0}),!c}},{key:"onIframeReady",value:function(a,b,c){try{!function(){var d=a.first()[0].contentWindow,e="about:blank",f="complete",g=function(){try{var d=a.contents();if(0===d.length)throw new Error("iframe inaccessible");b(d)}catch(a){c()}},h=function(){a.on("load.jqueryMark",function(){try{var b=a.attr("src").trim(),f=d.location.href;f===e&&b!==e&&""!==b||(a.off("load.jqueryMark"),g())}catch(a){c()}})};if(d.document.readyState===f){var i=a.attr("src").trim(),j=d.location.href;j===e&&i!==e&&""!==i?h():g()}else h()}()}catch(a){c()}}},{key:"forEachElementInIframe",value:function(b,c){var d=this,e=arguments.length<=2||void 0===arguments[2]?function(){}:arguments[2],f=0,g=function(){--f<1&&e()};this.onIframeReady(b,function(b){var e=b.find("*");f=e.length,0===f&&g(),e.each(function(b,f){var h=a(f);h.is("iframe")?!function(){var a=0;d.forEachElementInIframe(h,function(b,d){c(b,d),d-1===a&&g(),a++},g)}():(c(h,e.length),g())})},function(){var a=b.attr("src");d.log("iframe '"+a+"' could not be accessed","warn"),g()})}},{key:"forEachElement",value:function(b){var c=this,d=arguments.length<=1||void 0===arguments[1]?function(){}:arguments[1],e=arguments.length<=2||void 0===arguments[2]?!0:arguments[2],f=this.getElements(),g=f.elements,h=f.length,i=function(){0===--h&&d()};0===h&&d(),g.each(function(d,f){var g=a(f);if(!c.matchesFilter(g,e)){if(g.is("iframe"))return void c.forEachElementInIframe(g,function(a){c.matchesFilter(a,e)||b(a)},i);b(g)}i()})}},{key:"forEachNode",value:function(a){var b=arguments.length<=1||void 0===arguments[1]?function(){}:arguments[1];this.forEachElement(function(b){b.contents().each(function(b,c){3===c.nodeType&&""!==c.textContent.trim()&&a(c)})},b)}},{key:"wrapMatches",value:function(b,c){for(var d="*"===this.opt.element?"span":this.opt.element,e="*"===this.opt.className?"mark":this.opt.className,f=void 0;null!==(f=c.exec(b.textContent));){var g=b.splitText(f.index);if(b=g.splitText(f[0].length),null!==g.parentNode){var h=a("<"+d+" />",{class:e,"data-jquery-mark":!0,text:f[0]});g.parentNode.replaceChild(h[0],g),this.opt.each(h)}c.lastIndex=0}}},{key:"perform",value:function(){var a=this;this.sv instanceof RegExp?(this.log('Searching with expression "'+this.sv+'"'),this.forEachNode(function(b){a.wrapMatches(b,a.sv)},this.opt.complete)):!function(){var b=a.getSeparatedKeywords(),c=b.keywords,d=b.length;0===d&&a.opt.complete(),c.forEach(function(b){var e=new RegExp(a.createRegExp(b),"gmi");a.log('Searching with expression "'+e+'"'),a.forEachNode(function(b){a.wrapMatches(b,e)},function(){c[d-1]===b&&a.opt.complete()})})}()}},{key:"remove",value:function(){var b=this.opt.element+'[data-jquery-mark="true"]',c=this.opt.className;"*"!==c&&(b+="."+c),this.log('Removal selector "'+b+'"'),this.forEachElement(function(c){var d=a(c);if(d.is(b)){var e=d.parent();d.replaceWith(d.html()),e[0].normalize()}},this.opt.complete,!1)}}]),b}();a.fn.mark=function(c,d){var e=new b(a(this),d,c);return e.perform()},a.fn.markRegExp=function(c,d){var e=new b(a(this),d,c);return e.perform()},a.fn.removeMark=function(c){var d=new b(a(this),c);return d.remove()}});

/*!
 * scrollup v2.4.0
 * Url: http://markgoodyear.com/labs/scrollup/
 * Copyright (c) Mark Goodyear — @markgdyr — http://markgoodyear.com
 * License: MIT
 */
(function(n,t,i){"use strict";n.fn.scrollUp=function(t){n.data(i.body,"scrollUp")||(n.data(i.body,"scrollUp",!0),n.fn.scrollUp.init(t))};n.fn.scrollUp.init=function(r){var u=n.fn.scrollUp.settings=n.extend({},n.fn.scrollUp.defaults,r),o=!1,s,h,e,l,a,c,f;f=u.scrollTrigger?n(u.scrollTrigger):n("<a/>",{id:u.scrollName,href:"#top"});u.scrollTitle&&f.attr("title",u.scrollTitle);f.appendTo("body");u.scrollImg||u.scrollTrigger||f.html(u.scrollText);f.css({display:"none",position:"fixed",zIndex:u.zIndex});u.activeOverlay&&n("<div/>",{id:u.scrollName+"-active"}).css({position:"absolute",top:u.scrollDistance+"px",width:"100%",borderTop:"1px dotted"+u.activeOverlay,zIndex:u.zIndex}).appendTo("body");switch(u.animation){case"fade":s="fadeIn";h="fadeOut";e=u.animationSpeed;break;case"slide":s="slideDown";h="slideUp";e=u.animationSpeed;break;default:s="show";h="hide";e=0}l=u.scrollFrom==="top"?u.scrollDistance:n(i).height()-n(t).height()-u.scrollDistance;a=n(t).scroll(function(){n(t).scrollTop()>l?o||(f[s](e),o=!0):o&&(f[h](e),o=!1)});u.scrollTarget?typeof u.scrollTarget=="number"?c=u.scrollTarget:typeof u.scrollTarget=="string"&&(c=Math.floor(n(u.scrollTarget).offset().top)):c=0;f.click(function(t){t.preventDefault();n("html, body").animate({scrollTop:c},u.scrollSpeed,u.easingType)})};n.fn.scrollUp.defaults={scrollName:"scrollUp",scrollDistance:300,scrollFrom:"top",scrollSpeed:300,easingType:"linear",animation:"fade",animationSpeed:200,scrollTrigger:!1,scrollTarget:!1,scrollText:"Scroll to top",scrollTitle:!1,scrollImg:!1,activeOverlay:!1,zIndex:2147483647};n.fn.scrollUp.destroy=function(r){n.removeData(i.body,"scrollUp");n("#"+n.fn.scrollUp.settings.scrollName).remove();n("#"+n.fn.scrollUp.settings.scrollName+"-active").remove();n.fn.jquery.split(".")[1]>=7?n(t).off("scroll",r):n(t).unbind("scroll",r)};n.scrollUp=n.fn.scrollUp})(jQuery,window,document);
//# sourceMappingURL=jquery.scrollUp.min.js.map


jQuery(function($) {
	setHeight();
	$(window).resize(function() {
		setHeight();
	});

	var loading_stack = [];

	try {
		$.scrollUp && $.scrollUp({
			scrollName : 'scroll-up-btn',
			animationSpeed : '600',
			scrollText : '<i class="fa fa-4x fa-arrow-circle-up"></i>'
		});
	} catch (ex) {
		console.log(ex.message);
	}

	function setHeight() {
		var windowsHeight = parseInt($(window).height());
		var headerHeight = parseInt($('header').height());
		var searchHeight = parseInt($('.search').height());
		var contentHeight = windowsHeight - headerHeight - searchHeight - 100;
		$('.left-area').css('min-height', contentHeight + 'px');
		$('.right-area').css('min-height', contentHeight + 'px');
	}

	//点击我要咨询
	$('#askDialog').off('click').on('click', function(event) {
		var check_url = _path + "/zxzx/web/config/openStatus/json.zf";
		var new_topic_url = _path + "/zxzx/web/page/auth/new-topic.zf";
		var no_open_url = _path + "/zxzx/web/page/no-open.zf";
		var _open_state_open = 'open';	
		var _open_state_close = 'close';	
		var _open_state_no_auth = 'no-auth';	
		
		/**
		 * 加载咨询页面
		 */
		var _load_ask_page = function(){
			var dialog = bootbox.dialog({
			    message: '<div class="web-loading"></div>',
			    title: '我 要 提问',
			    closeButton: false
			});
			dialog.init(function(){
				$.when(
					dialog.find('.bootbox-body').load(new_topic_url)
				).done(function(data){
					
				}).always(function(data){
					setTimeout(function(){
						dialog.find('.web-loading').addClass('_hidden');
				    }, 300);
				});
				
			    
			});
	
		};
		$('.right-area').trigger('event-loading');
		$.when(
				$.getJSON(check_url)
			).done(function(data){
				setTimeout(function(){
					if(data['isOpen']){
						var dialog = bootbox.dialog({
						    message: '<div class="web-loading"></div>',
						    closeButton: false,
						    onShown:function(){
							   	$(".my-modal .modal-dialog").css("top","50px");
						    }
						});
						dialog.init(function(){
						    setTimeout(function(){
						        dialog.find('.bootbox-body').load(new_topic_url);
						    }, 100);
						});
						
					}else if(_open_state_close === data['state']){
						//loading_dialog.modal('hide');
						//加载未开放页面
						//$('#my-topic').click();
						var close_alter = 
						'<div class="tip-img">' +
						'	<img src="' + _path + '/css/zxzx/images/web/tips_noOpen.png" />' +
						'</div>' +
						'<div class="tip-font">' +
						'	<span class="gray"></span> <span class="gray">暂未开放</span>' +
						'</div>';

						bootbox.alert({ 
//							  title: "温馨提示",
							  message: close_alter, 
							  closeButton: false,
							  buttons:{
								  ok : {
									  label: '关闭',
									  className: "btn-default"
									}
							  },
							  callback: function(){ /* your callback code */ }
							});
						/*var dialog = bootbox.dialog({
						    message: '<div class="web-loading"></div>',
						    closeButton: true
						});
						dialog.init(function(){
						    setTimeout(function(){
						        dialog.find('.bootbox-body').load(no_open_url);
						    }, 100);
						});*/
					}else if(_open_state_no_auth === data['state']){
						bootbox.hideAll();
						
						//dialog.find('.bootbox-body').append(data['messageValue']);
						$('#my-topic').click();
					}
					
			    }, 100);
				//检查当前是否开放
				
				
			}).always(function(data){
				$('.right-area').trigger('event-loaded');
			});	
		event.stopPropagation();	
		//检查咨询模式
		//如果需要登陆咨询，检查登陆情况
	});
	
	/** 注册tab点击事件 */
	$(document).off('keypress','#zxzx-web-search-box').on('keypress','#zxzx-web-search-box',function(event){
		if(event.keyCode == "13"){
	        $('#topic').trigger('click');
	    }
		event.stopPropagation();
	}).off('click touchend','.glyphicon-search').on('click touchend','.glyphicon-search',function(event){
		$('#topic').trigger('click');
		event.stopPropagation();
	}).off('click', '#zxzx-web-search-box-remove').on('click','#zxzx-web-search-box-remove',function(event){
		$('#zxzx-web-search-box').val('');
		 $('#topic').trigger('click');
		 event.stopPropagation();
	}).off('click', '.left-area>.item:not(.last)').on('click','.left-area>.item:not(.last)', function(event) {
		$('.left-area>.item').removeClass('active');
		$('.right-area>.item').removeClass('active');
		$(this).addClass('active');
		var container_id = $(this).attr('data-content');
		var data_container = $('#' + container_id);
		data_container.trigger('event-loading');
		var data_url = $(this).attr('data-url');
		var params = null;
		if($(this).attr('id') === 'topic'){
			var bkmds = [];
			params = {};
			$('#topic-category>.active').each(function(i,n){
				//params['webSearchBkdmValues['+i+']'] = $(n).attr('data-dm');
				bkmds.push($(n).attr('data-dm'));
			});
			params['webSearchBkdmValues'] = bkmds.toString();
		}
		data_container.load(data_url, params, function(data) {
			data_container.trigger('event-loaded');
			data_container.addClass('active');
		});
		event.stopPropagation();
		// 异步加载页面
	}).off('event-loading', '.right-area').on('event-loading','.right-area', function(event) {
		$(this).find('.web-loading').removeClass('hidden2');
		event.stopPropagation();
	}).off('event-loaded', '.right-area').on('event-loaded','.right-area', function(event) {
		var _this = $(this);
		setTimeout(function() {
			_this.find('.web-loading').addClass('hidden2');
		}, 200);
		event.stopPropagation();
	}).off('touchend', '.footer-nav>.allQ').on('touchend','.footer-nav>.allQ', function(event) {
		if($(this).find('#mobile-topic-blocks').is(':hidden')){
			$('body').append('<div class="shade-zxzx"></div>');
		}
		$(this).find('#mobile-topic-blocks').show();
		event.stopPropagation();
	}).off('touchend', '.footer-nav .myq').on('touchend','.footer-nav .myq', function(event) {
		var page = $(this).find('a').attr('data-page');
		$('#' + page).trigger('click');
		$('.tier').hide();
		$('.shade-zxzx').remove();
		event.stopPropagation();
	}).off('touchend', '.footer-nav .sub').on('touchend','.footer-nav .sub', function(event) {
		var page = $(this).find('a').attr('data-page');
		$('#' + page).trigger('click');
		$('span[topic-selected-page]').html($(this).find('a').html());
		$(this).parent('#mobile-topic-blocks').hide();
		$('.shade-zxzx').remove();
		event.stopPropagation();
	}).off('click touchend', '.question-classify .panel .block').on('click touchend', '.question-classify .panel .block', function(event) {
		$('.question-classify .panel .block').find('i').remove();
		$('.question-classify .panel .block').removeClass('active');
		$(this).prepend('<i class="glyphicon glyphicon-ok"></i>');
		$(this).addClass('active');
		event.stopPropagation();
	}).off('click touchend', '.question-classify .item').on('click touchend', '.question-classify .item', function(event) {
		$(this).next('.panel').show();
		event.stopPropagation();
	}).off('click touchend', '#wdtw .more').on('click touchend','#wdtw .more', function(event) {
		$(this).next('.more-answer').show();
		$(this).closest('.article').find('.release-time').show();
		$(this).remove();
		event.stopPropagation();
	}).off('click touchend', '.shade-zxzx').on('click touchend', '.shade-zxzx', function(event) {
		$(this).remove();
		$('.tier').hide();
		event.stopPropagation();
	});
	
	//初始化
	$('#topic').trigger('click');
});
