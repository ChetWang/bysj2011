package com.sylinxsoft.csframework;

import java.awt.Color;

/**
 * �����г������õ��ĳ������ڴ˴�ͳһ����
 * <p>
 * 
 * @author Administrator 2007-9-28 AllConstatns.java
 */
public final class AllConstatns {

	/** ʼ�ռ����̶߳˿��߼��� */
	public static final String INITLISTENPORT = "init-listen-port";

	/** ������Ϣ�˿��߼��� */
	public static final String RECVMSGPORT = "recv-msg-port";
	
	/** ������Ϣ�˿� */
	public static final int RECVMSGIPPORT = 5077;
	
	/** �����ļ��˿��߼��� */
	public static final String RECVFILEPORT = "recv-file-prots";

	/** �ͻ������� */
	public static final String CLIENTNAME = "client-name";

	/** �ͻ������� */
	public static final String CLIENTIP = "client-ip";
	
	/** ɨ���ǣ���ˢ�»��ǻظ� */
	public static final String SCANMARK = "scanmark";
	
	/** ɨ���ǣ�ˢ�� */
	public static final String SCANREFRESHMARK = "refresh";
	
	/** ɨ���ǣ��ظ� */
	public static final String SCANREPLYMARK = "reply";
	
	/** Э�鿪ʼ��� */
	public static final String PROTOCOLSTARTMARK = "#protocolstart";

	/** Э�������� */
	public static final String PROTOCOLENDMARK = "#protocolend";

	/** ��Ϣ�ָ��� */
	public static final String MSGTOKEN = "#%";
	

	/** ��ʼ��Э�� */
	public static final String INITONLINEPCPROTOCOL = "initonlinepc";

	/** ������ͨ��ϢЭ�� */
	public static final String GENERALMSGPCPROTOCOL = "generalmsg";
	
	/**�����ļ�Э��*/
	public static final String SENDFILEPROTOCOL = "sendfile";
	
	/**ȷ���Ƿ�����ļ�Э��*/
	public static final String ANSWERSENDFILEPROTOCOL = "answersendfile";
	/**ȡ���ļ�����Э��*/
	public static final String CANNELSENDFILEPROTOCOL = "cannelsendfile";
	
	
	/**ȷ���Ƿ�����ļ�����ܾ�*/
	public static final String FSREFUSECOMMAND = "refuse";
	/**ȷ���Ƿ�����ļ����ͬ��*/
	public static final String FSAGREECOMMAND = "agree";
	/**ȷ���Ƿ�����ļ����ͬ��*/
	public static final String FSAGREETOCOMMAND = "agreeto";
	/**ȷ���Ƿ�����ļ�������ж�*/
	public static final String FSINTERRUPTCOMMAND = "interrupt";
	

	/** ������ͨ��ϢЭ�� */
	public static final String IPHEADERMARK = "%$%";
	
	// �����Ƿ�����Ϣͷ
	public static final String HEADFONTCOLOR = "%#col"; // ������ɫ

	public static final String HEADFONTSIZE = "%#siz"; // �����С

	public static final String HEADFONTFAMILY = "%#ftf"; // ������

	public static final String HEADBOLD = "%#bod"; // ����

	public static final String HEADEND = "%#end"; // ��Ϣͷ����

	public static final String FACEMARKSTART = "%#sta"; // ͼƬ��ʼ

	public static final String FACEMARKEND = "%#ind"; // ͼƬ����
	
	/**ɨ�����ʼIP*/
	public static final String SCANSTARTIP = "startip"; 
	
	/**ɨ�����ֹIP*/
	public static final String SCANENDTIP = "endip";
	

	
	/**�����ļ���*/
	public static final String SENDFILENAME = "#%fname%#";
	/**�ļ�����ȷ�϶˿�*/
	public static final String FILESERVERPORT = "#%port%#";
	/**�ļ���С*/
	public static final String FILESIZE = "#%fsize%#";
	/**�ļ���С*/
	public static final String FILECOMMAND = "#%command%#";
	
	/**��Ϣ�Ѿ��ı�*/
	public static final String MSGCHANGED = "msgchange";
	
	/**�����ļ�*/
	public static final String MSGSENDFILE = "msgsendfile";
	
	public static final String LISTENER_SERVER = "listener-msg-ip";
	public static final String LISTENER_SERVER_PORT = "listener-msg-port";
	
	public static final String MSGFILETOKEN = "basdffdsab";
	public static final String SELFMSGFILEMARK = "@@@";
	
	// ��屳��ɫ
	public static final Color BKCOLOR = new Color(215, 233, 251);
	

}
