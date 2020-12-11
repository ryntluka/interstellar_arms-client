package cz.cvut.fit.ryntluka.ui.form;

import cz.cvut.fit.ryntluka.dto.PlanetDTO;

import java.util.List;

public class CustomerCreateForm extends CustomerForm {

    public CustomerCreateForm(List<PlanetDTO> planets) {
        super(planets);
        id.setVisible(false);
        delete.setVisible(false);
    }
}
