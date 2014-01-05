// 黑名单管理
Smart.Block = function(config){
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllBlock.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'blockId',type : 'int'},
				{name : 'cardId',type : 'int'},
				{name : 'userId',type : 'int'},
				{name : 'blockDatetime',type : 'date',dateFormat :'Y-m-d\\TH:i:s'},
				{name : 'blockReason',type : 'string'},
				{name : 'userName',type : 'string'},
				{name : 'groupName',type : 'string'},
				{name : 'groupId',type : 'int'},
				{name : 'cardNo',type : 'string'},
				{name : 'cardName',type : 'string'},
				{name : 'cardSex',type : 'string'},
				{name : 'cardType',type : 'string'},
				{name : 'cardClass',type : 'string'},
				{name : 'cardMajor',type : 'string'},
				{name : 'cardDepartment',type : 'string'},
				{name : 'cardInstitute',type : 'string'},
				{name : 'cardGrade',type : 'int'},
				{name : 'cardComment',type : 'string'},
				{name : 'cardUpdate',type : 'int'},
				{name : 'cardStatus',type : 'int'}
			]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getSelectionModel().selectFirstRow();
			}
		}
	});
	
	// 信息展开器
	this.expander = new Ext.grid.RowExpander({
		tpl : new Ext.Template(
		'<p>' +
		'<table width="480" style="padding-left:20px" border="0" cellspacing="0" cellpadding="0">' +
		'<tr><td colspan="4" valign="top"><h4>门禁卡个人信息</h4></td></tr>' +
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
	
	// 分页大小
	this.pageSize = 20;
	// 定义列模型
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: true,
	        width: 90
    	},
		columns: [this.expander,
			{header : '编号',	width : 30,	dataIndex : 'blockId'},
			{header : '卡号',	width : 90,	dataIndex : 'cardId'},
			{header : '操作人',	width : 90,	dataIndex : 'userName'},
			{header : '操作时间',	width : 90,	dataIndex : 'blockDatetime',renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
			{header : '原因',	width : 90,	dataIndex : 'blockReason'}
		]
	});
	this.autoExpandColumn = 'blockReason';
	
	Ext.apply(this,{
		id : 'smart-block',
		title : "黑名单管理",
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		viewConfig : {forceFit : true},
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
    	iconCls: 'icon-grid',
    	plugins: this.expander,
    	bbar : {
			xtype : 'paging',
			pageSize : this.pageSize,
			store : this.store,
			displayInfo : true,
			displayMsg : '第 {0} - {1} 条 共 {2} 条',
			emptyMsg : "没有记录"
		},
		addWin : null,
		editWin : null,
		listeners: {
			scope: this,
			'render' : function(grid){
				this.store.load({params : {start : 0,limit : this.pageSize}});
			}
		}
	});
	
	Smart.Block.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.Block, Ext.grid.GridPanel, {
	initComponent: function(){
		
		Ext.apply(this, {
	           	tbar:[{xtype: 'button',
					text : '添加',
					iconCls : 'icon-add',
					handler : this.addItem,
					scope : this
	           	},{	xtype: 'button',
					text : '删除',
					iconCls : 'icon-del',
					handler : this.delItem,
					scope : this
	           	},{
					xtype: 'button',
					text : '编辑',
					iconCls : 'icon-edit',
					scope : this,
					handler : this.editItem
				},{
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
							this.getStore().baseParams.conditions = this.getTopToolbar().get(3).getValue();
							this.getStore().load({params : {start : 0,limit : this.pageSize}});
						}
					,
					scope: this
				}
				]});
				
		Smart.Block.superclass.initComponent.call(this);
	},
	initAdd : function(){
	
	    var ds = new Ext.data.Store({
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
				{name : 'cardGrade',type : 'int'},
				{name : 'cardComment',type : 'string'},
				{name : 'cardUpdate',type : 'int'},
				{name : 'cardStatus',type : 'int'}]
			),
			listeners: {
				scope: this,
				'load' : function(store,records, options){
					this.getSelectionModel().selectFirstRow();
				}
			}
		});
	    
	    var colModel = new Ext.grid.ColumnModel({
	    defaults: {
	        sortable: true,
	        menuDisabled: false,
	        width: 90
    	},
    	columns: [
	     	{header : '卡号',	width : 30,	dataIndex : 'cardId'},
			{header : '标记号',	width : 90,	dataIndex : 'cardNo'},
			{header : '姓名',	width : 90,	dataIndex : 'cardName', id : 'cardName'},
			{header : '性别',	width : 90,	dataIndex : 'cardSex'},
			{header : '身份',	width : 90,	dataIndex : 'cardType'},
			{header : '班级',	width : 90,	dataIndex : 'cardClass',hidden : true},
			{header : '专业',	width : 90,	dataIndex : 'cardMajor',hidden : true},
			{header : '系部',	width : 90,	dataIndex : 'cardDepartment',hidden : true},
			{header : '学院',	width : 90,	dataIndex : 'cardInstitute',hidden : true},
			{header : '年级',	width : 90,	dataIndex : 'cardGrade',hidden : true},
			{header : '注释',	width : 90,	dataIndex : 'cardComment',hidden : true},
			{header : '更新标记',	width : 90,	dataIndex : 'cardUpdate',hidden : true},
			{header : '卡状态',	width : 90,	dataIndex : 'cardStatus',hidden : true}
	    ]});
    
		this.addWin = new Ext.Window({
								id : "smart-block-add-win",
								title : '添加黑名单',
								width: 750,
								resizable : false,
								autoHeight : true,
								modal : true,
								closeAction : 'hide',
								maximizable : true,
								listeners : {
									'hide' : function() {}
								},
								layout: 'column',
								items : [{
						            columnWidth: 0.60,
						            id : 'smart-block-add-cardlist',
					                xtype: 'grid',
					                height: 350,
					                title:'门禁卡列表',
					                border: false,
					                loadMask : {msg : '数据加载中...'},
					                ds: ds,
					                cm: colModel,
					                autoExpandColumn: 'cardName',
					                sm: new Ext.grid.RowSelectionModel({
					                    singleSelect: true,
					                    listeners: {
					                        rowselect: function(sm, row, rec) {
					                            //console.dir(Ext.getCmp("smart-block-add-form").getForm());
					                            Ext.getCmp("smart-block-add-form").findById("block.cardId").setValue(rec.data.cardId);
					                            Ext.getCmp("smart-block-add-form").findById("block.cardNo").setValue(rec.data.cardNo);
					                            Ext.getCmp("smart-block-add-form").findById("block.cardName").setValue(rec.data.cardName);
					                        }
					                    }
					                }),
					              	tbar: [{
										xtype : 'textfield',
										width : 200,
										emptyText : '多条件可用逗号或者空格隔开!',
										listeners : {
											'specialkey' : function(field, e) {
												if (e.getKey() == Ext.EventObject.ENTER) {
													var grid = Ext.getCmp('smart-block-add-cardlist');
													grid.getCmp('smart-block-add-cardlist').getStore().baseParams.conditions = field.getValue();
													grid.getStore().load({params : {start : 0,limit : 20}});
												}
											}
										}
									},{
										text : '查询',
										iconCls : 'icon-search',
										handler : function() {
												var grid = Ext.getCmp('smart-block-add-cardlist');
												grid.getStore().baseParams.conditions = grid.getTopToolbar().get(0).getValue();
												grid.getStore().load({params : {start : 0,limit : 20}});
											}
									}],
					                bbar:  {
											xtype : 'paging',
											pageSize : 20,
											store : ds,
											displayInfo : true,
											displayMsg : '第 {0} - {1} 条 共 {2} 条',
											emptyMsg : "没有记录"
									},
					                listeners: {
					                    viewready: function(g) {
					                       // g.getSelectionModel().selectRow(0);
					                    } // Allow rows to be rendered.
					                }
						        },{
									columnWidth: 0.40,
									id : "smart-block-add-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 60,
									url : 'saveBlock.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [{	
									 		fieldLabel : '卡ID',
									 		name : 'block.cardId', 
									 		id : 'block.cardId', 
									 		allowBlank : false,
									 		readOnly : true,
									 		blankText : '卡ID不能为空',
									 		emptyText: '请从门禁卡列表中选择'
									 	},{	
									 		fieldLabel : '标记号',
									 		name : 'block.cardNo', 
									 		id : 'block.cardNo', 
									 		allowBlank : true,
									 		readOnly : true,
									 		blankText : '标记号不能为空',
									 		emptyText: '请从门禁卡列表中选择'
									 	},{	
									 		fieldLabel : '姓名',
									 		name : 'block.cardName', 
									 		id : 'block.cardName', 
									 		allowBlank : true,
									 		readOnly : true,
									 		blankText : '姓名不能为空',
									 		emptyText: '请从门禁卡列表中选择'
									 	},{
									 		xtype : 'hidden',
									 		fieldLabel : '用户ID',
									 		name : 'block.userId',
									 		id : 'block.userId',
									 		value : Smart.loginUser.userId
									 	},
									 	{
									 		xtype: 'textarea',
									 		fieldLabel : '原因',
									 		name : 'block.blockReason',
									 		id : 'block.blockReason',
									 		allowBlank : true,
									 		maxLength : 64
									 	}
									],
									buttonAlign : 'right',
									minButtonWidth : 60,
									buttons : [{
										text : '添加',
										handler : function(btn) {
											var frm = btn.ownerCt.ownerCt.form;
											if (frm.isValid()) {
													btn.disable();
													frm.submit({
														waitTitle : '请稍候',
														waitMsg : '正在提交表单数据,请稍候...',
														success : function(form, action) {
															
															var grid = Ext.getCmp('smart-block-add-cardlist');
															var record = grid.getSelectionModel().getSelected();
															grid.getStore().remove(record);
															
															//Ext.getCmp("smart-block-add-form").reset();
															form.reset();
															
															Smart.showSucTipMsg("黑名单添加成功!");
															btn.enable();
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("黑名单添加失败 !");
														}
													});
											}
										},
										scope: this
									}, {text : '重置',handler : function() {this.ownerCt.ownerCt.form.reset();}
									}, {text : '取消',handler : function() {this.ownerCt.ownerCt.ownerCt.hide();}
									}]
									
								}]
							});
	},
	initEdit : function(){
		this.editWin = new Ext.Window({
								id : "smart-block-edit-win",
								title : '编辑黑名单',
								width : 420,
								resizable : false,
								autoHeight : true,
								modal : true,
								closeAction : 'hide',
								maximizable : true,
								listeners : {
									'hide' : function() {}
								},
								items : [{
									id : "smart-block-edit-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 60,
									url : 'updateBlock.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
										{
									 		xtype : 'hidden',
									 		fieldLabel : 'ID',
									 		name : 'blockId',
									 		id : 'blockId'
									 	},
									 	{
									 		xtype: 'textarea',
									 		fieldLabel : '原因',
									 		name : 'blockReason',
									 		id : 'blockReason',
									 		allowBlank : true,
									 		maxLength : 64
									 	}
									],
									buttonAlign : 'right',
									minButtonWidth : 60,
									buttons : [{
										text : '更新',
										handler : function(btn) {
											var frm = btn.ownerCt.ownerCt.form;
											if (frm.isValid()) {
													btn.disable();
													frm.submit({
														waitTitle : '请稍候',
														waitMsg : '正在提交表单数据,请稍候...',
														success : function(form, action) {
															// 提交到服务器之后，更新本地表格数据
															var record = this.getSelectionModel().getSelected();
															frm.updateRecord(record);
															record.commit();
															
															Smart.showSucTipMsg("黑名单更新成功!");
															btn.enable();
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("黑名单更新失败 !");
														},
														scope: this
													});
											}
										},
										scope: this
									}, {text : '重置',scope : this, handler : function(btn) { btn.ownerCt.ownerCt.form.loadRecord(this.getSelectionModel().getSelected()); }
									}, {text : '取消',handler : function(btn) { btn.ownerCt.ownerCt.ownerCt.hide();}
									}]
									
								}]
							});
	},
	addItem : function(btn){
		if(this.addWin == null) this.initAdd(); 
		if(this.addWin != null) this.addWin.show();
	},
	delItem : function(btn){
		var record = this.getSelectionModel().getSelected();
		if (record) {
			Ext.Msg.confirm('确认', '你确定删除该黑名单?', function(btn) {
				if (btn == 'yes') {
					this.getEl().mask("删除黑名单中,请稍等...");
					Ext.Ajax.request({
						url : 'deleteBlock.action',
						params : {blockId : record.data.blockId},
						callback : function (options,success,response){
							this.getEl().unmask();
							var obj = Ext.decode(response.responseText);
							if(success & obj.success){
								this.getStore().remove(record);
							}else{
								Smart.showErrTipMsg('删除时发生错误,<br>请检查是否符合删除条件!');
							}
						},
						scope: this
					});
				};
			}, this);
		}
	},
	editItem : function(btn){
		var record = this.getSelectionModel().getSelected();
		if (record) {
			if(this.editWin == null) this.initEdit(); 
			if(this.editWin != null) {
				this.editWin.get(0).getForm().loadRecord(record);
				this.editWin.show();
			}
		}
	},
	onDestroy : function(){
		if(this.addWin != null) this.addWin.close();
		if(this.editWin != null) this.editWin.close();
		Smart.Block.superclass.onDestroy.call(this);
	}
});