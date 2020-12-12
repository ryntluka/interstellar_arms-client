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
        return new OrderDTO(customer.getValue().getId(), product.getValue().getId());
    }

    public OrderForm(List<CustomerDTO> customers, List<ProductDTO> products) {
        addClassName("contact-form");
        delete.setVisible(false);
        customer.setItems(customers);
        customer.setItemLabelGenerator(c -> c.getFirstName() + " " + c.getLastName());
        product.setItems(products);
        product.setItemLabelGenerator(ProductDTO::getName);

        add(
                customer,
                product,
                createButtonsLayout()
        );
    }
}
