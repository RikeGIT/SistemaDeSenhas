package duck.com.example.SistemaDeSenhas.repository;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SenhaRepository extends JpaRepository<Senha, Long> {
    @Query("SELECT s FROM Senha s " +
            "WHERE s.status = 'AGUARDANDO' AND s.servico = :servico " +
            "ORDER BY s.prioridade.peso DESC, s.dataHoraGeracao ASC")
    List<Senha> encontrarProximaSenha(Servico servico);

    // Counts para poder realizar um DELETE em cascade.
    long countByPrioridadeId(Long prioridadeId);
    long countByServicoId(Long servicoId);
}
