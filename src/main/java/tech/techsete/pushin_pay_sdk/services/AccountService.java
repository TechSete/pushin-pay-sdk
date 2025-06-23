package tech.techsete.pushin_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Serviço responsável pela interação com a API Pushin Pay para operações relacionadas a contas.
 * <p>
 * Esta classe fornece funcionalidades para consultar e verificar contas na plataforma Pushin Pay,
 * utilizando chamadas reativas com WebFlux. Implementa uma interface de serviço para verificação
 * de existência de contas pelo ID.
 * </p>
 * <p>
 * O serviço é configurado com um bean específico através da anotação @Service com o valor
 * "pushinPaySDKAccountService" para injeção de dependência em outros componentes.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * // Injetar o serviço em outro componente
 * private final AccountService accountService;
 *
 * // Verificar se uma conta existe
 * Map&lt;String, String&gt; headers = Map.of("Authorization", "Bearer seu-token-aqui");
 * boolean exists = accountService.existsByAccountId(headers, "acc_123456789");
 * </pre>
 *
 * @author EdsonIsaac
 * @see org.springframework.stereotype.Service
 * @see org.springframework.web.reactive.function.client.WebClient
 * @see reactor.core.publisher.Mono
 */
@Service(value = "pushinPaySDKAccountService")
public class AccountService {

    /**
     * Cliente HTTP reativo para comunicação com a API Pushin Pay.
     * <p>
     * Este WebClient é configurado com a URL base e outras configurações necessárias
     * para comunicação com a API Pushin Pay. É injetado através do construtor
     * e identificado pelo qualificador "pushinPayWebClient".
     * </p>
     */
    private final WebClient webClient;

    /**
     * Construtor para injeção do WebClient.
     * <p>
     * Inicializa a instância do serviço com um WebClient pré-configurado para
     * comunicação com a API Pushin Pay.
     * </p>
     *
     * @param webClient cliente HTTP reativo configurado para a API Pushin Pay,
     *                  identificado pelo qualificador "pushinPayWebClient"
     */
    public AccountService(@Qualifier("pushinPayWebClient")
                          WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * Verifica se uma conta existe na plataforma Pushin Pay pelo seu ID.
     * <p>
     * Este método realiza uma chamada GET síncrona para o endpoint de verificação
     * de contas da API Pushin Pay, passando os cabeçalhos HTTP necessários e o ID
     * da conta a ser verificada.
     * </p>
     * <p>
     * A chamada é realizada de forma bloqueante (usando .block()), o que significa
     * que o método aguardará a resposta da API antes de retornar.
     * </p>
     *
     * @param headers   mapa de cabeçalhos HTTP a serem incluídos na requisição,
     *                  geralmente contendo o token de autenticação
     * @param accountId identificador único da conta a ser verificada, geralmente
     *                  no formato "acc_" seguido por uma sequência alfanumérica
     * @return true se a conta existe, false caso contrário
     * @throws RuntimeException se ocorrer um erro de comunicação com a API ou
     *                          se a API retornar um código de status não esperado
     */
    public boolean existsByAccountId(Map<String, ?> headers, String accountId) {
        return Boolean.TRUE.equals(webClient.get()
                .uri("/api/accounts/check/" + accountId)
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .exchangeToMono(this::handleResponse)
                .block());
    }

    /**
     * Processa a resposta da API para verificação de existência de conta.
     * <p>
     * Este método privado é utilizado internamente para interpretar a resposta HTTP
     * da API Pushin Pay e convertê-la em um valor booleano que indica se a conta existe.
     * </p>
     * <p>
     * A interpretação é baseada no código de status HTTP:
     * </p>
     * <ul>
     *   <li>200 (OK): A conta existe (retorna true)</li>
     *   <li>404 (NOT_FOUND): A conta não existe (retorna false)</li>
     *   <li>Outros códigos: Propaga o erro como uma exceção</li>
     * </ul>
     *
     * @param response resposta HTTP recebida da API Pushin Pay
     * @return Mono contendo true se a conta existe, false caso contrário
     * @throws RuntimeException se a API retornar um código de status não esperado
     */
    private Mono<Boolean> handleResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return Mono.just(true);
        } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.just(false);
        } else {
            return response.createException().flatMap(Mono::error);
        }
    }
}