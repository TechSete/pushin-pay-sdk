package tech.techsete.pushin_pay_sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum que representa os possíveis estados de uma cobrança na API Pushin Pay.
 * <p>
 * Este enum define os diferentes estágios do ciclo de vida de uma cobrança,
 * desde sua criação até o recebimento do pagamento. É utilizado para acompanhar
 * e gerenciar o status atual de cada cobrança no sistema.
 * </p>
 * <p>
 * O status da cobrança é atualizado automaticamente pela API Pushin Pay quando
 * eventos relevantes ocorrem, como a confirmação de um pagamento Pix.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * ChargeResponse charge = chargeService.getCharge(chargeId);
 * ChargeStatus status = charge.status();
 * 
 * if (status == ChargeStatus.PAID) {
 *     // Processar cobrança paga
 * } else if (status == ChargeStatus.CREATED) {
 *     // Notificar cliente sobre cobrança pendente
 * }
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.ChargeResponse
 *
 * @author EdsonIsaac
 */
public enum ChargeStatus {
    
    /**
     * Cobrança criada e pendente de pagamento.
     * <p>
     * Este status indica que a cobrança foi registrada com sucesso na API Pushin Pay
     * e está aguardando o pagamento pelo cliente. Neste estado, o QR code Pix
     * está ativo e pode ser utilizado para efetuar o pagamento.
     * </p>
     */
    CREATED,
    
    /**
     * Cobrança paga com sucesso.
     * <p>
     * Este status indica que o pagamento da cobrança foi confirmado e processado.
     * Uma cobrança neste estado teve seu valor creditado na conta do recebedor
     * (ou distribuído conforme as regras de divisão, se aplicável).
     * </p>
     */
    PAID;

    /**
     * Converte uma string em um valor do enum ChargeStatus.
     * <p>
     * Este método é utilizado pelo Jackson durante a desserialização JSON para
     * converter valores de string recebidos da API em instâncias do enum ChargeStatus.
     * A comparação é case-insensitive, permitindo flexibilidade no formato dos valores.
     * </p>
     * <p>
     * Se o valor for nulo, retorna null. Se o valor não corresponder a nenhum
     * dos valores definidos no enum, lança uma exceção.
     * </p>
     *
     * @param value String contendo o nome do status (case-insensitive)
     * @return A instância do enum ChargeStatus correspondente ou null
     * @throws IllegalArgumentException se o valor não corresponder a nenhum status definido
     */
    @JsonCreator
    public static ChargeStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        for (ChargeStatus status : ChargeStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

    /**
     * Converte o valor do enum para uma string durante a serialização JSON.
     * <p>
     * Este método é utilizado pelo Jackson durante a serialização JSON para
     * converter instâncias do enum ChargeStatus em strings que serão enviadas
     * em requisições para a API ou apresentadas em respostas.
     * </p>
     *
     * @return Nome do enum como string (em maiúsculas)
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }
}