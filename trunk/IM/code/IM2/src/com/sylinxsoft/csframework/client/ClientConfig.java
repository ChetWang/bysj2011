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
	/** 标记是否出错 */
	private boolean isError = false;

	/** 出错信息 */
	private String error = new String();

	/** 存储配置信息 */
	private Map<Object, Object> map = new HashMap<Object, Object>();

	/** 保存被实例的引用 */
	private static ClientConfig config = null;

	/** 获取配置信息，这里保证只实例化一次 */
	public synchronized static ClientConfig getConfig() {
		if (config == null) {
			config = new ClientConfig();
		}
		return config;
	}

	/**
	 * 私有构造函数，不能被外界实例化
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

			// 这里只读一条配置信息，若文件中有多条，只取第一条
			if (driverElements.getLength() > 0) {
				Node node = driverElements.item(0);
				createNode(node);
				return;
			}

		} catch (FileNotFoundException fnfe) {
			isError = true;
			error = "conf目录下的server.xml配置文件找不到！";
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

	/** 依次提取各结点的信息保存起来 */
	private void createNode(Node node) {

		NodeList children = node.getChildNodes();

		// 读出每个子结点，并保存到MAP中
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			String nodeName = child.getNodeName();
			if (child instanceof Element) {
				// 否则将其只简单的保存到MAP中
				Node textNode = child.getChildNodes().item(0);
				if (textNode != null) {
					map.put(nodeName, textNode.getNodeValue());
				}
			}
		}
		// 重新设置下客户端名称
		try {
			if (null == ConfigSet.getInstance().getProperty("client-name")) {
				InetAddress localIp = InetAddress.getLocalHost();
				String cpName = localIp.getHostName();
				// 防止没有主机名的,就用其IP
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

	/** 判断是否出错 */
	public boolean isError() {
		return isError;
	}

	/** 获得错误 */
	public String getError() {
		return error;
	}

	/** 返回给定字段的配置信息 */
	public Object get(String which) {
		return map.get(which);
	}

	public static void main(String[] args) {
		ClientConfig config = ClientConfig.getConfig();
		System.out.println(config.get(AllConstatns.CLIENTNAME));

	}
}
