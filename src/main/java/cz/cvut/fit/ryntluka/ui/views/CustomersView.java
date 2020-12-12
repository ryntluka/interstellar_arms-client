package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.resource.CustomerResource;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.resource.PlanetResource;
import cz.cvut.fit.ryntluka.ui.MainLayout;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.customer.CustomerCreateForm;
import cz.cvut.fit.ryntluka.ui.form.customer.CustomerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;

import static cz.cvut.fit.ryntluka.dto.CustomerCreateDTO.toCreateDTO;

@Route(value = "customers", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Customers | Interstelar Arms Corporation")
public class CustomersView extends View<CustomerDTO, CustomerCreateDTO> {

    private final CustomerUpdateForm updateForm;
    private final CustomerCreateForm createForm;
    Grid<CustomerDTO> grid = new Grid<>(CustomerDTO.class);

    private final PlanetResource planetResource;

    public CustomersView(@Autowired CustomerResource customerResource, PlanetResource planetResource) {
        super(customerResource);
        this.planetResource = planetResource;
        updateForm = new CustomerUpdateForm(planetResource.findAll());
        createForm = new CustomerCreateForm(planetResource.findAll());

        configureGrid();

        configureForms();

        Div content = new Div(grid, updateForm, createForm);
        content.addClassName("content");
        content.setSizeFull();

        add(createToolBar(), content);
        closeUpdateEditor();
        closeCreateEditor();
        updateList();
    }

    protected void closeUpdateEditor() {
        updateForm.setCustomer(
                new CustomerDTO(0, "", "", "", 0),
                new PlanetDTO(0, "", new Point(0,0), "", ""));
        updateForm.setVisible(false);
        removeClassName("editing");
    }

    @Override
    CustomerCreateDTO getCreateDTO(ModelDTO entity) {
        return toCreateDTO((CustomerDTO) entity);
    }

    protected void closeCreateEditor() {
        createForm.setCustomer(
                new CustomerDTO(0, "", "", "", 0),
                new PlanetDTO(0, "", new Point(0,0), "", ""));
        createForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureForms() {
        updateForm.addListener(SaveEvent.class, this::editEntity);
        updateForm.addListener(DeleteEvent.class, this::deleteEntity);
        updateForm.addListener(CloseEvent.class, e -> closeUpdateEditor());

        createForm.addListener(SaveEvent.class, this::createEntity);
        createForm.addListener(CloseEvent.class, e -> closeCreateEditor());
    }


    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email");
        grid.addColumn(c->planetResource.findById(c.getPlanetId()).getName()).setHeader("Planet");

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateCustomer(event.getValue()));
    }

    protected void addEntity() {
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

    @Override
    void setGridItems(List<CustomerDTO> items) {
        grid.setItems(items);
    }

    @Override
    void setGridItems(CustomerDTO items) {
        grid.setItems(items);
    }

    @Override
    String getName() {
        return "customer";
    }
}
