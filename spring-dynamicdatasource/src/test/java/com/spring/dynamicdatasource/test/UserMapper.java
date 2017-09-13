package com.spring.dynamicdatasource.test;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

public interface UserMapper {
	
	List<User> findAllUser();
	
	List<User> selectMyPage(RowBounds rowBounds);
	
	int insert(User user);
}
