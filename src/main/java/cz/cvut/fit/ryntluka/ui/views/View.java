package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.resource.Resource;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public abstract class View<DTO, CreateDTO> extends VerticalLayout {
    private final Resource<DTO, CreateDTO> mainResource;

    TextField filterName = new TextField();
    TextField filterId = new TextField();

    protected View(Resource<DTO, CreateDTO> mainResource) {
        this.mainResource = mainResource;
        addClassName("list-view");
        setSizeFull();
    }

    protected void createEntity(SaveEvent evt) {
        mainResource.create(getCreateDTO(evt.getEntity()));
        updateList();
        closeCreateEditor();
    }

    protected void deleteEntity(DeleteEvent evt) {
        try {
            mainResource.delete(evt.getEntity().getId());
        } catch (HttpClientErrorException.Conflict e) {
          Notification.show(getName() + "cannot be deleted as it has items in it");
        }
        updateList();
        closeUpdateEditor();
    }

    protected void editEntity(SaveEvent evt) {
        mainResource.update(getCreateDTO(evt.getEntity()), evt.getEntity().getId());
        updateList();
        closeUpdateEditor();
    }

    private VerticalLayout createFilters() {
        VerticalLayout verticalLayout = new VerticalLayout(createNameFilter(), createIdFilter());

        com.vaadin.flow.component.button.Button resetFilters = new com.vaadin.flow.component.button.Button("Reset");
        resetFilters.addClickListener(e-> {
            updateList();
            filterName.setValue("");
            filterId.setValue("");
        });
        resetFilters.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetFilters.addClickShortcut(Key.ESCAPE);

        verticalLayout.add(resetFilters);
        return verticalLayout;
    }

    protected HorizontalLayout createToolBar() {
        Button addEntity = new Button("Add " + getName(), click -> {
            closeUpdateEditor();
            addEntity();
        });
        addEntity.setWidth("100%");
        addEntity.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout toolbar = new HorizontalLayout(createFilters(), addEntity);
        toolbar.addClassName("toolbar");
        toolbar.setAlignItems(Alignment.CENTER);
        return toolbar;
    }

    private HorizontalLayout createNameFilter() {
        filterName.setPlaceholder("Find by name...");
        filterName.setClearButtonVisible(true);

        com.vaadin.flow.component.button.Button apply = new com.vaadin.flow.component.button.Button("Find");
        apply.addThemeVariants(ButtonVariant.LUMO_ICON);
        apply.addClickListener(e-> {
            String name = filterName.getValue();
            try {
                setGridItems(mainResource.findByName(name));
            }
            catch (HttpClientErrorException.NotFound ex) {
                setGridItems(java.util.List.of());
                Notification.show(getName() + " not found!");
            }
        });
        return new HorizontalLayout(filterName, apply);
    }

    private HorizontalLayout createIdFilter() {
        filterId.setPlaceholder("Find by id...");
        filterId.setClearButtonVisible(true);

        com.vaadin.flow.component.button.Button apply = new Button("Find");
        apply.addThemeVariants(ButtonVariant.LUMO_ICON);
        apply.addClickListener(e -> {
            try {
                int id = Integer.parseInt(filterId.getValue());
                setGridItems(mainResource.findById(id));
            }
            catch (NumberFormatException ex) {
                setGridItems(java.util.List.of());
                Notification.show("Invalid input format!");
            }
            catch (HttpClientErrorException.NotFound ex) {
                setGridItems(List.of());
                Notification.show(getName() + " not found!");
            }
        });
        return new HorizontalLayout(filterId, apply);
    }

    protected void updateList() {
        setGridItems(mainResource.findAll());
    }

    protected Resource<DTO, CreateDTO> getMainResource() {
        return mainResource;
    }

    abstract void setGridItems(List<DTO> items);
    protected abstract void addEntity();
    abstract void setGridItems(DTO item);
    abstract CreateDTO getCreateDTO(ModelDTO entity);
    abstract void closeCreateEditor();
    abstract void closeUpdateEditor();
    abstract String getName();
}
