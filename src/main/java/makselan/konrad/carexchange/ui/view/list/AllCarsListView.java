package makselan.konrad.carexchange.ui.view.list;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import makselan.konrad.carexchange.backend.entity.User;
import makselan.konrad.carexchange.backend.service.CarService;
import makselan.konrad.carexchange.ui.MainLayout;
import makselan.konrad.carexchange.ui.view.list.broadcaster.Broadcaster;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Cars | Car Exchange")
public class AllCarsListView extends VerticalLayout {

    private FilterTextField filterTextField = new FilterTextField();
    private CarGrid grid = new CarGrid();
    private UserDetailsCard userDetailsCard = new UserDetailsCard();

    private CarService carService;

    private Registration broadcasterRegistration;

    @Autowired
    public AllCarsListView(CarService carService) {
        this.carService = carService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureUserDetailsCard();

        Div content = new Div(grid, userDetailsCard);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateListOfCars();
        closeUserDetailsCard();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = Broadcaster.register(newMessage -> {
            ui.access(() -> updateListOfCars());
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

    private void configureGrid(){
        grid.asSingleSelect().addValueChangeListener(
          singleCarSelected -> showSellerDetails(singleCarSelected.getValue().getUserWhoPosted())
        );
    }

    private void configureUserDetailsCard(){
        userDetailsCard.addListener(UserDetailsCard.CloseEvent.class, event -> closeUserDetailsCard());
    }

    private HorizontalLayout getToolbar() {
        filterTextField.addValueChangeListener(valueChanged -> updateListOfCars());
        HorizontalLayout toolbar = new HorizontalLayout(filterTextField);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void showSellerDetails(User seller){
        if(seller == null){
            closeUserDetailsCard();
        } else {
            userDetailsCard.setUser(seller);
            userDetailsCard.setVisible(true);
            addClassName("showing-seller-details");
        }
    }

    private void closeUserDetailsCard() {
        userDetailsCard.setUser(null);
        userDetailsCard.setVisible(false);
        removeClassName("showing-seller-details");
    }

    private void updateListOfCars() {
        grid.setItems(carService.findAll());
    }
}
