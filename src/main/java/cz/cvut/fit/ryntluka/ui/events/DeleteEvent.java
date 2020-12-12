package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public class DeleteEvent extends FormEvent {
    public DeleteEvent(FormComponent source, ModelDTO entity) {
        super(source, entity);
    }

}