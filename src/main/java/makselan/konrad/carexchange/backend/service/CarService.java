package makselan.konrad.carexchange.backend.service;

import makselan.konrad.carexchange.backend.entity.User;
import makselan.konrad.carexchange.backend.repository.CarRepository;
import makselan.konrad.carexchange.backend.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarService {

    private static final Logger LOGGER = Logger.getLogger(CarService.class.getName());
    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void save(Car car){
        if (car == null) { 
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        carRepository.save(car);
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public long count(){
        return carRepository.count();
    }

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public List<Car> findAll(String makeAndModelFilter){
        if(makeAndModelFilter == null || makeAndModelFilter.isEmpty()){
            return findAll();
        } else {
            return carRepository.search(makeAndModelFilter);
        }
    }

    public List<Car> findCarsOfUser(User user){
        return carRepository.findByUserWhoPosted(user);
    }

}
