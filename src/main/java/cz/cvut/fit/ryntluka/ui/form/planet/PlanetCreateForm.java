package cz.cvut.fit.ryntluka.ui.form;

import cz.cvut.fit.ryntluka.dto.PlanetDTO;

import java.util.List;

public class PlanetCreateForm extends PlanetForm {

    public PlanetCreateForm(List<PlanetDTO> planets) {
        super(planets);
        id.setVisible(false);
        delete.setVisible(false);
    }
}
