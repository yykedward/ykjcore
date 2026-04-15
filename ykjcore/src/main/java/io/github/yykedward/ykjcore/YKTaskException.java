package io.github.yykedward.ykjcore;

public class YKTaskException extends RuntimeException {
    public YKTaskException(String message) {
        super(message);
    }
    
    public YKTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
