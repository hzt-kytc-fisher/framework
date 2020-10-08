package com.kytc.framework.common.utils;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * 
 * 使用javassist解析方法中的参数
 * @author liningning
 * @date 2019年4月22日 下午1:54:42
 */
public class AnalyticalParamNames {

	private final static ClassPool pool = ClassPool.getDefault();

	static {
		ClassClassPath classPath = new ClassClassPath(AnalyticalParamNames.class);
		pool.insertClassPath(classPath);
	}

	public static String[] getNames(String className, String methodName) {
		
		CtClass ctClass = null;
		try {
			ctClass = pool.get(className);
			CtMethod cm = ctClass.getDeclaredMethod(methodName);
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (localVariableAttribute == null) {
				return null;
			}
			
			int begin = 0;

			String[] paramNames = new String[cm.getParameterTypes().length];
			int count = 0;

			for (int i = 0; i < localVariableAttribute.tableLength(); i++) {
				if (localVariableAttribute.variableName(i).equals("this")) {
					begin = i;
					break;
				}
			}

			for (int i = begin + 1; i <= begin + paramNames.length; i++) {
				paramNames[count] = localVariableAttribute.variableName(i);
				count++;
			}
			return paramNames;
		} catch (Exception e) {
		} finally {
			try {
				if (ctClass != null) {
					ctClass.detach();
				}
			} catch (Exception e2) {
			}

		}
		return null;
	}
	
	/**
	 * 判断是否是el表达式
	 */
	public static boolean isSpelEx(String key) {
		
		if(key.startsWith("#")) {
			return true;
		}
		
		return false;
	}
}