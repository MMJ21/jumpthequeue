#stage 1
FROM node:latest as node
WORKDIR /jumpthequeue
COPY . .
RUN set NODE_OPTIONS=--openssl-legacy-provider
RUN npm install --legacy-peer-deps 
RUN npm run build --prod
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/jumpthequeue /usr/share/nginx/html