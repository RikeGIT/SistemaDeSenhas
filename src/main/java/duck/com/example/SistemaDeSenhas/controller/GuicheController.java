package duck.com.example.SistemaDeSenhas.controller;

import duck.com.example.SistemaDeSenhas.entity.Guiche;
import duck.com.example.SistemaDeSenhas.repository.GuicheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/guiche")

public class GuicheController {

    @Autowired
    private GuicheRepository guicheRepository;

    @GetMapping
    public List<Guiche> listar(){return guicheRepository.findAll();}
}
