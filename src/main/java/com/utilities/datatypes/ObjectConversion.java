package com.utilities.datatypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class ObjectConversion {
	public static void main(String[] args) {
		// Object object = 1.0;
		// System.out.println(object);
		SourceClass sourceClass = new SourceClass(BigDecimal.valueOf(1), 25, "Harsh");
		TargetClass targetClass = new TargetClass();
		System.out.println(targetClass.getAge());
		setObject(sourceClass, targetClass);
		System.out.println("After");
		System.out.println(targetClass.getAge());
	}

	public static <T> void setObject(Object sourceClass, Object targetClass) {
		Method[] srcMethods = sourceClass.getClass().getMethods();
		Object srcValue = null;
		for (Method srcMethod : srcMethods) {
			if (srcMethod.getName().startsWith("get") && Character.isUpperCase(srcMethod.getName().charAt(3))
					&& srcMethod.getName() != "getClass") {
				try {
					System.out.println("Harsh");
					srcValue = srcMethod.invoke(sourceClass);
					System.out.println(srcMethod.getName() + srcValue);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					// Class<?>[] params = null;
					System.out.println(srcMethod.getReturnType());// srcMethod.getReturnType().getClass();
					// params[0] = srcMethod.getReturnType();
					Method targetMethod = targetClass.getClass().getMethod(srcMethod.getName().replace("get", "set"),
							srcMethod.getReturnType());
					targetMethod.invoke(targetClass, srcValue);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
