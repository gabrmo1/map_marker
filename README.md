# React Maps

## Tecnologias utilizadas:
[![Icons](https://skillicons.dev/icons?i=react,javascript,java,spring,postgres,docker&theme=light)](https://skillicons.dev)

## Descrição
O intuito deste projeto é criar marcadores em um mapa. Os marcadores podem ser inseridos a partir de latitude/longitude e/ou cliques no mapa.
Os marcadores podem conter descrição, templates de html com fotos, etc. Eles também podem ser criados, editados e excluídos.

---

## Instruções de uso
Ao acessar a página da aplicação, verá um **formulário à esquerda** e um **mapa à direita**. Ao preenchar os campos do formulário,
criará um marcador personalizado no mapa conforme os dados que informou. O mapa é interativo, pode **clicar para
definir as propriedades de latitude e longitude** do formulário automaticamente, bem como pode **arrastar os marcadores não salvos** para alterar
a posição deles.

Os marcadores salvos são exibidos em vermelho, enquanto que os marcadores que ainda não foram salvos são exibidos na cor azul.

Pode mover o mapa "puxando-o" com o mouse, e pode aumentar/reduzir o zoom através do scroll.

---

## Configuração e Execução

### Com Docker Compose

Se você tiver o Docker Compose instalado, siga os passos abaixo para executar o projeto:

1. Execute o seguinte comando para iniciar os containers do backend, frontend e banco de dados:
   ```bash
   docker-compose up
   ```
2. Acesse a aplicação pelo seu navegador, que estará acessível em http://localhost:3000.

---

### Sem Docker Compose (Execução Manual)
Se preferir executar o projeto localmente, sem Docker Compose, siga os passos abaixo:

#### 1. Pré-requisitos:
- **NodeJs 20** (ou compatível)
- **Jdk 21** (ou compatível)
- **PostgreSQL** em execução local (ou em outro host)
- **Configuração do banco no arquivo** [application.yml](backend/src/main/resources/application.yml)
    - **Ou via enviroment variable**, definindo *SPRING_DATASOURCE_URL*, *SPRING_DATASOURCE_USERNAME*, *SPRING_DATASOURCE_PASSWORD*

#### 2. Instalação das dependências
   ```bash
   cd frontend; npm install; cd ..
   ```

#### 3. Execução do backend:
- A partir da raiz do projeto:

Faça build do projeto com:
   ```bash
   cd backend; ./gradlew bootJar --no-daemon -x test; cd ..
   ```
Execute a aplicação com:
   ```bash
   java -jar .\backend\build\libs\mapmarker-backend-0.0.1-SNAPSHOT.jar
   ```

#### 4. Execução do frontend:
- A partir da raiz do projeto:
   ```bash
   cd frontend; npm start;
   ```

---

## Contribuição

Fique à vontade para contribuir com este projeto. Você pode enviar pull requests para correções de bugs, melhorias e novas funcionalidades.

