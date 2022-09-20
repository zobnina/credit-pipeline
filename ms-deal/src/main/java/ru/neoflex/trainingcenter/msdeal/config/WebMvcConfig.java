package ru.neoflex.trainingcenter.msdeal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.neoflex.trainingcenter.liblog.LogAspect;
import ru.neoflex.trainingcenter.liblog.LogControllerInterceptor;

@Configuration
@RequiredArgsConstructor
@Import({LogControllerInterceptor.class, LogAspect.class})
public class WebMvcConfig implements WebMvcConfigurer {

    private final LogControllerInterceptor logControllerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logControllerInterceptor)
                .addPathPatterns("/deal/*");
    }
}
