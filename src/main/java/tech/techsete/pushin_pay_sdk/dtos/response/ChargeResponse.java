package tech.techsete.pushin_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.techsete.pushin_pay_sdk.enums.ChargeStatus;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO (Data Transfer Object) que representa a resposta de uma operação de cobrança na API Pushin Pay.
 * <p>
 * Este record encapsula todos os dados retornados pela API Pushin Pay ao criar ou consultar
 * uma cobrança. Inclui informações como identificador da cobrança, QR code para pagamento,
 * status atual, valor, informações de webhook e regras de divisão de pagamento.
 * </p>
 * <p>
 * Por ser um record Java, esta classe é imutável, o que garante que os dados da resposta
 * não sejam modificados acidentalmente após serem recebidos da API.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * ChargeResponse response = chargeService.createCharge(chargeRequest);
 * String qrCodeForPayment = response.qrCode();
 * ChargeStatus currentStatus = response.status();
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.enums.ChargeStatus
 * @see tech.techsete.pushin_pay_sdk.dtos.response.WebhookResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.response.SplitRuleResponse
 * @see tech.techsete.pushin_pay_sdk.dtos.request.ChargeRequest
 *
 * @author EdsonIsaac
 */
public record ChargeResponse(

        /**
         * Identificador único da cobrança.
         * <p>
         * Este ID é gerado pela API Pushin Pay e pode ser utilizado para
         * consultar ou referenciar a cobrança em operações futuras.
         * </p>
         */
        @JsonProperty("id")
        String id,

        /**
         * String contendo o QR code para pagamento da cobrança.
         * <p>
         * Este campo contém o código Pix copia e cola que pode ser utilizado
         * pelo pagador para efetuar o pagamento da cobrança.
         * </p>
         */
        @JsonProperty("qr_code")
        String qrCode,

        /**
         * Status atual da cobrança.
         * <p>
         * Indica o estado atual da cobrança no sistema Pushin Pay,
         * podendo ser CREATED (criada) ou PAID (paga).
         * </p>
         */
        @JsonProperty("status")
        ChargeStatus status,

        /**
         * Valor da cobrança em centavos.
         * <p>
         * Este campo representa o valor monetário da cobrança em centavos.
         * Por exemplo, para uma cobrança de R$ 100,00, o valor será 10000.
         * </p>
         */
        @JsonProperty("value")
        Long value,

        /**
         * URL para notificações webhook.
         * <p>
         * URL configurada para receber notificações sobre eventos relacionados
         * a esta cobrança, como confirmação de pagamento.
         * </p>
         */
        @JsonProperty("webhook_url")
        String webhookUrl,

        /**
         * Imagem do QR code em formato Base64.
         * <p>
         * Representação visual do QR code codificada em Base64, que pode ser
         * exibida diretamente em interfaces web ou aplicativos móveis.
         * </p>
         */
        @JsonProperty("qr_code_base64")
        String qrCodeBase64,

        /**
         * Informações detalhadas sobre notificações webhook.
         * <p>
         * Contém dados sobre tentativas de notificação, status HTTP e outros
         * detalhes relacionados às chamadas webhook.
         * </p>
         */
        @JsonProperty("webhook")
        WebhookResponse webhook,

        /**
         * Regras de divisão de pagamento aplicadas à cobrança.
         * <p>
         * Coleção com as regras de divisão (split) configuradas para esta cobrança,
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
         */
        @JsonProperty("end_to_end_id")
        String endToEndId,

        /**
         * Nome do pagador da cobrança.
         * <p>
         * Nome da pessoa física ou jurídica que efetuou o pagamento da cobrança.
         * Este campo só é preenchido após o pagamento ser confirmado.
         * </p>
         */
        @JsonProperty("payer_name")
        String payerName,

        /**
         * Documento de identificação do pagador.
         * <p>
         * CPF ou CNPJ do pagador da cobrança, sem pontuação.
         * Este campo só é preenchido após o pagamento ser confirmado.
         * </p>
         */
        @JsonProperty("payer_national_registration")
        String payerNationalRegistration
) implements Serializable { }