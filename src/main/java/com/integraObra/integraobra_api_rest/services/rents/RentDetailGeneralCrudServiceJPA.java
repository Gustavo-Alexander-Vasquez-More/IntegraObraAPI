package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.RentDetailItemDTO;
import com.integraObra.integraobra_api_rest.dto.rents.RentDetailResponseDTO;
import com.integraObra.integraobra_api_rest.exceptions.NotFoundException;
import com.integraObra.integraobra_api_rest.models.Product;
import com.integraObra.integraobra_api_rest.models.Rent;
import com.integraObra.integraobra_api_rest.models.RentDetail;
import com.integraObra.integraobra_api_rest.repositories.ProductRepository;
import com.integraObra.integraobra_api_rest.repositories.RentDetailRepository;
import com.integraObra.integraobra_api_rest.repositories.RentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RentDetailGeneralCrudServiceJPA {
    private final RentDetailRepository rentDetailRepository;
    private final RentRepository rentRepository;
    private final ProductRepository productRepository;

    public RentDetailGeneralCrudServiceJPA(RentDetailRepository rentDetailRepository, RentRepository rentRepository, ProductRepository productRepository) {
        this.rentDetailRepository = rentDetailRepository;
        this.rentRepository = rentRepository;
        this.productRepository = productRepository;
    }

    //METODO PARA CREAR UN DETALLE DE RENTA
    @Transactional
    public List<RentDetailResponseDTO> createRentDetail(List<RentDetailItemDTO> rentDetailItemDTOS, Long rentId) {
        //BUSCAMOS LA RENTA PARA ASOCIAR EL DETALLE
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new NotFoundException("Renta con ID " + rentId + " no encontrada."));

        //RECORREMOS CADA ITEM DTO PARA CREAR EL DETALLE DE RENTA EN EL BUCLE
        for (RentDetailItemDTO itemDTO : rentDetailItemDTOS) {
            //BUSCAMOS EL PRODUCTO ITERADO PARA EVITAR INCONSISTENCIAS
            Product product = productRepository.findById(itemDTO.getIdProduct())
                    .orElseThrow(() -> new NotFoundException("Producto con ID " + itemDTO.getIdProduct() + " no encontrado."));

            //LOGICA PARA OBTENER LA TASA DE DESCUENTO SEGUN LOS DIAS DE RENTA
            BigDecimal discountRate;
            BigDecimal totalPriceWithDiscount;

            // Evaluar de mayor a menor umbral para evitar que un caso menor capture los mayores
            if (itemDTO.getDaysRented() >= 32) {
                discountRate = new BigDecimal("0.30"); //30% de descuento
            } else if (itemDTO.getDaysRented() >= 12) {
                discountRate = new BigDecimal("0.20"); //20% de descuento
            } else if (itemDTO.getDaysRented() >= 9) {
                discountRate = new BigDecimal("0.15"); //15% de descuento
            } else if (itemDTO.getDaysRented() >= 6) {
                discountRate = new BigDecimal("0.10"); //10% de descuento
            } else if (itemDTO.getDaysRented() >= 3) {
                discountRate = new BigDecimal("0.05"); //5% de descuento
            } else {
                discountRate = BigDecimal.ZERO; //Sin descuento
            }

            //CALCULAMOS EL PRECIO TOTAL CON DESCUENTO DE CADA ITEM(Producto)
            BigDecimal rentPrice = product.getRentPrice();
            BigDecimal quantity = new BigDecimal(itemDTO.getQuantity());
            BigDecimal days = new BigDecimal(itemDTO.getDaysRented());
            BigDecimal subtotal = rentPrice.multiply(quantity).multiply(days);
            BigDecimal discount = subtotal.multiply(discountRate);
            // Total con descuento = subtotal - descuento
            totalPriceWithDiscount = subtotal.subtract(discount);

            //ARMAMOS EL DETALLE DE RENTA
            RentDetail rentDetail = new RentDetail();
            rentDetail.setRent(rent);
            rentDetail.setProduct(product);
            rentDetail.setQuantity(itemDTO.getQuantity());
            rentDetail.setDaysRented(itemDTO.getDaysRented());
            rentDetail.setDiscountRate(discountRate);
            rentDetail.setTotalPriceWithDiscount(totalPriceWithDiscount);
            //COMO PASO FINAL DISMINUIMOS EL STOCK DEL PRODUCTO PARA REFLEJAR LA RENTA
            product.decreaseStock(itemDTO.getQuantity());

            rentDetailRepository.save(rentDetail);
        }
        //RETORNAMOS LA LISTA DE DETALLES DE RENTA CREADOS
        return rentDetailRepository.findByRentId(rentId).stream().map(rentDetail -> {
            RentDetailResponseDTO rentDetailResponseDTO = new RentDetailResponseDTO();
            rentDetailResponseDTO.setId(rentDetail.getId());
            //ASIGNAMOS EL PRODUCTO COMO ProductRentItemDTO
            Product product = rentDetail.getProduct();
            rentDetailResponseDTO.setProductRentItem(new com.integraObra.integraobra_api_rest.dto.products.ProductRentItemDTO(
                    product.getId(),
                    product.getName(),
                    product.getSku(),
                    product.getRentPrice()
            ));
            rentDetailResponseDTO.setDiscountRate(rentDetail.getDiscountRate());
            rentDetailResponseDTO.setDaysRented(rentDetail.getDaysRented());
            rentDetailResponseDTO.setQuantity(rentDetail.getQuantity());
            rentDetailResponseDTO.setTotalPriceWithDiscount(rentDetail.getTotalPriceWithDiscount());
            return rentDetailResponseDTO;
        }).toList();
    }

    //getRentDetailsByRentId
    public List<RentDetailResponseDTO> getRentDetailsByRentId(Long rentId) {
        return rentDetailRepository.findByRentId(rentId).stream().map(rentDetail -> {
            RentDetailResponseDTO rentDetailResponseDTO = new RentDetailResponseDTO();
            rentDetailResponseDTO.setId(rentDetail.getId());
            //ASIGNAMOS EL PRODUCTO COMO ProductRentItemDTO
            Product product = rentDetail.getProduct();
            rentDetailResponseDTO.setProductRentItem(new com.integraObra.integraobra_api_rest.dto.products.ProductRentItemDTO(
                    product.getId(),
                    product.getName(),
                    product.getSku(),
                    product.getRentPrice()
            ));
            rentDetailResponseDTO.setDiscountRate(rentDetail.getDiscountRate());
            rentDetailResponseDTO.setDaysRented(rentDetail.getDaysRented());
            rentDetailResponseDTO.setQuantity(rentDetail.getQuantity());
            rentDetailResponseDTO.setTotalPriceWithDiscount(rentDetail.getTotalPriceWithDiscount());
            return rentDetailResponseDTO;
        }).toList();
    }
}
