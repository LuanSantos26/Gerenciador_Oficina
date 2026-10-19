package gerenciador.oficina.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vários caminhões podem pertencer a uma mesma transportadora/cliente
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String marca; // Ex: Volkswagen, Ford

    @Column(nullable = false)
    private String modelo; // Ex: Constellation, Cargo 816

    private Integer anoFabricacao;

    private String chassi;
}