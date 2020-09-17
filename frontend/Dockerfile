FROM nginx:1.18-alpine

#copy site config
COPY ./dist/ /usr/share/nginx/html/info-portal/

#check copied files
RUN ls /usr/share/nginx/html/info-portal/

#loggin as root
RUN su

#create new file
RUN mkdir /etc/nginx/sites-available
RUN touch /etc/nginx/sites-available/info-portal
RUN cat /dist/vue-server.conf >  /etc/nginx/sites-available/info-portal

# delete old config file
RUN rm /etc/nginx/conf.d/default.conf

# create own config file link
RUN ln -s /etc/nginx/sites-available/info-portal /etc/nginx/sites-enabled/info-portal

#logout as root
RUN exit

EXPOSE 80