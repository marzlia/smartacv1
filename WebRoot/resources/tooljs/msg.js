// 本文件定义一些弹出确认消息对话框的公用函数
Smart.debugEnabled = false;
Smart.debug = function (msg){
	if(Smart.debugEnabled){
		//Ext.Msg.alert('Debug', msg);
		alert(msg);
	}
}

// showSucTipMsg : 提示操作成功
Smart.showSucTipMsg = function (msg){
	//Ext.Msg.alert('恭喜!', str);
	Ext.Msg.show({
	   title:'恭喜!',
	   msg: msg,
	   buttons: Ext.Msg.OK,
	   icon: Ext.MessageBox.INFO
	});
}

Smart.showWarnTipMsg = function (msg){
	Ext.Msg.show({
	   title:'警告!',
	   msg: msg,
	   buttons: Ext.Msg.OK,
	   icon: Ext.MessageBox.WARNING
	});
}

// showErrTipMsg ：提示操作错误
Smart.showErrTipMsg = function (msg){
	//Ext.Msg.alert('出错!', str);
	Ext.Msg.show({
	   title:'出错!',
	   msg: msg,
	   buttons: Ext.Msg.OK,
	   icon: Ext.MessageBox.ERROR
	});
}

Smart.Num2Bool = function(v){
	if(v == 0){
		return "否";
	} else {
		return "是";
	}
}


