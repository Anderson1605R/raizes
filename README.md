# API Raízes do Nordeste 🌵

Projeto de Back-End desenvolvido como estudo de caso para a disciplina de Projeto Multidisciplinar (Trilha Back-End). A API atende à rede de lanchonetes "Raízes do Nordeste", oferecendo suporte à multicanalidade (App, Totem, Balcão), controle de estoque descentralizado por unidades, mock de pagamentos externos, e proteção de dados com base na LGPD.

## 🛠️ Tecnologias e Requisitos

Para rodar este projeto, você precisará das seguintes ferramentas instaladas na sua máquina:
* **Java 17+**
* **Spring Boot 3+**
* **PostgreSQL**
* **Maven**

## ⚙️ Configuração das Variáveis de Ambiente e Banco de Dados

O projeto está configurado para ler as credenciais do banco de dados diretamente do arquivo `src/main/resources/application.properties`. Certifique-se de que as configurações correspondem ao seu ambiente local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/raizesdb
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```
*Nota: Crie um banco de dados vazio chamado `raizesdb` no seu PostgreSQL (como o pgAdmin) antes de rodar o projeto.*

## 🗄️ Criação das Tabelas e Seeds (Dados Iniciais)

* **Tabelas:** O projeto utiliza o Hibernate (`spring.jpa.hibernate.ddl-auto=update`). Ao iniciar a aplicação, as tabelas serão geradas automaticamente no banco `raizesdb`.
* **Seeds (Carga inicial):** O projeto conta com um arquivo `data.sql` configurado na pasta `src/main/resources`. O Spring Boot executará este script automaticamente na inicialização para popular o banco de dados com Unidades, Produtos, Estoques e um Usuário inicial para a realização dos testes.

## 🚀 Como Iniciar a API

1. Clone este repositório:
   ```bash
   git clone https://github.com/Anderson1605R/raizes.git
   ```
   
2. **⚠️ ATENÇÃO - PASSO IMPORTANTE:** O projeto Spring Boot está localizado dentro de uma subpasta também chamada `raizes`. Você precisa entrar nela para encontrar o arquivo `pom.xml` e executar a API:
   ```bash
   cd raizes
   ```
3. Instale as dependências e execute o projeto utilizando o Maven Wrapper que já vem incluso na pasta:
   * **No Linux/Mac:** `./mvnw spring-boot:run`
   * **No Windows:** `.\mvnw.cmd spring-boot:run`
4. A API estará rodando localmente na porta padrão: `http://localhost:8080`.

## 📄 Documentação da API (Swagger/OpenAPI)

A documentação visual e interativa de todos os endpoints e contratos da API foi gerada automaticamente. Com a aplicação rodando, acesse o link abaixo no seu navegador:

👉 [Acessar Swagger UI](http://localhost:8080/swagger-ui/index.html)

## 🧪 Como Executar os Testes (Postman)

A coleção completa com os 10 cenários de testes exigidos (6 positivos e 4 negativos) está disponível na raiz deste repositório no arquivo `Testes Projeto Final.postman_collection.json`.

**Passo a passo para validação:**
1. Abra o Postman e vá em `Import` para importar o arquivo `.json` da coleção.
2. **Ordem de Execução Sugerida:**
   * **1º Passo:** Execute a requisição `T03 - Login com Sucesso` utilizando as credenciais cadastradas pelo seed. 
   
     **🔐 Atenção ao Body do Login (LGPD):** O banco de dados armazena a senha em formato de Hash (BCrypt) para proteção de dados. No campo *Body* (raw/JSON) da requisição no Postman, você deve enviar o e-mail e a senha em texto limpo para que o Spring Security consiga combinar e validar o Hash. Utilize exatamente o JSON abaixo:
     ```json
     {
       "email": "cliente@exemplo.com",
       "senha": "123456"
     }
     ```
   * **2º Passo:** Copie o `accessToken` gerado na resposta com status 200 OK.
   * **3º Passo:** Para todas as outras requisições da coleção (como Criar Pedido ou Consultar Cardápio), vá até a aba **Authorization**, selecione o tipo **Bearer Token** e cole o token copiado.
3. **Validação do Fluxo Crítico:** O cenário `T01` realiza a criação do pedido garantindo a exigência de Multicanalidade (com o envio de `"canalPedido": "APP"`), faz a validação de estoque, aciona o Mock de Pagamento aprovado (`T04`) e retorna o pedido criado.
4. **Tratamento de Erros:** Estão inclusos cenários negativos tratando indisponibilidade de estoque (`T09` - Status 409) e falha no mock de pagamento (`T08` - Status 400), todos retornando um JSON padronizado de erro.
