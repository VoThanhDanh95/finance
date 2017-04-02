package vn.assignment.finance.util;

import android.content.Context;

import vn.eazy.loader.EzLoader;

/**
 * Created by cuong on 3/7/17.
 */

public class LoaderUtils {
    private static EzLoader ezLoader = new EzLoader();

    public static void show(Context context) {
        ezLoader.showLoading(context);
    }

    public static void show(Context context, String title) {
        ezLoader.showLoading(title, context);
    }

    public static void hide(){
        ezLoader.hideLoading();
    }
}
