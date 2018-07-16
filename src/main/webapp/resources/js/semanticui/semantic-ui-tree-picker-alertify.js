(function() {
  $.fn.treePicker = function(options) {
    var actionButtons, config, count, initialize, initializeNodeList, initializeNodes, loadNodes, modal, nodeClicked, nodeIsPicked, nodes, pickNode, picked, recursiveNodeSearch, renderList, renderTree, showPicked, showSearch, showTree, tabs, unpickNode, updatePickedIds, updatePickedNodes, widget;
    widget = $(this);
    picked = [];
    nodes  = [];
    tabs   = {};
//    modal = $("<div class=\"ui tree-picker modal\">\n  <i class=\"close icon\"></i>\n  <div class=\"header\">\n    " + options.name + "\n\n    <div class=\"ui menu\">\n      <a class=\"active tree item\">\n        <i class=\"list icon\"></i> Выбрать\n      </a>\n      <a class=\"picked item\">\n        <i class=\"checkmark icon\"></i> Выбранные <span class=\"count\"></span>\n      </a>\n    </div>\n  </div>\n  <div class=\"ui search form\">\n    <div class=\"field\">\n      <div class=\"ui icon input\">\n        <input type=\"text\" placeholder=\"Поиск\">\n        <i class=\"search icon\"></i>\n      </div>\n    </div>\n  </div>\n  <div class=\"content\">\n    <div class=\"ui active inverted dimmer\"><div class=\"ui text loader\">Loading data</div></div>\n    <div class=\"tree-tab\">\n      <div style=\"height: 400px\"></div>\n    </div>\n\n    <div class=\"search-tab\">\n    </div>\n\n    <div class=\"picked-tab\">\n    </div>\n  </div>\n  <div class=\"actions\">\n    <a class=\"pick-search\"><i class=\"checkmark icon\"></i> Выбрать все</a>\n    <a class=\"unpick-search\"><i class=\"remove icon\"></i> Убрать все</a>\n    <a class=\"unpick-picked\"><i class=\"remove icon\"></i> Убрать все</a>\n    <a class=\"ui blue button accept\">Принять</a>\n    <a class=\"ui button close\">Отмена</a>\n  </div>\n</div>").modal({
//      duration: 200
//    });
/*
	var header=$(' <div class="ui tree-picker amodal" id="aheaderid">\
                      <div class="header" >\
                           Подразделения \
                           <div class="ui menu">\
                            <a class="active tree item" id="idtree">\
	                           <i class="list icon"></i> \
	                             Выбрать\
	                        </a>\
	                        <a class="picked item"  id="idpicked">\
	                           <i class="checkmark icon"></i>\
	                             Выбранные <span class="count"></span>\
                            </a>\
                           </div>\
                       </div>\
                       <div class="ui search form">\
                         <div class="field">\
                            <div class="ui icon input">\
	                          <input type="text" placeholder="Поиск">\
	                          <i class="search icon"></i>\
	                        </div>\
                         </div>\
                       </div>\
                    </div>');
var content=$('<div class="content ui tree-picker amodal" id="acontentid">\
                  <div class="ui  inverted dimmer">\
	                 <div class="ui text loader">Загрузка данных</div>\
                  </div>\
                  <div class="active tree-tab">\
	                 <div style="height: 400px"></div>\
                  </div>\
	              <div class="search-tab"></div>\
	              <div class="picked-tab"></div>\
               </div>');
*/
	var content=$(' <div class="ui tree-picker amodal" id="contentid">\
                           <div class="ui menu">\
                            <a class="active tree item" id="idtree">\
	                           <i class="list icon"></i> \
	                             Выбрать\
	                        </a>\
	                        <a class="picked item"  id="idpicked">\
	                           <i class="checkmark icon"></i>\
	                             Выбранные <span class="count"></span>\
                            </a>\
                           </div>\
                       <div class="ui search form">\
                         <div class="field">\
                            <div class="ui icon input">\
	                          <input type="text" placeholder="Поиск">\
	                          <i class="search icon"></i>\
	                        </div>\
                         </div>\
                       </div>\
                  <div class="ui  inverted dimmer">\
	                 <div class="ui text loader">Загрузка данных</div>\
                  </div>\
                  <div class="active tree-tab">\
	                 <div style="height: 400px"></div>\
                  </div>\
	              <div class="search-tab"></div>\
	              <div class="picked-tab"></div>\
               </div>');

    count = $('.count', content);
    tabs = {
      tree  : $('.tree-tab'  , content),
      search: $('.search-tab', content),
      picked: $('.picked-tab', content)
    };
    actionButtons = {
      pickSearch   : $('.actions .pick-search'   , content),
      unpickSearch : $('.actions .unpick-search' , content),
      unpickPicked : $('.actions .unpick-picked' , content)
    };
    config = {
      childrenKey: 'nodes',
      singlePick: false,
      minSearchQueryLength: 3,
      hidden: function(node) {
        return false;
      },
      disabled: function(node) {
        return false;
      },
      displayFormat: function(picked) {
        return options.name + " (Выбрано " + picked.length + ")";
      }
    };
    $.extend(config, options);
    initialize = function() {
      if (config.data) {
        nodes = config.data;
      }
      if (widget.attr("data-picked-ids")) {
        config.picked = widget.attr("data-picked-ids").split(",");
      }
      if (config.picked) {
        if (nodes.length) {
//          console.log('inside nodes.length>0');
          updatePickedNodes();
          widget.html(config.displayFormat(picked));
        } else {
          widget.html(config.displayFormat(config.picked));
        }
      } else {
        widget.html(config.displayFormat([]));
      }
      widget.off('click');
      widget.on('click', function(e) {
        alertify.confirm('',function(){
//		console.log('success');	
		config.onSubmit(picked);	
//        alertify.success('Accepted');
		},function(){
//		console.log('rejected');	
//        alertify.error('Declined');
    }).set({
		    labels:{ok:'Выбрать', cancel: 'Отказ'},
			'overflow': true,
			'resizable': false,
    	    'padding': false,
			'maximizable': true,
			'pinnable': true
//			'startMaximized':true
			})
	.setHeader('Выбор подразделений')
	.setContent(content[0]).show();
		  
		  
      $('.ui.active.dimmer', content).removeClass('active');
          return initializeNodes(nodes);
      });
      $('.actions .accept', content).off('click');
      $('.actions .accept', content).on('click', function(e) {
        if (config.onSubmit) {
          config.onSubmit(picked);
        }
        return widget.html(config.displayFormat(picked));
      });
      actionButtons.pickSearch.off('click');
      actionButtons.pickSearch.on('click', function(e) {
        return $('.search-tab .node:not(.picked) .name').trigger('click');
      });
      actionButtons.unpickSearch.off('click');
      actionButtons.unpickSearch.on('click', function(e) {
        return $('.search-tab .node.picked .name').trigger('click');
      });
      actionButtons.unpickPicked.off('click');
      actionButtons.unpickPicked.on('click', function(e) {
        return $('.picked-tab .node.picked .name').trigger('click');
      });
//      $('.menu .tree', content).on('click', function(e) {

      $('#idtree', content).off('click');
      $('#idtree', content).on('click', function(e) {
        return showTree();
      });
//	  console.log('len='+$('#idpicked', content).length);
//      $('.menu .picked', content).on('click', function(e) {
      $('#idpicked', content).off('click');
      $('#idpicked', content).on('click', function(e) {
//        console.log('picked clicked->'+JSON.stringify(picked));
		return showPicked();
      });
      return $('.search input', content).on('keyup', function(e) {
        return showSearch($(this).val());
      });
    };
    initializeNodes = function(nodes) {
      var tree;
      updatePickedNodes();
      tree = renderTree(nodes, {
        height: '400px',
        overflowY: 'scroll'
      });
      tabs.tree.html(tree);
      return initializeNodeList(tree);
    };
    updatePickedNodes = function() {
      var i, id, len, ref, results1, searchResult;
      if (config.picked) {
        picked = [];
        ref = config.picked;
        results1 = [];
        for (i = 0, len = ref.length; i < len; i++) {
          id = ref[i];
          searchResult = recursiveNodeSearch(nodes, function(node) {
            return ("" + node.id) === ("" + id);
          });
          if (searchResult.length) {
            results1.push(picked.push(searchResult[0]));
          } else {
            results1.push(void 0);
          }
        }
//        console.log('updatePickedNodes picked='+JSON.stringify(picked));
//        console.log('updatePickedNodes results='+JSON.stringify(results1));
          return results1;
      }
    };
    showTree = function() {
      $('.menu .item', content).removeClass('active');
      $('.menu .tree', content).addClass('active');
      tabs.tree.show();
      tabs.search.hide();
      tabs.picked.hide();
      return content.attr('data-mode', 'tree');
    };
    showSearch = function(query) {
      var foundNodes, list;
//	  console.log('inside showSearch. query='+query);
      if (query !== null && query.length >= config.minSearchQueryLength) {
        foundNodes = recursiveNodeSearch(nodes, function(node) {
          return node.name && node.name.toLowerCase().indexOf(query.toLowerCase()) > -1;
        });
        list = renderList(foundNodes, {
          height: '400px',
          overflowY: 'scroll'
        });
        $('.menu .item', content).removeClass('active');
        tabs.search.show().html(list);
        tabs.tree.hide();
        tabs.picked.hide();
        content.attr('data-mode', 'search');
        initializeNodeList(list);
        return $('.name', list).each(function() {
          var name, regex;
          name = $(this).text();
          regex = RegExp('(' + query + ')', 'gi');
          name = name.replace(regex, "<strong class='search-query'>$1</strong>");
          return $(this).html(name);
        });
      } else {
        return showTree();
      }
    };
    showPicked = function() {
      var list;
      list = renderList(picked, {
        height: '400px',
        overflowY: 'scroll'
      });
      $('.menu .item', content).removeClass('active');
      $('.menu .picked', content).addClass('active');
      tabs.picked.show().html(list);
      tabs.tree.hide();
      tabs.search.hide();
      content.attr('data-mode', 'picked');
      return initializeNodeList(list);
    };
    renderTree = function(nodes, css) {
      var i, len, node, nodeElement, tree;
      if (css == null) {
        css = {};
      }
      tree = $('<div class="ui tree-picker tree"></div>').css(css);
      for (i = 0, len = nodes.length; i < len; i++) {
        node = nodes[i];
        if (config.hidden(node)) {
          continue;
        }
        nodeElement = $("<div class=\"node\" data-id=\"" + node.id + "\" data-name=\"" + node.name + "\">\n  <div class=\"head\">\n    <i class=\"add circle icon\"></i>\n    <i class=\"minus circle icon\"></i>\n    <i class=\"radio icon\"></i>\n    <a class=\"name\">" + node.name + "</a>\n    <i class=\"checkmark icon\"></i>\n  </div>\n  <div class=\"content\"></div>\n</div>").appendTo(tree);
        if (config.disabled(node)) {
          nodeElement.addClass('disabled');
        }
        if (node[config.childrenKey] && node[config.childrenKey].length) {
          $('.content', nodeElement).append(renderTree(node[config.childrenKey]));
        } else {
          nodeElement.addClass("childless");
        }
      }
      return tree;
    };
    renderList = function(nodes, css) {
      var i, len, list, node, nodeElement;
      if (css == null) {
        css = {};
      }
      list = $('<div class="ui tree-picker list"></div>').css(css);
      for (i = 0, len = nodes.length; i < len; i++) {
        node = nodes[i];
        if (config.hidden(node)) {
          continue;
        }
        nodeElement = $("<div class=\"node\" data-id=\"" + node.id + "\" data-name=\"" + node.name + "\">\n  <div class=\"head\">\n    <a class=\"name\">" + node.name + "</a>\n    <i class=\"checkmark icon\"></i>\n  </div>\n  <div class=\"content\"></div>\n</div>").appendTo(list);
        if (config.disabled(node)) {
          nodeElement.addClass('disabled');
        }
      }
      return list;
    };
    initializeNodeList = function(tree) {
      return $('.node', tree).each(function() {
        var content, head, node;
        node = $(this);
        head = $('>.head', node);
        content = $('>.content', node);
        $('>.name', head).on('click', function(e) {
          return nodeClicked(node);
        });
        if (nodeIsPicked(node)) {
          node.addClass('picked');
        }
        if (!node.hasClass('childless')) {
          $('>.icon', head).on('click', function(e) {
            node.toggleClass('opened');
            return content.slideToggle();
          });
        }
        return updatePickedIds();
      });
    };
    nodeClicked = function(node) {
      if (!node.hasClass('disabled')) {
        if (config.singlePick) {
          $('.node.picked', content).removeClass('picked');
          picked = [];
        }
        node.toggleClass('picked');
        if (node.hasClass('picked')) {
          return pickNode(node);
        } else {
          return unpickNode(node);
        }
      }
    };
    pickNode = function(node) {
      var id;
      config.picked = null;
      id = node.attr('data-id');
      picked.push({
        id: id,
        name: node.attr('data-name')
      });
      updatePickedIds();
      return $(".node[data-id=" + id + "]", content).addClass('picked');
    };
    unpickNode = function(node) {
      var id;
      config.picked = null;
      id = node.attr('data-id');
      picked = picked.filter(function(n) {
        return ("" + n.id) !== ("" + id);
      });
      updatePickedIds();
      return $(".node[data-id=" + id + "]", content).removeClass('picked');
    };
    nodeIsPicked = function(node) {
      return picked.filter(function(n) {
        return ("" + n.id) === node.attr('data-id');
      }).length;
    };
    updatePickedIds = function() {
      widget.attr('data-picked-ids', picked.map(function(n) {
        return n.id;
      }));
      if (picked.length) {
        count.closest('.item').addClass('highlighted');
        return count.html("(" + picked.length + ")");
      } else {
        count.closest('.item').removeClass('highlighted');
        return count.html("");
      }
    };
    recursiveNodeSearch = function(nodes, comparator) {
      var i, len, node, results;
      results = [];
      for (i = 0, len = nodes.length; i < len; i++) {
        node = nodes[i];
        if (comparator(node)) {
          results.push({
            id: node.id,
            name: node.name
          });
        }
        if (node[config.childrenKey] && node[config.childrenKey].length) {
          results = results.concat(recursiveNodeSearch(node[config.childrenKey], comparator));
        }
      }
      return results;
    };
    return initialize();
  };

}).call(this);
