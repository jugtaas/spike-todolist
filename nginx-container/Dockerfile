FROM smebberson/alpine-nginx

COPY default.conf /etc/nginx/conf.d/

COPY ../src/main/webapp/app.js /usr/html/
COPY ../src/main/webapp/styles.css /usr/html/
COPY ../src/main/webapp/index.html /usr/html/

RUN ln -sf /dev/stdout /var/log/nginx/access.log && \
    ln -sf /dev/stderr /var/log/nginx/error.log