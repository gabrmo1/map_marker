const { Sequelize } = require('sequelize');

const dialect = 'postgres';
const host = 'db';
const port = '5432';
const database = 'maps';
const user = 'postgres';
const password = 'Maps123!';

const sequelize = new Sequelize(`${dialect}://${user}:${password}@${host}:${port}/${database}`, {
  host,
  dialect,
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
