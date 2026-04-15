package io.github.yykedward.ykjcore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 行为管理器模型（不可变）
 */
final class YkActionManagerModel {
    private final String name;
    private final YKActionManagerInAppClosure closure;

    YkActionManagerModel(String name, YKActionManagerInAppClosure closure) {
        this.name = name;
        this.closure = closure;
    }

    String getName() {
        return name;
    }

    YKActionManagerInAppClosure getClosure() {
        return closure;
    }

    CompletableFuture<?> execute(Object data) {
        return closure.apply(data);
    }
}

/**
 * 行为管理器，支持 code 和 inApp 两种行为注册与执行
 */
public class YKActionManager {

    public static final String YK_AM_GLOBAL_KEY = "yk_am_global_type";
    public static final String YK_AM_FUNC_KEY = "yk_am_func_type";
    public static final String YK_AM_DATA_KEY = "yk_am_data";

    private static final YKActionManager instance = new YKActionManager();

    private final ConcurrentHashMap<String, YKActionManagerCodeClosure> codeMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, YkActionManagerModel> inAppMap = new ConcurrentHashMap<>();

    private Consumer<String> onError;

    private YKActionManager() {
    }

    public static YKActionManager getInstance() {
        return instance;
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    /**
     * 注册 code 行为
     */
    public void registerCodeAction(String code, YKActionManagerCodeClosure closure) {
        codeMap.put(code, closure);
    }

    /**
     * 执行 code 行为
     */
    public CompletableFuture<Object> executeAction(String action, Object actionContent) {
        YKActionManagerCodeClosure closure = codeMap.get(action);
        if (closure != null) {
            return closure.apply(actionContent);
        } else {
            if (onError != null) {
                onError.accept("暂不支持该跳转");
            }
            return CompletableFuture.completedFuture(null);
        }
    }

    /**
     * 注册 inApp 行为
     */
    public void registerInAppAction(String globalType, String funcType, YKActionManagerInAppClosure inAppClosure) {
        String name = globalType + "_" + funcType;
        inAppMap.put(name, new YkActionManagerModel(name, inAppClosure));
    }

    /**
     * 执行 inApp 行为
     */
    public CompletableFuture<?> executeInAppAction(String globalType, String funcType, Object data) {
        if (globalType == null || globalType.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        String name = globalType + "_" + funcType;
        YkActionManagerModel model = inAppMap.get(name);
        if (model != null) {
            return model.execute(data);
        } else {
            if (onError != null) {
                onError.accept("暂不支持该跳转");
            }
            return CompletableFuture.completedFuture(null);
        }
    }
}
