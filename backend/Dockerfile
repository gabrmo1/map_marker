FROM node:20-alpine3.18

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 5001

CMD sleep 10 && npm start
