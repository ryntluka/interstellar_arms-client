package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.ryntluka.CustomerResource;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Customers | Interstelar Arms Corporation")
public class ListView extends VerticalLayout {

    private final ContactForm form;
    Grid<CustomerDTO> grid = new Grid<>(CustomerDTO.class);
    TextField filterText = new TextField();

    private final CustomerResource customerResource;

    public ListView(@Autowired CustomerResource customerResource) {
        this.customerResource = customerResource;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();

        form = new ContactForm();

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);
        updateList();
        closeEditor();
    }

    private void closeEditor() {
//        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
//        grid.setItems(customerResource.findByName);
        grid.setItems(customerResource.testData());
    }

    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email");

        grid.getColumns().forEach(col->col.setAutoWidth(true));
        
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private void editContact(CustomerDTO customer) {
        if (customer == null)
            closeEditor();
        else {
//            form.setCustomer(customer);
            form.setVisible(true);
            addClassName("editing");
        }

    }


}
