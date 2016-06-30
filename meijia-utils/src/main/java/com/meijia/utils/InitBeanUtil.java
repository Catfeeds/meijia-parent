package com.meijia.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Jarvis 2009-09-07
 * <p>
 * <p>
 * 在实际项目中,对于一个有很多字段的表对应的javaBean中有很多属性,我遇到的有150多个,实际上我们可能只需要处理其中的30-50个字段,比如新增
 * <p>
 * <p>
 * ,那么我们需要手工对其它接近100个字段的属性set默认数据,容易出错！基于此问题,利用java的反射机制和多态可以很好的解决。具有很强的通用性
 * <p>
 * <p>
 * ,扩展性。实例化一个javaBean,可以先初始化部分或全部字段,使之不为空,在根据业务上的处理具体来做。
 * <p>
 * <p>
 * 1.对于标准的javaBean,属性一定有对应的get和set方法
 * </p>
 * <p>
 * 2.属性的set方法的参数只有一个,并且该参数的类型和该属性的get方法类型一致
 * </p>
 * <p>
 * 3.扩展性：如果需要具体只初始化某一种类型的属性 在initValue()加判断 对于此类未加入的类型或者无法实现的类型，可以参照加入，视实际项目需求
 * <p>
 * <p>
 * 4.总感觉initValue()的方法if...else...太多 还没找到更好的解决方法 待优化！
 * <p>
 */

public class InitBeanUtil {

	private static InitBeanUtil init = null;
	private final String START_SET = "set";
	private final String START_GET = "get";
	private final String START_IS = "is";
	private final int START_THREE = 3; // "set"字符串长度 3

	// 参数初始值---begin
	private final String INIT_STRING = "";
	private final int INIT_INT = 0;
	private final Long INIT_LONG = 0L;
	private final double INIT_DOUBLE = 0.0;
	private final float INIT_FLOAT = 0;
	private final boolean INIT_BOOLEAN = true;
	private final char INIT_CHAR = ' ';

	private final BigDecimal INIT_BigDecimal = new BigDecimal("0.0");
	private final Integer INIT_Integer = new Integer(0);
	private final Double INIT_Double = new Double(0.0);
	private final Long INIT_Long = 0L;
	private final Float INIT_Float = new Float(0.0);
	private final Boolean INIT_Boolean = new Boolean(true);
	private final Character INIT_Character = new Character(' ');
	private final java.sql.Date INIT_DateS = new java.sql.Date(System.currentTimeMillis());
	private final java.util.Date INIT_DateU = new java.util.Date(System.currentTimeMillis());
	private final java.sql.Timestamp INIT_Timestamp = new java.sql.Timestamp(System.currentTimeMillis());
	private final java.util.List INIT_List = new ArrayList();
	// 参数初始值---end

	// 参数类型---begin 封装类型给出完整名
	private final String TYPE_String = "java.lang.String";
	private final String TYPE_int = "int";
	private final String TYPE_Long = "java.lang.Long";
	private final String TYPE_double = "double";
	private final String TYPE_float = "float";
	private final String TYPE_boolean = "boolean";
	private final String TYPE_char = "char";

	private final String TYPE_BigDecimal = "java.math.BigDecimal";
	private final String TYPE_Integer = "java.lang.Integer";
	private final String TYPE_Double = "java.lang.Double";
	private final String TYPE_Float = "java.lang.Float";
	private final String TYPE_Boolean = "java.lang.Boolean";
	private final String TYPE_Character = "java.lang.Character";
	private final String TYPE_DateS = "java.sql.Date";
	private final String TYPE_DateU = "java.util.Date";
	private final String TYPE_Timestamp = "java.sql.Timestamp";
	private final String TYPE_List = "java.util.List";
	// 参数类型---end

	private List setList = new ArrayList(); // 存储所有setter方法
	private List getList = new ArrayList(); // 存储所有getter方法

	public void InitBean() {
		// 无参构造器
	}

	public static Object initObject(Object bean) { // 单例模式
		if (init == null) {
			init = new InitBeanUtil();
		}
		return init.initBean(bean);
	}

