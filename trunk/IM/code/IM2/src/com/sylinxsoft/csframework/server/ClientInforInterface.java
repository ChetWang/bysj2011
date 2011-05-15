package com.sylinxsoft.csframework.server;

import java.io.Serializable;

/**
 * 定义客户端相关信息的接口,当新进来的MSK初始化时，将信息发送到监
 * 听服务器中，并创建实现该接口的具体类
 * @author Administrator
 * 2007-9-28
 * ClientInforInterface.java
 */
public interface ClientInforInterface {
	
	/**设置客户端指定的信息*/
	public void setInfor(Object key, Object value);

	/**获得客户端指定的信息*/
	public Object getInfor(Object key);

}
