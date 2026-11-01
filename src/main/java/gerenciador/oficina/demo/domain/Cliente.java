package gerenciador.oficina.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeOuRazaoSocial;

    @Column(nullable = false, unique = true)
    private String cpfOuCnpj;

    private String telefone;
}