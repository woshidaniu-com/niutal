jQuery(function($) {
	//FIXME 面向对象方式整理这个文件所有功能的代码!!!
	//数组扩展
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val){
				return i;
			}
		}
		return -1;
	}
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	
	//试题序号记录器(可回收序号)
	var stSeqArray = new Array();
	//试题发号器
	function nextStSeq(){
		var length = stSeqArray.length;
		if(length <= 0){
			stSeqArray.push("1");
			return "1";
		}else{
			var newSeq = length+1;
			var newSeqStr = newSeq + "";
			stSeqArray.push(newSeqStr);
			return newSeqStr;
		}
	}
	function getOtherStSeqs(seq){
		var retArr = new Array();
		var i=0;
		for(i=0;i<stSeqArray.length;i++){
			var s = stSeqArray[i];
			if(s != seq){
				retArr.push(s);
			}
		}
		return retArr;
	}
	function getOtherAfterStSeqs(seq){
		
		var retArr = new Array();
		var reqNumber = parseInt(seq);
		var i=0;
		for(i=0;i<stSeqArray.length;i++){
			var s = stSeqArray[i];
			var sNumber = parseInt(s);
			if(sNumber > reqNumber){
				retArr.push(s);
			}
		}
		return retArr;
	}
	
	function cleanStSeq(){
		stSeqArray = new Array();
	}
	//试题序号回收
	function recycleStSeq(seq){
		stSeqArray.remove(seq);
	}
	
	//试卷元素记录器(可回收序号)
	var elementSeq = new Array();
	//全局元素序号发号器
	function nextElementSeq(){
		var length = elementSeq.length;
		if(length <= 0){
			elementSeq.push("1");
			return "1";
		}else{
			var newSeq = length+1;
			var newSeqStr = newSeq+"";
			elementSeq.push(newSeqStr);
			return newSeqStr;
		}
	}
	function cleanElementSeq(){
		elementSeq = new Array();
	}
	//全局元素序号回收
	function recycleElementSeq(seq){
		elementSeq.remove(seq);
	}
	//所有问卷上的元素,包括试题，描述说明
	var allElements = new Array();
	//创建类型元素
	function createElement(type){
		var $item = $('.topic-module [data-issue="' + type + '"]').clone();
		doSetSeqs($item);
		allElements.push($item);
		return $item;
	}
	function cleanAllEmenets(){
		allElements = new Array();
	}
	//设置元素序号和试题序号
	function doSetSeqs($item){
		
		var type = $item.data("issue");
		var isMssm = (type == "mssm");//是否是描述说明
		if(isMssm){
			//设置新元素序号，试题序号
			var newElementSeq = nextElementSeq();
			$item.attr("elementSeq",newElementSeq);
			//设置试题序号
			$item.attr("stSeq","");
		}else{
			//设置新元素序号，试题序号
			var newElementSeq = nextElementSeq();
			$item.attr("elementSeq",newElementSeq);
			//设置试题序号
			var newStSeq = nextStSeq();
			$item.attr("stSeq",newStSeq);
			
			$item.find('.current-number').text(newStSeq);
		}
	}
	//回收元素
	function recycleElement($item){
		var type = $item.data("issue");
		var isMssm = (type == "mssm");//是否是描述说明
		if(!isMssm){
			var stSeq = $item.attr("stSeq");
			recycleStSeq(stSeq);
		}
		var elementSeq = $item.attr("elementSeq");
		recycleElementSeq(elementSeq);
		
		allElements.remove($item);
		$item.remove();
		
		//刷新试题序号和元素序号
		refreshAllSeq();
	}
	//移动到指定的位置
	function moveStTo($item,targetStSeq){
		if(stSeqArray.indexOf(targetStSeq) == -1){
			alert("请输入正确且已存在的试题编号");
			return false;
		}
		var currentStSeq = $item.attr("stSeq");
		if(currentStSeq == targetStSeq){
			return false;
		}
		var isMssm = (currentStSeq == "");//是否是描述说明
		if(isMssm){//是描述说明，总在目标试题前面
			var i = 0;
			for(i=0;i<allElements.length;i++){
				var $t = allElements[i];
				var stSeq = $t.attr("stSeq");
				if(stSeq == targetStSeq){
					$t.before($item);
					refreshAllSeq();
					return true;
				}
			}
			return false;
		}else{
			var $targetItem = null;
			var i = 0;
			for(i=0;i<allElements.length;i++){
				var $t = allElements[i];
				var stSeq = $t.attr("stSeq");
				if(targetStSeq == stSeq){
					$targetItem = $t;
					break;
				}
			}
			if($targetItem){
				//向上移动
				if(targetStSeq <= currentStSeq){
					$targetItem.before($item);
				}else{
					//向下移动
					$targetItem.after($item);
				}
				refreshAllSeq();
				return true;
			}
			return false;
		}
	}
	
	//刷新所有序号
	function refreshAllSeq(){
		//保存之前的跳转试题
		//allElements
		
		cleanAllEmenets();
		cleanElementSeq();
		cleanStSeq();
		
		//遍历所有元素，进行刷新
		var $items = $('.wj-content .item');
		var i=0;
		for(i=0;i<$items.length;i++){
			var $item = $($items.get(i));
			allElements.push($item);
			doSetSeqs($item);
		}
		//需要保证item里面的选项的tzstid必须小于当前的，否则需要清空
		//刷新不支持的调整试题id
		refreshUnsupportTzstConfig();
	}
	refreshAllSeq();
	
	//未知特效
	$('.chosen-select').trigger("chosen");
	if($('body').find('.no-response-container')) {
		$('body').addClass('no-response');
	}
	function located() {
		//获取空白模板的位置给左侧定位
		var blankLeft = $('.blank-module').offset().left;
		var blankTop = $('.blank-module').offset().top;
		var leftWidth = $('.fixed-left-block').width();

		$('.fixed-left-block').css({
			left: blankLeft - leftWidth - 50,
			top: blankTop + 50
		});
	}
	
	//关于试卷元素的事件处理-----------------------------------------start
	$(document).off('click', '.fixed-left-block .content .item').on('click', '.fixed-left-block .content .item', function(event) {//点击左侧试题，右侧新增试题

		event.stopPropagation();
		//切换题目类型
		var type = $(this).attr('id');
		//创建并添加
		var $item = createElement(type);
		$('.wj-content').append($item);
		
	}).off('click', '.btn_sc').on('click', '.btn_sc', function(event) {//点击一个右侧元素内的删除按钮

		event.stopPropagation();
		//删除item
		var $item = $(this).closest('.item');
		recycleElement($item);

	});
	//拖动事件
	var currentItem, currentI, currentId, currentX, currentY;
	currentItem = $('.fixed-left-block .content .item');
	currentI = currentItem.find('.preview');
	currentItem.draggable({
		handle: currentI,
		opacity: 0.7,
		connectToSortable: '.blank-module .wj-content',
		helper: 'clone',
		start: function(event, ui) {
			currentId = $(this).attr('id');
			console.log("开始拖动:"+currentId);
		},
		drag: function(event, ui) {
			ui.helper.find('>div').show();
			ui.helper.find('.preview').hide();
			ui.helper.addClass('drag-style');
		},
		stop: function(event, ui) {
			currentId = $(this).attr('id');
			console.log("拖动停止:"+currentId);
			$('.mb input[type="checkbox"]:not(".is-mandatory"),input[type="radio"]:not(".is-mandatory")').attr('disabled', 'disabled');
			$('.wj-content .preview').remove();
			refreshAllSeq();
		}
	});
	//所有元素拖动事件
	$('.blank-module .wj-content').droppable({
		activeClass: 'wj-drop',
		drop: function(e, i) {
			refreshAllSeq();
		},
	});
	//关于试卷元素的事件处理-----------------------------------------end
	
	var $editModule  = $(".edit-module"); 
	function registeEditXxHandler($checkboxOrRadio){
		
		$editModule.unbind("click",complateEditXxCheckHandler);
		
		console.log("registeEditXxHandler:"+$checkboxOrRadio.find("span").text())
		
		$editModule.bind("click",$checkboxOrRadio,complateEditXxCheckHandler);
	}
	function unregisteEditXxHandler(){
		
		console.log("unregisteEditXxHandler")
		
		$editModule.unbind("click",complateEditXxCheckHandler);
		
	}
	//点击编辑选项名称,分值就开始注册此事件
	function complateEditXxCheckHandler(event){
		
		var $checkboxOrRadio = event.data;
		
		console.log("complateEditXxCheckHandler:"+$checkboxOrRadio.find("span").text());
		
		var target = event.target;
		var $target = $(target);
		//是checkbox或radio的div的内部的元素,就直接跳过
		var contains = $.contains($checkboxOrRadio.get(0),$target.get(0));
		if(contains){
			return;
		}else{
			//阻止继续传播事件
			event.stopPropagation();			
		}
		
		//获取编辑区域的文字
		var editBlock = $('.wj-setting-content .edit-area');
		var item = editBlock.closest('.item');
		var text = $.trim(editBlock.find('input[name=xxmc]').val());
		var xxfz = $.trim(editBlock.find('input[name=xxfz]').val());
		
		if(!(/(^[0-9]\d*$)/.test(xxfz))){
			editBlock.find('input[name=xxfz]').focus();
			alert("选项分值必须是整数，且在0到100之间");
			return;
		}
		
		var xxfzNumber = parseInt(xxfz);
		if(xxfzNumber < 0 || xxfzNumber > 100){
			editBlock.find('input[name=xxfz]').focus();
			alert("选项分值必须是整数，且在0到100之间");
			return;
		}
		
		var $prev = editBlock.prev();
		$prev.find('span').text(text);
		
		var $radio = $prev.find(':radio,:checkbox');
		$radio.attr('xxfz',xxfz);
		
		editBlock.remove();
		$prev.show();

		//启用item的sortable事件
		$(".blank-module .wj-content").sortable("enable");

		//这里是编辑成功，取消注册此事件
		unregisteEditXxHandler();
	}
	
	
	
	//其他事件处理
	$(document).off('mouseenter', '.blank-module .wj-content .item').on('mouseenter', '.blank-module .wj-content .item', function(event) {
		
		//鼠标进入题目
		var $item = $(this);
		if($item.find(".add-chosen").size() <= 0){
			
			var tools = $('.wj-chosen-module').find('.add-chosen').clone();
			$item.addClass('cur');
			$item.append(tools);
			
			var issue = $item.data('issue');
			//单选题和单选组合题，后面添加后台设置按钮
			if((issue == 'dxxz') || (issue == 'dxzhe')) {
				$(this).find('.btn_ydz').after('<button type="button" class="btn btn-danger btn-sm btn_htsz" href="javascript:void(0);"><i class="icon-share-alt"></i>后台设置</button>');
			}
			//获取当前items的序号
			var currentStSeq = $item.attr("stSeq");
			var $list = $('.tz-st-list').empty();
			var otherStSeqs = getOtherStSeqs(currentStSeq)
			
			var html = "";
			var i=0;
			for(i=0;i<otherStSeqs.length;i++){
				var stSeq = otherStSeqs[i];
				html += '<div class="item-option item-ydz">' + stSeq + '</div>';				
			}
			$list.append(html);
		}
	}).off('mouseleave', '.blank-module .wj-content .item').on('mouseleave', '.blank-module .wj-content .item', function(event) {
		//鼠标移出题目
		var $item = $(this);
		$item.removeClass('cur');
		$item.find('.add-chosen').remove();
		$('.analog-select .select-content').hide();
		
	}).off('click', '.btn_htsz').on('click', '.btn_htsz', function(event) {//后台设置按钮点击
		
		var $item = $(this).closest('.item');
		var $chosen = $item.closest('.add-chosen');
		
		//构建后台设置的左侧选项列表
		var $xxlist = $item.find(".xx-list");
		
		var $radioDivs = $item.find('.radio');
		$radioDivs.each(function(i,n) {
			var span = $.trim($(this).find('span').text());
			var html = '<div class="item-option item-option-xx" xxindex="'+ i +'">' + span + '</div>';
			$xxlist.append(html);
		});

		$item.find('.ydz-jump').addClass('hide');
		$item.find('.htsz-jump').removeClass('hide');
		
	}).off('click', '.analog-select-xx').on('click', '.analog-select-xx', function(e) {//后台设置出现的左侧下拉框列表出现
		
		var $xxlist = $('.xx-list');	
		if($xxlist.is(":hidden")){
			$xxlist.show();
			var h=parseInt($xxlist.height())+5;
			$xxlist.css({
				'top':-h
			});
			//隐藏试题
			$('.xx-st-list').hide();
		}else{
			$xxlist.hide();
		}
		
	}).off('click', '.analog-select-tz-st').on('click', '.analog-select-tz-st', function(e) {
		
		var content=$(this).find('.select-content');	
		var aContent=$(this).closest('.item').find('.select-content');
		if(content.is(":hidden")){
			aContent.hide();
			content.show();
			var h=parseInt(content.height())+5;
			content.css({
				'top':-h
			});
		}else{
			content.hide();
		}

	}).off('click', '.item-option-xx').on('click', '.item-option-xx', function(event) {//后台设置出现的左侧下拉框列表，点击其中一个选项,这个是试题的选项
		
		var $item = $(this).closest('.item');
		var $xx = $(this);
		var xxindex = $xx.attr("xxindex");
		//后台设置的左侧列表点击事件
		
		var _text=$.trim($xx.text());
		
		var $analogSelect = $item.find('.analog-select-xx');
		var _input = $analogSelect.find('.analog-input');
		_input.val(_text);
		_input.attr("xxindex",xxindex);
		
		//构建右侧的试题列表，若已经配置，则显示已经配置的,否则只显示提示
		var $choose = $item.find(":radio").eq(xxindex)
		var tzstIndex = $choose.attr('tzstid');
		//下一个analog-select
		var $nextAnalogSelect = $item.find(".analog-select-xx-st");
		var $nextInput = $nextAnalogSelect.find('.analog-input');
		if(tzstIndex){
			//让右侧的一个显示出来
			$nextInput.val(tzstIndex);
		}else{
			$nextInput.val();
			$nextInput.attr('placeholder',"请选择");
		}
		//隐藏选项列表
		$item.find(".xxlist").hide();
		
	}).off('click', '.analog-select-xx-st').on('click', '.analog-select-xx-st', function(event) {//后台设置出现的右侧下拉框，展示
		
		event.stopPropagation();
		//构建试题列表，不包含当前item试题
		var $item = $(this).closest(".item");
		var $xxstlist = $item.find('.xx-st-list');
		$xxstlist.empty();
		
		var currentStSeq = $item.attr("stSeq"); 
		var otherStSeqs = getOtherAfterStSeqs(currentStSeq);
		var i = 0;
		var html = "";
		for(i=0;i<otherStSeqs.length;i++){
			var stSeq = otherStSeqs[i];
			html += '<div class="item-option item-st" stid="'+ stSeq +'">' + stSeq + '</div>';
		}
		$xxstlist.append(html);
		
		if($xxstlist.is(":hidden")){
			//显示试题类别
			$xxstlist.show();
			var h=parseInt($xxstlist.height())+5;
			$xxstlist.css({
				'top':-h
			});
		}else{
			$xxstlist.hide();
		}
		//隐藏选项列表
		$item.find(".xxlist").hide();
		
	}).off('click', '.item-st').on('click', '.item-st', function(event) {//后台设置出现的右侧下拉框，展示
		
		event.stopPropagation();
		//后台设置的右侧列表点击事件
		var $item = $(this).closest(".item");
		//查看是否已经选择了选项
		var $leftInput = $item.find(".analog-select-xx input");
		var	xxindexStr = $leftInput.attr("xxindex");
		var xxindex = parseInt(xxindexStr);
		
		if(!xxindexStr || isNaN(xxindex)){
			alert("请先点选左侧选项列表");
			return;
		}
		
		var _text=$.trim($(this).text());
		var _input=$(this).parents('.analog-select').find('.analog-input');
		_input.val(_text);
		
		var stid = $(this).attr("stid");
		//点击后台设置的右侧选择列表
		
		//设置当前选择的试题，设置那个选项的属性
		var $choose = $item.find(":radio").eq(xxindex);
		$choose.attr("tzstid",stid);
		$item.find(".xx-st-list").hide();
		
	}).off('click', '.item-ydz').on('click', '.item-ydz', function(event) {

		//跳转设置的右侧列表点击事件
		var _text=$.trim($(this).text());
		var _input=$(this).parents('.analog-select').find('.analog-input');
		_input.val(_text);

	}).off('click', 'i.tjxx').on('click', 'i.tjxx', function(event) {//添加一个试题的选项
		//克隆选项
		event.stopPropagation();
		var $item = $(this).closest('.item');
		
		var issue = $item.data('issue');

		var $radio = $('.wj-chosen-module').find('>.radio').clone();
		$radio.find('input').attr('xxfz','0');

		var $checkbox = $('.wj-chosen-module').find('>.checkbox').clone();
		$checkbox.find('input').attr('xxfz','0');

		if(issue == 'dxxz') {
			//单项选择
			var $radios = $item.find(":radio");
			var nextIndex = $radios.length + 1;
			$radio.find("span").text("选项"+nextIndex);
			$item.find('.wj-setting-content').append($radio);
			
		} else if(issue == 'dxzhe') {
			//单项组合
			var $radioOther = $item.find('.radio.other');
			if($radioOther.size() <= 0){
				var $input = $("<input type='text' class='no-sort' />");
				$radio.find("label").after($input);
				$radio.addClass("other");
				$item.find('.wj-setting-content').append($radio);
			}else{
				$radioOther.before($radio);
			}
		} else if(issue == 'duxxz') {
			//多项选择
			$item.find('.wj-setting-content').append($checkbox);
		} else if(issue == 'duxzh') {
			//多项组合
			var $checkBoxOther = $item.find('.checkbox.other');
			if($checkBoxOther.size() <= 0){
				var $input = $("<input type='text' class='no-sort' />");
				$checkbox.find("label").after($input);
				$checkbox.addClass("other");
				$item.find('.wj-setting-content').append($checkbox);
			}else{
				$checkBoxOther.before($checkbox);
			}
		}
		
		
		var html = '';
		$item.find('.radio').each(function() {
			var span = $.trim($(this).find('span').text());
			html += '<div class="item-2">' + span + '</div>';
		});

		$('.answer-list').html(html);
		
		$item.find('.select-content').each(function(){
			if($(this).is(":visible")){
				var h=parseInt($(this).height())+5;
				$(this).css({
					'top':-h
				});
			}
		});
		
		refreshKxgsSelect($item);
		
	}).off('click', '.wj-setting-content > .radio,.wj-setting-content > div.checkbox').on('click', '.wj-setting-content > div.radio,.wj-setting-content > div.checkbox', function(event) {

		event.stopPropagation();
		
		var $checkboxOrRadio = $(this);
		
		console.log("click 选项:" + $checkboxOrRadio.find("span").text());
		registeEditXxHandler($checkboxOrRadio);
		
		//编辑题目内容
		var item= $checkboxOrRadio.closest('.item');
		item.find('.select-content').hide();
		//先移除之前创建的edit-area
		var $oldEditArea = item.find(".edit-area");
		var text = $.trim($oldEditArea.find('input[type="text"]').val());
		
		$oldEditArea.prev().find("span").text(text);
		$oldEditArea.prev().show();
		$oldEditArea.remove();
		
		var checkboxOrRadioText = $checkboxOrRadio.text();
		var trimedCheckboxOrRadioText = $.trim(checkboxOrRadioText);
		
		var $checkboxOrRadioInput = $checkboxOrRadio.find("input");
		var xxfz = $checkboxOrRadioInput.attr("xxfz");
		
		var $content = $('.wj-chosen-module').find('.edit-area').clone();
		
		$content.find('input[name=xxmc]').val(trimedCheckboxOrRadioText);
		$content.find('input[name=xxfz]').val(xxfz);
		
		if($(this).find('.edit-area').size() == 0) {
			$(this).append($content);
			$(this).find('.setting-block').hide();
		}

		$content.find('input[name=xxmc]').focus();

		$(".blank-module .wj-content").sortable("disable");
		
	}).off('click','.wj-setting-content .radio,.wj-setting-content input[name=xxmc]').on('click','.wj-setting-content .radio,.wj-setting-content input[name=xxmc]',function(event){//点击选项的第一个input
		event.stopPropagation();
		$(this).focus();
		
	}).off('click','.wj-setting-content .radio,.wj-setting-content input[name=xxfz]').on('click','.wj-setting-content .radio,.wj-setting-content input[name=xxfz]',function(event){//点击选项的第二个input
		event.stopPropagation();
		var $this = $(this);
		$this.focus();
		
		var $validation = $this.trigger("validation");
		var accept = $validation.valid();

	}).off('mouseleave', '.number-jump').on('mouseleave', '.number-jump', function() {

		//		$(this).hide();
	}).off('click', '.btn_fz').on('click', '.btn_fz', function(event) {

		event.stopPropagation();
		
		//复制item
		var $item = $(this).closest('.item');
		var $content = $item.clone();
		//移除工具栏
		$content.find('.add-chosen').remove();
		$item.after($content);
		
		refreshAllSeq();

	}).off('mouseover', 'body').on('mouseover ', 'body', function() {

		//获取页面上显示的题目数量
		var numbers = $('.wj-content>.item').size();
		$('.wj-topic-number').text(numbers);

	}).off('click', '.btn_tz,.btn_ydz').on('click', '.btn_tz,.btn_ydz', function(event) {//点击"移动至"按钮

		//跳转题目
		event.stopPropagation();
		//items是需要跳转的题目
		var $item = $(this).closest('.item');
		
		var $numberJump = $item.find('.ydz-jump');
		var $input = $numberJump.find("input");
		
		//需要跳转的题目索引号
		var targetNoText = $input.val();
		moveStTo($item,targetNoText);
		
	}).off('mouseleave', '.wbms_select').on('mouseleave', '.wbms_select', function() {

		//描述说明切换高度
		var checkValue = $(this).val();
		var checkValueNumber = parseInt(checkValue);
		var $textarea = $(this).closest('.item').find('textarea');
		$textarea.attr('rows', checkValue);
		
		var newHight =  checkValueNumber * 30 +"px";
		$textarea.css("height",newHight);
		
	}).off('click', '.wj-setting-content .checkbox').on('click', '.wj-setting-content .checkbox', function(event) {

		event.stopPropagation();

		//可选个数
		var checkValue = parseInt($(this).closest('.item').find(".chosen-size").val());
		var checkBox = $(this).closest('.item').find('.wj-setting-content>.checkbox [type="checkbox"]:checked');
		var checkedSize = parseInt(checkBox.size());

		if(checkedSize > checkValue) {
			alert("超出可选个数");
			checkBox.removeAttr('checked');
		}
	}).off('mouseleave', '.chosen-size').on('mouseleave', '.chosen-size', function() {

		var checkValue = parseInt($(this).val());
		var checkBox = $(this).closest('.item').find('.wj-setting-content>.checkbox [type="checkbox"]:checked');
		var checkedSize = parseInt(checkBox.size());

		if(checkedSize > checkValue) {
			alert("已超出可选个数，请重新选择");
			checkBox.removeAttr('checked');
		}
	}).off('click', '.edit-area .fa-arrow-circle-o-up').on('click', '.edit-area .fa-arrow-circle-o-up', function(event) {

		event.stopPropagation();

		//上移
		var $currentArea = $(this).closest('.edit-area').parent();
		var $index = parseInt($currentArea.index() + 1);
		var $prev = $currentArea.closest('.wj-setting-content').find('>div:eq(' + ($index - 2) + ')');

		if($index > 1) {
			$prev.before($currentArea);
		} else {
			alert('已最上');
		}
	}).off('click', '.edit-area .fa-arrow-circle-o-down').on('click', '.edit-area .fa-arrow-circle-o-down', function(event) {

		event.stopPropagation();

		//下移
		var $currentArea = $(this).closest('.edit-area').parent();
		var $index = parseInt($currentArea.index() + 1);
		var $total = parseInt($(this).closest('.wj-setting-content').find('>div').size());
		var $next = $currentArea.closest('.wj-setting-content').find('>div:eq(' + ($index) + ')');

		if($index < $total) {
			$next.after($currentArea);
		} else {
			alert('已最下');
		}
	}).off('click', '.edit-area .fa-trash').on('click', '.edit-area .fa-trash', function(event) {//删除题目中的一个选项
		
		event.stopPropagation();
		
		//删除
		var $item = $(this).closest(".item");
		
		console.log("事件触发-->删除题目"+ $item.attr("elementseq") +"的一个选项"+ $item.attr("stseq"));
		
		var $radioDiv = $(this).closest('.edit-area').parent();
		$radioDiv.remove();
		
		//删除题目，解除绑定时间处理函数
		unregisteEditXxHandler();
		
		//如果是单选组合题或是多选组合题，那么需要对最后一个选项添加input标签
		var issue = $item.data("issue");
		if(issue == "dxzhe"){
			var $lastRadio = $item.find("div.radio").last();
			if($lastRadio.size() > 0){
				if(!$lastRadio.hasClass("other")){
					$lastRadio.addClass("other");
					$lastRadio.find("label").after("<input type='text' class='no-sort' />");				
				}
			}
		}else if(issue == "duxzh"){
			var $lastCheckbox = $item.find("div.checkbox").last();
			if($lastCheckbox.size() > 0){
				if(!$lastCheckbox.hasClass("other")){
					$lastCheckbox.addClass("other");
					$lastCheckbox.find("label").after("<input type='text' class='no-sort' />");									
				}
			}
		}else{
			//其他类型无须考虑
		}
		refreshKxgsSelect($item);

	}).off('click', '.wj-question-type').on('click', '.wj-question-type', function(event) {

		//修改标题
		var iniText = $(this).find("span").eq(1).text();
		var editArea = $('.topic-module').find('.edit-title').clone();
		$(this).after(editArea);
		editArea.val(iniText);
		editArea.focus();
		$(this).hide();

	}).off('blur', '.edit-title').on('blur', '.edit-title', function(event) {

		var $val = $(this).val();
		$(this).prev().find('.official-title').text($val);
		$(this).prev().show();
		$(this).remove();
		
	});
	
	//空白模板
	$('.blank-module .wj-content').sortable({
		placeholder: "ui-state-highlight",
		cancel: '.no-sort',
		stop: function(event, ui) {
			refreshAllSeq();
		}
	});

	//所有的题目内容中的checkbox和radio都不可被选中
	$('.mb input[type="checkbox"]:not(".is-mandatory"),input[type="radio"]:not(".is-mandatory")').attr('disabled', 'disabled');
	
	function refreshUnsupportTzstConfig(){
		var $items = $(".wj-content .item");
		if($items.size() <= 1){
			return;
		}
		for(var i=0;i<$items.size();i++){
			var item = $items.get(i);
			var $item = $(item);
			var issue = $item.data("issue");
			//单项选择或单选组合
			if(issue == 'dxxz' || issue == 'dxzhe') {
				var $radios = $item.find(":radio");
				for(var j=0;j<$radios.size();j++){
					var radio = $radios.get(j);
					var $radio = $(radio);
					var tzstid = $radio.attr("tzstid");
					if(tzstid){
						//跳转试题id竟然小于当前试题索引号，肯定不行
						var tzstidNo = parseInt(tzstid);
						var currentItemIndex = $items.index($item);
						if(currentItemIndex >= tzstidNo){
							$radio.removeAttr("tzstid");
						}						
					}
				}
			}
		}
	}
	
	//全屏
	$(".bootbox-full").click();

	//单选组合，追加input标签
	var $item_dxzhe = $('.wj-content>.item[data-issue="dxzhe"]');
	$item_dxzhe.each(function(i,n){
		var $item = $(n);
		var $lastRadio = $item.find(":radio:last");
		$lastRadio.parent().parent().parent().addClass("other");
		$lastRadio.parent().after("<input type='text' value='' name='' class='no-sort'/>");
		
	});
	
	//刷新可选个数，当增减选项时，调整可选个数
	function refreshKxgsSelect($item){
		
		if($item.size() <= 0){
			return;
		}
		var issue = $item.data("issue");
		if(issue != "duxzh" && issue != "duxxz"){
			return;
		}
		//查找有多少个checkbox
		var $select = $item.find("select[name='kxgs']");
		
		//旧的选中的个数
		var oldKxgs = $select.find("option:selected").text();
		if(!oldKxgs){
			oldKxgs = "0";
		}
		var oldKxgsNumber = parseInt(oldKxgs);
		
		var $checkboxs = $item.find(":checkbox").not(".is-mandatory");
		var checkboxSize = $checkboxs.size();
		
		var $options = $select.find("option");
		var optionsSize = $options.size();
		
		//刷新可选个数列表
		if(checkboxSize < optionsSize){
			//减少可选个数
			var removeOptionNumber = optionsSize - checkboxSize;
			for(var i=0;i<removeOptionNumber;i++){
				//从后面一个一个移除
				$select.find("option:last").remove();
			}
		}else if(checkboxSize == optionsSize){
			
		}else{
			if(optionsSize > 0){
				var $lastOption = $select.find("option:last");
				var lastOptionValue = $lastOption.text();
				var lastOptionValueNumber = parseInt(lastOptionValue);
				for(var i = lastOptionValueNumber;i<checkboxSize;i++){
					var no = i + 1;
					var html = "<option value='"+ no +"'>"+ no +"</option>";
					$select.append(html);
				}				
			}else{
				var html = "<option value='1'>1</option>";
				$select.append(html);
			}
		}
		//重新选择选项个数
		var $newOptions = $select.find("option");
		$newOptions.removeAttr("selected");
		
		var newOptionsSize = $newOptions.size();
		
		if(oldKxgsNumber < newOptionsSize){
			//选中最后一个选项
			$select.find("option").eq(oldKxgsNumber-1).attr("selected","selected");
			
		}else if(oldKxgsNumber > newOptionsSize){
			
			$select.find("option:last").attr("selected","selected");
			
		}
	}
	
	//设置可选个数
	function settingKxgsSelect($item){
		
		var $checkboxs = $item.find(":checkbox").not(".is-mandatory");
		var kxgs_number = $checkboxs.size();
		
		var $select = $item.find("select[name='kxgs']");
		var kxgs_value = $select.attr("data-value");
		if(!kxgs_value){
			kxgs_value = kxgs_number;
		}
		
		$select.empty();
		for(var i=0;i<kxgs_number;i++){
			var no_index = i + 1;
			var html = "";
			if(no_index == kxgs_value){
				html = "<option value='"+ no_index +"' selected='selected'>"+ no_index +"</option>";				
			}else{
				html = "<option value='"+ no_index +"'>"+ no_index +"</option>";
			}
			$select.append(html);
		}
	}
	
	//多选组合，追加input标签，设置可选个数列表
	var $item_duxzh = $('.wj-content>.item[data-issue="duxzh"]')
	$item_duxzh.each(function(i,n){
		var $item = $(n);
		var $lastRadio = $item.find(":checkbox:last");
		$lastRadio.parent().parent().parent().addClass("other");
		$lastRadio.parent().after("<input type='text' value='' name='' class='no-sort'/>");
		
		//设置可选个数列表
		settingKxgsSelect($item);
	});
	
	//多选题，设置可选个数列表
	var $item_duxxz = $('.wj-content>.item[data-issue="duxxz"]');
	$item_duxxz.each(function(i,n){
		var $item = $(n);
		
		//设置可选个数列表
		settingKxgsSelect($item);
	});
	
});

