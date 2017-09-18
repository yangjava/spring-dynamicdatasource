package com.spring.dynamicdatasource;

import java.util.List;

import javax.sql.DataSource;

public interface DataSourceStrategy {

	
	public DataSource getDataSource(List<DataSource> DataSourceList);
	
}
