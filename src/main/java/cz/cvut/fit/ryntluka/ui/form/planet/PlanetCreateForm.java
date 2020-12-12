package cz.cvut.fit.ryntluka.ui.form.planet;

import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.ui.form.CreateForm;

import java.awt.*;
import java.util.List;

public class PlanetCreateForm extends CreateForm<PlanetDTO> {

    public PlanetCreateForm() {
        super();
        id.setVisible(false);
        delete.setVisible(false);
        addClassName("contact-form");
        id.setReadOnly(true);

        add(
                id,
                name,
                coordinate_x,
                coordinate_y,
                territory,
                nativeRace,
                createButtonsLayout()
        );
    }

    protected TextField id = new TextField("Id");
    protected TextField name = new TextField("Name");
    protected TextField coordinate_x = new TextField("X");
    protected TextField coordinate_y = new TextField("Y");
    protected TextField territory = new TextField("Territory");
    protected TextField nativeRace = new TextField("Native race");

    protected PlanetDTO getEntity() {
        return new PlanetDTO(Integer.parseInt(id.getValue()),
                name.getValue(),
                new Point(Integer.parseInt(coordinate_x.getValue()), Integer.parseInt(coordinate_y.getValue())),
                territory.getValue(),
                nativeRace.getValue());
    }

    public void setPlanet(PlanetDTO planet) {
        id.setValue(Integer.toString(planet.getId()));
        name.setValue(planet.getName());
        coordinate_x.setValue(Integer.toString((int)planet.getCoordinate().getX()));
        coordinate_y.setValue(Integer.toString((int)planet.getCoordinate().getY()));
        territory.setValue(planet.getTerritory());
        nativeRace.setValue(planet.getNativeRace());
    }
}
