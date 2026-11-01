package gerenciador.oficina.demo.repository;

import gerenciador.oficina.demo.domain.HistoricoStatusOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoStatusOSRepository extends JpaRepository<HistoricoStatusOS, Long> {
    List<HistoricoStatusOS> findByOrdemServicoIdOrderByDataHoraAsc(Long ordemServicoId);
}
