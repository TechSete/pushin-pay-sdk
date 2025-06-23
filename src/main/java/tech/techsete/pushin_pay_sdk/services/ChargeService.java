package tech.techsete.pushin_pay_sdk.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest;
import tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse;
import tech.techsete.pushin_pay_sdk.exceptions.InvalidChargeRequestException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Serviço responsável pela criação de cobranças via API Pushin Pay.
 * <p>
 * Esta classe realiza chamadas HTTP para o endpoint <code>/api/pix/cashIn</code> da
 * Pushin Pay, com validação completa da requisição antes do envio.
 * </p>
 *
 * <p>
 * Funcionalidades principais:
 * </p>
 * <ul>
 *     <li>Criação de cobranças Pix via WebClient</li>
 *     <li>Validação de valores e URL de webhook</li>
 *     <li>Validação de regras de divisão com verificação de existência das contas envolvidas</li>
 * </ul>
 *
 * <p>
 * Este serviço utiliza {@link WebClient} como cliente HTTP e {@link AccountService}
 * para validar as contas fornecidas nas regras de divisão.
 * </p>
 *
 * <p>
 * O serviço está registrado como <code>"pushinPaySDKChargeService"</code> no contexto Spring.
 * </p>
 *
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>{@code
 * ChargeRequest request = ChargeRequest.builder()
 *     .value(10000L)  // R$ 100,00 em centavos
 *     .webhookUrl("https://meusite.com.br/webhook/notificacoes")
 *     .build();
 *
 * Map<String, String> headers = Map.of(
 *     "Authorization", "Bearer seu-token-aqui"
 * );
 *
 * ChargeResponse response = chargeService.create(headers, request);
 * }</pre>
 *
 * @author EdsonIsaac
 * @see tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 * @see tech.techsete.pushin_pay_sdk.services.AccountService
 * @see tech.techsete.pushin_pay_sdk.exceptions.InvalidChargeRequestException
 */
@Service(value = "pushinPaySDKChargeService")
public class ChargeService {

    private final AccountService accountService;
    private final WebClient webClient;

    /**
     * Construtor da classe {@link ChargeService}.
     * <p>
     * Inicializa a instância do serviço com os componentes necessários para comunicação
     * com a API Pushin Pay e validação de regras de divisão (split rules).
     * </p>
     *
     * <p>
     * Os beans são injetados via construtor com uso explícito do {@link Qualifier} para
     * garantir a escolha correta das implementações registradas no contexto Spring.
     * </p>
     *
     * @param accountService serviço responsável por verificar a existência de contas
     *                       utilizadas nas regras de divisão da cobrança
     * @param webClient cliente HTTP reativo configurado com a base da API Pushin Pay,
     *                  utilizado para envio das requisições
     */
    public ChargeService(@Qualifier("pushinPaySDKAccountService") AccountService accountService,
                         @Qualifier("pushinPayWebClient") WebClient webClient
    ) {
        this.accountService = accountService;
        this.webClient = webClient;
    }

    /**
     * Cria uma cobrança na API Pushin Pay.
     * <p>
     * Este método envia uma requisição HTTP POST para o endpoint <code>/api/pix/cashIn</code>,
     * utilizando os dados informados no {@link ChargeRequest}. A requisição é realizada
     * de forma síncrona (bloqueante) utilizando {@link WebClient}.
     * </p>
     *
     * <p>
     * Antes do envio, o método executa validações obrigatórias:
     * </p>
     * <ul>
     *     <li>Verifica se o valor da cobrança é positivo e não nulo</li>
     *     <li>Valida a URL do webhook (se presente)</li>
     *     <li>Valida cada regra de divisão (split), incluindo:
     *         <ul>
     *             <li>Valor positivo e não nulo</li>
     *             <li>Presença de accountId</li>
     *             <li>Verificação da existência da conta via {@link AccountService}</li>
     *         </ul>
     *     </li>
     *     <li>Garante que a soma das regras de divisão não ultrapasse o valor total</li>
     * </ul>
     *
     * @param headers mapa de cabeçalhos HTTP, incluindo tokens de autenticação
     * @param chargeRequest objeto com os detalhes da cobrança (valor, webhook, split rules)
     * @return instância de {@link ChargeResponse} com os dados da cobrança criada
     * @throws InvalidChargeRequestException caso algum dado seja inválido ou inconsistente
     */
    public ChargeResponse create(Map<String, ?> headers, ChargeRequest chargeRequest) {

        validate(headers, chargeRequest);

        return webClient.post()
                .uri("/api/pix/cashIn")
                .headers(httpHeaders -> headers.forEach((key, value) -> httpHeaders.add(key, value.toString())))
                .bodyValue(chargeRequest)
                .retrieve()
                .bodyToMono(ChargeResponse.class)
                .block();
    }

