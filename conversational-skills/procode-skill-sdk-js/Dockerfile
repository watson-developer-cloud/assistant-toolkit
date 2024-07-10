FROM node:20

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY package.json ./

RUN npm install --only=production

# Bundle app source
COPY . .

EXPOSE 4000

CMD [ "npm", "start" ]

