package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.entity.Servico;

import java.util.List;

public interface NextSenhaStrategy {
    List<Senha> encontrarProxima(Servico servico);
}
