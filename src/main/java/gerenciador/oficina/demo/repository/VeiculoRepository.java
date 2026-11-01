package gerenciador.oficina.demo.repository;

import gerenciador.oficina.demo.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    //Apenas declarando este metodo, o Spring já entende que deve buscar pela placa
    boolean existsByPlaca(String placa);
}