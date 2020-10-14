/**
 * 前端分页插件
 */
!function($) {
  'use strict';
	$.fn.pagination = function(maxentries, opts) {
		
		opts = jQuery.extend({
			items_per_page: 10,
			num_display_entries: 5,
			current_page: 0,
			num_edge_entries: 1,
			link_to: "javascript:void(0)",
			prev_text: '<span aria-hidden="true">&larr;</span>上一页',
			next_text: '下一页<span aria-hidden="true">&rarr;</span>',
			ellipse_text: "...",
			prev_show_always: true,
			next_show_always: true,
			callback: function() {
				return false;
			}
		}, opts || {});
		
		return this.each(function() {
			/**
			 * Calculate the maximum number of pages
			 */
			function numPages() {
				return Math.ceil(maxentries / opts.items_per_page);
			}

			/**
			 * Calculate start and end point of pagination links depending on 
			 * current_page and num_display_entries.
			 * @return {Array}
			 */
			function getInterval() {
				var ne_half = Math.ceil(opts.num_display_entries / 2);
				var np = numPages();
				var upper_limit = np - opts.num_display_entries;
				var start = current_page > ne_half ? Math.max(Math.min(
						current_page - ne_half, upper_limit), 0) : 0;
				var end = current_page > ne_half ? Math.min(
						current_page + ne_half, np) : Math.min(
						opts.num_display_entries, np);
				return [start, end];
			}

			/**
			 * This is the event handling function for the pagination links. 
			 * @param {int} page_id The new page number
			 */
			function pageSelected(page_id, evt) {
				current_page = page_id;
				drawLinks_simple();
				var continuePropagation = opts.callback(page_id, panel);
				if (!continuePropagation) {
					if (evt.stopPropagation) {
						evt.stopPropagation();
					} else {
						evt.cancelBubble = true;
					}
				}
				return continuePropagation;
			}

			/**
			 * 简单版本的
			 * 
			 * 	//	
				//  <nav id="pager" data-for="my-topic-data">
				//	<ul class="pager">
				//		<li class="previous"><a href="#">上一页</a></li>
				//		<li class="next"><a href="#">下一页 </a></li>
				//	</ul>
				//</nav>
			 */
			function drawLinks_simple() {

				panel.empty();
				var list = jQuery("<ul class='pager'></ul>");
				panel.append(list);

				var interval = getInterval();
				var np = numPages();
				// This helper function returns a handler function that calls pageSelected with the right page_id
				var getClickHandler = function(page_id) {
					return function(evt) {
						return pageSelected(page_id, evt);
					}
				}
				// Helper function for generating a single link (or a span tag if it's the current page)
				var appendItem = function(page_id, appendopts) {
					page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
					appendopts = jQuery.extend({
						text : page_id + 1,
						classes : ""
					}, appendopts || {});
					if (page_id == current_page) {
						var clazz = appendopts.side ? 'disabled' : 'active';
						var lstItem = jQuery("<li class='" + clazz + "'><a>" + (appendopts.text) + "</a></li>");
					} else {
						var a = jQuery("<a>" + (appendopts.text) + "</a>")
							.attr('href', opts.link_to.replace(/__id__/, page_id));
						var lstItem = jQuery("<li></li>").bind("click", getClickHandler(page_id));
						lstItem.append(a);
					}
					if (appendopts.classes) {
						lstItem.addClass(appendopts.classes);
					}
					list.append(lstItem);
				}
				// Generate "Previous"-Link
				if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
					appendItem(current_page - 1, {
						text : opts.prev_text,
						side : true,
						classes:'previous'
					});
				}
				
				// Generate starting points
				if (interval[0] > 0 && opts.num_edge_entries > 0) {
					var end = Math.min(opts.num_edge_entries, interval[0]);
					for ( var i = 0; i < end; i++) {
						appendItem(i);
					}
					if (opts.num_edge_entries < interval[0] && opts.ellipse_text) {
						jQuery("<li class='disabled'><a>" + opts.ellipse_text + "</a></li>").appendTo(list);
					}
				}
				// Generate interval links
				for ( var i = interval[0]; i < interval[1]; i++) {
					appendItem(i);
				}
				// Generate ending points
				if (interval[1] < np && opts.num_edge_entries > 0) {
					if (np - opts.num_edge_entries > interval[1] && opts.ellipse_text) {
						jQuery("<li class='disabled'><a>" + opts.ellipse_text + "</a></li>").appendTo(list);
					}
					var begin = Math.max(np - opts.num_edge_entries,
							interval[1]);
					for ( var i = begin; i < np; i++) {
						appendItem(i);
					}
				}
				
				// Generate "Next"-Link
				if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
					appendItem(current_page + 1, {
						text : opts.next_text,
						side : true,
						classes:'next'
					});
				}
			}
			
			/**
			 * This function inserts the pagination links into the container element
			 */
			function drawLinks() {
				panel.empty();
				var list = jQuery("<ul></ul>");
				panel.append(list);

				var interval = getInterval();
				var np = numPages();
				// This helper function returns a handler function that calls pageSelected with the right page_id
				var getClickHandler = function(page_id) {
					return function(evt) {
						return pageSelected(page_id, evt);
					}
				}
				// Helper function for generating a single link (or a span tag if it's the current page)
				var appendItem = function(page_id, appendopts) {
					page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
					appendopts = jQuery.extend({
						text : page_id + 1,
						classes : ""
					}, appendopts || {});
					if (page_id == current_page) {
						var clazz = appendopts.side ? 'disabled' : 'active';
						var lstItem = jQuery("<li class='" + clazz + "'><a>" + (appendopts.text) + "</a></li>");
					} else {
						var a = jQuery("<a>" + (appendopts.text) + "</a>")
							.attr('href', opts.link_to.replace(/__id__/, page_id));
						var lstItem = jQuery("<li></li>").bind("click", getClickHandler(page_id));
						lstItem.append(a);
					}
					if (appendopts.classes) {
						lstItem.addClass(appendopts.classes);
					}
					list.append(lstItem);
				}
				// Generate "Previous"-Link
				if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
					appendItem(current_page - 1, {
						text : opts.prev_text,
						side : true
					});
				}
				// Generate starting points
				if (interval[0] > 0 && opts.num_edge_entries > 0) {
					var end = Math.min(opts.num_edge_entries, interval[0]);
					for ( var i = 0; i < end; i++) {
						appendItem(i);
					}
					if (opts.num_edge_entries < interval[0] && opts.ellipse_text) {
						jQuery("<li class='disabled'><a>" + opts.ellipse_text + "</a></li>").appendTo(list);
					}
				}
				// Generate interval links
				for ( var i = interval[0]; i < interval[1]; i++) {
					appendItem(i);
				}
				// Generate ending points
				if (interval[1] < np && opts.num_edge_entries > 0) {
					if (np - opts.num_edge_entries > interval[1] && opts.ellipse_text) {
						jQuery("<li class='disabled'><a>" + opts.ellipse_text + "</a></li>").appendTo(list);
					}
					var begin = Math.max(np - opts.num_edge_entries,
							interval[1]);
					for ( var i = begin; i < np; i++) {
						appendItem(i);
					}
				}
				// Generate "Next"-Link
				if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
					appendItem(current_page + 1, {
						text : opts.next_text,
						side : true
					});
				}
			}

			// Extract current_page from options
			var current_page = opts.current_page;
			// Create a sane value for maxentries and items_per_page
			maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
			opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1 : opts.items_per_page;
			// Store DOM element for easy access from all inner functions
			var panel = jQuery(this);
			// Attach control functions to the DOM element 
			this.selectPage = function(page_id) {
				pageSelected(page_id);
			};
			this.prevPage = function() {
				if (current_page > 0) {
					pageSelected(current_page - 1);
					return true;
				} else {
					return false;
				}
			};
			this.nextPage = function() {
				if (current_page < numPages() - 1) {
					pageSelected(current_page + 1);
					return true;
				} else {
					return false;
				}
			};
			
			//初始化代码，先执行，然后初始化控件，这里可以是第一次去数据的地方
			
			// When all initialisation is done, draw the links
			drawLinks_simple();
			// call callback function
			//opts.callback(current_page, this);
		});
	}
}(window.jQuery);