import React, { useState } from 'react';

const MCProfile = () => {
    const [input, setInput] = useState('');
    const [rank, setRank] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleFetch = () => {
        if (!input.trim()) return;

        setLoading(true);
        setError(null);
        setRank(null);

        fetch(`https://rank.joeyfox.dev/api/get-player-rank/${input}`)
            .then(res => {
                if (!res.ok) {
                    throw new Error("Failed to load rank");
                }
                return res.json();
            })
            .then(data => {
                setRank(data.rank); // expects { rank: "example" }
            })
            .catch(() => setError('Failed to load rank'))
            .finally(() => setLoading(false));
    };

    return (
        <div style={{ textAlign: 'center', fontFamily: 'monospace', marginTop: '30px' }}>
            <input
                type="text"
                placeholder="Enter UUID or username"
                value={input}
                onChange={(e) => setInput(e.target.value)}
                style={{ padding: '8px', fontSize: '16px', width: '300px' }}
            />
            <button
                onClick={handleFetch}
                style={{ padding: '8px 16px', marginLeft: '10px', fontSize: '16px', cursor: 'pointer' }}
            >
                Get Rank
            </button>

            <p style={{ fontSize: '18px', marginTop: '20px' }}>
                {loading
                    ? 'Loading...'
                    : error
                        ? error
                        : rank
                            ? `Rank: ${rank}`
                            : 'Enter a UUID or username to begin'}
            </p>
        </div>
    );
};

export default MCProfile;