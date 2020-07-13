FROM nginx:1.18-alpine
RUN ls -lsa ./dist/
COPY ./dist/** /usr/share/nginx/html/info-portal/
RUN ls -lsa /usr/share/nginx/html/info-portal/
EXPOSE 80
