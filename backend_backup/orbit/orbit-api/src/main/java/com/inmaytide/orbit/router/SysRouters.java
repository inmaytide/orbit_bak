package com.inmaytide.orbit.router;

import com.inmaytide.orbit.handler.sys.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Moss
 * @since October 10, 2017
 */
@Component
public class SysRouters {

    @Resource
    private LogHandler logHandler;

    @Resource
    private RoleHandler roleHandler;

    @Resource
    private UserHandler userHandler;

    @Resource
    private PermissionHandler permissionHandler;

    @Resource
    private DataDictionaryHandler dataDictionaryHandler;

    private RouterFunction<?> logRouters() {
        RouterFunction<ServerResponse> routers = route(GET("/"), logHandler::list)
                .and(route(GET("/as-excel"), logHandler::export));
        return nest(path("/logs"), routers);
    }

    private RouterFunction<?> dataDictionaryRouters() {
        RouterFunction<ServerResponse> routers = route(GET("/"), dataDictionaryHandler::listByCategory);
        return nest(path("/data-dictionaries"), routers);
    }

    private RouterFunction<?> permissionRouters() {
        RouterFunction<ServerResponse> routers = route(GET("/"), permissionHandler::list)
                .and(route(DELETE("/{ids}"), permissionHandler::remove))
                .and(route(POST("/").and(accept(APPLICATION_JSON)), permissionHandler::add))
                .and(route(PUT("/").and(accept(APPLICATION_JSON)), permissionHandler::update))
                .and(route(GET("/checkCode/{id}/{code}"), permissionHandler::checkCode))
                .and(route(PATCH("/move/{id}/{category}"), permissionHandler::move));
        return nest(path("/permissions"), routers);
    }

    private RouterFunction<?> roleRouters() {
        RouterFunction<ServerResponse> routers = route(GET("/"), roleHandler::list)
                .and(route(DELETE("/{ids}"), roleHandler::remove))
                .and(route(GET("/{id}"), roleHandler::get))
                .and(route(POST("/").and(accept(APPLICATION_JSON)), roleHandler::add))
                .and(route(PUT("/").and(accept(APPLICATION_JSON)), roleHandler::modify))
                .and(route(GET("/checkCode/{id}/{code}"), roleHandler::checkCode))
                .and(route(PATCH("/{id}/permissions/{permissionIds}"), roleHandler::associatePermissions))
                .and(route(PATCH("/{id}/users/{userIds}"), roleHandler::associateUsers))
                .and(route(DELETE("/{id}/users/{userIds}"), roleHandler::removeAssociatedUsers));
        return nest(path("/roles"), routers);
    }

    private RouterFunction<?> userRouters() {
        RouterFunction<ServerResponse> routers = route(GET("/"), userHandler::list)
                .and(route(PUT("/{id}/avatar").and(accept(MULTIPART_FORM_DATA)), userHandler::uploadAvatar))
                .and(route(DELETE("/{ids}"), userHandler::remove));
        return nest(path("/users"), routers);
    }

    public RouterFunction<?> routers() {
        return nest(path("/sys"), logRouters()
                .andOther(permissionRouters())
                .andOther(roleRouters())
                .andOther(dataDictionaryRouters())
                .andOther(userRouters()));
    }

}
