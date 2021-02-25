package makselan.konrad.carexchange.backend.repository;

import makselan.konrad.carexchange.backend.entity.Car;
import makselan.konrad.carexchange.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c " +
    "WHERE lower(c.make) like lower(concat('%', :searchTerm, '%'))" +
    "OR lower(c.model) like lower(concat('%', :searchTerm, '%'))")
    List<Car> search(@Param("searchTerm") String searchTerm);

    List<Car> findByUserWhoPosted(User userWhoPosted);

}
