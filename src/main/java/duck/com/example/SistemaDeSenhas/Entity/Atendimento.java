package duck.com.example.SistemaDeSenhas.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime dataHoraInicio;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime dataHoraFim;

    @OneToOne
    @JoinColumn(name = "senha_id", unique = true)
    private Senha senha;

    @ManyToOne
    @JoinColumn(name = "guiche_id")
    private Guiche guiche;
}