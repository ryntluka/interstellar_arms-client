package cz.cvut.fit.ryntluka.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;

import java.util.List;

public abstract class CustomerForm extends FormComponent {

    protected TextField id = new TextField("Id");
    protected TextField firstName = new TextField("First name");
    protected TextField lastName = new TextField("Last name");
    protected TextField email = new TextField("Email");
    protected ComboBox<PlanetDTO> planet = new ComboBox<>("Planet");

    protected Button save = new Button("Save");
    protected Button delete = new Button("Delete");
    protected Button close = new Button("Cancel");

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

        add(
                id,
                firstName,
                lastName,
                email,
                planet,
                createButtonsLayout()
        );
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> fireEvent(new SaveEvent(this, getCustomer())));
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, getCustomer())));
        close.addClickListener(click->fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }
}
