# React Maps

## Backend

O backend foi desenvolvido utilizando NodeJS e gerenciado pelo npm.

## Frontend

O frontend foi desenvolvido utilizando React e gerenciado pelo npm.

## Configuração e Execução

### Com Docker Compose

Se você tiver o Docker Compose instalado, siga os passos abaixo para executar o projeto:

1. Certifique-se de que o Docker Compose esteja instalado no seu sistema.
2. Abra um terminal e navegue até o diretório raiz do projeto.
3. instale as dependências do backend e frontend:
   ```bash
      cd frontend
      npm install
      
      cd ../backend
      npm install
      
      cd .. 
   ```
4. Execute o seguinte comando para iniciar os containers do backend, frontend e banco de dados:
   ```bash
      docker-compose up
   ```
5. Acesse a aplicação pelo seu navegador, que estará acessível em http://localhost:3000.

### Sem Docker compose 

Se você preferir executar o projeto sem o Docker Compose, siga os passos abaixo:

1. Abra um terminal e navegue até o diretório raiz do projeto.
2. instale as dependências do backend e frontend:
   ```bash
      cd frontend
      npm install
      
      cd ../backend
      npm install
      
      cd ..
   ```
3. vá até o arquivo de configuração [database.js](backend/src/config/database.js) e altere as propriedades para as do banco de dados da sua preferência.
4. Inicie o serviço backend:
   - No diretório raiz do projeto, execute:
   ```bash
      cd backend
      npm start
   ```
   - Certifique-se que a mensagem ```Marker table synchronized with the model``` apareceu na console para garantir que a conexão entre o backend e o banco de dados foi estabelecida.
5. Inicie o frontend:
   - No diretório raiz do projeto, execute:
   ```bash
      cd frontend
      npm start
   ```
## Contribuição

Fique à vontade para contribuir com este projeto. Você pode enviar pull requests para correções de bugs, melhorias e novas funcionalidades.

