package com.sylinxsoft.csframework.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * �洢���������������Ϣ������Ϣ������conf�ļ����µ�server.xml�У�����������Ϣ�˿ڣ������˿ڣ������ļ��˿ڵ� <br>
 * ����������Ϣ��ο�server.xml�����˵��
 * <p>
 * ������ʹ�õ���ģʽ��ȷ��ֻ��ʵ����һ�Ρ� ������ļ�����˿��ر����
 * 
 * @author Administrator 2007-9-28 ServerConfig.java
 */
public class ServerConfig {

	/** ����Ƿ���� */
	private boolean isError = false;

	/** ����ͬʱ���ܶ����ļ� */
	private final int MAXPORT = 20;

	/** �˿ڼ��� */
	private int MAXPORTNUMBER = 0;

	/** ������Ϣ */
	private String error = new String();

	/** �洢������Ϣ */
	private Map<Object, Object> map = new HashMap<Object, Object>();

	/** �洢�Ѿ���ʹ�õĶ˿� */
	private Map<Object, Object> usedMap = new HashMap<Object, Object>();

	/** �˿��б� */
	private int[] ports = new int[MAXPORT];

	/** ���汻ʵ�������� */
	private static ServerConfig config = null;

	/** ��ȡ������Ϣ�����ﱣֻ֤ʵ����һ�� */
	public synchronized static ServerConfig getConfig() {
		if (config == null) {
			config = new ServerConfig();
		}
		return config;
	}

	/**
	 * ˽�й��캯�������ܱ����ʵ����
	 * 
	 */
	private ServerConfig() {

		try {
			InputStream in = this.getClass().getResourceAsStream(
					"/conf/server.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			Element rootElement = document.getDocumentElement();

			NodeList driverElements = rootElement
					.getElementsByTagName("server");

			// ����ֻ��һ��������Ϣ�����ļ����ж�����ֻȡ��һ��
			if (driverElements.getLength() > 0) {
				Node node = driverElements.item(0);
				createNode(node);
				return;
			}
		} catch (FileNotFoundException fnfe) {
			isError = true;
			error = "confĿ¼�µ�server.xml�����ļ��Ҳ�����";
			System.out.println(error);
			return;
		} catch (IOException ioe) {
			isError = true;
			error = "Problem reading file: " + ioe;
			System.out.println(error);
			return;
		} catch (ParserConfigurationException pce) {
			isError = true;
			error = "Can't create DocumentBuilder";
			System.out.println(error);
			return;
		} catch (SAXException se) {
			isError = true;
			error = "Problem parsing document: " + se;
			System.out.println(error);

			return;
		}

	}

	/** ������ȡ��������Ϣ�������� */
	private void createNode(Node node) {
		NodeList children = node.getChildNodes();
		// ����ÿ���ӽ�㣬�����浽MAP��
		for (int i = 0; i < children.getLength(); i++) {

			Node child = children.item(i);
			String nodeName = child.getNodeName();

			if (child instanceof Element) {

				// ����ǽ����ļ��˿ڣ��ر���
				if (nodeName.equals(AllConstatns.RECVFILEPORT)) {
					// ����������ļ��˿�
					// �������α�������
					for (int j = 0; j < child.getChildNodes().getLength(); j++) {
						Node childPort = child.getChildNodes().item(j);
						if (childPort instanceof Element && childPort != null) {
							Node textChildPort = childPort.getChildNodes()
									.item(0);
							// ������port������
							ports[MAXPORTNUMBER] = Integer
									.parseInt(textChildPort.getNodeValue());
							MAXPORTNUMBER++;
						}
					}
				} else {
					// ������ֻ�򵥵ı��浽MAP��
					Node textNode = child.getChildNodes().item(0);
					if (textNode != null) {
						map.put(nodeName, textNode.getNodeValue());
						// System.out.println(nodeName);
					}
				}

			}
		}

	}

	/** �ж��Ƿ���� */
	public boolean isError() {
		return isError;
	}

	/** ��ô��� */
	public String getError() {
		return error;
	}

	/** ���ظ����ֶε�������Ϣ */
	public Object get(String which) {
		if (which.equals(AllConstatns.RECVFILEPORT)) {
			for (int i = 0; i < MAXPORTNUMBER; i++) {
				if (!usedMap.containsKey(ports[i])) {
					usePort(ports[i]);
					return new Integer(ports[i]);
				}
			}
			return new Integer(-1);
		}
		return map.get(which);
	}

	/** ʹ����ĳ���˿� */
	private void usePort(int port) {
		usedMap.put(port, "used");
	}

	/** ʹ������ͷ���ĳ���˿�,���������򵥵Ĺ�������ļ��˿ڵ�ʹ�� */
	public void releasePort(int port) {
		usedMap.remove(port);
	}

	public static void main(String[] args) {
		ServerConfig config = ServerConfig.getConfig();
		for (int i = 1; i < 10; i++) {
			if (i == 7) {
				config.releasePort(6004);
			}
			System.out.println(config.get(AllConstatns.RECVFILEPORT));
		}
	}

}
