package gerenciador.oficina.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "itens_servico")
public class ItemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao; // Ex: Troca de óleo, Substituição do Fusível F73

    @Column(nullable = false)
    private BigDecimal valor; // valor total do item (quantidade x unitário)

    // PECA ou MAO_DE_OBRA — permite o relatório de peças utilizadas
    @Enumerated(EnumType.STRING)
    private TipoItem tipo = TipoItem.MAO_DE_OBRA;

    private Integer quantidade = 1;

    private String codigoPeca;

    private LocalDateTime dataLancamento;

    @PrePersist
    public void aoLancar() {
        this.dataLancamento = LocalDateTime.now();
        if (this.tipo == null) this.tipo = TipoItem.MAO_DE_OBRA;
        if (this.quantidade == null || this.quantidade < 1) this.quantidade = 1;
    }

    // Relacionamento: Muitos itens pertencem a uma única Ordem de Serviço
    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServico ordemServico;

    // Registra qual mecânico/eletricista executou este item
    @ManyToOne(optional = false)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
}