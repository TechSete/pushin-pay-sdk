package tech.techsete.pushin_pay_sdk.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum que representa os possíveis estados de uma transação na API Pushin Pay.
 * <p>
 * Este enum define os diferentes estágios do ciclo de vida de uma transação,
 * desde sua criação até o recebimento do pagamento. É utilizado para acompanhar
 * e gerenciar o status atual de cada transação no sistema.
 * </p>
 * <p>
 * O status da transação é atualizado automaticamente pela API Pushin Pay quando
 * eventos relevantes ocorrem, como a confirmação de um pagamento Pix ou
 * processamento de outros métodos de pagamento.
 * </p>
 * <p>
 * Exemplo de uso:
 * </p>
 * <pre>
 * TransactionResponse transaction = transactionService.getTransaction(transactionId);
 * TransactionStatus status = transaction.status();
 * 
 * if (status == TransactionStatus.PAID) {
 *     // Processar transação paga
 * } else if (status == TransactionStatus.CREATED) {
 *     // Notificar cliente sobre transação pendente
 * }
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse
 *
 * @author EdsonIsaac
 */
public enum TransactionStatus {
    
    /**
     * Transação criada e pendente de pagamento.
     * <p>
     * Este status indica que a transação foi registrada com sucesso na API Pushin Pay
     * e está aguardando o pagamento pelo cliente. Dependendo do método de pagamento,
     * diferentes ações podem ser necessárias para completar o pagamento.
     * </p>
     */
    CREATED,
    
    /**
     * Transação paga com sucesso.
     * <p>
     * Este status indica que o pagamento da transação foi confirmado e processado.
     * Uma transação neste estado teve seu valor creditado na conta do recebedor
     * (ou distribuído conforme as regras de divisão, se aplicável).
     * </p>
     */
    PAID;

    /**
     * Converte uma string em um valor do enum TransactionStatus.
     * <p>
     * Este método é utilizado pelo Jackson durante a desserialização JSON para
     * converter valores de string recebidos da API em instâncias do enum TransactionStatus.
     * A comparação é case-insensitive, permitindo flexibilidade no formato dos valores.
     * </p>
     * <p>
     * Se o valor for nulo, retorna null. Se o valor não corresponder a nenhum
     * dos valores definidos no enum, lança uma exceção.
     * </p>
     *
     * @param value String contendo o nome do status (case-insensitive)
     * @return A instância do enum TransactionStatus correspondente ou null
     * @throws IllegalArgumentException se o valor não corresponder a nenhum status definido
     */
    @JsonCreator
    public static TransactionStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        for (TransactionStatus status : TransactionStatus.values()) {
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
     * converter instâncias do enum TransactionStatus em strings que serão enviadas
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
