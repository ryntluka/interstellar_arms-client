package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.resource.CustomerResource;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.resource.PlanetResource;
import cz.cvut.fit.ryntluka.ui.MainLayout;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.views.form.CustomerCreateForm;
import cz.cvut.fit.ryntluka.ui.views.form.CustomerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.awt.*;
import java.util.List;

import static cz.cvut.fit.ryntluka.dto.CustomerCreateDTO.toCreateDTO;

@Route(value = "", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Customers | Interstelar Arms Corporation")
public class CustomersView extends VerticalLayout {

    private final CustomerUpdateForm updateForm;
    private final CustomerCreateForm createForm;
    Grid<CustomerDTO> grid = new Grid<>(CustomerDTO.class);

    private final CustomerResource customerResource;
    private final PlanetResource planetResource;

    TextField filterName = new TextField();
    TextField filterId = new TextField();

    public CustomersView(@Autowired CustomerResource customerResource, PlanetResource planetResource) {
        this.customerResource = customerResource;
        this.planetResource = planetResource;
        updateForm = new CustomerUpdateForm(planetResource.findAll());
        createForm = new CustomerCreateForm(planetResource.findAll());

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        configureForms();

        Div content = new Div(grid, updateForm, createForm);
        content.addClassName("content");
        content.setSizeFull();

        add(createToolBar(), content);
        updateList();
        closeUpdateEditor();
        closeCreateEditor();
    }

    private void configureForms() {
        updateForm.addListener(SaveEvent.class, this::editCustomer);
        updateForm.addListener(DeleteEvent.class, this::deleteCustomer);
        updateForm.addListener(CloseEvent.class, e -> closeUpdateEditor());

        createForm.addListener(SaveEvent.class, this::createCustomer);
        createForm.addListener(CloseEvent.class, e -> closeCreateEditor());
    }

    private Component createToolBar() {
        Button addCustomer = new Button("Add contact", click -> {
            closeUpdateEditor();
            addCustomer();
        });
        HorizontalLayout toolbar = new HorizontalLayout(createFilters(), addCustomer);
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.CENTER);
        return toolbar;
    }

    private void createCustomer(SaveEvent evt) {
        customerResource.create(toCreateDTO(evt.getEntity()));
        updateList();
        closeCreateEditor();
    }

    private void deleteCustomer(DeleteEvent evt) {
        customerResource.delete(evt.getEntity().getId());
        updateList();
        closeUpdateEditor();
    }

    private void editCustomer(SaveEvent evt) {
        customerResource.update(toCreateDTO(evt.getEntity()), evt.getEntity().getId());
        updateList();
        closeUpdateEditor();
    }

    private void closeUpdateEditor() {
        updateForm.setCustomer(
                new CustomerDTO(0, "", "", "", 0),
                new PlanetDTO(0, "", new Point(0,0), "", ""));
        updateForm.setVisible(false);
        removeClassName("editing");
    }

    private void closeCreateEditor() {
        createForm.setCustomer(
                new CustomerDTO(0, "", "", "", 0),
                new PlanetDTO(0, "", new Point(0,0), "", ""));
        createForm.setVisible(false);
        removeClassName("editing");
    }

    private VerticalLayout createFilters() {
        VerticalLayout verticalLayout = new VerticalLayout(createNameFilter(), createIdFilter());

        Button resetFilters = new Button("Reset");
        resetFilters.addClickListener(e-> {
            updateList();
            filterName.setValue("");
            filterId.setValue("");
        });
        resetFilters.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetFilters.addClickShortcut(Key.ESCAPE);

        verticalLayout.add(resetFilters);
        return verticalLayout;
    }

    private HorizontalLayout createNameFilter() {
        filterName.setPlaceholder("Find by name...");
        filterName.setClearButtonVisible(true);

        Button apply = new Button("Find");
        apply.addThemeVariants(ButtonVariant.LUMO_ICON);
        apply.addClickListener(e-> {
            String name = filterName.getValue();
            try {
                grid.setItems(customerResource.findByName(name));
            }
            catch (HttpClientErrorException.NotFound ex) {
                grid.setItems(List.of());
                Notification.show("Customer not found!");
            }
        });
        return new HorizontalLayout(filterName, apply);
    }

    private HorizontalLayout createIdFilter() {
        filterId.setPlaceholder("Find by id...");
        filterId.setClearButtonVisible(true);

        Button apply = new Button("Find");
        apply.addThemeVariants(ButtonVariant.LUMO_ICON);
        apply.addClickListener(e -> {
            try {
                int id = Integer.parseInt(filterId.getValue());
                grid.setItems(customerResource.findById(id));
            }
            catch (NumberFormatException ex) {
                grid.setItems(List.of());
                Notification.show("Invalid input format!");
            }
            catch (HttpClientErrorException.NotFound ex) {
                grid.setItems(List.of());
                Notification.show("Customer not found!");
            }
        });
        return new HorizontalLayout(filterId, apply);
    }

    private void updateList() {
        grid.setItems(customerResource.findAll());
    }

    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email");
        grid.addColumn(c->planetResource.findById(c.getPlanetId()).getName()).setHeader("Planet");

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateCustomer(event.getValue()));
    }

    private void addCustomer() {
        grid.asSingleSelect().clear();
        createForm.setVisible(true);
        addClassName("editing");
    }

    private void updateCustomer(CustomerDTO customer) {
        closeCreateEditor();
        if (customer == null)
            closeUpdateEditor();
        else {
            updateForm.setCustomer(customer, planetResource.findById(customer.getPlanetId()));
            updateForm.setVisible(true);
            addClassName("editing");
        }
    }


}
