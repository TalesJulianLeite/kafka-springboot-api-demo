package leite.tales.kafka.config;

package leite.tales.kafka.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfig {

    // Configuração padrão para serviços Kafka
    @Bean
    public CircuitBreakerConfig kafkaCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Percentual de falhas para abrir o circuito
                .slowCallRateThreshold(50) // Percentual de chamadas lentas para abrir o circuito
                .slowCallDurationThreshold(Duration.ofSeconds(2)) // Tempo para considerar uma chamada como lenta
                .waitDurationInOpenState(Duration.ofSeconds(30)) // Tempo em estado aberto antes de tentar half-open
                .permittedNumberOfCallsInHalfOpenState(5) // Número de chamadas permitidas em half-open
                .minimumNumberOfCalls(10) // Número mínimo de chamadas para calcular as taxas
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // Tipo de janela
                .slidingWindowSize(20) // Tamanho da janela para cálculo das taxas
                .recordExceptions(Throwable.class) // Exceções que devem ser registradas como falhas
                .ignoreExceptions() // Exceções que devem ser ignoradas
                .automaticTransitionFromOpenToHalfOpenEnabled(true) // Transição automática para half-open
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
