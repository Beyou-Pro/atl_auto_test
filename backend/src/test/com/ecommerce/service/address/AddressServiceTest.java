package com.ecommerce.service.address;

import com.ecommerce.entity.address.Address;
import com.ecommerce.model.address.request.AddressRequest;
import com.ecommerce.repository.address.AddressRepository;
import com.ecommerce.service.address.impl.AddressServiceImpl;
import com.ecommerce.utils.enums.AddressType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressServiceImpl(addressRepository);
    }

    @Test
    void shouldCreateAndSaveAddress() {
        AddressRequest request = new AddressRequest(
                "123 rue Lafayette",
                "Paris",
                "75010",
                "France",
                AddressType.SHIPPING
        );

        Address savedAddress = Address.builder()
                .id(UUID.randomUUID())
                .street(request.street())
                .city(request.city())
                .zipcode(request.zipcode())
                .country(request.country())
                .addressType(request.addressType())
                .build();

        when(addressRepository.save(any(Address.class))).thenReturn(savedAddress);

        Address result = addressService.createAndSaveAddress(request);

        assertThat(result).isNotNull();
        assertThat(result.getStreet()).isEqualTo("123 rue Lafayette");
        assertThat(result.getCity()).isEqualTo("Paris");
        assertThat(result.getZipcode()).isEqualTo("75010");
        assertThat(result.getCountry()).isEqualTo("France");
        assertThat(result.getAddressType()).isEqualTo(AddressType.SHIPPING);

        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository, times(1)).save(captor.capture());

        Address captured = captor.getValue();
        assertThat(captured.getStreet()).isEqualTo("123 rue Lafayette");
    }
}
