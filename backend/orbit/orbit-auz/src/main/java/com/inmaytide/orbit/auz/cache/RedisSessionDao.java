package com.inmaytide.orbit.auz.cache;

import com.inmaytide.orbit.consts.Constants;
import com.inmaytide.orbit.exception.CannotReadSessionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisSessionDao extends AbstractSessionDAO {

    private static final Logger log = LoggerFactory.getLogger(RedisSessionDao.class);

    public static final String DEFAULT_KEY_PREFIX = "shiro_redis_session:";

    private String keyPrefix;

    public RedisSessionDao() {
        setKeyPrefix(DEFAULT_KEY_PREFIX);
    }

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    private String buildKey(Serializable sessionId) {
        return getKeyPrefix() + sessionId;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.error("Cannot get session with [null]");
            throw new CannotReadSessionException("Cannot get session with [null]");
        }
        ValueOperations ops = redisTemplate.opsForValue();
        Session session = (Session) ops.get(buildKey(sessionId));
        log.debug("Get session with id [{}]", sessionId);
        if (session != null) {
            redisTemplate.expire(buildKey(sessionId), Constants.SESSION_TIMEOUT, TimeUnit.MINUTES);
        } else {
            log.debug("There is no session with id [{}]", sessionId);
            session = new SimpleSession();
            doCreate(session);
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        log.debug("Remove a session with id [{}]", session.getId());
        redisTemplate.delete(buildKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<?> keys = redisTemplate.keys(buildKey("*"));
        if (CollectionUtils.isNotEmpty(keys)) {
            final ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
            keys.stream().map(ops::get).forEach(session -> sessions.add((Session) session));
        }
        return sessions;
    }

    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        ops.set(buildKey(session.getId()), session, Constants.SESSION_TIMEOUT, TimeUnit.MINUTES);
        Long expire = redisTemplate.getExpire(buildKey(session.getId()));
        session.setTimeout(expire == null ? -1 : expire * 1000);
        log.debug("Session [{}] created, expire => {}", session.getId(), session.getTimeout());
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
