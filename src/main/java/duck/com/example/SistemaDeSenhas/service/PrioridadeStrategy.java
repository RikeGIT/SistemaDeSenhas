package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.entity.Servico;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrioridadeStrategy implements NextSenhaStrategy {

    @Autowired
    private SenhaRepository senhaRepository;

    @Override
    public List<Senha> encontrarProxima(Servico servico) {
        return senhaRepository.encontrarProximaSenha(servico);
    }
}
