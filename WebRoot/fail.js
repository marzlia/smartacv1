
// Center : 故障信息列表
// East : 故障图片视图

Smart.Fail = function (){

	// 分页大小
	this.pageSize = 20;
	
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllAlarm.action?type=32768',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'alarmId',type : 'string'},
			{name : 'gateId',type : 'int'},
			{name : 'alarmDatetime',type : 'date',dateFormat :'Y-m-d\\TH:i:s'},
			{name : 'alarmType',type : 'int'},
			{name : 'alarmCode',type : 'string'},
			{name : 'alarmComment',type : 'string'},
			{name : 'gateIp',type : 'string'},
			{name : 'gateCampus',type : 'string'},
			{name : 'gateRoom',type : 'string'}]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getSelectionModel().selectFirstRow();
			}
		}
	});
	
	this.getSelectionModel = function(){
		return this.get(0).getSelectionModel();
	}
	
	this.getStore = function(){
		return this.get(0).getStore();
	}
	
	this.getTopToolbar = function(){
		return this.get(0).getTopToolbar();
	}
	
	// 定义列模型
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: false,
	        width: 90
    	},
		columns: [{header : '编号',	width : 30,	dataIndex : 'alarmId'},
			{header : '闸机',	width : 90,	dataIndex : 'gateId'},
			{header : '日期',	width : 90,	dataIndex : 'alarmDatetime',
				renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
			{header : '故障码',	width : 90,	dataIndex : 'alarmCode'},
			{header : '注释',	width : 90,	dataIndex : 'alarmComment',id:'alarmComment'},
			{header : '地址',	width : 90,	dataIndex : 'gateIp'},
			{header : '校区',	width : 90,	dataIndex : 'gateCampus'},
			{header : '房间',	width : 90,	dataIndex : 'gateRoom'}			
		]
	});
	this.autoExpandColumn = 'alarmComment';
	
	this.image = {
		xtype : 'dataview',
		autoHeight:true,
        multiSelect: true,
        overClass:'x-view-over',
        itemSelector:'div.thumb-wrap',
        emptyText: '没有找到相关图片',
        prepareData: function(data){
                //data.shortName = Ext.util.Format.ellipsis(data.name, 15);
                //data.sizeString = Ext.util.Format.fileSize(data.size);
                //data.dateString = data.lastmod.format("m/d/Y g:i a");
                return data;
         }
	};
	
	this.image.store = new Ext.data.Store({
		url : 'findAllImage.action',
		//remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'alarmId',type : 'int'},
			{name : 'imageId',type : 'string'},
			{name : 'imageUrl',type : 'string'},
			{name : 'imageSize',type : 'float'},
			{name : 'imageMdate',type : 'date',dateFormat :'Y-m-d\\TH:i:s'}]
		),
		listeners: {
			scope: this,
			'beforeload': function( store, options ) {
				this.getEl().mask("加载图片中...");
			},
			'load' : function(store,records, options){
				//alert("Load Images Over");
				this.getEl().unmask();
			}
		}
	});
	
	this.image.tpl =  new Ext.XTemplate(
		'<tpl for=".">',
		    '<div class="thumb-wrap" id="fail_{imageId}" qtip=\'<img src="system/{imageUrl}" />\'>',
		    '<div class="thumb"><img src="system/{imageUrl}" title="{imageId}"></div>',
		    '<span class="x-editable">{imageId}</span></div>',
		'</tpl>',
		'<div class="x-clear"></div>'
	);

	
	Smart.Fail.superclass.constructor.call(this, {
		id: "smart-fail",
		title : "故障分析",
		border : false,
		margins: '0 0 5 0',
		layout : 'border',
		defaults : {
				collapsible : false,
				split : true
		},
		closable: true,
		items : [{
			xtype : 'grid',
			id : 'fail-data',
			header: false,
			border : false,
			region : 'center',
			viewConfig : {forceFit : true},
			loadMask : {msg : '数据加载中...'},
			style:'border-right: 1px solid #8db2e3;',
			cm : this.cm,
			autoExpandColumn : this.autoExpandColumn,
			store : this.store,
			sm : new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : function( sm,rowIndex,r) { //加载图片
						if(this.get(1).collapsed){
							this.get(1).expand(true);
						}
						this.image.store.baseParams.alarmId=r.data.alarmId;
						this.image.store.baseParams.alarmDatetime=r.data.alarmDatetime.format('Y/m/d/His');
						
						this.image.store.load();
					},
					scope : this
				}
			}),
			tbar : [{
						xtype : 'textfield',
						width : 200,
						emptyText : '多条件可用逗号或者空格隔开!',
						listeners : {
							'specialkey' : function(field, e) {
								if (e.getKey() == Ext.EventObject.ENTER) {
									this.getStore().baseParams.conditions = field.getValue();
									this.getStore().load({params : {start : 0,limit : this.pageSize}});
								}
							},
							scope: this
						}
					},{
						text : '查询',
						iconCls : 'icon-search',
						handler : function() {
								this.getStore().baseParams.conditions = this.getTopToolbar().get(0).getValue();
								this.getStore().load({params : {start : 0,limit : this.pageSize}});
							}
						,
						scope: this
					}],
			bbar : new Ext.PagingToolbar({
				pageSize : this.pageSize,
				store : this.store,
				displayInfo : true,
				displayMsg : '第 {0} - {1} 条 共 {2} 条',
				emptyMsg : "没有记录"
			}),
			listeners: {
				scope: this,
				'render' : function(grid){
					this.store.load({params : {start : 0,limit : this.pageSize}});
				}
			}
		},
		{
			id : 'fail-image',
			title : '现场图片',
			//header: false,
			border : false,
			region : 'east',
			collapsible: true,
			collapseMode:'mini',
			collapsed : true,
			autoScroll: true,
			style:'border-left: 1px solid #8db2e3;',
			hidden : false,
			width       : 200,
			layout : 'fit',
			items : [this.image]
		}],
		listeners : {
			'show' : function(){
			},
			'afterrender' : function(p){
			},
			'bodyresize' : function(p, width,height){
			} 
		}
	});
};

Ext.extend(Smart.Fail, Ext.Panel, {
	initComponent: function(){
		Smart.Fail.superclass.initComponent.call(this);
	}
});