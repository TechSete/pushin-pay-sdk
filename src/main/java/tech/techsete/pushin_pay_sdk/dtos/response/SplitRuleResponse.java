package tech.techsete.pushin_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO (Data Transfer Object) que representa uma regra de divisão de pagamento na resposta da API Pushin Pay.
 * <p>
 * Este record encapsula os dados de uma regra de divisão (split) retornados pela API Pushin Pay
 * ao consultar uma cobrança ou transação. Contém informações como identificador da regra,
 * tipo, valor, identificadores de transação e conta, além de carimbos de data/hora.
 * </p>
 * <p>
 * As regras de divisão definem como o valor de uma cobrança é distribuído entre diferentes contas,
 * sendo particularmente úteis em cenários de marketplace, onde parte do pagamento vai para o 
 * vendedor e parte para a plataforma.
 * </p>
 * <p>
 * Por ser um record Java, esta classe é imutável, garantindo que os dados não
 * sejam modificados acidentalmente após serem recebidos da API.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * ChargeResponse response = chargeService.getCharge(chargeId);
 * Collection&lt;SplitRuleResponse&gt; splitRules = response.splitRules();
 * 
 * for (SplitRuleResponse rule : splitRules) {
 *     String accountId = rule.accountId();
 *     Long amount = rule.amount();
 *     // Processar informações da regra de divisão
 * }
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.request.SplitRuleRequest
 *
 * @author EdsonIsaac
 */
public record SplitRuleResponse(

        /**
         * Identificador único da regra de divisão.
         * <p>
         * Este ID é gerado pela API Pushin Pay e identifica unicamente esta
         * regra de divisão no sistema.
         * </p>
         */
        @JsonProperty("id")
        Long id,

        /**
         * Tipo da regra de divisão.
         * <p>
         * Indica o tipo de regra aplicada, que pode variar conforme as políticas
         * da Pushin Pay e o contexto da transação.
         * </p>
         */
        @JsonProperty("type")
        String type,

        /**
         * Valor a ser transferido para a conta especificada, em centavos.
         * <p>
         * Este campo representa o valor monetário em centavos que foi ou será
         * destinado à conta identificada pelo {@link #accountId}.
         * </p>
         * <p>
         * Por exemplo, para um valor de R$ 50,00, o amount será 5000.
         * </p>
         */
        @JsonProperty("amount")
        Long amount,

        /**
         * Identificador da transação associada a esta regra de divisão.
         * <p>
         * Refere-se ao ID da transação Pushin Pay que contém esta regra de divisão.
         * </p>
         */
        @JsonProperty("transaction_id")
        String transactionId,

        /**
         * Identificador único da conta que receberá o valor.
         * <p>
         * Este campo contém o ID da conta Pushin Pay para a qual o valor
         * especificado foi ou será transferido como parte da regra de divisão.
         * </p>
         * <p>
         * O formato típico é "acc_" seguido por uma sequência alfanumérica.
         * </p>
         */
        @JsonProperty("account_id")
        String accountId,

        /**
         * Data e hora de criação da regra de divisão no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando a regra de divisão foi criada no sistema Pushin Pay.
         * </p>
         */
        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        /**
         * Data e hora da última atualização da regra de divisão no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando a regra de divisão foi atualizada pela última vez no sistema Pushin Pay.
         * </p>
         */
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt
) implements Serializable { }