package cz.cvut.fit.ryntluka.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;


public class CustomerDTO extends RepresentationModel<CustomerDTO> implements ModelDTO {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int planetId;

    public CustomerDTO(int id, String firstName, String lastName, String email, int planetId)

    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.planetId = planetId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getPlanetId() {
        return planetId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CustomerDTO customer = (CustomerDTO) obj;
        return id == customer.id
                && Objects.equals(firstName, customer.firstName)
                && Objects.equals(lastName, customer.lastName)
                && Objects.equals(email, customer.email);
    }
}
