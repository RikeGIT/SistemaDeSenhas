package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Prioridade;
import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.entity.Servico;
import duck.com.example.SistemaDeSenhas.entity.Enums.StatusSenha;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SenhaFactory {

    public Senha criarSenha(Servico servico, Prioridade prioridade, String codigo) {
        Senha nova = new Senha();
        nova.setServico(servico);
        nova.setPrioridade(prioridade);
        nova.setDataHoraGeracao(LocalDateTime.now());
        nova.setStatus(StatusSenha.AGUARDANDO);
        nova.setCodigo(codigo);
        return nova;
    }
}
