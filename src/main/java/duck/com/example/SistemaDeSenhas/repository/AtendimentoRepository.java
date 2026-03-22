package duck.com.example.SistemaDeSenhas.repository;

import duck.com.example.SistemaDeSenhas.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
}
