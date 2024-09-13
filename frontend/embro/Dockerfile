FROM node:18.15.0-alpine

COPY build /embro/build

EXPOSE 3000

ENTRYPOINT ["npx", "serve", "-s", "embro/build"]