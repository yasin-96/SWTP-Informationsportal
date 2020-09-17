FROM nginx:1.18-alpine
#copy site config
RUN su cat ./dist/vue-server.conf > /etc/nginx/conf.d/default.conf
RUN cat /etc/nginx/nginx.conf
RUN cat /etc/conf.d/default.conf
COPY ./dist/ /usr/share/nginx/html/info-portal/
RUN ls /usr/share/nginx/html/info-portal/
EXPOSE 80