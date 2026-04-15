package io.github.yykedward.ykjcore;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface YKActionManagerCodeClosure extends Function<Object, CompletableFuture<Object>> {
}
