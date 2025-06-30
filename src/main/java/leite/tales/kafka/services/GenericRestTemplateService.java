package leite.tales.kafka.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class GenericRestTemplateService {

    private final RestTemplate restTemplate;

    public GenericRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public <T, B> ResponseEntity<T> post(String url, B body, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<B> entity = new HttpEntity<>(body, headers);

            return restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public <T, B> ResponseEntity<T> put(String url, B body, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<B> entity = new HttpEntity<>(body, headers);

            return restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public ResponseEntity<Void> delete(String url) {
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }
}

