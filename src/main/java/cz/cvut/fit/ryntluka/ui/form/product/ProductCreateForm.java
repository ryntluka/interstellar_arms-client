package cz.cvut.fit.ryntluka.ui.form.product;

import cz.cvut.fit.ryntluka.dto.PlanetDTO;

import java.util.List;

public class ProductCreateForm extends CustomerForm {

    public ProductCreateForm(List<PlanetDTO> planets) {
        super(planets);
        id.setVisible(false);
        delete.setVisible(false);
    }
}
