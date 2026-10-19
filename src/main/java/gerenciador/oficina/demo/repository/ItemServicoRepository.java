package gerenciador.oficina.demo.repository;

import gerenciador.oficina.demo.domain.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
}
