FROM nginx:1.18-alpine
#copy site config
RUN su
RUN touch /etc/nginx/config.d/default.conf
RUN cat ./dist/vue-server.conf > /etc/nginx/config.d/default.conf
RUN exit
RUN cat /etc/nginx/nginx.conf
RUN cat /etc/config.d/default.conf
COPY ./dist/ /usr/share/nginx/html/info-portal/
RUN ls /usr/share/nginx/html/info-portal/
EXPOSE 80