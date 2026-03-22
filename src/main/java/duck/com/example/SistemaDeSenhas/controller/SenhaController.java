package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/senha")
public class SenhaController {

    @Autowired
    private SenhaRepository senhaRepository;

    // Criar senha
    @PostMapping
    public Senha criar(@RequestBody Senha senha) {
        return senhaRepository.save(senha);
    }

    // Listar todas
    @GetMapping
    public List<Senha> listar() {
        return senhaRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Senha buscar(@PathVariable Long id) {
        return senhaRepository.findById(id).orElse(null);
    }

    // Atualizar
    @PutMapping("/{id}")
    public Senha atualizar(@PathVariable Long id, @RequestBody Senha senhaAtualizada) {
        Senha senha = senhaRepository.findById(id).orElse(null);

        if (senha != null) {
            senha.setCodigo(senhaAtualizada.getCodigo());
            senha.setStatus(senhaAtualizada.getStatus());
            senha.setDataHoraGeracao(senhaAtualizada.getDataHoraGeracao());
            return senhaRepository.save(senha);
        }

        return null;
    }

    // Deletar
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        senhaRepository.deleteById(id);
    }
}