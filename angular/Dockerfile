#Stage 1: Build Node App
FROM node:16.3.0-alpine as node
WORKDIR /app
COPY . .
RUN set NODE_OPTIONS=--openssl-legacy-provider
RUN npm install --legacy-peer-deps 
RUN npm run build --prod

#Stage 2: Deploy in NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
COPY --from=node /app/dist/angular .
ENTRYPOINT ["nginx", "-g", "daemon off;"]