/**
 * @discretion	: default messages for the jQuery SiftBox plugin.
 * @author    	: kangzhidong 
 * @email     	: hnxyhcwdl1003@163.com
 * @example   	: 1.引用jquery的库文件js/jquery.js
  				  2.引用样式文件css/uiwidget.siftbox-1.0.0.css
 				  3.引用效果的具体js代码文件 uiwidget.siftbox-1.0.0.js
 				  4.<script language="javascript" type="text/javascript">
					jQuery(function($) {
					
						$("#scrollDiv").siftbox({
							afterRender : function(){
								//这个方法是初始化后的回调函数，在需要做一些事情的时候重写即可
							
							}
						});
						
					});
					</script>
 */
jQuery(function($) {
	
	$.multiui = $.multiui || {};
	$.multiui.widget = $.multiui.widget || {};
	
	$.multiui.widget.ListNav = function(target,options){
		this.initialize.call(this,target,options);	//初始化组件参数
	};
	
	$.multiui.widget.ListNav.prototype = {
		/*组件参数*/
		options: {
			version:'1.0.0',/*版本号*/
			initLetter: '',
	        includeAll: true,
	        allText: 'All',
	        incudeOther: false,
	        otherText: '...',
	        incudeMatch: false,
	        matchText: 'matched',
	        matchSelector:"",
	        includeNums: true,
	        flagDisabled: true,
	        removeDisabled: false,
	        noMatchText: 'No matching entries',
	        showCounts: false,
	        cookieName: null,
	        onClick: $.noop,
	        prefixes: [],
	        listSelector: 'li',
	        filterSelector: '',
			afterRender: $.noop /*组件渲染完成后的回调函数*/
		},
		/*初始化组件参数*/
		initialize : function(target,_options) {
			var ctx = this;
			/*初始化默认行为参数*/
			$.extend(true,ctx.options,_options||{},{
				letters : ['_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '-','matched'],
			    firstClick : false,
		        //detect if you are on a touch device easily.
		        clickEventType:((document.ontouchstart!==null)?'click':'touchstart'),
		        plugin:function(){
					var ctx = this,context = new Array();
					context.push('<div class="ln-letters">');
	                for (var i = 1; i < ctx.options.letters.length; i++) {
	                    if (i === 1) {
	                    	context.push('<a class="all" href="#">'+ ctx.options.allText + '</a><a class="_" href="#">0-9</a>');
	                    }
	                    var text = ((ctx.options.letters[i] === '-') ? ctx.options.otherText : ((ctx.options.letters[i] === 'matched') ? ctx.options.matchText : ctx.options.letters[i].toUpperCase()) );
	                    context.push('<a class="' + ctx.options.letters[i] + '" href="#">' + text + '</a>');
	                }
	                context.push('</div>' + ((ctx.options.showCounts) ? '<div class="ln-letter-count listNavHide">0</div>' : ''));
	                // Remove inline styles, replace with css class
	                // Element will be repositioned when made visible
					return context.join("");
				}
			});
			ctx.options.prefixes = $.map(ctx.options.prefixes, function (n) {
	            return n.toLowerCase();
	        });
			this.buildComponents.call(this,target);	//组装组件html元素
			this.options.afterRender.call(this);	//渲染后的函数回调
		},
		/*构建组件UI*/
		buildComponents : function(target) {
			var ctx = this;
			$(target).each(function(index,element){
				$(element).prev("div.listNav").remove();
				$(element).find(".ln-no-match").remove();
				var $wrapper, $letters, $letterCount,
	                $element = $(this),
	                hasListSelector = (ctx.options.listSelector.length > 0),
	                hasMatchSelector = (ctx.options.matchSelector.length > 0),
	                $list,
	                counts = {},
	                allCount = 0,
	                isAll = true,
	                prevLetter = '',
	                firstClick = false;
				
				if(hasListSelector){
					$list = $element.find(ctx.options.listSelector);
				}else{
					$list = $element.children();
				}
				
				$('<div id="listNav-nav" class="listNav"/>').insertBefore($element);
				$element.append('<div class="ln-no-match listNavHide">' + ctx.options.noMatchText + '</div>');
	            $wrapper =  $element.prev('.listNav');
	            /*将生成的html添加到页面*/
	            $wrapper.empty().html(ctx.options.plugin.call(ctx))
	            //取得当前元素前面的a-z区域
				$letters = $('.ln-letters', $wrapper).slice(0, 1);
				//取得当前元素显示总数的div
                if (ctx.options.showCounts ) {
                    $letterCount = $('.ln-letter-count', $wrapper).slice(0, 1);
                }
                
                // adds a class to each LI that has text content inside of it (ie, inside an <a>, a <div>, nested DOM nodes, etc)
                function addClasses() {
                    var str, spl, $this,
                        firstChar = '',
                        hasPrefixes = (ctx.options.prefixes.length > 0),
                        hasFilterSelector = (ctx.options.filterSelector.length > 0);
                    // Iterate over the list and set a class on each one and use that to filter by
                    var matchedCount = 0;
                    $list.each(function (index,item) {
                    	$this = $(this);
                        // I'm assuming you didn't choose a filterSelector, hopefully saving some cycles
                        if ( !hasFilterSelector ) {
                            //Grab the first text content of the LI, we'll use this to filter by
                            str = $.trim($this.text()).toLowerCase();
                        } else {
                            // You set a filterSelector so lets find it and use that to search by instead
                            str = $.trim($this.find(ctx.options.filterSelector).text()).toLowerCase();
                        }

                        // This will run only if there is something to filter by, skipping over images and non-filterable content.
                        if (str !== '') {
                            // Apply the non-prefix class to LIs that have prefixed content in them
                            if (hasPrefixes) {
                                var prefixes = $.map(ctx.options.prefixes, function(value) {
                                    return value.indexOf(' ') <= 0 ? value + ' ' : value;
                                });
                                var matches = $.grep(prefixes, function(value) {
                                    return str.indexOf(value) === 0;
                                });
                                if (matches.length > 0) {
                                    var afterMatch = str.toLowerCase().split(matches[0])[1];
                                    if(afterMatch != null) {
                                        firstChar = $.trim(afterMatch).charAt(0);
                                    } else {
                                        firstChar = str.charAt(0);
                                    }
                                    addLetterClass(firstChar, $this, true);
                                    return;
                                }
                            }
                            // Find the first letter in the LI, including prefixes
                            firstChar = str.charAt(0);
                            // Doesn't send true to function, which will ++ the All count on prefixed items
                            addLetterClass(firstChar, $this);
                        }
                        
                        if(hasMatchSelector){
                        	if($(item).find(ctx.options.matchSelector).size()>0){
                            	matchedCount ++;
        					}
                        }
                    });
    				
                    counts["matched"] = matchedCount;
                }

                // Add the appropriate letter class to the current element
                function addLetterClass(firstChar, $el, isPrefix) {
                    if ( /\W/.test(firstChar) ) {
                        firstChar = '-'; // not A-Z, a-z or 0-9, so considered "other"
                    }
                    if ( !isNaN(firstChar) ) {
                        firstChar = '_'; // use '_' if the first char is a number
                    }
                    $el.addClass('ln-' + firstChar);
                    if ( counts[firstChar] === undefined ) {
                        counts[firstChar] = 0;
                    }
                    counts[firstChar]++;
                    if (!isPrefix) {
                        allCount++;
                    }
                }

                function addDisabledClass() {
                    for ( var i = 0; i < ctx.options.letters.length; i++) {
                        if ( counts[ctx.options.letters[i]] === undefined ) {
                            $('.' + ctx.options.letters[i], $letters).addClass('ln-disabled');
                        }
                    }
                }
                
                function getLetterCount(el) {
                    if ($(el).hasClass('all')) {
                        return allCount;
                    } else {
                        var count = counts[$(el).attr('class').split(' ')[0]];
                        return (count !== undefined) ? count : 0; // some letters may not have a count in the hash
                    }
                }
                
                
                addClasses();
                
                if (ctx.options.flagDisabled) {
                    addDisabledClass();
                }
                // remove nav items we don't need
                if ( !ctx.options.includeAll ) {
                    $('.all', $letters).remove();
                }
                if ( !ctx.options.includeNums ) {
                    $('._', $letters).remove();
                }
                if ( !ctx.options.includeOther ) {
                    $('.-', $letters).remove();
                }
                if ( !ctx.options.incudeMatch ) {
                    $('.matched', $letters).remove();
                }
                if ( ctx.options.removeDisabled ) {
                    $('.ln-disabled', $letters).remove();
                }
                $(':last', $letters).addClass('ln-last');
                if ( $.cookie && (ctx.options.cookieName !== null) ) {
                    var cookieLetter = $.cookie(ctx.options.cookieName);
                    if ( cookieLetter !== null ) {
                    	ctx.options.initLetter = cookieLetter;
                    }
                }

                // decide what to show first
                // Is there an initLetter set, if so, show that letter first
                if ( ctx.options.initLetter !== '' ) {
                    firstClick = true;
                    // click the initLetter if there was one
                    $('.' + ctx.options.initLetter.toLowerCase(), $letters).slice(0, 1).trigger(ctx.options.clickEventType);
                } else {
                    // If no init letter is set, and you included All, then show it
                    if ( ctx.options.includeAll ) {
                        // make the All link look clicked, but don't actually click it
                        $('.all', $letters).addClass('ln-selected');
                    } else {
                        // All was not included, lets find the first letter with a count and show it
                        for ( var i = ((ctx.options.includeNums) ? 0 : 1); i < ctx.options.letters.length; i++) {
                            if ( counts[ctx.options.letters[i]] > 0 ) {
                                firstClick = true;
                                $('.' + ctx.options.letters[i], $letters).slice(0, 1).trigger(ctx.options.clickEventType);
                                break;
                            }
                        }
                    }
                }
                
                if (ctx.options.showCounts) {
                    // sets the top position of the count div in case something above it on the page has resized
                    $wrapper.mouseover(function () {
                    	// we're going to need to subtract this from the top value of the wrapper to accomodate changes in font-size in CSS.
                        var letterCountHeight = $letterCount.outerHeight();
                        $letterCount.css({
                            top: $('a:first', $wrapper).slice(0, 1).position().top - letterCountHeight
                            // we're going to grab the first anchor in the list
                            // We can no longer guarantee that a specific letter will be present
                            // since adding the "removeDisabled" option
                        });
                    });
                    
                    //shows the count above the letter
                    $('.ln-letters a', $wrapper).mouseover(function () {
                        var left = $(this).position().left,
                            width = ($(this).outerWidth()) + 'px',
                            letter = $this.attr('class').split(' ')[0];
                        var count = 0;
                        if(letter=='matched'){
                        	count = ctx.options.getMatchedCount(this);
                        }else{
                        	count = getLetterCount(this);
                        }
                        $letterCount.css({
                            left: left,
                            width: width
                        }).text(count).addClass("letterCountShow").removeClass("listNavHide"); // set left position and width of letter count, set count text and show it
                    }).mouseout(function () { // mouseout for each letter: hide the count
                        $letterCount.addClass("listNavHide").removeClass("letterCountShow");
                    });
                }

                // click handler for letters: shows/hides relevant LI's
                $('a', $letters).bind(ctx.options.clickEventType, function (e) {
                    e.preventDefault();
                    var $this = $(this),
                        letter = $this.attr('class').split(' ')[0],
                        noMatches = $element.find('.ln-no-match');

                    if ( prevLetter !== letter ) {
                    // Only to run this once for each click, won't double up if they clicked the same letter
                    // Won't hinder firstRun
                        $('a.ln-selected', $letters).removeClass('ln-selected');
                        
                        //hidden all mathed item
                        if(hasMatchSelector){
                     		$list.each(function(index,item){
                     			var size = $(item).find(ctx.options.matchSelector).size();
            					if(size>0){
            						$(item).addClass("listNavHide").removeClass("listNavShow");
            					}
                     		});
                     	}
                        
                        
                        if ( letter === 'all' ) {
                            // If ALL button is clicked:
                            $list.addClass("listNavShow").removeClass("listNavHide"); // Show ALL
                            noMatches.addClass("listNavHide").removeClass("listNavShow"); // Hide the list item for no matches
                            isAll = true; // set this to quickly check later
                        } else if ( letter === 'matched' ) {
                         	
                         	var selectedNumber = 0;
                         	if(hasMatchSelector){
                         		$list.each(function(index,item){
                         			var size = $(item).find(ctx.options.matchSelector).size();
                					if(size>0){
                						selectedNumber += size;
                						$(item).addClass("listNavShow").removeClass("listNavHide");
                					}else{
                						$(item).addClass("listNavHide").removeClass("listNavShow");
                					}
                         		});
                         	}
            		    	if(selectedNumber==0){
            		    		noMatches.addClass("listNavShow").removeClass("listNavHide");
            		    	}else{
            		    		noMatches.addClass("listNavHide").removeClass("listNavShow");
            		    	}
                        }else {
                            // If you didn't click ALL
                            if ( isAll ) {
                                // since you clicked ALL last time:
                                $list.addClass("listNavHide").removeClass("listNavShow");
                                isAll = false;
                            } else if (prevLetter !== '') {
                                $list.filter('.ln-' + prevLetter).addClass("listNavHide").removeClass("listNavShow");
                            }

                            var count = getLetterCount(this);
                            if (count > 0) {
                                $list.filter('.ln-' + letter).addClass("listNavShow").removeClass("listNavHide");
                                noMatches.addClass("listNavHide").removeClass("listNavShow"); // in case it's showing
                            } else {
                                noMatches.addClass("listNavShow").removeClass("listNavHide");
                            }
                        }

                        prevLetter = letter;

                        if ($.cookie && (opts.cookieName !== null)) {
                            $.cookie(ctx.options.cookieName, letter, {
                                expires: 999
                            });
                        }
                        
                        $this.addClass('ln-selected');
                        $this.blur();
                        if (!firstClick && (ctx.options.onClick !== null)) {
                        	ctx.options.onClick(letter);
                        } else {
                            firstClick = false; //return false;
                        }
                    } // end if prevLetter !== letter

                }); // end click()
			});
		}
	};
	
	$.fn.extend({
		listnav:function(options){
			return new $.multiui.widget.ListNav(this, options);
		}
	});
	
});