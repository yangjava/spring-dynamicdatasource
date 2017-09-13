//package com.spring.dynamicdatasource;
//
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//
//public class DynamicDataSourceTransactionManager extends
//		DataSourceTransactionManager {
//
//	private static final long serialVersionUID = 7160082287881717832L;
//
//	/** 基于spring配置的容器事务
//读写事务到主库
//只读事务到从库
//如果没有配置事务，更新语句全部到主库，查询语句均衡到从库
//	 * 只读事务到从库
//	 * 读写事务到主库
//	 */
//	@Override
//	protected void doBegin(Object transaction, TransactionDefinition definition) {
//		System.out.println(definition.getName());
//		boolean readOnly = definition.isReadOnly();
//		if(readOnly){
//			DataSourceHolder.setSlave();
//		}else{
//			DataSourceHolder.setMaster();
//		}		
//		super.doBegin(transaction, definition);
//	}
//	
//	/**
//	 * 清理本地线程的数据源
//	 */
//	@Override
//	protected void doCleanupAfterCompletion(Object transaction) {
//		super.doCleanupAfterCompletion(transaction);
//		DataSourceHolder.clearDataSource();
//	}
//}
