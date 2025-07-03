FROM node:20-alpine3.18

RUN apk add --no-cache bash curl

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 5001

CMD ["/wait-for-it.sh", "db:5432", "--", "npm", "start"]