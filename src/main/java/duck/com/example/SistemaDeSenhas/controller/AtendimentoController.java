package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Atendimento;
import duck.com.example.SistemaDeSenhas.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping("/chamar/{guicheId}")
    public ResponseEntity<Atendimento> chamar(@PathVariable Long guicheId) {
        Atendimento atendimento = atendimentoService.chamarProxima(guicheId);
        if (atendimento == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atendimento);
    }

    @GetMapping("/atual/{guicheId}")
    public ResponseEntity<Atendimento> buscarAtual(@PathVariable Long guicheId) {
        Atendimento atendimento = atendimentoService.buscarAtendimentoAtual(guicheId);
        if (atendimento == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atendimento);
    }
}
