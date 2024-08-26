# API PROMAD

Micro serviço de processos.

## Requisitos

Para que seja possível rodar essa aplicação, é necessário atender a alguns requisitos básicos:

- Java 17
- Maven 3.8+
- Postgres
- Docker (Opcional, apenas para execução via Docker)

## Instruções de Uso

Você pode rodar a aplicação de duas maneiras: **via Docker** ou **via Maven**.

### Rodar via Docker

Se você preferir usar o Docker, siga estes passos:

1. **Certifique-se de que o Docker está instalado e em execução.**
2. **Configure o banco de dados PostgreSQL** (verifique o arquivo `docker-compose.yml` para detalhes de configuração).
3. **Execute o comando abaixo para construir e iniciar a aplicação:**

    ```bash
    $ docker-compose up --build
    ```

   Isso irá construir a imagem Docker e iniciar os containers definidos no arquivo `docker-compose.yml`. A aplicação estará disponível em `http://localhost:8080`.

### Rodar via Maven

Se preferir rodar a aplicação diretamente via Maven, siga estes passos:

1. **Certifique-se de que o PostgreSQL está instalado e em execução localmente.**
2. **Configure o banco de dados PostgreSQL**:
   - **Verifique o arquivo `application.properties`** da aplicação para garantir que as credenciais e a URL do banco de dados estão corretas. As configurações que você deve usar são:

     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
     spring.datasource.username=postgres
     spring.datasource.password=123
     ```

   - **Assegure-se de que o banco de dados `postgres` está criado** e que as credenciais fornecidas têm acesso a ele.
3. **Clone o repositório da aplicação.**
4. **Abra o projeto na sua IDE.**
5. **Compile e execute o projeto utilizando Maven:**

    ```bash
    $ mvn spring-boot:run
    ```

   A aplicação será iniciada e estará disponível em `http://localhost:8080`.
   
## Endpoints

### Processos

- **Listar todos os processos**

  - **URL:** `http://localhost:8080/processos`
  - **Método HTTP:** GET

- **Criar um novo processo**

  - **URL:** `http://localhost:8080/processos`
  - **Método HTTP:** POST
  - **Exemplo de JSON:**
    ```json
    {
        "numeros": [
            1, 2, 3, 4, 5
        ]
    }
    ```

- **Obter detalhes de um processo por id**

  - **URL:** `http://localhost:8080/processos/{id}`
  - **Método HTTP:** GET

- **Deletar um processo**

  - **URL:** `http://localhost:8080/processos/{id}`
  - **Método HTTP:** DELETE

- **Atualizar um processo**

  - **URL:** `http://localhost:8080/processos/{id}`
  - **Método HTTP:** PUT

- **Adicionar réu a um processo**

  - **URL:** `http://localhost:8080/processos/{id}/reu`
  - **Método HTTP:** PUT
  - **Exemplo de JSON:**
    ```json
    {
        "numeros": [
            1, 2, 3, 4, 5
        ],
        "reu": {
            "nome": "Teste processo"
        }
    }
    ```

### Réus

- **Listar todos os réus**

  - **URL:** `http://localhost:8080/reu`
  - **Método HTTP:** GET

- **Obter detalhes de um réu por id**

  - **URL:** `http://localhost:8080/reu/{id}`
  - **Método HTTP:** GET

- **Deletar um réu**

  - **URL:** `http://localhost:8080/reu/{id}`
  - **Método HTTP:** DELETE

- **Atualizar um réu**

  - **URL:** `http://localhost:8080/reu/{id}`
  - **Método HTTP:** PUT
  - **Exemplo de JSON:**
    ```json
    {
    "nome": "Teste atualizando o reu"
    }    
    ```
