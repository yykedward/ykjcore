package io.github.yykedward.tools

import io.github.yykedward.ykjcore.YKTaskUtil;

class YKDemoTask : YKTaskUtil.YKTaskUtilCallBack {
    override fun doNext(nextTaskCallBack: YKTaskUtil.YKTaskUtilNextTaskCallBack?) {

        nextTaskCallBack?.next()
    }


}