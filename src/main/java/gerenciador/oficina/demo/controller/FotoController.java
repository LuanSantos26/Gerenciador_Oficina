package gerenciador.oficina.demo.controller;

import gerenciador.oficina.demo.domain.CategoriaFoto;
import gerenciador.oficina.demo.domain.FotoOS;
import gerenciador.oficina.demo.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class FotoController {

    @Autowired
    private FotoService service;

    // multipart/form-data: arquivo (imagem), categoria (VEICULO, PROBLEMA, SERVICO, PECA, SAIDA), descricao
    @PostMapping(value = "/api/ordens-servico/{id}/fotos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoOS> enviar(@PathVariable Long id,
                                         @RequestParam("arquivo") MultipartFile arquivo,
                                         @RequestParam(value = "categoria", defaultValue = "PROBLEMA") CategoriaFoto categoria,
                                         @RequestParam(value = "descricao", required = false) String descricao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(id, arquivo, categoria, descricao));
    }

    @GetMapping("/api/ordens-servico/{id}/fotos")
    public ResponseEntity<List<FotoOS>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(service.listar(id));
    }

    // Devolve a imagem em si (usada direto na tag <img src="...">)
    @GetMapping("/api/fotos/{fotoId}/arquivo")
    public ResponseEntity<Resource> arquivo(@PathVariable Long fotoId) {
        FotoOS foto = service.buscar(fotoId);
        Path caminho = service.caminhoDoArquivo(foto);
        if (!Files.exists(caminho)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foto.getContentType() != null ? foto.getContentType() : "image/jpeg"))
                .body(new FileSystemResource(caminho));
    }

    @DeleteMapping("/api/fotos/{fotoId}")
    public ResponseEntity<Void> remover(@PathVariable Long fotoId) {
        service.remover(fotoId);
        return ResponseEntity.noContent().build();
    }
}
