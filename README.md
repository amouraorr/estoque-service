# estoque-service

# Serviço de Estoque - Backend

## Introdução

Este microsserviço faz parte de um sistema modular de gerenciamento de pedidos, focado na gestão de estoque. 
Ele é responsável pelo controle e atualização da quantidade disponível de cada produto, processando baixas e 
reposições conforme os pedidos realizados e garantindo a consistência das informações de disponibilidade.

## Objetivo do Projeto

O objetivo principal deste microsserviço é fornecer uma API robusta para gerenciar o estoque de produtos, 
permitindo consultar, atualizar e baixar a quantidade disponível, garantindo a integridade e consistência dos dados de estoque. 

## Requisitos do Sistema

Para executar este microsserviço, você precisará dos seguintes requisitos:

- **Sistema Operacional**: Windows, macOS ou Linux  
- **Memória RAM**: Pelo menos 4 GB recomendados  
- **Espaço em Disco**: Pelo menos 500 MB de espaço livre  
- **Software**:  
    - Docker e Docker Compose  
    - Java JDK 11 ou superior  
    - Maven 3.6 ou superior  
    - PostgreSQL  
    - Git  

## Estrutura do Projeto

A estrutura do projeto está organizada da seguinte forma:
```plaintext
estoque-service/
│
├── src/
│ └── main/
│   ├── java/
│   │ └── com.fiap.estoque
│   │   ├── config/ : Configurações de segurança e Swagger.
│   │   ├── controller/ : Controladores REST para endpoints de estoque.
│   │   ├── domain/ : Entidades de domínio do estoque.
│   │   ├── dto/ : Objetos de transferência de dados (DTOs) para requisições e respostas.
│   │   ├── entity/ : Entidades JPA para persistência.
│   │   ├── exception/ : Tratamento global de exceções.
│   │   ├── gateway/ : Implementação da camada de acesso a dados.
│   │   ├── mapper/ : Mapeamento entre entidades, domínios e DTOs.
│   │   ├── repository/ : Repositórios JPA.
│   │   ├── usecase/ : Serviços de caso de uso para regras de negócio.
│   │   └── EstoqueServiceApplication.java : Classe principal da aplicação.
│   └── resources/
│       └── application.properties : Configurações da aplicação.
├── pom.xml : Arquivo de configuração do Maven.
├── Dockerfile : Arquivo para construção da imagem Docker.
├── docker-compose.yml : Arquivo para orquestração de contêineres.
└── README.md : Documentação do projeto.
```

## Segurança

A segurança do microsserviço é configurada com Spring Security.

## Visão Geral do Projeto

Este microsserviço é desenvolvido com Spring Boot e segue uma arquitetura limpa simplificada, separando claramente as responsabilidades entre domínio, 
persistência, casos de uso e interface.

## Arquitetura

A arquitetura segue o padrão MVC e princípios da Arquitetura Limpa, com camadas bem definidas:

- **Domain**: Representa as entidades de negócio (Estoque).
- **UseCase**: Serviços que implementam regras de negócio e casos de uso.
- **Gateway**: Interface e implementação para acesso a dados.
- **Controller**: Exposição dos endpoints REST para interação externa.
- **Mapper**: Conversão entre entidades JPA, domínios e DTOs.

## Princípios de Design e Padrões de Projeto

### Princípios de Design

1. **Single Responsibility Principle (SRP)**:
    - Cada classe tem uma única responsabilidade, como o serviço de consulta ou o controlador REST.

2. **Open/Closed Principle (OCP)**:
    - As classes são abertas para extensão e fechadas para modificação, facilitando manutenção e evolução.

### Padrões de Projeto

1. **MVC (Model-View-Controller)**:
    - Controladores REST atuam como controladores.
    - Domínio representa o modelo.
    - Respostas JSON são a "view".

2. **Gateway Pattern**:
    - Abstrai o acesso a dados, facilitando troca da implementação sem impactar o domínio.

3. **Mapper Pattern**:
    - Facilita a conversão entre diferentes camadas do sistema.

## Interação entre as Partes do Sistema

1. **Cliente**: Envia requisições HTTP para consultar, atualizar ou baixar estoque.
2. **Controller**: Recebe as requisições e delega para os casos de uso.
3. **UseCase**: Executa regras de negócio, validações e chama o gateway para persistência.
4. **Gateway**: Interage com o banco de dados via JPA.
5. **Banco de Dados**: Armazena os dados do estoque.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento Java.
- **Spring Security**: Segurança da aplicação.
- **Spring Data JPA**: Persistência com banco relacional.
- **PostgreSQL**: Banco de dados relacional.
- **MapStruct**: Mapeamento entre objetos.
- **Lombok**: Redução de boilerplate.
- **Swagger (Springdoc OpenAPI)**: Documentação da API.
- **Docker e Docker Compose**: Containerização e orquestração.

## Pré-requisitos

Antes de executar o microsserviço, certifique-se de ter instalado:

- Docker e Docker Compose
- Java JDK 11 ou superior
- Maven 3.6 ou superior
- PostgreSQL rodando localmente ou via container

### Passos para Executar o Docker Compose

1. Certifique-se de que o Docker e o Docker Compose estejam instalados e rodando na sua máquina.
2. No terminal, navegue até o diretório onde está localizado o arquivo `docker-compose.yml`.
3. Execute o seguinte comando para iniciar os contêineres:
   ```bash
   docker compose up
   ```
4. A aplicação estará disponível em `http://localhost:8086` e o Swagger em `http://localhost:8086/swagger-ui/index.html#/`.
5. O banco de dados PostgreSQL estará rodando em `http://localhost:5432`.
6. A ferramenta Adminer estará disponível para visualização do banco de dados no endereço `http://localhost:8088`.

### Passos para Conectar no Banco de Dados com o Adminer

1. Acesse o endereço `http://localhost:8088`.
2. Em Sistema, escolha PostgreSQL.
3. Em Servidor, preencha o nome do serviço do Postgres do Docker Compose (postgres).
4. Em Usuário, preencha postgres.
5. Em Senha, preencha postgres.
6. Em Base de dados, preencha com postgres.
7. Clique em Entrar.


## Endpoints Principais

- `GET /estoques/{sku}` - Consultar estoque por SKU
- `PUT /estoques` - Atualizar estoque
- `POST /estoques/{sku}/baixa?quantidade={quantidade}` - Baixar estoque para SKU

## Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nome-da-feature`).
3. Faça commit das suas alterações (`git commit -m 'Descrição da feature'`).
4. Envie para o repositório remoto (`git push origin feature/nome-da-feature`).
5. Abra um Pull Request.

## Licença

Este projeto é privado ou não possui licença específica.

## Referências e Recursos

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Swagger OpenAPI Documentation](https://springdoc.org/)

## Conclusão

Este microsserviço de estoque exemplifica a aplicação de arquitetura limpa e boas práticas no desenvolvimento de microsserviços, garantindo modularidade, escalabilidade e facilidade de manutenção dentro do sistema de gerenciamento de pedidos.
