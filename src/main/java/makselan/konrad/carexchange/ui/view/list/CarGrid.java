package makselan.konrad.carexchange.ui.view.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.NumberRenderer;
import makselan.konrad.carexchange.backend.entity.Car;
import java.text.NumberFormat;
import java.util.Locale;

public class CarGrid extends Grid<Car> {

    public CarGrid() {
        super(Car.class);
        addClassName("car-grid");
        setSizeFull();
        setColumns("make", "model", "year", "color");
        NumberFormat withoutFractionDigitsFormat = NumberFormat.getCurrencyInstance(new Locale("pl-PL"));
        withoutFractionDigitsFormat.setMaximumFractionDigits(0);
        addColumn(new NumberRenderer<>(
                Car::getPrice, withoutFractionDigitsFormat)
        ).setHeader("Price");

        getColumns().forEach(column -> column.setAutoWidth(true));
    }


}
