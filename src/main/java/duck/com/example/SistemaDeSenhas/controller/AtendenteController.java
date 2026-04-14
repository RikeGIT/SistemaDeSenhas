package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Atendente;
import duck.com.example.SistemaDeSenhas.repository.AtendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AtendenteController {

    @Autowired
    private AtendenteRepository atendenteRepository;

    @GetMapping("/listar")
    public List<Atendente> listar(){return atendenteRepository.findAll();}

    @PostMapping("/registrar")
    public ResponseEntity<Atendente> registrar(@RequestBody Atendente novo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendenteRepository.save(novo));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> credenciais) {
        String username = credenciais.get("username");
        String password = credenciais.get("password");

        return atendenteRepository.findByUsername(username)
                .filter(a -> a.getPassword().equals(password))
                .map(a -> ResponseEntity.ok((Object) a))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos"));
    }

    @PutMapping("/{id}/tornar-admin")
    public ResponseEntity<Atendente> tornarAdmin(@PathVariable Long id) {
        return atendenteRepository.findById(id).map(atendente -> {
            atendente.setAdmin(true);
            return ResponseEntity.ok(atendenteRepository.save(atendente));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        atendenteRepository.deleteById(id);
    }
}