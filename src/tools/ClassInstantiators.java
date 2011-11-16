package tools;

import java.lang.reflect.InvocationTargetException;

import baseBaygan.BAutoInstantiatable;

public class ClassInstantiators {
	/**
	 * Creates and returns an instance of the class which the input classPath
	 * points to. It takes an Object[] as input of the constructor of the class.
	 * Only classes extends BAutoInstantiable can be instantiate using this
	 * function.
	 * 
	 * @param classPath
	 * @param args
	 * @return
	 */
	public static BAutoInstantiatable instantiate(String classPath,
			Object[] args) {
		try {

			return (BAutoInstantiatable) Class.forName(classPath)
					.getConstructor(new Class[] { Object[].class })
					.newInstance(new Object[] { args });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object instantiate(String classPath, String arg) {
		try {
			return Class.forName(classPath)
					.getConstructor(new Class[] { String.class })
					.newInstance(arg);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Instantiates from the input class with a given argument of Object[] as
	 * arguments of the constructor.
	 * 
	 * @param class
	 * @param objects
	 * @return
	 */
	public static Object instantiateClass(Class<?> cls, Object[] objects) {
		try {
			return cls.getConstructor(new Class[] { String.class })
					.newInstance(objects);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
