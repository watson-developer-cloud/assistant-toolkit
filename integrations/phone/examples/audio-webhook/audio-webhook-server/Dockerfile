FROM node:20 as builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build # Assuming you have a "build" script in your package.json

FROM node:20
WORKDIR /app
COPY --from=builder /app/dist ./dist
COPY package*.json ./
COPY .env ./
RUN npm install --production
EXPOSE 3000
CMD ["node", "dist/index.js"]

