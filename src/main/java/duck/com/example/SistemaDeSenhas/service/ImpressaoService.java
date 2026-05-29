package duck.com.example.SistemaDeSenhas.service;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpressaoService {

    @Autowired
    private ReportPrinter reportPrinter;

    public void imprimirSenhaSilenciosa(Senha senha) {
        reportPrinter.printSenha(senha);
    }
}