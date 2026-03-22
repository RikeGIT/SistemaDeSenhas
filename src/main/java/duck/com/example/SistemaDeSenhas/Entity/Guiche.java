package duck.com.example.SistemaDeSenhas.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String setor;
    private boolean ocupado;
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;
}