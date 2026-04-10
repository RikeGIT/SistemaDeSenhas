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
        Guiche guiche = guicheRepository.findById(guicheId)
                .orElseThrow(() -> new RuntimeException("Guichê não encontrado"));

        Atendimento ultimoGeral = atendimentoRepository.findFirstByOrderByDataHoraInicioDesc();

        if (ultimoGeral != null && ultimoGeral.getDataHoraFim() == null) {
            long segundosDecorridos = java.time.Duration.between(ultimoGeral.getDataHoraInicio(), LocalDateTime.now()).toSeconds();
            if (segundosDecorridos < 30) {
                throw new RuntimeException("Aguarde " + (30 - segundosDecorridos) + " segundos para chamar a próxima senha.");
            }
        }

        // verifica se existe atendimento em aberto
        Atendimento atual = atendimentoRepository
                .findByGuicheIdAndDataHoraFimIsNull(guicheId)
                .orElse(null);

        if (atual != null) {

            atual.setDataHoraFim(LocalDateTime.now());

            Senha senhaAtual = atual.getSenha();

            // ALTERAÇÃO IMPORTANTE
            senhaAtual.setStatus(StatusSenha.FINALIZADA);
            senhaRepository.save(senhaAtual);

            atendimentoRepository.save(atual);

            guiche.setOcupado(false);
            guicheRepository.save(guiche);
        }

        List<Senha> fila = senhaRepository.encontrarProximaSenha(guiche.getServico());

        if (fila.isEmpty()) {
            return null;
        }

        Senha proximaSenha = fila.get(0);

        proximaSenha.setStatus(StatusSenha.CHAMADA);

        guiche.setOcupado(true);

        senhaRepository.save(proximaSenha);
        guicheRepository.save(guiche);

        Atendimento atendimento = new Atendimento();
        atendimento.setSenha(proximaSenha);
        atendimento.setGuiche(guiche);
        atendimento.setDataHoraInicio(LocalDateTime.now());

        return atendimentoRepository.save(atendimento);
    }

    public Atendimento buscarAtendimentoAtual(Long guicheId) {

        return atendimentoRepository
                .findByGuicheIdAndDataHoraFimIsNull(guicheId)
                .orElse(null);
    }

    // MÉTODO PARA RECHAMAR SENHA
    @Transactional
    public Atendimento rechamar(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        // Verifica se o guichê já tem um atendimento aberto (fantasma ou real)
        Atendimento atual = atendimentoRepository
                .findByGuicheIdAndDataHoraFimIsNull(atendimento.getGuiche().getId())
                .orElse(null);

        // Se houver um atendimento aberto e NÃO for este que estamos tentando rechamar, bloqueia
        if (atual != null && !atual.getId().equals(atendimentoId)) {
            throw new RuntimeException("Finalize o atendimento atual antes de rechamar uma senha antiga.");
        }

        Senha senha = atendimento.getSenha();
        senha.setStatus(StatusSenha.CHAMADA);
        senhaRepository.save(senha);

        // O PULO DO GATO: Removemos a data de fim para ele voltar a ser "Ativo" no sistema
        atendimento.setDataHoraFim(null);
        atendimento.setDataHoraInicio(LocalDateTime.now());

        Guiche guiche = atendimento.getGuiche();
        guiche.setOcupado(true);
        guicheRepository.save(guiche);

        return atendimentoRepository.save(atendimento);
    }

    @Transactional
    public void finalizar(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        atendimento.setDataHoraFim(LocalDateTime.now());

        Senha senha = atendimento.getSenha();
        senha.setStatus(StatusSenha.FINALIZADA);

        Guiche guiche = atendimento.getGuiche();
        guiche.setOcupado(false);

        senhaRepository.save(senha);
        guicheRepository.save(guiche);
        atendimentoRepository.save(atendimento);
    }
}
