package cz.cvut.fit.ryntluka.resource;

import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.PlanetCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class PlanetResource implements Resource<PlanetDTO, PlanetCreateDTO> {

    private final RestTemplate restTemplate;

    private static final String ROOT_RESOURCE_URL = "http://localhost:8080/api/planets";
    private static final String ONE_URI = "/{id}";
    private final static String CONTENT_TYPE = "application/vnd-planets+json";
    HttpHeaders headers;


    public PlanetResource(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.rootUri(ROOT_RESOURCE_URL).build();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(CONTENT_TYPE));
    }

    public void create(PlanetCreateDTO data) {
        restTemplate.exchange("/",
                HttpMethod.POST,
                new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<>() {}
        );
    }

    public void update(PlanetCreateDTO data, int id) {
        restTemplate.exchange("/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(data, headers),
                new ParameterizedTypeReference<>() {}
        );
    }


    public PlanetDTO findById(int id) {
        ResponseEntity<PlanetDTO> result = restTemplate.exchange("/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public List<PlanetDTO> findByName(String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ROOT_RESOURCE_URL)
                .queryParam("name", name);
        ResponseEntity<List<PlanetDTO>> result = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getBody();
    }

    public List<PlanetDTO> findAll() {
        ResponseEntity<List<PlanetDTO>> result = restTemplate.exchange("/",
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
                new ParameterizedTypeReference<PlanetDTO>(){}
        );
    }
}
