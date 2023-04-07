package io.github.franzli347.foss.aspect;

import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.constant.AuthConstant;
import io.github.franzli347.foss.exception.BucketException;
import io.github.franzli347.foss.utils.AuthUtil;
import io.github.franzli347.foss.web.service.BucketPrivilegeService;
import io.github.franzli347.foss.web.service.FilesService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @ClassName AuthAspect
 * @Author AlanC
 * @Date 2023/1/12 21:31
 **/
@Aspect
@Component
@Slf4j
public class AuthAspect {
    private Object bid;
    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    private final BucketPrivilegeService privilegeService;

    private final FilesService filesService;

    public AuthAspect(BucketPrivilegeService privilegeService, FilesService filesService) {
        this.privilegeService = privilegeService;
        this.filesService = filesService;
    }
    @Pointcut(value = "@annotation(io.github.franzli347.foss.annotation.CheckBucketPrivilege)")
    public void pointcut() {}
    @Before("pointcut() && @annotation(checkBucketPrivilege)")
    public void Auth(JoinPoint joinPoint, CheckBucketPrivilege checkBucketPrivilege){
        String[] privilege=checkBucketPrivilege.privilege();
        String id=generateKeyBySpEL(checkBucketPrivilege.spelString(),joinPoint);
        if (checkBucketPrivilege.argType().equals(AuthConstant.BID)){
            bid=id;
        }
        else if (checkBucketPrivilege.argType().equals(AuthConstant.PRIVILEGE_ID)){
            bid = Optional.ofNullable(privilegeService.getById(id)).orElseThrow(() -> new BucketException("权限认证失败!")).getBid();
        } else if (checkBucketPrivilege.argType().equals(AuthConstant.FILE_ID)) {
            bid= Optional.ofNullable(filesService.getById(id)).orElseThrow(() -> new BucketException("权限认证失败!")).getBid();
        }
        for(int i = 0; i < privilege.length; i++){
            privilege[i]= AuthUtil.bidConcat(bid,privilege[i]);
        }
        StpUtil.checkPermissionOr(privilege);
    }
    public String generateKeyBySpEL(String spELString, JoinPoint joinPoint) {
        // 通过joinPoint获取被注解方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 使用spring的DefaultParameterNameDiscoverer获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        // 解析过后的Spring表达式对象
        Expression expression = parser.parseExpression(spELString);
        // spring的表达式上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        // 通过joinPoint获取被注解方法的形参
        Object[] args = joinPoint.getArgs();
        // 给上下文赋值
        for(int i = 0 ; i < args.length ; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return String.valueOf(expression.getValue(context));
    }
}
