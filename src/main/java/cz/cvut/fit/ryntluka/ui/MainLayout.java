package cz.cvut.fit.ryntluka.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import cz.cvut.fit.ryntluka.ui.views.CustomersView;
import cz.cvut.fit.ryntluka.ui.views.PlanetsView;
import cz.cvut.fit.ryntluka.ui.views.ProductView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink customersLink = new RouterLink("Customers", CustomersView.class);
        RouterLink planetLink = new RouterLink("Planets", PlanetsView.class);
        RouterLink productLink = new RouterLink("Products", ProductView.class);
        customersLink.setHighlightCondition(HighlightConditions.sameLocation());
        planetLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                customersLink,
                planetLink,
                productLink
        ));
    }

    private void createHeader() {
        H1 logo = new H1("Interstellar Arms Corporation");
        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }
}
