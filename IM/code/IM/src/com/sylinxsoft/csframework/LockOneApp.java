package com.sylinxsoft.csframework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Date;

/**
 * ��ֻ֤����һ������
 * 
 * @author Administrator
 * 
 */
public class LockOneApp {
	private static String userDir = System.getProperty("user.home");
	public static File file = new File(userDir + "/SylinxSoft.app");
	private static FileChannel channel;
	private static FileLock lock;

	public static boolean isAppActive() {
		try {

			channel = new RandomAccessFile(file, "rw").getChannel();
			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				closeLock();
				return true;
			}
			if (lock == null) {
				closeLock();
				return true;
			}
			
			//�ڳ����˳�ǰ�ر����ļ�
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					closeLock();
					deleteFile();
				}
			});

			return false;
		} catch (Exception e) {
			closeLock();
			return true;
		}
	}

	protected static void deleteFile() {
		try {
			file.delete();
		} catch (Exception e) {

		}
	}

	private static void closeLock() {
		try {
			lock.release();
		} catch (Exception e) {

		}
		try {
			channel.close();
		} catch (Exception e) {

		}
	}

}
