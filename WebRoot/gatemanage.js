// 闸机管理


// 闸机编辑窗口
Smart.GateEditWin = Ext.extend(Ext.Window, {
	rindex : 0,
	records : null,
	constructor: function(config) {

		Smart.GateEditWin.superclass.constructor.apply(this, arguments);

		//alert(this.records);
		//console.dir(this.records);
		if(null != this.records){
			//alert(this.records.length);
			this.get(0).getForm().loadRecord(this.records[0]);
			rindex = 0;
			this.get(0).buttons[0].disable();
			if(this.records.length == 1) {
				this.get(0).buttons[1].disable();
			}else {
				this.get(0).buttons[1].enable();
			}
		}
	},
	initComponent: function(){
		//alert("before parent initComponent");
		Smart.GateEditWin.superclass.initComponent.call(this);
		//alert("after parent initComponent");
	},
	resetRecord: function() {
		this.get(0).getForm().loadRecord(this.records[rindex]);
	},
	getRecord: function() {
		return this.records[rindex];
	},
	getRecords: function() {
		return this.records;
	},
	setRecords: function(rs){
		this.records = rs;
		this.get(0).getForm().loadRecord(this.records[0]);
		rindex = 0;
		this.get(0).buttons[0].disable();
		if(this.records.length == 1) {
			this.get(0).buttons[1].disable();
		} else {
			this.get(0).buttons[1].enable();
		}
	},
	movePrev: function(){
		if(rindex != 0) {
			rindex--;
			this.get(0).getForm().loadRecord(this.records[rindex]);
			if(rindex == 0){
				this.get(0).buttons[0].disable();
			}
			if(rindex == this.records.length - 2) {
				this.get(0).buttons[1].enable();
			}
		} 
		return rindex;
	},
	moveNext: function(){
		if(rindex != this.records.length -1) {
			rindex++;
			this.get(0).getForm().loadRecord(this.records[rindex]);
			if(rindex == 1){
				this.get(0).buttons[0].enable();
			}
			if(rindex == this.records.length - 1) {
				this.get(0).buttons[1].disable();
			}
		}
		return rindex;
	}
});

