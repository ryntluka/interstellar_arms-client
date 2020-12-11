package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.ui.views.form.CustomerForm;

public class UpdateEvent extends FormEvent {
    public UpdateEvent(CustomerForm source, CustomerDTO entity) {
        super(source, entity);
    }
}