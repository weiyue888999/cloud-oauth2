package com.github.cloudoauth2.server.log;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.cloudoauth2.server.common.Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class AuthorizationEndpointAuthorizeAop {

	private static final Logger log = LoggerFactory.getLogger(AuthorizationEndpointAuthorizeAop.class);

	@Pointcut("execution(public * org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint.authorize(..))")
	private void log() {
	}

	@Around("log()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String requestUrl = request.getRequestURL().toString();
		String address = Utils.getRemoteAddress(request);
		String proxyAddress = Utils.getRemoteProxyAddress(request);
		
		//参数
		Object[] args = pjp.getArgs();
		Map<String, Object> model = (Map<String, Object>) args[0];
		Map<String, String> parameters = (Map<String, String>) args[1];
		SessionStatus sessionStatus = (SessionStatus) args[2];
		Principal principal = (Principal) args[3];
		
		//用户名
		String userName = principal.getName();
		String clientId = request.getParameter("client_id");
		
		Object result = null;
		try {
			result = pjp.proceed();
			log.info("用户[{}],访问[{}]进入授权页面,授权客户端[{}],请求参数[{}],proxyAddr[{}],addr[{}]",userName,requestUrl,clientId,JSON.toJSONString(parameters),proxyAddress,address);
		} catch (Throwable t) {
			log.error("用户[{}],访问[{}]进入授权页面,授权客户端[{}],请求参数[{}],proxyAddr[{}],addr[{}],发生异常[{}]",userName,requestUrl,clientId,JSON.toJSONString(parameters),proxyAddress,address,t);
			throw t;
		} finally {
			//nothing to do
		}
		return result;
	}
}
