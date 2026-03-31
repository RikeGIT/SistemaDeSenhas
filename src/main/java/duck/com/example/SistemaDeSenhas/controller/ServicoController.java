package duck.com.example.SistemaDeSenhas.controller;
import duck.com.example.SistemaDeSenhas.entity.Servico;
import duck.com.example.SistemaDeSenhas.repository.GuicheRepository;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import duck.com.example.SistemaDeSenhas.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    @Autowired
    private SenhaRepository senhaRepository;

    @Autowired
    private GuicheRepository guicheRepository;

    @Autowired
    private ServicoRepository repository;

    @GetMapping
    public List<Servico> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Servico> criar(@RequestBody Servico servico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(servico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @RequestBody Servico dados) {
        return repository.findById(id).map(s -> {
            s.setNome(dados.getNome());
            s.setDescricao(dados.getDescricao());
            s.setSigla(dados.getSigla());
            return ResponseEntity.ok(repository.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean confirmado) {
        long totalSenhas = senhaRepository.countByServicoId(id);
        long totalGuiches = guicheRepository.countByServicoId(id);

        if ((totalSenhas > 0 || totalGuiches > 0) && !confirmado) {
            String msg = "Atenção: Este serviço possui ";
            if (totalSenhas > 0) msg += totalSenhas + " senhas ";
            if (totalGuiches > 0) msg += (totalSenhas > 0 ? "e " : "") + totalGuiches + " guichês ";
            msg += "vinculados. Confirmar exclusão total?";

            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
