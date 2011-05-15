package com.sylinxsoft.csframework.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import com.sylinxsoft.csframework.ConfigSet;

public class ClientConfig {
	/** ����Ƿ���� */
	private boolean isError = false;

	/** ������Ϣ */
	private String error = new String();

	/** �洢������Ϣ */
	private Map<Object, Object> map = new HashMap<Object, Object>();

	/** ���汻ʵ�������� */
	private static ClientConfig config = null;

	/** ��ȡ������Ϣ�����ﱣֻ֤ʵ����һ�� */
	public synchronized static ClientConfig getConfig() {
		if (config == null) {
			config = new ClientConfig();
		}
		return config;
	}

	/**
	 * ˽�й��캯�������ܱ����ʵ����
	 * 
	 */
	private ClientConfig() {

		try {

			InputStream in = this.getClass().getResourceAsStream(
					"/conf/client.xml");

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			Element rootElement = document.getDocumentElement();

			NodeList driverElements = rootElement
					.getElementsByTagName("client");

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
		// ���������¿ͻ�������
		try {
			if (null == ConfigSet.getInstance().getProperty("client-name")) {
				InetAddress localIp = InetAddress.getLocalHost();
				String cpName = localIp.getHostName();
				// ��ֹû����������,������IP
				if (null == cpName || "".equals(cpName.trim())) {
					cpName = localIp.getHostAddress();
				}
				map.put("client-name", cpName);
			} else {
				map.put("client-name", ConfigSet.getInstance().getProperty(
						"client-name"));
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		ClientConfig config = ClientConfig.getConfig();
		System.out.println(config.get(AllConstatns.CLIENTNAME));

	}
}
