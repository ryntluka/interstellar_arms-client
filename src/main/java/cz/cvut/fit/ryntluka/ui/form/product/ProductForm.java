package cz.cvut.fit.ryntluka.ui.form.product;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

import java.util.List;

public abstract class CustomerForm extends FormComponent {

    protected TextField id = new TextField("Id");
    protected TextField firstName = new TextField("First name");
    protected TextField lastName = new TextField("Last name");
    protected TextField email = new TextField("Email");
    protected ComboBox<PlanetDTO> planet = new ComboBox<>("Planet");

    private CustomerDTO getCustomer() {
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

    public CustomerForm(List<PlanetDTO> planets) {
        addClassName("contact-form");
        planet.setItems(planets);
        planet.setItemLabelGenerator(PlanetDTO::getName);
        id.setReadOnly(true);
        addButtonsListeners();

        add(
                id,
                firstName,
                lastName,
                email,
                planet,
                createButtonsLayout()
        );
    }

    protected void addButtonsListeners() {
        save.addClickListener(click -> fireEvent(new SaveEvent(this, getCustomer())));
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, getCustomer())));
    }
}
