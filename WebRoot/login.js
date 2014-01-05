
	
Smart.loginForm = new Ext.form.FormPanel({
    //baseCls: 'x-plain',
	id : 'login-form',
    title: "身份认证",
    labelWidth: 55,
    url:'',
    defaultType: 'textfield',
	bodyStyle:'padding:5px;',
    items: [{
        fieldLabel: '用户名',
        name: 'userName',
        maxLength : 45,
        anchor:'85%',  // anchor width by percentage
        allowBlank: false,
        value : 'admin',
        blankText : "用户名不能为空"
    },{
    	xtype: 'textfield',
    	inputType: 'password',
        fieldLabel: '密码',
        minLength : 6,
	    maxLength : 20,
        name: 'userPassword',
        allowBlank: false,
        value : '123456',
        blankText : "密码不能为空",
        anchor: '85%'  // anchor width by percentage
    }/*, {
        xtype: 'textfield',
        fieldLabel: '验证码',
        name: 'userCode',
        anchor: '85%'  // anchor width by percentage and height by raw adjustment
    }*/],
    buttons: [{
            text: '确认',
            handler: function(){
            		// 表单对象
            		var frm = Smart.loginForm.getForm();
            		
            		if(frm.isValid()){
            			// 登录状态栏
            			var loginSb = Ext.getCmp('login-statusbar');
            			loginSb.showBusy("身份认证 中...");
            			Smart.loginForm.getEl().mask("身份认证 中...");
            			frm.submit({
	            			clientValidation: true,
						    url: 'login.action',
						    success: function(form, action) {
						       //alert("ddd");
						    	// 登录成功，撤除MASK，隐藏登录框
						    	Smart.loginForm.getEl().unmask();
						    	loginSb.setStatus({
	                                text:'认证成功!',
	                                iconCls:'x-status-saved',
	                                clear: true
	                            });
						    	Smart.loginWin.hide();
						    	Smart.loginUser = action.result.user;
						    	//console.dir(Smart.loginUser);
						    	Ext.get('logout').update("注销<b>"+Smart.loginUser.userName+"</b>");
						    	//console.dir(Ext.get('logout'));
						    	createViewPort();
						    	
						    },
						    failure: function(form, action) {
					    		Smart.loginForm.getEl().unmask();
						        switch(action.failureType) {
						            case Ext.form.Action.CLIENT_INVALID:
						               loginSb.setStatus({
			                                text:'认证失败: 客户端数据不正确!',
			                                iconCls:'x-status-error',
			                                clear: true
			                            });
						                break;
						            case Ext.form.Action.CONNECT_FAILURE:
						            	loginSb.setStatus({
			                                text:'认证失败: 无法连接到服务器!',
			                                iconCls:'x-status-error',
			                                clear: true
			                            });
						                break;
						            case Ext.form.Action.SERVER_INVALID:
						               loginSb.setStatus({
			                                text:'认证失败: ' + action.result.msg,
			                                iconCls:'x-status-error',
			                                clear: true
			                            });
						               break;
						            default:
						            	loginSb.setStatus({
			                                text:'认证失败：未知原因!',
			                                iconCls:'x-status-error',
			                                clear: true
			                            });
						        }
					        }
            			});
            		}
            	}
        },{
            text: '取消',
            handler: function(){
            		Smart.loginWin.hide();
            }
        }]/**/
});

Smart.loginWin = new Ext.Window({
		id : 'login-win',
        title: '登录',
        width: 460,
        height:310,
        minWidth: 300,
        minHeight: 200,
        hidden:true,
        layout: 'border',
        plain:true,
        buttonAlign:'center',
        bbar: new Ext.ux.StatusBar({
            id: 'login-statusbar',
            defaultText: '就绪',
            plugins: new Ext.ux.ValidationStatus({
            	form :'login-form',
            	container : 'login-win'
            })
        }),
        items: [
        {
        	region : 'north',
        	baseCls: 'x-plain',
            html:'<img src="./resources/images/banner.png" width="444" height="72"/> ',
            height: 72,
            margins: '0 0 5 0'
        },{
        		region : 'center',
        		baseCls: 'x-plain',
        		layout: 'fit',
            	items : {
            		xtype: "tabpanel",
            		activeTab: 0,
            		border: false,
            		autoTabs:true,
            		deferredRender:false,
            		items: [Smart.loginForm,
            			{
            				title:"信息公告栏",
            				bodyStyle:"padding:5px;",
                			html: Smart.config.sysMsg
            			},{
            				title:'关于',
            				bodyStyle:'padding:5px;',
                			html: Smart.config.aboutUs
            			} 
            		]
            	},
            	margins: '0 0 0 0'
        }]
    });
