🚛 API Gerenciador de Oficina Mecânica

Um sistema de back-end (API RESTful) desenvolvido para gerenciar o fluxo de trabalho de uma oficina mecânica de caminhões. O sistema permite o controle de clientes, veículos, equipe técnica (mecânicos/eletricistas) e o ciclo de vida completo de Ordens de Serviço (Entrada, Lançamento de Peças/Serviços e Finalização).

🚀 Tecnologias Utilizadas

Este projeto foi construído com as seguintes tecnologias:

Java 17

Spring Boot (Web, Data JPA, Validation)

<<<<<<< HEAD
PostgreSQL (Banco de dados relacional)
=======
MySQL (Banco de dados relacional)
>>>>>>> 1bc28070f1389c1461170931e3a598bb0f43d48d

Lombok (Redução de código boilerplate)

Maven (Gerenciador de dependências)

⚙️ Funcionalidades (Domínio de Negócio)

Gestão de Clientes: Cadastro de proprietários e transportadoras (CPF/CNPJ).

Gestão de Frota (Veículos): Cadastro de caminhões atrelados a um cliente específico (Validação por Placa única).

Gestão de Equipe: Registro de funcionários (ex: Mecânico Diesel, Eletricista Automotivo).

Ordens de Serviço (OS):

Abertura de OS atrelada a um veículo, registrando a data e hora de entrada e o defeito relatado.

Inclusão de peças e mão de obra à OS, vinculando o funcionário executor a cada item para controle.

Cálculo automático do valor total da OS a cada novo item adicionado.

Finalização da OS, registrando a data/hora de saída e travando a adição de novos itens.

🛡️ Arquitetura e Padrões

Padrão MVC/Camadas: Dividido em domain (entidades), repository (banco de dados), service (regras de negócio) e controller (endpoints REST).

Tratamento Global de Exceções: Utilização de @RestControllerAdvice para interceptar erros e retornar mensagens JSON limpas e amigáveis ao invés de stack traces.

Validação de Dados: Uso do Spring Validation (@NotBlank, @NotNull) para garantir a integridade dos dados antes da persistência.

CORS Configurado: API pronta para ser consumida por aplicações Web (React/Angular) e Mobile (React Native/Expo).

🛠️ Como executar o projeto localmente

Pré-requisitos

Java Development Kit (JDK) 17 ou superior instalado.

<<<<<<< HEAD
Servidor PostgreSQL rodando localmente (porta padrão 5432).
=======
Servidor MySQL rodando localmente (porta padrão 3306).
>>>>>>> 1bc28070f1389c1461170931e3a598bb0f43d48d

Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code).

Passo a passo

Clone este repositório:

git clone https://github.com/seu-usuario/gerenciador-oficina.git


<<<<<<< HEAD
Crie o banco de dados no PostgreSQL:
Abra o pgAdmin (Query Tool) ou o psql e execute:

CREATE DATABASE oficina;


Configure as credenciais do banco:
Navegue até o arquivo src/main/resources/application.properties e ajuste usuário e senha do seu PostgreSQL
(ou defina as variáveis de ambiente DB_URL, DB_USER e DB_PASSWORD):

spring.datasource.url=jdbc:postgresql://localhost:5432/oficina
spring.datasource.username=postgres
=======
Crie o banco de dados no MySQL:
Abra o seu terminal MySQL ou Workbench e execute:

CREATE DATABASE oficina_db;


Configure as credenciais do banco:
Navegue até o arquivo src/main/resources/application.properties e altere a senha para a senha do seu usuário root local:

spring.datasource.url=jdbc:mysql://localhost:3306/oficina_db?serverTimezone=UTC
spring.datasource.username=root
>>>>>>> 1bc28070f1389c1461170931e3a598bb0f43d48d
spring.datasource.password=SUA_SENHA_AQUI


Inicie a aplicação:
Rode a classe DemoApplication.java na sua IDE ou use o Maven no terminal:

./mvnw spring-boot:run


A aplicação estará rodando em http://localhost:8080. O Hibernate criará as tabelas no banco automaticamente.

🔗 Endpoints Principais

Abaixo estão as principais rotas da API:

Clientes

POST /api/clientes - Cadastra um cliente.

GET /api/clientes - Lista todos os clientes.

Veículos

POST /api/veiculos/{clienteId} - Cadastra um veículo atrelando-o ao ID de um cliente.

GET /api/veiculos - Lista todos os veículos.

Funcionários

POST /api/funcionarios - Cadastra um funcionário (mecânico/eletricista).

Ordens de Serviço

POST /api/ordens-servico - Abre uma nova OS.

POST /api/ordens-servico/{id}/itens - Adiciona uma peça ou serviço à OS (requer funcionarioId).

PUT /api/ordens-servico/{id}/finalizar - Finaliza a OS, registrando a saída.

GET /api/ordens-servico - Lista o histórico de Ordens de Serviço.

Desenvolvido como back-end para um sistema de gestão de oficinas. Próxima etapa: Construção da interface Mobile em React Native.
