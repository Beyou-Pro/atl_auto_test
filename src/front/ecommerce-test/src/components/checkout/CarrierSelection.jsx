import React from 'react';

export default function CarrierSelection({ form, setForm, carriers, payments, onNext, onBack }) {
    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    return (
        <div>
            <h3>Livraison et Paiement</h3>
            <label>Transporteur</label>
            <select name="carrierId" value={form.carrierId} onChange={handleChange}>
                <option value="">-- Choisir --</option>
                {carriers.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
            </select>

            <label>Paiement</label>
            <select name="paymentId" value={form.paymentId} onChange={handleChange}>
                <option value="">-- Choisir --</option>
                {payments.map(p => <option key={p.id} value={p.id}>{p.method}</option>)}
            </select>

            <button onClick={onBack}>Retour</button>
            <button onClick={onNext}>Suivant</button>
        </div>
    );
}
