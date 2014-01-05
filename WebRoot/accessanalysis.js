// 维度分析数据明细
Smart.Accessanalysisdetail = function (config){
	
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: false,
	        width: 90
    	},
		columns: [new Ext.grid.RowNumberer(),
			{header : '日期', dataIndex : 'name',id : 'accessanalysisdetail_name'}
		]
	});
	this.autoExpandColumn = 'accessanalysisdetail_name';
	
	Ext.apply(this,{
        id : "accessanalysis-data",
		region : 'center',
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : true,
		margins: '0 0 0 0',
		viewConfig : {forceFit : true},
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
    	listeners : {
			'destroy' : function(){
			}
		}
	});
	
	Smart.Accessanalysisdetail.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.Accessanalysisdetail, Ext.grid.GridPanel, {
	initComponent: function(){
		Smart.Accessanalysisdetail.superclass.initComponent.call(this);
	}
});


// 维度分析图表
Smart.AccessLineChart = function(config){
    
	Ext.apply(this,{
            xField: 'name',
            yAxis: new Ext.chart.NumericAxis({
                displayName: 'count',
                labelRenderer : Ext.util.Format.numberRenderer('0,0')
            }),/**/
            
            tipRenderer : function(chart, record, index, series){
            	//console.dir(record);
            	//console.dir(series);
            	//alert(series.yField);
            	var nodeValue = "";
            	eval("nodeValue=record.data."+series.yField);
            	//alert(nodeValue);
            	return '(' + series.displayName + ',' + record.data.name+ '): ' + Ext.util.Format.number(nodeValue, '0,0');
            	
            },
            chartStyle: {
                padding: 10,
                animationEnabled: true,
                font: {
                    name: 'Tahoma',
                    color: 0x444444,
                    size: 11
                },
                dataTip: {
                    padding: 5,
                    border: {
                        color: 0x99bbe8,
                        size:1
                    },
                    background: {
                        color: 0xDAE7F6,
                        alpha: .9
                    },
                    font: {
                        name: 'Tahoma',
                        color: 0x15428B,
                        size: 10,
                        bold: true
                    }
                },
                xAxis: {
                    color: 0x69aBc8,
                    majorTicks: {color: 0x69aBc8, length: 4},
                    minorTicks: {color: 0x69aBc8, length: 2},
                    majorGridLines: {size: 1, color: 0xeeeeee}
                },
                yAxis: {
                    color: 0x69aBc8,
                    majorTicks: {color: 0x69aBc8, length: 4},
                    minorTicks: {color: 0x69aBc8, length: 2},
                    majorGridLines: {size: 1, color: 0xdfe8f6}
                }
            },
            extraStyle:
		            {
		                legend:
		                {
		                    display: 'top',
		                    padding: 1,
		                    font:
		                    {
		                        family: 'Tahoma',
		                        size: 10
		                    }
		                }
		            },
            series: null
	});
	
	Smart.AccessLineChart.superclass.constructor.apply(this, arguments);
};

Ext.extend(Smart.AccessLineChart, Ext.chart.LineChart, {
	initComponent: function(){
		Smart.AccessLineChart.superclass.initComponent.call(this);
	}
});

