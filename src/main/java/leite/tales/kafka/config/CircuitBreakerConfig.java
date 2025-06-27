package leite.tales.kafka.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfig {

    // Configuração padrão para serviços Kafka
    @Bean
    public io.github.resilience4j.circuitbreaker.CircuitBreakerConfig kafkaCircuitBreakerConfig() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .permittedNumberOfCallsInHalfOpenState(5)
                .minimumNumberOfCalls(10)
                .slidingWindowType(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(20)
                .recordExceptions(Throwable.class)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
    }

    // Registro do Circuit Breaker
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    // Circuit Breaker para o produtor Kafka
    @Bean
    public CircuitBreaker kafkaProducerCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("kafkaProducer", kafkaCircuitBreakerConfig());
    }

    // Circuit Breaker para o consumidor Kafka
    @Bean
    public CircuitBreaker kafkaConsumerCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("kafkaConsumer", kafkaCircuitBreakerConfig());
    }
}
