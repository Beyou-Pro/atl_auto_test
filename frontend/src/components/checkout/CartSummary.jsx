import React from 'react';

export default function CartSummary({ cart, onNext }) {
    return (
        <div>
            <h3>Résumé du panier</h3>
            {cart.map(item => (
                <div key={item.productId}>{item.name} x {item.quantity}</div>
            ))}
            <button onClick={onNext}>Suivant</button>
        </div>
    );
}
