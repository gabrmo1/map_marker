import React from 'react';
import Map from "./components/Map";
import 'bootstrap/dist/css/bootstrap.css'
import './index.css'

function App() {
  return (
      <div className="App">
        <h1 className="text-center text-shadow-white">Adicionar marcadores: Google Maps</h1>
          <br/>
          <Map/>
      </div>
  );
}

export default App;
