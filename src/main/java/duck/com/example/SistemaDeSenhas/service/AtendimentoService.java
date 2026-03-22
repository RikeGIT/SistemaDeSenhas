package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Atendimento;
import duck.com.example.SistemaDeSenhas.entity.Enums.StatusSenha;
import duck.com.example.SistemaDeSenhas.entity.Guiche;
import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.repository.AtendimentoRepository;
import duck.com.example.SistemaDeSenhas.repository.GuicheRepository;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private SenhaRepository senhaRepository;
    @Autowired
    private GuicheRepository guicheRepository;
    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Transactional
    public Atendimento chamarProxima(Long guicheId) {
        // 1. Verificar se o guichê existe e o serviço que ele presta
        Guiche guiche = guicheRepository.findById(guicheId)
                .orElseThrow(() -> new RuntimeException("Guichê não encontrado"));

        if (guiche.isOcupado()) {
            throw new RuntimeException("Este guichê já está em atendimento");
        }

        // 2. Buscar a próxima senha da fila para aquele serviço
        List<Senha> fila = senhaRepository.encontrarProximaSenha(guiche.getServico());
        if (fila.isEmpty()) {
            return null; // Caso a fila esteja vazia
        }

        Senha proximaSenha = fila.get(0);

        // 3. Atualizar estados
        proximaSenha.setStatus(StatusSenha.CHAMADA);
        guiche.setOcupado(true);

        senhaRepository.save(proximaSenha);
        guicheRepository.save(guiche);

        // 4. Registrar o Início do Atendimento
        Atendimento atendimento = new Atendimento();
        atendimento.setSenha(proximaSenha);
        atendimento.setGuiche(guiche);
        atendimento.setDataHoraInicio(LocalDateTime.now());

        return atendimentoRepository.save(atendimento);
    }

    @Transactional
    public void finalizarAtendimento(Long atendimentoId) {
        // Quarta Parte: Finalizar atendimento e liberar guichê
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        atendimento.setDataHoraFim(LocalDateTime.now());

        Senha senha = atendimento.getSenha();
        senha.setStatus(StatusSenha.FINALIZADA);

        Guiche guiche = atendimento.getGuiche();
        guiche.setOcupado(false);

        atendimentoRepository.save(atendimento);
        senhaRepository.save(senha);
        guicheRepository.save(guiche);
    }
}