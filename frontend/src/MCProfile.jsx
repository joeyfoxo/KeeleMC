import React, { useEffect, useState } from 'react';

const MCProfile = ({ username, rankIndex = 0 }) => {
    const [ranks, setRanks] = useState([]);
    const [error, setError] = useState(null);

    const skinURL = `https://crafatar.com/renders/body/${username}?scale=10&default=MHF_Steve`;

    useEffect(() => {
        fetch('http://localhost:8088/api/get-all-ranks')
            .then(res => res.json())
            .then(data => setRanks(data))
            .catch(() => setError('Failed to load rank'));
    }, []);

    return (
        <div style={{ textAlign: 'center', fontFamily: 'monospace', marginTop: '30px' }}>
            <h2>{username}</h2>
            <h2>TEST</h2>
            <img
                src={skinURL}
                alt={`${username}'s Minecraft skin`}
                style={{ height: '250px', imageRendering: 'pixelated' }}
            />
            <p style={{ fontSize: '18px', marginTop: '10px' }}>
                {error ? error : `Rank: ${ranks[rankIndex] || 'Loading...'}`}
            </p>
        </div>
    );
};

export default MCProfile;