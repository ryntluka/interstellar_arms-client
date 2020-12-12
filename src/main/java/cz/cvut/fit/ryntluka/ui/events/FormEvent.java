package cz.cvut.fit.ryntluka.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public abstract class FormEvent<DTO extends ModelDTO> extends ComponentEvent<FormComponent<DTO>> {
    private final DTO entity;

    protected FormEvent(FormComponent<DTO> source, DTO entity) {
        super(source, false);
        this.entity = entity;
    }

    public DTO getEntity() {
        return entity;
    }
}
