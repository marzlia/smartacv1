Smart.Group = function (){
	
	this.pageSize = 20;
	
	this.cm = new Ext.grid.ColumnModel({
		columns: [
			{header : 'ID',	width : 90,	dataIndex : 'groupId',id : 'groupId',sortable : true},
			{
				header : '用户组',	
				width : 90,	
				dataIndex : 'groupName',
				id : 'groupName',
				sortable : true,
			    menuDisabled : true,
			    editor : new Ext.form.TextField({
			    	readOnly : false,
			    	emptyText : '请输入用户组名',
			    	allowBlank : false,
			    	listeners : {
							'blur' : function(f) {
							/*	if(!f.isValid()){
									this.ds.remove(0);
									Smart.showErrTipMsg("您的输入有误,该数据被自动删除!");
								}*/
							},
							scope: this
						}
			    	})
			}
		],
		defaults: {
	        sortable: true,
	        menuDisabled: true,
	        width: 100
    	}
	});
	
	this.getGrid = function(){
		return this.get(0);
	}
	
	this.getPanel = function(){
		return this.get(1);
	}
	
	this.gridtbar= [{
			xtype: 'button',
			text : '添加',
			iconCls : 'icon-add',
			scope: this,
			handler : function() {
				var groupRecordType = this.ds.recordType;
                var groupRecord = new groupRecordType({
                    groupId : 0,
                    groupName : ""
                });
                this.getGrid().stopEditing();
                this.ds.insert(0, groupRecord);
                this.getGrid().startEditing(0, 1);

			}
		},{
			xtype: 'button',
			text : '删除',
			iconCls : 'icon-del',
			scope : this,
			handler : function() {
				var record = this.getGrid().getSelectionModel().getSelected();
				if (record) {
					if(record.data.groupId == 0){
						this.getGrid().getStore().remove(record);
						return;
					}else if(record.data.groupId == 1){
						Smart.showWarnTipMsg("系统用户组Admin不能删除!");
						return;
					}
					
					var delFn = function(btn) {
						if (btn == 'yes') {
							this.getEl().mask("删除用户组中,请稍等...");
							Ext.Ajax.request({
								url : 'deleteGroup.action',
								params : {groupId : record.data.groupId},
								callback : function (options,success,response){
									this.getEl().unmask();
									var obj = Ext.decode(response.responseText);
									if(success & obj.success){
										this.getGrid().getStore().remove(record);
									}else{
										if(obj.msg){
											Smart.showErrTipMsg(obj.msg);
										}else{
											Smart.showErrTipMsg('删除时发生错误!');
										}
									}
								},
								scope: this
							});
						};
					}
					
					Ext.Msg.confirm('确认', '你确定删除该用户组?', delFn, this);
				}
			}
		},{
			xtype: 'button',
			text : '编辑权限',
			iconCls : 'icon-edit',
			scope : this,
			handler : function(){
				//alert(this.ownerCt.ownerCt.ownerCt.id);
				var record = this.getGrid().getSelectionModel().getSelected();
				if(record){//选中一条记录 
					//console.dir(this.getPanel().get(0).getView().scroller.dom);
					if (this.getPanel().hidden){
						this.getPanel().show();
						this.doLayout(true,true);
					}
				}
			}
		},{
			xtype : 'textfield',
			width : 200,
			emptyText : '多条件可用逗号或者空格隔开!',
			listeners : {
				'specialkey' : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						this.ds.baseParams.conditions = field.getValue();
						this.ds.load({params : {start : 0,limit : this.pageSize}});
					}
				},
				scope: this
			}
		},{
			text : '查询',
			iconCls : 'icon-search',
			handler : function() {
					this.ds.baseParams.conditions = this.get(0).getTopToolbar().get(3).getValue();
					this.ds.load({params : {start : 0,limit : this.pageSize}});
				}
			,
			scope: this
		}];

	
	
	this.ds = new Ext.data.Store({
		url : 'findAllGroup.action',
		autoDestroy : true,
		remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'groupId',type : 'int'},
				{name : 'groupName',type : 'string'}
			]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				this.getGrid().getSelectionModel().selectFirstRow();
			}
		},
		sortInfo: {
    		field: 'groupId',
    		direction: 'ASC'
		}
	});
	
	this.grid = {
		xtype : 'editorgrid',
		region : 'center',
		border : false,
		//autoHeight : true,
		loadMask : {msg : '数据加载中...'},
		enableColumnMove : false,
		cm : this.cm,
		ds : this.ds,
		sm : new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : function( sm,rowIndex,r) {
						//alert(r.data.groupId);
						//加载权限数据
						this.groupprivStore.baseParams.groupId = r.data.groupId;
						this.groupprivStore.load();
						
						this.privStore.baseParams.groupId = r.data.groupId;
						this.privStore.load();
					},
					scope : this
				}
			}),
		clicksToEdit: 2,
		autoExpandColumn : 'groupId',
		viewConfig : {forceFit : true},
		tbar : this.gridtbar,
		bbar : new Ext.PagingToolbar({
			pageSize : this.pageSize,
			store : this.ds,
			displayInfo : true,
			displayMsg : '第 {0} - {1} 条 共 {2} 条',
			emptyMsg : "没有记录"
		}),
		listeners : {
			'afteredit' : function(e) {
				//Smart.debug(e.record.data.groupId);
				if(e.record.data.groupId == 0){
					this.getEl().mask("添加用户组中,请稍等...");
					Ext.Ajax.request({
						url : 'saveGroup.action',
						params : {'group.groupName' : e.value},
						callback : function (options,success,response){
							this.getEl().unmask();
							var obj = Ext.decode(response.responseText);
							if(success & obj.success){
								//Smart.debug(obj.groupId);
								if(obj.groupId){
									e.record.data.groupId=obj.groupId;
									e.record.commit();
									//this.ds.reload();
								}
								//this.getStore().remove(record);
							}else{
								Smart.showErrTipMsg('添加时发生错误!');
							}
						},
						scope: this
					});
				}else{
					this.getEl().mask("更新用户组中,请稍等...");
					Ext.Ajax.request({
						url : 'updateGroup.action',
						params : {'groupName' : e.value,
						groupId : e.record.data.groupId},
						callback : function (options,success,response){
							this.getEl().unmask();
							var obj = Ext.decode(response.responseText);
							if(success & obj.success){
								Smart.debug(obj.groupId);
								//this.getStore().remove(record);
								e.record.commit(false);
							}else{
								e.record.set(e.field, e.originalValue);
								Smart.showErrTipMsg('更新时发生错误!');
							}
						},
						scope: this
					});
				}
				/*
				Ext.Ajax.request({
					url : 'updateGroup.action',
					params : {
					    groupName: e.value,
						groupId : e.record.data.groupId
					},
					success : function() {
					},
					failure : function() {
						Ext.Msg.show({
							title : '错误提示',
							msg : '修改数据发生错误,操作将被回滚!',
							fn : function() {e.record.set(e.field, e.originalValue);},
							buttons : Ext.Msg.OK,
							icon : Ext.Msg.ERROR
						});
					}
				});
				*/
			},
			'rowdblclick':function(grid,rowIndex,e) {
				
			},
			'rowclick':function(grid,rowIndex,e) {
								
			},
			scope : this,
			'render' : function(grid){
				grid.getStore().load({params : {start : 0,limit : this.pageSize}});
			}
		}
	};

	this.groupprivStore = new Ext.data.Store({
		url : 'findAllPrivassign.action',
		baseParams : {'groupId' : 0},
		autoDestroy : true,
		//remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'privId',type : 'int'},
				{name : 'privName',type : 'string'},
				{name : 'privDesc',type : 'string'}
			]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
				//this.getGrid().getSelectionModel().selectFirstRow();
				//alert("load sucess");
			}
		}
	});
	
	this.groupprivGrid = {
			xtype : 'grid',
			region : 'center',
			//width : '50%',
			border : false,
			loadMask : {msg : '数据加载中...'},
			ddGroup          : 'privGridDDGroup',
	        store            : this.groupprivStore,
	        colModel: new Ext.grid.ColumnModel({
	        	defaults: {
		            //width: 30,
		            sortable: true
		        },
	        	columns          : [
	        		{header: "ID", dataIndex: 'privId'},
					{header: "权限名称",  dataIndex: 'privName'},
					{id : 'group_privDesc', header: "权限描述",  dataIndex: 'privDesc'}]
	        }),
			enableDragDrop   : true,
	        stripeRows       : true,
	        autoExpandColumn : 'group_privDesc',
	        //viewConfig : {forceFit : true},
	        margins : '0 0 0 0',
	        style:'border-left: 1px solid #8db2e3;',
	        title            : '已分配权限',
	        listeners : {
	        	'afterrender': function(grid){
	        		//console.dir(this.getView().scroller.dom);
	        		var groupprivGridDropTargetEl = grid.getView().scroller.dom;
	        		var groupprivGridDropTarget = new Ext.dd.DropTarget(groupprivGridDropTargetEl, {
			                ddGroup    : 'groupprivGridDDGroup',
			                notifyDrop : function(ddSource, e, data){
			                        var records =  ddSource.dragData.selections;
			                        //alert("Save records to database");
			                        var privIdArray = [];
			                        Ext.each(records,function(r){
			                        	//alert(r.data.privId);
			                        	privIdArray.push(r.data.privId);
			                        });
			                        
			                        //console.debug(privIdArray);
			                        //console.debug(data.grid.getStore().baseParams.groupId);
			                        
			                        data.grid.ownerCt.getEl().mask("分配权限中,请稍等!");
			                        
			                        Ext.Ajax.request({
										url : 'savePrivassign.action',
										params : {'privIdArray' : privIdArray,
										'groupId' : data.grid.getStore().baseParams.groupId },
										callback : function (options,success,response){
											data.grid.ownerCt.getEl().unmask();
											var obj = Ext.decode(response.responseText);
											if(success & obj.success){
						                        
												Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
						                        grid.getStore().add(records);
						                        grid.getStore().sort('privId', 'ASC');
											}else{
												Smart.showErrTipMsg('分配时发生错误,请重新分配!');
											}
										},
										scope: this
									}); /**/
									
									//Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
						            //grid.getStore().add(records);
						            //grid.getStore().sort('privId', 'ASC');
									
			                        return true;
			                }
			        });
	        	},
	        	scope : this
	        }
    };
    
    this.privStore = new Ext.data.Store({
		url : 'findAllPriv.action',
		baseParams : {'groupId' : 0},
		autoDestroy : true,
		//remoteSort : true,
		reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
			[
				{name : 'privId',type : 'int'},
				{name : 'privName',type : 'string'},
				{name : 'privDesc',type : 'string'}
			]
		),
		listeners: {
			scope: this,
			'load' : function(store,records, options){
			}
		}
	});

    // create the destination Grid
    this.privGrid = {
    		region : 'west',
    		xtype : 'grid',
    		border : false,
    		width : '50%',
    		//width : '150',
    		loadMask : {msg : '数据加载中...'},
			ddGroup          : 'groupprivGridDDGroup',
	        store            : this.privStore,
	        colModel: new Ext.grid.ColumnModel({
		        	defaults: {
			            //width: 50,
			            sortable: true
			        },
		        	columns          : [
		        		{header: "ID", dataIndex: 'privId'},
						{header: "权限名称",  dataIndex: 'privName'},
						{id : 'privDesc', header: "权限描述",  dataIndex: 'privDesc'}]
		    }),
			enableDragDrop   : true,
	        stripeRows       : true,
	        autoExpandColumn : 'privDesc',
	        viewConfig : {forceFit : true},
	        margins : '0 0 0 0',
	        style:'border-right: 1px solid #8db2e3;',
	        title            : '未分配权限',
	        listeners : {
	        	'afterrender': function(grid){
	        		//console.dir(this.getView().scroller.dom);
	        		var privGridDropTargetEl = grid.getView().scroller.dom;
	        		var privGridDropTarget = new Ext.dd.DropTarget(privGridDropTargetEl, {
			                ddGroup    : 'privGridDDGroup',
			                notifyDrop : function(ddSource, e, data){
			                        var records =  ddSource.dragData.selections;
			                        
			                        var privIdArray = [];
			                        Ext.each(records,function(r){
			                        	privIdArray.push(r.data.privId);
			                        });
			                        
			                        data.grid.ownerCt.getEl().mask("分配权限中,请稍等!");
			                        
			                        Ext.Ajax.request({
										url : 'deletePrivassign.action',
										params : {'privIdArray' : privIdArray,
										'groupId' : data.grid.getStore().baseParams.groupId },
										callback : function (options,success,response){
											data.grid.ownerCt.getEl().unmask();
											var obj = Ext.decode(response.responseText);
											if(success & obj.success){
																		                        
												Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
						                        grid.getStore().add(records);
						                        grid.getStore().sort('privId', 'ASC');
											}else{
												Smart.showErrTipMsg('分配时发生错误,请重新分配!');
											}
										},
										scope: this
									}); /*
			                        Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
			                        grid.getStore().add(records);
			                        grid.getStore().sort('privId', 'ASC');*/
			                        
			                        return true;
			                }
			        });
	        	},
		        scope : this
		    }
    };


	
	Smart.Group.superclass.constructor.call(this, {
		id: "smart-group",
		title : "用户组管理",
		iconCls: 'icon-usergroup',
		border : false,
		margins: '0 0 5 0',
		layout : 'border',
		viewConfig : {forceFit : true},
		defaults : {
				collapsible : false,
				split : true
		},
		closable: true,
		items : [this.grid,{
			id : 'grouppriv',
			header: false,
			border : false,
			region : 'south',
			collapseMode:'mini',
			hidden : true,
			height       : 200,
			layout : 'border',
			defaults : {
				collapsible : false,
				split : true
			},
			items        : [this.privGrid,this.groupprivGrid],//
			listeners : {
				'bodyresize' : function(p,width,height){
					//if(!p.hidden){
					//	p.doLayout(true,true);
					//}
					//alert("resize");
					//Ext.example.msg("提示","窗体尺寸改变");
				} 
			}
		}],
		listeners : {
			'show' : function(){
				//alert(this.title);
				//this.get(1).get(1).doLayout();
			},
			'afterrender' : function(p){
				//console.dir(this.getPanel().get(0).getView().scroller.dom);
			},
			'bodyresize' : function(p, width,height){
				//alert("resize");
				//Ext.example.msg("提示","窗体尺寸改变");
			} 
		}
	});
}

Ext.extend(Smart.Group, Ext.Panel, {
	initComponent: function(){
		Smart.Group.superclass.initComponent.call(this);
	}
});