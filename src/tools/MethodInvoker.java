package tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
	/**
	 * Checks if input Object[] as args is the same as arguments of the input
	 * method.
	 * 
	 * @param method
	 * @param args
	 * @return
	 */
	public static boolean isAppropriate(Method method, Object[] args) {
		if (args.length != method.getParameterTypes().length)
			return false;
		for (int i = 0; i < args.length; i++) {
			if (!args[i].getClass().equals(method.getParameterTypes()[i]))
				return false;
		}
		return true;
	}

	/**
	 * Invokes a method on an Object (obj) with arguments Object[] (args).
	 * 
	 * @param method
	 * @param obj
	 * @param args
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invoke(Method method, Object obj, Object[] args)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return method.invoke(obj, args);
	}
}
