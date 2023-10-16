FROM node:16

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
COPY package.json ./

RUN npm install --only=production

# Bundle app source
COPY . .
# Copy .env-sample to .env
COPY .env-sample .env

EXPOSE 4000

CMD [ "npm", "start" ]

