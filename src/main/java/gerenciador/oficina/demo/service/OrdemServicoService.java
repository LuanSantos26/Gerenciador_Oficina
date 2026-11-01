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

    @Autowired
    private HistoricoStatusOSRepository historicoRepository;

    public OrdemServico abrirOS(Long veiculoId, String defeitoRelatado) {
        return abrirOS(veiculoId, defeitoRelatado, null);
    }

    public OrdemServico abrirOS(Long veiculoId, String defeitoRelatado, Integer kmEntrada) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));

        OrdemServico novaOs = new OrdemServico();
        novaOs.setVeiculo(veiculo);
        novaOs.setDefeitoRelatado(defeitoRelatado);
        novaOs.setKmEntrada(kmEntrada);

        OrdemServico salva = osRepository.save(novaOs);
        registrarHistorico(salva, StatusOS.ABERTA, "Entrada do veículo");
        return salva;
    }

    public ItemServico adicionarItem(Long osId, String descricao, BigDecimal valor, Long funcionarioId) {
        return adicionarItem(osId, descricao, valor, funcionarioId, TipoItem.MAO_DE_OBRA, 1, null);
    }

    public ItemServico adicionarItem(Long osId, String descricao, BigDecimal valor, Long funcionarioId,
                                     TipoItem tipo, Integer quantidade, String codigoPeca) {
        OrdemServico os = buscarPorId(osId);

        if (os.getStatus() == StatusOS.FINALIZADA) {
            throw new RuntimeException("Não é possível adicionar peças a uma OS já finalizada.");
        }

        Funcionario executor = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado no sistema!"));

        if (os.getStatus() == StatusOS.ABERTA) {
            os.setStatus(StatusOS.EM_ANDAMENTO);
            registrarHistorico(os, StatusOS.EM_ANDAMENTO, "Primeiro item lançado");
        }

        ItemServico item = new ItemServico();
        item.setDescricao(descricao);
        item.setValor(valor);
        item.setOrdemServico(os);
        item.setFuncionario(executor); // Vincula o eletricista/mecânico ao item
        item.setTipo(tipo != null ? tipo : TipoItem.MAO_DE_OBRA);
        item.setQuantidade(quantidade != null && quantidade > 0 ? quantidade : 1);
        item.setCodigoPeca(codigoPeca);

        ItemServico itemSalvo = itemRepository.save(item);

        os.setValorTotal(os.getValorTotal().add(valor));
        osRepository.save(os);

        return itemSalvo;
    }

    public OrdemServico finalizarOS(Long osId) {
        return finalizarOS(osId, null, null);
    }

    /** Saída do veículo: registra data/hora, km de saída e quem retirou */
    public OrdemServico finalizarOS(Long osId, Integer kmSaida, String retiradoPor) {
        OrdemServico os = buscarPorId(osId);

        if (os.getStatus() == StatusOS.FINALIZADA) {
            throw new RuntimeException("Esta OS já está finalizada.");
        }
        if (kmSaida != null && os.getKmEntrada() != null && kmSaida < os.getKmEntrada()) {
            throw new RuntimeException("O km de saída não pode ser menor que o km de entrada (" + os.getKmEntrada() + ").");
        }

        os.setStatus(StatusOS.FINALIZADA);
        os.setDataSaida(LocalDateTime.now());
        os.setKmSaida(kmSaida);
        os.setRetiradoPor(retiradoPor);
        OrdemServico salva = osRepository.save(os);
        registrarHistorico(salva, StatusOS.FINALIZADA, retiradoPor != null ? "Retirado por " + retiradoPor : "Saída do veículo");
        return salva;
    }

    public OrdemServico buscarPorId(Long osId) {
        return osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("OS não encontrada!"));
    }

    public List<ItemServico> listarItens(Long osId) {
        buscarPorId(osId); // garante que a OS existe
        return itemRepository.findByOrdemServicoId(osId);
    }

    public OrdemServico alterarStatus(Long osId, StatusOS novoStatus) {
        return alterarStatus(osId, novoStatus, null);
    }

    public OrdemServico alterarStatus(Long osId, StatusOS novoStatus, String observacao) {
        OrdemServico os = buscarPorId(osId);
        if (os.getStatus() == StatusOS.FINALIZADA) {
            throw new RuntimeException("Esta OS já foi finalizada e não pode mudar de status.");
        }
        if (novoStatus == StatusOS.FINALIZADA) {
            return finalizarOS(osId); // finalizar também registra a data de saída
        }
        os.setStatus(novoStatus);
        OrdemServico salva = osRepository.save(os);
        registrarHistorico(salva, novoStatus, observacao);
        return salva;
    }

    /** Linha do tempo de status da OS (progresso) */
    public List<HistoricoStatusOS> historico(Long osId) {
        buscarPorId(osId);
        return historicoRepository.findByOrdemServicoIdOrderByDataHoraAsc(osId);
    }

    private void registrarHistorico(OrdemServico os, StatusOS status, String observacao) {
        HistoricoStatusOS h = new HistoricoStatusOS();
        h.setOrdemServico(os);
        h.setStatus(status);
        h.setDataHora(LocalDateTime.now());
        h.setObservacao(observacao);
        historicoRepository.save(h);
    }

    public List<OrdemServico> listarTodas() {
        return osRepository.findAll();
    }
}
