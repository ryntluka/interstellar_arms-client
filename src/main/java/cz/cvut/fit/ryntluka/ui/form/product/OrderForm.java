package cz.cvut.fit.ryntluka.ui.form.product;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.*;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

import java.util.List;

public class OrderForm extends FormComponent<OrderDTO> {

    protected ComboBox<CustomerDTO> customer = new ComboBox<>("Customer");
    protected ComboBox<ProductDTO> product = new ComboBox<>("Product");

    protected OrderDTO getEntity() {
        if (customer.isEmpty() || product.isEmpty())
            throw new IllegalArgumentException();
        return new OrderDTO(customer.getValue().getId(), product.getValue().getId());
    }

    public OrderForm(List<CustomerDTO> customers, List<ProductDTO> products) {
        addClassName("contact-form");
        customer.setItems(customers);
        customer.setItemLabelGenerator(c -> c.getFirstName() + " " + c.getLastName());
        customer.setAllowCustomValue(false);
        product.setItems(products);
        product.setItemLabelGenerator(ProductDTO::getName);
        product.setAllowCustomValue(false);

        add(
                customer,
                product,
                createButtonsLayout()
        );
    }

    public void update(List<CustomerDTO> customers, List<ProductDTO> products) {
        customer.setItems(customers);
        product.setItems(products);
    }
}
