package duck.com.example.SistemaDeSenhas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "guiches")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Guiche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    // NOVO CAMPO
    private String nome;

    private String setor;

    private boolean ocupado;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private Atendente atendenteAtual;

    @JsonIgnore
    @OneToMany(mappedBy = "guiche", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Atendimento> atendimentos;
}