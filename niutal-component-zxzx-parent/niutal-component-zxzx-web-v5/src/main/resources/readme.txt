当前版本：1.3.18.1-SNAPSHOT

1.	组件导入：
		<dependency>
			<groupId>${project.groupId}</groupId>
			<artifactId>niutal-component-zxzx-controller</artifactId>
			<version>1.3.18.1-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>${project.groupId}</groupId>
			<artifactId>niutal-component-zxzx-web-v5</artifactId>
			<version>1.3.18.1-SNAPSHOT</version>
			<type>war</type>
		</dependency>
		
		配置maven-war-plugin插件，加入：
		<overlay>
			<groupId>${project.groupId}</groupId>
			<artifactId>niutal-component-zxzx-web-v5</artifactId>
		</overlay>
		
2.	数据库初始化脚本
	zxzx-sql.zip
	
3.	实现 com.woshidaniu.zxzx.auth.ZxzxAuthStateChecker接口
  	配置该实现类到 config-zxzx.xml配置文件中
