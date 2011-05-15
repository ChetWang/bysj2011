package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HyperLinkManager {

	private static HyperLinkManager manager = new HyperLinkManager();

	private Map map = new HashMap();

	private HyperLinkManager() {
	}

	public static HyperLinkManager getInstance() {
		return manager;
	}

	/**
	 * 添加一组互斥的超连接
	 * 
	 * @param key
	 * @param list
	 */
	public void addHyperLinks(String key, ArrayList list) {
		map.remove(key);
		map.put(key, list);
	}

	/**
	 * 添加一个链接
	 * 
	 * @param key
	 * @param link
	 */
	public void addHyperLink(String key, HyperLink link) {
		if (map.containsKey(key)) {
			ArrayList list = (ArrayList) map.get(key);
			list.add(link);
		} else {
			ArrayList list = new ArrayList();
			list.add(link);
			addHyperLinks(key, list);
		}
	}

	/**
	 * 移除所有超连接
	 * 
	 * @param key
	 */
	public void removeHyperLinks(final String key) {

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}

				ArrayList list = (ArrayList) map.get(key);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						HyperLink link = (HyperLink) list.get(i);
						if (link != null) {
							link.clearHyperLink();
						}
					}
				}

			}
		});

		thread.start();

	}

}
