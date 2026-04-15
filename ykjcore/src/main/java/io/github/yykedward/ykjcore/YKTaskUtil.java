package io.github.yykedward.ykjcore;

import java.util.ArrayList;

public class YKTaskUtil {

    public interface YKTaskUtilNextTaskCallBack {

        void next();
    }

    public interface YKTaskUtilCallBack {

        void doNext(YKTaskUtilNextTaskCallBack nextTaskCallBack);
    }


    private final ArrayList<YKTaskUtilCallBack> taskList = new ArrayList<>();
    private int currentIndex = 0;
    private int saveIndex = 0;

    public YKTaskUtil() { }

    public void add(YKTaskUtilCallBack callBack) {
        if (callBack != null) {
            taskList.add(callBack);
        }
    }

    public void executeFirstTask() {
        this.currentIndex = 0;
        this.saveIndex = 0;
        nextTask();
    }


    public void nextTask() {
        if (this.currentIndex > (this.taskList.size() - 1)) {
            return;
        }
        YKTaskUtilCallBack callBack = this.taskList.get(this.currentIndex);
        try {
            callBack.doNext(new YKTaskUtilNextTaskCallBack() {
                @Override
                public void next() {
                    nextTask();
                }
            });

            this.saveIndex = this.currentIndex;
            this.currentIndex = this.currentIndex + 1;

        } catch (Exception ignored) {

        }
    }
}
