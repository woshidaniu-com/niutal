/*
 * 三级联动调用示例，若不需联动去掉nextNode节点
 *
jQuery(function(){
	jQuery('#xydm').textClue({
		id:'xydm',
		divId:'xyDiv',
		url:_path+'/zfxg!plugins/znxgl_getXyList.html',
		listKey:'xydm',
		listText:'xymc',
		nextNode:{
			id:'zydm',
			divId:'zyDiv',
			url:_path+'/zfxg!plugins/znxgl_getZyList.html',
			listKey:'zydm',
			listText:'zymc',
			nextNode:{
				id:'bjdm',
				divId:'bjDiv',
				url:_path+'/zfxg!plugins/znxgl_getBjList.html',
				listKey:'bjdm',
				listText:'bjmc'
			}
		}
	});
}) 
 */
(function($) {

	$.fn.textClue = function textClue(obj) {
		
		var id=this.attr('id');
		
		var autoComplete = {
			pop_cn : 'autoDis',
			divId : obj.divId,
			hover_cn : 'cur',
			showState : '0',
			source : [], 
			sourceKey : [],
			url : obj.url,
			params : obj.params==null ? {} : obj.params,
			listKey : obj.listKey,
			listText : obj.listText,
			valueId : obj.valueId,
			refData:obj.refData==null ? false : obj.refData,//是否动态加载数据
			nextNode:obj.nextNode,
			allowClear: obj.allowClear==null ? true : obj.allowClear,//如果输入值不存在下拉列表，是否允许清空
			event: obj.event,//事件
			//初始化标签
			init : function() {
				$.ajaxSetup({async : false});
					this.initData(this.url, this.params, this.listKey,this.listText);
					this.setDom();
					this.bind(document.getElementById(id));
					//设置初始下拉内容
					this.initNextSelect();
				$.ajaxSetup({async : true});
				return autoComplete;
			},
			//初始化数据
			initData : function(url, params, listKey, listText) {
				
				var newParams = params;
				
				if (params["parentKey"] != null){
					var parentKey = params["parentKey"].split(",");
					
					for (var i = 0 ; i < parentKey.length ; i++){
						newParams[parentKey[i]] = $('#'+parentKey[i]).val();
					}
				}
				
				$.post(url, newParams, function(data) {
					
					var keys = '';
					var texts = '';
					for ( var i = 0; i < data.length; i++) {
						if (keys == "") {
							keys = data[i][listKey];
							texts = data[i][listText];
						} else {
							keys = keys + "," + data[i][listKey];
							texts = texts + "," + data[i][listText];
						}
					}
					autoComplete.source = texts.split(",");
					autoComplete.sourceKey = keys.split(",");
				}, 'json');
			},
			//初始化下拉
			initNextSelect : function() {
				var sValue=document.getElementById(id).value;
				var tValue="";
				if(this.nextNode != null && this.source != null){
					for ( var i = 0; i < this.source.length; i++) {
						if(this.source[i] == sValue){
							tValue=this.sourceKey[i];
							break;
						}
					}
					//this.setValue(sValue, tValue);
					this.setNextNode(this.nextNode,tValue);
				}
			},
			setDom : function() {
				var self = this;
				var dom;
				
				if (document.getElementById(self.divId)){
					dom = document.getElementById(self.divId);
				} else {
					dom = document.createElement('div'), 
					frame = document.createElement('iframe'), 
					ul = document.createElement('ul');
					document.body.appendChild(dom);
					
					with (frame) { // 用来在ie6下遮住select元素
						setAttribute('frameborder', '0');
						setAttribute('scrolling', 'no');
						style.cssText = 'z-index:-1;position:absolute;left:0;top:0;';
					}
					with (dom) { // 对弹出层li元素绑定onmouseover，onmouseover
						className = this.pop_cn;
						id = this.divId;
						appendChild(frame);
						appendChild(ul);
						onmouseover = function(e) { // 在li元素还没有加载的时候就绑定这个方法，通过判断target是否是li元素进行处理
							e = e || window.event;
							var target = e.srcElement || e.target;
							if (target.tagName == 'LI') { // 添加样式前先把所有的li样式去掉，这里用的是一种偷懒的方式，没有单独写removeClass方法
								jQuery("li ."+self.hover_cn).removeClass(self.hover_cn);
								jQuery(target).addClass(self.hover_cn);
								self.showState = '1';
							}
							dom.focus();
						};
						onmouseout = function(e) {
							e = e || window.event;
							var target = e.srcElement || e.target;
							if (target.tagName == 'LI'){
								target.className = '';
								self.showState = '0';
							}
						};
						onfocus = function() {
							self.pop.style.display = 'block';
						};
						onblur = function(){
							self.pop.style.display = 'none';
						};
					}
				}
//				
				this.pop = dom;

			},
			bind : function(x) {
				var self = this;
				x.onkeyup = function(e) {
					e = e || window.event;
					var lis = self.pop.getElementsByTagName('li'), 
						lens = lis.length, 
						n = lens, 
						temp;
					if (e.keyCode == 38) { // 键盘up键被按下
						if (self.pop.style.display != 'none') {
							for ( var i = 0; i < lens; i++) { // 遍历结果数据，判断是否被选中
								if (lis[i].className)
									temp = i;
								else
									n--;
							}
							if (n == 0) { // 如果没有被选中的li元素，则选中最后一个
								lis[lens - 1].className = self.hover_cn;
								autoComplete.setValue(lis[lens - 1].title,lis[lens - 1].id);
							} else { // 如果有被选中的元素，则选择上一个元素并赋值给input
								if (lis[temp] == lis[0]) { // 如果选中的元素是第一个孩子节点则跳到最后一个选中
									lis[lens - 1].className = self.hover_cn;
									lis[temp].className = '';
									autoComplete.setValue(lis[lens - 1].title,lis[lens - 1].id);
								} else {
									lis[temp - 1].className = self.hover_cn;
									lis[temp].className = '';
									autoComplete.setValue(lis[temp - 1].title, lis[temp - 1].id);
								}
							}
						} else
							// 如果弹出层没有显示则执行插入操作，并显示弹出层
							self.insert(this);
					} else if (e.keyCode == 40) { // down键被按下，原理同up键
						if (self.pop.style.display != 'none') {
							for ( var i = 0; i < lens; i++) {
								if (lis[i].className)
									temp = i;
								else
									n--;
							}
							if (n == 0) {
								lis[0].className = self.hover_cn;
								autoComplete.setValue(lis[0].title,lis[0].id);
							} else {
								if (lis[temp] == lis[lens - 1]) {
									lis[0].className = self.hover_cn;
									lis[temp].className = '';
									autoComplete.setValue(lis[0].title,lis[0].id);
								} else {
									lis[temp + 1].className = self.hover_cn;
									lis[temp].className = '';
									autoComplete.setValue(lis[temp + 1].title,lis[temp + 1].id);
								}
							}
						} else {
							self.insert(this);
						}
					} else if (e.keyCode == 13) {
						self.hiddenAll();
					} else if (e.keyCode == 9) {
					} else {
						// 如果按下的键既不是up又不是down那么直接去匹配数据并插入
						self.insert(this);
						//动态查询
						if (self.refData){
							autoComplete.params[id]=x.value;
							autoComplete.init();
							self.showAll(this);
						}
						
					}
					return false;
				};
				x.onblur = function(e) {
					e = e || window.event;
					var target = e.srcElement || e.target;
					
					if (self.showState=='0' && target.tagName != "INPUT"){
						$('#'+self.divId).css('display','none');
					}
				};
				x.onfocus = function() {
					self.showAll(this);
				};
				x.onchange = function(){
					var textVal = x.value;
					var dataName = self.source;
					var dataValue = self.sourceKey;
					var isUpdate = false;
					for(var i=0;i< dataName.length;i++){
						if(dataName[i] == textVal){
							self.setValue(textVal,dataValue[i]);
							isUpdate = true;
							break;
						}
						isUpdate = false;				
					}
					if(!isUpdate && self.allowClear){
						self.setValue("","");
					}else if (!self.allowClear && self.valueId != id){
						$("#"+self.valueId).val("");
					}  
				};
				return this;
			},
			//插入数据
			insert : function(self) {
				var thisObj = this;
				var bak = [], s, li = [], left = 0, top = 0, val = self.value;
				var bakKey = [];
				for ( var i = 0, leng = this.source.length; i < leng; i++) { // 判断input的数据是否与数据源里的数据一致
					if (!!val && val.length <= this.source[i].length
							&& this.source[i].substr(0, val.length) == val) {
						bak.push(this.source[i]);
						bakKey.push(this.sourceKey[i]);
					}
				}
				if (bak.length == 0) { // 如果没有匹配的数据则隐藏弹出层
					this.pop.style.display = 'none';
					return false;
				}
				// 这个弹出层定位方法之前也是用循环offsetParent，但发现ie跟ff下差别很大（可能是使用方式不当），所以改用这个getBoundingClientRect
				left = self.getBoundingClientRect().left
						+ document.documentElement.scrollLeft;
				top = self.getBoundingClientRect().top
						+ document.documentElement.scrollTop
						+ self.offsetHeight;
				with (this.pop) {
					style.cssText = 'width:' + self.offsetWidth + 'px;'
							+ 'position:absolute;left:' + left + 'px;top:'
							+ top + 'px;display:none;';
					getElementsByTagName('iframe')[0].setAttribute('width',
							self.offsetWidth);
					getElementsByTagName('iframe')[0].setAttribute('height',
							self.offsetHeight);
					onclick = function(e) {
						e = e || window.event;
						var target = e.srcElement || e.target;
						if (target.tagName == 'LI') {
							autoComplete.setValue(target.title,target.id);
							this.style.display = 'none';
						}
					};
				}
				s = bak.length;
				
				for ( var i = 0; i < s; i++) {
					li.push('<li title="' + bak[i] + '"  id="'+ bakKey[i] + '" style="width:98%;text-overflow:ellipsis;white-space:nowrap;overflow:hidden;">' + bak[i] + '</li>');
				}

				this.pop.getElementsByTagName('ul')[0].innerHTML = li.join('');
				this.pop.style.display = 'block';
				return false;
			},
			showAll : function(self) {
				var thisObj = this;
				var key = "";
				var bak = this.source, s = this.source.length, li = [], left = 0, top = 0;
				var bakKey = this.sourceKey;
				if (!(s == 1 && this.source[0] == '')) {
					var uls = this.pop.getElementsByTagName('ul');
					
					for ( var i = 0; i < s; i++) {
						li.push('<li title="' + bak[i] + '"  id="'+ bakKey[i] + '" style="width:98%;text-overflow:ellipsis;white-space:nowrap;overflow:hidden;">' + bak[i] + '</li>');
					}

					// 这个弹出层定位方法之前也是用循环offsetParent，但发现ie跟ff下差别很大（可能是使用方式不当），所以改用这个getBoundingClientRect
					left = self.getBoundingClientRect().left + document.documentElement.scrollLeft;
					top = self.getBoundingClientRect().top
							+ document.documentElement.scrollTop
							+ self.offsetHeight;
					with (this.pop) {
						style.cssText = 'width:' + (self.offsetWidth-2) + 'px;'
								+ 'position:absolute;left:' + left + 'px;top:'
								+ top + 'px;';
						getElementsByTagName('iframe')[0].setAttribute('width',
								self.offsetWidth);
						getElementsByTagName('iframe')[0].setAttribute(
								'height', self.offsetHeight);
						onclick = function(e) {
							e = e || window.event;
							var target = e.srcElement || e.target;
							if (target.tagName == 'LI') {
								autoComplete.setValue(target.title,target.id);
								this.style.display = 'none';
							}
						};
					}
					uls.innerHTML = '';// 清空当前层
					uls[0].innerHTML = li.join('');// 加入数据源
					if (this.showState == '0') {
						this.pop.style.display = 'block';
						$('#'+this.divId).css('display','');
					}
				}
				
			},
			hiddenAll : function() {
				if (this.showState == '0') {
					this.pop.style.display = 'none';
					$('#'+this.divId).css('display','none');
				}
			},
			setValue : function(selfValue,targetValue){
				if (this.nextNode != null){
					this.clearNextNode(this.nextNode);
					this.setNextNode(this.nextNode,targetValue);
				}
				
				$('#'+id).val(selfValue);
				
				if (this.valueId != null){
					$('#'+this.valueId).val(targetValue);
				}
				
				if (this.event != null && this.event.onselect != null){
					var selectFun = this.event.onselect;
					selectFun();
				}
			},
			clearNextNode : function(nextNode){
				var node = nextNode;
				while (node != null){
					$('#'+node.id).val('');
					$('#'+node.valueId).val('');
					$('#'+node.divId).css('display','none');
					node = node.nextNode;
				}
			},
			setNextNode : function(nextNode,parentValue){
				
				var nextParams = nextNode.params == null ? {} : nextNode.params;
				nextParams["parentValue"] = parentValue;
				
				$('#'+nextNode.id).textClue({
					id:nextNode.id,
					divId:nextNode.divId,
					url:nextNode.url,
					listKey:nextNode.listKey,
					listText:nextNode.listText,
					valueId:nextNode.valueId,
					nextNode:nextNode.nextNode,
					allowClear:nextNode.allowClear,
					params:nextParams
				});
			}
		};
		return autoComplete.init();
	}
})(jQuery);

