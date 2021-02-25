package makselan.konrad.carexchange.ui.view.list;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;


public class FilterTextField extends TextField {

    public FilterTextField() {
        setPlaceholder("Filter by make and/or model");
        setWidth("15em");
        setClearButtonVisible(true);
        setValueChangeMode(ValueChangeMode.LAZY);
    }

}
