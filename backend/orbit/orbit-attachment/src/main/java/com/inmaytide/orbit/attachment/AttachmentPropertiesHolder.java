package com.inmaytide.orbit.attachment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AttachmentPropertiesHolder {

    private static final Logger log = LoggerFactory.getLogger(AttachmentPropertiesHolder.class);

    private static AttachmentProperties props;

    public static AttachmentProperties get() {
        if (props == null) {
            props = new AttachmentProperties();
            props.afterPropertiesSet();
            log.warn("There is no instance for AttachmentProperties, use default [{}]", props.toString());
        }
        return props;
    }

    public static void set(AttachmentProperties props) {
        Assert.notNull(props, "The paramater [props] cannot be null");
        AttachmentPropertiesHolder.props = props;
        log.debug("Set attachmentProperties with [{}]", props.toString());
    }

}
