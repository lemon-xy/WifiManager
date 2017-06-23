package pear.lemon.wifi.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String s, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, s, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > duration) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showShortToast(Context context, int resId) {
        showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context, String s) {
        showToast(context, s, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, int resId) {
        showToast(context, context.getString(resId), Toast.LENGTH_LONG);
    }

    public static void showLongToast(Context context, String s) {
        showToast(context, s, Toast.LENGTH_LONG);
    }
}
