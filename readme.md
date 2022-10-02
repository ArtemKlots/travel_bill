[![Codeship Status for ArtemKlots/travel_bill](https://app.codeship.com/projects/2d56b6b0-bc7e-0137-f28c-7207021c806d/status?branch=master)](https://app.codeship.com/projects/365326) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/e1d86f35a09948449de97257412abe46)](https://app.codacy.com/manual/ArtemKlots/travel_bill?utm_source=github.com&utm_medium=referral&utm_content=ArtemKlots/travel_bill&utm_campaign=Badge_Grade_Dashboard)


# Setup environment

## Infrastructure

### Database

To configure database connection you should set up the following environment variables:
* `SPRING_DATASOURCE_URL` (e.g. jdbc:mysql://localhost:3306/travel_bill?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true)
* `SPRING_DATASOURCE_USERNAME`
* `SPRING_DATASOURCE_PASSWORD`


### Healthcheck & lifecycle
* `HEALTH_CHECK_URL` - healthcheck url for get request
* `HEALTH_CHECK2_URL` - healthcheck url for post request (optional)
* `LIFECYCLE_URL` - url for self awake

### Rollbar
* `ROLLBAR_ACCESS_KEY`
* `ROLLBAR_ENVIRONMENT`

### Auth 
* `JWT_SECRET`
* `LOGIN_URL` (e.g. http://your.domain:56789/login)


## Telegram

### Common
To make bot to communicate with Telegram you should register your bot with @BotFather and set  `TELEGRAM_KEY` environment variable

`TELEGRAM_BOT_MENTION` - used for sending direct link to bot in public events and for Telegram login

### Bot configuration

* `BOT_MODE` should be set as `POOLING` or `WEBHOOK`

#### Webhook
If `BOT_MODE` set as `WEBHOOK`, the following environment variables should be set:

* `telegram.external-url` (e.g. ngrok proxy to localhost: https://9b13-176-67-14-211.ngrok.io -> localhost:8090)
* `telegram.internal-url` (e.g. http://localhost:8090). It means that under the hood bot launch http proxy server on 8090 port. It will redirect telegram messages to TB API 

### Shortcut to copy all env vars

```
BOT_MODE=WEBHOOK
HEALTH_CHECK2_URL=
HEALTH_CHECK_URL=
JAWSDB_URL=
JWT_SECRET=
LIFECYCLE_URL=
LOGIN_URL=
ROLLBAR_ACCESS_KEY=
ROLLBAR_ENVIRONMENT=dev
SPRING_DATASOURCE_PASSWORD=
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/travelbill?reconnect\=true&useSSL\=false&serverTimezone\=UTC&allowPublicKeyRetrieval\=true
SPRING_DATASOURCE_USERNAME=
telegram.external-url=https://***.ngrok.io
telegram.internal-url=http://localhost:8090
TELEGRAM_BOT_MENTION=@travel_bill_bot
TELEGRAM_KEY=
TZ=Europe/Kiev
```

### Bot group policy

To let the bot to access all the group messages, you have to use the @BotFather bot to set the Group Privacy off.

By default, Group Privacy is enabled for bots. This setting has to be changed used the @BotFather bot >> Bot Settings >> Group Privacy >> Turn off

### Linking your domain to the bot
To enable the Telegram login you need to [set up](https://core.telegram.org/widgets/login#linking-your-domain-to-the-bot) your domain 