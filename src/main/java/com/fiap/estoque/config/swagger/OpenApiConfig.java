package com.fiap.estoque.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PÓS GRADUAÇÃO - FIAP 2025 - SERVIÇO DE ESTOQUE")
                        .version("1.0.0")
                        .description("Microsserviço responsável pelo controle e atualização do estoque de produtos. Gerencia a quantidade disponível de cada produto, processa baixas e reposições de estoque conforme os pedidos realizados e garante a consistência das informações de disponibilidade."));
    }
}