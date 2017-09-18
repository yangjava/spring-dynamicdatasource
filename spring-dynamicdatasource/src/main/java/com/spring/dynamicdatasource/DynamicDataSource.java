package com.spring.dynamicdatasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;




public class DynamicDataSource extends AbstractRoutingDataSource{
	
	// master数据库的连接
	private DataSource master;
	
	private List<DataSource> slaves;

	public DataSource getMaster() {
		return master;
	}

	public void setMaster(DataSource master) {
		this.master = master;
	}

	public List<DataSource> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<DataSource> slaves) {
		this.slaves = slaves;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		
		DataSourceRW datasource = DynamicDataSourceHolder.getDataSource();

	        if(datasource == null
	                || datasource == DataSourceRW.WRITE) {
	            return DataSourceRW.WRITE.name();
	        }

	        return DataSourceRW.READ.name();
		
	}
	
    @Override
    public void afterPropertiesSet() {
        if (this.master == null) {
            throw new IllegalArgumentException("Property 'master' cannot Null ");
        }
        setDefaultTargetDataSource(master);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceRW.WRITE.name(), master);
        if(slaves != null) {
        	//去从库第一个数据库连接 
        	//TODO 可以配置 轮训 负载等策略
            targetDataSources.put(DataSourceRW.READ.name(), slaves.get(0));
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
	
}
