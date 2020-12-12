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
import cz.cvut.fit.ryntluka.dto.*;
import cz.cvut.fit.ryntluka.resource.PlanetResource;
import cz.cvut.fit.ryntluka.ui.MainLayout;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.planet.PlanetCreateForm;
import cz.cvut.fit.ryntluka.ui.form.planet.PlanetUpdateForm;
import cz.cvut.fit.ryntluka.ui.form.planet.PlanetCreateForm;
import cz.cvut.fit.ryntluka.ui.form.planet.PlanetUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.awt.*;
import java.util.List;

import static cz.cvut.fit.ryntluka.dto.PlanetCreateDTO.toCreateDTO;

@Route(value = "planets", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Planets | Interstelar Arms Corporation")
public class PlanetsView extends View<PlanetDTO, PlanetCreateDTO> {

    private final PlanetUpdateForm updateForm;
    private final PlanetCreateForm createForm;
    Grid<PlanetDTO> grid = new Grid<>(PlanetDTO.class);

    public PlanetsView(@Autowired PlanetResource planetResource) {
        super(planetResource);
        updateForm = new PlanetUpdateForm();
        createForm = new PlanetCreateForm();

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

    private void configureForms() {
        updateForm.addListener(SaveEvent.class, this::editEntity);
        updateForm.addListener(DeleteEvent.class, this::deleteEntity);
        updateForm.addListener(CloseEvent.class, e -> closeUpdateEditor());

        createForm.addListener(SaveEvent.class, this::createEntity);
        createForm.addListener(CloseEvent.class, e -> closeCreateEditor());
    }

    protected void closeUpdateEditor() {
        updateForm.setPlanet(
                new PlanetDTO(0, "", new Point(0,0), "", ""));
        updateForm.setVisible(false);
        removeClassName("editing");
    }

    protected void closeCreateEditor() {
        createForm.setPlanet(
                new PlanetDTO(0, "", new Point(0,0), "", ""));
        createForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("planet-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "territory", "nativeRace");
        grid.addColumn(c-> (int)c.getCoordinate().getX() + ", " + (int)c.getCoordinate().getY()).setHeader("Coordinate");

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updatePlanet(event.getValue()));
    }

    protected void addEntity() {
        grid.asSingleSelect().clear();
        createForm.setVisible(true);
        addClassName("editing");
    }

    private void updatePlanet(PlanetDTO planet) {
        closeCreateEditor();
        if (planet == null)
            closeUpdateEditor();
        else {
            updateForm.setPlanet(planet);
            updateForm.setVisible(true);
            addClassName("editing");
        }
    }

    @Override
    void setGridItems(List<PlanetDTO> items) {
        grid.setItems(items);
    }

    @Override
    void setGridItems(PlanetDTO item) {
        grid.setItems(item);
    }

    @Override
    PlanetCreateDTO getCreateDTO(ModelDTO entity) {
        return toCreateDTO((PlanetDTO) entity);
    }

    @Override
    String getName() {
        return "planet";
    }
}
