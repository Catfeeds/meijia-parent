package com.simi.base.handler;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSON;

/**
 * mapper里json型字段到类的映射。
 * 
 * 
 * 用法:
 * 
 * 入库：
 * 	  //错误用法（百度到的）：  这种用法会在 入库时, 错误的将  json字段 转义，并在 首尾 添加 引号，导致取数据时不能正确解析json
 * 	   #{item.myObject, typeHandler=com.xxx.typehandler.JsonTypeHandler}
 * 
 *    // 正确用法: 由  generator 插件生成的  语句， 可以正确插入。修改（mybatis不会有多余处理）
 * 	  #{jsonInfo,jdbcType=OTHER})
 * 
 * 	  ps: 当前使用的为 
 * 			 	mysql-connector-java-5.1.33 ，
 * 				MyBatis_Generator_1.3.1
 * 	
 * 		 会将  mysql5.7 数据库表中的  json 类型的字段  映射为 object 类型的属性
 * 			
 * 		参考   xcompanyStaff 
 * 	
 * 出库：
 * <resultMap>
 *      <result property="jsonDataField" column="json_data_field" javaType="com.xxx.MyClass" typeHandler="com.xxx.typehandler.JsonTypeHandler"/>
 * </resultMap>
 *
 *
* @author hulj 
* @date 2016年6月8日 下午3:26:37 
*
*
 */
public class JsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {
	
	private static Log log = LogFactory.getLog(JsonTypeHandler.class);
	
	private Class<T> clazz;
	 
    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }
    
    //常用编码格式	
    private static String ISO88591_ENCODE = "ISO8859_1";
    private static String UTF8_ENCODE = "UTF-8";
    private static String GBK_ENCODE = "GBK";
    
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }
 
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    	
    	String columnValue = rs.getString(columnName);
    	
    	
    	//由于 mybatis 对于  mysql5.7的 json格式的数据 ，中文乱码(转为了 iso-8859-1编码)，    在 得到 结果集后进行 转码
    	try {
	    	if (columnValue != null) {
	            if (ISO88591_ENCODE.equals(getEncode(columnValue))) {
	            	columnValue =  new String(columnValue.getBytes(ISO88591_ENCODE), UTF8_ENCODE);
	            }
	        }
    	} catch (UnsupportedEncodingException e) {
    		  log.debug("对mybatis返回结果的 json格式字段转码失败");
    	}
    	
    	if(columnValue != null){
    		
    		// 去掉转义字符  "\", 使得json可以正确解析
    		columnValue = StringEscapeUtils.unescapeJava(columnValue);
    		
    		return  JSON.parseObject(columnValue, clazz);
    	}
    	
    	return (T) clazz;
    	
    }
 
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        
    	String columnValue = rs.getString(columnIndex);
    	
    	try {
	    	if (columnValue != null) {
	            if (ISO88591_ENCODE.equals(getEncode(columnValue))) {
	            	columnValue =  new String(columnValue.getBytes(ISO88591_ENCODE), UTF8_ENCODE);
	            }
	        }
    	} catch (UnsupportedEncodingException e) {
    		  log.debug("对mybatis返回结果的 json格式字段转码失败");
    	}
    	
    	if(columnValue != null){
    		
    		columnValue = StringEscapeUtils.unescapeJava(columnValue);
    		
    		return  JSON.parseObject(columnValue, clazz);
    	}
    	
    	return (T) clazz;
    }
 
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    	
    	String columnValue = cs.getString(columnIndex);
    	
    	try {
	    	if (columnValue != null) {
	            if (ISO88591_ENCODE.equals(getEncode(columnValue))) {
	            	columnValue =  new String(columnValue.getBytes(ISO88591_ENCODE), UTF8_ENCODE);
	            }
	        }
    	} catch (UnsupportedEncodingException e) {
    		  log.debug("对mybatis返回结果的 json格式字段转码失败");
    	}
    	
    	if(columnValue != null){
    		
    		// 入库时，会有 \ 转义字符。。用 apach 工具类处理,解决 json转换的问题
    		
    		columnValue = StringEscapeUtils.unescapeJava(columnValue);
    		
    		return  JSON.parseObject(columnValue, clazz);
    	}
    	
    	return (T) clazz;
    }
	
  //返回编码格式
    private String getEncode(String str) {
        String encode = null;
        if (verifyEncode(str, GBK_ENCODE)) {
            encode = GBK_ENCODE;
        } else if (verifyEncode(str, ISO88591_ENCODE)) {
            encode = ISO88591_ENCODE;
        } else if (verifyEncode(str, UTF8_ENCODE)) {
            encode = UTF8_ENCODE;
        } 

        return encode;
    }

    //判断编码格式是否相符
    private boolean verifyEncode(String str, String encode) {
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
        	 log.debug("对mybatis返回结果的 json格式字段转码失败");
        }
        return false;
    }
    
    public static void main(String[] args) {
		String  url = "{\"bankCardNo\":\"411081199209244059\",\"bankName\":\"大邮局\",\"contractBeginDate\":\"2019-08-08\",\"contractLimit\":\"1年\"}";
		
		System.out.println(StringEscapeUtils.unescapeJava(url));
	}
    
}
