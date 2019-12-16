heroku apps:create geek7-shop-admin-ui
heroku addons:create heroku-postgresql:hobby-dev --app geek7-shop-admin-ui
heroku config:set JDBC_DRIVER_CLASS=org.postgresql.Driver HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect --app geek7-shop-admin-ui