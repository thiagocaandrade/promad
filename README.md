# API PROMAD

Micro serviço de processos.

## Requisitos

Para que seja possível rodar essa aplicação é necessário atender alguns requisitos básicos.

- Java 17
- Maven 3.8+
- Postgres
- Docker

## Como usar via Docker (Opcional):

```
$ docker-compose up --build
```
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

