package com.inmaytide.orbit.attachment.configuration;

import com.inmaytide.orbit.holder.ApplicationContextHolder;

public class AttachmentPropertiesHolder {

    private static AttachmentProperties attachmentProperties;

    public static AttachmentProperties get() {
        if (attachmentProperties == null) {
            attachmentProperties = ApplicationContextHolder.getBean(AttachmentProperties.class);
        }
        return attachmentProperties;
    }

    public static void set(AttachmentProperties attachmentProperties) {
        AttachmentPropertiesHolder.attachmentProperties = attachmentProperties;
    }
}
