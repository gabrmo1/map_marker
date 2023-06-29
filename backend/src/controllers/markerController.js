const Marker = require('../models/Marker')

const getMarkers = async (req, res) => {
    try {
        const markers = await Marker.findAll();

        return res.status(200).json(markers);
    } catch (error) {
        return res.status(500).json({error: 'Ocorreu um erro ao buscar os marcadores'});
    }
};

const createMarker = async (req, res) => {
    const {latitude, longitude, title, text} = req.body;

    if (!latitude || !longitude || !title || !text) {
        return res.status(400).json({error: 'Nenhum dos dados pode estar vazio.'});
    }
    if (isNaN(latitude) || isNaN(longitude)) {
        return res.status(400).json({error: 'O objeto enviado é inválido. A latitude e a longitude devem ser números.'});
    }
    if (latitude < -90 || latitude > 90) {
        return res.status(400).json({error: 'A latitude deve estar no intervalo válido (-90 a 90).'});
    }
    if (longitude < -180 || longitude > 180) {
        return res.status(400).json({error: 'A longitude deve estar no intervalo válido (-180 a 180).'});
    }

    try {
        const marker = await Marker.create({latitude, longitude, title, text});
        return res.status(201).json(marker);
    } catch (err) {
        return res.status(500).json({error: 'Ocorreu um erro ao criar o marcador.', err});
    }
}

const deleteMarkers = async (req, res) => {
    const id = req.params.id;

    try {
        const marker = await Marker.findByPk(id);

        if (marker) {
            marker.destroy()
                .then(() => {
                    return res.status(200).json({success: 'Marcador removido'})
                });
        } else {
            return res.status(500).json({error: 'Ocorreu um erro ao buscar o marcador' + id + ' para remover'});
        }

    } catch {
        return res.status(500).json({error: 'Ocorreu um erro ao remover o marcador' + id});
    }
};


module.exports = {
    createMarker,
    getMarkers,
    deleteMarkers
};
