package ar.edu.iua.iw3.modelo.cuentas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findFirstByUsernameOrEmail(String username, String email);
	
}
