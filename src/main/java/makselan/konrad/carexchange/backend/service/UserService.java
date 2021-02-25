package makselan.konrad.carexchange.backend.service;

import makselan.konrad.carexchange.backend.dto.UserDto;
import makselan.konrad.carexchange.backend.entity.Car;
import makselan.konrad.carexchange.backend.entity.User;
import makselan.konrad.carexchange.backend.repository.CarRepository;
import makselan.konrad.carexchange.backend.repository.UserRepository;
import makselan.konrad.carexchange.backend.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;
    private CarRepository carRepository;

    @Autowired
    public UserService(UserRepository userRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public void registerUser(UserDto userDto) throws UserAlreadyExistsException {
        if(checkIfUserExist(userDto.getUsername())){
            throw new UserAlreadyExistsException("This username is already taken");
        }
        userRepository.save(
                new User(
                    userDto.getUsername(),
                    userDto.getPassword(),
                    userDto.getPhoneNumber()
                )
        );
    }

    public User getByUsername(String username){
        return userRepository.getByUsername(username);
    }

    private boolean checkIfUserExist(String username) {
        return userRepository.getByUsername(username) == null ? false : true;
    }

    @PostConstruct
    public void fillDataBaseWithTestData() {
        if (userRepository.count() == 0) {

            User user1 = new User("ania1", "pass1", "111-111-111");
            User user2 = new User("kamil2", "pass2", "111 222 333");
            User user3 = new User("pati32", "pass3", "777777777");

            Car car1 = new Car("Audi", "B5", 1995, "Red", 4500, user1);
            Car car2 = new Car("BMW", "X4", 2005, "Black", 50000, user1);

            Car car3 = new Car("Seat", "Toledo", 2006, "Silver", 7600, user2);
            Car car4 = new Car("Toyota", "Corolla", 2010, "White", 42000, user2);

            Car car5 = new Car("Fiat", "Punto", 1998, "Red", 5550, user3);
            Car car6 = new Car("Fiat", "126p", 1980, "Red", 15000, user3);

            userRepository.saveAll(Stream.of(user1, user2, user3).collect(Collectors.toList()));
            carRepository.saveAll(Stream.of(car1, car2, car3, car4, car5, car6).collect(Collectors.toList()));
        }
    }
}
