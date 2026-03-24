package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Enums.StatusSenha;
import duck.com.example.SistemaDeSenhas.entity.Prioridade;
import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.entity.Servico;
import duck.com.example.SistemaDeSenhas.repository.PrioridadeRepository;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import duck.com.example.SistemaDeSenhas.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
public class SenhaService {
    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private PrioridadeRepository prioridadeRepository;

    @Autowired
    private SenhaRepository senhaRepository;

    public Senha gerarNovaSenha(Long servicoId, Long prioridadeId) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        Prioridade prioridade = prioridadeRepository.findById(prioridadeId)
                .orElseThrow(() -> new RuntimeException("Prioridade não encontrada"));

        Senha novaSenha = new Senha();
        novaSenha.setServico(servico);
        novaSenha.setPrioridade(prioridade);
        novaSenha.setDataHoraGeracao(LocalDateTime.now());
        novaSenha.setStatus(StatusSenha.AGUARDANDO);

        // Lógica do Código: [Prefixo][ID Serviço][Aleatório]
        String prefixo = (prioridade.getPeso() > 1) ? "2" : "1";
        String idServicoStr = String.valueOf(servico.getId());
        int aleatorio = new Random().nextInt(90) + 10; // Gera entre 10 e 99

        String codigoGerado = prefixo + idServicoStr + aleatorio;
        novaSenha.setCodigo(codigoGerado);

        return senhaRepository.save(novaSenha);
    }
}
