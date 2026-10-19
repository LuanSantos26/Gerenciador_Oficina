package gerenciador.oficina.demo.service;

import gerenciador.oficina.demo.domain.*;
import gerenciador.oficina.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository osRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ItemServicoRepository itemRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public OrdemServico abrirOS(Long veiculoId, String defeitoRelatado) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

        OrdemServico novaOs = new OrdemServico();
        novaOs.setVeiculo(veiculo);
        novaOs.setDefeitoRelatado(defeitoRelatado);

        return osRepository.save(novaOs);
    }

    public ItemServico adicionarItem(Long osId, String descricao, BigDecimal valor, Long funcionarioId) {
        OrdemServico os = osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("OS não encontrada!"));

        if (os.getStatus() == StatusOS.FINALIZADA) {
            throw new RuntimeException("Não é possível adicionar peças a uma OS já finalizada.");
        }

        Funcionario executor = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado no sistema!"));

        os.setStatus(StatusOS.EM_ANDAMENTO);

        ItemServico item = new ItemServico();
        item.setDescricao(descricao);
        item.setValor(valor);
        item.setOrdemServico(os);
        item.setFuncionario(executor); // Vincula o eletricista/mecânico ao item

        ItemServico itemSalvo = itemRepository.save(item);

        os.setValorTotal(os.getValorTotal().add(valor));
        osRepository.save(os);

        return itemSalvo;
    }

    public OrdemServico finalizarOS(Long osId) {
        OrdemServico os = osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("OS não encontrada!"));

        os.setStatus(StatusOS.FINALIZADA);
        os.setDataSaida(LocalDateTime.now());
        return osRepository.save(os);
    }

    public List<OrdemServico> listarTodas() {
        return osRepository.findAll();
    }
}