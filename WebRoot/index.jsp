<!-- 系统页面 ：-->
<!-- Author：Yu Zhou-->
<!-- Date：2011-03-19 -->

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	java.io.InputStream is = getClass().getResourceAsStream("/config.properties");

	java.util.Properties props = new java.util.Properties();   
	props.load(is);
	
	String sysTitle = props.getProperty("sysTitle");
	
	String sysSimTitle = props.getProperty("sysSimTitle");
	
	
	String sysName = "smartac";
	String sysMsg = props.getProperty("sysMsg") == null? "暂无" : props.getProperty("sysMsg");;
	String aboutUs = props.getProperty("aboutUs") == null? "暂无" : props.getProperty("aboutUs");
%>
<html xmlns="http://www.w3.org/1999/xhtml" class="x-viewport">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=sysTitle%></title>
<meta name="description" content="<%=sysTitle%>" />
<script type="text/javascript">
			Smart = {
				config : {
					sysTitle : "<%=sysTitle%>",
					sysSimTitle : "<%=sysSimTitle%>",
					sysName : "<%=sysName%>",
					sysMsg : "<%=sysMsg%>",
					aboutUs : "<%=aboutUs%>"
				},
				setting : {
					singleAddMode : true,
					autoloadAfterAdd : true,
					popupSucMsgAfterAdd : false
				}
				
			};
/*
			loginUser = {
					groupId : groupId,
					groupName : groupName,
					userId: userId,
					userName : userName
				};
*/

			var sysUser = '<s:property value="#session.user"/>';
			if(sysUser != '') {
				groupId = '<s:property value="#session.user.groupId"/>';
				groupName = '<s:property value="#session.user.groupName"/>';
				userId = '<s:property value="#session.user.userId"/>';
				userName = '<s:property value="#session.user.userName"/>';
			}
			//alert(groupId);
</script>
</head>
<body id='samrt-body'>
<div id="loading-mask"></div>
<div id="loading">
    <div class="loading-indicator">
        <img src="./resources/images/loading32.gif" width="31" height="31" style="margin-right:8px;float:left;vertical-align:top;"  />
        <%=sysTitle%>
		<br />
        <span id="loading-msg">加载样式和图片...</span>
    </div>
</div>
<div id="bd">
<link rel="stylesheet" type="text/css" media="all" href="./resources/css/sys.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/xtheme-gray.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/docs.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/style.css" /> 
<link rel="stylesheet" type="text/css" href="./resources/extjs/ux/statusbar/css/statusbar.css" />
<link rel="stylesheet" type="text/css" href="./resources/extjs/ux/gridfilters/css/GridFilters.css" />
<link rel="stylesheet" type="text/css" href="./resources/extjs/ux/gridfilters/css/RangeMenu.css" />
<link rel="stylesheet" type="text/css" href="./resources/extjs/ux/fileuploadfield/css/fileuploadfield.css" />
<link rel="shortcut icon" href="./resources/favicon.ico" />
<link rel="icon" href="./resources/favicon.ico" />
<style type="text/css"></style>
<!--  -->
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '加载系统核心...';</script>

<!-- 
<script type="text/javascript" src="./resources/extjs/ext-base-debug.js"></script>
<script type="text/javascript" src="./resources/extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/ext-basex-debug.js"></script>
 -->
 
<script type="text/javascript" src="./resources/extjs/ext-base.js"></script>
<script type="text/javascript" src="./resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/ext-basex.js"></script>
 
<script type="text/javascript" src="./resources/extjs/ux/TabCloseMenu.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/RowExpander.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/Exporter-all.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/statusbar/StatusBar.js"> </script>
<script type="text/javascript" src="./resources/extjs/ux/statusbar/ValidationStatus.js"> </script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/menu/RangeMenu.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/menu/ListMenu.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/GridFilters.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/filter/Filter.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/filter/StringFilter.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/filter/DateFilter.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/filter/ListFilter.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/filter/NumericFilter.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/gridfilters/filter/BooleanFilter.js"></script>
<script type="text/javascript" src="./resources/extjs/ux/fileuploadfield/FileUploadField.js"></script>
<script type="text/javascript" src="./resources/extjs/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '系统初始化...';</script>
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript" src="plugin.js"></script>
<script type="text/javascript" src="./resources/tooljs/msg.js"></script>
<script type="text/javascript" src="login.js"></script>
<script type="text/javascript" src="view.js"></script>

<!-- PRIV_USER Module 
<script type="text/javascript" src="group.js"></script>
<script type="text/javascript" src="user.js"></script>
-->

<!-- PRIV_QUERY Module 
<script type="text/javascript" src="accessdetail.js"></script>
<script type="text/javascript" src="accessview.js"></script>
<script type="text/javascript" src="accessanalysis.js"></script>
<script type="text/javascript" src="alarm.js"></script>
<script type="text/javascript" src="timer.js"></script>
-->

<!-- PRIV_SETTING Module 
<script type="text/javascript" src="gate.js"></script>
<script type="text/javascript" src="gatemanage.js"></script>
<script type="text/javascript" src="watcher.js"></script>
<script type="text/javascript" src="block.js"></script>
-->

</div>

<div id="header">
    <img style="margin-left: 5px" src="./resources/images/title-ext.png" alt="<%=sysTitle%>" />
   	<a id="aboutus" onClick="AboutUs()" style="padding:5px">关于<b><%=sysSimTitle%></b></a> | 
    <a id="logout" onClick="logout()" style="padding:5px">注销<b><s:property value="#session.user.userName"/></b></a>
</div>

<div id="login-win" class="x-hidden">
</div>
</body>
</html>