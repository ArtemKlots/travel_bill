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


## Telegram

To make bot to communicate with Telegram you should register your bot with @BotFather and set  `TELEGRAM_KEY` environment variable

`TELEGRAM_BOT_MENTION` - used for sending direct link to bot in public events

### Bot group policy

To let the bot to access all the group messages, you have to use the @BotFather bot to set the Group Privacy off.

By default, Group Privacy is enabled for bots. This setting has to be changed used the @BotFather bot >> Bot Settings >> Group Privacy >> Turn off
