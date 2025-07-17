import React, { useState } from 'react';

export default function ProductList({ products, onAddToCart }) {
    const [quantities, setQuantities] = useState({});

    const handleQuantityChange = (id, value) => {
        setQuantities({
            ...quantities,
            [id]: parseInt(value) || 1
        });
    };

    return (
        <div>
            <h2>Produits</h2>
            {products.length === 0 ? <p>Chargement...</p> :
                products.map(product => (
                    <div key={product.id}>
                        {product.name} - {product.price} €
                        <input type="number" min="1" value={quantities[product.id] || 1}
                               onChange={e => handleQuantityChange(product.id, e.target.value)} />
                        <button onClick={() => onAddToCart(product, quantities[product.id] || 1)}>Ajouter</button>
                    </div>
                ))}
        </div>
    );
}
