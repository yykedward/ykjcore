package io.github.yykedward.ykjcore;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface YKActionManagerInAppClosure extends Function<Object, CompletableFuture<?>> {
}
