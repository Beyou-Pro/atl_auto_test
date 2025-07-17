import React from 'react';

export default function ShippingForm({ form, setForm, onNext, onBack }) {
    const handleChange = (e) => {
        const { name, value } = e.target;
        const [section, field] = name.split('.');
        setForm({
            ...form,
            [section]: {
                ...form[section],
                [field]: value
            }
        });
    };

    return (
        <div>
            <h3>Adresses</h3>
            <div>
                <h4>Livraison</h4>
                <input name="shipping.street" placeholder="Rue" value={form.shipping.street} onChange={handleChange} />
                <input name="shipping.city" placeholder="Ville" value={form.shipping.city} onChange={handleChange} />
                <input name="shipping.zipcode" placeholder="Code Postal" value={form.shipping.zipcode} onChange={handleChange} />
                <input name="shipping.country" placeholder="Pays" value={form.shipping.country} onChange={handleChange} />
            </div>
            <div>
                <h4>Facturation</h4>
                <input name="billing.street" placeholder="Rue" value={form.billing.street} onChange={handleChange} />
                <input name="billing.city" placeholder="Ville" value={form.billing.city} onChange={handleChange} />
                <input name="billing.zipcode" placeholder="Code Postal" value={form.billing.zipcode} onChange={handleChange} />
                <input name="billing.country" placeholder="Pays" value={form.billing.country} onChange={handleChange} />
            </div>
            <button onClick={onBack}>Retour</button>
            <button onClick={onNext}>Suivant</button>
        </div>
    );
}
