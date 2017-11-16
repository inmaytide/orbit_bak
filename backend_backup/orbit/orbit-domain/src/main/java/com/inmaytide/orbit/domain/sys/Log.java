package com.inmaytide.orbit.domain.sys;

import com.inmaytide.orbit.office.excel.annotation.Comment;
import com.inmaytide.orbit.office.excel.annotation.ExcelTemplate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;

import java.time.LocalDateTime;

@Entity(table = "sys_log")
@ExcelTemplate
public class Log implements java.io.Serializable {

    private static final long serialVersionUID = 9133989827160716863L;

    @Id(strategy = Id.GenerationType.ASSIGNATION)
    private Long id;

    @Comment(header="日志名称", column = 0)
    private String name;

    @Column(name = "ip_address")
    @Comment(header="IP", column = 3)
    private String ipAddress;

    @CreatedBy
    private Long operator;

    @Transient
    @Comment(header="操作人", column = 2)
    private String operatorName;

    @Column(name = "class_name")
    private String className;

    @Column(name = "method_name")
    private String methodName;

    @CreatedDate
    @Comment(header = "操作时间", column=4)
    private LocalDateTime time;

    @Column(name = "is_succeed")
    @Comment(header="是否成功", column = 1)
    private String isSucceed;

    @Comment(header = "日志信息", column=5)
    private String message;

    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}