    /**
     * Valida os dados do {@link ChargeRequest} antes de criar uma cobrança.
     * <p>
     * Esta função é utilizada internamente para garantir que a requisição está
     * corretamente formatada e consistente antes de ser enviada à API.
     * </p>
     *
     * <p>
     * As seguintes validações são realizadas:
     * </p>
     * <ul>
     *     <li>O objeto <code>chargeRequest</code> não pode ser nulo</li>
     *     <li>O valor deve ser maior que zero</li>
     *     <li>A URL do webhook (se fornecida) deve ser válida</li>
     *     <li>Cada regra de divisão (split) deve:
     *         <ul>
     *             <li>Ter valor maior que zero</li>
     *             <li>Possuir um accountId não nulo e não vazio</li>
     *             <li>Referenciar uma conta existente na plataforma (verificado via {@link AccountService})</li>
     *         </ul>
     *     </li>
     *     <li>A soma dos valores das regras de divisão não pode exceder o valor da cobrança</li>
     * </ul>
     *
     * @param headers mapa de cabeçalhos HTTP utilizados para autenticar a requisição à API
     * @param chargeRequest objeto com os dados da cobrança
     * @throws InvalidChargeRequestException se qualquer validação falhar
     */
    private void validate(Map<String, ?> headers, ChargeRequest chargeRequest) {
        if (chargeRequest == null) {
            throw new InvalidChargeRequestException("O objeto ChargeRequest não pode ser nulo!");
        }

        if (chargeRequest.getValue() == null) {
            throw new InvalidChargeRequestException("O valor da cobrança não pode ser nulo!");
        }

        if (chargeRequest.getValue() <= 0) {
            throw new InvalidChargeRequestException("O valor da cobrança deve ser maior que zero!");
        }

        if (chargeRequest.getWebhookUrl() != null) {
            try {
                new URL(chargeRequest.getWebhookUrl());
            } catch (MalformedURLException e) {
                throw new InvalidChargeRequestException("A URL do webhook é inválida: " + chargeRequest.getWebhookUrl());
            }
        }

        if (chargeRequest.getSplitRules() != null && !chargeRequest.getSplitRules().isEmpty()) {
            long sumSplitValues = 0L;

            for (var rule : chargeRequest.getSplitRules()) {
                if (rule.getValue() == null || rule.getValue() <= 0) {
                    throw new InvalidChargeRequestException("Cada regra de divisão deve ter um valor maior que zero!");
                }

                if (rule.getAccountId() == null || rule.getAccountId().isBlank()) {
                    throw new InvalidChargeRequestException("Cada regra de divisão deve possuir um accountId válido!");
                }

                boolean accountExists = accountService.existsByAccountId(headers, rule.getAccountId());
                if (!accountExists) {
                    throw new InvalidChargeRequestException(
                            "Conta inválida na regra de divisão: accountId '" + rule.getAccountId() + "' não existe."
                    );
                }

                sumSplitValues += rule.getValue();
            }

            if (sumSplitValues > chargeRequest.getValue()) {
                throw new InvalidChargeRequestException("A soma das regras de divisão não pode ultrapassar o valor total da cobrança!");
            }
        }
    }
}