package tech.techsete.pushin_pay_sdk.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * DTO (Data Transfer Object) para definir regras de divisão de pagamento em cobranças.
 * <p>
 * Esta classe representa uma regra individual de divisão de pagamento (split payment)
 * para uso em requisições de criação de cobrança na API Pushin Pay. Cada instância
 * define um valor a ser destinado a uma conta específica.
 * </p>
 * <p>
 * As regras de divisão permitem que o valor de uma cobrança seja distribuído entre
 * múltiplas contas, sendo útil para cenários como marketplaces, onde parte do pagamento
 * vai para o vendedor e parte para a plataforma.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * SplitRuleRequest rule = SplitRuleRequest.builder()
 *     .value(5000L)  // R$ 50,00 em centavos
 *     .accountId("acc_123456789")
 *     .build();
 * </pre>
 * <p>
 * Esta classe é geralmente utilizada como parte de uma coleção no
 * {@link tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest}.
 * </p>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest
 *
 * @author EdsonIsaac
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SplitRuleRequest implements Serializable {

    /**
     * Valor a ser transferido para a conta especificada, em centavos.
     * <p>
     * Este campo representa o valor monetário em centavos que será destinado
     * à conta identificada pelo {@link #accountId}.
     * </p>
     * <p>
     * Por exemplo, para transferir R$ 50,00, o valor deve ser 5000.
     * </p>
     */
    @JsonProperty("value")
    private Long value;

    /**
     * Identificador único da conta que receberá o valor.
     * <p>
     * Este campo contém o ID da conta Pushin Pay para a qual o valor
     * especificado será transferido como parte da regra de divisão.
     * </p>
     */
    @JsonProperty("account_id")
    private String accountId;
}