FROM nginx:1.18-alpine
COPY ./dist/vue-server.config  /etc/nginx/config.d/
RUN cat /etc/nginx/nginx.conf
RUN  cat "include conf.d/vue-server.config" >> /etc/nginx/nginx.conf
COPY ./dist/ /usr/share/nginx/html/info-portal/
RUN ls /usr/share/nginx/html/info-portal/
EXPOSE 80
