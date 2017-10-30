package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.dao.sys.UserRepository;
import com.inmaytide.orbit.dao.sys.link.UserOrganizationRepository;
import com.inmaytide.orbit.dao.sys.link.UserRoleRepository;
import com.inmaytide.orbit.domain.basic.RequestConditions;
import com.inmaytide.orbit.domain.basic.RequestPageable;
import com.inmaytide.orbit.domain.sys.User;
import com.inmaytide.orbit.holder.WebExchangeHolder;
import com.inmaytide.orbit.service.sys.UserService;
import com.inmaytide.orbit.util.ApiUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private UserOrganizationRepository userOrganizationRepository;

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(getRepository().findByUsername(username));
    }

    @Override
    public List<User> listByIds(List<Long> ids) {
        List<User> users = new ArrayList<>();
        getRepository().findAllById(ids).forEach(users::add);
        return users;
    }

    @Override
    public List<User> listByRole(Long roleId) {
        return getRepository().findByRole(roleId);
    }

    @Override
    public Page<User> list(RequestConditions conditions, RequestPageable pageModel) {
        Pageable pageable = pageModel.transform();
        pagingInformation(conditions, pageable);
        List<User> users = getRepository().findList(conditions.getConditions());
        Integer count = getRepository().findCount(conditions.getConditions());
        Page<User> page = new PageImpl<>(users, pageable, count);
        log.debug("Get users with conditions [{}]", conditions.toString());
        return page;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void remove(List<Long> ids) {
        getRepository().deleteByIdIn(ids);
        userRoleRepository.deleteByUIdIn(ids);
        userOrganizationRepository.deleteByUIdIn(ids);
        removeAvatar(ids);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void remove(String ids) {
        remove(split(ids, NumberUtils::toLong, Collectors.toList()));
    }

    private void removeAvatar(List<Long> ids) {
        String belongs = StringUtils.join(ids.toArray(new Long[ids.size()]), ",");
        URI uri = WebExchangeHolder.get().getRequest().getURI();
        ClientResponse response = ApiUtils.delete()
                .uri("/orbit/attachments/{ids}", belongs)
                .exchange().block();
        System.out.println(response);
    }

    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }
}
