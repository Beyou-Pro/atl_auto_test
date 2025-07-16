import React, { useEffect, useState } from 'react';

export default function App() {
    const [products, setProducts] = useState([]);
    const [carriers, setCarriers] = useState([]);
    const [payments, setPayments] = useState([]);

    const [cart, setCart] = useState([]);
    const [checkoutMode, setCheckoutMode] = useState(false);
    const [form, setForm] = useState({
        billing: { street: '', city: '', zipcode: '', country: '' },
        shipping: { street: '', city: '', zipcode: '', country: '' },
        paymentId: '',
        carrierId: ''
    });


    useEffect(() => {
        // Fetch products
        fetch('http://localhost:8080/product/products', {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(setProducts)
            .catch(console.error);

        // Fetch carriers
        fetch('http://localhost:8080/carriers', {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(setCarriers)
            .catch(console.error);

        // Fetch payment methods
        fetch('http://localhost:8080/payment', {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(setPayments)
            .catch(console.error);

        // Fetch cart from session
        fetch('http://localhost:8080/cart', {
            credentials: 'include'
        })
            .then(res => res.json())
            .then(setCart)
            .catch(console.error);

    }, []);

    const addToCart = (product, quantity) => {
        fetch('http://localhost:8080/cart/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                productId: product.id,
                quantity: quantity
            }),
            credentials: 'include'
        })
            .then(() => {
                // Refresh cart from backend
                return fetch('http://localhost:8080/cart', {
                    credentials: 'include'
                })
                    .then(res => res.json())
                    .then(setCart);
            })
            .catch(console.error);
    };

    const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    const handleSubmit = (e) => {
        e.preventDefault();

        const orderRequest = {
            customerId: "mock-customer-id-uuid",
            billingAddress: {
                ...form.billing,
                addressType: "BILLING"
            },
            shippingAddress: {
                ...form.shipping,
                addressType: "SHIPPING"
            },
            carrierId: form.carrierId,
            paymentId: form.paymentId,
            orderTotal: total,
            orderItems: cart.map(item => ({
                productId: item.productId,
                quantity: item.quantity
            }))
        };


        fetch('http://localhost:8080/order', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderRequest),
            credentials: 'include'
        })
            .then(res => res.json())
            .then(data => {
                alert('Commande validée ! ID: ' + data.id);
                // Clear backend cart session
                fetch('http://localhost:8080/cart/clear', {
                    method: 'POST',
                    credentials: 'include'
                }).then(() => {
                    setCart([]);
                    setForm({ delivery: '', billing: '', paymentId: '', carrierId: '' });
                    setCheckoutMode(false);
                });
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

    const handleQuantityChange = (id, value) => {
        setQuantities({
            ...quantities,
            [id]: parseInt(value) || 1
        });
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
                        onChange={e => handleQuantityChange(p.id, e.target.value)}
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
        const { name, value } = e.target;
        const [section, field] = name.split('.');

        if (field) {
            setForm({
                ...form,
                [section]: {
                    ...form[section],
                    [field]: value
                }
            });
        } else {
            setForm({
                ...form,
                [name]: value
            });
        }
    };

    return (
        <form onSubmit={onSubmit}>
            <h2>Finaliser la commande</h2>

            <h3>Adresse de livraison</h3>
            <div>
                <input name="shipping.street" placeholder="Rue" value={form.shipping.street} onChange={handleChange}
                       required/>
                <input name="shipping.city" placeholder="Ville" value={form.shipping.city} onChange={handleChange}
                       required/>
                <input name="shipping.zipcode" placeholder="Code postal" value={form.shipping.zipcode}
                       onChange={handleChange} required/>
                <input name="shipping.country" placeholder="Pays" value={form.shipping.country} onChange={handleChange}
                       required/>
            </div>

            <h3>Adresse de facturation</h3>
            <div>
                <input name="billing.street" placeholder="Rue" value={form.billing.street} onChange={handleChange}
                       required/>
                <input name="billing.city" placeholder="Ville" value={form.billing.city} onChange={handleChange}
                       required/>
                <input name="billing.zipcode" placeholder="Code postal" value={form.billing.zipcode}
                       onChange={handleChange} required/>
                <input name="billing.country" placeholder="Pays" value={form.billing.country} onChange={handleChange}
                       required/>
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
