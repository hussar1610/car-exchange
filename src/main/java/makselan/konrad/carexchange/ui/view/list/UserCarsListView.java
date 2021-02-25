package makselan.konrad.carexchange.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import makselan.konrad.carexchange.backend.entity.Car;
import makselan.konrad.carexchange.backend.entity.User;
import makselan.konrad.carexchange.backend.service.CarService;
import makselan.konrad.carexchange.backend.service.UserService;
import makselan.konrad.carexchange.ui.MainLayout;
import makselan.konrad.carexchange.ui.view.list.broadcaster.Broadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@Route(value = "user_cars", layout = MainLayout.class)
@PageTitle("User cars | Car Exchange")
public class UserCarsListView extends VerticalLayout {

    private User loggedUser;
    private UserService userService;
    private CarService carService;

    private Button addCarButton = new Button("Add car");
    private CarGrid grid = new CarGrid();
    private final CarForm carForm = new CarForm();

    @Autowired
    public UserCarsListView(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
        addClassName("list-view");
        setSizeFull();

        setLoggedUser();

        configureGrid();
        configureCarForm();
        configureAddButton();

        Div content = new Div(grid, carForm);
        content.addClassName("content");
        content.setSizeFull();

        add(addCarButton, content);
        updateListOfCars();
        closeEditor();
    }

    private void setLoggedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        loggedUser = userService.getByUsername(username);
    }

    private void configureAddButton() {
        addCarButton.addClassName("toolbar");
        addCarButton.addClickListener(clicked -> addCar());
    }

    private void configureGrid() {
        grid.asSingleSelect().addValueChangeListener(
                singleCarSelected -> editCar(singleCarSelected.getValue())
        );
    }

    private void configureCarForm(){
        carForm.addListener(CarForm.SaveEvent.class, this::saveCar);
        carForm.addListener(CarForm.DeleteEvent.class, this::deleteCar);
        carForm.addListener(CarForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveCar(CarForm.SaveEvent event) {
        carService.save(event.getCar());
        updateListOfCars();
        Broadcaster.broadcast("Car list updated");
        closeEditor();
    }
    private void deleteCar(CarForm.DeleteEvent event) {
        carService.delete(event.getCar());
        updateListOfCars();
        Broadcaster.broadcast("Car list updated");
        closeEditor();
    }

    private void updateListOfCars() {
        grid.setItems(carService.findCarsOfUser(loggedUser));
    }

    private void addCar() {
        grid.asSingleSelect().clear();
        Car carToAdd = new Car("", "", 2000, "", 0, loggedUser);
        editCar(carToAdd);
    }

    private void editCar(Car car) {
        if (car == null) {
            closeEditor();
        } else {
            carForm.setCar(car);
            carForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        carForm.setCar(null);
        carForm.setVisible(false);
        removeClassName("editing");
    }

}
