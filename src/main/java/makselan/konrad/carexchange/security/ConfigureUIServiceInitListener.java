package makselan.konrad.carexchange.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import makselan.konrad.carexchange.ui.view.login.LoginView;
import makselan.konrad.carexchange.ui.view.login.RegisterView;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            final UI ui = uiInitEvent.getUI();
            ui.addBeforeEnterListener(this:: authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event){
        if(!LoginView.class.equals(event.getNavigationTarget())
            && !RegisterView.class.equals(event.getNavigationTarget())
            && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }

}
