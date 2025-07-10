import React, { useState } from 'react';

const products = [
  { id: 1, name: 'Produit A', price: 10 },
  { id: 2, name: 'Produit B', price: 20 },
  { id: 3, name: 'Produit C', price: 15 },
];

export default function App() {
  const [cart, setCart] = useState([]);
  const [checkoutMode, setCheckoutMode] = useState(false);
  const [form, setForm] = useState({
    delivery: '',
    billing: '',
    payment: '',
  });

  const addToCart = (product, quantity) => {
    const existing = cart.find(item => item.id === product.id);
    if (existing) {
      setCart(cart.map(item =>
          item.id === product.id ? { ...item, quantity: item.quantity + quantity } : item
      ));
    } else {
      setCart([...cart, { ...product, quantity }]);
    }
  };

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Commande envoyée :', { cart, ...form });
    alert('Commande validée !');
    setCart([]);
    setForm({ delivery: '', billing: '', payment: '' });
    setCheckoutMode(false);
  };

  return (
      <div>
        <h1>Mini E-Commerce</h1>

        {!checkoutMode && (
            <>
              <ProductList onAddToCart={addToCart} />
              <Cart cart={cart} total={total} onCheckout={() => setCheckoutMode(true)} />
            </>
        )}

        {checkoutMode && (
            <Checkout
                form={form}
                setForm={setForm}
                onSubmit={handleSubmit}
                onBack={() => setCheckoutMode(false)}
            />
        )}
      </div>
  );
}

function ProductList({ onAddToCart }) {
  const [quantities, setQuantities] = useState({});

  const handleChange = (id, value) => {
    setQuantities({ ...quantities, [id]: parseInt(value) || 1 });
  };

  return (
      <div>
        <h2>Produits</h2>
        {products.map(p => (
            <div key={p.id}>
              <span>{p.name} - {p.price}€</span>
              <input
                  type="number"
                  min="1"
                  value={quantities[p.id] || 1}
                  onChange={e => handleChange(p.id, e.target.value)}
              />
              <button onClick={() => onAddToCart(p, quantities[p.id] || 1)}>Ajouter</button>
            </div>
        ))}
      </div>
  );
}

function Cart({ cart, total, onCheckout }) {
  return (
      <div>
        <h2>Panier</h2>
        {cart.length === 0 && <p>Panier vide.</p>}
        {cart.map(item => (
            <div key={item.id}>
              {item.name} x {item.quantity} = {item.price * item.quantity}€
            </div>
        ))}
        <p>Total : {total}€</p>
        {cart.length > 0 && <button onClick={onCheckout}>Commander</button>}
      </div>
  );
}

function Checkout({ form, setForm, onSubmit, onBack }) {
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  return (
      <form onSubmit={onSubmit}>
        <h2>Finaliser la commande</h2>
        <div>
          <label>Adresse de livraison :</label>
          <input name="delivery" value={form.delivery} onChange={handleChange} required />
        </div>
        <div>
          <label>Adresse de facturation :</label>
          <input name="billing" value={form.billing} onChange={handleChange} required />
        </div>
        <div>
          <label>Moyen de paiement :</label>
          <select name="payment" value={form.payment} onChange={handleChange} required>
            <option value="">-- Choisir --</option>
            <option value="carte">Carte Bancaire</option>
            <option value="paypal">PayPal</option>
            <option value="virement">Virement</option>
          </select>
        </div>
        <button type="submit">Valider la commande</button>
        <button type="button" onClick={onBack}>Retour</button>
      </form>
  );
}
