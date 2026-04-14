package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Guiche;
import duck.com.example.SistemaDeSenhas.repository.GuicheRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guiche")
public class GuicheController {

    private final GuicheRepository guicheRepository;

    public GuicheController(GuicheRepository guicheRepository) {
        this.guicheRepository = guicheRepository;
    }

    @GetMapping
    public List<Guiche> listar(){
        return guicheRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Guiche> criar(@RequestBody Guiche guiche) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guicheRepository.save(guiche));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guiche> atualizar(@PathVariable Long id, @RequestBody Guiche guicheAtualizado) {

        return guicheRepository.findById(id).map(guiche -> {

            // 🔥 Atualiza apenas se vier valor (evita null sobrescrevendo dado)
            if (guicheAtualizado.getNumero() != null) {
                guiche.setNumero(guicheAtualizado.getNumero());
            }

            if (guicheAtualizado.getSetor() != null) {
                guiche.setSetor(guicheAtualizado.getSetor());
            }

            if (guicheAtualizado.getServico() != null) {
                guiche.setServico(guicheAtualizado.getServico());
            }

            // boolean sempre vem (true/false), então pode setar direto
            guiche.setOcupado(guicheAtualizado.isOcupado());

            return ResponseEntity.ok(guicheRepository.save(guiche));

        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        guicheRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}