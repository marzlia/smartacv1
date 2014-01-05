/*
 * @ 定时信息维护模块
*/

// 声明Smart.Timer类，用于完成定时信息管理模块的功能
// 该类基于Ext的GridPanel类进行扩展，首先定义该类的构造器
// 注：对于JS，类是与构造器是同时定义的
Smart.Timer = function (config){
	
	this.pageSize = 20;
	
	this.cm = new Ext.grid.ColumnModel({
		columns: [
			{header : '编号',	width : 30,	dataIndex : 'timerId'},
			{header : '开始时间',	width : 90,	dataIndex : 'timerBegin',renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
			{header : '结束时间',	width : 90,	dataIndex : 'timerEnd',renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
			{header : '激活',	width : 90,	dataIndex : 'timerEnable', align : 'center', renderer: Smart.Num2Bool},
			{header : '是否创建新进程',	width : 90,	dataIndex : 'timerCreatenew', align : 'center',renderer: Smart.Num2Bool},
			{header : '定时任务',	width : 90,	dataIndex : 'timerTask'},
			//{header : '循环方式',	width : 90,	dataIndex : 'cycleId'},
			{header : '循环方式',	width : 90,	dataIndex : 'cycleName'}
		],
		defaults: {
	        sortable: true,
	        menuDisabled: false,
	        width: 100
    	}
	});
	this.autoExpandColumn = 'timerTask';

	//alert(this.id + " Constructor 1");
	this.ds = new Ext.data.Store({
		url : 'findAllTimer.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'timerId',type : 'int'},
				{name : 'timerBegin',type : 'date',dateFormat :'Y-m-d\\TH:i:s'},
				{name : 'timerEnd',type : 'date',dateFormat :'Y-m-d\\TH:i:s'},
				{name : 'timerEnable',type : 'int'},
				{name : 'timerCreatenew',type : 'int'},
				{name : 'timerTask',type : 'string'},
				{name : 'cycleId',type : 'int'},
				{name : 'cycleName',type : 'string'}
			]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getSelectionModel().selectFirstRow();
			}
		}
	});
	
	// 此处设定预配置的参数项
	Ext.apply(this,{
		id : 'smart-timer',
		title : "数据更新设置",
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
			store : this.ds,
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
	
	//alert(this.id + " Constructor 2");
	// 由于JS没有自动调用父类构造器的机制，在构造器中需要显式调用
	// 注：在父类的构造函数中会调用initComponent
	Smart.Timer.superclass.constructor.apply(this, arguments);
	
	//alert(this.id + " Constructor 3");
	
	//return this; // 此句不加会被自动加上
}

Ext.extend(Smart.Timer,Ext.grid.GridPanel,{
	initComponent: function(){
		// 添加Toolbar
		Ext.apply(this, {
	           	tbar:[{xtype: 'button',
					text : '添加',
					iconCls : 'icon-add',
					handler : this.addTimer,
					scope : this
	           	},{	xtype: 'button',
					text : '删除',
					iconCls : 'icon-del',
					handler : this.delTimer,
					scope : this
	           	},{
					xtype: 'button',
					text : '编辑',
					iconCls : 'icon-edit',
					scope : this,
					handler : this.editTimer
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
		Smart.Timer.superclass.initComponent.call(this);
		//initAdd();
		//initUpdate();
	},
	initAdd : function(){
		//alert("initAdd");
		//alert(this.id);
		this.addWin = new Ext.Window({
								id : "smart-timer-add-win",
								title : '添加定时器',
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
									id : "smart-timer-add-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'saveTimer.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
									 	{	
									 		xtype : 'datefield',
									 		fieldLabel : '开始时间',
									 		name : 'timer.timerBegin', 
									 		id : 'timer.timerBegin', 
									 		allowBlank : false,
									 		altFormats: 'd/m/Y H:i:s',
            								format : 'Y-m-d H:i:s',
            								blankText: '开始时间',
     										emptyText: '请输入开始时间'
									 	},{	
									 		xtype : 'datefield',
									 		fieldLabel : '结束时间',
									 		name : 'timer.timerEnd', 
									 		id : 'timer.timerEnd', 
									 		allowBlank : true,
									 		altFormats: 'd/m/Y H:i:s',
            								format : 'Y-m-d H:i:s'//,
            								//blankText: '结束时间',
     										//emptyText: '请输入结束时间'
									 	},
									 	{
									 		xtype : 'hidden',
									 		fieldLabel : '循环方式ID',
									 		name : 'timer.cycleId',
									 		id : 'timer.cycleId'
									 	},{
											xtype : 'combo',
											id : 'timer.cycleName',
											fieldLabel : '循环方式',
											mode : 'remote',
											triggerAction : 'all',
											editable : false,
											allowBlank : false,
											store : new Ext.data.Store({
												url : 'findAllCycle.action',
												reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
												[
													{name : 'cycleId',type : 'int'},
													{name : 'cycleName',type : 'string'}
												])
											}),
											displayField : 'cycleName',
											valueField : 'cycleId',
											emptyText : '请选择用户组',
											listeners : {
												'select' : function(combo, record, index) {
														combo.ownerCt.findById("timer.cycleId").setValue(record.data.cycleId);
												}
											}
										},{
									 		xtype : 'radiogroup',
									 		fieldLabel : '是否激活',
									 		//name : 'timer.timerEnable',
									 		id : 'timer.timerEnable',
									 		allowBlank : false,
									 		blankText : '选择是否激活',
									 		items: [
								                {boxLabel: '是', name: 'timer.timerEnable', inputValue: 1},
								                {boxLabel: '否', name: 'timer.timerEnable', inputValue: 0, checked: true}
								            ]
									 	},{
									 		xtype : 'radiogroup',
									 		fieldLabel : '是否创建新进程',
									 		id : 'timer.timerCreatenew',
									 		allowBlank : false,
									 		blankText : '选择是否创建新进程',
									 		items: [
								                {boxLabel: '是', name: 'timer.timerCreatenew', inputValue: 1},
								                {boxLabel: '否', name: 'timer.timerCreatenew', inputValue: 0, checked: true}
								            ]
									 	},{
									 		fieldLabel : '定时任务',
									 		name : 'timer.timerTask',
									 		id : 'timer.timerTask',
									 		value : 'notepad.exe',
									 		allowBlank : false,
									 		blankText : '定时任务',
									 		emptyText: '请输入定时任务',
									 		maxLength : 128
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
															btn.enable();
															if(Smart.setting.singleAddMode) this.addWin.hide();
															if(Smart.setting.autoloadAfterAdd) this.getStore().reload();
															if(Smart.setting.popupSucMsgAfterAdd) Smart.showSucTipMsg("定时器添加成功!");
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("定时器添加失败 !");
														},
														scope : this
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
		//alert("initEdit");
		this.editWin = new Ext.Window({
								id : "smart-timer-edit-win",
								title : '编辑定时器',
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
									id : "smart-timer-edit-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'updateTimer.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
										{
									 		xtype : 'hidden',
									 		fieldLabel : 'ID',
									 		name : 'timerId',
									 		id : 'timerId'
									 	},
									 	{	
									 		xtype : 'datefield',
									 		fieldLabel : '开始时间',
									 		name : 'timerBegin', 
									 		id : 'timerBegin', 
									 		allowBlank : false,
									 		altFormats: 'd/m/Y H:i:s',
            								format : 'Y-m-d H:i:s',
            								blankText: '开始时间',
     										emptyText: '请输入开始时间'
									 	},{	
									 		xtype : 'datefield',
									 		fieldLabel : '结束时间',
									 		name : 'timerEnd', 
									 		id : 'timerEnd', 
									 		allowBlank : true,
									 		altFormats: 'd/m/Y H:i:s',
            								format : 'Y-m-d H:i:s'//,
            								//blankText: '结束时间',
     										//emptyText: '请输入结束时间'
									 	},
									 	{
									 		xtype : 'hidden',
									 		fieldLabel : '循环方式ID',
									 		name : 'cycleId',
									 		id : 'cycleId'
									 	},{
											xtype : 'combo',
											id : 'cycleName',
											name : 'cycleName',
											fieldLabel : '循环方式',
											mode : 'remote',
											triggerAction : 'all',
											editable : false,
											allowBlank : false,
											store : new Ext.data.Store({
												url : 'findAllCycle.action',
												reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
												[
													{name : 'cycleId',type : 'int'},
													{name : 'cycleName',type : 'string'}
												])
											}),
											displayField : 'cycleName',
											valueField : 'cycleName',
											emptyText : '请选择用户组',
											listeners : {
												'select' : function(combo, record, index) {
														combo.ownerCt.findById("cycleId").setValue(record.data.cycleId);
												}
											}
										},{
									 		xtype : 'radiogroup',
									 		fieldLabel : '是否激活',
									 		//name : 'timer.timerEnable',
									 		id : 'timerEnable',
									 		allowBlank : false,
									 		blankText : '选择是否激活',
									 		items: [
								                {boxLabel: '是', name: 'timerEnable', inputValue: 1},
								                {boxLabel: '否', name: 'timerEnable', inputValue: 0, checked: true}
								            ]
									 	},{
									 		xtype : 'radiogroup',
									 		fieldLabel : '是否创建新进程',
									 		id : 'timerCreatenew',
									 		allowBlank : false,
									 		blankText : '选择是否创建新进程',
									 		items: [
								                {boxLabel: '是', name: 'timerCreatenew', inputValue: 1},
								                {boxLabel: '否', name: 'timerCreatenew', inputValue: 0, checked: true}
								            ]
									 	},{
									 		fieldLabel : '定时任务',
									 		name : 'timerTask',
									 		id : 'timerTask',
									 		value : 'notepad.exe',
									 		allowBlank : false,
									 		blankText : '定时任务',
									 		emptyText: '请输入定时任务',
									 		maxLength : 128
									 	}
									 	
									],
									buttonAlign : 'right',
									minButtonWidth : 60,
									buttons : [{
										text : '更新',
										handler : function(btn) {
											var frm = btn.ownerCt.ownerCt.form;
											
											if (frm.isValid() && frm.isDirty() ) {
													btn.disable();
													frm.submit({
														waitTitle : '请稍候',
														waitMsg : '正在提交表单数据,请稍候...',
														success : function(form, action) {
															// 更新UI上的记录数据
															var record = this.getSelectionModel().getSelected();
															frm.updateRecord(record);
															record.commit();
															Smart.showSucTipMsg("定时器更新成功!");
															btn.enable();
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("定时器更新失败 !");
														},
														scope : this
													});
											}
										},
										scope: this
									}, {	
											text : '重置',
											handler : function(btn) {
												btn.ownerCt.ownerCt.form.loadRecord(this.getSelectionModel().getSelected());
												//btn.ownerCt.ownerCt.form.reset();
											},
											scope : this
									}, {text : '取消',handler : function() {this.ownerCt.ownerCt.ownerCt.hide();}
									}]
									
								}]
							});
	},
	initEvents : function(){
        Smart.Timer.superclass.initEvents.call(this);
        //this.on('beforedestroy', function(){ alert("before destroy"); }, this);
    },
	addTimer : function(btn){
		if(this.addWin == null) this.initAdd(); 
		if(this.addWin != null) this.addWin.show();
	},
	delTimer : function(btn){
		var record = this.getSelectionModel().getSelected();
		if (record) {
			Ext.Msg.confirm('确认', '你确定删除该定时器?', function(btn) {
				if (btn == 'yes') {
					this.getEl().mask("删除定时器中,请稍等...");
					Ext.Ajax.request({
						url : 'deleteTimer.action',
						params : {timerId : record.data.timerId},
						callback : function (options,success,response){
							this.getEl().unmask();
							var obj = Ext.decode(response.responseText);
							if(success & obj.success){
								this.getStore().remove(record);
							}else{
								Smart.showErrTipMsg('删除时发生错误!');
							}
						},
						scope: this
					});
				};
			}, this);
		}
	},
	editTimer : function(btn){
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
		//alert("Destroy components");
		if(this.addWin != null) this.addWin.close();
		if(this.editWin != null) this.editWin.close();
		Smart.Timer.superclass.onDestroy.call(this);
	}
});
