import React, { useState } from 'react';
import {
  GoogleMap, InfoWindow, LoadScript, Marker,
} from '@react-google-maps/api';
import '../index.css';
import { doFetch, doFetchBody } from './api';
import blue from './blue_icon.webp';
import red from './red_icon.webp';

const containerStyle = {
  width: '800px',
  height: '600px',
};

export default function Map() {
  const [center] = useState({ lat: 0, lng: 0 });
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const [text, setText] = useState('');
  const [title, setTitle] = useState('');
  const [errors, setErrors] = useState({});
  const [markers, setMarkers] = useState([]);
  const [selectedMarker, setSelectedMarker] = useState(null);

  function setNewMarkerPosition(event) {
    const { latLng } = event;
    const lat = latLng.lat();
    const lng = latLng.lng();

    setLatitude(lat);
    setLongitude(lng);

    const newList = markers.filter((marker) => marker.id != null);
    setMarkers(newList);
    setMarkers((prevMarkers) => [...prevMarkers, {
      id: null, latitude: lat, longitude: lng, text: '',
    }]);
  }

  const handleMapClick = (event) => {
    setSelectedMarker(null);
    setNewMarkerPosition(event);
  };

  const handleMarkerDrag = (event) => {
    setNewMarkerPosition(event);
  };

  function fetchMarkers() {
    doFetch('http://localhost:5001/api/markers', 'GET')
      .then((response) => {
        if (response) {
          response.json().then((data) => {
            setMarkers(data);
          });
        }
      });
  }

  function handleSubmit(event) {
    event.preventDefault();

    const newErrors = {};
    if (!latitude) {
      newErrors.latitude = 'O preenchimento deste campo é obrigatório';
    }
    if (!longitude) {
      newErrors.longitude = 'O preenchimento deste campo é obrigatório';
    }
    if (!text) {
      newErrors.text = 'O preenchimento deste campo é obrigatório';
    }
    if (!title) {
      newErrors.title = 'O preenchimento deste campo é obrigatório';
    } else if (title.length > 50) {
      newErrors.title = 'O título pode ter apenas 50 caracteres';
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      doFetchBody('http://localhost:5001/api/markers', 'POST', {
        latitude, longitude, title, text,
      })
        .then(() => {
          fetchMarkers();
        });
    }
  }

  function removeMarker(id) {
    if (id === null) {
      const newMarkers = markers.filter((marker) => marker.id != null);
      setMarkers(newMarkers);
      setSelectedMarker(null);
    } else {
      doFetch(`http://localhost:5001/api/markers/${id}`, 'DELETE')
        .then(() => {
          setSelectedMarker(null);
          fetchMarkers();
        });
    }
  }

  // Usado para melhorar a visualizaçao dentro do InfoWindow
  function formatText(paramText, interval) {
    let result = '';
    for (let i = 0; i < paramText.length; i += interval) {
      result += `${paramText.substr(i, interval)}\n`;
    }
    return result;
  }

  function returnIconMap(marker) {
    return new window.google.maps.MarkerImage(
      (marker.id ? red : blue),
      null, /* size is determined at runtime */
      null, /* origin is 0,0 */
      null, /* anchor is bottom center of the scaled image */
      new window.google.maps.Size(15, 24),
    );
  }

  return (
    <div className="container stylized">
      <div className="row">
        <div className="col-md-6">
          <form onSubmit={handleSubmit}>
            <h1 className="mb-5 text-center text-shadow-black">Criar marcador</h1>
            <div className="mb-4">
              <label htmlFor="latitude" className="form-label">Latitude</label>
              <input type="number" className={`form-control ${errors.latitude && 'is-invalid'}`} id="latitude" value={latitude} onChange={(e) => setLatitude(e.target.value)} />
              {errors.latitude && (
                <div className="invalid-feedback position-absolute">{errors.latitude}</div>
              )}
            </div>
            <hr />
            <div className="mb-4">
              <label htmlFor="longitude" className="form-label">Longitude</label>
              <input type="number" className={`form-control ${errors.longitude && 'is-invalid'}`} id="longitude" value={longitude} onChange={(e) => setLongitude(e.target.value)} />
              {errors.longitude && (
                <div className="invalid-feedback position-absolute">{errors.longitude}</div>
              )}
            </div>
            <hr />
            <div className="mb-4">
              <label htmlFor="texto" className="form-label">Título</label>
              <input type="text" className={`form-control ${errors.title && 'is-invalid'}`} id="title" value={title} onChange={(e) => setTitle(e.target.value)} />
              {errors.title && (
                <div className="invalid-feedback position-absolute">{errors.title}</div>
              )}
            </div>
            <hr />
            <div className="mb-4">
              <label htmlFor="texto" className="form-label">Texto</label>
              <textarea className={`form-control ${errors.text && 'is-invalid'}`} id="texto" value={text} onChange={(e) => setText(e.target.value)} />
              {errors.text && (
                <div className="invalid-feedback position-absolute">{errors.text}</div>
              )}
            </div>
            <hr className="mb-3" />
            <button type="submit" className="btn btn-primary w-100">Enviar</button>
          </form>
        </div>
        <div className="col-md-6 d-flex align-items-center justify-content-center">
          <LoadScript googleMapsApiKey="AIzaSyCxGT6Q7lWCxS9B71BTac6-GYYfk-Mfazk">
            <GoogleMap
              mapContainerClassName="radius-border"
              mapContainerStyle={containerStyle}
              center={center}
              zoom={2}
              options={{ disableDefaultUI: true }}
              onClick={handleMapClick}
              /* eslint-disable-next-line react/jsx-no-bind */
              onLoad={fetchMarkers}
            >
              {markers.map((marker) => (
                <Marker
                  key={marker.id}
                  position={{ lat: marker.latitude, lng: marker.longitude }}
                  title={marker.text}
                  onClick={() => setSelectedMarker(marker)}
                  draggable={(marker.id === null)}
                  onDragEnd={handleMarkerDrag}
                  icon={returnIconMap(marker)}
                />
              ))}
              {selectedMarker && (
                <InfoWindow
                  position={{ lat: selectedMarker.latitude, lng: selectedMarker.longitude }}
                  onCloseClick={() => { setSelectedMarker(null); }}
                >
                  <div className="text-center">
                    <div>
                      <h6>{selectedMarker.title}</h6>
                    </div>
                    <div>
                      {formatText(selectedMarker.text, 50)}
                    </div>
                    <br />
                    <button type="button" className="btn btn-danger w-100 p-0" onClick={() => { removeMarker(selectedMarker.id); }}>Remover</button>
                  </div>
                </InfoWindow>
              )}
            </GoogleMap>
          </LoadScript>
        </div>
      </div>
    </div>
  );
}
