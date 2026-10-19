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

    @Column(nullable = false)
    private String defeitoRelatado;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

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