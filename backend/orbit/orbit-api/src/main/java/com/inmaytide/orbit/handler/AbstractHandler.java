package com.inmaytide.orbit.handler;

import com.inmaytide.orbit.domain.basic.BasicEntity;
import com.inmaytide.orbit.util.CommonUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Moss
 * @since October 10, 2017
 */
public abstract class AbstractHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractHandler.class);

    public Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public <T> Map<String, String> transformErrorMessage(Set<ConstraintViolation<T>> violations) {
        Map<String, String> message = new HashMap<>();
        violations.forEach(violation -> message.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return message;
    }

    public <T> boolean validate(T instance) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(instance);
        Assert.isTrue(CollectionUtils.isEmpty(violations), CommonUtils.getJsonString(transformErrorMessage(violations)));
        return true;
    }

    public void requireNotBlank(String parameter) {
        Assert.hasText(parameter, "The parameter cannot not be blank");
    }

    public <T extends BasicEntity> T idRequireNonnull(T t) {
        Assert.notNull(t.getId(), "The id of instance to update cannot not be null");
        return t;
    }

}
