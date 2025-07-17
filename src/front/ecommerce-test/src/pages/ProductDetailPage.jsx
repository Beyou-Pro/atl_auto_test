import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

export default function ProductDetailPage() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/product/${id}`, { credentials: 'include' })
            .then(res => res.json())
            .then(setProduct)
            .catch(console.error);
    }, [id]);

    if (!product) return <p>Chargement...</p>;

    return (
        <div>
            <h1>{product.name}</h1>
            <p>Prix: {product.price} €</p>
            <p>Description: {product.description}</p>
            <Link to="/products">← Retour à la liste</Link>
        </div>
    );
}
