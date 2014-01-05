package com.smart.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * MyUtils.java Create on 2008-9-17 10:45:20
 * 
 * 
 * 
 * Copyright (c) 2008 by MTA.
 * 
 * @author 
 * @version 1.0
 */
public class MyUtils {

	static Logger logger = Logger.getLogger(MyUtils.class);
	
	public static void main(String[] s) {
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split("a,b-c d,e f-g", " ,-"));
		System.out.println(conditions);// output[a, b, c, d, e, f, g]
	}

	public static List<String> fileList = new ArrayList<String>();
	
	/**
	*
	* @param path 文件路径
	* @param suffix 后缀名
	* @param isdepth 是否遍历子目录
	* @return
	*/
	public static List getListFiles(String path, String suffix,boolean isdepth) {
	   File file = new File(path);
	   fileList.removeAll(fileList);
	   return listFile(file, suffix, isdepth, true);  
	}
	
	/**
	*
	* @param f File Object 
	* @param suffix The suffix of files you care
	* @param isdepth if need to search file in sub-directory 
	* @return
	*/
	public static List listFile(File f, String suffix, boolean isdepth, boolean isroot) {
		
		 
		 if(f.isDirectory()){ //文件对象f指向的是一个目录，同时需要遍历子目录时，对该目录下所有文件递归调用该函数
			 	if(isroot || isdepth){ // If this is root dir or need to list sub dir
				    File[] t = f.listFiles();
				    for (int i = 0; i < t.length; i++){
				    		listFile(t[i], suffix,isdepth,false);
				    }
			 	}
		 }else{ // 文件对象f指向的是一个文件
		    String filePath = f.getAbsolutePath();
		    if(suffix != null){ // 需要匹配后缀
		    	int begIndex = filePath.lastIndexOf(".");//最后一个.(即后缀名前面的.)的索引
		    	String tempsuffix = "";
		    	if(begIndex != -1){//防止是文件但却没有后缀名结束的文件
		    		tempsuffix = filePath.substring(begIndex + 1, filePath.length());
		    	}
		    
		    	if(tempsuffix.equalsIgnoreCase(suffix)){
		    		fileList.add(filePath);
		    	}
		    }
		    else{ //后缀名为null则为所有文件
		    	fileList.add(filePath);
		    }
		 }
		 
		return fileList;
	}
	
	/**
	 * 
	 * 
	 * @param filePathAndName
	 *          String :/fqf.txt
	 * @param fileContent
	 *          String
	 * @return boolean
	 */
	public static boolean delFile(String filePathAndName) {
		File myDelFile = new java.io.File(filePathAndName);
		if (!myDelFile.exists()) {
			return true;
		}
		return myDelFile.delete();
	}

	/**
	 * �ϴ��ļ�
	 * 
	 * @param uploadFileName
	 *          
	 * @param savePath
	 *          
	 * @param uploadFile
	 *         
	 * @return newFileName
	 */
	public static String upload(String uploadFileName, String savePath, File uploadFile) {
		
		logger.debug("uploadFileName: " + uploadFileName);
		
		if(null == uploadFileName) {
			uploadFileName = uploadFile.getName();
			
		}
		logger.debug("uploadFileName: " + uploadFileName);
		
		String newFileName = getRandomName(uploadFileName, savePath);
		try {
			FileOutputStream fos = new FileOutputStream(savePath + newFileName);
			FileInputStream fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFileName;
	}

	/**
	 * 
	 * 
	 * @param path
	 */
	public static void mkDirectory(String path) {
		File file;
		try {
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
	}

	/**
	 * �����������ÿһ��Ԫ�طֱ���ӵ�ָ��������,����Apache commons collections �еķ���
	 * 
	 * @param collection
	 *          Ŀ�꼯�϶���
	 * @param arr
	 *          ��������
	 */
	public static void addToCollection(Collection collection, Object[] arr) {
		if (null != collection && null != arr) {
			CollectionUtils.addAll(collection, arr);
		}
	}

	/**
	 * 
	 * 
	 * <pre>
	 *    Example:
	 *     String[] arr = StringUtils.split(&quot;a b,c d,e-f&quot;, &quot; ,-&quot;);
	 *     System.out.println(arr.length);//���6
	 * </pre>
	 * 
	 * @param str
	 *          Ŀ���ַ�
	 * @param separatorChars
	 *          �ָ���ַ�
	 * @return �ַ�����
	 */
	public static String[] split(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}

	/**
	 * 
	 * 
	 * <pre>
	 *    Example:
	 *    User user = new User();
	 *    MyUtils.invokeSetMethod(&quot;userName&quot;, user, new Object[] {&quot;����&quot;});
	 * </pre>
	 * 
	 * @param fieldName
	 *          �ֶ�(����)���
	 * @param invokeObj
	 *          �����÷����Ķ���
	 * @param args
	 *          �����÷����Ĳ�������
	 * @return �ɹ����
	 */
	public static boolean invokeSetMethod(String fieldName, Object invokeObj, Object[] args) {
		boolean flag = false;
		Field[] fields = invokeObj.getClass().getDeclaredFields(); // ��ö���ʵ���������ж�����ֶ�
		Method[] methods = invokeObj.getClass().getDeclaredMethods(); // ��ö���ʵ���������ж���ķ���
		for (Field f : fields) {
			String fname = f.getName();
			if (fname.equalsIgnoreCase(fieldName)) {// �ҵ�Ҫ���µ��ֶ���
				String mname = "set" + (fname.substring(0, 1).toUpperCase() + fname.substring(1));// �齨setter����
				for (Method m : methods) {
					String name = m.getName();
					if (mname.equals(name)) {
						// ����Integer����
						if (f.getType().getSimpleName().equalsIgnoreCase("integer") && args.length > 0) {
							args[0] = Integer.valueOf(args[0].toString());
						}
						// ����Boolean����
						if (f.getType().getSimpleName().equalsIgnoreCase("boolean") && args.length > 0) {
							args[0] = Boolean.valueOf(args[0].toString());
						}
						try {
							m.invoke(invokeObj, args);
							flag = true;
						} catch (IllegalArgumentException e) {
							flag = false;
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							flag = false;
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							flag = false;
							e.printStackTrace();
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * �ж��ļ��Ƿ����
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return (files.exists()) ? true : false;
	}

	/**
	 * getRandomName
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static String getRandomName(String fileName, String dir) {
		String[] split = fileName.split("\\.");// 
		String extendFile = "." + split[split.length - 1].toLowerCase(); // 

		Random random = new Random();
		int add = random.nextInt(1000000); // 
		String ret = add + extendFile;
		while (isFileExist(ret, dir)) {
			add = random.nextInt(1000000);
			ret = fileName + add + extendFile;
		}
		return ret;
	}

	/**
	 * ��������ͼ
	 * 
	 * @param file
	 *          �ϴ����ļ���
	 * @param height
	 *          ��С�ĳߴ�
	 * @throws IOException
	 */
	public static void createMiniPic(File file, float width, float height) throws IOException {
		Image src = javax.imageio.ImageIO.read(file); // ����Image����
		int old_w = src.getWidth(null); // �õ�Դͼ��
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0; // �õ�Դͼ��
		float tempdouble;
		if (old_w >= old_h) {
			tempdouble = old_w / width;
		} else {
			tempdouble = old_h / height;
		}

		if (old_w >= width || old_h >= height) { // ����ļ�С������ͼ�ĳߴ����Ƽ���
			new_w = Math.round(old_w / tempdouble);
			new_h = Math.round(old_h / tempdouble);// ������ͼ����
			while (new_w > width && new_h > height) {
				if (new_w > width) {
					tempdouble = new_w / width;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
				if (new_h > height) {
					tempdouble = new_h / height;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
			}
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null); // ������С���ͼ
			FileOutputStream newimage = new FileOutputStream(file); // ����ļ���
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
			param.setQuality((float) (100 / 100.0), true);// ����ͼƬ��,100���,Ĭ��70
			encoder.encode(tag, param);
			encoder.encode(tag); // ��JPEG����
			newimage.close();
		}
	}

	/**
	 * �ж��ļ������Ƿ��ǺϷ���,�����ж�allowTypes���Ƿ��contentType
	 * 
	 * @param contentType
	 *          �ļ�����
	 * @param allowTypes
	 *          �ļ������б�
	 * @return �Ƿ�Ϸ�
	 */
	public static boolean isValid(String contentType, String[] allowTypes) {
		if (null == contentType || "".equals(contentType)) {
			return false;
		}
		for (String type : allowTypes) {
			if (contentType.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * CRC8_cal Calculate CRC8 validation code of a byte data array
	 * 
	 * @param ptr 
	 *			Data Array 
	 * @param len
	 * 			The length of array
	 * @return CRC8 code
	 */
	public static byte CRC8_cal(byte[] ptr,int len){
		
		 char crc8_tab[] = {0x0,0x7,0xe,0x9,0x1c,0x1b,0x12,0x15,
				  0x38,0x3f,0x36,0x31,0x24,0x23,0x2a,0x2d,
				  0x70,0x77,0x7e,0x79,0x6c,0x6b,0x62,0x65,
				  0x48,0x4f,0x46,0x41,0x54,0x53,0x5a,0x5d,
				  0xe0,0xe7,0xee,0xe9,0xfc,0xfb,0xf2,0xf5,
				  0xd8,0xdf,0xd6,0xd1,0xc4,0xc3,0xca,0xcd,
				  0x90,0x97,0x9e,0x99,0x8c,0x8b,0x82,0x85,
				  0xa8,0xaf,0xa6,0xa1,0xb4,0xb3,0xba,0xbd,
				  0xc7,0xc0,0xc9,0xce,0xdb,0xdc,0xd5,0xd2,
				  0xff,0xf8,0xf1,0xf6,0xe3,0xe4,0xed,0xea,
				  0xb7,0xb0,0xb9,0xbe,0xab,0xac,0xa5,0xa2,
				  0x8f,0x88,0x81,0x86,0x93,0x94,0x9d,0x9a,
				  0x27,0x20,0x29,0x2e,0x3b,0x3c,0x35,0x32,
				  0x1f,0x18,0x11,0x16,0x3,0x4,0xd,0xa,
				  0x57,0x50,0x59,0x5e,0x4b,0x4c,0x45,0x42,
				  0x6f,0x68,0x61,0x66,0x73,0x74,0x7d,0x7a,
				  0x89,0x8e,0x87,0x80,0x95,0x92,0x9b,0x9c,
				  0xb1,0xb6,0xbf,0xb8,0xad,0xaa,0xa3,0xa4,
				  0xf9,0xfe,0xf7,0xf0,0xe5,0xe2,0xeb,0xec,
				  0xc1,0xc6,0xcf,0xc8,0xdd,0xda,0xd3,0xd4,
				  0x69,0x6e,0x67,0x60,0x75,0x72,0x7b,0x7c,
				  0x51,0x56,0x5f,0x58,0x4d,0x4a,0x43,0x44,
				  0x19,0x1e,0x17,0x10,0x5,0x2,0xb,0xc,
				  0x21,0x26,0x2f,0x28,0x3d,0x3a,0x33,0x34,
				  0x4e,0x49,0x40,0x47,0x52,0x55,0x5c,0x5b,
				  0x76,0x71,0x78,0x7f,0x6a,0x6d,0x64,0x63,
				  0x3e,0x39,0x30,0x37,0x22,0x25,0x2c,0x2b,
				  0x6,0x1,0x8,0xf,0x1a,0x1d,0x14,0x13,
				  0xae,0xa9,0xa0,0xa7,0xb2,0xb5,0xbc,0xbb,
				  0x96,0x91,0x98,0x9f,0x8a,0x8d,0x84,0x83,
				  0xde,0xd9,0xd0,0xd7,0xc2,0xc5,0xcc,0xcb,
				  0xe6,0xe1,0xe8,0xef,0xfa,0xfd,0xf4,0xf3};
		 
		 char crc = 0x00;
		 int i = 0;
		 while(len-- != 0){
			 crc = crc8_tab[(ptr[i] & 0xff)^crc];
			 i++;
		 }
		 
		return (byte)crc;
	}
	
	/**
	 * CRC8_cal Calculate CRC8 validation code of a byte data array
	 * 
	 * @param ptr 
	 *			Data Array 
	 * @param len
	 * 			The length of array
	 * @return CRC8 code
	 */
	public static char CRC8_cal(char[] ptr,int len){
		
		 char crc8_tab[] = {0x0,0x7,0xe,0x9,0x1c,0x1b,0x12,0x15,
				  0x38,0x3f,0x36,0x31,0x24,0x23,0x2a,0x2d,
				  0x70,0x77,0x7e,0x79,0x6c,0x6b,0x62,0x65,
				  0x48,0x4f,0x46,0x41,0x54,0x53,0x5a,0x5d,
				  0xe0,0xe7,0xee,0xe9,0xfc,0xfb,0xf2,0xf5,
				  0xd8,0xdf,0xd6,0xd1,0xc4,0xc3,0xca,0xcd,
				  0x90,0x97,0x9e,0x99,0x8c,0x8b,0x82,0x85,
				  0xa8,0xaf,0xa6,0xa1,0xb4,0xb3,0xba,0xbd,
				  0xc7,0xc0,0xc9,0xce,0xdb,0xdc,0xd5,0xd2,
				  0xff,0xf8,0xf1,0xf6,0xe3,0xe4,0xed,0xea,
				  0xb7,0xb0,0xb9,0xbe,0xab,0xac,0xa5,0xa2,
				  0x8f,0x88,0x81,0x86,0x93,0x94,0x9d,0x9a,
				  0x27,0x20,0x29,0x2e,0x3b,0x3c,0x35,0x32,
				  0x1f,0x18,0x11,0x16,0x3,0x4,0xd,0xa,
				  0x57,0x50,0x59,0x5e,0x4b,0x4c,0x45,0x42,
				  0x6f,0x68,0x61,0x66,0x73,0x74,0x7d,0x7a,
				  0x89,0x8e,0x87,0x80,0x95,0x92,0x9b,0x9c,
				  0xb1,0xb6,0xbf,0xb8,0xad,0xaa,0xa3,0xa4,
				  0xf9,0xfe,0xf7,0xf0,0xe5,0xe2,0xeb,0xec,
				  0xc1,0xc6,0xcf,0xc8,0xdd,0xda,0xd3,0xd4,
				  0x69,0x6e,0x67,0x60,0x75,0x72,0x7b,0x7c,
				  0x51,0x56,0x5f,0x58,0x4d,0x4a,0x43,0x44,
				  0x19,0x1e,0x17,0x10,0x5,0x2,0xb,0xc,
				  0x21,0x26,0x2f,0x28,0x3d,0x3a,0x33,0x34,
				  0x4e,0x49,0x40,0x47,0x52,0x55,0x5c,0x5b,
				  0x76,0x71,0x78,0x7f,0x6a,0x6d,0x64,0x63,
				  0x3e,0x39,0x30,0x37,0x22,0x25,0x2c,0x2b,
				  0x6,0x1,0x8,0xf,0x1a,0x1d,0x14,0x13,
				  0xae,0xa9,0xa0,0xa7,0xb2,0xb5,0xbc,0xbb,
				  0x96,0x91,0x98,0x9f,0x8a,0x8d,0x84,0x83,
				  0xde,0xd9,0xd0,0xd7,0xc2,0xc5,0xcc,0xcb,
				  0xe6,0xe1,0xe8,0xef,0xfa,0xfd,0xf4,0xf3};
		 
		 char  crc = 0x00;
		 int i = 0;
		 while(len-- != 0){
			 crc = crc8_tab[ptr[i]^crc];
			 i++;
		 }
		 
		return crc;
	}
	
	/**
	 * CharArrayToHexString
	 * 
	 * @param a 
	 * 		Array of bytes
	 * @param len
	 * 		length of Array
	 * @return Hex format string of bytes array
	 */
	public static String ByteArrayToHexString(char a[],int len){
		String s = new String("");
		for(int i=0; i<len; i++){
			s = String.format("%s0x%02x ", s,(byte)a[i]);
		}
		return s;
	}
	
	/**
	 * CharArrayToHexString
	 * 
	 * @param a 
	 * 		Array of bytes
	 * @param len
	 * 		length of Array
	 * @return Hex format string of bytes array
	 */
	public static String ByteArrayToHexString(byte a[],int len){
		String s = new String("");
		for(int i=0; i<len; i++){
			s = String.format("%s0x%02x ", s,a[i]);
		}
		return s;
	}
	
	public static boolean ByteArrayEqual(byte[] a,byte[] b){
		
		if(a.length != b.length) {
			return false;
		}
		
		for (int i=0; i < a.length; i++){
			
			if(a[i] != b[i]){
				return false;
			}
		}
		
		return true;
	}
}