	/**
	 * 初始化属性主要方法
	 */
	public Object initBean(Object bean) {
		Object object = null;
		try {
			object = bean.getClass().newInstance(); // 实例化bean
			Class beanClass = bean.getClass(); // 获取bean的类名
			Method[] beanMethods = beanClass.getMethods(); // 获取该Bean对外公共setter方法
			for (int i = 0; i < beanMethods.length; i++) {
				if (beanMethods[i].getName().startsWith(START_SET)) {
					// 获取所有set方法 满足条件：set开头
					// System.out.println("set方法 = " + beanMethods[i].getName()
					// );
					this.setList.add(beanMethods[i]);
				} else if (beanMethods[i].getName().startsWith(START_GET) || beanMethods[i].getName().startsWith(START_IS)) {
					// 获取所有get方法 满足条件：get开头或者is开始 <boolean的get方法是 isBool()>
					// System.out.println("get方法 = " + beanMethods[i].getName()
					// );
					this.getList.add(beanMethods[i]);
				}
			}
			for (int i = 0; i < this.setList.size(); i++) {
				Method setMethod = (Method) this.setList.get(i);
				String getMethod = getter(setMethod.getName());
				if (hasMethod(getMethod)) { // 判断属性是否有get方法 检查 可以省略
					Class[] typeSet = setMethod.getParameterTypes(); // set方法参数类型
					Object[] oarray = initValue(typeSet[0]); // javaBean的set方法只有一个参数,只需要取第一个
					setMethod.invoke(object, oarray);
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 获取getter方法 一个属性get方法名 = get + 该属性set方法名字符串不包含set字符部分
	 * 特殊：属性是boolean类型的"get"方式是"is"开头
	 */
	private String getter(String setter) {
		String getMethod = START_GET + setter.substring(START_THREE);
		if (hasMethod(getMethod)) { // 获得getter方法存在返回
			return getMethod;
		} else { // 获得getter方法不在 参数可能是boolean类型
			getMethod = START_IS + setter.substring(START_THREE);
			if (hasMethod(getMethod)) {
				return getMethod;
			}
		}
		return getMethod;
	}

	/**
	 * <>判断get方法是否存在<>
	 */
	private boolean hasMethod(String MethodName) {
		boolean flag = false;
		String tempName = null;
		Method tempMethod = null;
		for (int k = 0; k < getList.size(); k++) {
			tempMethod = (Method) getList.get(k);
			tempName = tempMethod.getName();
			if (MethodName.equals(tempName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 对属性初始化主要方法<br>
	 * 可根据具体需求变动,比如初始化某一类型属性<br>
	 * <br>
	 */
	private Object[] initValue(Class type) {
		Object[] oarray = new Object[1]; // javaBean的setter方法只有一个参数，只需要一个一维Object数组
		String typeStr = type.getName();
		// System.out.println("typeStr = " +typeStr);
		if (TYPE_String.equals(typeStr)) { // String类型初始化
			oarray[0] = INIT_STRING;
		} else if (TYPE_int.equals(typeStr)) { // int类型初始化
			oarray[0] = new Integer(INIT_INT);
		} else if (TYPE_double.equals(typeStr)) { // double类型初始化
			oarray[0] = new Double(INIT_DOUBLE);
		} else if (TYPE_float.equals(typeStr)) { // float类型初始化
			oarray[0] = new Float(INIT_FLOAT);
		} else if (String.valueOf(TYPE_char).equals(typeStr)) { // char类型初始化
			oarray[0] = new Character(INIT_CHAR);
		} else if (TYPE_BigDecimal.equals(typeStr)) { // BigDecima类型初始化
			oarray[0] = INIT_BigDecimal;
		} else if (TYPE_Integer.equals(typeStr)) { // Integer类型初始化
			oarray[0] = INIT_Integer;
		} else if (TYPE_Long.equals(typeStr)) { // Long类型初始化
			oarray[0] = INIT_Long;
		} else if (TYPE_Double.equals(typeStr)) { // Double类型初始化
			oarray[0] = INIT_Double;
		} else if (TYPE_Float.equals(typeStr)) { // Float类型初始化
			oarray[0] = INIT_Float;
		} else if (TYPE_boolean.equals(typeStr)) { // boolean类型初始化
			oarray[0] = new Boolean(INIT_BOOLEAN);
		} else if (TYPE_Boolean.equals(typeStr)) { // Boolean类型初始化
			oarray[0] = INIT_Boolean;
		} else if (TYPE_Character.equals(typeStr)) { // Character类型初始化
			oarray[0] = INIT_Character;
		} else if (TYPE_DateS.equals(typeStr)) { // Date类型初始化(java.sql.Date)
			oarray[0] = INIT_DateS;
		} else if (TYPE_DateU.equals(typeStr)) { // Date类型初始化(java.util.Date)
			oarray[0] = INIT_DateU;
		} else if (TYPE_Timestamp.equals(typeStr)) { // Timestamp类型初始化(java.sql.Timestamp)
			oarray[0] = INIT_Timestamp;
		} else if (TYPE_List.equals(typeStr)) { // List类型初始化(java.util.List)
			oarray[0] = INIT_List;
		} else {
			// 针对引用类型进行初始化
			// 比如属性也是一个javaBean,学生Student有一个属性是学校School,School是一个javaBean
			try {
				oarray[0] = type.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		// System.out.println("oarray[0] = " +oarray[0]);
		return oarray;
	}

	public static void main(String[] args) {

	}

}