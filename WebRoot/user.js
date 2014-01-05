// 用户管理
Smart.UserNameExist = "用户名已经存在";

Smart.User = function(config){
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllUser.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'userId',type : 'int'},
				{name : 'userName',type : 'string'},
				{name : 'groupName',type : 'string'}
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
			{header : 'ID',	width : 90,	dataIndex : 'userId', sortable : true},
			{header : '用户',width : 90,	dataIndex : 'userName',id : 'userName',sortable : false},
			{header : '用户组',width : 90,	dataIndex : 'groupName',id : 'groupName',sortable : false}
		]
	});
	this.autoExpandColumn = 'userName';
	
	Ext.apply(this,{
		id : 'smart-user',
		title : "用户管理",
		iconCls: 'icon-user',
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		viewConfig : {forceFit : true},
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
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
	
	Smart.User.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.User, Ext.grid.GridPanel, {
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
				
		Smart.User.superclass.initComponent.call(this);
	},
	initAdd : function(){
		this.addWin = new Ext.Window({
								id : "smart-user-add-win",
								title : '添加用户',
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
									id : "smart-user-add-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'saveUser.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
									 		{	fieldLabel : '用户名',
										 		name : 'user.userName', 
										 		id : 'user.userName', 
										 		allowBlank : false, 
										 		maxLength : 45,
										 		validator : function(v){
										 			if(v == ""){
										 				return "用户名不能为空";
										 			}
										 			 Ext.Ajax.request({
										                url:'existUserName.action',
										                params:{
										                	userName: v
										                },
										                success: function(response, options) {  
										                   var responseArray = Ext.util.JSON.decode(response.responseText);                                                
										                   if(responseArray.success == true){ //用户名已经被注册                       
										                       SetValue("用户名已经存在");
										                   		//alert("true");
										                   }else{//用户名可以注册                       
										                       SetValue(true);
										                   		//alert("false");
										                   }  
										                }  
										            });
										 			
										             function SetValue(b){
											              Smart.UserNameExist = b;//给变量赋值
											         }
											         
									 				return Smart.UserNameExist;
									 			}
										 	},{	fieldLabel : '密码',
										 		name : 'user.userPassword', 
										 		id : 'user.userPassword', 
										 		allowBlank : false, 
										 		minLength : 6,
										 		maxLength : 20,
										 		inputType:'password'
										 	},{fieldLabel : '确认密码',
										 		name : 'user.userPasswordAgain',
										 		 id : 'user.userPasswordAgain', 
										 		 allowBlank : false,
										 		 minLength : 6,
										 		 maxLength : 20,
										 		 inputType:'password',
										 		 validator: function(v){
										 		 	if(Ext.get('user.userPassword').dom.value == v) return true;
										 		 	else return "两次输入的密码不一致!";
										 		 }
										 	},
										 	{ xtype : 'hidden',
										 		fieldLabel : '用户组ID',
										 		name : 'user.groupId',
										 		id : 'user.groupId'
										 	},{
												xtype : 'combo',
												id : 'user.groupName',
												fieldLabel : '用户组',
												mode : 'remote',
												editable : false,
												store : new Ext.data.Store({
													url : 'findAllGroup.action',
													reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
													[
														{name : 'groupId',type : 'int'},
														{name : 'groupName',type : 'string'}
													])
												}),
												displayField : 'groupName',
												valueField : 'groupName',
												triggerAction : 'all',
												pageSize: this.pageSize,
												emptyText : '请选择用户组',
												allowBlank : false,
												listeners : {
													'select' : function(combo, record, index) {
															combo.ownerCt.findById("user.groupId").setValue(record.data.groupId);
													}
												}
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
															//Smart.showSucTipMsg("用户添加成功!");
															btn.enable();
															if(Smart.setting.singleAddMode) this.addWin.hide();
															if(Smart.setting.autoloadAfterAdd) this.getStore().reload();
															if(Smart.setting.popupSucMsgAfterAdd) Smart.showSucTipMsg("用户添加成功!");
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("用户添加失败 !");
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
								id : "smart-user-edit-win",
								title : '编辑用户',
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
									id : "smart-user-edit-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'updateUser.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
										{
									 		xtype : 'hidden',
									 		fieldLabel : 'ID',
									 		name : 'userId',
									 		id : 'userId'
									 	},
									 	{	fieldLabel : '用户名',
									 		name : 'userName', 
									 		id : 'userName', 
									 		allowBlank : false, 
									 		maxLength : 45,
									 		validator : function(v){
									 			if(v == ""){
									 				return "用户名不能为空";
									 			}
									 			 Ext.Ajax.request({
									                url:'existUserName.action',
									                params:{
									                	userName: v,
									                	userId : Ext.get('userId').dom.value
									                },
									                success: function(response, options) {  
									                   var responseArray = Ext.util.JSON.decode(response.responseText);                                                
									                   if(responseArray.success == true){ //用户名已经被注册                       
									                       SetValue("用户名已经存在");
									                   }else{//用户名可以注册                       
									                       SetValue(true);
									                   }  
									                }  
									            });
									 			
									             function SetValue(b){
										              Smart.UserNameExist = b;//给变量赋值
										         }
										         
								 				return Smart.UserNameExist;
									 		}
									 	},{	fieldLabel : '密码',
									 		name : 'userPassword', 
									 		id : 'userPassword', 
									 		allowBlank : true, 
									 		minLength : 6,
									 		maxLength : 20,
									 		inputType:'password'
									 	},{
									 		 fieldLabel : '确认密码',
									 		 name : 'userPasswordAgain',
									 		 id : 'userPasswordAgain', 
									 		 allowBlank : true,
									 		 minLength : 6,
									 		 maxLength : 20,
									 		 inputType:'password',
									 		 validator : function(v){
									 		 	if(Ext.get('userPassword').dom.value == v) return true;
										 		else return "两次输入的密码不一致!";
									 		}
									 	},{ xtype : 'hidden',
									 		fieldLabel : '用户组ID',
									 		name : 'groupId',
									 		id : 'groupId'
									 	},{
											xtype : 'combo',
											id : 'groupName',
											fieldLabel : '用户组',
											mode : 'remote',
											editable : false,
											store : new Ext.data.Store({
												url : 'findAllGroup.action',
												reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
												[
													{name : 'groupId',type : 'int'},
													{name : 'groupName',type : 'string'}
												])
											}),
											displayField : 'groupName',
											valueField : 'groupName',
											triggerAction : 'all',
											pageSize: this.pageSize,
											emptyText : '请选择用户组',
											allowBlank : true,
											listeners : {
												'select' : function(combo, record, index) {
														combo.ownerCt.findById("groupId").setValue(record.data.groupId);
												}
											}
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
															
															Smart.showSucTipMsg("用户更新成功!");
															btn.enable();
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("用户更新失败 !");
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
			Ext.Msg.confirm('确认', '你确定删除该用户?', function(btn) {
				if (btn == 'yes') {
					this.getEl().mask("删除用户中,请稍等...");
					Ext.Ajax.request({
						url : 'deleteUser.action',
						params : {userId : record.data.userId},
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
		Smart.User.superclass.onDestroy.call(this);
	}
});