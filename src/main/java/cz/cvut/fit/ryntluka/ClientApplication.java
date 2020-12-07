package cz.cvut.fit.ryntluka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ClientApplication implements ApplicationRunner {
    @Autowired
    private CustomerResource customerResource;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    RestTemplateCustomizer customizer(HypermediaRestTemplateConfigurer c) {
        return restTemplate -> {c.registerHypermediaTypes(restTemplate);};
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getOptionValues("app.action") != null
            && args.getOptionValues("app.entity").contains("customer")){

        }
    }
}
