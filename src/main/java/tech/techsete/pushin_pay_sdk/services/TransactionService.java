package tech.techsete.pushin_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse;

import java.util.Map;

/**
 * Serviço responsável por operações relacionadas a transações na API Pushin Pay.
 * <p>
 * Esta classe oferece métodos para consultar transações através da integração
 * com a API Pushin Pay. Utiliza {@link WebClient} para realizar chamadas HTTP.
 * </p>
 * <p>
 * O serviço é registrado no contexto Spring com o nome "pushinPaySDKTransactionService",
 * permitindo sua injeção em outros componentes da aplicação.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * String transactionId = "txn_1234567890";
 *
 * Map&lt;String, String&gt; headers = Map.of(
 *     "Authorization", "Bearer seu-token-aqui"
 * );
 *
 * TransactionResponse response = transactionService.retrieveByTransactionId(headers, transactionId);
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse
 *
 * @author EdsonIsaac
 */
@Service(value = "pushinPaySDKTransactionService")
public class TransactionService {

    private final WebClient webClient;

    /**
     * Constrói uma nova instância do serviço de transações.
     * <p>
     * Este construtor inicializa o serviço com o {@link WebClient} necessário para
     * realizar requisições HTTP à API Pushin Pay.
     * </p>
     *
     * @param webClient O WebClient pré-configurado para comunicação com a API Pushin Pay
     */
    public TransactionService(
            @Qualifier("pushinPayWebClient")
            WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * Recupera os detalhes de uma transação com base no ID fornecido.
     * <p>
     * Este método envia uma requisição GET para o endpoint "/api/transactions/{transactionId}"
     * da API Pushin Pay, utilizando os cabeçalhos especificados, para obter informações
     * sobre uma transação previamente registrada.
     * </p>
     * <p>
     * A requisição é feita de forma síncrona (bloqueante) e retorna a resposta da API
     * contendo os detalhes da transação, como status, valor, data e identificadores.
     * </p>
     *
     * @param headers        Mapa contendo os cabeçalhos HTTP a serem enviados na requisição,
     *                       como token de autenticação, etc.
     * @param transactionId  Identificador da transação a ser consultada
     * @return               Um objeto {@link TransactionResponse} contendo os detalhes da transação
     */
    public TransactionResponse retrieveByTransactionId(Map<String, ?> headers, String transactionId) {

        return webClient.get()
                .uri("/api/transactions/" + transactionId)
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }
}
