package gerenciador.oficina.demo.domain;

public enum CategoriaFoto {
    VEICULO,   // fotos do veículo na entrada (frente, laterais, painel com km)
    PROBLEMA,  // defeito relatado: vazamento, peça quebrada, tela do scanner
    SERVICO,   // durante o serviço: desmontagem, medições
    PECA,      // peça substituída (velha ao lado da nova)
    SAIDA      // veículo pronto na entrega
}
