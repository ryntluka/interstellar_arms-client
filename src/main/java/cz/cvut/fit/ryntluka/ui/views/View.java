package cz.cvut.fit.ryntluka.ui.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.ModelDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.resource.Resource;
import cz.cvut.fit.ryntluka.ui.events.CloseEvent;
import cz.cvut.fit.ryntluka.ui.events.DeleteEvent;
import cz.cvut.fit.ryntluka.ui.events.SaveEvent;
import cz.cvut.fit.ryntluka.ui.form.CreateForm;
import cz.cvut.fit.ryntluka.ui.form.UpdateForm;
import org.springframework.web.client.HttpClientErrorException;

import java.awt.*;
import java.util.List;

public abstract class View<DTO extends ModelDTO, CreateDTO> extends VerticalLayout {
    private final Resource<DTO, CreateDTO> mainResource;

    TextField filterName = new TextField();
    TextField filterId = new TextField();
    protected final UpdateForm<DTO> updateForm;
    protected final CreateForm<DTO> createForm;

    protected View(Resource<DTO, CreateDTO> mainResource, UpdateForm<DTO> updateForm, CreateForm<DTO> createForm) {
        this.mainResource = mainResource;
        this.updateForm = updateForm;
        this.createForm = createForm;

        configureForms();

        addClassName("list-view");
        setSizeFull();
    }

    protected void createEntity(SaveEvent<DTO> evt) {
        mainResource.create(getCreateDTO(evt.getEntity()));
        updateList();
        closeCreateEditor();
    }

    protected void deleteEntity(DeleteEvent<DTO> evt) {
        try {
            mainResource.delete(evt.getEntity().getId());
        } catch (HttpClientErrorException.Conflict e) {
          Notification.show(getName() + "cannot be deleted as it has items in it");
        }
        updateList();
        closeUpdateEditor();
    }

    protected void editEntity(SaveEvent<DTO> evt) {
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

    private void configureForms() {
        updateForm.addListener(SaveEvent.class, this::editEntity);
        updateForm.addListener(DeleteEvent.class, this::deleteEntity);
        updateForm.addListener(CloseEvent.class, e -> closeUpdateEditor());

        createForm.addListener(SaveEvent.class, this::createEntity);
        createForm.addListener(CloseEvent.class, e -> closeCreateEditor());
    }

    protected void updateList() {
        setGridItems(mainResource.findAll());
    }

    protected Resource<DTO, CreateDTO> getMainResource() {
        return mainResource;
    }

    protected void closeUpdateEditor() {
        setUpdateFormEntity(null);
        updateForm.setVisible(false);
        removeClassName("editing");
    }

    protected void closeCreateEditor() {
        setCreateFormEntity(null);
        createForm.setVisible(false);
        removeClassName("editing");
    }
    protected void updateEntity(DTO dto) {
        closeCreateEditor();
        if (dto == null)
            closeUpdateEditor();
        else {
            setUpdateFormEntity(dto);
            updateForm.setVisible(true);
            addClassName("editing");
        }
    }

    abstract void setUpdateFormEntity(DTO dto);
    abstract void setCreateFormEntity(DTO dto);
    abstract void setGridItems(List<DTO> items);
    protected abstract void addEntity();
    abstract void setGridItems(DTO item);
    abstract CreateDTO getCreateDTO(DTO entity);
    abstract String getName();
}
