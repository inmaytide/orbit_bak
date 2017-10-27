package com.inmaytide.orbit.handler.sys;

import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.Log;
import com.inmaytide.orbit.handler.AbstractHandler;
import com.inmaytide.orbit.attachment.util.FileUtils;
import com.inmaytide.orbit.log.LogAnnotation;
import com.inmaytide.orbit.office.excel.ExcelExportUtils;
import com.inmaytide.orbit.service.sys.LogService;
import com.inmaytide.orbit.util.DateTimeUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Moss
 * @since October 10, 2017
 */
@Component
public class LogHandler extends AbstractHandler {

    private static final Logger log = LoggerFactory.getLogger(LogHandler.class);

    private static final LocalTime SEARCH_DEFAULT_BEGIN_TIME = LocalTime.of(0, 0, 0);

    private static final LocalTime SEARCH_DEFAULT_END_TIME = LocalTime.of(23, 59, 59);

    @Autowired
    private LogService service;

    @Nonnull
    @RequiresPermissions("log:list")
    public Mono<ServerResponse> list(ServerRequest request) {
        RequestPageable pageable = RequestPageable.of(request);
        RequestConditions conditions = buildConditions(request);
        return ok().body(Mono.just(service.list(conditions, pageable)), Page.class);
    }

    @Nonnull
    @LogAnnotation("导出日志")
    @RequiresPermissions("log:export")
    public Mono<ServerResponse> export(ServerRequest request) {
        RequestConditions conditions = buildConditions(request);
        log.debug("Begin export logs with conditions [{}]", conditions.toString());
        FileSystemResource resource = FileUtils.getTemporaryResource(System.currentTimeMillis() + ".xlsx");
        try (OutputStream os = resource.getOutputStream()) {
            ExcelExportUtils.export(Log.class, service.list(conditions), os);
        } catch (IOException | InvalidFormatException e) {
            log.error("Cannot write logs to excel", e);
            throw new RuntimeException(e);
        }
        log.debug("Export logs finished.");
        return FileUtils.download(resource);
    }


    private RequestConditions buildConditions(ServerRequest request) {
        RequestConditions conditions = new RequestConditions(request);
        conditions.fromRequest("keywords");
        try {
            conditions.fromRequest("begin", value -> DateTimeUtils.format(value, SEARCH_DEFAULT_BEGIN_TIME, "yyyy-M-d"));
            conditions.fromRequest("end", value -> DateTimeUtils.format(value, SEARCH_DEFAULT_END_TIME, "yyyy-M-d"));
        } catch (Exception e) {
            log.error("Failed to build the search conditions", e);
            throw new IllegalArgumentException(e);
        }
        return conditions;
    }

}
