package com.productmanager.application.service.impl;

import com.productmanager.application.exception.InvalidOperationException;
import com.productmanager.application.exception.ResourceNotFoundException;
import com.productmanager.application.model.entity.Supplier;
import com.productmanager.application.repository.ISupplierRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private ISupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;


    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setEmail("mail@fi.unju.edu.ar");
        supplier.setPhone("388-154-123456");
        supplier.setName("Juan Perez");
    }

    @AfterEach
    void tearDown() {
        supplier = null;
    }

    @Test
    void createSupplier_EmailAlreadyExists_ThrowsException() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            supplierService.createSupplier(supplier);
        });

        assertEquals("Supplier", exception.getResourceName());
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void createSupplier_Success() {
        when(supplierRepository.findSupplierByEmail(supplier.getEmail()))
                .thenReturn(Optional.of(supplier));

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(supplier);

        assertNotNull(createdSupplier);
        assertEquals("Juan Perez", createdSupplier.getName());
        verify(supplierRepository, times(1)).findSupplierByEmail(supplier.getEmail());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }


    @Test
    void updateSupplier_Success() {
        var updatedSupplier = supplier;
        updatedSupplier.setName("Pablo");
        updatedSupplier.setEmail("pablo@gmail.com");

        when(supplierRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        Supplier result = supplierService.updateSupplier(1L, supplier);


        assertNotNull(updatedSupplier);
        assertEquals("Pablo", result.getName());
        assertEquals("pablo@gmail.com", result.getEmail());
        assertEquals("388-154-123456", result.getPhone());
        verify(supplierRepository, times(1)).findById(anyLong());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void updateSupplier_NotFound() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            supplierService.updateSupplier(1L, supplier);
        });

        assertEquals("Supplier", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(supplierRepository, times(1)).findById(anyLong());
        verify(supplierRepository, times(0)).save(any(Supplier.class));
    }

    @Test
    void deleteSupplier_Success() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));

        supplierService.deleteSupplier(1L);

        verify(supplierRepository, times(1)).findById(anyLong());
        verify(supplierRepository, times(1)).delete(any(Supplier.class));
    }

    @Test
    void deleteSupplier_NotFound() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            supplierService.deleteSupplier(1L);
        });

        assertEquals("Supplier", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(supplierRepository, times(1)).findById(anyLong());
        verify(supplierRepository, times(0)).delete(any(Supplier.class));
    }

    @Test
    void getSupplierById_Success() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Supplier foundSupplier = supplierService.getSupplierById(1L);

        assertNotNull(foundSupplier);
        assertEquals(1L, foundSupplier.getId());
        verify(supplierRepository, times(1)).findById(anyLong());
    }

    @Test
    void getSupplierById_NotFound() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            supplierService.getSupplierById(1L);
        });

        assertEquals("Supplier", exception.getResourceName());
        assertEquals("id", exception.getFieldName());
        assertEquals(1L, exception.getFieldValue());
        verify(supplierRepository, times(1)).findById(anyLong());
    }
}