package com.simi.utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 *
 * @author :hulj
 * @Date : 2016年4月22日下午4:26:36
 * @Description: TODO
 *
 */
public class TranslateJsonSqlCharacter implements TypeHandler<String> {

	
	/** 
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型 
     * @param preparedStatement 当前的PreparedStatement对象 
     * @param i 当前参数的位置 
     * @param b 当前参数的Java对象 
     * @param jdbcType 当前参数的数据库类型 
     * @throws SQLException 
     */  
	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		
	}
	
	/** 
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型 
     * @param resultSet 当前的结果集 
     * @param columnName 当前的字段名称 
     * @return 转换后的Java对象 
     * @throws SQLException 
     */
	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		
		
		
		
		
		return null;
	}
	
	/** 
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型 
     * @param resultSet 当前的结果集 
     * @param i 当前字段的位置 
     * @return 转换后的Java对象 
     * @throws SQLException 
     */  
	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}
	
	
	/** 
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型 
     * @param callableStatement 当前的CallableStatement执行后的CallableStatement 
     * @param columnIndex 当前输出参数的位置 
     * @return 
     * @throws SQLException 
     */
	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
	
	
//	private String transJsonColumnToUTF8(String jsonColumn){
//			
//		if(("assetJson").equals(jsonColumn)){
//			
////			GsonUtil.GsonToObject(jsonColumn, record)
//			
//		}
//		
//		
//	}
	
}
