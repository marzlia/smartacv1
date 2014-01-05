// 闸机监视管理
Smart.Gate = function (){
	// 分页大小
	this.pageSize = 20;
	
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllGate.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'gateId',type : 'auto'},
				{name : 'gateIp',type : 'string'},
				{name : 'gateCampus',type : 'string'},
				{name : 'gateRoom',type : 'string'},
				{name : 'gateStatus',type : 'string'},
				{name : 'gateErrcode',type : 'int'},
				{name : 'gateRefresh',type : 'date', dateFormat : 'Y-m-d\\TH:i:s'}
			]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getSelectionModel().selectFirstRow();
			}
		}
	});
	
	// 定义列模型
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: true,
	        width: 90
    	},
		columns: [
			{header : '编号',	width : 30,	dataIndex : 'gateId',
				renderer : function(v){
					return String.leftPad(v,5,'0');
				}},
			{header : '地址',	width : 90,	dataIndex : 'gateIp'},
			{header : '校区',	width : 90,	dataIndex : 'gateCampus'},
			{header : '房间',	width : 90,	dataIndex : 'gateRoom'},
			{header : '状态',	width : 90,	dataIndex : 'gateStatus',
				renderer : function(v,m){
					if(v == '状态正常'){
						m.cellAttr = 'bgcolor="#00FF00"';
						return '<span style="display:table;width:100%;" qtip=\'状态正常\'>' + v + '</span>';
					}else {
						m.cellAttr = 'bgcolor="#FF0000"';
						return '<span style="display:table;width:100%;" qtip=\'状态不正常\'>' + v + '</span>';
					}
				}
			},
			{header : '响应时间',	width : 90,	dataIndex : 'gateErrcode',
				renderer : function(v,m){
					if(v < 0){
						m.cellAttr = 'bgcolor="#FF0000"';
						return "超时";
					} else if(v <= 500){
						m.cellAttr = 'bgcolor="#00FF00"';
					} else if(v <= 1000){
						m.cellAttr = 'bgcolor="#FFFF00"';
					}
					return v + " ms";
				}
			},
			{header : '更新时间', width : 90, dataIndex : 'gateRefresh', id: 'gateRefresh',
				renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')}
		]
	});
	this.autoExpandColumn = 'gateRefresh';
	
	this.bbar = {
			xtype : 'paging',
			pageSize : this.pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '第 {0} - {1} 条 共 {2} 条',
			emptyMsg : "没有记录"
	};
	
	this.tbar= [{
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
	}];
	
	Smart.Gate.superclass.constructor.call(this, {
		id: "smart-gate",
		title : "闸机监视",
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		viewConfig : {forceFit : true},
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
    	iconCls: 'icon-grid',
    	listeners : {
			'destroy' : function(){
				// Destroy related window
			},
			'render' : function(grid){
				this.store.load({params : {start : 0,limit : this.pageSize}});
			},
			scope : this
		}
	});
}

Ext.extend(Smart.Gate, Ext.grid.GridPanel, {
	initComponent: function(){
		Smart.Gate.superclass.initComponent.call(this);
	}
});