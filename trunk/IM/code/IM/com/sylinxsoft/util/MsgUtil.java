package com.sylinxsoft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * 保存聊天记录
 * 
 * @author YanQg
 * 
 */
public class MsgUtil {

	private static BufferedWriter bfw = null;

	private static String fileNmae = "";
	static {
		String date = DateFormater
				.formatDate(new Date(), "yyyy_MM_dd");
		String userDir = System.getProperty("user.home");
		fileNmae = userDir +"/msg/" + date + ".msgf";
		File msgfile = new File(userDir + "/msg");
		if (!msgfile.exists()) {
			msgfile.mkdirs();
		}
		msgfile = new File(fileNmae);
		try {
			msgfile.createNewFile();
			bfw = new BufferedWriter(new FileWriter(msgfile,true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 保存聊天记录
	 * 
	 * @param msg
	 */
	public static void save(String msg) {
		if (null != bfw) {
			try {
				String date = DateFormater.formatDate(new Date(),
						"yyyy-MM-dd hh:mm:ss");
				msg = date + AllConstatns.IPHEADERMARK + msg;
				bfw.write(msg + AllConstatns.MSGFILETOKEN);
				bfw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("找不到消息记录文件");
		}
	}

	public static Iterator<String> reader(String date) {
		List<String> list = new ArrayList<String>();
		String userDir = System.getProperty("user.home");
		File msgfile = new File(userDir +"/msg");
		if (msgfile.exists()) {
			File[] files = msgfile.listFiles();
			for (File f : files) {
				if (null == date) {
					list.add(f.getName());
				} else if (f.getName().startsWith(date)) {
					list.add(f.getName());
				}
			}
		}
		Collections.sort(list, new StringComparator());
		List<String> msgList = new ArrayList<String>();

		for (String s : list) {
			File file = new File(userDir + "/msg/" + s);
			try {
				StringBuffer bf = new StringBuffer();
				BufferedReader reader = new BufferedReader(new FileReader(file));
				int ch = reader.read();
				while (-1 != ch) {
					bf.append((char) ch);
					ch = reader.read();
				}
				String str = bf.toString();
				if (!"".equals(str) && !"".equals(str.trim())) {
					String[] msgs = str.split(AllConstatns.MSGFILETOKEN);
					for (String msg : msgs) {
						msgList.add(msg);
					}
					/*
					while (-1 != str.indexOf(AllConstatns.MSGFILETOKEN)) {
						System.out.println("######### : "
								+ str);
						msgList.add(str.substring(str
								.indexOf(AllConstatns.MSGFILETOKEN)));
						str = str.substring(str
								.indexOf(AllConstatns.MSGFILETOKEN)
								+ AllConstatns.MSGFILETOKEN.length());
					}
					*/
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msgList.iterator();
	}
}
