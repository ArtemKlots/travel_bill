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


## Telegram

To make bot to communicate with Telegram you should register your bot with @BotFather and set  `TELEGRAM_KEY` environment variable

`TELEGRAM_BOT_MENTION` - used for sending direct link to bot in public events

### Bot group policy

To let the bot to access all the group messages, you have to use the @BotFather bot to set the Group Privacy off.

By default, Group Privacy is enabled for bots. This setting has to be changed used the @BotFather bot >> Bot Settings >> Group Privacy >> Turn off
