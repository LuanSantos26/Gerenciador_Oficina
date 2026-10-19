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

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String marca; // Ex: Volkswagen, Ford

    @Column(nullable = false)
    private String modelo; // Ex: Constellation, Cargo 816

    private Integer anoFabricacao;

    private String chassi;
}