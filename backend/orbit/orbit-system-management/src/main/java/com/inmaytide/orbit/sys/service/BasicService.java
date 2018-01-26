package com.inmaytide.orbit.sys.service;

import com.inmaytide.orbit.commons.domain.AbstractEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface BasicService<R extends JpaRepository<T, Long>, T extends AbstractEntity> {

    default List<Long> split(String ids) {
        Assert.hasText(ids, "The ids to split must not be empty");
        String[] splitted = StringUtils.split(ids, ",");
        return Stream.of(splitted).map(NumberUtils::toLong).collect(Collectors.toList());
    }

    default Optional<T> get(Long id) {
        return getRepository().findById(id);
    }

    R getRepository();

}
