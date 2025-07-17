import React, { useState } from 'react';
import CartSummary from './checkout/CartSummary';
import ShippingForm from './checkout/ShippingForm';
import CarrierSelection from './checkout/CarrierSelection';
import FinalReview from './checkout/FinalReview';

export default function Checkout(props) {
    const [currentStep, setCurrentStep] = useState(0);

    const steps = [
        <CartSummary {...props} onNext={() => setCurrentStep(1)} />,
        <ShippingForm {...props} onNext={() => setCurrentStep(2)} onBack={() => setCurrentStep(0)} />,
        <CarrierSelection {...props} onNext={() => setCurrentStep(3)} onBack={() => setCurrentStep(1)} />,
        <FinalReview {...props} onSubmit={props.onSubmit} onBack={() => setCurrentStep(2)} />
    ];

    return <div>{steps[currentStep]}</div>;
}
