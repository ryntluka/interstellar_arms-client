package cz.cvut.fit.ryntluka.ui.form.customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.UpdateForm;

import java.util.List;

public class CustomerUpdateForm extends UpdateForm<CustomerDTO> {

    protected TextField id = new TextField("Id");
    protected TextField firstName = new TextField("First name");
    protected TextField lastName = new TextField("Last name");
    protected TextField email = new TextField("Email");
    protected ComboBox<PlanetDTO> planet = new ComboBox<>("Planet");

    public CustomerUpdateForm(List<PlanetDTO> planets) {
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
