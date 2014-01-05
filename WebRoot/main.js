Ext.BLANK_IMAGE_URL = './resources/images/default/s.gif';
Ext.chart.Chart.CHART_URL = './resources/charts.swf';

function createViewPort(){
	
	if(Smart.viewport){
		Ext.getCmp('header').show();
		Ext.getCmp('doc-body').show();
		Ext.getCmp('admin-tree').getRootNode().reload();
		Ext.getCmp('admin-tree').show();
		Ext.getCmp('smart-statusbar').show();
		return;
	}
	
	Ext.get('header').show();
	var header =	{
				id : 'header',
            	cls: 'docs-header',
		        height: 40,
		        region:'north',
		        xtype:'box',
		        el:'header',
		        border:false,
		        margins: '0 0 5 0',
		        onClick: function(e, target){ //点击了页面
		        	alert(e);
    			}
		    };
		    
	var leftNavigator = new AdminPanel();
    var mainPanel = new MainPanel();
    var statusBar = {
    			id :'smart-statusbar',
		    	region: 'south',
		    	xtype : 'statusbar', 
		    	autoHeight : true,
		    	defaultText: '就绪',
		    	items : [
		    	 {
		    	 	type : 'tbbutton',
		    	 	iconCls : 'icon-max',
                    text: '扩展',
                    //tooltip: '扩展/收缩',
                    handler: function (){
                       //alert(this.text);
                    	//alert(this.iconCls);
                    	if(this.text == "扩展"){
                    		this.setText("收缩");
                    		this.setIconClass("icon-min");
                    		Ext.getCmp('header').hide();
                    		Ext.getCmp('admin-tree').hide();
                    		//Ext.get('header').setVisible(true);
                    		Smart.viewport.doLayout();
                    	} else{
                    		this.setText("扩展");
                    		this.setIconClass("icon-max");
                    		Ext.getCmp('header').show();
                    		Ext.getCmp('admin-tree').show();
                    		//Ext.get('header').setVisible(false);
                    		Smart.viewport.doLayout();
                    	}
                    }
                }
		    	]
		    };

    leftNavigator.on('click', function(node, e){
         if(node.isLeaf()){
            e.stopEvent();
            mainPanel.loadModule(node.id);
         }
    });
    

	Smart.viewport = new Ext.Viewport({
			id : 'viewport',
			layout : 'border',
			defaults : {
				collapsible : false,
				split : true
			},
			items : [header,mainPanel,leftNavigator,statusBar]
		}
	);

	Smart.viewport.doLayout();
}

function logout (){
	
	Ext.Msg.confirm('操作提示', '您确定要退出本系统?', function(btn) {
		if ('yes' == btn) {
			Ext.Ajax.request({
				url : 'logout.action',
				success : function() {
					Ext.getCmp('header').hide();;
					Ext.getCmp('doc-body').closeAllTabs();
					Ext.getCmp('doc-body').hide();
					Ext.getCmp('admin-tree').hide();
					Ext.getCmp('smart-statusbar').hide();
					Smart.loginWin.show();
				
				},
				failure : function() {
					Ext.Msg.show({
						title : '错误提示',
						msg : '退出系统失败!',
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
				}
			});
		}
	});
	
	return false;
}

function AboutUs(){
	
	new Ext.Window({
		closeAction : 'close',
		resizable : false,
		bodyStyle : 'padding: 7',
		modal : true,
		title : '关于' + Smart.config.sysTitle,
		html : Smart.config.aboutUs,
		width : 300,
		height : 200
	}).show();
	
	return false;
}
								
								
Ext.form.Field.prototype.msgTarget = 'qtip';
Ext.QuickTips.init();

Ext.onReady(function(){
	Smart.logined = (sysUser == ''? false : true);
	
	
	
    setTimeout(function(){
    	
    	//Ext.get('header').setVisibilityMode(Ext.Element.DISPLAY);
    	Ext.get('header').hide();
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
        Smart.loginWin.applyToMarkup('login-win');
        
        if(!Smart.logined){
	    	Smart.loginWin.show();
	    	//Ext.fly("login-win").setStyle('z-index','80000');
		} else {
			Smart.loginUser = {
				groupId : groupId,
				groupName : groupName,
				userId: userId,
				userName : userName
			};
			//console.dir(Smart.loginUser);
			createViewPort();
		}
		
		
		Ext.Ajax.on('requestcomplete',function(conn,response,options){
			
			//console.dir(response);
			if(typeof(response.status) == "undefined"){
				Ext.Msg.show({
					   title:'错误!',
					   msg: '请求失败,请重新发送!',
					   buttons: Ext.Msg.OK,
					   icon: Ext.MessageBox.ERROR,
					   fn : function(){
					   }
					});
				return;
			}
			
			var sessionStatus = response.getResponseHeader('sessionstatus');
			if(typeof(sessionStatus) != "undefined" ){
				//alert(sessionStatus);
				if(sessionStatus == 'timeout'){
				 	//Smart.showWarnTipMsg("会话过期,请重新登录");
					Ext.Msg.show({
					   title:'警告!',
					   msg: '会话过期,请重新登录!',
					   buttons: Ext.Msg.OK,
					   icon: Ext.MessageBox.WARNING,
					   fn : function(){
					   		Ext.getCmp('header').hide();;
							Ext.getCmp('doc-body').closeAllTabs();
							Ext.getCmp('doc-body').hide();
							Ext.getCmp('admin-tree').hide();
							Ext.getCmp('smart-statusbar').hide();
							Smart.loginWin.show();
					   }
					});
					
				}
			}
		
		}, this);   /**/
		
    }, 250);
});