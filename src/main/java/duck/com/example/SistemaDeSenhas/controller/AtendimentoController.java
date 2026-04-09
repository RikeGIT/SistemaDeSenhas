package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Atendimento;
import duck.com.example.SistemaDeSenhas.repository.AtendimentoRepository;
import duck.com.example.SistemaDeSenhas.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

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

    // NOVO ENDPOINT PARA O PAINEL
    @GetMapping("/painel")
    public List<Atendimento> painel() {
        return atendimentoRepository.findByDataHoraFimIsNull();
    }

    // NOVO ENDPOINT PARA RECHAMAR SENHA
    @PostMapping("/rechamar/{id}")
    public ResponseEntity<Atendimento> rechamar(@PathVariable Long id){

        Atendimento atendimento = atendimentoService.rechamar(id);

        if(atendimento == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(atendimento);
    }
}
