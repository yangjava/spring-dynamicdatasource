package com.spring.dynamicdatasource.test;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userMapper")
public class UserMapperImpl implements UserMapper{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Transactional(readOnly=true)
	public List<User> findAllUser() {
		/*int a=1/0;*/
		return sqlSessionTemplate.selectList("findAllUser");
	}

	public List<User> selectMyPage(RowBounds rowBounds) {
		return null;
	}

	public int insert(User user) {
		return sqlSessionTemplate.insert("insert", user);
	}

}
