package cz.cvut.fit.ryntluka.resource;

import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class CustomerResource {

    private final RestTemplate restTemplate;

    private static final String ROOT_RESOURCE_URL = "http://localhost:8080/api/customers";
    private static final String ONE_URI = "/{id}";
    private final static String CONTENT_TYPE = "application/vnd-customers+json";
    HttpHeaders headers;


    public CustomerResource(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.rootUri(ROOT_RESOURCE_URL).build();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(CONTENT_TYPE));
    }

    public URI create(CustomerDTO data) {
        return restTemplate.postForLocation("/", data);
    }

    public CustomerDTO findById(int id) {
        ResponseEntity<CustomerDTO> result = restTemplate.exchange("/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<CustomerDTO>(){}
        );
        return result.getBody();
    }

    public List<CustomerDTO> findAll() {
        ResponseEntity<List<CustomerDTO>> result = restTemplate.exchange("/",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<CustomerDTO>>(){}
        );
        return result.getBody();
    }

    public int count() {
        return 50;  //TODO
    }
}
