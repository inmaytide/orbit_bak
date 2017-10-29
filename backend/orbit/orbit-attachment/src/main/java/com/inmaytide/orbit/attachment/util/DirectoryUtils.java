package com.inmaytide.orbit.attachment.util;

import com.inmaytide.orbit.attachment.configuration.AttachmentPropertiesHolder;
import com.inmaytide.orbit.util.DateTimeUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class DirectoryUtils {

    public static String getStorageAddress() {
        String folder = DateTimeUtils.format(LocalDate.now(), "yyyyMMdd");
        String storageAddress = FilenameUtils.concat(AttachmentPropertiesHolder.get().getBaseStorageDirectory(), folder);
        createIfNotExist(storageAddress);
        return storageAddress;
    }

    public static String getTemporaryAddress() {
        return AttachmentPropertiesHolder.get().getTemporaryDirectory();
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
