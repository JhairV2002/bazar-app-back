package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.res.ProductResDTO;
import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.factory.IProductPromo;
import jv.bazar.amacame.factory.IPromo;
import jv.bazar.amacame.factory.ProductPromoFactory;
import jv.bazar.amacame.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillDetailLineService {

    private final ProductService  productService;
    private final ProductPromoFactory productPromoFactory;

    public BillDetailLineService (ProductService productService, ProductPromoFactory productPromoFactory) {
        this.productService = productService;
        this.productPromoFactory = productPromoFactory;
    }



    public BigDecimal calculateTotalPriceByProduct(BillDetailLineReqDTO billDetailLineReqDTO) {
        try{
            ProductResDTO product = productService.getProductById(billDetailLineReqDTO.getProduct().getProductId());
            BigDecimal totalPrice = product.getProductSalePrice().multiply(BigDecimal.valueOf(billDetailLineReqDTO.getQuantity()));
            // validates if any promo is applied
            if (billDetailLineReqDTO.getHasPromo() &&  billDetailLineReqDTO.getPromo() != null) {
                IProductPromo promo = productPromoFactory.getPromo(billDetailLineReqDTO.getPromo().getPromoType().name());
                totalPrice = promo.applyPromo(totalPrice, billDetailLineReqDTO.getPromo(), billDetailLineReqDTO.getQuantity());
            }
            return totalPrice;
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "El producto no existe", e.getMessage());
        }
    }

    public BigDecimal calculateTotalProfitByProduct(BillDetailLineReqDTO billDetailLineReqDTO) {
        try {
            ProductResDTO product = productService.getProductById(billDetailLineReqDTO.getProduct().getProductId());
            BigDecimal totalProfit = product.getProductProfit().multiply(BigDecimal.valueOf(billDetailLineReqDTO.getQuantity()));
            // validates if any promo is applied
            if (billDetailLineReqDTO.getHasPromo() &&  billDetailLineReqDTO.getPromo() != null) {
                IProductPromo promo = productPromoFactory.getPromo(billDetailLineReqDTO.getPromo().getPromoType().name());
                totalProfit = promo.applyPromo(totalProfit, billDetailLineReqDTO.getPromo(), billDetailLineReqDTO.getQuantity());
            }
            return totalProfit;
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage());
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
