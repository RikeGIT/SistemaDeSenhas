package duck.com.example.SistemaDeSenhas.entity;

import duck.com.example.SistemaDeSenhas.entity.Enums.StatusSenha;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Anottetions para definição de enitidade e criação de tabela
@Entity
@Table(name = "senhas")

// Getters e Setters usando Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Senha {

    //  Anotation para gerar Id automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime dataHoraGeracao;

    @Enumerated(EnumType.STRING)
    private StatusSenha status;

//  Cardinalidade Senha:Servico(N:1)
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;
//  Cardinalidade Senha:Prioridade(1:1)
    @OneToOne
    @JoinColumn(name = "prioridade_id")
    private Prioridade prioridade;
}
