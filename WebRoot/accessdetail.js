// 门禁数据明细管理
Smart.Accessdetail = function(config){
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllAccess.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[{name : 'accessId',type : 'int'},
			{name : 'cardId',type : 'int'},
			{name : 'cardNo',type : 'string'},
			{name : 'cardName',type : 'string'},
			{name : 'cardSex',type : 'string'},
			{name : 'cardType',type : 'string'},
			{name : 'cardClass',type : 'string'},
			{name : 'cardMajor',type : 'string'},
			{name : 'cardDepartment',type : 'string'},
			{name : 'cardInstitute',type : 'string'},
			{name : 'gateId',type : 'int'},
			{name : 'gateIp',type : 'string'},
			{name : 'gateCampus',type : 'string'},
			{name : 'gateRoom',type : 'string'},
			{name : 'accessDatetime',type : 'date',dateFormat :'Y-m-d\\TH:i:s'},
			{name : 'accessDirection',type : 'int'},
			{name : 'cardGrade',type : 'int'}]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getSelectionModel().selectFirstRow();
			}
		}
	});
	
	this.filters = new Ext.ux.grid.GridFilters({
        // encode and local configuration options defined previously for easier reuse
        encode: true, // json encode the filter query
        local: false,   // defaults to false (remote filtering)
        filters: [{
            type: 'numeric',
            dataIndex: 'accessId'
        },{
            type: 'numeric',
            dataIndex: 'cardId'
        },{
            type: 'string',
            dataIndex: 'cardNo'
        },{
            type: 'string',
            dataIndex: 'cardName',
            disabled: false
        },{
            type: 'list',
            dataIndex: 'cardSex',
            options: ['男', '女', '不男不女'],
            phpMode: true
        },{
            type: 'date',
            dataIndex: 'accessDatetime'
        }]
    }); 
	
	// 信息展开器
	this.expander = new Ext.grid.RowExpander({
		tpl : new Ext.Template(
		'<p>' +
		'<table width="480" style="padding-left:20px" border="0" cellspacing="0" cellpadding="0">' +
		'<tr><td colspan="4" valign="top"><h4>个人卡信息</h4></td></tr>' +
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
		'<tr><td colspan="4" valign="top"><h4>闸机信息</h4></td></tr>' +
		'<tr>' +
		'<td width="20%" align="right">校区:</td>' +
		'<td width="30%">{gateCampus}</td>' +
		'<td width="20%" align="right">房间:</td>' +
		'<td width="30%">{gateRoom}</td>' +
		'</tr>' +
		'<tr>' +
		'<td colspan="1" width="20%" align="right">地址:</td>' +
		'<td colspan="1" width="80%">{gateIp}</td>' +
		'<td width="0%" align="right">&nbsp;</td>' +
		'<td width="0%">&nbsp;</td>' +
		'</tr>' +
		'<tr><td colspan="4" valign="top">&nbsp;</td></tr>' +
		'</table></p>')
	});
	
	// 分页大小
	this.pageSize = 20;
	// 定义列模型
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: false,
	        width: 90
    	},
		columns: [this.expander,{header : '编号',	width : 50,	dataIndex : 'accessId'},
			{header : '卡号',	width : 90,	dataIndex : 'cardId'},
			{header : '标记号',	width : 90,	dataIndex : 'cardNo'},
			// 个人用户信息
			{header : '姓名',	width : 90,	dataIndex : 'cardName',id : 'cardName'},
			{header : '性别',	width : 90,	dataIndex : 'cardSex', hidden : true},
			{header : '身份',	width : 90,	dataIndex : 'cardType',hidden : true},
			{header : '学院',	width : 90,	dataIndex : 'cardInstitute',hidden : true},
			{header : '系部',	width : 90,	dataIndex : 'cardDepartment',hidden : true},
			{header : '专业',	width : 90,	dataIndex : 'cardMajor',hidden : true},
			{header : '年级',	width : 90,	dataIndex : 'cardGrade',hidden : true},
			{header : '班级',	width : 90,	dataIndex : 'cardClass',hidden : true},
			{header : '闸机号',	width : 90,	dataIndex : 'gateId'},
			{header : '校区',	width : 90,	dataIndex : 'gateCampus',hidden : true},
			{header : '房间',	width : 90,	dataIndex : 'gateRoom',hidden : true},
			{header : '地址',	width : 90,	dataIndex : 'gateIp',hidden : true},
			{header : '通过时间',	width : 90,	dataIndex : 'accessDatetime',
				renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
			{header : '通过方向',	width : 90,	dataIndex : 'accessDirection',
				renderer: function(v){
					if(v == 0) return "由外向内";
					else return "由内向外";
				}}
			
		]
	});
	this.autoExpandColumn = 'cardName';
	
	Ext.apply(this,{
		id: "smart-accessdetail",
		title : "数据明细",
		iconCls: 'icon-grid',
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		plugins: [this.expander,this.filters],
		viewConfig : {forceFit : true},
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
    	bbar : {
			xtype : 'paging',
			pageSize : this.pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '第 {0} - {1} 条 共 {2} 条',
			plugins: [this.filters],
			emptyMsg : "没有记录"
		},
		listeners: {
			scope: this,
			'render' : function(grid){
				this.store.load({params : {start : 0,limit : this.pageSize}});
			}
		}
	});
	
	Smart.Accessdetail.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.Accessdetail, Ext.grid.GridPanel, {
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
				}/**/,
				'-',
				{
					//xtype : 'exportbutton',
					text : '导出',
					iconCls : 'page_excel',
					//component: this,
					handler : function() {
							
					}, 
					scope: this
				}
				]
	    });
				
		Smart.Accessdetail.superclass.initComponent.call(this);
	},
	
	onDestroy : function(){
		Smart.Accessdetail.superclass.onDestroy.call(this);
	}
});