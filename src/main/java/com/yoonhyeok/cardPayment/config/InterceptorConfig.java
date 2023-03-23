package com.yoonhyeok.cardPayment.config;

import com.yoonhyeok.cardPayment.common.ValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig
 * - Interceptor 설정
 * @author cyh68
 * @since 2023-03-18
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    
    /**
     * Interceptor 추가 메소드
     * @param registry {@link InterceptorRegistry}
     * @author cyh68
     * @since 2023-03-23
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ValidationInterceptor());
//                .order(1)
//                .addPathPatterns("/payment", "/cancel", "/find");
    }
}
