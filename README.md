# React Maps

## Tecnologias utilizadas:
[![Icons](https://skillicons.dev/icons?i=nodejs,react,postgres,express,sequelize,docker&theme=light)](https://skillicons.dev)

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

### Execução Manual (Sem Docker Compose)
Se preferir executar o projeto localmente, sem Docker Compose, siga os passos abaixo:

#### 1. Pré-requisitos:
- Node.js 20+
- PostgreSQL em execução local (ou em outro host)
- Variáveis de ambiente ou configuração do banco no arquivo [database.js](backend/src/config/database.js)

#### 2. Instalação das dependências
   ```bash
   echo "Instalando dependências do frontend..."
   npm install --prefix ./frontend

   echo "Instalando dependências do backend..."
   npm install --prefix ./backend

   echo "Instalação concluída."
   ```

#### 3. Execução do backend:
- A partir da raiz do projeto:
  ```bash
     cd backend
     npm start
  ```

#### 4. Execução do frontend:
- A partir da raiz do projeto:
  ```bash
     cd frontend
     npm start
  ```

---

## Contribuição

Fique à vontade para contribuir com este projeto. Você pode enviar pull requests para correções de bugs, melhorias e novas funcionalidades.

