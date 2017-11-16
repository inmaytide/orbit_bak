package com.inmaytide.orbit.attachment.util;

import com.inmaytide.orbit.attachment.AttachmentProperties;
import com.inmaytide.orbit.commons.util.DateTimeUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryUtils {

    private static AttachmentProperties props;



    public static String getStorageAddress() {
        String folder = DateTimeUtils.formatNow("yyyyMMdd");
        String storageAddress = FilenameUtils.concat(props.getBaseStorageDirectory(), folder);
        createIfNotExist(storageAddress);
        return storageAddress;
    }

    public static String getTemporaryAddress() {
        return props.getTemporaryDirectory();
    }

    public static void createIfNotExist(String directories) {
        Path path = Paths.get(directories);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
