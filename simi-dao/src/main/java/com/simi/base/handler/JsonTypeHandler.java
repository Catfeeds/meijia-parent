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
 * mapper��json���ֶε����ӳ�䡣
 * 
 * 
 * �÷�:
 * 
 * ��⣺
 * 	  //�����÷����ٶȵ��ģ���  �����÷����� ���ʱ, ����Ľ�  json�ֶ� ת�壬���� ��β ��� ���ţ�����ȡ����ʱ������ȷ����json
 * 	   #{item.myObject, typeHandler=com.xxx.typehandler.JsonTypeHandler}
 * 
 *    // ��ȷ�÷�: ��  generator ������ɵ�  ��䣬 ������ȷ���롣�޸ģ�mybatis�����ж��ദ��
 * 	  #{jsonInfo,jdbcType=OTHER})
 * 
 * 	  ps: ��ǰʹ�õ�Ϊ 
 * 			 	mysql-connector-java-5.1.33 ��
 * 				MyBatis_Generator_1.3.1
 * 	
 * 		 �Ὣ  mysql5.7 ���ݿ���е�  json ���͵��ֶ�  ӳ��Ϊ object ���͵�����
 * 			
 * 		�ο�   xcompanyStaff 
 * 	
 * ���⣺
 * <resultMap>
 *      <result property="jsonDataField" column="json_data_field" javaType="com.xxx.MyClass" typeHandler="com.xxx.typehandler.JsonTypeHandler"/>
 * </resultMap>
 *
 *
* @author hulj 
* @date 2016��6��8�� ����3:26:37 
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
    
    //���ñ����ʽ	
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
    	
    	
    	//���� mybatis ����  mysql5.7�� json��ʽ������ ����������(תΪ�� iso-8859-1����)��    �� �õ� ���������� ת��
    	try {
	    	if (columnValue != null) {
	            if (ISO88591_ENCODE.equals(getEncode(columnValue))) {
	            	columnValue =  new String(columnValue.getBytes(ISO88591_ENCODE), UTF8_ENCODE);
	            }
	        }
    	} catch (UnsupportedEncodingException e) {
    		  log.debug("��mybatis���ؽ���� json��ʽ�ֶ�ת��ʧ��");
    	}
    	
    	if(columnValue != null){
    		
    		// ȥ��ת���ַ�  "\", ʹ��json������ȷ����
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
    		  log.debug("��mybatis���ؽ���� json��ʽ�ֶ�ת��ʧ��");
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
    		  log.debug("��mybatis���ؽ���� json��ʽ�ֶ�ת��ʧ��");
    	}
    	
    	if(columnValue != null){
    		
    		// ���ʱ������ \ ת���ַ������� apach �����ദ��,��� jsonת��������
    		
    		columnValue = StringEscapeUtils.unescapeJava(columnValue);
    		
    		return  JSON.parseObject(columnValue, clazz);
    	}
    	
    	return (T) clazz;
    }
	
  //���ر����ʽ
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

    //�жϱ����ʽ�Ƿ����
    private boolean verifyEncode(String str, String encode) {
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
        	 log.debug("��mybatis���ؽ���� json��ʽ�ֶ�ת��ʧ��");
        }
        return false;
    }
    
    public static void main(String[] args) {
		String  url = "{\"bankCardNo\":\"411081199209244059\",\"bankName\":\"���ʾ�\",\"contractBeginDate\":\"2019-08-08\",\"contractLimit\":\"1��\"}";
		
		System.out.println(StringEscapeUtils.unescapeJava(url));
	}
    
    
    
    
}
