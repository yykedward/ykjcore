package io.github.yykedward.ykjcore;

import java.util.LinkedList;

public class YKTaskUtil {

    public interface YKTaskUtilNextTaskCallBack {
        void next();
    }

    public interface YKTaskUtilCallBack {
        void doNext(YKTaskUtilNextTaskCallBack nextTaskCallBack);
    }

    public interface OnTaskQueueCompleteListener {
        void onComplete();
    }

    private final LinkedList<YKTaskUtilCallBack> taskQueue = new LinkedList<>();
    private boolean isExecuting = false;
    private OnTaskQueueCompleteListener listener;

    public YKTaskUtil() { }

    public void add(YKTaskUtilCallBack callBack) {
        if (callBack != null) {
            taskQueue.offer(callBack);
        }
    }

    public void setOnTaskQueueCompleteListener(OnTaskQueueCompleteListener listener) {
        this.listener = listener;
    }

    public synchronized void executeFirstTask() {
        if (isExecuting) {
            throw new YKTaskException("任务队列正在执行中，请勿重复调用 executeFirstTask()");
        }
        if (taskQueue.isEmpty()) {
            return;
        }
        isExecuting = true;
        executeNext();
    }

    private synchronized void executeNext() {
        if (taskQueue.isEmpty()) {
            isExecuting = false;
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }
        
        YKTaskUtilCallBack task = taskQueue.poll();
        if (task != null) {
            try {
                task.doNext(this::executeNext);
            } catch (Exception e) {
                isExecuting = false;
                throw new YKTaskException("任务执行失败", e);
            }
        }
    }
}
