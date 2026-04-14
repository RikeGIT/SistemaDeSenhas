package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Enums.StatusSenha;
import duck.com.example.SistemaDeSenhas.entity.Prioridade;
import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.entity.Servico;
import duck.com.example.SistemaDeSenhas.repository.AtendimentoRepository;
import duck.com.example.SistemaDeSenhas.repository.PrioridadeRepository;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import duck.com.example.SistemaDeSenhas.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@Service
public class SenhaService {
    @Autowired
    private AtendimentoRepository atendimentoRepository;
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

        // 1. Pega as duas primeiras letras da Sigla (ou Nome) em Maiúsculo
        String prefixo = servico.getSigla().substring(0, 2).toUpperCase();

        // 2. Conta quantas senhas já existem para este serviço para gerar a sequência
        long totalParaServico = senhaRepository.countByServicoId(servicoId);
        String sequencia = String.format("%03d", totalParaServico + 1);

        String codigoGerado = prefixo + sequencia;

        Senha novaSenha = new Senha();
        novaSenha.setServico(servico);
        novaSenha.setPrioridade(prioridade);
        novaSenha.setDataHoraGeracao(LocalDateTime.now());
        novaSenha.setStatus(StatusSenha.AGUARDANDO);
        novaSenha.setCodigo(codigoGerado);

        return senhaRepository.save(novaSenha);
    }
    // Em SenhaService.java
    @Transactional
    public void resetarFila() {
        atendimentoRepository.deleteAll(); // Limpa atendimentos primeiro
        senhaRepository.deleteAll();      // Depois limpa as senhas
    }
}
