package io.github.pacatovisk.msavaliadorcredito.application;

import io.github.pacatovisk.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadoCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "OK";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity<SituacaoCliente> getSituacaoCliente(@RequestParam("cpf") String cpf) {

        SituacaoCliente situacaoCliente = avaliadorCreditoService
                .obterSituacaoCliente(cpf);

        return ResponseEntity.ok(situacaoCliente);
    }

}
