package com.productmanager.application.service.impl;

import com.productmanager.application.dto.SupplierDto;
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
    private SupplierDto supplierDto;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setEmail("mail@fi.unju.edu.ar");
        supplier.setPhone("388-154-123456");
        supplier.setName("Juan Perez");

        supplierDto = new SupplierDto();
        supplierDto.setId(1L);
        supplierDto.setEmail("dto@fi.unju.edu.ar");
        supplierDto.setPhone("388-154-333333");
        supplierDto.setName("Juan Perez Dto");
    }

    @AfterEach
    void tearDown() {
        supplier = null;
        supplierDto = null;
    }

    @Test
    void createSupplier_EmailAlreadyExists_ThrowsException() {
        supplierDto.setEmail(supplier.getEmail());

        when(supplierRepository.findSupplierByEmail(supplierDto.getEmail()))
                .thenReturn(Optional.of(supplier));

        InvalidOperationException exception = assertThrows(InvalidOperationException.class, () -> {
            supplierService.createSupplier(supplierDto);
        });

        assertEquals("Supplier", exception.getResourceName());
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void createSupplier_Success() {
        when(supplierRepository.findSupplierByEmail(supplierDto.getEmail()))
                .thenReturn(Optional.empty());

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(supplierDto);

        assertNotNull(createdSupplier);
        assertEquals("dto@fi.unju.edu.ar", createdSupplier.getEmail());
        assertEquals("388-154-333333", createdSupplier.getPhone());
        assertEquals("Juan Perez Dto", createdSupplier.getName());
        verify(supplierRepository, times(1)).findSupplierByEmail(supplierDto.getEmail());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }


    @Test
    void updateSupplier_Success() {
        when(supplierRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(any());

        Supplier result = supplierService.updateSupplier(supplierDto);

        assertNotNull(result);
        supplierDto.setEmail("dto@fi.unju.edu.ar");
        supplierDto.setPhone("388-154-333333");
        supplierDto.setName("Juan Perez Dto");
        assertEquals("Juan Perez Dto", result.getName());
        assertEquals("dto@fi.unju.edu.ar", result.getEmail());
        assertEquals("388-154-333333", result.getPhone());
        verify(supplierRepository, times(1)).findById(anyLong());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void updateSupplier_NotFound() {
        when(supplierRepository.findById(supplierDto.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            supplierService.updateSupplier(supplierDto);
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