package cz.cvut.fit.ryntluka.ui.form.planet;

import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class PlanetHelperForm {

    public static void setPlanet(PlanetDTO planet, TextField id, TextField name, TextField coordinate_x,
                                 TextField coordinate_y, TextField territory, TextField nativeRace) {
        id.setValue(Integer.toString(planet.getId()));
        name.setValue(planet.getName());
        coordinate_x.setValue(Integer.toString((int)planet.getCoordinate().getX()));
        coordinate_y.setValue(Integer.toString((int)planet.getCoordinate().getY()));
        territory.setValue(planet.getTerritory());
        nativeRace.setValue(planet.getNativeRace());
    }

    private static boolean validatePlanet(TextField id, TextField name, TextField coordinate_x, TextField coordinate_y,
                                   TextField territory, TextField nativeRace) {
        boolean number = true;
        try {
            parseInt(id.getValue());
            parseInt(coordinate_x.getValue());
            parseInt(coordinate_y.getValue());
        } catch (NumberFormatException e) {
            number = false;
        }
        return number
                && !name.isEmpty()
                && !territory.isEmpty()
                && !nativeRace.isEmpty();
    }

    public static PlanetDTO getPlanet(TextField id, TextField name, TextField coordinate_x, TextField coordinate_y,
                                      TextField territory, TextField nativeRace) {
        if (validatePlanet(id, name, coordinate_x, coordinate_y, territory, nativeRace))
            return new PlanetDTO(parseInt(id.getValue()),
                name.getValue(),
                new Point(parseInt(coordinate_x.getValue()), parseInt(coordinate_y.getValue())),
                territory.getValue(),
                nativeRace.getValue());
        else
            throw new IllegalArgumentException();
    }
}
