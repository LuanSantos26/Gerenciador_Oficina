package gerenciador.oficina.demo.controller;

import gerenciador.oficina.demo.domain.ItemServico;
import gerenciador.oficina.demo.domain.OrdemServico;
import gerenciador.oficina.demo.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService service;

    @PostMapping
    public ResponseEntity<OrdemServico> abrirOS(@RequestBody Map<String, Object> payload) {
        Long veiculoId = Long.valueOf(payload.get("veiculoId").toString());
        String defeito = payload.get("defeitoRelatado").toString();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirOS(veiculoId, defeito));
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<ItemServico> adicionarItem(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        String descricao = payload.get("descricao").toString();
        BigDecimal valor = new BigDecimal(payload.get("valor").toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarItem(id, descricao, valor));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OrdemServico> finalizarOS(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarOS(id));
    }

    @GetMapping
    public ResponseEntity<List<OrdemServico>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }
}