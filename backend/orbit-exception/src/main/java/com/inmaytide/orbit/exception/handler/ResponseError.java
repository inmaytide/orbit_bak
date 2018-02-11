package com.inmaytide.orbit.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Instant;

public class ResponseError implements Serializable {

    private static final long serialVersionUID = 4807240539075912410L;

    private final Long timestamp;

    private Integer status;

    private String error;

    private String path;

    private String message;

    private ResponseError() {
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static ResponseErrorBuilder getBuilder() {
        return new ResponseErrorBuilder();
    }

    public static ResponseErrorBuilder withThrowable(Throwable throwable) {
        return getBuilder().throwable(throwable).status(HttpStatusParser.parser(throwable));
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResponseErrorBuilder {

        private String path;
        private Throwable throwable;
        private HttpStatus status;

        private ResponseErrorBuilder() {

        }

        public ResponseErrorBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ResponseErrorBuilder throwable(Throwable throwable) {
            Assert.notNull(throwable, "The throwable must not be null");
            this.throwable = throwable;
            return this;
        }

        public ResponseErrorBuilder status(HttpStatus status) {
            Assert.notNull(throwable, "The status must not be null");
            this.status = status;
            return this;
        }

        public ResponseError build() {
            Assert.notNull(status, "The status must not be null");
            ResponseError inst = new ResponseError();
            inst.setPath(path);
            inst.setStatus(status.value());
            inst.setError(status.getReasonPhrase());
            if (throwable != null) {
                inst.setMessage(throwable.getMessage());
            }
            return inst;
        }
    }
}
