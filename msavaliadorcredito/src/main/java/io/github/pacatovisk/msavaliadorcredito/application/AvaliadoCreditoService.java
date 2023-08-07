package io.github.pacatovisk.msavaliadorcredito.application;

import io.github.pacatovisk.msavaliadorcredito.domain.model.DadosCliente;
import io.github.pacatovisk.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.pacatovisk.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadoCreditoService {

    private final ClienteResourceClient clientesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {

        ResponseEntity<DadosCliente> dadosClientesResponse = clientesClient.getByCpf(cpf);

        return SituacaoCliente.builder().cliente(dadosClientesResponse.getBody()).build();
    }

}
