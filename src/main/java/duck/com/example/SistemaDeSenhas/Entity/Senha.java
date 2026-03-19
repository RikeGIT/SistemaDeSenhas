package duck.com.example.SistemaDeSenhas.Entity;

import duck.com.example.SistemaDeSenhas.Entity.Enums.StatusSenha;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "senhas")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Senha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime dataHoraGeracao;

    @Enumerated(EnumType.STRING)
    private StatusSenha status;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "prioridade_id")
    private Prioridade prioridade;
}
