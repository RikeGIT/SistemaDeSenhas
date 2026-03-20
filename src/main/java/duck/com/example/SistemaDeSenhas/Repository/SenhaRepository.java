package duck.com.example.SistemaDeSenhas.Repository;

import duck.com.example.SistemaDeSenhas.Entity.Senha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenhaRepository extends JpaRepository<Senha, Long> {
}
