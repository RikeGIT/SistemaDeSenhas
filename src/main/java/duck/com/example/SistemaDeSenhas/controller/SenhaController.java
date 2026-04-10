package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Senha;
import duck.com.example.SistemaDeSenhas.repository.SenhaRepository;
import duck.com.example.SistemaDeSenhas.service.ImpressaoService;
import duck.com.example.SistemaDeSenhas.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/senha")
public class SenhaController {

    @Autowired
    private SenhaRepository senhaRepository;

    @Autowired
    private SenhaService senhaService;

    // Criar senha
    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody SenhaRequestDTO request) {
        Senha novaSenha = senhaService.gerarNovaSenha(request.getServicoId(), request.getPrioridadeId());

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Senha gerada com sucesso!");
        response.put("id", novaSenha.getId());
        response.put("codigo", novaSenha.getCodigo());
        response.put("servico", novaSenha.getServico().getNome());
        response.put("dataHora", novaSenha.getDataHoraGeracao().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    @Autowired
    private ImpressaoService impressaoService;

    @PostMapping("/{id}/imprimir")
    public ResponseEntity<Void> imprimirSenha(@PathVariable Long id) {
        Senha senha = senhaRepository.findById(id).orElse(null);
        if (senha != null) {
            impressaoService.imprimirSenhaSilenciosa(senha);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        senhaRepository.deleteById(id);
    }
}