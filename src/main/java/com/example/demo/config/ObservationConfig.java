package com.example.demo.config;

import com.example.demo.aop.ServiceMethodTracingAspect;
import io.lettuce.core.resource.DefaultClientResources;
import io.micrometer.observation.ObservationRegistry;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.ClientResourcesBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.observability.MicrometerTracingAdapter;

@Configuration
public class ObservationConfig {

    @Bean
    public OtlpHttpSpanExporter httpSpanExporter() {
        return OtlpHttpSpanExporter.getDefault();
    }

    @Bean
    public MicrometerTracingAdapter micrometerTracingAdapter(ObservationRegistry observationRegistry) {
        return new MicrometerTracingAdapter(observationRegistry, "redis");
    }

    @Bean
    public ServiceMethodTracingAspect serviceMethodTracingAspect(ObservationRegistry observationRegistry) {
        return new ServiceMethodTracingAspect(observationRegistry);
    }

    @Bean(destroyMethod = "shutdown")
    public DefaultClientResources lettuceClientResources(ObjectProvider<ClientResourcesBuilderCustomizer> customizers,
                                                         MicrometerTracingAdapter micrometerTracingAdapter) {
        DefaultClientResources.Builder builder = DefaultClientResources.builder();
        customizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        builder.tracing(micrometerTracingAdapter);
        return builder.build();
    }

}
