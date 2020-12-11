package cz.cvut.fit.ryntluka.dto;

import java.util.Objects;

public class CustomerCreateDTO {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final int planetId;

    public CustomerCreateDTO(String firstName, String lastName, String email, int planetId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.planetId = planetId;
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
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CustomerCreateDTO customer = (CustomerCreateDTO) obj;
        return Objects.equals(firstName, customer.firstName)
                && Objects.equals(lastName, customer.lastName)
                && Objects.equals(email, customer.email)
                && Objects.equals(planetId, customer.planetId);
    }
}
