package cz.cvut.fit.ryntluka.ui.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.ui.views.CustomerForm;

public abstract class FormEvent<Type, Form extends Component> extends ComponentEvent<Form> {
    private final Type entity;

    protected FormEvent(Form source, Type entity) {
        super(source, false);
        this.entity = entity;
    }

    public Type getEntity() {
        return entity;
    }
}
