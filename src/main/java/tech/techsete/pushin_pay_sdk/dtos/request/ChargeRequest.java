package tech.techsete.pushin_pay_sdk.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO (Data Transfer Object) para requisições de criação de cobranças na API Pushin Pay.
 * <p>
 * Esta classe representa os dados necessários para criar uma nova cobrança
 * através da API Pushin Pay. Ela contém informações como o valor da cobrança,
 * a URL para notificações webhook e regras de divisão de pagamento, se aplicável.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * ChargeRequest request = ChargeRequest.builder()
 *     .value(10000L)  // Valor em centavos (R$ 100,00)
 *     .webhookUrl("https://meusite.com.br/webhook/notificacoes")
 *     .build();
 * </pre>
 * <p>
 * Este objeto é utilizado pelo {@link tech.techsete.pushin_pay_sdk.services.ChargeService}
 * para enviar requisições de criação de cobrança para a API Pushin Pay.
 * </p>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.request.SplitRuleRequest
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 * @see tech.techsete.pushin_pay_sdk.services.ChargeService
 *
 * @author EdsonIsaac
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest implements Serializable {

    /**
     * Valor da cobrança em centavos.
     * <p>
     * Este campo representa o valor monetário da cobrança em centavos.
     * Por exemplo, para uma cobrança de R$ 100,00, o valor deve ser 10000.
     * </p>
     */
    @JsonProperty("value")
    private Long value;

    /**
     * URL para notificações webhook.
     * <p>
     * Quando ocorrerem eventos relacionados a esta cobrança (como pagamento confirmado),
     * a API Pushin Pay enviará notificações para esta URL.
     * </p>
     * <p>
     * A URL deve estar acessível pela internet e configurada para receber
     * requisições POST com payload JSON.
     * </p>
     */
    @JsonProperty("webhook_url")
    private String webhookUrl;

    /**
     * Regras de divisão de pagamento.
     * <p>
     * Esta coleção contém as regras para dividir o valor da cobrança entre
     * diferentes contas, permitindo implementar funcionalidades de split payment.
     * </p>
     * <p>
     * Cada regra de divisão específica um valor e o ID da conta que receberá
     * essa parte do pagamento.
     * </p>
     */
    @JsonProperty("split_rules")
    private Collection<SplitRuleRequest> splitRules;
}