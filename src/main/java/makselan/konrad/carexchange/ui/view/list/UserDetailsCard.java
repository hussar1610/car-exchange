package makselan.konrad.carexchange.ui.view.list;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import makselan.konrad.carexchange.backend.entity.User;

public class UserDetailsCard extends FormLayout {

    private TextField username = new TextField("Username");
    private TextField phoneNumber = new TextField("Phone number");
    private Button closeButton = new Button("Close");

    private Binder<User> binder = new Binder<>(User.class);

    public UserDetailsCard() {
        addClassName("user-form");
        binder.bindInstanceFields(this);

        configureFields();
        configureCloseButton();

        add(
                new H2("Contact the seller"),
                username,
                phoneNumber
        );
    }

    private void configureFields(){
        username.setReadOnly(true);
        phoneNumber.setReadOnly(true);
    }

    public void configureCloseButton(){
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        closeButton.addClickShortcut(Key.ESCAPE);
        closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));
    }

    public void setUser(User user){
        binder.readBean(user);
    }

    public static abstract class UserDetailsCardEvent extends ComponentEvent<UserDetailsCard> {

        private User user;

        protected UserDetailsCardEvent(UserDetailsCard source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class CloseEvent extends UserDetailsCardEvent {
        CloseEvent(UserDetailsCard source) { super(source, null); }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
