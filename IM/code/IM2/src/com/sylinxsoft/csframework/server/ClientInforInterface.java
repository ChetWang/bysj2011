package com.sylinxsoft.csframework.server;

import java.io.Serializable;

/**
 * ����ͻ��������Ϣ�Ľӿ�,���½�����MSK��ʼ��ʱ������Ϣ���͵���
 * ���������У�������ʵ�ָýӿڵľ�����
 * @author Administrator
 * 2007-9-28
 * ClientInforInterface.java
 */
public interface ClientInforInterface {
	
	/**���ÿͻ���ָ������Ϣ*/
	public void setInfor(Object key, Object value);

	/**��ÿͻ���ָ������Ϣ*/
	public Object getInfor(Object key);

}
