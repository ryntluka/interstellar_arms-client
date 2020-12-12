package cz.cvut.fit.ryntluka.ui.events;

import cz.cvut.fit.ryntluka.ui.form.FormComponent;

public class CloseEvent extends FormEvent {
    public CloseEvent(FormComponent source) {
        super(source, null);
    }
}
