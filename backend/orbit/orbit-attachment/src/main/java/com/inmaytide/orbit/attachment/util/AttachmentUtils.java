package com.inmaytide.orbit.attachment.util;

import com.inmaytide.orbit.domain.sys.Attachment;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;

public class AttachmentUtils {

    public static FileSystemResource getResource(Attachment attachment) {
        return new FileSystemResource(getFilename(attachment));
    }

    public static String getFilename(Attachment attachment) {
        return FilenameUtils.concat(attachment.getStorageAddress(), attachment.getStorageName());
    }

}
