package leite.tales.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class KafkaApplicationConfig {

    @Bean
    @Profile("dev")  // Só será criado no profile 'dev'
    public DevEnvironment configDev() {
        return new MinhaConfig("Configuração de Desenvolvimento");
    }

    @Bean
    @Profile("prod")  // Só será criado no profile 'prod'
    public MinhaConfig configProd() {
        return new MinhaConfig("Configuração de Produção");
    }
}