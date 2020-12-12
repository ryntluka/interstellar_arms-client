package cz.cvut.fit.ryntluka.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public abstract class FormEvent extends ComponentEvent<FormComponent> {
    private final ModelDTO entity;

    protected FormEvent(FormComponent source, ModelDTO entity) {
        super(source, false);
        this.entity = entity;
    }

    public ModelDTO getEntity() {
        return entity;
    }
}
