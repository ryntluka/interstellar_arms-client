package cz.cvut.fit.ryntluka.ui.form.customer;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;

import static java.lang.Integer.parseInt;

public class CustomerHelperForm {

    public static CustomerDTO getCustomer(TextField id, TextField firstName, TextField lastName,
                                        TextField email, ComboBox<PlanetDTO> planet) {
        if (validateCustomer(id, firstName, lastName, email, planet))
            return new CustomerDTO(Integer.parseInt(id.getValue()),
                firstName.getValue(),
                lastName.getValue(),
                email.getValue(),
                planet.getValue().getId());
        else
            throw new IllegalArgumentException();
    }

    private static boolean validateCustomer(TextField id, TextField firstName, TextField lastName,
                                            TextField email, ComboBox<PlanetDTO> planet) {
        boolean number = true;
        try {
            parseInt(id.getValue());
        } catch (NumberFormatException e) {
            number = false;
        }
        return number
                && !firstName.isEmpty()
                && !lastName.isEmpty()
                && !email.isEmpty()
                && !planet.isEmpty();
    }

    public static void setCustomer(CustomerDTO customer, PlanetDTO planetAddress,
                                   TextField id, TextField firstName, TextField lastName,
                                   TextField email, ComboBox<PlanetDTO> planet) {
        id.setValue(Integer.toString(customer.getId()));
        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        email.setValue(customer.getEmail());
        planet.setValue(planetAddress);
    }
}
