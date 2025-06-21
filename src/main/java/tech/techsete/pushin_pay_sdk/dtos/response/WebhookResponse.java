package tech.techsete.pushin_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * DTO (Data Transfer Object) que representa informações sobre notificações webhook na API Pushin Pay.
 * <p>
 * Este record encapsula os dados relacionados às tentativas de notificação webhook realizadas
 * pela API Pushin Pay. Inclui informações como identificador da notificação, URL de destino,
 * códigos de status HTTP, número de tentativas, estado de conclusão e identificadores de recursos
 * relacionados como transações, transferências e contas.
 * </p>
 * <p>
 * Os webhooks são utilizados pela Pushin Pay para notificar sistemas externos sobre eventos
 * importantes, como confirmação de pagamentos, estornos, falhas de processamento ou mudanças
 * de status em recursos monitorados.
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
 * WebhookResponse webhook = transaction.webhook();
 *
 * if (webhook != null &amp;&amp; webhook.complete()) {
 *     // Webhook foi entregue com sucesso
 *     Integer httpStatus = webhook.httpStatus();
 *     // Processar informações da notificação
 * }
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse
 *
 * @author EdsonIsaac
 */
public record WebhookResponse(
        /**
         * Identificador único da notificação webhook.
         * <p>
         * Este ID é gerado pela API Pushin Pay e identifica unicamente esta
         * tentativa de notificação no sistema.
         * </p>
         */
        @JsonProperty("id")
        String id,

        /**
         * URL de destino para a qual a notificação webhook foi enviada.
         * <p>
         * Endereço HTTP/HTTPS completo do endpoint que recebeu ou receberá
         * a notificação webhook dos eventos relacionados.
         * </p>
         */
        @JsonProperty("url")
        String url,

        /**
         * Código de status HTTP retornado pelo servidor de destino.
         * <p>
         * Indica o código de resposta HTTP recebido ao entregar a notificação.
         * Valores na faixa 2xx (como 200 OK) geralmente indicam sucesso na entrega.
         * </p>
         * <p>
         * Este campo pode ser nulo se a notificação ainda não foi enviada ou
         * se ocorreu um erro de conexão antes de receber um código de status.
         * </p>
         */
        @JsonProperty("http_status")
        Integer httpStatus,

        /**
         * Código de erro HTTP caso a entrega tenha falhado.
         * <p>
         * Fornece informações adicionais sobre o erro ocorrido durante a tentativa
         * de entrega da notificação, quando aplicável.
         * </p>
         * <p>
         * Este campo pode ser nulo se a notificação foi entregue com sucesso ou
         * se ainda não houve tentativa de entrega.
         * </p>
         */
        @JsonProperty("http_error")
        Integer httpError,

        /**
         * Número da tentativa atual de envio da notificação.
         * <p>
         * Contador que indica quantas vezes o sistema tentou entregar esta
         * notificação ao endpoint configurado.
         * </p>
         * <p>
         * A Pushin Pay geralmente realiza múltiplas tentativas em caso de falha,
         * seguindo uma política de retentativas com intervalos crescentes.
         * </p>
         */
        @JsonProperty("attempt")
        Integer attempt,

        /**
         * Indica se o processo de notificação foi concluído.
         * <p>
         * Quando true, significa que a notificação foi entregue com sucesso ou
         * que o sistema esgotou todas as tentativas configuradas e não tentará
         * novamente.
         * </p>
         * <p>
         * Quando false, indica que o processo de notificação ainda está em andamento
         * e novas tentativas podem ocorrer.
         * </p>
         */
        @JsonProperty("complete")
        Boolean complete,

        /**
         * Identificador da transação associada a esta notificação webhook.
         * <p>
         * Referência ao ID da transação Pushin Pay que gerou este evento de notificação.
         * </p>
         * <p>
         * Este campo pode ser nulo se a notificação não estiver relacionada a uma transação
         * específica, mas a outro tipo de recurso.
         * </p>
         */
        @JsonProperty("transaction_id")
        String transactionId,

        /**
         * Identificador da transferência associada a esta notificação webhook.
         * <p>
         * Referência ao ID da transferência Pushin Pay que gerou este evento de notificação.
         * </p>
         * <p>
         * Este campo pode ser nulo se a notificação não estiver relacionada a uma transferência
         * específica, mas a outro tipo de recurso.
         * </p>
         */
        @JsonProperty("transfer_id")
        String transferId,

        /**
         * Identificador da conta associada a esta notificação webhook.
         * <p>
         * Referência ao ID da conta Pushin Pay relacionada a este evento de notificação.
         * </p>
         * <p>
         * O formato típico é "acc_" seguido por uma sequência alfanumérica.
         * </p>
         */
        @JsonProperty("account_id")
        String accountId,

        /**
         * Data e hora de criação da notificação webhook no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando a notificação webhook foi criada no sistema Pushin Pay.
         * </p>
         */
        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        /**
         * Data e hora da última atualização da notificação webhook no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando a notificação webhook foi atualizada pela última vez, por exemplo,
         * após uma nova tentativa de envio.
         * </p>
         */
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt,

        /**
         * Data e hora de exclusão lógica da notificação webhook no formato ISO 8601 com fuso horário.
         * <p>
         * Quando presente, indica que esta notificação webhook foi marcada como excluída
         * no sistema Pushin Pay (exclusão lógica).
         * </p>
         * <p>
         * Este campo será nulo para notificações ativas no sistema.
         * </p>
         */
        @JsonProperty("deleted_at")
        OffsetDateTime deletedAt
) implements Serializable {
}