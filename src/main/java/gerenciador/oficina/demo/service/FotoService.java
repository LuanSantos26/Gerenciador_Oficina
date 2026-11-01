package gerenciador.oficina.demo.service;

import gerenciador.oficina.demo.domain.CategoriaFoto;
import gerenciador.oficina.demo.domain.FotoOS;
import gerenciador.oficina.demo.domain.OrdemServico;
import gerenciador.oficina.demo.repository.FotoOSRepository;
import gerenciador.oficina.demo.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FotoService {

    @Autowired
    private FotoOSRepository fotoRepository;

    @Autowired
    private OrdemServicoRepository osRepository;

    @Value("${oficina.uploads.dir:uploads}")
    private String pastaUploads;

    public FotoOS salvar(Long osId, MultipartFile arquivo, CategoriaFoto categoria, String descricao) {
        OrdemServico os = osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("OS não encontrada!"));

        if (arquivo == null || arquivo.isEmpty()) {
            throw new RuntimeException("Envie o arquivo da foto.");
        }
        String tipo = arquivo.getContentType();
        if (tipo == null || !tipo.startsWith("image/")) {
            throw new RuntimeException("O arquivo enviado não é uma imagem.");
        }

        String extensao = tipo.equals("image/png") ? ".png" : tipo.equals("image/webp") ? ".webp" : ".jpg";
        String nome = UUID.randomUUID() + extensao;
        try (InputStream in = arquivo.getInputStream()) {
            Path pasta = Paths.get(pastaUploads).toAbsolutePath();
            Files.createDirectories(pasta);
            Files.copy(in, pasta.resolve(nome), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível gravar a foto no servidor.");
        }

        FotoOS foto = new FotoOS();
        foto.setOrdemServico(os);
        foto.setCategoria(categoria);
        foto.setDescricao(descricao);
        foto.setNomeArquivo(nome);
        foto.setContentType(tipo);
        foto.setTamanho(arquivo.getSize());
        return fotoRepository.save(foto);
    }

    public List<FotoOS> listar(Long osId) {
        return fotoRepository.findByOrdemServicoIdOrderByDataEnvioAsc(osId);
    }

    public FotoOS buscar(Long fotoId) {
        return fotoRepository.findById(fotoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada!"));
    }

    public Path caminhoDoArquivo(FotoOS foto) {
        return Paths.get(pastaUploads).toAbsolutePath().resolve(foto.getNomeArquivo());
    }

    public void remover(Long fotoId) {
        FotoOS foto = buscar(fotoId);
        try {
            Files.deleteIfExists(caminhoDoArquivo(foto));
        } catch (IOException e) {
            // se o arquivo já não existe, segue removendo o registro
        }
        fotoRepository.delete(foto);
    }
}
