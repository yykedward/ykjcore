package io.github.yykedward.ykjcore;

import java.util.HashMap;
import java.util.Map;

public class YKParamsConfig {

    public interface YKParamsConfigCallBack {
        Object callBack();
    }

    private static final YKParamsConfig instance = new YKParamsConfig();

    private Map<String, Object> params = new HashMap<>();

    private boolean isProduce = false;

    public static void config(boolean isProduce, Map<String, Object> params) {
        YKParamsConfig.instance.isProduce = isProduce;
        YKParamsConfig.instance.params = params;
    }

    public static Object getParams(String key) {

        return getParams(key, null, null);
    }

    public static Object getParams(YKParamsConfigCallBack debugCallBack, YKParamsConfigCallBack productCallBack) {
        return getParams(null, debugCallBack, productCallBack);
    }

    public static Object getParams(String key, YKParamsConfigCallBack debugCallBack, YKParamsConfigCallBack productCallBack) {
        if (key != null && YKParamsConfig.instance.params != null) {
            return YKParamsConfig.instance.params.get(key);
        } else {
            if (YKParamsConfig.instance.isProduce) {
                return productCallBack.callBack();
            } else {
                return debugCallBack.callBack();
            }
        }
    }


}
