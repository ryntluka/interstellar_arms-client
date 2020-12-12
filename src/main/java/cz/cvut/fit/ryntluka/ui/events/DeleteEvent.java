package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public class DeleteEvent<DTO extends ModelDTO> extends FormEvent<DTO> {
    public DeleteEvent(FormComponent<DTO> source, DTO entity) {
        super(source, entity);
    }

}