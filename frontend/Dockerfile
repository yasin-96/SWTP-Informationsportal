FROM nginx:1.18-alpine
#copy site config

COPY ./dist/ /usr/share/nginx/html/info-portal/
RUN ls /usr/share/nginx/html/info-portal/

RUN sudo ln -s ./dist/vue-server.conf /etc/nginx/conf.d/default.conf
RUN cat /etc/nginx/nginx.conf
RUN cat /etc/conf.d/default.conf

EXPOSE 80