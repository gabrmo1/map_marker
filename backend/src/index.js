const express = require('express');

const app = express();
const cors = require('cors');
const markerRoutes = require('./routes/markerRoutes');
const Marker = require('./models/Marker');

app.use(cors());
app.use(express.json());

Marker.sync()
  .then(() => {
    console.log('Marker table synchronized with the model');
  })
  .catch((error) => {
    console.error('Unable to sync marker table:', error);
  });

app.use('/api/markers', markerRoutes);

const port = 5001;

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
