package tech.techsete.pushin_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import tech.techsete.pushin_pay_sdk.serializers.LocalDateTimeDeserializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * DTO (Data Transfer Object) que representa os detalhes específicos de uma transação Pix.
 * <p>
 * Este record encapsula as informações detalhadas sobre uma transação Pix retornadas
 * pela API Pushin Pay. Inclui dados como identificador único, data de expiração,
 * código EMV (QR code Pix padrão) e informações temporais da transação.
 * </p>
 * <p>
 * Este objeto geralmente é retornado como parte de uma {@link TransactionResponse},
 * fornecendo informações específicas do Pix que não estão presentes em outros tipos
 * de transação.
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
 * PixDetailsResponse pixDetails = transaction.pixDetails();
 * String emvCode = pixDetails.emv();
 * LocalDateTime expirationDate = pixDetails.expirationDate();
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse
 * @author EdsonIsaac
 */
public record PixDetailsResponse(

        /**
         * Identificador único dos detalhes do Pix.
         * <p>
         * Este ID é gerado pela API Pushin Pay e identifica unicamente esta
         * informação de detalhes do Pix no sistema.
         * </p>
         */
        @JsonProperty("id")
        String id,

        /**
         * Data e hora de expiração do QR code Pix.
         * <p>
         * Após esta data, o QR code Pix não poderá mais ser utilizado para pagamento.
         * </p>
         */
        @JsonProperty("expiration_date")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime expirationDate,

        /**
         * Código EMV (Europay, Mastercard e Visa) do Pix.
         * <p>
         * Este campo contém o código EMV que forma o QR code Pix dinâmico.
         * É o formato padronizado pelo Banco Central do Brasil para QR codes Pix.
         * </p>
         * <p>
         * Diferente do QR code no formato copia e cola, este código é usado para
         * gerar a imagem do QR code que pode ser escaneada por aplicativos bancários.
         * </p>
         */
        @JsonProperty("emv")
        String emv,

        /**
         * Data e hora de criação do registro no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando os detalhes do Pix foram criados no sistema Pushin Pay.
         * </p>
         */
        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        /**
         * Data e hora da última atualização do registro no formato ISO 8601 com fuso horário.
         * <p>
         * Indica quando os detalhes do Pix foram atualizados pela última vez no sistema Pushin Pay.
         * </p>
         */
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt
) implements Serializable { }