package makselan.konrad.carexchange.backend.repository;

import makselan.konrad.carexchange.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);

}
