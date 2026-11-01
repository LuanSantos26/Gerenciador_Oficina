package gerenciador.oficina.demo.repository;

import gerenciador.oficina.demo.domain.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
    // Itens (peças e mão de obra) de uma OS específica
    List<ItemServico> findByOrdemServicoId(Long ordemServicoId);
}
