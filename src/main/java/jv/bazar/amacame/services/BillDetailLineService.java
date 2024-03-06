package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillDetailLineService {
    @Autowired
    private ProductRepository productRepository;



    public BigDecimal calculateTotalPriceByProduct(BillDetailLineReqDTO billDetailLineReqDTO) {
        try{
            Product product = productRepository.findByProductIdAndIsActive(billDetailLineReqDTO.getProduct().getProductId(), true);
            return product.getProductSalePrice().multiply(BigDecimal.valueOf(billDetailLineReqDTO.getQuantity()));
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "El producto no existe", e.getMessage());
        }
    }

    public BigDecimal calculateTotalProfitByProduct(BillDetailLineReqDTO billDetailLineReqDTO) {
        try {
            Product product = productRepository.findByProductIdAndIsActive(billDetailLineReqDTO.getProduct().getProductId(), true);
            return product.getProductProfit().multiply(BigDecimal.valueOf(billDetailLineReqDTO.getQuantity()));
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "El producto no existe", e.getMessage());
        }
    }

    public List<BillDetailLineReqDTO> calculatePriceAndProfitByProduct(List<BillDetailLineReqDTO> billDetailLineReqDTOList) {
        for (BillDetailLineReqDTO billDetailLineReqDTO : billDetailLineReqDTOList) {
            billDetailLineReqDTO.setTotalProfitByProduct(calculateTotalProfitByProduct(billDetailLineReqDTO));
            billDetailLineReqDTO.setTotalPriceByProduct(calculateTotalPriceByProduct(billDetailLineReqDTO));
        }
        return billDetailLineReqDTOList;
    }
}
