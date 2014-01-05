// 监视器管理
Smart.Watcher = function(config){
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllWatcher.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'watcherId',type : 'int'},
				{name : 'watcherIp',type : 'string'},
				{name : 'watcherCampus',type : 'string'},
				{name : 'watcherRoom',type : 'string'}
			]
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
	// 定义列模型
	this.cm = new Ext.grid.ColumnModel({
		defaults: {
	        sortable: true,
	        menuDisabled: true,
	        width: 90
    	},
		columns: [
			{header : '编号',	width : 30,	dataIndex : 'watcherId'},
			{header : '地址',	width : 90,	dataIndex : 'watcherIp'},
			{header : '校区',	width : 90,	dataIndex : 'watcherCampus'},
			{header : '房间',	width : 90,	dataIndex : 'watcherRoom'}
		]
	});
	this.autoExpandColumn = 'watcherIp';
	
	Ext.apply(this,{
		id : 'smart-watcher',
		title : "监视器管理",
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		viewConfig : {forceFit : true},
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
		addWin : null,
		editWin : null,
		listeners: {
			scope: this,
			'render' : function(grid){
				this.store.load({params : {start : 0,limit : this.pageSize}});
			}
		}
	});
	
	Smart.Watcher.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.Watcher, Ext.grid.GridPanel, {
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
				
		Smart.Watcher.superclass.initComponent.call(this);
	},
	initAdd : function(){
		this.addWin = new Ext.Window({
								id : "smart-watcher-add-win",
								title : '添加监视器',
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
									id : "smart-watcher-add-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'saveWatcher.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
									 	{	
									 		fieldLabel : 'IP地址',
									 		name : 'watcher.watcherIp', 
									 		id : 'watcher.watcherIp', 
									 		allowBlank : false,
            								blankText: 'IP地址',
     										emptyText: '请输入IP地址',
     										regex : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
     										regexText : '请输入合法的IP地址,例：10.111.3.108'
									 	},{
									 		fieldLabel : '物理地址',
									 		name : 'watcher.watcherMac',
									 		id : 'watcher.watcherMac',
									 		allowBlank : true,
									 		maxLength : 32
									 	},{
									 		fieldLabel : '校区',
									 		name : 'watcher.watcherCampus',
									 		id : 'watcher.watcherCampus',
									 		allowBlank : false,
									 		blankText : '校区',
									 		emptyText: '请输入校区',
									 		maxLength : 16,
									 		regex : /^\s*|\s*$/,
									 		regexText : '输入内容首尾不能有空白字符'
									 	},{
									 		fieldLabel : '房间',
									 		name : 'watcher.watcherRoom',
									 		id : 'watcher.watcherRoom',
									 		allowBlank : true,
									 		//blankText : '房间',
									 		//emptyText: '请输入房间',
									 		maxLength : 32,
									 		regex : /^\s*|\s*$/,
									 		regexText : '输入内容首尾不能有空白字符'
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
															//Smart.showSucTipMsg("监视器添加成功!");
															btn.enable();
															if(Smart.setting.singleAddMode) this.addWin.hide();
															if(Smart.setting.autoloadAfterAdd) this.getStore().reload();
															if(Smart.setting.popupSucMsgAfterAdd) Smart.showSucTipMsg("监视器添加成功!");
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("监视器添加失败 !");
														},
														scope: this
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
								id : "smart-watcher-edit-win",
								title : '编辑监视器',
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
									id : "smart-watcher-edit-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'updateWatcher.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
										{
									 		xtype : 'hidden',
									 		fieldLabel : 'ID',
									 		name : 'watcherId',
									 		id : 'watcherId'
									 	},
									 	{	
									 		fieldLabel : 'IP地址',
									 		name : 'watcherIp', 
									 		id : 'watcherIp', 
									 		allowBlank : false,
            								blankText: 'IP地址',
     										emptyText: '请输入IP地址',
     										regex : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
     										regexText : '请输入合法的IP地址,例：10.111.3.108'
									 	},{
									 		fieldLabel : '物理地址',
									 		name : 'watcherMac',
									 		id : 'watcherMac',
									 		allowBlank : true,
									 		maxLength : 32
									 	},{
									 		fieldLabel : '校区',
									 		name : 'watcherCampus',
									 		id : 'watcherCampus',
									 		allowBlank : false,
									 		blankText : '校区',
									 		emptyText: '请输入校区',
									 		maxLength : 16,
									 		regex : /^\s*|\s*$/,
									 		regexText : '输入内容首尾不能有空白字符'
									 	},{
									 		fieldLabel : '房间',
									 		name : 'watcherRoom',
									 		id : 'watcherRoom',
									 		allowBlank : true,
									 		//blankText : '房间',
									 		//emptyText: '请输入房间',
									 		maxLength : 32,
									 		regex : /^\s*|\s*$/,
									 		regexText : '输入内容首尾不能有空白字符'
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
															
															Smart.showSucTipMsg("监视器更新成功!");
															btn.enable();
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("监视器更新失败 !");
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
			Ext.Msg.confirm('确认', '你确定删除该监视器?', function(btn) {
				if (btn == 'yes') {
					this.getEl().mask("删除监视器中,请稍等...");
					Ext.Ajax.request({
						url : 'deleteWatcher.action',
						params : {watcherId : record.data.watcherId},
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
		Smart.Watcher.superclass.onDestroy.call(this);
	}
});