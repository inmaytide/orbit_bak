package com.inmaytide.orbit.attachment.util;

import com.inmaytide.orbit.consts.AttachmentStatus;
import com.inmaytide.orbit.domain.sys.Attachment;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;

public class AttachmentUtils {

    public static FileSystemResource getResource(Attachment attachment) {
        return new FileSystemResource(getFilename(attachment));
    }

    public static String getFilename(Attachment attachment) {
        return FilenameUtils.concat(attachment.getStorageAddress(), attachment.getStorageName());
    }

    public static Attachment formal(Attachment attachment) {
        FileSystemResource temporary = getResource(attachment);

        attachment.setStorageAddress(DirectoryUtils.getStorageAddress());
        FileSystemResource formal = getResource(attachment);

        try {
            FileSystemUtils.copyRecursively(temporary.getFile(), formal.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        attachment.setStatus(AttachmentStatus.FORMAL.getValue());
        return attachment;

    }

}
