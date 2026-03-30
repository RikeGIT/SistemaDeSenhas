package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Guiche;
import duck.com.example.SistemaDeSenhas.repository.GuicheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/guiche")

public class GuicheController {

    @Autowired
    private GuicheRepository guicheRepository;

    @GetMapping
    public List<Guiche> listar(){return guicheRepository.findAll();}

    @PostMapping
    public ResponseEntity<Guiche> criar(@RequestBody Guiche guiche) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guicheRepository.save(guiche));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        guicheRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
