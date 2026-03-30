package duck.com.example.SistemaDeSenhas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "atendentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nome;

    private boolean admin; // Para distinguir quem pode acessar o CRUD administrativo
}
