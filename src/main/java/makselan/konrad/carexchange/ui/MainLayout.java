package makselan.konrad.carexchange.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import makselan.konrad.carexchange.ui.view.list.AllCarsListView;
import makselan.konrad.carexchange.ui.view.list.UserCarsListView;

@Push
@CssImport("./styles/shared-styles.css")
@PWA(
        name = "Car Exchange",
        shortName = "CarEx"
)
public class MainLayout extends AppLayout {

    public MainLayout(){
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 appLogo = new H1("Car Exchange");
        appLogo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), appLogo, logout);
        header.expand(appLogo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink allCarsListLink = new RouterLink("Cars", AllCarsListView.class);
        allCarsListLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink userCarsListLink = new RouterLink("My cars", UserCarsListView.class);

        addToDrawer(new VerticalLayout(
                allCarsListLink,
                userCarsListLink));
    }

}