//表单提交
$(function(){
	$("#ajaxForm").submit(function(){
		
		//验证
		var $validation = $('[data-toggle*="validation"]').trigger("validation");
		var accept = $validation.valid();
		if(!accept){
			return;
		}
		 var wjst = [];
		 
		 $('.wj-content>.item').each(function(i,n){
			 
			//.item对象在集合中的顺序作为显示顺序
			var xssx = i + 1;
		 	var dataType=$(n).attr("data-type");
		 	
		 	if (dataType == 0){//描述说明
		 		var mssm = $('textarea',$(n)).val();
		 		var dqfs = $(n).find("select[name=dqfs]>option:selected").val()||"center";
				wjst.push({
					type:dataType,
					mssm:mssm,
					xssx:xssx,
					dqfs:dqfs
				});
		 	} else if (dataType == 5){//文本
		 		var title = $(n).find(".official-title").text();
		 		var sfbt = $(n).find(".is-mandatory:checked").size()>0;
		 		var zdzs = $(n).find(":input[name=zdzs]").val();
		 		var wbgd = $(n).find("select[name=wbgd]>option:selected").val();
		 		var wblx = $(n).find("select[name=wblx]>option:selected").val();
		 		var ts = $(n).find("input[name=ts]").val()||"";//提示
		 		wjst.push({
		 			type:dataType,
		 			title:title,
		 			sfbt:sfbt,
		 			xssx:xssx,
		 			zdzs:zdzs,
		 			wbgd:wbgd,
		 			wblx:wblx,
		 			ts:ts
		 		});
		 	} else {//单选、单选组合、多选、多选组合
		 		var title = $(n).find(".official-title").text();
		 		var sfbt = $(n).find(".is-mandatory:checked").size()>0;
		 		var kxgs = $(n).find("select[name=kxgs]>option:selected").val()||1;
		 		var mhxxgs = $(n).find("select[name=mhxxgs]>option:selected").val()||1;
		 		var ts = $(n).find("input[name=ts]").val()||"";//提示
		 		var sfyxpx = $(n).find("select[name=sfyxpx]>option:selected").val()||"0";//是否可排序，仅对多选题有效
		 		if(sfyxpx){
		 			mhxxgs = 1;
		 		}
		 		var stxx = [];
		 		$(n).find(".wj-setting-content :radio,:checkbox:not(.is-mandatory)").each(function(x,y){
		 			//选项名称
		 			var stxx_xxmc = $(y).next().text();
		 			
		 			//跳转试题id
		 			var stxx_tzstid = $(y).attr("tzstid");
		 			
		 			//这里跳转试题是索引值，要变成真正的id
		 			if(stxx_tzstid){
		 				stxx_tzstid = parseInt(stxx_tzstid) - 1;
		 			}
		 			//选项分值
		 			var xxfz = $(y).attr("xxfz");
		 			if(!xxfz){
		 				xxfz = "0";
		 			}
		 			stxx.push({
		 				"xxmc":stxx_xxmc,
		 				"tzstid":stxx_tzstid,
		 				"xxfz":xxfz
		 			});
		 		})
		 		wjst.push({
		 			type:dataType,
		 			title:title,
		 			sfbt:sfbt,
		 			xssx:xssx,
		 			stxx:stxx,
		 			kxgs:kxgs,
		 			mhxxgs:mhxxgs,
		 			sfyxpx:sfyxpx,
		 			ts:ts
		 		});
		 	}
		 })
		 $("#wjst").val(JSON.stringify(wjst));
	});
	
	$(".edit-paper-icon").click(function(){
		 submitForm("ajaxForm",function(responseData,statusText){
			 var wjid= $("#wjid").val();
			 $("#modifyModal").reloadDialog({
				 href:"editWjst/"+wjid+".zf?type=1"
			 })
		 });
	 });
	 
	 KindEditor.create('textarea[name="jsy"]', simpleOption);
	 KindEditor.create('textarea[name="jwy"]', simpleOption);
});

function onchange_kxgs(select){
	var $select = $(select);
	var $item = $select.closest('.item');
	doOnChangeTs($item);
}
function onchange_sfyxpx(select){
	var $select = $(select);
	var $item = $select.closest('.item');
	doOnChangeTs($item);
}
function doOnChangeTs($item){
	var $select_kxgs = $item.find("select[name='kxgs']");
	var $select_sfyxpx = $item.find("select[name='sfyxpx']");
	var $input_ts = $item.find("input[name='ts']");
	var kxgs = $select_kxgs.find("option:selected").val();
	var sfyxpx = $select_sfyxpx.find("option:selected").val();
	var msg = "最多可选"+kxgs+"项";
	if("1" == sfyxpx){
		msg += "，可排序";
	}
	$input_ts.val(msg);
}