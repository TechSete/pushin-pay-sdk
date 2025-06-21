package tech.techsete.pushin_pay_sdk.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializador personalizado para converter strings de data e hora em objetos LocalDateTime.
 * <p>
 * Esta classe estende {@link JsonDeserializer} do Jackson para processar campos de data e hora
 * em um formato específico utilizado pela API Pushin Pay. É utilizada principalmente através
 * da anotação {@code @JsonDeserialize} em DTOs que recebem datas da API.
 * </p>
 * <p>
 * O formato padrão aceito por este deserializador é "yyyy-MM-dd HH:mm:ss.SSS", que representa
 * uma data e hora completa incluindo milissegundos. Exemplo: "2023-10-15 14:30:25.789".
 * </p>
 *
 * Exemplo: {@code @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)}
 *
 * @see com.fasterxml.jackson.databind.annotation.JsonDeserialize
 * @see java.time.LocalDateTime
 * @see tech.techsete.pushin_pay_sdk.dtos.response.PixDetailsResponse
 *
 * @author EdsonIsaac
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    /**
     * Formatador que define o padrão de data e hora esperado.
     * <p>
     * Este formatador está configurado para o padrão "yyyy-MM-dd HH:mm:ss.SSS",
     * que representa datas no formato: ano (4 dígitos), mês (2 dígitos), dia (2 dígitos),
     * hora (24h), minutos, segundos e milissegundos.
     * </p>
     * <p>
     * Exemplo: "2023-10-15 14:30:25.789" para 15 de outubro de 2023, 14:30:25 e 789 milissegundos.
     * </p>
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Realiza a conversão de uma string no formato configurado para um objeto LocalDateTime.
     * <p>
     * Este método é chamado automaticamente pelo Jackson durante a desserialização de JSON
     * quando um campo está anotado com {@code @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)}.
     * </p>
     * <p>
     * O método faz a remoção de espaços em branco extras (trim) antes de fazer o parse da string,
     * o que aumenta a robustez do processo em casos onde a API possa retornar strings com espaços adicionais.
     * </p>
     *
     * @param p    O parser JSON que contém a string de data/hora a ser convertida
     * @param ctxt O contexto de desserialização
     * @return Um objeto LocalDateTime representando a data e hora da string parseada
     * @throws IOException Se ocorrer algum erro durante a leitura do JSON ou se a string
     *                     não estiver no formato esperado
     */
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return LocalDateTime.parse(p.getText().trim(), FORMATTER);
    }
}