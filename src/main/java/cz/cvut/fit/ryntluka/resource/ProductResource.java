package cz.cvut.fit.ryntluka.resource;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class ProductResource implements Resource<ProductDTO, ProductCreateDTO> {

    private final RestTemplate restTemplate;

    private static final String ROOT_RESOURCE_URL = "http://localhost:8080/api/products";
    private final static String CONTENT_TYPE = "application/vnd-products+json";
    HttpHeaders headers;


    public ProductResource(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.rootUri(ROOT_RESOURCE_URL).build();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(CONTENT_TYPE));
    }

    public void create(ProductCreateDTO data) {
        restTemplate.exchange("/",
                HttpMethod.POST,
                new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<>() {}
        );
    }

    public void update(ProductCreateDTO data, int id) {
        restTemplate.exchange("/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<>() {}
        );
    }


    public ProductDTO findById(int id) {
        ResponseEntity<ProductDTO> result = restTemplate.exchange("/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public List<ProductDTO> findByName(String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ROOT_RESOURCE_URL)
                .queryParam("name", name);
        ResponseEntity<List<ProductDTO>> result = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public List<ProductDTO> findAll() {
        ResponseEntity<List<ProductDTO>> result = restTemplate.exchange("/",
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
                new ParameterizedTypeReference<ProductDTO>(){}
        );
    }

    public void order(int customerId, int productId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ROOT_RESOURCE_URL + "/" + productId)
                .queryParam("customerId", customerId);
        restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.PUT,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
