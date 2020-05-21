# ev-gb-gateway 基于GB32960协议数据接入网关

#支持百万车辆以上!核心链路弹性运维!建议K8S部署!
#网关安全:专网
#可以将Redis订阅发布换成MQ广播

ev-gb-gateway-server

	TSP数据接入网关
	808协议的压测简介:16C32G-IO密集型机器实测单节点TPS稳定高达50000/s
    GB32960的压测待验证！

ev-gb-gateway-tcu

    模拟Tbox终端与TSP联调测试,提供RestFul接口给测试人员使用,降低测试成本。提供实时在线监控车辆功能
	
ev-gb-gateway-exe

    Java实现windwos桌面程序,测试人员通过gb32960.exe即可快速进行报文解析与编码

ev-gb-gateway-dispatcher

	将接入数据标准封装成业务对象分发到不同Topic

ev-gb-gateway-protocol

	功能:GB32960协议抽象封装
	
ev-gb-gateway-document

	功能:文档


