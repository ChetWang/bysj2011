package com.sylinxsoft.csframework;

import java.awt.Color;

/**
 * 将所有程序中用到的常量放在此处统一管理
 * <p>
 * 
 * @author Administrator 2007-9-28 AllConstatns.java
 */
public final class AllConstatns {

	/** 始终监听线程端口逻辑名 */
	public static final String INITLISTENPORT = "init-listen-port";

	/** 接收消息端口逻辑名 */
	public static final String RECVMSGPORT = "recv-msg-port";
	
	/** 接收消息端口 */
	public static final int RECVMSGIPPORT = 5077;
	
	/** 接收文件端口逻辑名 */
	public static final String RECVFILEPORT = "recv-file-prots";

	/** 客户端名称 */
	public static final String CLIENTNAME = "client-name";

	/** 客户端名称 */
	public static final String CLIENTIP = "client-ip";
	
	/** 扫描标记，是刷新还是回复 */
	public static final String SCANMARK = "scanmark";
	
	/** 扫描标记，刷新 */
	public static final String SCANREFRESHMARK = "refresh";
	
	/** 扫描标记，回复 */
	public static final String SCANREPLYMARK = "reply";
	
	/** 协议开始标记 */
	public static final String PROTOCOLSTARTMARK = "#protocolstart";

	/** 协议结束标记 */
	public static final String PROTOCOLENDMARK = "#protocolend";

	/** 消息分隔符 */
	public static final String MSGTOKEN = "#%";
	

	/** 初始化协议 */
	public static final String INITONLINEPCPROTOCOL = "initonlinepc";

	/** 发送普通消息协议 */
	public static final String GENERALMSGPCPROTOCOL = "generalmsg";
	
	/**发送文件协议*/
	public static final String SENDFILEPROTOCOL = "sendfile";
	
	/**确认是否接收文件协议*/
	public static final String ANSWERSENDFILEPROTOCOL = "answersendfile";
	/**取消文件发送协议*/
	public static final String CANNELSENDFILEPROTOCOL = "cannelsendfile";
	
	
	/**确认是否接收文件命令，拒绝*/
	public static final String FSREFUSECOMMAND = "refuse";
	/**确认是否接收文件命令，同意*/
	public static final String FSAGREECOMMAND = "agree";
	/**确认是否接收文件命令，同意*/
	public static final String FSAGREETOCOMMAND = "agreeto";
	/**确认是否接收文件命令，被中断*/
	public static final String FSINTERRUPTCOMMAND = "interrupt";
	

	/** 发送普通消息协议 */
	public static final String IPHEADERMARK = "%$%";
	
	// 以下是发送消息头
	public static final String HEADFONTCOLOR = "%#col"; // 字体颜色

	public static final String HEADFONTSIZE = "%#siz"; // 字体大小

	public static final String HEADFONTFAMILY = "%#ftf"; // 字体风格

	public static final String HEADBOLD = "%#bod"; // 粗体

	public static final String HEADEND = "%#end"; // 消息头结束

	public static final String FACEMARKSTART = "%#sta"; // 图片开始

	public static final String FACEMARKEND = "%#ind"; // 图片结束
	
	/**扫描的起始IP*/
	public static final String SCANSTARTIP = "startip"; 
	
	/**扫描的终止IP*/
	public static final String SCANENDTIP = "endip";
	

	
	/**发送文件名*/
	public static final String SENDFILENAME = "#%fname%#";
	/**文件发送确认端口*/
	public static final String FILESERVERPORT = "#%port%#";
	/**文件大小*/
	public static final String FILESIZE = "#%fsize%#";
	/**文件大小*/
	public static final String FILECOMMAND = "#%command%#";
	
	/**消息已经改变*/
	public static final String MSGCHANGED = "msgchange";
	
	/**发送文件*/
	public static final String MSGSENDFILE = "msgsendfile";
	
	public static final String LISTENER_SERVER = "listener-msg-ip";
	public static final String LISTENER_SERVER_PORT = "listener-msg-port";
	
	public static final String MSGFILETOKEN = "basdffdsab";
	public static final String SELFMSGFILEMARK = "@@@";
	
	// 面板背景色
	public static final Color BKCOLOR = new Color(215, 233, 251);
	

}
