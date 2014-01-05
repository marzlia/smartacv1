
Smart.Loader = Ext.apply({}, {
    
/**
     * Loads a given set of .js files. Calls the callback function when all files have been loaded
     * Set preserveOrder to true to ensure non-parallel loading of files if load order is important
     * @param {Array} fileList Array of all files to load
     * @param {Function} callback Callback to call after all files have been loaded
     * @param {Object} scope The scope to call the callback in
     * @param {Boolean} preserveOrder True to make files load in serial, one after the other (defaults to false)
     */
    load: function(fileList, callback, scope, preserveOrder) {
        var scope       = scope || this,
            head        = document.getElementById("bd"),
            fragment    = document.createDocumentFragment(),
            numFiles    = fileList.length,
            loadedFiles = 0,
            me          = this;
        
        
/**
         * Loads a particular file from the fileList by index. This is used when preserving order
         */
        var loadFileIndex = function(index) {
            head.appendChild(
                me.buildScriptTag(fileList[index], onFileLoaded)
            );
        };
        
        
/**
         * Callback function which is called after each file has been loaded. This calls the callback
         * passed to load once the final file in the fileList has been loaded
         */
        var onFileLoaded = function() {
            loadedFiles ++;
            
            //if this was the last file, call the callback, otherwise load the next file
            if (numFiles == loadedFiles && typeof callback == 'function') {
                callback.call(scope);
            } else {
                if (preserveOrder === true) {
                    loadFileIndex(loadedFiles);
                }
            }
        };
        
        if (preserveOrder === true) {
            loadFileIndex.call(this, 0);
        } else {
            //load each file (most browsers will do this in parallel)
            Ext.each(fileList, function(file, index) {
                fragment.appendChild(
                    this.buildScriptTag(file, onFileLoaded)
                );  
            }, this);
            
            head.appendChild(fragment);
        }
    },
    
    /**
     * @private
     * Creates and returns a script tag, but does not place it into the document. If a callback function
     * is passed, this is called when the script has been loaded
     * @param {String} filename The name of the file to create a script tag for
     * @param {Function} callback Optional callback, which is called when the script has been loaded
     * @return {Element} The new script ta
     */
    buildScriptTag: function(filename, callback) {
        var script  = document.createElement('script');
        script.type = "text/javascript";
        script.src  = filename;
        
        //IE has a different way of handling <script> loads, so we need to check for it here
        if (script.readyState) {
            script.onreadystatechange = function() {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    callback();
                }
            };
        } else {
            script.onload = callback;
        }    
        
        return script;
    }
});


AdminPanel = function() {
    AdminPanel.superclass.constructor.call(this, {
        id: 'admin-tree',
        region: 'west',
        split: true,
        header: false,
        width: 180,
        minSize: 175,
        maxSize: 500,
        margins:'0 0 0 5',
        cmargins:'0 0 0 0',
        lines:false,
        autoScroll:true,
        animCollapse:false,
        animate: false,
        collapsible: true,
        collapseMode:'mini',
        collapseFirst:false,
        rootVisible:false,
        dataUrl: 'findModule.action',
        root: {
            nodeType: 'async'
        }
     /*   loader: new Ext.tree.TreeLoader({
			preloadChildren: true,
			clearOnLoad: false
		}),
        root: new Ext.tree.AsyncTreeNode({
            text:'SmartAC Administrator',
            id:'root',
            expanded:true,
            children:[{
			    text:'数据查询', expanded: true,
			    children:[
				{text:'数据明细', id:'accessdetail', leaf:true},
				{text:'信息视图',id:'accessview',leaf:true},
				{text:'维度分析',id:'accessanalysis',leaf:true},
				{text:'警告追忆',id:'alarm',leaf:true},
				{text:'闸机监视',id:'gate',leaf:true}]
			},
			{
			    text:'系统配置', expanded: true,
			    children:[
				{text:'闸机管理', id:'gatemanage', leaf:true},
				{text:'监视管理',id:'watcher',leaf:true},
				{text:'数据更新设置',id:'timer',leaf:true},
				{text:'闸机参数设置',id:'config',leaf:true},
				{text:'待机画面设置',id:'screen',leaf:true},
				{text:'黑名单',id:'block',leaf:true}]
			},
            {
			    text:'用户管理', expanded: false,
			    children:[
			    	{text:'用户管理', id:'user', leaf:true},
			    	{text:'用户组管理', id:'group', leaf:true}]
			}]
         })  */
    });

    this.getSelectionModel().on('beforeselect', function(sm, node){
        return node.isLeaf();
    });
};

