package io.github.pacatovisk.msavaliadorcredito.application;

import feign.FeignException;
import io.github.pacatovisk.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.pacatovisk.msavaliadorcredito.application.exception.ErroComunicaoMicroservicesException;
import io.github.pacatovisk.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.pacatovisk.msavaliadorcredito.domain.model.DadosCliente;
import io.github.pacatovisk.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.pacatovisk.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.pacatovisk.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadoCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {

        try {
            ResponseEntity<DadosCliente> dadosClientesResponse = clientesClient.getByCpf(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClientesResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException("Dados do cliente não encontrado para o CPF informado!");
            }
            throw new ErroComunicaoMicroservicesException(e.getMessage(), e.status());
        }
    }

}
