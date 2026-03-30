package duck.com.example.SistemaDeSenhas.controller;
import duck.com.example.SistemaDeSenhas.entity.Servico;
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
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