// 闸机参数设置窗口
Smart.GateSetWin = Ext.extend(Ext.Window,{
	formId : 'Smart-gate-set-form',
	gridId : 'Smart-gate-set-list',
	ds : null,
	colModel : null,
	autoExpandColumn : null,
	constructor: function(config) {
		// Your preprocessing here
		if(this.ds == null) {
				this.ds = new Ext.data.Store({
		        reader: new Ext.data.JsonReader({}, [
		            {name: 'gateId'},
		            {name: 'gateIp'},
		            {name: 'gateSet'},
		            {name: 'gateParam'}
		        ])
		    });
		}
		
		if(this.colModel == null) {
			this.colModel = new Ext.grid.ColumnModel([
		        {header: "编号", width: 50, sortable: true, locked:false, dataIndex: 'gateId'},
		        {header: "闸机IP", width: 80, sortable: true, dataIndex: 'gateIp'},
		        {header: "设置状态", width: 55, align:'center', sortable: false, renderer: function(v){ 
		        	if(typeof v == 'object'){
			        	switch(v.code){
			        		case -1 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/loading.gif" /></span>';
			        		case 0 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/accept.png" /></span>';
			        		case 1 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/saved.png" /></span>';
			        		case 2 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/exclamation.gif" /></span>';
			        	}
		        	}
		        	return v;
		        }, dataIndex: 'gateSet'},
		        {id:'gateSetScreen',header: "参数", width: 55, align:'center', sortable: false, renderer: function(v){ 
		        	if(typeof v == 'object' && null != v){
		        		var rText = '<span style="display:table;width:100%" ><table border="1px">';
		        		if(typeof v.gateSetDelay1 == 'number'){
		        			rText += '<tr><td>开门不通过延时关门时间: </td><td>' + v.gateSetDelay1 +'秒</td></tr>';
		        		}
		        		
		        		if(typeof v.gateSetDelay2 == 'number'){
		        			rText += '<tr><td>通过后关门时间: </td><td>' + v.gateSetDelay2 +'秒</td></tr>';
		        		}
		        		
		        		if(typeof v.gateSetMaxcount == 'number'){
		        			rText += '<tr><td>最大未通过人数: </td><td>' + v.gateSetMaxcount +'</td></tr>';
		        		}
		        		
		        		if(typeof v.gateSetState == 'number'){
		        			rText += '<tr><td>工作状态: </td><td>' + v.gateSetStateLabel +'</td></tr>';
		        		}
		        		
		        		return rText + '</table></span>';
		        	}
		        	return v;
		        }, dataIndex: 'gateParam'}
		    ]);
		}
		
		if(this.autoExpandColumn == null) {
			this.autoExpandColumn = 'gateSetScreen';
		}
		
		Smart.GateSetWin.superclass.constructor.apply(this, arguments);
		//this.on('beforehide',this.onBeforehide());
	},
	initComponent: function(){
		//alert("before parent initComponent");
		Smart.GateSetWin.superclass.initComponent.call(this);
		//alert("after parent initComponent");
		this.add({
			id : this.formId,
			xtype : 'form',
			border : false,
			baseCls : 'x-plain',
			bodyStyle : 'padding:5px',
			labelAlign : 'top',
			layout: 'column',
			items :[{
		            columnWidth: 0.60,
		            layout: 'fit',
	                tbar : [{xtype:'tbtext', text : '闸机列表:'},'->',
	                {
	                	xtype: 'button', text : '提交', iconCls : 'icon-config',scope : this,
						handler : this.submit
	                }],
		            items: {
		            	id : this.gridId,
		                xtype: 'grid',
		                ds: this.ds,
		                cm: this.colModel,
		                sm: new Ext.grid.RowSelectionModel({singleSelect: true,
		                    listeners: {
		                        rowselect: function(sm, row, rec) {
		                            //Ext.getCmp("company-form").getForm().loadRecord(rec);
		                        }
		                    }
		                }),
		                autoExpandColumn: this.autoExpandColumn,
		                height: 350,
		                border: true,
		                listeners: {
		                    viewready: function(g) {
		                        //g.getSelectionModel().selectRow(0);
		                    } // Allow rows to be rendered.
		                }
		            }
		        },{
		            columnWidth: 0.4,
		            xtype: 'fieldset',
		            labelWidth: 230,
		            title:'参数:',
		            defaults: {width: 230, border:false},    // Default config options for child items
		            defaultType: 'numberfield',
		            autoHeight: true,
		            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
		            border: false,
		            style: {
		                "margin-left": "10px", // when you add custom margin in IE 6...
		                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
		            },
		            items: [
		            {
		                fieldLabel: '开门不通过延时关门时间(秒)',
		                name: 'gateSetDelay1',
		                minValue : 0,
		                maxValue : 255
		            },{
		                fieldLabel: '通过后关门时间(毫秒)',
		                name: 'gateSetDelay2',
		                minValue : 0,
		                maxValue : 1275
		            },{
		                fieldLabel: '最大未通过人数',
		                name: 'gateSetMaxcount',
		                minValue : 0,
		                maxValue : 255
		            },{
		                xtype: 'radiogroup',
		                columns: 'auto',
		                fieldLabel: '工作状态',
		                id: 'gateSetState',
		                items: [{
		                    name: 'gateSetState',
		                    inputValue: 0,
		                    boxLabel: '自动'
		                }, {
		                    name: 'gateSetState',
		                    inputValue: 64,
		                    boxLabel: '常开'
		                }, {
		                    name: 'gateSetState',
		                    inputValue: 192,
		                    boxLabel: '常闭'
		                }]
		            }],
		            buttonAlign : 'right',
					minButtonWidth : 60,
					buttons : [{
						text : '应用',
						handler : function(btn) {
							var frm = btn.ownerCt.ownerCt.ownerCt.form;
							if (frm.isValid()) {
								var fv = frm.getFieldValues();
								if(fv.gateSetState != null 
									|| typeof fv.gateSetDelay1 == 'number' 
									|| typeof fv.gateSetDelay2 == 'number' 
									|| typeof fv.gateSetMaxcount == 'number' )
								this.updateGateParam(frm.getFieldValues());
								else {
									
								}
							}
						},
						scope: this
					}, {text : '重置',scope : this, handler : function(btn) { btn.ownerCt.ownerCt.ownerCt.form.reset(); }
					}, {text : '取消',handler : function(btn) { btn.ownerCt.ownerCt.ownerCt.ownerCt.hide();}
					}]
		        }
			]
		})
	},
	loadGateData : function(data){
		Ext.getCmp(this.gridId).getStore().loadData(data);
	},
	updateGateParam : function(params){
		var v = {
			gateSetDelay1 : params.gateSetDelay1,
			gateSetDelay2 : params.gateSetDelay2,
			gateSetMaxcount : params.gateSetMaxcount,
			gateSetStateLabel : params.gateSetState == null? "" : params.gateSetState.boxLabel,
			gateSetState :  params.gateSetState == null? "" : params.gateSetState.inputValue
		};
		var grid = Ext.getCmp(this.gridId);
		grid.getStore().each(function(r,i){
			r.set('gateSet',{code : 0, text: '就绪'});
			r.set('gateParam',v);
			r.commit();
		});
	},
	submit : function(btn){
		var grid = Ext.getCmp(this.gridId);
		this.getEl().mask("提高闸机参数,请稍等...");
		grid.getStore().each(function(r,i){
			//alert(r.data.gateIp);
			if(r.data.gateParam != null && r.data.gateParam != ''){
				r.set('gateSet',{code : -1, text: '设置中'});
				r.commit();
				var transactionId = Ext.Ajax.request({
					async: true, 
					timeout : 120000,
					url : 'setGate.action',
					jsonData  : {
						gateIp : r.data.gateIp,
						gateParam : r.data.gateParam},
					callback : function (options,success,response){
						if(success){
							var obj = Ext.decode(response.responseText);
							if(obj.success){
								r.set('gateSet',{code : 1, text: '设置成功'});
								r.commit();
							}else{
								r.set('gateSet',{code : 2, text: obj.errMsg});
								r.commit();
							}
						} else {
							r.set('gateSet',{code : 2, text: '提交请求失败'});
							r.commit();
						}
					},
					scope: r
				});
				
				//console.dir(transactionId);
			}
		}, this);
		
		this.getEl().unmask();
	}
	/*,
	onBeforehide : function(){
		alert("Beforehide");
	}*/
});


