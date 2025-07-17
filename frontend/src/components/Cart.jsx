import React from 'react';

export default function Cart({ cart, onCheckout }) {
    const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div>
            <h2>Panier</h2>
            {cart.length === 0 ? <p>Panier vide</p> :
                cart.map(item => (
                    <div key={item.productId}>
                        {item.name} x {item.quantity} = {(item.price * item.quantity).toFixed(2)} €
                    </div>
                ))
            }
            <p>Total: {total.toFixed(2)} €</p>
            {cart.length > 0 && <button onClick={onCheckout}>Commander</button>}
        </div>
    );
}
