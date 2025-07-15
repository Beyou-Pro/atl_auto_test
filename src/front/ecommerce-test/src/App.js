import React, { useEffect, useState } from 'react';

export default function App() {
    const [products, setProducts] = useState([]);
    const [carriers, setCarriers] = useState([]);
    const [payments, setPayments] = useState([]);

    const [cart, setCart] = useState([]);
    const [checkoutMode, setCheckoutMode] = useState(false);
    const [form, setForm] = useState({
        delivery: '',
        billing: '',
        paymentId: '',
        carrierId: '',
    });

    useEffect(() => {
        // Fetch products
        fetch('http://localhost:8080/product/products')
            .then(res => res.json())
            .then(setProducts)
            .catch(console.error);

        // Fetch carriers
        fetch('http://localhost:8080/carriers')
            .then(res => res.json())
            .then(setCarriers)
            .catch(console.error);

        // Fetch payment methods
        fetch('http://localhost:8080/payment')
            .then(res => res.json())
            .then(setPayments)
            .catch(console.error);
    }, []);

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

        const orderRequest = {
            customer: { id: "mock-customer-id-uuid" }, // TODO: replace by logged in customer
            billingAddress: { id: form.billing },
            shippingAddress: { id: form.delivery },
            carrierId: form.carrierId,
            paymentId: form.paymentId,
            orderTotal: total,
            orderItems: cart.map(item => ({
                product: { id: item.id },
                quantity: item.quantity
            }))
        };

        fetch('http://localhost:8080/orders', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderRequest),
        })
            .then(res => res.json())
            .then(data => {
                alert('Commande validée ! ID: ' + data.id);
                setCart([]);
                setForm({ delivery: '', billing: '', paymentId: '', carrierId: '' });
                setCheckoutMode(false);
            })
            .catch(console.error);
    };


    return (
        <div>
            <h1>Mini E-Commerce</h1>

            {!checkoutMode && (
                <>
                    <ProductList products={products} onAddToCart={addToCart} />
                    <Cart cart={cart} total={total} onCheckout={() => setCheckoutMode(true)} />
                </>
            )}

            {checkoutMode && (
                <Checkout
                    form={form}
                    setForm={setForm}
                    onSubmit={handleSubmit}
                    onBack={() => setCheckoutMode(false)}
                    carriers={carriers}
                    payments={payments}
                />
            )}
        </div>
    );
}

function ProductList({ products, onAddToCart }) {
    const [quantities, setQuantities] = useState({});

    const handleChange = (id, value) => {
        setQuantities({ ...quantities, [id]: Math.max(1, parseInt(value) || 1) });
    };

    return (
        <div>
            <h2>Produits</h2>
            {products.length === 0 && <p>Chargement des produits...</p>}
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
            {cart.length === 0 ? <p>Panier vide.</p> : cart.map(item => (
                <div key={item.id}>
                    {item.name} x {item.quantity} = {(item.price * item.quantity).toFixed(2)}€
                </div>
            ))}
            <p>Total : {total.toFixed(2)}€</p>
            {cart.length > 0 && <button onClick={onCheckout}>Commander</button>}
        </div>
    );
}

function Checkout({ form, setForm, onSubmit, onBack, carriers, payments }) {
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
                <label>Transporteur :</label>
                <select name="carrierId" value={form.carrierId} onChange={handleChange} required>
                    <option value="">-- Choisir --</option>
                    {carriers.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
                </select>
            </div>
            <div>
                <label>Moyen de paiement :</label>
                <select name="paymentId" value={form.paymentId} onChange={handleChange} required>
                    <option value="">-- Choisir --</option>
                    {payments.map(p => <option key={p.id} value={p.id}>{p.method}</option>)}
                </select>
            </div>
            <button type="submit">Valider la commande</button>
            <button type="button" onClick={onBack}>Retour</button>
        </form>
    );
}
