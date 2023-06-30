const express = require('express');

const router = express.Router();
const markerController = require('../controllers/markerController');

router.post('/', markerController.createMarker);
router.get('/', markerController.getMarkers);
router.delete('/:id', markerController.deleteMarkers);

module.exports = router;
