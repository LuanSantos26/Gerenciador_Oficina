package gerenciador.oficina.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ordens_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Column(nullable = false, length = 1000)
    private String defeitoRelatado;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    // Controle de entrada e saída do veículo
    private Integer kmEntrada;
    private Integer kmSaida;
    private String retiradoPor;

    // Inicia a OS sempre zerada
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private StatusOS status;

    @PrePersist
    public void aoCadastrar() {
        this.dataEntrada = LocalDateTime.now();
        this.status = StatusOS.ABERTA;
    }
}