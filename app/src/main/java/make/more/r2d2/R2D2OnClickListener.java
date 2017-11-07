package make.more.r2d2;

import android.util.SparseArray;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by hezexi on 2016.6.14.
 */
public class R2D2OnClickListener implements View.OnClickListener {
	private SparseArray<Method> methodArr;
	private Object target;

	public R2D2OnClickListener(SparseArray<Method> methodArr, Object target) {
		this.methodArr = methodArr;
		this.target = target;
	}

	@Override
	public void onClick(View v) {
		Method method = methodArr.get(v.getId());
		Class<?>[] types = method.getParameterTypes();
		try {
			if (types == null) {
				return;
			} else if (types.length == 0) {
				method.invoke(target);
			} else if (types.length == 1 && View.class.isAssignableFrom(types[0])) {
				method.invoke(target, v);
			}
		} catch (Exception e) {
			throw new RuntimeException("onClick method inject wrong for " + target, e);
		}
	}
}
