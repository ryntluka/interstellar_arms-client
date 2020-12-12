package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
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
import cz.cvut.fit.ryntluka.dto.*;
import cz.cvut.fit.ryntluka.resource.CustomerResource;
import cz.cvut.fit.ryntluka.resource.ProductResource;
import cz.cvut.fit.ryntluka.ui.MainLayout;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.product.OrderForm;
import cz.cvut.fit.ryntluka.ui.form.product.ProductCreateForm;
import cz.cvut.fit.ryntluka.ui.form.product.ProductUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.dto.ProductCreateDTO.toCreateDTO;

@Route(value = "products", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Products | Interstelar Arms Corporation")
public class ProductView extends View<ProductDTO, ProductCreateDTO> {

    private final ProductUpdateForm updateForm;
    private final ProductCreateForm createForm;
    private final OrderForm orderForm;
    Grid<ProductDTO> grid = new Grid<>(ProductDTO.class);

    private final CustomerResource customerResource;

    public ProductView(@Autowired ProductResource productResource, CustomerResource customerResource) {
        super(productResource);
        this.customerResource = customerResource;
        orderForm = new OrderForm(customerResource.findAll(), productResource.findAll());
        updateForm = new ProductUpdateForm();
        createForm = new ProductCreateForm();

        configureGrid();

        configureForms();

        Div content = new Div(grid, updateForm, createForm, orderForm);
        content.addClassName("content");
        content.setSizeFull();

        HorizontalLayout toolBar = createToolBar();
        Button order = new Button("Order", e -> openOrder());
        order.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        toolBar.add(order);

        add(toolBar, content);
        updateList();
        closeUpdateEditor();
        closeCreateEditor();
        closeOrderEditor();
    }

    private void configureForms() {
        updateForm.addListener(SaveEvent.class, this::editEntity);
        updateForm.addListener(DeleteEvent.class, this::deleteEntity);
        updateForm.addListener(CloseEvent.class, e -> closeUpdateEditor());

        createForm.addListener(SaveEvent.class, this::createEntity);
        createForm.addListener(CloseEvent.class, e -> closeCreateEditor());

        orderForm.addListener(SaveEvent.class, this::orderProduct);
    }

    private void openOrder() {
        closeCreateEditor();
        closeUpdateEditor();
        grid.asSingleSelect().clear();
        orderForm.setVisible(true);
        addClassName("editing");
    }

    private void orderProduct(SaveEvent evt) {
        OrderDTO orderDTO = (OrderDTO) evt.getEntity();
        ((ProductResource)getMainResource()).order(orderDTO.getCustomerId(), orderDTO.getProductId());
        updateList();
    }

    protected void closeUpdateEditor() {
        updateForm.setProduct(
                new ProductDTO(0, 0, "", List.of()));
        updateForm.setVisible(false);
        removeClassName("editing");
    }

    protected void closeCreateEditor() {
        createForm.setProduct(
                new ProductDTO(0, 0, "", List.of()));
        createForm.setVisible(false);
        removeClassName("editing");
    }

    private void closeOrderEditor() {
        orderForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("product-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "price");
        grid.addColumn(product -> product.getOrdersIds()
                .stream()
                .map(c -> {
                    CustomerDTO customer = customerResource.findById(c);
                    return customer.getFirstName() + " " + customer.getLastName();
                })
                .collect(Collectors.joining(", ")))
        .setHeader("Orders");

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateProduct(event.getValue()));
    }

    protected void addEntity() {
        grid.asSingleSelect().clear();
        createForm.setVisible(true);
        addClassName("editing");
    }

    private void updateProduct(ProductDTO product) {
        closeCreateEditor();
        closeOrderEditor();
        if (product == null)
            closeUpdateEditor();
        else {
            updateForm.setVisible(true);
            addClassName("editing");
        }
    }

    @Override
    void setGridItems(List<ProductDTO> items) {
        grid.setItems(items);
    }

    @Override
    void setGridItems(ProductDTO item) {
        grid.setItems(item);
    }

    @Override
    ProductCreateDTO getCreateDTO(ModelDTO entity) {
        return toCreateDTO((ProductDTO) entity);
    }

    @Override
    String getName() {
        return "product";
    }
}
