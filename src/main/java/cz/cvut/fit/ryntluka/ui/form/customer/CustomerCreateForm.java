package cz.cvut.fit.ryntluka.ui.form.customer;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.ui.form.CreateForm;

import java.util.List;

public class CustomerCreateForm extends CreateForm<CustomerDTO> {

    protected TextField id = new TextField("Id");
    protected TextField firstName = new TextField("First name");
    protected TextField lastName = new TextField("Last name");
    protected TextField email = new TextField("Email");
    protected ComboBox<PlanetDTO> planet = new ComboBox<>("Planet");

    public CustomerCreateForm(List<PlanetDTO> planets) {
        id.setVisible(false);
        delete.setVisible(false);
        addClassName("contact-form");
        planet.setItems(planets);
        planet.setItemLabelGenerator(PlanetDTO::getName);
        id.setReadOnly(true);

        add(
                id,
                firstName,
                lastName,
                email,
                planet,
                createButtonsLayout()
        );
    }

    @Override
    protected CustomerDTO getEntity() {
        return new CustomerDTO(Integer.parseInt(id.getValue()),
                firstName.getValue(),
                lastName.getValue(),
                email.getValue(),
                planet.getValue().getId());
    }

    public void setCustomer(CustomerDTO customer, PlanetDTO planetAddress) {
        id.setValue(Integer.toString(customer.getId()));
        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        email.setValue(customer.getEmail());
        planet.setValue(planetAddress);
    }
}
