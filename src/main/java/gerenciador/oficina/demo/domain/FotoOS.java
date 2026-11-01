package gerenciador.oficina.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/** Foto ligada a uma OS. O arquivo fica em disco (pasta "uploads"); no banco só os metadados. */
@Data
@Entity
@Table(name = "fotos_os")
public class FotoOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServico ordemServico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaFoto categoria;

    private String descricao;

    @JsonIgnore
    @Column(nullable = false)
    private String nomeArquivo; // nome gerado no disco (UUID)

    private String contentType;

    private Long tamanho;

    private LocalDateTime dataEnvio;

    @PrePersist
    public void aoCadastrar() {
        this.dataEnvio = LocalDateTime.now();
    }
}
