package leite.tales.kafka.domain.impl;

import leite.tales.kafka.domain.GenericEntity;
import lombok.Data;

@Data
public class Example extends GenericEntity<Long> {
    private String topic;
    private String group;
    private String message;
    private Long offset;
}
