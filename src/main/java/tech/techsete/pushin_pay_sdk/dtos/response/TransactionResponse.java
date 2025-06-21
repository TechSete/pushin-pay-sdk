package tech.techsete.pushin_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.techsete.pushin_pay_sdk.enums.TransactionStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Collection;

/**
 * DTO (Data Transfer Object) que representa uma transação completa na API Pushin Pay.
 * <p>
 * Este record encapsula todos os dados de uma transação retornados pela API Pushin Pay,
 * incluindo informações gerais como identificador, status e valor, bem como detalhes
 * específicos sobre o tipo de pagamento, detalhes do pagador, regras de divisão,
 * configurações de webhook e produtos associados.
 * </p>
 * <p>
 * A classe é mais abrangente que {@link ChargeResponse}, pois representa transações
 * que podem ter diferentes origens e tipos de pagamento, não apenas cobranças Pix.
 * Fornece um modelo completo de dados de transação para aplicativos que precisam
 * gerenciar pagamentos através da API Pushin Pay.
 * </p>
 * <p>
 * Por ser um record Java, esta classe é imutável, garantindo que os dados não
 * sejam modificados acidentalmente após serem recebidos da API.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * TransactionResponse transaction = transactionService.getTransaction(transactionId);
 * TransactionStatus status = transaction.status();
 * Long value = transaction.value();
 * String payerName = transaction.payerName();
 * 
 * if (transaction.pixDetails() != null) {
 *     // Processar detalhes específicos de Pix
 * }
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.enums.TransactionStatus
 * @see tech.techsete.pushin_pay_sdk.dtos.response.PixDetailsResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.SplitRuleResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.WebhookResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionProductResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 *
 * @author EdsonIsaac
 */
public record TransactionResponse(

        /**
         * Identificador único da transação.
         * <p>
         * Este ID é gerado pela API Pushin Pay e pode ser utilizado para
         * consultar ou referenciar a transação em operações futuras.
         * </p>
         */
        @JsonProperty("id")
        String id,

        /**
         * Status atual da transação.
         * <p>
         * Indica o estado atual da transação no sistema Pushin Pay,
         * podendo assumir valores como CREATED ou PAID.
         * dependendo do ciclo de vida da transação.
         * </p>
         */
        @JsonProperty("status")
        TransactionStatus status,

        /**
         * Valor da transação em centavos.
         * <p>
         * Este campo representa o valor monetário total da transação em centavos.
         * Por exemplo, para uma transação de R$ 100,00, o valor será 10000.
         * </p>
         */
        @JsonProperty("value")
        Long value,

        /**
         * Descrição da transação.
         * <p>
         * Texto descritivo que fornece informações adicionais sobre a transação,
         * como finalidade do pagamento, identificação de pedido ou informações
         * complementares para o pagador.
         * </p>
         */
        @JsonProperty("description")
        String description,

        /**
         * Tipo de pagamento utilizado na transação.
         * <p>
         * Identifica o método de pagamento utilizado para esta transação,
         * como "pix".
         * </p>
         */
        @JsonProperty("payment_type")
        String paymentType,

        /**
         * Data e hora de criação da transação no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando a transação foi criada no sistema Pushin Pay.
         * </p>
         */
        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        /**
         * Data e hora da última atualização da transação no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando a transação foi atualizada pela última vez no sistema Pushin Pay,
         * como quando seu status mudou.
         * </p>
         */
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt,

        /**
         * URL para notificações webhook.
         * <p>
         * URL configurada para receber notificações sobre eventos relacionados
         * a esta transação, como confirmação de pagamento ou estorno.
         * </p>
         */
        @JsonProperty("webhook_url")
        String webhookUrl,

        /**
         * Regras de divisão de pagamento aplicadas à transação.
         * <p>
         * Coleção com as regras de divisão (split) configuradas para esta transação,
         * contendo detalhes sobre como o valor foi ou será distribuído entre diferentes contas.
         * </p>
         */
        @JsonProperty("split_rules")
        Collection<SplitRuleResponse> splitRules,

        /**
         * Identificador fim a fim da transação Pix.
         * <p>
         * Código único gerado pelo Banco Central do Brasil para identificar
         * a transação Pix no Sistema de Pagamentos Brasileiro (SPB).
         * </p>
         * <p>
         * Este campo só é preenchido para transações do tipo Pix.
         * </p>
         */
        @JsonProperty("end_to_end_id")
        String endToEndId,

        /**
         * Nome do pagador da transação.
         * <p>
         * Nome da pessoa física ou jurídica que efetuou o pagamento da transação.
         * Este campo só é preenchido após o pagamento ser confirmado.
         * </p>
         */
        @JsonProperty("payer_name")
        String payerName,

        /**
         * Documento de identificação do pagador.
         * <p>
         * CPF ou CNPJ do pagador da transação, sem pontuação.
         * Este campo só é preenchido após o pagamento ser confirmado.
         * </p>
         */
        @JsonProperty("payer_national_registration")
        String payerNationalRegistration,

        /**
         * Informações detalhadas sobre notificações webhook.
         * <p>
         * Contém dados sobre tentativas de notificação, status HTTP e outros
         * detalhes relacionados às chamadas webhook para esta transação.
         * </p>
         */
        @JsonProperty("webhook")
        WebhookResponse webhook,

        /**
         * Detalhes específicos para transações Pix.
         * <p>
         * Este campo contém informações adicionais específicas para transações
         * do tipo Pix, como código EMV, data de expiração e outros parâmetros
         * relevantes para este método de pagamento.
         * </p>
         * <p>
         * O campo será nulo para transações que não são do tipo Pix.
         * </p>
         */
        @JsonProperty("pix_details")
        PixDetailsResponse pixDetails,

        /**
         * Produtos associados à transação.
         * <p>
         * Coleção de produtos ou serviços que compõem esta transação,
         * permitindo o rastreamento detalhado dos itens incluídos em cada
         * pagamento processado.
         * </p>
         */
        @JsonProperty("transaction_product")
        Collection<TransactionProductResponse> transactionProduct
) implements Serializable {
}