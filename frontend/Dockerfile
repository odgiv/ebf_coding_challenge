FROM node:latest

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY ./frontend/package*.json ./

RUN npm install

WORKDIR /srv/app/frontend

COPY . .

EXPOSE 3000
