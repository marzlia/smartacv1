Smart.Accessview = function (){
	this.activeStore = null;
	this.foreDay = new Date();
	this.foreDay.setTime(this.foreDay.getTime() - 24*60*60*1000);
	
	this.timeBtn = new Ext.CycleButton({
		id : 'time-btn',
	    showText: true,
	    prependText: '时间粒度: ',
	    items: [{
	        text:'年',
	        checked:false
	    },{
	        text:'月',
	        checked:false
	    },{
	        text:'周',
	        checked:false
	    },{
	        text:'日',
	        checked:false
	    },{
	        text:'时',
	        checked:true
	    }],
	    scope : this,
	    changeHandler:function(btn, item){
	    	this.timeStore.baseParams.groupColname = item.text;
	        this.timeStore.reload();
	    }
	});
	
	this.tbar = [this.timeBtn,'-','时间跨度： ',
        {
        	xtype: 'datefield',
        	id : 'start-date',
            name : 'startDate',
            altFormats: 'd/m/Y H:i:s',
            format : 'Y-m-d H:i:s',
            value : this.foreDay,
            allowBlank: false,
            blankText: '起始时间',
     		emptyText: '请输入起始时间',
     		width : 140
        },
        '至',
        {
        	xtype: 'datefield',
        	id : 'end-date',
            altFormats: 'd/m/Y H:i:s',
            format : 'Y-m-d H:i:s',
            value : new Date(),
            allowBlank: false,
            blankText: '结束时间',
     		emptyText: '请输入结束时间',
     		width : 140
        },'-', {
			text : '查询',
			iconCls : 'icon-search',
			handler : function() {
						
					this.activeStore.baseParams.startDate = Ext.getCmp('start-date').value;
					this.activeStore.baseParams.endDate = Ext.getCmp('end-date').value;
					this.activeStore.load();
				}
			,
			scope: this
		},'->',{
			tooltip: "刷新",
            iconCls: "x-tbar-loading",
            scope : this,
            handler: function(){
            	if(this.activeStore != null){ this.activeStore.reload();}
            }
		}
	];
	
	this.tplData = {
        firstFive: [],
        lastFive: []
    };
    
     this.tpl = new Ext.XTemplate(
	    '<p>前五位: ',
	    '<tpl for="firstFive">',
	        '<tpl><p>{aggName}: {aggCount}</p></tpl>',
	    '</tpl></p><br>',
	    '<p>后五位: ',
	    '<tpl for="lastFive">',
	        '<tpl><p>{aggName}: {aggCount}</p></tpl>',
	    '</tpl></p>'
	);
                
	this.timeStore = new Ext.data.Store({
		url : 'findAggregation.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader(
			{
				totalProperty : 'totalProperty',
				root : 'root'
			}, 
			[{name : 'aggName',type : 'string'},
			{name : 'aggCount',type : 'int'}]
		),
		listeners: {
			scope: this,
			'beforeload' : function(store,options){
				//this.getEl().mask("加载时间数据中...");
			},
			'load' : function(store,records, options){
				for(var i=0; i < records.length && i<5; i++){
					this.tplData.firstFive[i] = records[i].data;
				}
				
				while(this.tplData.firstFive.length > i){
					this.tplData.firstFive.remove(this.tplData.firstFive[this.tplData.firstFive.length - 1]);
				}
				
				for(var i=0; (records.length - i - 1) > -1  && i<5; i++){
					this.tplData.lastFive[i] = records[(records.length-i-1)].data;
				}
				
				while(this.tplData.lastFive.length > i){
					this.tplData.lastFive.remove(this.tplData.lastFive[this.tplData.lastFive.length - 1]);
				}
				
				this.tpl.overwrite(this.get('time-view').get(0).get(1).body, this.tplData);
			}
		}
	});
	
	this.deptStore = new Ext.data.Store({
		url : 'findAggregation.action',
		remoteSort : true,
		baseParams : {groupColname :'cardDepartment'},
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'aggName',type : 'string'},
			{name : 'aggCount',type : 'int'}]
		),
		listeners: {
			scope: this,
			'beforeload' : function(store,options){
				//this.getEl().mask("加载部门数据中...");
			},
			'load' : function(store,records, options){
				//alert("load over");
				//this.getEl().unmask();
				for(var i=0; i < records.length && i<5; i++){
					this.tplData.firstFive[i] = records[i].data;
				}
				
				while(this.tplData.firstFive.length > i){
					this.tplData.firstFive.remove(this.tplData.firstFive[this.tplData.firstFive.length - 1]);
				}
				
				for(var i=0; (records.length - i - 1) > -1  && i<5; i++){
					this.tplData.lastFive[i] = records[(records.length-i-1)].data;
				}
				
				while(this.tplData.lastFive.length > i){
					this.tplData.lastFive.remove(this.tplData.lastFive[this.tplData.lastFive.length - 1]);
				}
				
				this.tpl.overwrite(this.get('dept-view').get(0).get(1).body, this.tplData);/**/
			}
		}
	});
	
	this.campStore = new Ext.data.Store({
		url : 'findAggregation.action',
		remoteSort : true,
		baseParams : {groupColname :'gateCampus'},
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'aggName',type : 'string'},
			{name : 'aggCount',type : 'int'}]
		),
		listeners: {
			scope: this,
			'beforeload' : function(store,options){
				//this.getEl().mask("加载部门数据中...");
			},
			'load' : function(store,records, options){
				//this.getEl().unmask();
			}
		}
	});
	
	this.typeStore = new Ext.data.Store({
		url : 'findAggregation.action',
		remoteSort : true,
		baseParams : {groupColname :'cardType'},
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'aggName',type : 'string'},
			{name : 'aggCount',type : 'int'}]
		),
		listeners: {
			scope: this,
			'beforeload' : function(store,options){
				//this.getEl().mask("加载部门数据中...");
			},
			'load' : function(store,records, options){
				//this.getEl().unmask();
			}
		}
	});
	
	this.timeView = {
			id : 'time-view',
			title: '时间饼图',
			layout: 'fit',
			items: {
				title: '按时间排序',
				bodyStyle:'padding:10px;',
				border: false,
				layout : 'border',
				defaults : {
						collapsible : false,
						split : true
				},
				items: [{
					region : 'center',
				 	store : this.timeStore,
		            xtype: 'piechart',
		            dataField: 'aggCount',
		            categoryField: 'aggName',
		            //extra styles get applied to the chart defaults
		            // Contain extra styles
		            extraStyle:
		            {
		                legend:
		                {
		                    display: 'right',
		                    padding: 5,
		                    font:
		                    {
		                        family: 'Tahoma',
		                        size: 13
		                    }
		                }
		            },
		            listeners:{
		            	scope : this,
		            	'render' : function(c){
		            		//alert("Time's Pie Chart's markup is rendered");
		            	},
		            	'afterrender' : function(c){
		            		//alert("Time's Pie Chart is rendered");
		            		c.store.reload();
		            	},
		            	'beforerefresh' : function(c){
		            		//alert("before refresh");
		            		//this.getEl().mask("刷新数据中...");
		            	},
		            	'refresh' : function(c){
		            		//alert("Time's Pie Chart is refreshed");
		            		//this.getEl().unmask();
		            	}
		            }
		        },{
		        	header: false,
					border : false,
					region : 'east',
					collapsible: true,
					collapseMode:'mini',
					collapsed : false,
					autoScroll: true,
					style:'border-left: 1px solid #8db2e3;padding:10px;',
					hidden : false,
					width       : 150,
					html : "<p>加载数据中...</p>"
		        }]
			}
		};
		
	this.deptView = {
			id : 'dept-view',
			title: '部门饼图',
			layout: 'fit',
			items: {
				title: '按部门排序',
				bodyStyle:'padding:10px;',
				border: false,
				layout : 'border',
				defaults : {
						collapsible : false,
						split : true
				},
				items: [{
					region : 'center',
				 	store : this.deptStore,
		            xtype: 'piechart',
		            dataField: 'aggCount',
		            categoryField: 'aggName',
		            //extra styles get applied to the chart defaults
		            extraStyle:
		            {
		                legend:
		                {
		                    display: 'right',
		                    padding: 5,
		                    font:
		                    {
		                        family: 'Tahoma',
		                        size: 13
		                    }
		                }
		            },
		            listeners:{
		            	'render' : function(c){
		            		//alert("Dept's Pie Chart's markup is rendered");
		            	},
		            	'afterrender' : function(c){
		            		//alert("Dept's Pie Chart is rendered");
		            		c.store.reload();
		            	},
		            	'beforerefresh' : function(c){
		            		//alert("before refresh");
		            	},
		            	'refresh' : function(c){
		            		//alert("Dept's Pie Chart is refreshed");
		            		//c.store.reload();
		            	}
		            }
		        },{
		        	header: false,
					border : false,
					region : 'east',
					collapsible: true,
					collapseMode:'mini',
					collapsed : false,
					autoScroll: true,
					style:'border-left: 1px solid #8db2e3;padding:10px;',
					hidden : false,
					width       : 150,
					html : "<p>加载数据中...</p>"
		        }]
			}
		};
		
	this.campView = {
			id : 'camp-view',
			title: '校区饼图',
			layout: 'fit',
			items: {
				title: '按校区排序',
				bodyStyle:'padding:10px;',
				border: false,
				 items: {
				 	store : this.campStore,
		            xtype: 'piechart',
		            dataField: 'aggCount',
		            categoryField: 'aggName',
		            //extra styles get applied to the chart defaults
		            extraStyle:
		            {
		                legend:
		                {
		                    display: 'right',
		                    padding: 5,
		                    font:
		                    {
		                        family: 'Tahoma',
		                        size: 13
		                    }
		                }
		            },
		            listeners:{
		            	'render' : function(c){
		            		//alert("Dept's Pie Chart's markup is rendered");
		            	},
		            	'afterrender' : function(c){
		            		//alert("Dept's Pie Chart is rendered");
		            		c.store.reload();
		            	},
		            	'beforerefresh' : function(c){
		            		//alert("before refresh");
		            	},
		            	'refresh' : function(c){
		            		//alert("Dept's Pie Chart is refreshed");
		            		//c.store.reload();
		            	}
		            }
		        }
			}
		};
	
	this.typeView = {
			id : 'type-view',
			title: '人员类型饼图',
			layout: 'fit',
			items: {
				title: '按人员类型排序',
				bodyStyle:'padding:10px;',
				border: false,
				 items: {
				 	store : this.typeStore,
		            xtype: 'piechart',
		            dataField: 'aggCount',
		            categoryField: 'aggName',
		            //extra styles get applied to the chart defaults
		            extraStyle:
		            {
		                legend:
		                {
		                    display: 'right',
		                    padding: 5,
		                    font:
		                    {
		                        family: 'Tahoma',
		                        size: 13
		                    }
		                }
		            },
		            listeners:{
		            	'render' : function(c){
		            		//alert("Dept's Pie Chart's markup is rendered");
		            	},
		            	'afterrender' : function(c){
		            		//alert("Dept's Pie Chart is rendered");
		            		c.store.reload();
		            	},
		            	'beforerefresh' : function(c){
		            		//alert("before refresh");
		            	},
		            	'refresh' : function(c){
		            		//alert("Dept's Pie Chart is refreshed");
		            		//c.store.reload();
		            	}
		            }
		        }
			}
		};
	
	Smart.Accessview.superclass.constructor.call(this, {
		id: "smart-accessview",
		title : "数据视图",
		iconCls : "icon-piechart",
		closable: true,
		tabPosition: 'bottom',
		activeTab: 0,
		items : [this.timeView,this.deptView,this.campView,this.typeView],
		listeners: {
			scope : this,
			'tabchange' : function(parent,tab){
				//alert("tab changed to " + tab.id);
				//alert("this.activeStroe = this." + tab.id.split('-')[0] + "Store;");
				eval("this.activeStore = this." + tab.id.split('-')[0] + "Store;");
				if(this.timeBtn.isVisible()){
					if(tab.id.split('-')[0] != 'time') { 
						this.timeBtn.setVisible(false);
						this.getTopToolbar().get(1).setVisible(false);
					}
				}else{ // 隐藏状态
					if(tab.id.split('-')[0] == 'time') { 
						this.timeBtn.setVisible(true);
						this.getTopToolbar().get(1).setVisible(false);
					}
				}
			}
		}
	});
	
	
}

Ext.extend(Smart.Accessview, Ext.TabPanel, {
	initComponent: function(){
		Smart.Accessview.superclass.initComponent.call(this);
	}
});