import React from 'react';

export default function FinalReview({ form, cart, onSubmit, onBack }) {
    const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div>
            <h3>Résumé Final</h3>
            <div>
                {cart.map(item => (
                    <div key={item.productId}>{item.name} x {item.quantity}</div>
                ))}
            </div>
            <div>Total: {total.toFixed(2)} €</div>
            <button onClick={onBack}>Retour</button>
            <button onClick={onSubmit}>Confirmer</button>
        </div>
    );
}
