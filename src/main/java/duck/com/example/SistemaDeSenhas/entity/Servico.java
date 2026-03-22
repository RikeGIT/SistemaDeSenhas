package duck.com.example.SistemaDeSenhas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicos")
// Getters e Setters usando Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Servico {
    //  Anotation para gerar Id automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String sigla;
}