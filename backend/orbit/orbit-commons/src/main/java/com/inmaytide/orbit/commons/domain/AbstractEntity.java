package com.inmaytide.orbit.commons.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 5784033340704847103L;

    @Id
    @GenericGenerator(name = "", strategy = "")
    private Long id;

    @Column(name = "create_time")
    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @CreatedBy
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creator;

    @LastModifiedBy
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updater;

    @Version
    private Integer version;

}
