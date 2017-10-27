package com.inmaytide.orbit.attachment.configuration;

import com.inmaytide.orbit.attachment.util.DirectoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "orbit.attachment")
public class AttachmentProperties implements InitializingBean {

    private String temporaryDirectory = System.getProperty("java.io.tmpdir");

    private String baseStorageDirectory = System.getProperty("user.dir") + "/attachments/";

    public String getTemporaryDirectory() {
        return temporaryDirectory;
    }

    public void setTemporaryDirectory(String temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
    }

    public String getBaseStorageDirectory() {
        return baseStorageDirectory;
    }

    public void setBaseStorageDirectory(String baseStorageDirectory) {
        this.baseStorageDirectory = baseStorageDirectory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DirectoryUtils.createIfNotExist(getBaseStorageDirectory());
        DirectoryUtils.createIfNotExist(getTemporaryDirectory());
    }
}
