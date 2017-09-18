package com.spring.dynamicdatasource;


public class DynamicDataSourceHolder {
	
	private static final ThreadLocal<DataSourceRW> holder = new ThreadLocal<DataSourceRW>();

    private DynamicDataSourceHolder() {
    	
    }

    public static void putDataSource(DataSourceRW dataSource){
        holder.set(dataSource);
    }

    public static DataSourceRW getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }
}
