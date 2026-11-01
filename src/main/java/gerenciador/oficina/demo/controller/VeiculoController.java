package gerenciador.oficina.demo.controller;

import gerenciador.oficina.demo.domain.Veiculo;
import gerenciador.oficina.demo.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    // Novo método que exige o ID do cliente para cadastrar o caminhão
    @PostMapping("/{clienteId}")
    public ResponseEntity<Veiculo> cadastrar(@PathVariable Long clienteId, @RequestBody Veiculo veiculo) {
        Veiculo novoVeiculo = service.cadastrar(clienteId, veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVeiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
}