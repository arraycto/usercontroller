package com.platform.uc.adapter.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求缓存
 * @author hao.yan
 */
@Slf4j
//@Component
public class BizRequestCache implements RequestCache {

    static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

    private final RequestMatcher requestMatcher = AnyRequestMatcher.INSTANCE;

    private final PortResolver portResolver = new PortResolverImpl();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存请求信息
     */
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (requestMatcher.matches(request)) {
            DefaultSavedRequest savedRequest = new DefaultSavedRequest(request,
                    portResolver);

            BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(SAVED_REQUEST);
            operations.set(savedRequest);
        }else {
            log.debug("Request not saved as configured RequestMatcher did not match");
        }
    }

    /**
     * 获取保存的请求信息
     */
    @Override
    public SavedRequest getRequest(HttpServletRequest request, HttpServletResponse response) {
        BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(SAVED_REQUEST);
        return (SavedRequest) operations.get();
    }

    /**
     * 匹配请求信息
     */
    @Override
    public HttpServletRequest getMatchingRequest(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest saved = getRequest(request, response);

        if (!matchesSavedRequest(request, saved)) {
            log.debug("saved request doesn't match");
            return null;
        }

        removeRequest(request, response);

        return new SavedRequestAwareWrapper(saved, request);
    }

    private boolean matchesSavedRequest(HttpServletRequest request, SavedRequest savedRequest) {
        if (savedRequest == null) {
            return false;
        }

        if (savedRequest instanceof DefaultSavedRequest) {
            DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) savedRequest;
            return defaultSavedRequest.doesRequestMatch(request, this.portResolver);
        }

        String currentUrl = UrlUtils.buildFullRequestUrl(request);
        return savedRequest.getRedirectUrl().equals(currentUrl);
    }

    /**
     * 移除请求信息
     */
    @Override
    public void removeRequest(HttpServletRequest request, HttpServletResponse response) {
        redisTemplate.delete(SAVED_REQUEST);
    }

}
