package makselan.konrad.carexchange.ui.view.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import makselan.konrad.carexchange.backend.entity.Car;

import java.time.LocalDate;

public class CarForm extends FormLayout {

    private ComboBox<String> make = new ComboBox<>("Mark");
    private TextField model = new TextField("Model");
    private IntegerField year = new IntegerField("Year");
    private TextField color = new TextField("Color");
    private IntegerField price = new IntegerField("Price");

    private Binder<Car> binder = new BeanValidationBinder<>(Car.class);

    private Car car;

    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");
    private Button cancelButton = new Button("Cancel");

    public CarForm() {
        addClassName("car-form");
        binder.bindInstanceFields(this);
        configureFields();
        add(make, model, year, color, price, createButtonsLayout());
    }

    public void setCar(Car car) {
        this.car = car;
        binder.readBean(car);
    }

    private void configureFields() {
        year.setHasControls(true);
        year.setValue(2000);
        year.setMin(1886);
        year.setMax(LocalDate.now().getYear());
        make.setItems(Car.MAKES);
    }

    private Component createButtonsLayout(){
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        saveButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, car)));
        cancelButton.addClickListener(event -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> saveButton.setEnabled(binder.isValid()));

        return new HorizontalLayout(saveButton, deleteButton, cancelButton);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(car);
            fireEvent(new SaveEvent(this, car));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    public static abstract class CarFormEvent extends ComponentEvent<CarForm> {

        private Car car;

        protected CarFormEvent(CarForm source, Car car) {
            super(source, false);
            this.car = car;
        }
        public Car getCar() {
            return car;
        }
    }
    public static class SaveEvent extends CarFormEvent {
        SaveEvent(CarForm source, Car car) {
            super(source, car);
        }
    }
    public static class DeleteEvent extends CarFormEvent {
        DeleteEvent(CarForm source, Car car) {
            super(source, car);
        }
    }
    public static class CloseEvent extends CarFormEvent {
        CloseEvent(CarForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
