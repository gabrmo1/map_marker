const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('', {});

sequelize
    .authenticate()
    .then(() => {
        console.log('Connected to the database');
    })
    .catch((error) => {
        console.error('Unable to connect to the database:', error);
    });

module.exports = { sequelize };