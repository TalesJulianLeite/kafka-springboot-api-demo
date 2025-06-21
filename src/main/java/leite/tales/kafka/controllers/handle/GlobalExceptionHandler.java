package leite.tales.kafka.controllers.handle;

import leite.tales.kafka.exceptions.KafkaConsumerException;
import leite.tales.kafka.exceptions.KafkaProducerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(KafkaConsumerException.class)
    public ResponseEntity<String> handleKafkaConsumerException(KafkaConsumerException ex) {
        log.error("Kafka consumer error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing Kafka message: " + ex.getMessage());
    }

    @ExceptionHandler(KafkaProducerException.class)
    public ResponseEntity<String> handleKafkaProducerException(KafkaProducerException ex) {
        log.error("Kafka consumer error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing Kafka message: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}