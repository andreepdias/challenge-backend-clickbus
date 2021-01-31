# Challenge Backend Clickbus

Desafio de um processo seletivo de 2019 da Clickbus, realizado para praticar Java e testes unitários. A solução proposta contou com 31 testes unitários.

### Resumo

O desafio consiste em um api REST bastante simples que manipula lugares (Place), onde é possível criar, editar, buscar por id, listar todos, listar todos filtrando por nome. Um Place é composto por *name*, *slang*, *city*, *state*, *createdAt* e *updatedAt*.

### Solução

A solução foi construída com Spring Boot e está disponível também no DockerHub andreepdias/challenge-backend-clickbus:

`docker pull andreepdias/challenge-backend-clickbus`

Para executar basta expor a porta 8080:

`docker run -p 8080:8080 andreepdias/challenge-backend-clickbus`

#### Endpoints

Os endpoints criados são os seguintes:

`GET http://localhost:8080/places`: retorna lista de places cadastradados;

`GET http://localhost:8080/places?name=`: retorna lista de places cadastradados filtrando por nome;

`GET http://localhost:8080/places/id`: retorna um place específico;

`POST http://localhost:8080/places`: cria um novo place a partir de um json com body *name*, *slang*, *city* e/ou *state*;

`POST http://localhost:8080/places/1`: atualiza um place específico;

#### Testes

Todas as camadas da aplicação foram testadas (domain, repository, controller, service e util) com JUnit5 e mockadas com Mockito, quando necessário. Nota-se que nem todos os testes precisavam ser realizados, já em que alguns foi testado apenas a IDE ou o próprio  framework (especialmente nos testes de repository). Entretanto, foram escritos mesmo assim para praticar a habilidade de testes.

Os 31 testes realizados se dividem em: domain (2), repository (8), controller (8), service (9), util (4).



