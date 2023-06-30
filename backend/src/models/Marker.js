const { DataTypes } = require('sequelize');
const { sequelize } = require('../config/database');

const Marker = sequelize.define('Marker', {
  latitude: {
    type: DataTypes.FLOAT,
    allowNull: false,
  },
  longitude: {
    type: DataTypes.FLOAT,
    allowNull: false,
  },
  title: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  text: {
    type: DataTypes.TEXT,
    allowNull: false,
  },
});

module.exports = Marker;
