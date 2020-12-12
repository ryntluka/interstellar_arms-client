package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.cvut.fit.ryntluka.dto.*;
import cz.cvut.fit.ryntluka.resource.PlanetResource;
import cz.cvut.fit.ryntluka.ui.MainLayout;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.customer.CustomerUpdateForm;
import cz.cvut.fit.ryntluka.ui.form.planet.PlanetCreateForm;
import cz.cvut.fit.ryntluka.ui.form.planet.PlanetUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import static cz.cvut.fit.ryntluka.dto.PlanetCreateDTO.toCreateDTO;

@Route(value = "planets", layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Planets | Interstelar Arms Corporation")
public class PlanetsView extends View<PlanetDTO, PlanetCreateDTO> {

    Grid<PlanetDTO> grid = new Grid<>(PlanetDTO.class);

    public PlanetsView(@Autowired PlanetResource planetResource) {
        super(planetResource, new PlanetUpdateForm(), new PlanetCreateForm());

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
        grid.addClassName("planet-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "territory", "nativeRace");
        grid.addColumn(c-> (int)c.getCoordinate().getX() + ", " + (int)c.getCoordinate().getY()).setHeader("Coordinate");

        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateEntity(event.getValue()));
    }

    protected void addEntity() {
        grid.asSingleSelect().clear();
        createForm.setVisible(true);
        addClassName("editing");
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
    PlanetCreateDTO getCreateDTO(PlanetDTO entity) {
        return toCreateDTO(entity);
    }

    @Override
    String getName() {
        return "planet";
    }

    @Override
    void setUpdateFormEntity(PlanetDTO dto) {
        ((PlanetUpdateForm) updateForm)
                .setPlanet(Objects.requireNonNullElseGet(
                        dto, () -> new PlanetDTO(0, "", new Point(0, 0), "", ""))
                );
    }

    @Override
    void setCreateFormEntity(PlanetDTO dto) {
        ((PlanetCreateForm) createForm)
                .setPlanet(Objects.requireNonNullElseGet(
                        dto, () -> new PlanetDTO(0, "", new Point(0, 0), "", ""))
                );
    }
}
