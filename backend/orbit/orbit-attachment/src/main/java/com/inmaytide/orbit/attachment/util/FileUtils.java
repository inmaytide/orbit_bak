package com.inmaytide.orbit.attachment.util;

import com.inmaytide.orbit.attachment.enums.AttachmentStatus;
import com.inmaytide.orbit.commons.id.UUIDGenerator;
import com.inmaytide.orbit.attachment.domain.Attachment;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.web.reactive.function.BodyInserters.fromResource;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since October 26, 2017
 */
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static String getTemporaryFilename(String filename) {
        return FilenameUtils.concat(DirectoryUtils.getTemporaryAddress(), filename);
    }

    public static FileSystemResource getTemporaryResource(String filename) {
        return new FileSystemResource(getTemporaryFilename(filename));
    }

    public static Mono<ServerResponse> download(Resource resource) {
        return download(resource, APPLICATION_OCTET_STREAM);
    }

    public static Mono<ServerResponse> download(Resource resource, MediaType mediaType) {
        return ok().contentType(mediaType).body(fromResource(resource));
    }

    public static Mono<Attachment> upload(ServerRequest request, String name) {
        return request.body(BodyExtractors.toMultipartData())
                .map(parts -> {
                    Map<String, Part> map = parts.toSingleValueMap();
                    FilePart part = (FilePart) map.get(name);
                    return buildAttachmentAndWriteFile(part, request);
                });
    }

    private static Attachment buildAttachmentAndWriteFile(FilePart part, ServerRequest request) {
        Attachment attachment = new Attachment();
        attachment.setOriginalName(FilenameUtils.removeExtension(part.filename()));
        attachment.setStorageName(UUIDGenerator.generate());
        attachment.setExtension(FilenameUtils.getExtension(part.filename()));
        attachment.setStorageAddress(DirectoryUtils.getTemporaryAddress());
        attachment.setStatus(AttachmentStatus.TEMPORARY.getValue());
        request.queryParam("belong").map(NumberUtils::toLong).ifPresent(attachment::setBelong);
        FileSystemResource resource = AttachmentUtils.getResource(attachment);
        part.transferTo(createIfNotExist(resource));
        try {
            attachment.setSize(resource.contentLength());
        } catch (IOException e) {
            log.error("Failed to get file size. [{}]", AttachmentUtils.getFilename(attachment));
            throw new RuntimeException(e);
        }
        return attachment;
    }


    public static File createIfNotExist(FileSystemResource resource) {
        if (!resource.exists()) {
            try {
                resource.getFile().createNewFile();
            } catch (IOException e) {
                log.error("Failed to create file [{}], cause by [{} => {}]", resource.getPath(), e.getClass().getName(), e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return resource.getFile();
    }

}
