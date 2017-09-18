# spring-dynamicdatasource

  数据库层面大都采用读写分离技术，就是一个Master数据库，多个Slave数据库。
  
  Master库负责数据更新和实时数据查询，Slave库当然负责非实时数据查询。
  
因为在实际的应用中，数据库都是读多写少（读取数据的频率高，更新数据的频率相对较少），而读取数据通常耗时比较长，占用数据库服务器的CPU较多，从而影响用户体验。我们通常的做法就是把查询从主库中抽取出来，采用多个从库，使用负载均衡，减轻每个从库的查询压力。

　　采用读写分离技术的目标：有效减轻Master库的压力，又可以把用户查询数据的请求分发到不同的Slave库，从而保证系统的健壮性。我们看下采用读写分离的背景。

　　随着网站的业务不断扩展，数据不断增加，用户越来越多，数据库的压力也就越来越大，采用传统的方式，比如：数据库或者SQL的优化基本已达不到要求，这个时候可以采用读写分离的策 略来改变现状。


我们最常用的方式，就是定义2个数据库连接，一个是MasterDataSource,另一个是SlaveDataSource。更新数据时我们读取MasterDataSource，查询数据时我们读取SlaveDataSource

1.定义数据库
	<!-- C3P0连接池配置 -->
	<bean id="master" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass">
			<value>com.mysql.jdbc.Driver</value>
		</property>
		<property name="jdbcUrl">
			<value>jdbc:mysql://localhost:3306/mybatistest?useSSL=false</value>
		</property>
		<property name="user">
			<value>root</value>
		</property>
		<property name="password">
			<value>root</value>
		</property>
		<property name="initialPoolSize">
			<value>20</value>
		</property>
		<property name="minPoolSize">
			<value>20</value>
		</property>
		<property name="maxPoolSize">
			<value>200</value>
		</property>
		<property name="maxIdleTime">
			<value>255000</value>
		</property>
	</bean>
	
	<bean id="dataSource2" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass">
			<value>com.mysql.jdbc.Driver</value>
		</property>
		<property name="jdbcUrl">
			<value>jdbc:mysql://localhost:3306/mybatistest2?useSSL=false</value>
		</property>
		<property name="user">
			<value>root</value>
		</property>
		<property name="password">
			<value>root</value>
		</property>
		<property name="initialPoolSize">
			<value>20</value>
		</property>
		<property name="minPoolSize">
			<value>20</value>
		</property>
		<property name="maxPoolSize">
			<value>200</value>
		</property>
		<property name="maxIdleTime">
			<value>255000</value>
		</property>
	</bean>
	

	
	<bean id="dataSource" class="com.spring.dynamicdatasource.DynamicDataSource">
 		<property name="master" ref="master" />		
		<property name="slaves">
			<list>
				<ref bean="dataSource2"/>
			</list>		
				
		</property>
	</bean>
  
  
  
  
  2.借助spring的【org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource】这个抽象类实现，
  看名字可以了解到是一个路由数据源的东西
  protected abstract Object determineCurrentLookupKey(); 
  由于这个方法没有入参，并且是spring自动调用的，因此考虑使用静态变量存储dataSource的key，在调用sql语句前设置静态变量的值，然后在这个方法中得到静态变量的值，返回。又考虑到多线程，同时可能会有很多请求，为避免线程之间相互干扰，考虑使用threadLocal。
  
  3.根据数据配置动态获取主库从库
  	<!-- 定义单个jdbc数据源的事务管理器 -->
	<bean id="transactionManager"
		class="com.spring.dynamicdatasource.DynamicDataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
  4.配置mybaits插件
  
      <plugins>
    <plugin interceptor="com.spring.dynamicdatasource.DynamicPlugin"></plugin>
    </plugins>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
