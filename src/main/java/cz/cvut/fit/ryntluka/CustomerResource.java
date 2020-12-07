package cz.cvut.fit.ryntluka;

import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class CustomerResource {

    private final RestTemplate restTemplate;

    private static final String COLLECTION_PAGED_URI = "?page={page}&size={size}";
    private static final String ROOT_RESOURCE_URL = "http://localhost:8080/api/customers";
    private static final String ONE_URI = "/{id}";

    public CustomerResource(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.rootUri(ROOT_RESOURCE_URL).build();
    }

    public URI create(CustomerDTO data) {
        return restTemplate.postForLocation("/", data);
    }

    public CustomerDTO findById(int id) {
        return restTemplate.getForObject(ONE_URI, CustomerDTO.class, id);
    }

    public PagedModel<CustomerDTO> findAll(int page, int size) {
        ResponseEntity<PagedModel<CustomerDTO>> result = restTemplate.exchange(COLLECTION_PAGED_URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PagedModel<CustomerDTO>>(){},
                page,
                size
        );
        return result.getBody();
    }
}
