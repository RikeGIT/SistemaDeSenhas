package duck.com.example.SistemaDeSenhas.repository;

import duck.com.example.SistemaDeSenhas.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    Optional<Atendimento> findByGuicheIdAndDataHoraFimIsNull(Long guicheId);
    long countByGuicheServicoId(Long servicoId);
}
