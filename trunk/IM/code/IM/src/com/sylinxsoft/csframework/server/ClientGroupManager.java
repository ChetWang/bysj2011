package com.sylinxsoft.csframework.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * 管理互连的客户端,在这里使用到java自带的观察者模式，当有新客户端连接上来 或者删除一个客户端时，该客户端管理器作为被观察者对象将主动通知所有观察者，
 * 因此观察者得以更新，观察这需要实现Interface Observer
 * <p>
 * 
 * @author Administrator 2007-9-28 ClientGroupManager.java
 */
public class ClientGroupManager extends Observable {

	private static ClientGroupManager client = new ClientGroupManager();;

	/** 存储客户端列表 */
	private List<ClientInforInterface> clientList = Collections
			.synchronizedList(new LinkedList<ClientInforInterface>());

	private ClientGroupManager() {

	}

	public static ClientGroupManager getInstance() {
		return client;
	}

	/**
	 * 通过名称获得客户端
	 * 
	 * @param clientName
	 * @return
	 */
	public ClientInforInterface getClientByName(String clientName) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientName.equals(((ClientInforInterface) (clientList.get(i)))
					.getInfor(AllConstatns.CLIENTNAME))) {
				return (ClientInforInterface) (clientList.get(i));
			}
		}
		return null;
	}

	/**
	 * 通过ip获得客户端
	 * 
	 * @param clientIp
	 * @return
	 */
	public ClientInforInterface getClientByIp(String clientIp) {

		for (int i = 0; i < clientList.size(); i++) {
			if (clientIp.equals(((ClientInforInterface) (clientList.get(i)))
					.getInfor(AllConstatns.CLIENTIP))) {
				return (ClientInforInterface) (clientList.get(i));
			}
		}
		return null;
	}

	/**
	 * 添加一个新客户端
	 * 
	 * @param client
	 * @return 当有相同客户端已经加上，返回false,否则返回true;
	 */
	public boolean addClient(ClientInforInterface client) {
		if (client == null) {
			return false;
		}
		// System.out.print(clientList.size());
		// 相同的IP只加入一次
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getInfor(AllConstatns.CLIENTIP).equals(
					client.getInfor(AllConstatns.CLIENTIP))) {
				return false;
			}
		}
		clientList.add(client);

		// 标记被观察者状态已经改变
		setChanged();
		// 通知观察者
		notifyObservers();

		return true;
	}

	/**
	 * 是否已包含某个客户端
	 * 
	 * @param client
	 * @return
	 */
	public boolean contains(ClientInforInterface client) {
		if (client == null) {
			return false;
		}
		// System.out.print(clientList.size());
		// 相同的IP只加入一次
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getInfor(AllConstatns.CLIENTIP).equals(
					client.getInfor(AllConstatns.CLIENTIP))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过客户端名字移除客户端
	 * 
	 * @param clientName
	 *            客户端名称
	 */
	public void removeClientByName(String clientName) {
		// 遍历一遍，移除更彻底
		for (int i = 0; i < clientList.size(); i++) {
			if (clientName.equals(((ClientInforInterface) (clientList.get(i)))
					.getInfor(AllConstatns.CLIENTNAME))) {
				clientList.remove(i);
				// 标记被观察者状态已经改变
				setChanged();
				// 通知观察者
				notifyObservers();
			}
		}
	}

	/**
	 * 通过ip移除客户端
	 * 
	 * @param clientIp
	 *            客户端ip
	 */
	public void removeClientByIp(String clientIp) {

		for (int i = 0; i < clientList.size(); i++) {
			if (clientIp.equals(((ClientInforInterface) (clientList.get(i)))
					.getInfor(AllConstatns.CLIENTIP))) {
				clientList.remove(i);
				// 标记被观察者状态已经改变
				setChanged();
				// 通知观察者
				notifyObservers();
			}
		}
	}

	/**
	 * 清除所有客户端
	 * 
	 */
	public void clearAllClient() {
		clientList.clear();
		// 标记被观察者状态已经改变
		setChanged();
		// 通知观察者
		notifyObservers();

	}

	/**
	 * 返回一个包含所有客户端的iterator
	 * 
	 * @return
	 */
	public Iterator clients() {
		return clientList.iterator();
	}

}
