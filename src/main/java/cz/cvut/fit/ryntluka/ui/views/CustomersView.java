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


    Grid<CustomerDTO> grid = new Grid<>(CustomerDTO.class);

    private final PlanetResource planetResource;

    public CustomersView(@Autowired CustomerResource customerResource, PlanetResource planetResource) {
        super(customerResource,
                new CustomerUpdateForm(planetResource.findAll()),
                new CustomerCreateForm(planetResource.findAll()));
        this.planetResource = planetResource;

        configureGrid();

        Div content = new Div(grid, updateForm, createForm);
        content.addClassName("content");
        content.setSizeFull();

        add(createToolBar(), content);
        closeUpdateEditor();
        closeCreateEditor();
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("customer-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email");
        grid.addColumn(c->planetResource.findById(c.getPlanetId()).getName()).setHeader("Planet");

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateEntity(event.getValue()));
    }

    protected void addEntity() {
        grid.asSingleSelect().clear();
        createForm.setVisible(true);
        addClassName("editing");
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

    @Override
    void setUpdateFormEntity(CustomerDTO customer) {
        if (customer == null)
            ((CustomerUpdateForm)updateForm).setCustomer(new CustomerDTO(0, "", "", "", 0),
                    new PlanetDTO(0, "", new Point(0,0), "", ""));
        else
            ((CustomerUpdateForm)updateForm).setCustomer(customer, planetResource.findById(customer.getPlanetId()));
    }

    @Override
    void setCreateFormEntity(CustomerDTO customer) {
        if (customer == null)
            ((CustomerCreateForm)createForm).setCustomer(new CustomerDTO(0, "", "", "", 0),
                    new PlanetDTO(0, "", new Point(0,0), "", ""));
        else
            ((CustomerCreateForm)createForm).setCustomer(customer, planetResource.findById(customer.getPlanetId()));
    }

    @Override
    CustomerCreateDTO getCreateDTO(CustomerDTO entity) {
        return toCreateDTO(entity);
    }
}
