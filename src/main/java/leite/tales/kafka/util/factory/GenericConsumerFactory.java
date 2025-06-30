package leite.tales.kafka.util.factory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

public class GenericConsumerFactory<T> {

    private final ConcurrentKafkaListenerContainerFactory<String, T> factory;

    public GenericConsumerFactory(Map<String, Object> consumerConfigs, Class<T> payloadType) {
        this.factory = createContainerFactory(consumerConfigs, payloadType);
    }

    private ConcurrentKafkaListenerContainerFactory<String, T> createContainerFactory(
            Map<String, Object> consumerConfigs,
            Class<T> payloadType
    ) {
        ConsumerFactory<String, T> consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerConfigs,
                new StringDeserializer(),
                new JsonDeserializer<>(payloadType, objectMapper()) // Configura desserializador JSON
        );

        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public ConcurrentKafkaListenerContainerFactory<String, T> getFactory() {
        return this.factory;
    }
}