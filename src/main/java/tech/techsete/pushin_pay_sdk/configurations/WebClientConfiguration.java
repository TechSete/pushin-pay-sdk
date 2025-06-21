package tech.techsete.pushin_pay_sdk.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuração do cliente HTTP reativo para o SDK Pushin Pay.
 * <p>
 * Esta classe fornece a configuração necessária para a comunicação com a API da Pushin Pay,
 * criando e configurando uma instância de {@link WebClient} do Spring WebFlux.
 * </p>
 *
 * O WebClient é configurado com:
 * <ul>
 *   <li>URL base da API Pushin Pay</li>
 *   <li>Cabeçalhos HTTP padrão para comunicação JSON</li>
 * </ul>
 *
 * <p>
 * Este componente é fundamental para os serviços do SDK que precisam se comunicar
 * com os endpoints da API Pushin Pay, fornecendo uma interface reativa para
 * requisições HTTP.
 * </p>
 */
@Configuration
public class WebClientConfiguration {

    /**
     * Cria e configura um {@link WebClient} para comunicação com a API Pushin Pay.
     * <p>
     * Este bean cria uma instância de WebClient configurada especificamente para
     * interagir com a API da Pushin Pay. A configuração inclui:
     * </p>
     * <ul>
     *   <li>URL base da API: https://api.pushinpay.com.br</li>
     *   <li>Cabeçalho Accept: application/json</li>
     *   <li>Cabeçalho Content-Type: application/json</li>
     * </ul>
     * <p>
     * O WebClient resultante é utilizado pelos serviços do SDK para realizar
     * requisições HTTP de forma reativa à API da Pushin Pay.
     * </p>
     */
    @Bean(name = "pushinPayWebClient")
    public WebClient pushinPayWebClient() {

        return WebClient.builder()
                .baseUrl("https://api.pushinpay.com.br")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