Smart.Gatemanage = function(config){
	// 定义数据源
	this.store = new Ext.data.Store({
		url : 'findAllGate.action',
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'gateId',type : 'auto'},
				{name : 'gateIp',type : 'string'},
				{name : 'gateMac',type : 'string'},
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
		/*	new Ext.grid.CheckboxSelectionModel({
				checkOnly :true,
				singleSelect : false }),*/
			{header : '编号',	width : 30,	dataIndex : 'gateId',
				renderer : function(v){
					return String.leftPad(v,5,'0');
				}},
			{header : '地址',	width : 90,	dataIndex : 'gateIp'},
			{header : '物理地址',	width : 90,	dataIndex : 'gateMac'},
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
					return v;
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
	
	Ext.apply(this,{
		id : 'smart-gatemanage',
		title : "闸机管理",
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		border : false,
		margins: '0 0 5 0',
		closable: true,
		viewConfig : {forceFit : true},
		sm : new Ext.grid.RowSelectionModel({singleSelect : false}),
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
	
	Smart.Gatemanage.superclass.constructor.apply(this, arguments);
}

Ext.extend(Smart.Gatemanage, Ext.grid.GridPanel, {
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
				},'->',
				{
					xtype: 'button',
					text : '参数设置',
					iconCls : 'icon-config-setting',
					handler : this.setParam,
					scope : this
	           	},
	           	{
	           		xtype: 'button',
					text : '待机画面设置',
					iconCls : 'icon-screen-saver',
					handler : this.setScreen,
					scope : this
	           	}]});
				
		Smart.Gatemanage.superclass.initComponent.call(this);
	},
	initAdd : function(){
		this.addWin = new Ext.Window({
								id : "smart-gate-add-win",
								title : '添加闸机',
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
									id : "smart-gate-add-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'saveGate.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
									 	{	
									 		fieldLabel : 'IP地址',
									 		name : 'gate.gateIp', 
									 		id : 'gate.gateIp', 
									 		allowBlank : false,
            								blankText: 'IP地址',
     										emptyText: '请输入IP地址',
     										regex : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
     										regexText : '请输入合法的IP地址,例：10.111.3.108'
									 	},{
									 		fieldLabel : '物理地址',
									 		name : 'gate.gateMac',
									 		id : 'gate.gateMac',
									 		allowBlank : true,
									 		maxLength : 32
									 	},{
									 		fieldLabel : '校区',
									 		name : 'gate.gateCampus',
									 		id : 'gate.gateCampus',
									 		allowBlank : false,
									 		blankText : '校区',
									 		emptyText: '请输入校区',
									 		maxLength : 16,
									 		regex : /^\s*|\s*$/,
									 		regexText : '输入内容首尾不能有空白字符'
									 	},{
									 		fieldLabel : '房间',
									 		name : 'gate.gateRoom',
									 		id : 'gate.gateRoom',
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
															btn.enable();
															if(Smart.setting.singleAddMode) this.addWin.hide();
															if(Smart.setting.autoloadAfterAdd) this.getStore().reload();
															if(Smart.setting.popupSucMsgAfterAdd) Smart.showSucTipMsg("闸机添加成功!");
															
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("闸机添加失败 !");
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
	initEdit : function(rs){
		this.editWin = new Smart.GateEditWin({
								id : "smart-gate-edit-win",
								records : rs,
								title : '编辑闸机',
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
									id : "smart-gate-edit-form",
									xtype : 'form',
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px 5px 0',
									labelAlign : 'right',
									labelWidth : 90,
									url : 'updateGate.action',
									defaults : {anchor : '93%',msgTarget : 'side'},
									defaultType : 'textfield',
									items : [
										{
									 		xtype : 'hidden',
									 		fieldLabel : 'ID',
									 		name : 'gateId',
									 		id : 'gateId'
									 	},
									 	{	
									 		fieldLabel : 'IP地址',
									 		name : 'gateIp', 
									 		id : 'gateIp', 
									 		allowBlank : false,
            								blankText: 'IP地址',
     										emptyText: '请输入IP地址',
     										regex : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
     										regexText : '请输入合法的IP地址,例：10.111.3.108'
									 	},{
									 		fieldLabel : '物理地址',
									 		name : 'gateMac',
									 		id : 'gateMac',
									 		allowBlank : true,
									 		maxLength : 32
									 	},{
									 		fieldLabel : '校区',
									 		name : 'gateCampus',
									 		id : 'gateCampus',
									 		allowBlank : false,
									 		blankText : '校区',
									 		emptyText: '请输入校区',
									 		maxLength : 16,
									 		regex : /^\s*|\s*$/,
									 		regexText : '输入内容首尾不能有空白字符'
									 	},{
									 		fieldLabel : '房间',
									 		name : 'gateRoom',
									 		id : 'gateRoom',
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
									buttons : [
									{
										text : '上一条',
										scope : this,
										handler : function(btn){
											//console.dir(btn.ownerCt.ownerCt.ownerCt);
											btn.ownerCt.ownerCt.ownerCt.movePrev();
										}
									},
									{
										text : '下一条',
										scope : this,
										handler : function(btn){
											//console.dir(btn.ownerCt.ownerCt.ownerCt);
											btn.ownerCt.ownerCt.ownerCt.moveNext();
										}
									},
									{
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
															var record = btn.ownerCt.ownerCt.ownerCt.getRecord();
															frm.updateRecord(record);
															record.commit();
															
															Smart.showSucTipMsg("闸机更新成功!");
															btn.enable();
														},
														failure : function(form, action) {
															btn.enable();
															Smart.showErrTipMsg("闸机更新失败 !");
														},
														scope: this
													});
											}
										},
										scope: this
									}, {text : '重置',scope : this, handler : function(btn) { 
											btn.ownerCt.ownerCt.ownerCt.resetRecord(); 
										}
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
			Ext.Msg.confirm('确认', '你确定删除该闸机?', function(btn) {
				if (btn == 'yes') {
					this.getEl().mask("删除闸机中,请稍等...");
					Ext.Ajax.request({
						url : 'deleteGate.action',
						params : {gateId : record.data.gateId},
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
		//var record = this.getSelectionModel().getSelected();
		var records = this.getSelectionModel().getSelections();
		if (records) {
			if(this.editWin == null) {
				this.initEdit(records); 
				this.editWin.show();
			}
			else {
				this.editWin.setRecords(records);
				this.editWin.show();
			}
		}
	},
	initSet : function(){
		this.setWin = new Smart.GateSetWin({
			id : "smart-gate-set",
			title : '闸机参数设置',
			width : 750,
			resizable : true,
			autoHeight : true,
			modal : true,
			closeAction : 'hide',
			maximizable : true  
		});
	},
	initSetScreen : function(){
		var ds = new Ext.data.Store({
	        reader: new Ext.data.JsonReader({}, [
	            {name: 'gateId'},
	            {name: 'gateIp'},
	            {name: 'gateSet'},
	            {name: 'gateScreen',type : 'string'}
	        ])
	    });
    
		var colModel = new Ext.grid.ColumnModel([
	        {header: "编号", width: 50, sortable: true, locked:false, dataIndex: 'gateId'},
	        {header: "闸机IP", width: 80, sortable: true, dataIndex: 'gateIp'},
	        {header: "状态", width: 55, align:'center', sortable: false, renderer: function(v){ 
	        	if(typeof v == 'object'){
		        	switch(v.code){
		        		case -1 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/loading.gif" /></span>';
		        		case 0 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/accept.png" /></span>';
		        		case 1 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/saved.png" /></span>';
		        		case 2 : return '<span style="display:table;width:100%" qtip="'+ v.text +'"><img src="./resources/extjs/ux/statusbar/images/exclamation.gif" /></span>';
		        	}
	        	}
	        	return v;
	        }, dataIndex: 'gateSet'},
	        {id:'gateSetScreen',header: "待机画面", width: 55, align:'center', sortable: false, renderer: function(v){ 
	        	if(v != ""){
	        		return '<span style="display:table;width:100%" qtip=\'<img src="system/screen/'+ v +'" />\'>'+ v +'</span>'
	        	}
	        	return v;
	        }, dataIndex: 'gateScreen'}
	    ]);
	    
	    var setScreenWin = new Ext.Window({
								id : "smart-gate-set-screen",
								title : '闸机待机画画设置',
								width : 750,
								resizable : true,
								autoHeight : true,
								modal : true,
								closeAction : 'hide',
								maximizable : true,
								listeners : {
									'hide' : function() {}
								},
								items : [{
									id : "smart-gate-set-screen-form",
									xtype : 'form',
									fileUpload : true,
									border : false,
									baseCls : 'x-plain',
									bodyStyle : 'padding:5px',
									labelAlign : 'left',
									url : 'setScreen.action',
									layout: 'column', 
									items : [
									{
							            columnWidth: 0.60,
							            layout: 'fit',
							            //title:'闸机列表',
						                tbar : [{xtype:'tbtext', text : '图片列表:'},{
											xtype : 'combo',
											tpl: '<tpl for="."><div ext:qtip="<img src=\'system/screen/{name}\'/>" class="x-combo-list-item">{name}</div></tpl>',
									        typeAhead: true,
									        forceSelection: true,
									        selectOnFocus:true,
											mode : 'remote',
											triggerAction : 'all',
											allowBlank : false,
											store : new Ext.data.Store({
												url : 'findAllScreen.action',
												reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
												[
													{name : 'name',type : 'string'}
												])
											}),
											displayField : 'name',
											valueField : 'name',
											emptyText : '请选择待机画面',
											listeners : {
												'select' : function(combo, record, index) {
													var grid = Ext.getCmp('smart-gate-set-screen-gatelist');
													grid.getStore().each(function(r,i){
														r.set('gateSet',{code : 0, text: '就绪'});
														r.set('gateScreen',record.data.name);
														r.commit();
													});
												}
											}
						                	
						                },{
						                	xtype: 'button',
						                	text : '重载列表',
											iconCls : '',
											handler : function(btn){
												console.dir(btn.ownerCt);
												btn.ownerCt.items.get(1).getStore().reload();
											}
						                },'->',
						                {
						                	xtype: 'button',
						                	text : '清除待机画面',
											iconCls : 'icon-hide-inherited',
											handler : function(btn){
												Ext.Msg.confirm('确认', '你确定清除待机画面?', function(btn) {
													if (btn == 'yes') {
														setScreenWin.getEl().mask("清除待机画面,请稍等...");
														var store = Ext.getCmp('smart-gate-set-screen-gatelist').getStore();
														var count = 0;
														store.each(function(r,i){
															r.set('gateSet',{code : -1, text: '设置中'});
															r.commit();
															Ext.Ajax.request({
																async: true, 
																timeout : 60000,
																url : 'clearGateSreen.action',
																params : {gateIp : r.data.gateIp},
																callback : function (options,success,response){
																	if(success){
																		var obj = Ext.decode(response.responseText);
																		if(obj.success){
																			r.set('gateSet',{code : 1, text: '清除成功'});
																			r.commit();
																		}else{
																			r.set('gateSet',{code : 2, text: obj.errMsg});
																			r.commit();
																		}
																	} else {
																		r.set('gateSet',{code : 2, text: '提交请求失败'});
																		r.commit();
																	}
																	count++;
																	if(count == store.getTotalCount()){
																		setScreenWin.getEl().unmask();
																	}
																},
																scope: r
															});
															
														},this);
													};
												}, this);
											}
						                },
						                {
						                	xtype: 'button',
						                	text : '提交待机画面',
											iconCls : 'icon-config',
											handler : function(btn){
												//console.dir(this);
												var grid = Ext.getCmp('smart-gate-set-screen-gatelist');
												//setScreenWin.getEl().mask("提交待机画面,请稍等...");
												grid.getStore().each(function(r,i){
													//alert(i);
													if(r.data.gateScreen != ""){
														r.set('gateSet',{code : -1, text: '设置中'});
														r.commit();
														Ext.Ajax.request({
															async: true, 
															timeout : 60000,
															url : 'setGateSreen.action',
															params : {gateIp : r.data.gateIp,
															gateScreen : r.data.gateScreen},
															callback : function (options,success,response){
																if(success){
																	var obj = Ext.decode(response.responseText);
																	if(obj.success){
																		r.set('gateSet',{code : 1, text: '设置成功'});
																		r.commit();
																	}else{
																		r.set('gateSet',{code : 2, text: obj.errMsg});
																		r.commit();
																	}
																} else {
																	r.set('gateSet',{code : 2, text: '提交请求失败'});
																	r.commit();
																}
															},
															scope: r
														});
													}
													
												}, this);
											},
											scope : this
						                }
						                ],
							            items: {
							            	id : 'smart-gate-set-screen-gatelist',
							                xtype: 'grid',
							                ds: ds,
							                cm: colModel,
							                sm: new Ext.grid.RowSelectionModel({
							                    singleSelect: true,
							                    listeners: {
							                        rowselect: function(sm, row, rec) {
							                            //Ext.getCmp("company-form").getForm().loadRecord(rec);
							                        }
							                    }
							                }),
							                autoExpandColumn: 'gateSetScreen',
							                height: 350,
							                border: true,
							                listeners: {
							                    viewready: function(g) {
							                        //g.getSelectionModel().selectRow(0);
							                    } // Allow rows to be rendered.
							                }
							            }
							        },{
							            columnWidth: 0.4,
							            xtype: 'fieldset',
							            labelWidth: 60,
							            title:'本地图片上传:',
							            defaults: {width: 170, border:false},    // Default config options for child items
							            defaultType: 'textfield',
							            autoHeight: true,
							            bodyStyle: Ext.isIE ? 'padding:0 0 5px 15px;' : 'padding:10px 15px;',
							            border: false,
							            style: {
							                "margin-left": "10px", // when you add custom margin in IE 6...
							                "margin-right": Ext.isIE6 ? (Ext.isStrict ? "-10px" : "-13px") : "0"  // you have to adjust for it somewhere else
							            },
							            items: [{
									            xtype: 'fileuploadfield',
									            id: 'gate-screen-file',
									            emptyText: '选择图片文件',
									            fieldLabel: '图片文件',
									            name: 'upload',
									            allowBlank : false,
									            buttonText: '',
									            buttonCfg: {
									                iconCls: 'upload-icon'
									            },
									            listeners :{
									            	fileselected : function(f,v){
									            		//console.dir(Ext.get('gate-screen-file-file'));
									            		//console.dir(Ext.get('logoFile'));
									            	    var url = 'file:///'+Ext.get('gate-screen-file-file').dom.value; 
								            		    if(Ext.isIE){     
						   									 var image = Ext.get('gate-screen-picture').dom;     
															 image.src = Ext.BLANK_IMAGE_URL;//覆盖原来的图片     
															 image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src= url;     
						                           		}else{     
						                                	Ext.get('gate-screen-picture').dom.src = Ext.get('gate-screen-file-file').dom.files.item(0).getAsDataURL();     
						                            	}
									            	}
									            }
									        },
									        {
									        	xtype : 'box',
												fieldLabel: '图片预览'
									        },
									        {
												id : 'gate-screen-picture',
												xtype : 'box',
												fieldLabel: '图片预览',
												//border: true,
												hideLabel : true,
												width  : 150,
												height : 120,
												anchor: '98%',
												autoEl : {
													tag: 'img',
													src: Ext.BLANK_IMAGE_URL,
													style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);'
												}
											}
							            ],
							            buttonAlign : 'right',
										minButtonWidth : 60,
										buttons : [{
											text : '上传',
											handler : function(btn) {
												var frm = btn.ownerCt.ownerCt.ownerCt.form;
												//console.dir(frm);
												
												if (frm.isValid()) {
														btn.disable();
														frm.submit({
															waitTitle : '请稍候',
															waitMsg : '正在提交表单数据,请稍候...',
															success : function(form, action) {
																//console.dir(action);
																var grid = Ext.getCmp('smart-gate-set-screen-gatelist');
																grid.getStore().each(function(r,i){
																	r.set('gateSet',{code : 0, text: '就绪'});
																	r.set('gateScreen',action.result.imageUrl);
																	r.commit();
																});
																
																Smart.showSucTipMsg("图片上传成功!");
																btn.enable();
															},
															failure : function(form, action) {
																btn.enable();
																Smart.showErrTipMsg("图片上传失败 !");
															},
															scope: this
														});
												}
											},
											scope: this
										}, {text : '重置',scope : this, handler : function(btn) { btn.ownerCt.ownerCt.ownerCt.form.reset(); }
										}, {text : '取消',handler : function(btn) { btn.ownerCt.ownerCt.ownerCt.ownerCt.hide();}
										}]
							        }]
									
								}]
							});
	    
	    Ext.applyIf(this,
			{
				setScreenWin : setScreenWin
			}
		);
	},
	setParam : function(btn) {
		//alert("setParam");
		// Check if user select multiple user to set param
		//var records = this.getSelectionModel().getSelected();
		//console.dir(records);
		
		var selModel = this.getSelectionModel();
		var selCount = selModel.getCount();
		if(selCount>0){
			
			var records = selModel.getSelections();
			var gateListData = [];
			for(var i=0; i < selCount; i++) {
				//alert(records[i].data.gateIp);
				gateListData[i] = {
					'gateId' : records[i].data.gateId,
					'gateIp' : records[i].data.gateIp,
					'gateSet' : '',
					'gateParam' : ''
				};
			}
			
			if(!this.setWin){
				this.initSet();
			}
			this.setWin.loadGateData(gateListData);	
			this.setWin.show();
		}
	},
	setScreen : function(btn) {
		//alert("setScreen");
		var selModel = this.getSelectionModel();
		var selCount = selModel.getCount();
		if( selCount > 0){
			
			var records = selModel.getSelections();
			var gateListData = [];
			for(var i=0; i < selCount; i++) {
				//alert(records[i].data.gateIp);
				gateListData[i] = {
					'gateId' : records[i].data.gateId,
					'gateIp' : records[i].data.gateIp,
					'gateSet' : '',
					'gateScreen' : ''
				};
			}
			//console.dir(records);
			if(!this.setScreenWin){
				this.initSetScreen();
			}
			//console.dir(gateListData);
			Ext.getCmp('smart-gate-set-screen-gatelist').getStore().loadData(gateListData);
			this.setScreenWin.show();
		}
	},
	onDestroy : function(){
		if(this.addWin != null) this.addWin.close();
		if(this.editWin != null) this.editWin.close();
		if(this.setWin){
			this.setWin.close();
		}
		
		if(this.setScreenWin) {
			this.setScreenWin.close();
		}
		Smart.Gatemanage.superclass.onDestroy.call(this);
	}
});