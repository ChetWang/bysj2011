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
 * 存储管理服务器配置信息，该信息保存在conf文件夹下的server.xml中，包括接收消息端口，监听端口，接收文件端口等 <br>
 * 具体配置信息请参考server.xml里面的说明
 * <p>
 * 在这里使用单例模式，确保只被实例化一次。 这里对文件传输端口特别管理
 * 
 * @author Administrator 2007-9-28 ServerConfig.java
 */
public class ServerConfig {

	/** 标记是否出错 */
	private boolean isError = false;

	/** 最多可同时接受多少文件 */
	private final int MAXPORT = 20;

	/** 端口计数 */
	private int MAXPORTNUMBER = 0;

	/** 出错信息 */
	private String error = new String();

	/** 存储配置信息 */
	private Map<Object, Object> map = new HashMap<Object, Object>();

	/** 存储已经被使用的端口 */
	private Map<Object, Object> usedMap = new HashMap<Object, Object>();

	/** 端口列表 */
	private int[] ports = new int[MAXPORT];

	/** 保存被实例的引用 */
	private static ServerConfig config = null;

	/** 获取配置信息，这里保证只实例化一次 */
	public synchronized static ServerConfig getConfig() {
		if (config == null) {
			config = new ServerConfig();
		}
		return config;
	}

	/**
	 * 私有构造函数，不能被外界实例化
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

				// 如果是接受文件端口，特别处理
				if (nodeName.equals(AllConstatns.RECVFILEPORT)) {
					// 获得允许传输文件端口
					// 将其依次保存起来
					for (int j = 0; j < child.getChildNodes().getLength(); j++) {
						Node childPort = child.getChildNodes().item(j);
						if (childPort instanceof Element && childPort != null) {
							Node textChildPort = childPort.getChildNodes()
									.item(0);
							// 保存在port数组中
							ports[MAXPORTNUMBER] = Integer
									.parseInt(textChildPort.getNodeValue());
							MAXPORTNUMBER++;
						}
					}
				} else {
					// 否则将其只简单的保存到MAP中
					Node textNode = child.getChildNodes().item(0);
					if (textNode != null) {
						map.put(nodeName, textNode.getNodeValue());
						// System.out.println(nodeName);
					}
				}

			}
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

	/** 使用了某个端口 */
	private void usePort(int port) {
		usedMap.put(port, "used");
	}

	/** 使用完后释放了某个端口,这里用来简单的管理接收文件端口的使用 */
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
