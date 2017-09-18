package com.spring.dynamicdatasource;

import java.util.List;

import javax.sql.DataSource;

public class FirstDataSourceStrategy implements DataSourceStrategy{

	@Override
	public DataSource getDataSource(List<DataSource> DataSourceList) {
		if(DataSourceList.size()>=1){
			return DataSourceList.get(0);
		}else{
		throw new RuntimeException("DataSourceList size must > =1 ");
		}
	}

}
