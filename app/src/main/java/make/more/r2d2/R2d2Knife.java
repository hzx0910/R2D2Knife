package make.more.r2d2;

import android.app.Activity;
import android.app.Dialog;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public final class R2d2Knife {
	private static final String TAG = "R2d2Knife";


	private R2d2Knife() {
		throw new AssertionError("No instances.");
	}

	public static void inject(Activity target) {
		inject(target, target);
	}

	public static void inject(View target) {
		inject(target, target);
	}


	public static void inject(TextView target) {
		inject(target, target);
	}

	public static void inject(Dialog target) {
		inject(target, target);
	}

	/**
	 * 绑定带@InjectId注解的view变量<Br/>
	 * 绑定带@OnClickId注解的view点击方法<Br/>
	 * 注解应附带view元素ID值
	 *
	 * @param target
	 * @param viewSource
	 */
	public static void inject(Object target, Object viewSource) {
		Class targetClass = target.getClass();
		try {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAccessible() == false) {
					field.setAccessible(true);
				}
				if (field.isAnnotationPresent(InjectId.class)) {
					InjectId annotation = field.getAnnotation(InjectId.class);
					field.set(target, $(viewSource, annotation.value()));
				}
			}
			//-------------------------------------------------------------
			Method[] methods = targetClass.getDeclaredMethods();
			SparseArray<Method> methodArr = new SparseArray<Method>();
			for (Method method : methods) {
				if (method.isAccessible() == false) {
					method.setAccessible(true);
				}
				if (method.isAnnotationPresent(OnClickId.class)) {
					OnClickId annotation = method.getAnnotation(OnClickId.class);
					methodArr.put(annotation.value(), method);
				}
			}
			if (methodArr.size() > 0) {
				R2D2OnClickListener listener = new R2D2OnClickListener(methodArr, target);
				for (int i = 0; i < methodArr.size(); i++) {
					$(viewSource, methodArr.keyAt(i)).setOnClickListener(listener);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to inject views for " + target, e);
		}
	}

	public static void injectSameId(Activity target, Class RidClass) {
		injectSameId(target, target, RidClass);
	}

	public static void injectSameId(View target, Class RidClass) {
		injectSameId(target, target, RidClass);
	}

	public static void injectSameId(Dialog target, Class RidClass) {
		injectSameId(target, target, RidClass);
	}

	/**
	 * 绑定带@InjectSameId注解的 view变量<Br/>
	 * 绑定带@OnClickSameId注解的view点击方法<Br/>
	 * 相应view id取R.id中与变量或方法同名的值
	 *
	 * @param target     变量及方法所在对象
	 * @param viewSource view对象
	 * @param RidClass   R.id.class
	 */
	public static void injectSameId(Object target, Object viewSource, Class RidClass) {
		Class targetClass = target.getClass();
		try {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAccessible() == false) {
					field.setAccessible(true);
				}
				if (field.isAnnotationPresent(InjectSameId.class)) {
					field.set(target, $(viewSource, RidClass.getField(field.getName()).getInt(null)));
				}
			}
			//-------------------------------------------------------------
			Method[] methods = targetClass.getDeclaredMethods();
			SparseArray<Method> methodArr = new SparseArray<Method>();
			for (Method method : methods) {
				if (method.isAccessible() == false) {
					method.setAccessible(true);
				}
				if (method.isAnnotationPresent(OnClickSameId.class)) {
					methodArr.put(RidClass.getField(method.getName()).getInt(null), method);
				}
			}
			if (methodArr.size() > 0) {
				R2D2OnClickListener listener = new R2D2OnClickListener(methodArr, target);
				for (int i = 0; i < methodArr.size(); i++) {
					$(viewSource, methodArr.keyAt(i)).setOnClickListener(listener);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to inject views for " + target, e);
		}
	}

	public static <T extends View> T $(Object viewSource, int id) {
		if (viewSource instanceof View) {
			return (T) ((View) viewSource).findViewById(id);
		} else if (viewSource instanceof Activity) {
			return (T) ((Activity) viewSource).findViewById(id);
		} else if (viewSource instanceof Dialog) {
			return (T) ((Dialog) viewSource).findViewById(id);
		}
		return null;
	}

}

