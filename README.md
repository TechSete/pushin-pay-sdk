# PushinPay SDK

[![Maven Central](https://img.shields.io/maven-central/v/tech.techsete/pushin-pay-sdk.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/tech.techsete/pushin-pay-sdk)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**PushinPay SDK** é uma biblioteca Java que simplifica integrações de pagamentos usando o serviço PushinPay. Ele oferece recursos prontos para iniciar, validar e gerenciar transações financeiras com segurança e simplicidade.

---

## 📦 Instalação

Adicione a dependência ao seu projeto Maven:

```xml
<dependency>
  <groupId>tech.techsete</groupId>
  <artifactId>pushin-pay-sdk</artifactId>
  <version>1.0.4</version>
</dependency>
```

Ou com Gradle:

```groovy
implementation 'tech.techsete:pushin-pay-sdk:1.0.4'
```

---

## 🚀 Recursos

- Integração com API de pagamentos PushinPay
- Suporte a validação de dados com Spring Validation
- Baseado em WebFlux (reativo)
- Suporte a testes com JUnit 5 + Reactor Test

---

## ✅ Exemplos de uso

### 🔐 Verificar se uma conta existe (síncrono)

```java
Map<String, String> headers = Map.of("Authorization", "Bearer seu-token-aqui");

boolean exists = accountService.existsByAccountId(headers, "acc_1234567890");
System.out.println("Conta existe? " + exists);
```

### 🔐 Verificar se uma conta existe (assíncrono)

```java
accountService.existsByAccountIdAsync(headers, "acc_1234567890")
    .subscribe(exists -> System.out.println("Conta existe? " + exists));
```

---

### 💸 Criar uma cobrança (síncrono)

```java
ChargeRequest request = ChargeRequest.builder()
    .value(10000L) // R$ 100,00 em centavos
    .webhookUrl("https://exemplo.com/webhook")
    .build();

ChargeResponse response = chargeService.create(headers, request);
System.out.println("Cobrança criada: " + response);
```

### 💸 Criar uma cobrança (assíncrono)

```java
ChargeRequest request = ChargeRequest.builder()
    .value(10000L) // R$ 100,00 em centavos
    .webhookUrl("https://exemplo.com/webhook")
    .build();

chargeService.createAsync(headers, request)
    .subscribe(response -> System.out.println("Cobrança criada: " + response));
```

---

### 🔍 Consultar uma transação por ID (síncrono)

```java
TransactionResponse response = transactionService.retrieveByTransactionId(headers, "txn_abcdef123456");
System.out.println("Transação encontrada: " + response);
```

### 🔍 Consultar uma transação por ID (assíncrono)

```java
transactionService.retrieveByTransactionIdAsync(headers, "txn_abcdef123456")
    .subscribe(response -> System.out.println("Transação encontrada: " + response));
```

---

## 🛠️ Tecnologias utilizadas

- Java 17+
- Spring Boot 3
- WebFlux
- Lombok
- Maven

---

## 📝 Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).

---

## 👨‍💻 Autor

- **Edson Isaac** – [GitHub](https://github.com/edsonisaac) | [TechSete](https://github.com/TechSete)

---

## 🔗 Links úteis

- [Maven Central](https://central.sonatype.com/artifact/tech.techsete/pushin-pay-sdk)
- [Repositório GitHub](https://github.com/TechSete/pushin-pay-sdk)
