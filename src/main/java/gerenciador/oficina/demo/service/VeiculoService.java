package gerenciador.oficina.demo.service;

import gerenciador.oficina.demo.domain.Cliente;
import gerenciador.oficina.demo.domain.Veiculo;
import gerenciador.oficina.demo.repository.ClienteRepository;
import gerenciador.oficina.demo.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Veiculo cadastrar(Long clienteId, Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("Já existe um caminhão cadastrado com esta placa!");
        }

        Cliente dono = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        veiculo.setCliente(dono);
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }
}