package cz.cvut.fit.ryntluka.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;

public abstract class FormComponent<DTO extends ModelDTO> extends FormLayout {

    protected Button save = new Button("Save");
    protected Button delete = new Button("Delete");
    protected Button close = new Button("Cancel");

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    protected Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER).listenOn(this);
        close.addClickShortcut(Key.ESCAPE).listenOn(this);

        close.addClickListener(click-> fireEvent(new CloseEvent(this)));
        save.addClickListener(click -> {
            try {
                fireEvent(new SaveEvent(this, getEntity()));
            } catch (IllegalArgumentException e) {
                Notification.show("Form filled incorrectly.");
            }
        });
        delete.addClickListener(click -> {
            try {
                fireEvent(new DeleteEvent(this, getEntity()));
            } catch (IllegalArgumentException e) {
                Notification.show("Form filled incorrectly.");
        }
        });

        close.setSizeFull();
        save.setSizeFull();
        delete.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout(save, delete, close);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    protected abstract DTO getEntity();
}
