package gerenciador.oficina.demo.repository;

import gerenciador.oficina.demo.domain.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.List;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
    // Itens (peças e mão de obra) de uma OS específica
    List<ItemServico> findByOrdemServicoId(Long ordemServicoId);
=======
@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
>>>>>>> 1bc28070f1389c1461170931e3a598bb0f43d48d
}
