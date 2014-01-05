package com.smart.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import com.smart.core.MyUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Debug {
/*
	public static char CRC8_cal(char[] ptr,int len){
		//unsigned int
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
		 
		 //System.out.println(crc8_tab.length);
		 char  crc = 0x00;
		 int i = 0;
		 while(len-- != 0){
			 crc = crc8_tab[ptr[i]^crc];
			 i++;
		 }
		 
		return crc;
	}*/
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//byte a = (byte) 0x82;
		//System.out.println(a == (byte)0x82);
		//System.out.printf("0x%02x\n",a);
	
		//byte toSend[] = {0x01,0x03,0x00,0x00,0x00,0x00,0x00};
		//byte recv[] = {0x01,0x03,0x00,0x00,0x00,0x00,0x00};
		
		//System.out.println(toSend.length);
		int i = 0x0000ADAD;
		System.out.println(i);
		
		byte b0 = (byte) (0xFF & i);
		byte b1 = (byte) (0xFF & (i >> 8));
		System.out.println(String.format("0x%02x ", b0));
		System.out.println(String.format("0x%02x ", b1));
		
		
		
		/*
		String a = "test.jpg";;
		byte b[] = a.getBytes();
		
		for(int i =0; i<b.length; i++){
			System.out.printf("0x%02x\n",(byte)b[i]);
		}
		
		/*	int i = 128;
		byte a = (byte)i;
		System.out.printf("0x%02x\n",a);
		
		
		byte b = (byte)0xff;
		int c = (int) (b & 0xff);
		System.out.printf("%d\n",c);
		
	/*	char toSend[] = {0X01, 0X03,0X00,0X00};
		char toRecv[] = {0X01, 0X03,0X00,0X00};
		
		System.out.println(toSend[0] == toRecv[0]);
	/*	 toSend[2] = 0X01;
		 toSend[3] = 0x01;
		 System.out.println(toSend.length);
	/*
		String a = "255";
		byte abyte = (byte)Integer.valueOf(a).intValue();
		System.out.printf("0x%02x\n",abyte);
	/*	
		int i =0;
		for(i =0; i<256; i++){
			System.out.printf("0x%02x\n",(byte)i);
		}
		*/
		//char a[]={0x01,0x43,0x00};
	/*	char a[] = {0X01,0X82,0X40,0x00};
		int l = 3;
		a[3] = MyUtils.CRC8_cal(a,l);
		
		byte b[] = {0X01,(byte) 0X82,0X40,0x00};
		b[3] = MyUtils.CRC8_cal(b,l);
		
		String r = "";
		for(int i=0; i<4; i++){
			r = String.format("%s0x%02x ", r,(byte)b[i]);
		}
		System.out.println(r);
		 
	/*  
		uint16 l,
			   crc9,
			   crc16;
		uint32 crc32;
		l=3;
	  crc8=CRC8_cal(a, l);
	  printf("0x%x\n",crc8);

  
		Debug.CRC8_cal();
		/*
		byte t = 0X01;
		System.out.printf("%H%c%c%c", 0X01, 0X01, 0X01, 0X01);
		/*
		java.io.InputStream is = Debug.class.getResourceAsStream("/config.properties");
		
		Properties props = new Properties();   
		props.load(is);
		
		System.out.println(props.getProperty("sysTitle"));
		System.out.println(props.getProperty("sysSimTitle"));
		
		/*
		List<String> arrayList = MyUtils.getListFiles("E:\\smartacv1\\smartacv1\\WebRoot\\system\\alarm\\2011/03/29/065612","jpg");
		
		int count = arrayList.size();
		
		System.out.println(count);
		
		for(int i =0; i< count; i++){
			
			
			File  f = new File(arrayList.get(i));
			
			if(f.isFile()){
				System.out.println(f.toURI());
				System.out.println(f.toURL());
				System.out.println(f.getPath());
				System.out.println(f.getName());
				System.out.println(new Date(f.lastModified()));
				System.out.println(f.length()/1024);
				//logger.debug(f.getPath());
			}
		}
		
		/*
		String filter = "[{\"type\":\"numeric\",\"comparison\":\"lt\",\"value\":100,\"field\":\"accessId\"}]";
		if(null != filter){
			//JSONObject jsonObj = JSONObject.fromObject(filter);
			JSONArray jsonArr = JSONArray.fromObject(filter);
			int filterSize = jsonArr.size();
			System.out.println("Get " + filterSize + " filters");
			for (int i=0; i<filterSize; i++){
				JSONObject jsonObj = JSONObject.fromObject(jsonArr.get(i));
				System.out.println((i+1) + ": " + jsonObj);
			}
		}
		
		/*
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		
		Date startDt = new Date();
		startDt = dateFormat.parse("2011-05-03 21:00:02");
		
		Date endDt = new Date();
		endDt = dateFormat.parse("2011-05-13 21:57:02");
	
		Calendar objCalendarDate1 = Calendar.getInstance();
		Calendar objCalendarDate2 = Calendar.getInstance();
		
		if(startDt.getTime() < endDt.getTime()){
	
		   objCalendarDate1.setTime(startDt);
		   objCalendarDate2.setTime(endDt);
		} else {
			objCalendarDate1.setTime(endDt);
			objCalendarDate2.setTime(startDt);
		}
		   
		// 获取起始时间所在的月份
		//System.out.println(objCalendarDate1.get(Calendar.YEAR));
		//System.out.println(objCalendarDate1.get(Calendar.MONTH)+1);
		
		//System.out.println(objCalendarDate2.get(Calendar.YEAR));
		//System.out.println(objCalendarDate2.get(Calendar.MONTH)+1);
		
		while (!objCalendarDate1.equals(objCalendarDate2)){
			System.out.println(String.format("%d-%02d-%02d",objCalendarDate1.get(Calendar.YEAR),objCalendarDate1.get(Calendar.MONTH)+1,objCalendarDate1.get(Calendar.DAY_OF_MONTH)));
			objCalendarDate1.add(Calendar.DAY_OF_YEAR, 1);

		}
		//System.out.println(objCalendarDate1.equals(objCalendarDate2));
		
		
		
		for (int Y=objCalendarDate1.get(Calendar.YEAR); Y <= objCalendarDate2.get(Calendar.YEAR); Y++){
			int startM = 1;
			int endM = 12;
			if(Y == objCalendarDate1.get(Calendar.YEAR)){
				startM = objCalendarDate1.get(Calendar.MONTH)+1;
			}
			
			if(Y == objCalendarDate2.get(Calendar.YEAR)){
				endM = objCalendarDate2.get(Calendar.MONTH)+1;
			}
			
			for(int M = startM; M <= endM; M++ ){
				//int startD = 1;
				//int endD = 
				//System.out.println(String.format("%d-%02d",Y,M));
			}
		}
		*/
	}

}
