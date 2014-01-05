/**
 * 
 */
package com.smart.help;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jdom.CDATA;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.smart.action.UserAction;
import com.smart.help.DB;
import com.smart.help.Meta;

/**
 * @author Yu Zhou
 *
 */
public class GenFile {
	
	public static String classPath = "E:\\smartacv1\\smartacv1\\src\\com\\smart\\";
	public static String modCname = "";
	public static String modEname = "";
	public static String table = "";
	public static String view = "";

	// 获取元数据
	public static List getAllMeta(String object){
		List metaList = new ArrayList();
		DB db = new DB();
		ResultSet rs = db.ExcuteQuery("select * from COLUMNS where TABLE_SCHEMA='smartac' and TABLE_NAME like '"+ object +"'");
		
		try
		{
			if(!rs.isLast()){
				rs.first();
				int	i = 0;
				do
				{
					Meta meta = new Meta();
					meta.setEName(rs.getString("COLUMN_NAME"));
					meta.setDataType(rs.getString("DATA_TYPE"));
					meta.setCName(rs.getString("COLUMN_COMMENT"));
					
					meta.setIsNull(rs.getString("IS_NULLABLE").equalsIgnoreCase("yes") ? "true" : "false");
					meta.setMaxLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
					
					metaList.add(meta);
					
				}while(rs.next());
			}
			rs.close();
		}
		catch (Exception e)
		{
			System.out.println("error in  initialDataHeader"+e.getMessage());
		}
		
		db.Close();
		
		return metaList;
	}
	
	public static void genBean() {
		// TODO Auto-generated method stub
		try { 
			  System.out.println("create the bean file ");
			  FileWriter fw = new FileWriter(classPath + "po\\" + modEname + ".java");
			  PrintWriter out = new PrintWriter(fw);    
			  out.println("package com.smart.po;\n");   
			  out.println("import java.io.Serializable;\n");
			  out.println("import java.util.Date;\n");
			  
			  out.println("@SuppressWarnings(\"serial\")");
			  out.println("public class " + modEname +" implements Serializable {");
			  
			  out.println("\tpublic "+ modEname +"() {}");
			  out.println("");
			  
			  //
			  List showList = getAllMeta(view);
				
			for(int i=0; i<showList.size(); i++) {
				Meta meta = (Meta)showList.get(i);
				
				if(meta.getDataType().equals("varchar") || meta.getDataType().equals("char")  ){
					out.println("\tprivate String " + meta.getEName() + "; // " + meta.getCName());
				}
				else if(meta.getDataType().equals("int")){
					out.println("\tprivate Integer " + meta.getEName() + "; // " + meta.getCName());
				}
				else if(meta.getDataType().equals("bigint")){
					out.println("\tprivate Long " + meta.getEName() + "; // " + meta.getCName());
				}
				else if(meta.getDataType().equals("decimal")){
					out.println("\tprivate Integer " + meta.getEName() + "; // " + meta.getCName());
				}
				else if(meta.getDataType().equals("double")){
					out.println("\tprivate Double " + meta.getEName() + "; // " + meta.getCName());
				}	
				else if(meta.getDataType().equals("bit")){
					out.println("\tprivate boolean " + meta.getEName() + "; // " + meta.getCName()); 
				}
				else if(meta.getDataType().equals("date")){
					out.println("\tprivate Date " + meta.getEName() + "; // " + meta.getCName()); 
				} 
				else if(meta.getDataType().equals("datetime")){
					out.println("\tprivate Date " + meta.getEName() + "; // " + meta.getCName()); 
				} 
			}
			  out.println("}");
			  out.close();
			  fw.close();
			  
		  } catch (IOException e) { 
			  System.out.println("Uh oh, got an IOException error!");
			  e.printStackTrace();
		  }
	}
	
