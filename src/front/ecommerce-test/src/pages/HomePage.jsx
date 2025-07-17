import React, { useEffect, useState } from 'react';
import ProductList from '../components/ProductList';
import Checkout from '../components/Checkout';
import Cart from '../components/Cart';

export default function HomePage() {
    const [products, setProducts] = useState([]);
    const [carriers, setCarriers] = useState([]);
    const [payments, setPayments] = useState([]);
    const [customerUUID, setCustomerUUID] = useState('');
    const [cart, setCart] = useState([]);
    const [checkoutMode, setCheckoutMode] = useState(false);
    const [form, setForm] = useState({
        billing: { street: '', city: '', zipcode: '', country: '' },
        shipping: { street: '', city: '', zipcode: '', country: '' },
        paymentId: '',
        carrierId: ''
    });

    useEffect(() => {
        // Set or retrieve UUID
        let uuid = localStorage.getItem('customerUUID');
        if (!uuid) {
            uuid = crypto.randomUUID();
            localStorage.setItem('customerUUID', uuid);
        }
        setCustomerUUID(uuid);

        fetch('http://localhost:8080/product/products', { credentials: 'include' })
            .then(res => res.json())
            .then(setProducts)
            .catch(console.error);

        fetch('http://localhost:8080/carriers', { credentials: 'include' })
            .then(res => res.json())
            .then(setCarriers)
            .catch(console.error);

        fetch('http://localhost:8080/payment', { credentials: 'include' })
            .then(res => res.json())
            .then(setPayments)
            .catch(console.error);

        fetch('http://localhost:8080/cart', { credentials: 'include' })
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
            .then(() => fetch('http://localhost:8080/cart', { credentials: 'include' }))
            .then(res => res.json())
            .then(setCart)
            .catch(console.error);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const orderRequest = {
            customerId: customerUUID,
            billingAddress: { ...form.billing, addressType: 'BILLING' },
            shippingAddress: { ...form.shipping, addressType: 'SHIPPING' },
            carrierId: form.carrierId,
            paymentId: form.paymentId,
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
                return fetch('http://localhost:8080/cart/clear', {
                    method: 'POST',
                    credentials: 'include'
                });
            })
            .then(() => {
                setCart([]);
                setForm({
                    billing: { street: '', city: '', zipcode: '', country: '' },
                    shipping: { street: '', city: '', zipcode: '', country: '' },
                    paymentId: '',
                    carrierId: ''
                });
                setCheckoutMode(false);
            })
            .catch(console.error);
    };

    const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div>
            <h1>Mini E-Commerce</h1>

            {!checkoutMode ? (
                <>
                    <ProductList products={products} onAddToCart={addToCart} />
                    <Cart cart={cart} total={total} onCheckout={() => setCheckoutMode(true)} />
                </>
            ) : (
                <Checkout
                    cart={cart}
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
