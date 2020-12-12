package cz.cvut.fit.ryntluka.resource;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.ModelCreateDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class CustomerResource {

    private final RestTemplate restTemplate;

    private static final String ROOT_RESOURCE_URL = "http://localhost:8080/api/customers";
    private final static String CONTENT_TYPE = "application/vnd-customers+json";
    HttpHeaders headers;


    public CustomerResource(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.rootUri(ROOT_RESOURCE_URL).build();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(CONTENT_TYPE));
    }

    public void create(CustomerCreateDTO data) {
        restTemplate.exchange("/",
                HttpMethod.POST,
                new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<>() {}
        );
    }

    public void update(CustomerCreateDTO data, int id) {
        restTemplate.exchange("/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<>() {}
        );
    }


    public CustomerDTO findById(int id) {
        ResponseEntity<CustomerDTO> result = restTemplate.exchange("/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public List<CustomerDTO> findByName(String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ROOT_RESOURCE_URL)
                .queryParam("name", name);
        ResponseEntity<List<CustomerDTO>> result = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public List<CustomerDTO> findAll() {
        ResponseEntity<List<CustomerDTO>> result = restTemplate.exchange("/",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public void delete(int id) {
        restTemplate.exchange("/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<CustomerDTO>(){}
        );
    }
}