Ext.extend(AdminPanel, Ext.tree.TreePanel, {
    initComponent: function(){
        this.hiddenPkgs = [];
        Ext.apply(this, {
            tbar:[ ' ',
			new Ext.form.TextField({
				width: 110,
				emptyText:'查找一个模块',
                enableKeyEvents: true,
				listeners:{
					render: function(f){
                    	this.filter = new Ext.tree.TreeFilter(this, {
                    		clearBlank: true,
                    		autoClear: true
                    	});
					},
                    keydown: {
                        fn: this.filterTree,
                        buffer: 350,
                        scope: this
                    },
                    scope: this
				}
			}), ' ', ' ',
			{
                iconCls: 'icon-expand-all',
				tooltip: '全部展开',
                handler: function(){ this.root.expand(true); },
                scope: this
            }, '-', {
                iconCls: 'icon-collapse-all',
                tooltip: '全部收缩',
                handler: function(){ this.root.collapse(true); },
                scope: this
            }]
        })
        AdminPanel.superclass.initComponent.call(this);
    },
	filterTree: function(t, e){
		var text = t.getValue();
		//alert(text);
		Ext.each(this.hiddenPkgs, function(n){
			n.ui.show();
		});
		if(!text){
			this.filter.clear();
			return;
		}
		this.expandAll();
		
		var re = new RegExp('^' + Ext.escapeRe(text), 'i');
		this.filter.filterBy(function(n){
			return !n.attributes.isClass || re.test(n.text);
		});
		
		// hide empty packages that weren't filtered
		this.hiddenPkgs = [];
                var me = this;
		this.root.cascade(function(n){
			if(!n.attributes.isClass && n.ui.ctNode.offsetHeight < 3){
				n.ui.hide();
				me.hiddenPkgs.push(n);
			}
		});
	},
    selectClass : function(cls){
        if(cls){
            var parts = cls.split('.');
            var last = parts.length-1;
            var res = [];
            var pkg = [];
            for(var i = 0; i < last; i++){ // things get nasty - static classes can have .
                var p = parts[i];
                var fc = p.charAt(0);
                var staticCls = fc.toUpperCase() == fc;
                if(p == 'Ext' || !staticCls){
                    pkg.push(p);
                    res[i] = 'pkg-'+pkg.join('.');
                }else if(staticCls){
                    --last;
                    res.splice(i, 1);
                }
            }
            res[last] = cls;

            this.selectPath('/root/apidocs/'+res.join('/'));
        }
    }
});

MainPanel = function(){
	
    MainPanel.superclass.constructor.call(this, {
        id:'doc-body',
        region:'center',
        margins:'0 5 0 0',
        resizeTabs: true,
        minTabWidth: 135,
        tabWidth: 135,
        plugins: new Ext.ux.TabCloseMenu(),
        enableTabScroll: true,
        activeTab: 0,

        items: {
            id:'welcome-panel',
            title: '系统主页',
            //autoLoad: {url: 'welcome.html', callback: this.initSearch, scope: this},
            iconCls:'icon-docs',
            autoScroll: true,
			//tbar: [	'Search: ', ' '],
			layout : 'fit',
			bodyStyle : 'padding:25px',
			html : '<img src="./resources/images/bg.jpg"/>'
        },
        listeners : {
        	'tabchange' : function(tp, tb) {
        		//alert(tb.title);
        		Ext.getCmp('smart-statusbar').setStatus({
	                                text: tb.title,
	                                iconCls:'x-status-valid',
	                                clear: false
	                            });;
        	}
        }
    });
};

Ext.extend(MainPanel, Ext.TabPanel, {

    initEvents : function(){
        MainPanel.superclass.initEvents.call(this);
        this.body.on('click', this.onClick, this);
    },

    onClick: function(e, target){ //点击了页面
    	//alert("onClick of MainPanel");
    },

    loadClass : function(href, cls, member){
    },
	
    /*
     * var p = this.add(new Smart.Group());
			        this.setActiveTab(p);
    	 			break;
    	 			var p = this.add(new Smart.User());
			        this.setActiveTab(p);
    	 			break;
     */
    // 加载模块
    loadModule : function(mod){
    	//alert(this.id);
    	var id = 'smart-' + mod;
    	 var tab = this.getComponent(id);
    	 if(tab){
    	 	this.setActiveTab(tab);
    	 }else{
    	 	var modClass = "Smart."+ mod.substring(0,1).toUpperCase() + mod.substring(1);
    	 	//alert(modClass);
    	 	if(eval(modClass)){
    	 		eval("var p = this.add(new "+ modClass +"())");
    	 		this.setActiveTab(p);
    	 	} else {
    	 		this.getEl().mask("加载模块中,请稍等...");
    	 		Smart.Loader.load([mod + '.js'],
	    	 		function(){
	    	 			this.getEl().unmask();
	    	 			if(eval(modClass)){
	    	 				eval("var p = this.add(new "+ modClass +"())");
    	 					this.setActiveTab(p);
	    	 			} else {
	    	 				alert(mod + " is developing...");
	    	 			}
	    	 		},
	    	 		this
    	 		);
    	 	}
    	 	/*
    	 	switch(mod){
    	 		case 'group' :
    	 		case 'user' :
    	 		case 'gate' :
    	 		case 'accessdetail' :
    	 		case 'accessview' :
    	 		case 'accessanalysis' :
    	 		case 'alarm' :
    	 		case 'gatemanage' :
    	 		case 'timer' :
    	 		case 'watcher' :
    	 		case 'block' :
    	 			var modClass = "Smart."+ mod.substring(0,1).toUpperCase() + mod.substring(1);
    	 			
    	 			
    	 			//eval("var p = this.add(new Smart."+ mod.substring(0,1).toUpperCase() + mod.substring(1) +"())");
    	 			//this.setActiveTab(p);
    	 			break;
    	 		default:
    	 			alert(mod + " is developing...");
    	 	}*/
    	 }
    },
    
	initSearch : function(){
		// Custom rendering Template for the View
	},
	
	doSearch : function(e){
	},
	
	closeAllTabs : function(){
		var items = [];
        this.items.each(function(item){
            if(item.closable){
                items.push(item);
            }
        }, this);
        
        Ext.each(items, function(item){
            this.remove(item);
        }, this);
	}
});