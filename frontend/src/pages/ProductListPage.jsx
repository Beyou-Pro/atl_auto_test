import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

export default function ProductListPage() {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/product/products', { credentials: 'include' })
            .then(res => res.json())
            .then(setProducts)
            .catch(console.error);
    }, []);

    return (
        <div>
            <h1>Nos Produits</h1>
            {products.length === 0 && <p>Chargement...</p>}
            {products.map(product => (
                <div key={product.id}>
                    <h3>
                        <Link to={`/products/${product.id}`}>{product.name}</Link>
                    </h3>
                    <p>Prix: {product.price} €</p>
                </div>
            ))}
        </div>
    );
}
