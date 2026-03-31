package duck.com.example.SistemaDeSenhas.repository;

import duck.com.example.SistemaDeSenhas.entity.Guiche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuicheRepository extends JpaRepository<Guiche, Long> {
    long countByServicoId(Long servicoId);
}
