package com.sylinxsoft.csframework.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * �������Ŀͻ���,������ʹ�õ�java�Դ��Ĺ۲���ģʽ�������¿ͻ����������� ����ɾ��һ���ͻ���ʱ���ÿͻ��˹�������Ϊ���۲��߶�������֪ͨ���й۲��ߣ�
 * ��˹۲��ߵ��Ը��£��۲�����Ҫʵ��Interface Observer
 * <p>
 * 
 * @author Administrator 2007-9-28 ClientGroupManager.java
 */
public class ClientGroupManager extends Observable {

	private static ClientGroupManager client = new ClientGroupManager();;

	/** �洢�ͻ����б� */
	private List<ClientInforInterface> clientList = Collections
			.synchronizedList(new LinkedList<ClientInforInterface>());

	private ClientGroupManager() {

	}

	public static ClientGroupManager getInstance() {
		return client;
	}

	/**
	 * ͨ�����ƻ�ÿͻ���
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
	 * ͨ��ip��ÿͻ���
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
	 * ���һ���¿ͻ���
	 * 
	 * @param client
	 * @return ������ͬ�ͻ����Ѿ����ϣ�����false,���򷵻�true;
	 */
	public boolean addClient(ClientInforInterface client) {
		if (client == null) {
			return false;
		}
		// System.out.print(clientList.size());
		// ��ͬ��IPֻ����һ��
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getInfor(AllConstatns.CLIENTIP).equals(
					client.getInfor(AllConstatns.CLIENTIP))) {
				return false;
			}
		}
		clientList.add(client);

		// ��Ǳ��۲���״̬�Ѿ��ı�
		setChanged();
		// ֪ͨ�۲���
		notifyObservers();

		return true;
	}

	/**
	 * �Ƿ��Ѱ���ĳ���ͻ���
	 * 
	 * @param client
	 * @return
	 */
	public boolean contains(ClientInforInterface client) {
		if (client == null) {
			return false;
		}
		// System.out.print(clientList.size());
		// ��ͬ��IPֻ����һ��
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getInfor(AllConstatns.CLIENTIP).equals(
					client.getInfor(AllConstatns.CLIENTIP))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ͨ���ͻ��������Ƴ��ͻ���
	 * 
	 * @param clientName
	 *            �ͻ�������
	 */
	public void removeClientByName(String clientName) {
		// ����һ�飬�Ƴ�������
		for (int i = 0; i < clientList.size(); i++) {
			if (clientName.equals(((ClientInforInterface) (clientList.get(i)))
					.getInfor(AllConstatns.CLIENTNAME))) {
				clientList.remove(i);
				// ��Ǳ��۲���״̬�Ѿ��ı�
				setChanged();
				// ֪ͨ�۲���
				notifyObservers();
			}
		}
	}

	/**
	 * ͨ��ip�Ƴ��ͻ���
	 * 
	 * @param clientIp
	 *            �ͻ���ip
	 */
	public void removeClientByIp(String clientIp) {

		for (int i = 0; i < clientList.size(); i++) {
			if (clientIp.equals(((ClientInforInterface) (clientList.get(i)))
					.getInfor(AllConstatns.CLIENTIP))) {
				clientList.remove(i);
				// ��Ǳ��۲���״̬�Ѿ��ı�
				setChanged();
				// ֪ͨ�۲���
				notifyObservers();
			}
		}
	}

	/**
	 * ������пͻ���
	 * 
	 */
	public void clearAllClient() {
		clientList.clear();
		// ��Ǳ��۲���״̬�Ѿ��ı�
		setChanged();
		// ֪ͨ�۲���
		notifyObservers();

	}

	/**
	 * ����һ���������пͻ��˵�iterator
	 * 
	 * @return
	 */
	public Iterator clients() {
		return clientList.iterator();
	}

}
