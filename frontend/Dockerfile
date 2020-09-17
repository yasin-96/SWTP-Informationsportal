FROM nginx:1.18-alpine

#copy site config
COPY ./dist/ /usr/share/nginx/html/info-portal/

#check copied files
RUN ls /usr/share/nginx/html/info-portal/

#loggin as root
RUN su

#create folder
RUN mkdir /etc/nginx/sites-available
RUN mkdir /etc/nginx/sites-enabled

#create new file
RUN touch /etc/nginx/sites-available/info-portal
RUN cat /usr/share/nginx/html/info-portal/vue-server.conf >  /etc/nginx/sites-available/info-portal

#check syntax 
RUN nginx -t

# delete old config file
#RUN rm /etc/nginx/conf.d/default.conf

# create own config file link
RUN ln -s /etc/nginx/sites-available/info-portal /etc/nginx/sites-enabled/info-portal

RUN systemctl restart nginx

#logout as root
RUN exit

EXPOSE 80