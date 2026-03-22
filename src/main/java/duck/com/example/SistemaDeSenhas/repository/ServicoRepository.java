package duck.com.example.SistemaDeSenhas.repository;

import duck.com.example.SistemaDeSenhas.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico,Long > {
}
