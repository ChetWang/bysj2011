package com.sylinxsoft.csframework.server;

import java.util.HashMap;
import java.util.Map;

public class ClientInfor implements ClientInforInterface {

	private Map<Object,Object> map = new HashMap<Object,Object>();
	
	
	public Object getInfor(Object key) {
		return map.get(key);
	}

	
	
	public void setInfor(Object key, Object value) {
		if (!map.containsKey(key)) {
			map.put(key, value);
		}
	}

}
