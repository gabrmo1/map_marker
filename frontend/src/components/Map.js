import React, {useState} from "react";
import {GoogleMap, LoadScript, Marker, InfoWindow} from '@react-google-maps/api';
import '../index.css'
import {doFetch, doFetchBody} from "./api";

const containerStyle = {
    width: '800px',
    height: '600px',
};

export default function Map() {
    const [center] = useState({lat: 0, lng: 0});
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [text, setText] = useState('');
    const [errors, setErrors] = useState({});
    const [markers, setMarkers] = useState([]);
    const [selectedMarker, setSelectedMarker] = useState(null);

    function fetchMarkers() {
        try {
            doFetch('http://localhost:5001/api/markers', 'GET')
                .then((response) => {
                    if (response) {
                        response.json().then((data) => {
                            setMarkers(data);
                        })
                    }
                });
        } catch (error) {
            console.error('Failed to fetch markers:', error);
        }
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

        setErrors(newErrors);

        if (Object.keys(newErrors).length === 0) {
            try {
                doFetchBody('http://localhost:5001/api/markers', 'POST', {latitude, longitude, text})
                    .then(() => {
                        fetchMarkers();
                    });
            } catch (error) {
                console.error('Failed to save marker:', error);
            }
        }
    }

    return (
        <div className="container stylized">
            <div className="row">
                <div className="col-md-6">
                    <form onSubmit={handleSubmit}>
                        <h1 className="mb-5 text-center text-shadow-black">Criar marcador</h1>
                        <hr/>
                        <div className="mb-4">
                            <label htmlFor="latitude" className="form-label">Latitude</label>
                            <input type="number" className={`form-control ${errors.latitude && 'is-invalid'}`} id="latitude" value={latitude} onChange={(e) => setLatitude(e.target.value)}/>
                            {errors.latitude && (
                                <div className="invalid-feedback position-absolute">{errors.latitude}</div>
                            )}
                        </div>
                        <hr/>
                        <div className="mb-4">
                            <label htmlFor="longitude" className="form-label">Longitude</label>
                            <input type="number" className={`form-control ${errors.longitude && 'is-invalid'}`} id="longitude" value={longitude} onChange={(e) => setLongitude(e.target.value)}/>
                            {errors.longitude && (
                                <div className="invalid-feedback position-absolute">{errors.longitude}</div>
                            )}
                        </div>
                        <hr/>
                        <div className="mb-4">
                            <label htmlFor="texto" className="form-label">Texto</label>
                            <textarea className={`form-control ${errors.text && 'is-invalid'}`} id="texto" value={text} onChange={(e) => setText(e.target.value)}></textarea>
                            {errors.text && (
                                <div className="invalid-feedback position-absolute">{errors.text}</div>
                            )}
                        </div>
                        <hr className="mb-5"/>
                        <button type="submit" className="btn btn-primary w-100">Enviar</button>
                    </form>
                </div>
                <div className="col-md-6 d-flex align-items-center justify-content-center">
                    <LoadScript googleMapsApiKey="AIzaSyDkEJ7mjBSZXEK8d4_Cq_x9SXi_ZAjvaiA">
                        <GoogleMap mapContainerClassName="bordered" mapContainerStyle={containerStyle} center={center} zoom={2} options={{disableDefaultUI: true}} onLoad={fetchMarkers}>
                            {markers.map((marker) => (
                                <Marker key={marker.id} position={{lat: marker.latitude, lng: marker.longitude}} title={marker.text} onClick={() => setSelectedMarker(marker)}/>
                            ))}
                            {selectedMarker && (
                                <InfoWindow position={{ lat: selectedMarker.latitude, lng: selectedMarker.longitude }} onCloseClick={() => {setSelectedMarker(null)}}>
                                    <div>
                                        {selectedMarker.text}
                                    </div>
                                </InfoWindow>
                            )}
                        </GoogleMap>
                    </LoadScript>
                </div>
            </div>
        </div>
    )
}