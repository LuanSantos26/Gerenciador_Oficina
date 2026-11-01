package gerenciador.oficina.demo.controller;

import gerenciador.oficina.demo.domain.HistoricoStatusOS;
import gerenciador.oficina.demo.domain.ItemServico;
import gerenciador.oficina.demo.domain.OrdemServico;
import gerenciador.oficina.demo.domain.StatusOS;
import gerenciador.oficina.demo.domain.TipoItem;
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

    // Abre a OS. Body: { "veiculoId": 1, "defeitoRelatado": "...", "kmEntrada": 350000 }  (kmEntrada é opcional)
    @PostMapping
    public ResponseEntity<OrdemServico> abrirOS(@RequestBody Map<String, Object> payload) {
        Long veiculoId = Long.valueOf(payload.get("veiculoId").toString());
        String defeito = payload.get("defeitoRelatado").toString();
        Integer kmEntrada = inteiro(payload.get("kmEntrada"));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.abrirOS(veiculoId, defeito, kmEntrada));
    }

    // Body: { "descricao", "valor", "funcionarioId", "tipo": "PECA" | "MAO_DE_OBRA", "quantidade", "codigoPeca" }
    // (tipo, quantidade e codigoPeca são opcionais)
    @PostMapping("/{id}/itens")
    public ResponseEntity<ItemServico> adicionarItem(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        String descricao = payload.get("descricao").toString();
        BigDecimal valor = new BigDecimal(payload.get("valor").toString());
        Long funcionarioId = Long.valueOf(payload.get("funcionarioId").toString());
        TipoItem tipo = payload.get("tipo") != null ? TipoItem.valueOf(payload.get("tipo").toString()) : TipoItem.MAO_DE_OBRA;
        Integer quantidade = inteiro(payload.get("quantidade"));
        String codigoPeca = payload.get("codigoPeca") != null ? payload.get("codigoPeca").toString() : null;

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.adicionarItem(id, descricao, valor, funcionarioId, tipo, quantidade, codigoPeca));
    }

    // Saída do veículo. Body opcional: { "kmSaida": 350010, "retiradoPor": "João" }
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OrdemServico> finalizarOS(@PathVariable Long id, @RequestBody(required = false) Map<String, Object> payload) {
        Integer kmSaida = payload != null ? inteiro(payload.get("kmSaida")) : null;
        String retiradoPor = payload != null && payload.get("retiradoPor") != null ? payload.get("retiradoPor").toString() : null;
        return ResponseEntity.ok(service.finalizarOS(id, kmSaida, retiradoPor));
    }

    @GetMapping
    public ResponseEntity<List<OrdemServico>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemServico>> listarItens(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarItens(id));
    }

    // Body: { "status": "AGUARDANDO_PECA", "observacao": "..." }
    @PutMapping("/{id}/status")
    public ResponseEntity<OrdemServico> alterarStatus(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        StatusOS status = StatusOS.valueOf(payload.get("status").toString());
        String observacao = payload.get("observacao") != null ? payload.get("observacao").toString() : null;
        return ResponseEntity.ok(service.alterarStatus(id, status, observacao));
    }

    // Progresso: todas as mudanças de status com data/hora
    @GetMapping("/{id}/historico")
    public ResponseEntity<List<HistoricoStatusOS>> historico(@PathVariable Long id) {
        return ResponseEntity.ok(service.historico(id));
    }

    private static Integer inteiro(Object valor) {
        if (valor == null || valor.toString().isBlank()) return null;
        return new BigDecimal(valor.toString()).intValue();
    }
}
