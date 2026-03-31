package duck.com.example.SistemaDeSenhas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "prioridades")

// Getters e Setters usando Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Prioridade {
    //  Anotation para gerar Id automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer peso;

    @JsonIgnore
    @OneToMany(mappedBy = "prioridade", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Senha> senhas;
}