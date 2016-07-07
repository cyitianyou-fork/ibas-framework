# ibas-framework
这里是一个业务系统框架，萌萌哒。

以下是备忘录的了。
001. BO使用基本类型，无法定义泛型属性。
002. IBOLine保存时获取主键，每次都从数据库获取，因为树形对象时没办法单层确认主键值。
003. 序列化小数时，默认只保留6位。
004. jersey的json使用moxy，可解决jackson的侵入问题，此支持xml和json。
005. 字段关联设置，仅支持关联到对象或对象数组，例如：IBusinessObjectBase或IBusinessObjectBase[]
006. Decimal的构造方法，如果使用double类型，则仅保留9位小数。
007. Decimal持久化（保存数据库，序列化）时仅保留6位小数。
008. expressions命名空间，用于处理类似where条件的表达式，如：ItemCode = "A0001" and CardCode = "C0001"
009. 网站启动后的路径设置tomcat的server.xml
010. tomcat运行不起来，检查maven库是否添加引用，引用的项目是否添加。
011. 所有的路径不带结尾的文件夹分隔符（\），如：D:\workspace||/home/workspace