Smart.Accessanalysis = function (){
	
	
	this.store = new Ext.data.Store({
		url : 'findAccessCount.action',
		reader : new Ext.data.JsonReader(),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				
			}
		}
	});
	
	this.foreYear = new Date();
	this.foreYear.setFullYear(this.foreYear.getFullYear() - 1);
	
	this.tbar = [
		{
			xtype : 'cycle',
			id : 'accessanalysis-timegrain',
		    showText: true,
		    prependText: '时间粒度: ',
		    items: [{
		    	id: 'accessanalysis-timegrain-month',
		        text:'月',
		        checked:true
		    },{
		    	id: 'accessanalysis-timegrain-day',
		        text:'日',
		        checked:false
		    }],
		    scope : this,
		    changeHandler:function(btn, item){
		    /*	 if(item.id == 'accessanalysis-timegrain-month'){
		    	 	// set the default time span is the newest year
		    	 	var s = Ext.getCmp('analysis-endtime').value;
					var dt = new Date(Date.parse(s.replace(/-/g, "/"))); 
		    	 	//var dt = new Date();
		    	 	Ext.getCmp('analysis-endtime').setValue(dt);
		    	 	dt.setFullYear(dt.getFullYear() - 1);
		    	 	Ext.getCmp('analysis-starttime').setValue(dt);
		    	 	
		    	 } else {
		    	 	
		    	 	var s = Ext.getCmp('analysis-endtime').value;
					var dt = new Date(Date.parse(s.replace(/-/g, "/"))); 
		    	 	//var dt = new Date();
		    	 	//Ext.getCmp('analysis-endtime').setValue(dt);
		    	 	dt.setMonth(new Date().getMonth()-1);
		    	 	Ext.getCmp('analysis-starttime').setValue(dt); 
		    	 }*/
		    }
		},'时间跨度： ',
        {
            xtype: 'datefield',
            id : 'analysis-starttime',
            name : 'analysis-starttime',
            altFormats: 'd/m/Y H:i:s',
            format : 'Y-m-d H:i:s',
            value : this.foreYear,
            allowBlank: false,
            blankText: '起始时间',
     		emptyText: '请输入起始时间',
     		width : 130
        },
        '至',
        {
            xtype: 'datefield',
             id : 'analysis-endtime',
            name : 'analysis-endtime',
            altFormats: 'd/m/Y H:i:s',
            format : 'Y-m-d H:i:s',
            value : new Date(),
            allowBlank: false,
            blankText: '结束时间',
     		emptyText: '请输入结束时间',
     		width : 130
        },'-', {
			text : '查询',
			iconCls : 'icon-search',
			handler : function() {
				
					//console.dir(this.table.colModel);
					//alert(Ext.getCmp('accessanalysis-timegrain').getActiveItem().id);
					
					var checked = '', selNodes = this.tree.getChecked();
					var tablColumns = [new Ext.grid.RowNumberer(),
					{header : '日期', dataIndex : 'name',id : 'accessanalysisdetail_name'}];
					var chartSeries = [];
					var i = 0;
	                Ext.each(selNodes, function(node){
	                    if(checked.length > 0){
	                        checked += ',';
	                    }
	                    checked += node.text;
	                    
	                    var oneSeries = {
	                    	type : 'line',
	                    	displayName : node.text,
	                    	yField : this.categoryGroup[this.categoryLevel].key+ '_' +node.text
	                    };
	                    chartSeries[i] = oneSeries;
	                    
	                    var oneColumns = {
	                    	header : node.text, 
	                    	dataIndex : oneSeries.yField
	                    };
	                    tablColumns[i+2] = oneColumns;
	                    
	                    i++;
	                },this);
                
					// set the columns of data table
	                this.table.colModel.setConfig(tablColumns, false);
	                
	                // Set the series of line chart
					this.lineChart.setSeries(chartSeries);
					
					this.store.baseParams.timeGrain = Ext.getCmp('accessanalysis-timegrain').getActiveItem().id.split('-')[2];
					this.store.baseParams.startDate = Ext.getCmp('analysis-starttime').value;
					this.store.baseParams.endDate = Ext.getCmp('analysis-endtime').value;
					// Category
					
					this.store.baseParams.parent = "";
	            	for(var i=0; i<this.categoryLevel; i++){
	            		this.store.baseParams.parent += 
	            			this.categoryGroup[i].key + "='" + this.categoryGroup[i].value + "',";
	            	}
					this.store.baseParams.key = this.categoryGroup[this.categoryLevel].key;
					this.store.baseParams.value = checked;
					this.store.load();
				
					
				}
			,
			scope: this
		}
		/*,
		'->','当前筛选路径：',{
			id : 'Accessanalysis-select-path',
           	text : '系统',
           	tooltip : '系统'
		}*/
	];
	
	// 表格
	this.table = new Smart.Accessanalysisdetail({store : this.store});
   
	// 图表
	this.lineChart = new Smart.AccessLineChart({store : this.store});

    this.categoryGroup = [{key: 'gateCampus', name: '校区',value: ''},
    {key: 'cardDepartment', name: '系部',value: ''},{key: 'cardGrade', name: '年级',value: ''},
    {key: 'cardClass', name: '班级',value: ''}];
    this.categoryLevel = 0;
    
    this.treeMenu  = new Ext.menu.Menu({
    		id : 'accessanalysis-selector-contextmenu',
	        items: [{
	            id: 'last-level',
	            text: '返回上一级'
	        },
	        {
	            id: 'next-level',
	            text: '进入下一级'
	        }],
	        listeners: {
	        	scope : this,
	            itemclick: function(item) {
	                switch (item.id) {
	                    case 'next-level':
	                       this.nextLevel(this.treeMenu.contextNode);  
	                       break;
	                    case 'last-level':
	                    	this.lastLevel();
	                       break;
	                }
	            },
	            'beforeshow' : function(m){
	            	//console.dir(m.get('last-level'));
	            	if(this.categoryLevel > 0){
	            		m.get('last-level').enable();
	            		m.get('last-level').setText("返回上一级(" + this.categoryGroup[this.categoryLevel-1].name+")");
	            	} else {
	            		m.get('last-level').disable();
	            		m.get('last-level').setText("返回上一级");
	            	}
	            	
	            	if(this.categoryLevel < this.categoryGroup.length - 1){
	            		m.get('next-level').enable();
	            		m.get('next-level').setText("进入下一级(" + this.categoryGroup[this.categoryLevel+1].name+")");
	            	} else {
	            		m.get('next-level').disable();
	            		m.get('next-level').setText("进入下一级");
	            	}
	            	
	            	return true;
	            } 
	        }
	    });
    
	this.tree = new Ext.tree.TreePanel({
        id : "accessanalysis-selector",
		region : 'east',
		header : false,
		//title : this.categoryGroup[this.categoryLevel].name + "列表",//'条件筛选器' +
		width : 150,
        useArrows: true,
        autoScroll: true,
        animate: true,
        enableDD: false,
        containerScroll: true,
        tbar : [{
			id : 'Accessanalysis-select-path-' + this.categoryLevel,
           	text : this.categoryGroup[this.categoryLevel].name,
           	//tooltip : "进入" + this.categoryGroup[this.categoryLevel].name,
           	handler : function(btn){
           		//alert(btn.id);
           		var level = btn.id.substring(btn.id.lastIndexOf('-')+1);
           		this.setLevel(level);
           	},
           	scope : this
		}],
        rootVisible: false,
        frame: false,
        root: {
            nodeType: 'async'
        },
        
        // auto create TreeLoader
        dataUrl: 'findAllCategory.action',
        contextMenu: this.treeMenu,
        listeners: {
        	scope : this,
        	'dblclick' : function(node,e) {
        		//console.dir(node);
        		//console.dir(e);
        		this.nextLevel(node);
        	},
            'checkchange': function(node, checked){
                if(checked){
                    //node.getUI().addClass('complete');
                }else{
                    //node.getUI().removeClass('complete');
                }
            },
            'contextmenu': function(node, e) {
            	//console.dir(node);
            	node.select();
	            var c = node.getOwnerTree().contextMenu;
	            c.contextNode = node;
	            c.showAt(e.getXY());
            }
        }
       /* ,
        buttons: [{
        	scope : this,
            text: 'Get Completed Tasks',
            handler: function(){
                var msg = '', selNodes = this.tree.getChecked();
                Ext.each(selNodes, function(node){
                    if(msg.length > 0){
                        msg += ', ';
                    }
                    msg += node.text;
                });
                Ext.Msg.show({
                    title: 'Completed Tasks', 
                    msg: msg.length > 0 ? msg : 'None',
                    icon: Ext.Msg.INFO,
                    minWidth: 200,
                    buttons: Ext.Msg.OK
                });
            }
        }]*/
    });

    this.tree.getRootNode().expand(true);
	
	Smart.Accessanalysis.superclass.constructor.call(this, {
		id: "smart-accessanalysis",
		title : "维度分析",
		iconCls:'icon-linechart',
		border : false,
		margins: '0 0 5 0',
		layout : 'border',
		defaults : {
				collapsible : false,
				split : true
		},
		closable: true,
		items :[
		this.table,this.tree,
		{
			id : "accessanalysis-chart",
			region : 'south',
			layout : 'fit',
			items : [this.lineChart],
			height : 150
		}]
	});
}

