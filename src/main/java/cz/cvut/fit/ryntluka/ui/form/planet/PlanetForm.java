package cz.cvut.fit.ryntluka.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;

import java.awt.*;
import java.util.List;

public abstract class PlanetForm extends FormLayout {

    protected TextField id = new TextField("Id");
    protected TextField name = new TextField("Name");
    protected TextField coordinate_x = new TextField("X");
    protected TextField coordinate_y = new TextField("Y");
    protected TextField territory = new TextField("Territory");
    protected TextField nativeRace = new TextField("Native race");

    protected Button save = new Button("Save");
    protected Button delete = new Button("Delete");
    protected Button close = new Button("Cancel");

    private PlanetDTO getPlanet() {
        return new PlanetDTO(Integer.parseInt(id.getValue()),
                name.getValue(),
                new Point(Integer.parseInt(coordinate_x.getValue()),
                        Integer.parseInt(coordinate_y.getValue())),
                territory.getValue(),
                nativeRace.getValue());
    }

    public void setPlanet(PlanetDTO planet) {
        id.setValue(Integer.toString(planet.getId()));
        name.setValue(planet.getName());
        coordinate_x.setValue(String.valueOf(planet.getCoordinate().getX()));
        coordinate_y.setValue(String.valueOf(planet.getCoordinate().getY()));
        territory.setValue(planet.getTerritory());
        nativeRace.setValue(planet.getNativeRace());
    }

    public PlanetForm(List<PlanetDTO> planets) {
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

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

//        save.addClickListener(click -> fireEvent(new SaveEvent(this, getPlanet())));
//        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, getPlanet())));
//        close.addClickListener(click->fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }


}
