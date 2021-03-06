package com.platform.uc.adapter.handler;

import com.platform.uc.adapter.service.BizUserDetailsService;
import com.ztkj.framework.common.authorization.vo.OAuthUser;
import com.ztkj.framework.response.utils.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 授权
 * @author hao.yan
 */
@Slf4j
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Value("${zt.sso.private-key:}")
    private String privateKey;

    @Resource
    private BizUserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails){
            throw new UsernameNotFoundException("用户名不存在");
        }

        OAuthUser user = (OAuthUser) userDetails;
        String password = (String) authentication.getCredentials();
        log.info("登录加密的密码 = {}", password);
        // 密码 解密
        password = RSAUtils.decrypt(password, privateKey);
        log.info("登录密码解码后 = {}", password);

        // 比对密码
        log.info("password = {}, db password = {}", passwordEncoder.encode(password), user.getPassword());
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Bad credentials");
        }
        log.info("获取登录用户 == {}", user);
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("{}", authentication);
        return true;
    }

}