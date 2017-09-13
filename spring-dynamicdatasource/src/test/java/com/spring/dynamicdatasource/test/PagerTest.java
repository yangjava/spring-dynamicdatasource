package com.spring.dynamicdatasource.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PagerTest {
	// @Test
	// public void testAll() throws Exception{
	// // mybatis配置文件
	// String resource = "SqlMapConfig.xml";
	// // 得到配置文件流
	// InputStream inputStream = Resources.getResourceAsStream(resource);
	// //创建会话工厂，传入mybatis配置文件的信息
	// SqlSessionFactory sqlSessionFactory = new
	// SqlSessionFactoryBuilder().build(inputStream);
	//
	// // 通过工厂得到SqlSession
	// SqlSession sqlSession = sqlSessionFactory.openSession();
	// //通过Mapper 加载SQL信息
	// UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
	// List<User> user=userMapper.findAllUser();
	// System.out.println(user.size());
	// // 释放资源
	// sqlSession.close();
	//
	// }

//	@Test
//	public void testAllPager() throws Exception {
//		// mybatis配置文件
//		String resource = "SqlMapConfig.xml";
//		// 得到配置文件流
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		// 创建会话工厂，传入mybatis配置文件的信息
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
//				.build(inputStream);
//
//		// 通过工厂得到SqlSession
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		// 通过Mapper 加载SQL信息
//		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//		List<User> user = userMapper.findAllUser();
//		// 释放资源
//		sqlSession.close();
//
//	}

	UserMapper userMapper;

	@Before
	public void before() {
		String[] xmls = new String[] { "classpath:applicationContext.xml",
				"classpath:dataSource.xml",
				"classpath:applicationContext-tx.xml" };
		// String[] xmls = new String[]{
		// "classpath:applicationContext.xml","classpath:dataSource.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
//		DynamicSqlSessionTemplate dynamicSqlSessionTemplate=(DynamicSqlSessionTemplate)context.getBean("dynamicSqlSessionTemplate");
		SqlSessionTemplate bean = (SqlSessionTemplate)context.getBean("sqlSessionTemplate");
		userMapper = (UserMapper) context.getBean("userMapper");
		System.out.println(userMapper);
		
	}

	@Test
	public void testUserMapper() throws Throwable {
		User user = new User();
		user.setId(2);
		user.setUsername("yang");
		userMapper.insert(user);
	}

	@Test
	public void testSelectByUserNameAndPwd() throws Throwable {
		List<User> findAllUser = userMapper.findAllUser();
		System.out.println(findAllUser.size());
	}
}
