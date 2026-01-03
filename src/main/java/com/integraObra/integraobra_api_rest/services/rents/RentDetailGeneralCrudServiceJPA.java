package com.integraObra.integraobra_api_rest.services.rents;

import com.integraObra.integraobra_api_rest.dto.rents.RentDetailItemDTO;
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
    public List<RentDetail> createRentDetail(List<RentDetailItemDTO> rentDetailItemDTOS, Long rentId) {
        //Buscar la renta
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new NotFoundException("Renta con ID " + rentId + " no encontrada."));

        //Recorrer los items del detalle de la renta
        for (RentDetailItemDTO itemDTO : rentDetailItemDTOS) {
            //Buscar el producto
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new NotFoundException("Producto con ID " + itemDTO.getProductId() + " no encontrado."));

            //Logica para el descuento segun la cantidad y dias rentados se implementaria aqui
            BigDecimal discountRate;
            BigDecimal totalPriceWithDiscount;

            if (itemDTO.getDaysRented() >= 30) {
                discountRate = new BigDecimal("0.20"); //20% de descuento
            } else if (itemDTO.getDaysRented() >= 15) {
                discountRate = new BigDecimal("0.10"); //10% de descuento
            } else {
                discountRate = BigDecimal.ZERO; //Sin descuento
            }

            //Calcular el total del precio con el descuento
            BigDecimal rentPrice = product.getRentPrice();
            BigDecimal quantity = new BigDecimal(itemDTO.getQuantity());
            BigDecimal days = new BigDecimal(itemDTO.getDaysRented());

            // Subtotal = rentPrice * quantity * days
            BigDecimal subtotal = rentPrice.multiply(quantity).multiply(days);

            // Descuento = subtotal * discountRate
            BigDecimal discount = subtotal.multiply(discountRate);

            // Total con descuento = subtotal - descuento
            totalPriceWithDiscount = subtotal.subtract(discount);

            RentDetail rentDetail = new RentDetail();
            rentDetail.setRent(rent);
            rentDetail.setProduct(product);
            rentDetail.setQuantity(itemDTO.getQuantity());
            rentDetail.setDaysRented(itemDTO.getDaysRented());
            rentDetail.setDiscountRate(discountRate);
            rentDetail.setTotalPriceWithDiscount(totalPriceWithDiscount);

            //Descontamos el stock a la entidad producto
            product.decreaseStock(itemDTO.getQuantity());

            rentDetailRepository.save(rentDetail);
        }
        //retorna la lista de todos los detalles guardados
        return rentDetailRepository.findByRentId(rentId);
    }
}
