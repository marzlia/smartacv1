// 监视器管理
Smart.Card = function(config){
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllCard.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'cardId',type : 'int'},
			{name : 'cardNo',type : 'string'},
			{name : 'cardName',type : 'string'},
			{name : 'cardSex',type : 'string'},
			{name : 'cardType',type : 'string'},
			{name : 'cardClass',type : 'string'},
			{name : 'cardMajor',type : 'string'},
			{name : 'cardDepartment',type : 'string'},
			{name : 'cardInstitute',type : 'string'},
			{name : 'cardGrade',type : 'int'}]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getSelectionModel().selectFirstRow();
			}
		}
	});
	
	// 分页大小
	this.pageSize = 20;
	
	this.expander = new Ext.grid.RowExpander({
		tpl : new Ext.Template(
		'<p>' +
		'<table width="480" style="padding-left:20px" border="0" cellspacing="0" cellpadding="0">' +
		'<tr>' +
		'<td width="20%" align="right">姓名:</td>' +
		'<td width="30%">{cardName}</td>' +
		'<td width="20%" align="right">性别:</td>' +
		'<td width="30%">{cardSex}</td>' +
		'</tr>' +
		'<tr>' +
		'<td width="20%" align="right">身份:</td>' +
		'<td width="30%">{cardType}</td>' +
		'<td width="20%" align="right">学院:</td>' +
		'<td width="30%">{cardInstitute}</td>' +
		'</tr>' +
		'<tr>' +
		'<td width="20%" align="right">系部:</td>' +
		'<td width="30%">{cardDepartment}</td>' +
		'<td width="20%" align="right">专业:</td>' +
		'<td width="30%">{cardMajor}</td>' +
		'</tr>' +
		'<tr>' +
		'<td width="20%" align="right">年级:</td>' +
		'<td width="30%">{cardGrade}</td>' +
		'<td width="20%" align="right">班级:</td>' +
		'<td width="30%">{cardClass}</td>' +
		'</tr>' +
		'<tr><td colspan="4" valign="top">&nbsp;</td></tr>' +
		'</table></p>')
	});
	// 定义列模型
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: true,
	        width: 90
    	},
		columns: [this.expander,
			{header : '卡号',	width : 90,	dataIndex : 'cardId'},
			{header : '标记号',	width : 90,	dataIndex : 'cardNo'},
			// 个人用户信息
			{header : '姓名',	width : 90,	dataIndex : 'cardName',id : 'cardName'},
			{header : '性别',	width : 90,	dataIndex : 'cardSex', hidden : false},
			{header : '身份',	width : 90,	dataIndex : 'cardType',hidden : false},
			{header : '学院',	width : 90,	dataIndex : 'cardInstitute',hidden : true},
			{header : '系部',	width : 90,	dataIndex : 'cardDepartment',hidden : true},
			{header : '专业',	width : 90,	dataIndex : 'cardMajor',hidden : true},
			{header : '年级',	width : 90,	dataIndex : 'cardGrade',hidden : true},
			{header : '班级',	width : 90,	dataIndex : 'cardClass',hidden : true}
		]
	});
	this.autoExpandColumn = 'cardName';
	
	Ext.apply(this,{
		id : 'smart-Card',
		title : "门卡明细",
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		viewConfig : {forceFit : true},
		plugins: [this.expander],
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
    	iconCls: 'icon-grid',
    	bbar : {
			xtype : 'paging',
			pageSize : this.pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '第 {0} - {1} 条 共 {2} 条',
			emptyMsg : "没有记录"
		},
		listeners: {
			scope: this,
			'render' : function(grid){
				this.store.load({params : {start : 0,limit : this.pageSize}});
			}
		}
	});
	
	Smart.Card.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.Card, Ext.grid.GridPanel, {
	initComponent: function(){
		
		Ext.apply(this, {
	           	tbar:[{
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
				}
				]});
				
		Smart.Card.superclass.initComponent.call(this);
	},
	onDestroy : function(){
		if(this.addWin != null) this.addWin.close();
		if(this.editWin != null) this.editWin.close();
		Smart.Card.superclass.onDestroy.call(this);
	}
});