package com.swithun.backend.config.uniform_response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object arg0, MethodParameter arg1, MediaType arg2,
            Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest arg4, ServerHttpResponse arg5) {
        if (arg0 == null) {
            return UnifiedResponsor.ofFail(CommonStatusCodeEnum.SUCCESS_COMMON);
        } else if (arg0 instanceof UnifiedResponsor) {
            return arg0;
        } else {
            return UnifiedResponsor.ofSuccess(CommonStatusCodeEnum.SUCCESS_COMMON, arg0);
        }
    }

}
