package gerenciador.oficina.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/** Cada mudança de status da OS — base do acompanhamento de progresso */
@Data
@Entity
@Table(name = "historico_status_os")
public class HistoricoStatusOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServico ordemServico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOS status;

    private LocalDateTime dataHora;

    private String observacao;
}
