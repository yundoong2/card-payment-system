package com.kakaopay.cardPayment.config;

import com.kakaopay.cardPayment.common.CommonInterceptor;
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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor())
                .order(1)
                .addPathPatterns("/payment", "/cancel", "/find");
    }
}
