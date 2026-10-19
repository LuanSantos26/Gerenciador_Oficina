package gerenciador.oficina.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

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
    private BigDecimal valor;

    // Relacionamento: Muitos itens pertencem a uma única Ordem de Serviço
    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServico ordemServico;
}