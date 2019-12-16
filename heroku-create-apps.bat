heroku apps:create geek4-shop-admin-ui
heroku addons:create heroku-postgresql:hobby-dev --app geek4-shop-admin-ui
heroku config:set JDBC_DRIVER_CLASS=org.postgresql.Driver HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect --app geek4-shop-admin-ui