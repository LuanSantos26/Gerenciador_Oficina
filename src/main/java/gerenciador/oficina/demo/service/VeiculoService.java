package gerenciador.oficina.demo.service;

import gerenciador.oficina.demo.domain.Veiculo;
import gerenciador.oficina.demo.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    public Veiculo cadastrar(Veiculo veiculo) {
        if (repository.existsByPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("Já existe um caminhão cadastrado com esta placa!");
        }
        return repository.save(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return repository.findAll();
    }
}