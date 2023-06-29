const {DataTypes} = require('sequelize');
const sequelize = require('../config/database').sequelize;

const Marker = sequelize.define('Marker', {
    latitude: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    longitude: {
        type: DataTypes.FLOAT,
        allowNull: false,
    },
    text: {
        type: DataTypes.STRING,
        allowNull: false,
    },
});

module.exports = Marker;
