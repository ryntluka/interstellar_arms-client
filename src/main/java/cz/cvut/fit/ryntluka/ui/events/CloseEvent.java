package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public class CloseEvent<DTO extends ModelDTO> extends FormEvent<DTO> {
    public CloseEvent(FormComponent<DTO> source) {
        super(source, null);
    }
}
