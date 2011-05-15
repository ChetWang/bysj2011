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


public class MsgMapping {
	
	/** ����Ƿ���� */
	private boolean isError = false;

	/** ������Ϣ */
	private String error = new String();

	/** �洢������Ϣ */
	private Map<Object,Object> map = new HashMap<Object,Object>();

	/** ���汻ʵ�������� */
	private static MsgMapping config = null;

	/** ��ȡ������Ϣ�����ﱣֻ֤ʵ����һ�� */
	public synchronized static MsgMapping getConfig() {
		if (config == null) {
			config = new MsgMapping();
		}
		return config;
	}

	/**
	 * ˽�й��캯�������ܱ����ʵ����
	 * 
	 */
	private MsgMapping() {

		File file = new File("conf/MsgProtocol.xml");

		try {

			InputStream in =  this.getClass().getResourceAsStream(
			"/conf/MsgProtocol.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			Element rootElement = document.getDocumentElement();

			NodeList driverElements = rootElement
					.getElementsByTagName("protocol");

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
				// ������ֻ�򵥵ı��浽MAP��
				Node textNode = child.getChildNodes().item(0);
				if (textNode != null) {
					map.put(nodeName, textNode.getNodeValue());
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
		return map.get(which);
	}

	public static void main(String[] args) {
		MsgMapping config = MsgMapping.getConfig();
		System.out.println(config.get(AllConstatns.INITONLINEPCPROTOCOL));

	}
}