	public static void genIService() {
		
		try { 
			 System.out.println("create the service interface ");
			  FileWriter fw = new FileWriter(classPath + "service\\I" + modEname + "Service.java");
			  PrintWriter out = new PrintWriter(fw); 
			  // ����
			  out.println("//"+ modCname +"  by zhouyu"); 
			  out.println("package com.smart.service;\n");   
			  //�����
			  out.println("import com.smart.core.Page;");
			  out.println("import com.smart.po."+ modEname +";\n");
			  //����
			  out.println("public interface I" + modEname +"Service {");
			  
			  out.println("//"+ modCname +"----add an object"); 
			  out.println("\tObject save"+ modEname +"("+ modEname + " " + modEname.toLowerCase() +");\n");
			  
			  out.println("//"+ modCname +"----find a page of objects"); 
			  out.println("\tPage findByPage(Page page);\n");
			  
			  out.println("//"+ modCname +"----update an object"); 
			  out.println("\tboolean update"+ modEname +"("+ modEname +" "+ modEname.toLowerCase() +") throws Exception;\n");
			  
			  out.println("//"+ modCname +"----delete an object by id"); 
			  out.println("\tboolean delete"+ modEname +"(Integer "+ modEname.toLowerCase() +"Id);\n");
			  
			  out.println("}");
			  out.close();
			  fw.close();
			  
		} catch (IOException e) { 
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
	}
	
	// 生成服务实现类文件
	public static void genService() {
		
		try { 
			  System.out.println("create the service implement file ");
			  FileWriter fw = new FileWriter(classPath + "service\\impl\\" + modEname + "Service.java");
			  PrintWriter out = new PrintWriter(fw); 
			  // ����
			  out.println("//"+ modCname +"  by zhouyu"); 
			  out.println("package com.smart.service.impl;\n");   
			  //�����
			  out.println("import com.smart.core.Page;");
			  out.println("import com.smart.po."+ modEname +";");
			  out.println("import com.smart.service.I"+ modEname +"Service;");
			  out.println("import com.smart.dao.I"+ modEname +"Dao;\n");
			  
			  //����
			  out.println("public class " + modEname +"Service implements I" + modEname +"Service {");
			  
			  //
			  out.println("\tprivate I"+ modEname +"Dao "+ modEname.toLowerCase() +"Dao;\n");
			  out.println("\tpublic void set"+ modEname +"Dao(I"+ modEname +"Dao "+ modEname.toLowerCase() +"Dao) {");
			  out.println("\t\tthis."+ modEname.toLowerCase() +"Dao = "+ modEname.toLowerCase() +"Dao;");
			  out.println("\t}\n");
			  
			  out.println("//"+ modCname +"----add"); 
			  out.println("\tpublic Object save"+ modEname +"("+ modEname + " "+ modEname.toLowerCase() +") {\n");
			  out.println("\t\treturn "+ modEname.toLowerCase() +"Dao.save"+ modEname +"("+ modEname.toLowerCase() +");");
			  out.println("\t}\n");
			  
			  out.println("//"+ modCname +"----find"); 
			  out.println("\tpublic Page findByPage(Page page) {\n");
			  out.println("\t\tpage.setRoot("+ modEname.toLowerCase() +"Dao.findByPage(page));");
			  out.println("\t\tpage.setTotalProperty("+ modEname.toLowerCase() +"Dao.findByCount(page));");
			  out.println("\t\treturn page;");
			  out.println("\t}\n");
			  
			  out.println("//"+ modCname +"----update"); 
			  out.println("\tpublic boolean update"+ modEname +"("+ modEname +" "+ modEname.toLowerCase() +") throws Exception {\n");
			  out.println("\t\tInteger flag = "+ modEname.toLowerCase() +"Dao.update("+ modEname.toLowerCase() +");");
			  out.println("\t\tif (flag != null) { return true; }");
			  out.println("\t\treturn false;");
			  out.println("\t}\n");
			  
			  out.println("//"+ modCname +"----delete"); 
			  out.println("\tpublic boolean delete"+ modEname +"(Integer "+ modEname.toLowerCase() +"Id) {\n");
			  out.println("\t\tInteger flag = "+ modEname.toLowerCase() +"Dao.deleteById("+ modEname.toLowerCase() +"Id);");
			  out.println("\t\tif (flag != null) { return true; }");
			  out.println("\t\treturn false;");
			  out.println("\t}\n");
			  
			  out.println("}");
			  out.close();
			  fw.close();
			  
		} catch (IOException e) { 
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
	}
	
	// 生成数据访问对象接口文件
	public static void genIDao() {
		
		try { 
			  System.out.println("create the dao interface ");
			  FileWriter fw = new FileWriter(classPath + "dao\\I" + modEname + "Dao.java");
			  PrintWriter out = new PrintWriter(fw); 
			  out.println("//"+ modCname +"  by zhouyu"); 
			  out.println("package com.smart.dao;\n");   
			  out.println("import java.util.List;\n");
			  out.println("import com.smart.core.Page;");
			  out.println("import com.smart.po."+ modEname +";\n");
			  out.println("public interface I" + modEname +"Dao {");
			  
			  out.println("\tpublic Object save"+ modEname +"("+ modEname + " " +modEname.toLowerCase()+");\n");
			  out.println("\tpublic List findByPage(Page page);\n");
			  out.println("\tpublic int findByCount(Page page);\n");
			  out.println("\tpublic Integer update("+ modEname +" "+ modEname.toLowerCase() +") throws Exception;\n");
			  out.println("\tpublic Integer deleteById(Integer "+ modEname.toLowerCase() +"Id);\n");
			  
			  out.println("}");
			  out.close();
			  fw.close();
		} catch (IOException e) { 
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
	}
	
	// 生成数据访问对象实现类文件
	public static void genDao() {
		
		try { 
			 System.out.println("create the dao class ");
			  FileWriter fwDao = new FileWriter(classPath + "dao\\impl\\" + modEname + "Dao.java");
			  PrintWriter outDao = new PrintWriter(fwDao); 
			  outDao.println("//"+ modCname +"  by zhouyu"); 
			  outDao.println("package com.smart.dao.impl;\n");   
			  outDao.println("import java.util.List;\n");
			  outDao.println("import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;\n");
			  outDao.println("import com.smart.core.Page;");
			  outDao.println("import com.smart.po."+ modEname +";");
			  outDao.println("import com.smart.dao.I"+ modEname +"Dao;\n");
			  
			  outDao.println("public class " + modEname +"Dao extends SqlMapClientDaoSupport implements I"+ modEname +"Dao {\n");
			  
			  outDao.println("//"+ modCname +"----删除对象"); 
			  outDao.println("\tpublic Integer deleteById(Integer "+ modEname.toLowerCase() +"Id) {\n");
			  outDao.println("\t\treturn getSqlMapClientTemplate().delete(\""+ modEname +".deleteById\", "+ modEname.toLowerCase() +"Id);");
			  outDao.println("\t}\n");
			  
			  outDao.println("//"+ modCname +"----获得对象总数"); 
			  outDao.println("\tpublic int findByCount(Page page) {\n");
			  outDao.println("\t\treturn (Integer) getSqlMapClientTemplate().queryForObject(\""+ modEname +".findByCount\", page);");
			  outDao.println("\t}\n");
			  
			  outDao.println("//"+ modCname +"----获取一页对象"); 
			  outDao.println("\tpublic List findByPage(Page page) {\n");
			  outDao.println("\t\treturn getSqlMapClientTemplate().queryForList(\""+ modEname +".findByPage\", page);");
			  outDao.println("\t}\n");
			  
			  outDao.println("//"+ modCname +"----添加一个对象"); 
			  outDao.println("\tpublic Object save"+ modEname +"("+ modEname + " " +modEname.toLowerCase()+") {\n");
			  outDao.println("\t\treturn getSqlMapClientTemplate().insert(\""+ modEname +".save\", "+ modEname.toLowerCase() +");");
			  outDao.println("\t}\n");
				  
			  outDao.println("//"+ modCname +"----更新一个对象");   
			  outDao.println("\tpublic Integer update("+ modEname +" "+ modEname.toLowerCase() +") throws Exception {\n");
			  outDao.println("\t\treturn getSqlMapClientTemplate().update(\""+ modEname +".update\", "+ modEname.toLowerCase() +");");
			  outDao.println("\t}\n");
			  
			  outDao.println("}");
			  outDao.close();
			  fwDao.close();
		} catch (IOException e) { 
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
	}
	
	// action
	public static void genAction() {
		
		try { 
			  System.out.println("create an action file");
			  
			  FileWriter fw = new FileWriter(classPath + "action\\" + modEname + "Action.java");
			  PrintWriter out = new PrintWriter(fw); 
			  
			  out.println("//"+ modCname +"  by zhouyu"); 
			  out.println("package com.smart.action;\n");   
			  //�����
			  out.println("import java.util.ArrayList;");
			  out.println("import java.util.List;");
			  
			  out.println("import org.apache.log4j.Logger;");
			  out.println("import com.smart.core.BaseAction;");
			  out.println("import com.smart.core.MyUtils;");
			  out.println("import com.smart.core.Page;");
			  out.println("import com.smart.po."+ modEname +";");
			  out.println("import com.smart.service.I"+ modEname +"Service;\n");
			  
			  out.println("@SuppressWarnings(\"serial\")");			  
			  //
			  out.println("public class " + modEname +"Action extends BaseAction {");
			  
			  //
			  out.println("\tstatic Logger logger = Logger.getLogger("+ modEname +"Action.class);");
			  
			  out.println("\n\tprivate Integer "+ modEname.toLowerCase() +"Id;");
			  out.println("\tprivate I"+ modEname +"Service "+ modEname.toLowerCase() +"Service;");
			  out.println("\tprivate boolean success;");
			  out.println("\tprivate "+ modEname +" "+ modEname.toLowerCase() +";");
			  out.println("\tprivate Page page;\n");
			  
			  out.println("//"+ modCname +"----add"); 
			  out.println("\tpublic String save"+ modEname +"() {\n");
			  out.println("\t\t"+ modEname.toLowerCase() +"Id = (Integer) "+ modEname.toLowerCase() +"Service.save"+ modEname +"("+ modEname.toLowerCase() +");");
			  out.println("\t\tif ("+ modEname.toLowerCase() +"Id != null) { success = true;}");
			  out.println("\t\treturn SUCCESS;");
			  out.println("\t}\n");
			  
			  out.println("//"+ modCname +"----find"); 
			  out.println("\tpublic String findAll"+ modEname +"() {\n");
			  out.println("\t\tString strCondition = getRequest().getParameter(\"conditions\") == null? \"\" :getRequest().getParameter(\"conditions\");");
			  out.println("\t\tList conditions = new ArrayList();");
			  out.println("\t\tMyUtils.addToCollection(conditions, MyUtils.split(strCondition, \" ,\"));");
			  out.println("\t\tpage = new Page();");
			  out.println("\t\tpage.setConditions(conditions);");
			  out.println("\t\tint start = getRequest().getParameter(\"start\") == null? 0 : Integer.valueOf(getRequest().getParameter(\"start\"));");
			  out.println("\t\tint limit = getRequest().getParameter(\"limit\") == null? 0 :Integer.valueOf(getRequest().getParameter(\"limit\"));");
			  out.println("\t\tpage.setStart(++start);");
			  out.println("\t\tpage.setLimit(limit = limit == 0 ? 20 : limit);");
			  out.println("\t\tpage = "+ modEname.toLowerCase() +"Service.findByPage(page);");
			  out.println("\t\treturn SUCCESS;");
			  out.println("\t}\n");
			  
			  out.println("//"+ modCname +"----update"); 
			  out.println("\tpublic String update"+ modEname +"() throws Exception {\n");
			  out.println("\t\t"+ modEname +" "+ modEname.toLowerCase() +" = new "+ modEname +"();");
			  out.println("\t\t"+ modEname.toLowerCase() +".set"+ modEname +"Id("+ modEname.toLowerCase() +"Id);");
			  out.println("\t\t// \n");
			  out.println("\t\tsuccess = "+ modEname.toLowerCase() +"Service.update"+ modEname +"("+ modEname.toLowerCase() +");");
			  out.println("\t\treturn SUCCESS;");
			  out.println("\t}\n");	
			  
			  out.println("//"+ modCname +"----delete"); 
			  out.println("\t public String delete"+ modEname +"() {\n");
			  out.println("\t\tString strId = getRequest().getParameter(\""+ modEname.toLowerCase() +"Id\");");
			  out.println("\t\tif (strId != null && !\"\".equals(strId)) {");
			  out.println("\t\t\tsuccess = "+ modEname.toLowerCase() +"Service.delete"+ modEname +"(Integer.valueOf(strId));");
			  out.println("\t\t}");
			  out.println("\t\treturn SUCCESS;");
			  out.println("\t}\n");
			  
			  
			  out.println("}");
			  out.close();
			  fw.close();
			  
			  
		} catch (IOException e) { 
			System.out.println("Uh oh, got an IOException error!");
			e.printStackTrace();
		}
	}
	
	public static void genPoXml(){
		
		List metalist = getAllMeta(table);
		List showList = getAllMeta(view);
		if(metalist.size() == 0 || showList.size() == 0){ 
			System.out.println("Get Data Failed, please debug the issue!");
			return; 
		}
		
		try {
			
		// ����һ��XML�ĵ�
		Document poXML = new Document();
		
		// ������ĵ����ĵ�����
		DocType docType = new DocType("sqlMap","-//ibatis.apache.org//DTD SQL Map 2.0//EN",
				"http://ibatis.apache.org/dtd/sql-map-2.dtd");
		poXML.setDocType(docType);
		
		// ������ĵ��ĸ�Ԫ��
		Element rootElement = new Element("sqlMap");
		rootElement.setAttribute("namespace", modEname);
		poXML.setRootElement(rootElement);
		
		// ����һ����Ԫ��update
		Element update = new Element("update");
		update.setAttribute("id","update");
		update.setAttribute("parameterClass",modEname.toLowerCase());
		// ��updateԪ���м���Cdata
		CDATA updateCdata = new CDATA("UPDATE " + table );
		update.addContent("\n\t\t");
		update.addContent(updateCdata);
		update.addContent("\n\t\t");
		// ��updateԪ���м��������Ԫ��dynamic
		Element updateDynamic = new Element("dynamic");
		updateDynamic.setAttribute("prepend","SET");
		update.addContent(updateDynamic);
		update.addContent("\n\t\t");
		
		// ���һ����Ԫ�� typeAlias
		Element typeAlias1 = new Element("typeAlias");
		typeAlias1.setAttribute("alias",modEname.toLowerCase());
		typeAlias1.setAttribute("type","com.smart.po." + modEname);
		rootElement.addContent("\n\t");
		rootElement.addContent(typeAlias1);
		// ���һ����Ԫ�� typeAlias
		Element typeAlias2 = new Element("typeAlias");
		typeAlias2.setAttribute("alias","page");
		typeAlias2.setAttribute("type","com.smart.core.Page");
		rootElement.addContent("\n\t");
		rootElement.addContent(typeAlias2);
		// ���һ����Ԫ�� parameterMap
		Element parameterMap = new Element("parameterMap");
		parameterMap.setAttribute("class", modEname.toLowerCase());
		parameterMap.setAttribute("id","pm_"+ modEname.toLowerCase() +"_without_id");
		rootElement.addContent("\n\n\t");
		rootElement.addContent(parameterMap);
		
		//
		//
		String colList = "";	// 
		String valList = "";	// 
		for(int i=1; i<metalist.size(); i++) {
			Meta meta = (Meta)metalist.get(i);
			
			Element parameter = new Element("parameter");
			parameter.setAttribute("property", meta.getEName());
			
			Element isNotNull = new Element("isNotNull");
			isNotNull.setAttribute("property", meta.getEName());
			isNotNull.setAttribute("prepend", ",");
			String cdataString = "";
			if(meta.getDataType().equals("varchar") || meta.getDataType().equals("char")){
				parameter.setAttribute("javaType","string");
				parameter.setAttribute("jdbcType","VARCHAR");
				cdataString = "\n\t\t\t\t\t"+ meta.getEName() 
				+" = #"+ meta.getEName() +":VARCHAR#\n\t\t\t\t";
			}
			else if(meta.getDataType().equals("int")){
				parameter.setAttribute("javaType","integer");
				parameter.setAttribute("jdbcType","NUMBER");
				cdataString = "\n\t\t\t\t\t"+ meta.getEName() 
				+" = #"+ meta.getEName() +":NUMBER#\n\t\t\t\t";
			}
			else if(meta.getDataType().equals("double")){
				parameter.setAttribute("javaType","Double");
				parameter.setAttribute("jdbcType","NUMBER");
				cdataString = "\n\t\t\t\t\t"+ meta.getEName() 
				+" = #"+ meta.getEName() +":NUMBER#\n\t\t\t\t";
			}
			else if(meta.getDataType().equals("bit")){
				parameter.setAttribute("javaType","boolean");
				parameter.setAttribute("jdbcType","BIT");
				cdataString = "\n\t\t\t\t\t"+ meta.getEName() 
				+" = #"+ meta.getEName() +":BIT#\n\t\t\t\t";
			}
			else if(meta.getDataType().equals("date")){
				parameter.setAttribute("javaType","java.util.Date");
				parameter.setAttribute("jdbcType","DATE");
				cdataString = "\n\t\t\t\t\t"+ meta.getEName() 
				+" = #"+ meta.getEName() +":DATE#\n\t\t\t\t";
			} else if (meta.getDataType().equalsIgnoreCase("datetime")){
				parameter.setAttribute("javaType","java.util.Date");
				parameter.setAttribute("jdbcType","DATETIME");
				cdataString = "\n\t\t\t\t\t"+ meta.getEName() 
				+" = #"+ meta.getEName() +":DATETIME#\n\t\t\t\t";
			}
			
			parameterMap.addContent("\n\t\t");
			parameterMap.addContent(parameter);
			
			CDATA isnCdata = new CDATA(cdataString);
			isNotNull.addContent("\n\t\t\t\t");
			isNotNull.addContent(isnCdata);
			isNotNull.addContent("\n\t\t\t");
			
			updateDynamic.addContent("\n\t\t\t");
			updateDynamic.addContent(isNotNull);
			
			//��ӵ�colList
			if(colList.isEmpty()) {
				colList = meta.getEName();
				valList = "?";
			}
			else {
				colList += "," + meta.getEName();
				valList += ",?";
			}
			
		}
		parameterMap.addContent("\n\t");
		updateDynamic.addContent("\n\t\t");
		
		// ���һ����Ԫ�� sql
		Element sql = new Element("sql");
		sql.setAttribute("id","by" + modEname + "IdCondition");
		rootElement.addContent("\n\t");
		rootElement.addContent(sql);
		CDATA sqlCdata = new CDATA( modEname.toLowerCase() + "Id = #"+ modEname.toLowerCase() +"Id:NUMBER#");
		sql.addContent(sqlCdata);
		rootElement.addContent("\n");
		
		// ���һ����Ԫ�� insert
		Element insert = new Element("insert");
		insert.setAttribute("id","save");
		insert.setAttribute("parameterMap","pm_"+ modEname.toLowerCase() +"_without_id");
		rootElement.addContent("\n\t");
		rootElement.addContent(insert);
		rootElement.addContent("\n");
		
		// ��Ӷ�����Ԫ��selectKey
		Element selectKey = new Element("selectKey");
		selectKey.setAttribute("resultClass","int");
		// ��selectKeyԪ���м���Cdata
		CDATA skCdata = new CDATA("\n\t\t\t\tSELECT @@IDENTITY AS ID\n\t\t\t");
		selectKey.addContent("\n\t\t\t");
		selectKey.addContent(skCdata);
		selectKey.addContent("\n\t\t");
		
		// ��insertԪ���м���Cdata
		String insertSql = "\n\t\t\tINSERT INTO "+ table +" \n\t\t\t\t\t"
							+ "("+ colList +")\n\t\t\t\t"
							+ "VALUES ("+ valList +")\n\t\t";
		CDATA insertCdata = new CDATA(insertSql);
		insert.addContent("\n\t\t");
		insert.addContent(insertCdata);
		insert.addContent("\n\t\t");
		// ��nsertԪ���м���selectKey
		insert.addContent(selectKey);
		insert.addContent("\n\t");
		
		// ���һ����Ԫ�� delete
		Element delete = new Element("delete");
		delete.setAttribute("id","deleteById");
		delete.setAttribute("parameterClass","integer");
		rootElement.addContent("\n\t");
		rootElement.addContent(delete);
		rootElement.addContent("\n");
		
		// ��deleteԪ���м���Cdata
		CDATA delCdata = new CDATA("\n\t\t\tdelete from " + table + "\n\t\t");
		delete.addContent("\n\t\t");
		delete.addContent(delCdata);
		delete.addContent("\n\t\t");
		
		// ��Ӷ�����Ԫ��dynamic
		Element dynamic = new Element("dynamic");
		dynamic.setAttribute("prepend","where");
		delete.addContent(dynamic);
		delete.addContent("\n\t");
		
		// �������Ԫ��include
		Element include = new Element("include");
		include.setAttribute("refid","by"+ modEname +"IdCondition");
		dynamic.addContent("\n\t\t\t");
		dynamic.addContent(include);
		dynamic.addContent("\n\t\t");
		
		// ���һ������Ԫ��update
		update.addContent((Element)dynamic.clone());
		update.addContent("\n\t");
		rootElement.addContent("\n\t");
		rootElement.addContent(update);
		rootElement.addContent("\n");
		
		// ���һ����Ԫ��sql : ��ҳ��ѯ���
		Element sqlCondition = new Element("sql");
		sqlCondition.setAttribute("id","find" + modEname + "ByPageCondition");
		
		// ��Ӷ�����Ԫ��
		Element isNotEmpty = new Element("isNotEmpty");
		isNotEmpty.setAttribute("property","conditions");
		sqlCondition.addContent("\n\t\t");
		sqlCondition.addContent(isNotEmpty);
		sqlCondition.addContent("\n\t");
		
		// �������Ԫ��
		Element iterate = new Element("iterate");
		iterate.setAttribute("property","conditions");
		iterate.setAttribute("open","(");
		iterate.setAttribute("close",")");
		iterate.setAttribute("conjunction","OR");
		isNotEmpty.addContent("\n\t\t\t");
		isNotEmpty.addContent(iterate);
		isNotEmpty.addContent("\n\t\t");
		
		// iterate���Cdata
		String condition = "";
		for(int i=1; i<showList.size(); i++) {
			Meta meta = (Meta)showList.get(i);
			
			if(meta.getDataType().equals("varchar") || meta.getDataType().equals("char")){
				if(!condition.isEmpty()) {
					condition += "\n\t\t\t\t\tOR ";
				}
				condition += "upper("+ meta.getEName() +") LIKE concat('%',upper(#conditions[]:VARCHAR#),'%')";
			}
			else if(meta.getDataType().equals("int")){
				
			}
			else if(meta.getDataType().equals("double")){
				
			}			
		}
		
		CDATA conditionCdata = new CDATA("\n\t\t\t\t\t" + condition + "\n\t\t\t\t");
		iterate.addContent("\n\t\t\t\t");
		iterate.addContent(conditionCdata);
		iterate.addContent("\n\t\t\t");
		
		rootElement.addContent("\n\t");
		rootElement.addContent(sqlCondition);
		rootElement.addContent("\n");
		
		// ���һ����Ԫ��select ��ҳ��ѯ
		Element findByPage = new Element("select");
		findByPage.setAttribute("id","findByPage");
		findByPage.setAttribute("parameterClass","page");
		findByPage.setAttribute("resultClass",modEname.toLowerCase());
		// ����
		CDATA queryCdata = new CDATA("\n\t\t\tSELECT  * FROM "+ view +" "
					+"\n\t\t\t\tWHERE ("+ modEname.toLowerCase() +"Id >= (SELECT MAX("+ modEname.toLowerCase() +"Id) FROM (SELECT "+ modEname.toLowerCase() +"Id FROM "+ view +"\n\t\t");
		findByPage.addContent("\n\t\t");
		findByPage.addContent(queryCdata);
		findByPage.addContent("\n\t\t");
		
		// ���� ��ѯ���
		Element dynamicSelect = new Element("dynamic");
		dynamicSelect.setAttribute("prepend","WHERE");
		findByPage.addContent(dynamicSelect);
		findByPage.addContent("\n\t\t");
		
		// �� ��ѯ���
		Element includeSelect = new Element("include");
		includeSelect.setAttribute("refid","find"+ modEname +"ByPageCondition");
		dynamicSelect.addContent("\n\t\t\t");
		dynamicSelect.addContent(includeSelect);
		dynamicSelect.addContent("\n\t\t");
		
		CDATA orderCdata = new CDATA("\n\t\t\tORDER BY "+ modEname.toLowerCase() +"Id LIMIT $start$ ) AS T))\n\t\t");
		findByPage.addContent(orderCdata);
		findByPage.addContent("\n\t\t");
		
		// ���� ��ѯ���
		Element dynamicSelectAnd = new Element("dynamic");
		dynamicSelectAnd.setAttribute("prepend","AND");
		findByPage.addContent(dynamicSelectAnd);
		findByPage.addContent("\n\t\t");
		
		dynamicSelectAnd.addContent("\n\t\t\t");
		dynamicSelectAnd.addContent((Element)includeSelect.clone());
		dynamicSelectAnd.addContent("\n\t\t");
		
		CDATA orderLimitCdata = new CDATA("\n\t\t\tORDER BY "+ modEname.toLowerCase() +"Id LIMIT $limit$\n\t\t");
		findByPage.addContent(orderLimitCdata);
		findByPage.addContent("\n\t");
	
		rootElement.addContent("\n\t");
		rootElement.addContent(findByPage);
		rootElement.addContent("\n");
		
		// ���һ����Ԫ��select ��ѯ��¼����
		Element findByCount = new Element("select");
		findByCount.setAttribute("id","findByCount");
		findByCount.setAttribute("parameterClass","page");
		findByCount.setAttribute("resultClass","int");
		
		// ����
		CDATA queryCdataForTotal = new CDATA("\n\t\t\tSELECT COUNT(*) FROM "+ view +"\n\t\t");
		findByCount.addContent("\n\t\t");
		findByCount.addContent(queryCdataForTotal);
		findByCount.addContent("\n\t\t");
		
		// ���� ��ѯ���
		findByCount.addContent((Element)dynamicSelect.clone());
		findByCount.addContent("\n\t");
		
		rootElement.addContent("\n\t");
		rootElement.addContent(findByCount);
		rootElement.addContent("\n");
		
		
		//�����ĵ�
		XMLOutputter out = new XMLOutputter();
		//out.output(poXML, System.out);
		out.output(poXML, new FileOutputStream(classPath + "po\\xml\\"+ modEname +".xml"));
		
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void updateDaoXML(){
		try {
			SAXBuilder sb = new SAXBuilder(); 
			
			Document doc = sb.build(new FileInputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml")); 
			
			Element root = doc.getRootElement();
		
			// ������Ԫ��property
			Element property = new Element("property");
			property.setAttribute("name", "sqlMapClient");
			property.setAttribute("ref", "sqlMapClient");
			
			// ������Ԫ��bean
			Element bean = new Element("bean");
			bean.setAttribute("id", modEname.toLowerCase() + "Dao");
			bean.setAttribute("class", "com.smart.dao.impl."+ modEname +"Dao");
			
			// ��property���Ϊbean����Ԫ��
			bean.addContent("\n\t\t");
			bean.addContent(property);
			bean.addContent("\n\t");
			
			root.addContent("\t");
			Element e = root.addContent(bean);
			root.addContent("\n");
			
			System.out.println(e.toString());
			
			XMLOutputter outp = new XMLOutputter(); 
			outp.output(doc, new FileOutputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateServiceXML(){
		try {
			SAXBuilder sb = new SAXBuilder(); 
			
			Document doc = sb.build(new FileInputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml")); 
			
			Element root = doc.getRootElement();
		
			// ������Ԫ��property
			Element property = new Element("property");
			property.setAttribute("name", modEname.toLowerCase() + "Dao");
			property.setAttribute("ref", modEname.toLowerCase() + "Dao");
			
			// ������Ԫ��bean
			Element bean = new Element("bean");
			bean.setAttribute("id", modEname.toLowerCase() + "Service");
			bean.setAttribute("class", "com.smart.service.impl."+ modEname +"Service");
			
			// ��property���Ϊbean����Ԫ��
			bean.addContent("\n\t\t");
			bean.addContent(property);
			bean.addContent("\n\t");
			
			root.addContent("\t");
			Element e = root.addContent(bean);
			root.addContent("\n");
			
			System.out.println(e.toString());
			
			XMLOutputter outp = new XMLOutputter(); 
			outp.output(doc, new FileOutputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSqlMap(){
		try {
			SAXBuilder sb = new SAXBuilder(); 
			
			//Document doc = sb.build(new FileInputStream("C:\\smart\\smart\\src\\SqlMapConfig.xml")); 
			Document doc = sb.build(new FileInputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml")); 
			Element root = doc.getRootElement();
		
			//<sqlMap resource="com/smart/po/xml/Factype.xml" />
			
			// ������Ԫ��sqlMap
			Element sqlMap = new Element("sqlMap");
			sqlMap.setAttribute("resource", "com/smart/po/xml/"+ modEname +".xml");
			
			root.addContent("\n\t");
			Element e = root.addContent(sqlMap);
			root.addContent("\n");
			
			System.out.println(e.toString());
			
			XMLOutputter outp = new XMLOutputter(); 
			outp.output(doc, new FileOutputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateActionXML(){
		try {
			SAXBuilder sb = new SAXBuilder(); 
			
			Document doc = sb.build(new FileInputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml")); 
			
			Element root = doc.getRootElement();
			
			// ������Ԫ��property
			Element property = new Element("property");
			property.setAttribute("name", modEname.toLowerCase() + "Service");
			property.setAttribute("ref", modEname.toLowerCase() + "Service");
			
			// ������Ԫ��bean
			Element bean = new Element("bean");
			bean.setAttribute("name", modEname.toLowerCase() + "Action");
			bean.setAttribute("class", "com.smart.action."+ modEname +"Action");
			bean.setAttribute("scope", "prototype");
			
			// ��property���Ϊbean����Ԫ��
			bean.addContent("\n\t\t");
			bean.addContent(property);
			bean.addContent("\n\t");
			
			root.addContent("\t");
			Element e = root.addContent(bean);
			root.addContent("\n");
			
			System.out.println(e.toString());
			
			XMLOutputter outp = new XMLOutputter(); 
			outp.output(doc, new FileOutputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateStructs(){
		try {
			
			
			
			
			
			SAXBuilder sb = new SAXBuilder(); 
			
			Document doc = sb.build(new FileInputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml")); 
			Element root = doc.getRootElement();
			
			// Save
			Element saveparam = new Element("param");
			saveparam.setAttribute("name", "includeProperties");
			saveparam.addContent("success,"+ modEname.toLowerCase() +"Id");
			
			Element saveresult = new Element("result");
			saveresult.setAttribute("type", "json");
			
			saveresult.addContent("\n\t\t\t");
			saveresult.addContent(saveparam);
			saveresult.addContent("\n\t\t");
			
			Element saveaction = new Element("action");
			saveaction.setAttribute("name", "save" + modEname);
			saveaction.setAttribute("class", modEname.toLowerCase() +"Action");
			saveaction.setAttribute("method", "save" + modEname);
			
			saveaction.addContent("\n\t\t");
			saveaction.addContent(saveresult);
			saveaction.addContent("\n\t");
			
			// delete
			
			Element deleteparam = new Element("param");
			deleteparam.setAttribute("name", "includeProperties");
			deleteparam.addContent("success");
			
			Element deleteresult = new Element("result");
			deleteresult.setAttribute("type", "json");
			
			deleteresult.addContent("\n\t\t\t");
			deleteresult.addContent(deleteparam);
			deleteresult.addContent("\n\t\t");
			
			Element deleteaction = new Element("action");
			deleteaction.setAttribute("name", "delete" + modEname);
			deleteaction.setAttribute("class", modEname.toLowerCase() +"Action");
			deleteaction.setAttribute("method", "delete" + modEname);
			
			deleteaction.addContent("\n\t\t");
			deleteaction.addContent(deleteresult);
			deleteaction.addContent("\n\t");
			
			// update
			
			Element updateparam = new Element("param");
			updateparam.setAttribute("name", "includeProperties");
			updateparam.addContent("success");
			
			Element updateresult = new Element("result");
			updateresult.setAttribute("type", "json");
			
			updateresult.addContent("\n\t\t\t");
			updateresult.addContent(updateparam);
			updateresult.addContent("\n\t\t");
			
			Element updateaction = new Element("action");
			updateaction.setAttribute("name", "update" + modEname);
			updateaction.setAttribute("class", modEname.toLowerCase() +"Action");
			updateaction.setAttribute("method", "update" + modEname);
			
			updateaction.addContent("\n\t\t");
			updateaction.addContent(updateresult);
			updateaction.addContent("\n\t");
			
			// findAll
			
			Element rootparam = new Element("param");
			rootparam.setAttribute("name", "root");
			rootparam.addContent("page");
			Element excludeparam = new Element("param");
			excludeparam.setAttribute("name", "excludeProperties");
			excludeparam.addContent("conditions,limit,start,success,objCondition");
			
			Element findAllresult = new Element("result");
			findAllresult.setAttribute("type", "json");
			
			findAllresult.addContent("\n\t\t\t");
			findAllresult.addContent(rootparam);
			findAllresult.addContent("\n\t\t\t");
			findAllresult.addContent(excludeparam);
			findAllresult.addContent("\n\t\t");
			
			Element findAllaction = new Element("action");
			findAllaction.setAttribute("name", "findAll" + modEname);
			findAllaction.setAttribute("class", modEname.toLowerCase() +"Action");
			findAllaction.setAttribute("method", "findAll" + modEname);
			
			findAllaction.addContent("\n\t\t");
			findAllaction.addContent(findAllresult);
			findAllaction.addContent("\n\t");
			
			root.addContent("\n\t");
			root.addContent(saveaction);
			root.addContent("\n\t");
			root.addContent(deleteaction);
			root.addContent("\n\t");
			root.addContent(updateaction);
			root.addContent("\n\t");
			root.addContent(findAllaction);
			root.addContent("\n");
			
			
			XMLOutputter outp = new XMLOutputter(); 
			outp.output(doc, new FileOutputStream("E:\\smartacv1\\smartacv1\\temp\\a.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genColInfo() {
		  //
		List showList = getAllMeta(view);	
		for(int i=0; i<showList.size(); i++) {
			Meta meta = (Meta)showList.get(i);
			
			System.out.print("{header : '" + meta.getCName()
					+ "',	width : 90,	dataIndex : '" 
					+ meta.getEName() + "'}");	
			
			if(i != showList.size()-1){
				System.out.println(",");
			}
		}		  
	}
	
	public static void genDataSource(){
		
		List metalist = getAllMeta(table);
		List showList = getAllMeta(view);
		
		for(int i=0; i<showList.size(); i++) {
			Meta meta = (Meta)showList.get(i);
			
			//System.out.println(meta.getEName());
			
			if(meta.getDataType().equals("varchar")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'string'},");	
			}
			if(meta.getDataType().equals("char")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'string'},");	
			}
			else if(meta.getDataType().equals("int")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'int'},");
			}
			else if(meta.getDataType().equals("bigint")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'int'},");
			}
			else if(meta.getDataType().equals("double")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'float'},");
			}	
			else if(meta.getDataType().equals("bit")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'bool'},");
			}
			else if(meta.getDataType().equals("datetime")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'date',dateFormat :'Y-m-d\\\\TH:i:s'},");
			}
			else if(meta.getDataType().equals("date")){
				System.out.println("{name : '"+ meta.getEName() +"',type : 'date',dateFormat :'Y-m-d H:i:s.u'},");
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	String cnName = "";
		String enName = "";
		String tName = "";
		String vName = ""; */
		
		if(args.length < 4) {
			System.out.println("Usage: GenFile cnName enName tName vName");
			System.exit(0);
			// 加上从配置文件读取
		}
		
		modCname = args[0];
		modEname = args[1];
		table 	 = args[2];
		view	 = args[3];
		
		
		
		// 定义标准输入流对象
		InputStreamReader isr = new InputStreamReader(System.in);
		// 定义标准输入流的缓存读对象
		BufferedReader br = new BufferedReader(isr);
		
		//String userRes = "1";
		String exeOpt = "0";
		int step = 0;
		try{
			System.out.println("**欢迎进入自动代码系统**");
			System.out.println("您输入的模块中文名称是：" + modCname);
			System.out.println("您输入的模块英文名称是：" + modEname);
			System.out.println("您输入的模块表名称是：" + table);
			System.out.println("您输入的模块视图名称" + view);
			
			do{
				System.out.println("请选择您所要自动完成的操作(退出：Quit)：");
				Map map = new HashMap(); 
				map.put(1, "生成Bean文件.");
				map.put(2, "生成Service接口文件.");
				map.put(3, "生成Service实现文件.");
				map.put(4, "生成Dao接口文件.");
				map.put(5, "生成Dao实现文件.");
				map.put(6, "生成Action文件.");
				map.put(7, "生成数据映射XML.");
				map.put(8, "生成Dao绑定Bean代码.");
				map.put(9, "生成Service绑定Dao代码.");
				map.put(10, "生成Action绑定Service代码.");
				map.put(11, "生成SqlMap配置代码.");
				map.put(12, "生成Structs配置代码.");
				map.put(13, "生成数据列.");
				map.put(14, "生成数据映射.");
				
				
				for (Object o : map.keySet()) {
					String val = map.get(o).toString();
					System.out.println( o.hashCode() + "：" + val);
				}
				exeOpt = br.readLine();
				
				if (exeOpt.equalsIgnoreCase("Quit")){
					break;
				}
				else{
					System.out.println("您选择的操作是：" + exeOpt + "," + map.get(Integer.valueOf(exeOpt)));
					if(exeOpt.equals("1")){
						genBean();
					}else if (exeOpt.equals("2")){
						genIService();
					}else if (exeOpt.equals("3")){
						genService();
					}else if (exeOpt.equals("4")){
						genIDao();
					}else if (exeOpt.equals("5")){
						genDao();
					}else if (exeOpt.equals("6")){
						genAction();
					}else if (exeOpt.equals("7")){
						genPoXml();
					}else if (exeOpt.equals("8")){
						updateDaoXML();
					}else if (exeOpt.equals("9")){
						updateServiceXML();
					}else if (exeOpt.equals("10")){
						updateActionXML();
					}else if (exeOpt.equals("11")){
						updateSqlMap();
					}else if (exeOpt.equals("12")){
						updateStructs();
					}else if (exeOpt.equals("13")){
						genColInfo();
					}else if (exeOpt.equals("14")){
						genDataSource();
					}
				}
			}while(true);
			
/*			while(step < 5){
				switch(step){
				case 0:// 接收用户输入的模块中文名称
					System.out.println("步骤1：请输入模块中文名称：");
					cnName = br.readLine();
					System.out.println("您输入的模块中文名称是：" + cnName);	
					step++;
					break;
				case 1:// 接收用户输入的模块英文名称
					System.out.println("步骤2：请输入模块英文名称(后退：BACK)：");
					enName = br.readLine();
					if (enName.equalsIgnoreCase("BACK")){
						step--;
						break;
					}
					else{
						System.out.println("您输入的模块英文名称是：" + enName);
					}
					step++;
					break;
				case 2:// 接收用户输入的模块英文名称
					System.out.println("步骤3：请输入模块表名称(后退：BACK)：");
					tName = br.readLine();
					if (tName.equalsIgnoreCase("BACK")){
						step--;
						break;
					}
					else{
						System.out.println("您输入的模块表名称是：" + tName);
					}
					step++;
					break;
				case 3:// 接收用户输入的模块英文名称
					System.out.println("步骤4：请输入模块视图名称(后退：BACK)：");
					vName = br.readLine();
					if (vName.equalsIgnoreCase("BACK")){
						step--;
						break;
					}
					else{
						System.out.println("您输入的模块视图名称" + vName);
					}
					step++;
					break;
				case 4:// 接收用户输入的操作项，完成文件操作
					do{
						System.out.println("步骤3：请选择您所要自动完成的操作(后退：BACK)：");
						Map map = new HashMap(); 
						map.put(1, "生成Bean文件.");
						map.put(2, "生成Service接口文件.");
						map.put(3, "生成Service实现文件.");
						map.put(4, "生成Dao接口文件.");
						map.put(5, "生成Dao实现文件.");
						map.put(6, "生成Action文件.");
						map.put(7, "生成数据映射XML.");
						map.put(8, "生成Dao绑定Bean代码.");
						map.put(9, "生成Service绑定Dao代码.");
						map.put(10, "生成Action绑定Service代码.");
						
						for (Object o : map.keySet()) {
							String val = map.get(o).toString();
							System.out.println( o.hashCode() + "：" + val);
						}
						exeOpt = br.readLine();
						
						modCname = cnName;
						modEname = enName;
						table = tName;
						view = vName;
						
						if (exeOpt.equalsIgnoreCase("BACK")){
							step = 2;
							break;
						}
						else{
							System.out.println("您选择的操作是：" + exeOpt + "," + map.get(Integer.valueOf(exeOpt)));
							if(exeOpt.equals("1")){
								genBeanFile();
							}else (){}
						}
					}while(true);
					step++;
					break;
				}
			}*/
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				br.close();
				isr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

}
