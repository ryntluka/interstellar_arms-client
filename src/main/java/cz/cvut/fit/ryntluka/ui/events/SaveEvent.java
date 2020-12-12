package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public class SaveEvent extends FormEvent {
    public SaveEvent(FormComponent source, ModelDTO entity) {
        super(source, entity);
    }
}