Ext.extend(Smart.Accessanalysis, Ext.Panel, {
	initComponent: function(){
		Smart.Accessanalysis.superclass.initComponent.call(this);
	},
	nextLevel : function(treenode){
		
		// Confirm this.categoryLevel did not exceed limit
		if(this.categoryLevel >= this.categoryGroup.length - 1){
			return;
		}
		// Update parameters
		var parentName = this.categoryGroup[this.categoryLevel].name;
		var parentValue = treenode.text
		this.categoryGroup[this.categoryLevel].value = parentValue;
    	this.categoryLevel++;
		
		// Update the path showed in toolbar: add parent to path
    	var tb = this.tree.getTopToolbar();
    	tb.getComponent(tb.items.length-1).setText(parentName + ":" + parentValue + "/");
    	
    	tb.addButton({
    		id : 'Accessanalysis-select-path-' + this.categoryLevel,
           	text : this.categoryGroup[this.categoryLevel].name,
           	//tooltip : "进入" + this.categoryGroup[this.categoryLevel].name,
           	handler : function(btn){
           		//alert(btn.id);
           		var level = btn.id.substring(btn.id.lastIndexOf('-')+1);
           		this.setLevel(level);
           	},
           	scope : this
    	});
    	
    	tb.doLayout();
    	this.reloadTree();
    	
    	
	},
	lastLevel : function(){
		
		this.categoryLevel--;
		
		
		var tb = this.tree.getTopToolbar();
		tb.remove(tb.getComponent(tb.items.length -1 ));
		tb.getComponent(tb.items.length-1).setText(this.categoryGroup[this.categoryLevel].name);
    	tb.doLayout();
    	
    	this.reloadTree();
    	
	},
	setLevel : function(level){
		//alert(level);
		if(this.categoryLevel == level){
			return;
		} else {
			this.categoryLevel = level;
		}
		
		var tb = this.tree.getTopToolbar();
		while(tb.items.length-1 > this.categoryLevel){
			tb.remove(tb.getComponent(tb.items.length -1 ));
		}
		
		tb.getComponent(tb.items.length-1).setText(this.categoryGroup[this.categoryLevel].name);
    	tb.doLayout();
		
    	this.reloadTree();
		//console.dir(this.tree.getTopToolbar());
	},
	reloadTree: function(){
		
		this.tree.getLoader().baseParams.key = this.categoryGroup[this.categoryLevel].key;	                    	
    	this.tree.getLoader().baseParams.parent = "";
    	for(var i=0; i<this.categoryLevel; i++){
    		this.tree.getLoader().baseParams.parent += 
    			this.categoryGroup[i].key + "='" + this.categoryGroup[i].value + "',";
    	}
    			
    	this.tree.getRootNode().reload();
	}
});