

&lt;hr /&gt;


<b>2012 毕业设计，共分4个系统</b>

针对2012毕设的开发工具，已经有一个Windows下<a href='http://pan.baidu.com/share/link?shareid=142106&uk=2852395859'>Eclipse3.7的集成包（eclipse3.7+svn+gwt/smartgwt）</a>，请下载直接解压到D:，eclipse的workspace使用D:\JavaDevelopment\bysjworkspace

a、基于SmartGWT的中小企业CRM系统的设计与实现

b、基于SmartGWT的中小企业绩效管理系统的设计与实现

c、基于SmartGWT的中小企业资产管理系统的设计与实现

d、基于Apache James的中小企业邮件营销系统的设计与实现



&lt;hr /&gt;


<p>2011 毕业设计，共分4个系统</p>

a、基于GWT技术的进销存系统

b、基于GWT技术的订单系统

c、基于Swing的IM系统

d、基于MQ\Web Service的统一消息平台


内容上相互独立，具体业务是统一的，以“鲜花配送”为场景。
系统设计时必须考虑业务需求！！！



&lt;hr /&gt;



<b>前期准备工作：</b>

文档管理使用svn，因此必须使用svn客户端进行操作！

Windows下推荐使用tortoisesvn，下载地址：http://tortoisesvn.net/downloads , 根据自己平台选择，2012的集成安装包下的Eclipse已经有svn插件。

JDK（Java Development Toolkit，java开发工具包），所有java开发过程都需要这个最基本的工具集合，去http://www.oracle.com/technetwork/java/javase/downloads/index.html, 选择最新的JDK下载并安装（本页<a href='http://code.google.com/p/bysj2011/downloads/list'>Downloads</a>有提供Windows 32位安装程序）。

集成开发环境IDE（使用已提供的集成包开发的请忽略此项），目前免费优秀的开发环境很多（Netbeans，IDEA，Eclipse...），凭个人喜好，但不建议使用破解的商业版本。
GWT开发目前最好的环境是Eclipse，Swing开发最好的环境是Netbeans（虽然Eclipse也有google优秀的Swing开发插件），IDEA中规中矩，有些“贵族”成分。Eclipse下载地址：http://www.eclipse.org/downloads/, 建议选择“Eclipse IDE for Java EE Developers”，根据操作系统位数选择32位还是64位。Netbeans下载地址http://netbeans.org/downloads/index.html, 选择最基本的Java SE版本就行。其它后台程序开发，Eclipse和Netbeans没什么区别。

GWT开发需要安装GWT工具包，可以一次性通过Google plugin完成，地址是http://code.google.com/intl/zh-CN/webtoolkit/download.html, 深入开发的话，建议下载SDK，Plugin for eclipse, GWT Designer（与Netbeans开发Swing一样，拖拖拽拽就行）

Netbeans本身支持SVN。

Eclipse需要额外下载svn插件，参见http://subclipse.tigris.org/, 通过update manager就可以实现安装。如果无法在线update，则可以下载zip包(本页<a href='http://code.google.com/p/bysj2011/downloads/list'>Downloads</a>有提供)，通过archive升级或者直接解压到eclipse的dropins目录下。具体格式请网上查阅。







---


<b>每届都有QQ讨论组</b>


---


<b>参考材料：</b>

基础：Effective Java、Thinking in Java（Java编程思想）、Java核心技术。至少要有此类Java基础丛书中的一本。

高级：

Swing----推荐阅读“Java动画、图形和极富客户端效果开发(Filthy Rich Clients)”

MQ\Web Service----基础是网络编程，所以推荐“Java网络编程 O’Reilly Java系列 ”

GWT----新兴的技术，目前已有书籍内容远跟不上最新GWT版本，最好的实践和学习的地方是Internet，GWT是多种技术的集合体（Java、HTML、JavaScript），但Google的GWT能把这些技术集成得很好，学习门槛一点也不高。

SmartGWT----GWT技术的扩展，<a href='http://www.smartclient.com'>smartclient</a>公司的产品，有开源版本和商业版本，开源版本参考<a href='http://code.google.com/p/smartgwt/'>SmartGWT on Google Code</a>，在线完整参考示例<a href='http://www.smartclient.com/smartgwt/showcase/'>SmartGwt ShowCase</a>

更细致的代码参考，<b>强烈推荐</b>去<a href='http://www.java2s.com'><a href='http://www.java2s.com'>http://www.java2s.com</a></a>查阅。





---


<b>当前库中的所有文档都遵循Apache2.0协议。</b>