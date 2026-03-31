package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Prioridade;
import duck.com.example.SistemaDeSenhas.repository.PrioridadeRepository;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prioridades")
public class PrioridadeController {

    @Autowired
    private PrioridadeRepository repository;

    @Autowired
    private SenhaRepository senhaRepository;

    @GetMapping
    public List<Prioridade> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Prioridade> criar(@RequestBody Prioridade prioridade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(prioridade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prioridade> atualizar(@PathVariable Long id, @RequestBody Prioridade dados) {
        return repository.findById(id).map(p -> {
            p.setNome(dados.getNome());
            p.setPeso(dados.getPeso());
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean confirmado) {
        long totalSenhas = senhaRepository.countByPrioridadeId(id);

        if (totalSenhas > 0 && !confirmado) {
            // Retorna um erro proposital com uma mensagem específica para o Frontend
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Existem " + totalSenhas + " senhas vinculadas. Confirmar exclusão?");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
