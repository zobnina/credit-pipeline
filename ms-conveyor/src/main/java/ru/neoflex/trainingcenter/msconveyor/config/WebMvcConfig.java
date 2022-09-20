package ru.neoflex.trainingcenter.msconveyor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.azobnina.liblog.LogAspect;
import ru.azobnina.liblog.web.LogControllerInterceptor;

@Configuration
@RequiredArgsConstructor
@Import({LogControllerInterceptor.class, LogAspect.class})
public class WebMvcConfig implements WebMvcConfigurer {

    private final LogControllerInterceptor logControllerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logControllerInterceptor)
                .addPathPatterns("/conveyor/*");
    }
}
