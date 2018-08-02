package com.inmaytide.orbit.commons.exception.handler;

import com.inmaytide.orbit.commons.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Instant;

public class ResponseError implements Serializable {

    private static final long serialVersionUID = 4807240539075912410L;

    private final Long timestamp;

    private Integer status;

    private String code;

    private String path;

    private String message;

    private ResponseError() {
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static ResponseErrorBuilder getBuilder() {
        return new ResponseErrorBuilder();
    }

    public static ResponseErrorBuilder withThrowable(Throwable throwable) {
        return getBuilder().throwable(throwable);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        private Integer status;
        private String code;
        private String message;

        private ResponseErrorBuilder() {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            code = GeneralException.ERROR_UNEXPECTED;
        }

        public ResponseErrorBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ResponseErrorBuilder throwable(Throwable throwable) {
            Assert.notNull(throwable, "The throwable must not be null");
            this.message = throwable.getMessage();
            if (throwable instanceof GeneralException) {
                GeneralException e = (GeneralException) throwable;
                this.status = e.getHttpStatus().value();
                this.code = e.getCode();
            }
            return this;
        }

        public ResponseError build() {
            ResponseError inst = new ResponseError();
            inst.setMessage(message);
            inst.setStatus(status);
            inst.setCode(code);
            inst.setPath(path);
            return inst;
        }
    }
}
