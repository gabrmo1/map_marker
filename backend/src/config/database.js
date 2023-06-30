const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('postgres://postgres:Maps123!@db:5432/maps', {
    host: 'db',
    dialect: 'postgres',
});

sequelize
    .authenticate()
    .then(() => {
        console.log('Connected to the database');
    })
    .catch((error) => {
        console.error('Unable to connect to the database:', error);
    });

module.exports = { sequelize };
