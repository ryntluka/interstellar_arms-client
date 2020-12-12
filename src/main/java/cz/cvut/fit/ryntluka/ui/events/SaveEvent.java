package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public class SaveEvent<DTO extends ModelDTO> extends FormEvent<DTO> {
    public SaveEvent(FormComponent<DTO> source, DTO entity) {
        super(source, entity);
    }
}