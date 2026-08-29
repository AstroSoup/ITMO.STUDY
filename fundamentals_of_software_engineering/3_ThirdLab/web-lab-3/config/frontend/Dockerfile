FROM nginx:latest

COPY config/nginx/nginx.conf /etc/nginx/nginx.conf
COPY frontend/vue-project/dist /usr/share/nginx/html
COPY config/nginx/certs /etc/nginx/certs

EXPOSE 80 443

ENTRYPOINT ["nginx", "-c", "/etc/nginx/nginx.conf"]
CMD ["-g", "daemon off;"]
