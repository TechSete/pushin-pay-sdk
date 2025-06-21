package tech.techsete.pushin_pay_sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest;
import tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse;

import java.util.Map;

/**
 * Serviço responsável por operações relacionadas a cobranças na API Pushin Pay.
 * <p>
 * Esta classe oferece métodos para criar e gerenciar cobranças através da integração
 * com a API Pushin Pay. Utiliza WebClient para realizar chamadas HTTP à API e
 * ObjectMapper para serialização/desserialização de objetos JSON.
 * </p>
 * <p>
 * O serviço é registrado no contexto Spring com o nome "pushinPaySDKChargeService",
 * permitindo sua injeção em outros componentes da aplicação.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * ChargeRequest request = ChargeRequest.builder()
 *     .value(10000L)  // R$ 100,00 em centavos
 *     .webhookUrl("https://meusite.com.br/webhook/notificacoes")
 *     .build();
 *     
 * Map&lt;String, String&gt; headers = Map.of(
 *     "Authorization", "Bearer seu-token-aqui"
 * );
 * 
 * ChargeResponse response = chargeService.create(headers, request);
 * </pre>
 * 
 * @see tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 *
 * @author EdsonIsaac
 */
@Service(value = "pushinPaySDKChargeService")
public class ChargeService {

    private final ObjectMapper mapper;
    private final WebClient webClient;

    /**
     * Constrói uma nova instância do serviço de cobranças.
     * <p>
     * Este construtor inicializa o serviço com as dependências necessárias para
     * realizar operações de cobrança na API Pushin Pay.
     * </p>
     * 
     * @param mapper    O ObjectMapper utilizado para serialização/desserialização de JSON
     * @param webClient O WebClient pré-configurado para comunicação com a API Pushin Pay
     */
    public ChargeService(
            ObjectMapper mapper,
            @Qualifier("pushinPayWebClient")
            WebClient webClient
    ) {
        this.mapper = mapper;
        this.webClient = webClient;
    }

    /**
     * Cria uma nova cobrança na API Pushin Pay.
     * <p>
     * Este método envia uma requisição para o endpoint "/api/pix/cashIn" da API Pushin Pay
     * para criar uma nova cobrança com os parâmetros especificados. Os cabeçalhos de autenticação
     * e outros cabeçalhos necessários devem ser fornecidos no mapa de headers.
     * </p>
     * <p>
     * A requisição é feita de forma síncrona (bloqueante) e retorna a resposta da API
     * contendo os detalhes da cobrança criada, incluindo o QR Code Pix para pagamento.
     * </p>
     * 
     * @param headers       Mapa contendo os cabeçalhos HTTP a serem enviados na requisição,
     *                      como token de autenticação, etc.
     * @param chargeRequest Objeto contendo os detalhes da cobrança a ser criada,
     *                      como valor, URL de webhook e regras de divisão
     * @return              Um objeto {@link ChargeResponse} contendo os detalhes da cobrança criada
     * @throws JsonProcessingException Se ocorrer um erro durante a serialização do objeto de requisição
     */
    public ChargeResponse create(Map<String, ?> headers, ChargeRequest chargeRequest) throws JsonProcessingException {

        return webClient.post()
                .uri("/api/pix/cashIn")
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .body(Mono.just(mapper.writeValueAsString(chargeRequest)), String.class)
                .retrieve()
                .bodyToMono(ChargeResponse.class)
                .block();
    }
}