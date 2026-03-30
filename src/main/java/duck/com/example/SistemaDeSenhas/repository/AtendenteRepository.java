package duck.com.example.SistemaDeSenhas.repository;
import duck.com.example.SistemaDeSenhas.entity.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AtendenteRepository extends JpaRepository<Atendente, Long> {
    Optional<Atendente> findByUsername(String username);
}
