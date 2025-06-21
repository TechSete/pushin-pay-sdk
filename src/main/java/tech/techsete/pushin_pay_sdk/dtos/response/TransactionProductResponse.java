package tech.techsete.pushin_pay_sdk.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO (Data Transfer Object) que representa informações sobre um produto associado a uma transação.
 * <p>
 * Este record encapsula os dados de um produto vinculado a uma transação processada pela API Pushin Pay.
 * Atualmente contém apenas o identificador único do produto, mas pode ser expandido no futuro
 * para incluir mais informações como nome, descrição, valor unitário, quantidade, etc.
 * </p>
 * <p>
 * Este objeto geralmente é retornado como parte de uma coleção em {@link TransactionResponse},
 * permitindo o rastreamento dos produtos ou serviços incluídos em uma transação específica.
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
 * Collection&lt;TransactionProductResponse&gt; products = transaction.transactionProduct();
 * 
 * for (TransactionProductResponse product : products) {
 *     String productId = product.id();
 *     // Processar informações do produto
 * }
 * </pre>
 *
 * @see tech.techsete.pushin_pay_sdk.dtos.response.TransactionResponse
 *
 * @author EdsonIsaac
 */
public record TransactionProductResponse(

        /**
         * Identificador único do produto associado à transação.
         * <p>
         * Este ID referencia um produto específico que faz parte da transação.
         * Pode ser utilizado para consultar informações detalhadas do produto
         * em outros sistemas ou na própria API Pushin Pay, caso suporte tal operação.
         * </p>
         */
        @JsonProperty("id")
        String id
) implements Serializable { }