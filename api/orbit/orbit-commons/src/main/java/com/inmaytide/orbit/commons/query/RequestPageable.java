package com.inmaytide.orbit.commons.query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * Some properties about pagination.
 *
 * @author Moss
 * @since September 10, 2017
 */
public class RequestPageable implements Serializable {

    private static final long serialVersionUID = -744865805141356260L;
    private static final Logger log = LoggerFactory.getLogger(RequestPageable.class);
    private static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "createTime");
    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer DEFAULT_SIZE = 10;

    public static final String REQUEST_PARAM_NAME_KEYWORD = "keywords";
    public static final String REQUEST_PARAM_NAME_NUMBER = "number";
    public static final String REQUEST_PARAM_NAME_SIZE = "size";

    private Integer number;

    private Integer size;

    private String keywords;

    public static RequestPageable of(ServerRequest request) {
        RequestPageable pageable = new RequestPageable();
        request.queryParam(REQUEST_PARAM_NAME_KEYWORD).ifPresent(pageable::setKeywords);
        try {
            request.queryParam(REQUEST_PARAM_NAME_NUMBER).map(Integer::valueOf).ifPresent(pageable::setNumber);
            request.queryParam(REQUEST_PARAM_NAME_SIZE).map(Integer::valueOf).ifPresent(pageable::setSize);
        } catch (NumberFormatException e) {
            log.error("Failed to build the pageant model, because the number or size parameters is not a number");
            throw new IllegalArgumentException("Failed to build the pageant model, because the number or size parameters is not a number");
        }
        return pageable;
    }

    public Integer getNumber() {
        if (Objects.isNull(number)) {
            number = DEFAULT_NUMBER;
        }
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        if (Objects.isNull(size)) {
            size = DEFAULT_SIZE;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Optional<String> getKeywords(boolean wrap) {
        if (StringUtils.isBlank(keywords)) {
            return Optional.empty();
        }
        String wrapped = wrap ? StringUtils.join("%", keywords, "%") : keywords;
        return Optional.of(wrapped);
    }

    public Optional<String> getKeywords() {
        return getKeywords(true);
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Pageable transform() {
        return transform(DEFAULT_SORT);
    }

    public Pageable transform(Sort.Direction direction, String... properties) {
        Sort sort = new Sort(direction, properties);
        return transform(sort);
    }

    public Pageable transform(Sort sort) {
        return PageRequest.of(getNumber() - 1, getSize(), sort);
    }
}
