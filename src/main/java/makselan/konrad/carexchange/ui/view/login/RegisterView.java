package makselan.konrad.carexchange.ui.view.login;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import makselan.konrad.carexchange.backend.dto.UserDto;
import makselan.konrad.carexchange.backend.service.UserService;
import makselan.konrad.carexchange.backend.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
@PageTitle("Register | Car exchange")
public class RegisterView extends VerticalLayout {

    public static final String PASSWORDS_DO_NOT_MATCH_ERROR = "Passwords don't match!";
    public static final String USERNAME_AND_PASSWORD_MATCH_ERROR = "Username and password cannot match!";

    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private PasswordField confirmPassword = new PasswordField("Confirm password");
    private TextField phoneNumber = new TextField("Phone number");
    private Button registerButton = new Button("Register");
    private Button backToLoginViewButton = new Button("Return to login page");

    private RegisterLogic registerLogic = new RegisterLogic();

    private Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
    private Label validationErrorLabel = new Label();
    private UserService userService;

    @Autowired
    public RegisterView(UserService userService) {
        addClassName("register-view");
        this.userService = userService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        configureRegisterButton();
        configureBinder();

        add(
                new H1("Sign up"),
                validationErrorLabel,
                username,
                password,
                confirmPassword,
                phoneNumber,
                registerButton,
                backToLoginViewButton
        );
    }

    private void configureRegisterButton(){
        username.setWidth("20em");
        password.setWidth("20em");
        confirmPassword.setWidth("20em");
        phoneNumber.setWidth("20em");
        registerButton.setWidth("20em");
        backToLoginViewButton.setWidth("20em");

        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        registerButton.addClickListener(registerLogic::validateAndRegister);

        backToLoginViewButton.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(LoginView.class));
    }

    private void configureBinder(){
        validationErrorLabel.addClassName("error-label");
        binder.setStatusLabel(validationErrorLabel);
        binder.bindInstanceFields(this);

        binder.withValidator(registerLogic::checkIfPasswordsMatch, PASSWORDS_DO_NOT_MATCH_ERROR )
                .withValidator(registerLogic:: checkIfUsernameAndPasswordDoNotMatch, USERNAME_AND_PASSWORD_MATCH_ERROR);

        binder.addStatusChangeListener(statusChangeEvent -> registerButton.setEnabled(binder.isValid()));
        binder.setBean(new UserDto("", "", "", ""));
    }

    private class RegisterLogic {

        private boolean checkIfPasswordsMatch(UserDto userDto){
            return userDto.getPassword().equals(userDto.getConfirmPassword());
        }

        private boolean checkIfUsernameAndPasswordDoNotMatch(UserDto userDto){
            return !userDto.getUsername().equals(userDto.getPassword());
        }

        private void validateAndRegister(ClickEvent event){

            if (binder.validate().isOk()) {
                try {
                    userService.registerUser(binder.getBean());
                    UI.getCurrent().navigate(LoginView.class);
                    Notification.show("Registration succeed! You can now log in!", 2000, Notification.Position.MIDDLE);
                } catch (UserAlreadyExistsException userAlreadyExistsException) {
                    Notification.show("This username is already taken!", 2000, Notification.Position.MIDDLE);
                }
            }
        }

    }


}
