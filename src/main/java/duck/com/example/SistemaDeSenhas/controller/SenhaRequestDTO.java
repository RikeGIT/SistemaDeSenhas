package duck.com.example.SistemaDeSenhas.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaRequestDTO {
    private Long servicoId;
    private Long prioridadeId;
}