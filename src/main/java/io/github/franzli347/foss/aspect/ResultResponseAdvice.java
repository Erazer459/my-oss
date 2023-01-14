package io.github.franzli347.foss.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
/**
 * 统一返回结果切面
 * @author FranzLi
 */
@RestControllerAdvice(basePackages = "io.github.franzli347.foss.controller")

public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 不拦截Result类型相应和swagger
        return !returnType.getGenericParameterType().equals(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null || body instanceof Result) {
            return body;
        }
        Result result = Result.builder().code(ResultCode.CODE_SUCCESS).data(body).build();
        if(body instanceof String){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(result);
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
        return result;
    }
}
