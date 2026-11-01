package gerenciador.oficina.demo.repository;

import gerenciador.oficina.demo.domain.FotoOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoOSRepository extends JpaRepository<FotoOS, Long> {
    List<FotoOS> findByOrdemServicoIdOrderByDataEnvioAsc(Long ordemServicoId);
}
