package com.inmaytide.orbit.commons.exception.handler;

import com.inmaytide.orbit.commons.exception.ResponseException;
import com.inmaytide.orbit.commons.exception.consts.ThrowableDefinition;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Instant;

public class ThrowableBody implements Serializable {

    private static final long serialVersionUID = 4807240539075912410L;

    private final Long timestamp;

    private Integer status;

    private String code;

    private String path;

    private String message;

    private ThrowableBody() {
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {

        private String path;
        private Integer status;
        private String code;
        private String message;

        private Builder() {
            status = ThrowableDefinition.UNEXPECTED.getStatus().value();
            code = ThrowableDefinition.UNEXPECTED.getCode();
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder throwable(Throwable throwable) {
            Assert.notNull(throwable, "The throwable must not be null");
            this.message = throwable.getMessage();
            if (throwable instanceof ResponseException) {
                ResponseException e = (ResponseException) throwable;
                this.status = e.getHttpStatus().value();
                this.code = e.getCode();
            } else if (throwable instanceof IllegalArgumentException) {
                this.status = ThrowableDefinition.BAD_ARGUMENTS.getStatus().value();
                this.code = ThrowableDefinition.BAD_ARGUMENTS.getCode();
            }
            return this;
        }

        public ThrowableBody build() {
            ThrowableBody inst = new ThrowableBody();
            inst.setMessage(message);
            inst.setStatus(status);
            inst.setCode(code);
            inst.setPath(path);
            return inst;
        }
    }
}
