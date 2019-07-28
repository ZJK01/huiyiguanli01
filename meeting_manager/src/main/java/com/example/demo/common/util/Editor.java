package com.example.demo.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;

import net.bytebuddy.asm.Advice.This;

@Component
public class Editor {

	public static final String FILEADDRESS=new String("E:\\file\\");
	public static final String DOWNADDRESS=new String("C:\\Users\\Administrator\\Desktop\\downfile\\");

	public void docFile(String context, String filename) throws Exception {
		/* InputStream cssIs2 = new FileInputStream("D:\\java\\zyf.css"); */
		String body = context;
		/* String css2 = this.getContent(cssIs2); */
		// 拼一个标准的HTML格式文档
		/*
		 * String content = "<html><head><style>" + css2 + "</style></head><body>" +
		 * body + "</body></html>";
		 */		
		String content = "<html><head></head><body>" + body + "</body></html>";
		InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
		if (filename == null) {
			filename = new Date().getTime() + "";
		}
		OutputStream os = new FileOutputStream(FILEADDRESS+ filename + ".doc");
		this.inputStreamToWord(is, os);
	}

	/**
	 * 把is写入到对应的word输出流os中 不考虑异常的捕获，直接抛出
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */

	private  void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem();
		// 对应于org.apache.poi.hdf.extractor.WordDocument
		fs.createDocument(is, "WordDocument");
		fs.writeFilesystem(os);
		os.close();
		is.close();
	}

	/**
	 * 把输入流里面的内容以UTF-8编码当文本取出。 不考虑异常，直接抛出
	 * @param ises
	 * @return
	 * @throws IOException
	 */
	public String getContent(InputStream... ises) throws IOException {
		if (ises != null) {
			StringBuilder result = new StringBuilder();
			BufferedReader br;
			String line;
			for (InputStream is : ises) {
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				while ((line = br.readLine()) != null) {
					result.append(line);
				}
			}
			return result.toString();
		}
		return null;
	}
	
	/**
	 * 下载文件
	 * */
	public  void downfile(String filename) {
		try {
			InputStream is = new FileInputStream(new File(FILEADDRESS+filename+".doc"));
			String context=getContent(is);							//以UTF-8编码取出
			InputStream iss = new ByteArrayInputStream(context.getBytes("UTF-8"));
			
			File file=new File(DOWNADDRESS);			//判断文件夹是否存在
			if(!file.exists()) {
				file.mkdirs();
			}
			OutputStream os = new FileOutputStream(new File(DOWNADDRESS+filename + ".doc"));
			this.inputStreamToWord(iss,os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件
	 * */
//	public  void downfile(String filename) {
//		try {
//			InputStream is = new FileInputStream(new File(FILEADDRESS+filename+".doc"));
//			String context=getContent(is);							//以UTF-8编码取出
//			InputStream iss = new ByteArrayInputStream(context.getBytes("UTF-8"));
//			
//			File file=new File(DOWNADDRESS);			//判断文件夹是否存在
//			if(!file.exists()) {
//				file.mkdirs();
//			}
//			OutputStream os = new FileOutputStream(new File(DOWNADDRESS+filename + ".doc"));
//			this.inputStreamToWord(iss,os);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
